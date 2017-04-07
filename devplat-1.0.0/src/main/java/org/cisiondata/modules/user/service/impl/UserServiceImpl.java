package org.cisiondata.modules.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.auth.entity.UserAttribute;
import org.cisiondata.modules.auth.service.IAuthService;
import org.cisiondata.modules.auth.web.WebUtils;
import org.cisiondata.modules.user.dao.UserDAO;
import org.cisiondata.modules.user.entity.AUser;
import org.cisiondata.modules.user.entity.Security;
import org.cisiondata.modules.user.service.IMessageService;
import org.cisiondata.modules.user.service.IUserService;
import org.cisiondata.utils.endecrypt.EndecryptUtils;
import org.cisiondata.utils.endecrypt.SHAUtils;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.cisiondata.utils.regex.RegexUtils;
import org.cisiondata.utils.web.IPUtils;
import org.springframework.stereotype.Service;

@Service("auserService")
public class UserServiceImpl implements IUserService {

	@Resource(name = "auserDAO")
	private UserDAO auserDAO;

	@Resource(name = "authService")
	private IAuthService authService = null;

	@Resource(name = "userService")
	private org.cisiondata.modules.auth.service.IUserService userService;

	@Resource(name = "messageService")
	private IMessageService messageService;
	
	// 通过账号添加个人信息
	@Override
	public String updateUserSetting(String realName,String mobilePhone,
			String newPassword,String verificationCode,HttpServletRequest request) throws BusinessException {
		AUser user = new AUser();
		String account = WebUtils.getCurrentAccout();
		String macAddress = IPUtils.getMACAddress(request);
		if (StringUtils.isBlank(account)
				|| StringUtils.isBlank(realName)
				|| StringUtils.isBlank(mobilePhone)
				|| !RegexUtils.isMoblePhone(mobilePhone)
				|| StringUtils.isBlank(verificationCode)
				|| StringUtils.isBlank(newPassword)) {
			throw new BusinessException(ResultCode.PARAM_ERROR);
		}
		if (!macAddress.matches("([A-Fa-f0-9]{2}[:,-]{1}){5}[A-Fa-f0-9]{2}") ||  StringUtils.isBlank(macAddress)) {
			throw new BusinessException(668,"macAddress错误");
		}
		user = auserDAO.findUserByPhone(mobilePhone);
		if (user != null) {
			throw new BusinessException(669,"电话号码已存在,请更换电话号码");
		}
		judgeValidation(mobilePhone, verificationCode);
		user = auserDAO.findUser(account);
		if (user == null) {
			throw new BusinessException(ResultCode.ACCOUNT_NOT_EXIST);
		}
		user.setRealName(realName);
		user.setMobilePhone(mobilePhone);
		user.setPassword(EndecryptUtils.encryptPassword(newPassword,
				user.getSalt()));
		user.setMacAddress(macAddress);
		auserDAO.updateUser(user);
		
		UserAttribute userAttribute = new UserAttribute();
		userAttribute.setUserId(user.getId());
		userAttribute.setKey("information");
		userAttribute.setValue("false");
		userAttribute.setType("boolean");
		auserDAO.addUserAttribute(userAttribute);
		
		String encrypted = auserDAO.findUserAttribute(user.getId(), "encrypted");
		if ("false".equals(encrypted)) {
			userService.deleteUserCache(account);
			return authService.readUserAuthorizationToken(account);
		}else {
			return "未填写密保问题";
		}
	}

	// 通过账号添加密保
	@Override
	public String updateUserSecurity(String question, String answer)
			throws BusinessException {
		AUser user = new AUser();
		UserAttribute userAttribute = new UserAttribute();
		String account = WebUtils.getCurrentAccout();
		if (StringUtils.isBlank(account) || StringUtils.isBlank(answer)
				|| StringUtils.isBlank(question)) {
			throw new BusinessException(ResultCode.PARAM_ERROR);
		}
		user = auserDAO.findUser(account);
		if (null == user) {
			throw new BusinessException(ResultCode.ACCOUNT_NOT_EXIST);
		}
		
		userAttribute.setUserId(user.getId());
		userAttribute.setKey("question");
		userAttribute.setValue(question);
		userAttribute.setType("string");
		auserDAO.addUserAttribute(userAttribute);
		
		userAttribute.setKey("answer");
		userAttribute.setValue(SHAUtils.SHA1(answer));
		userAttribute.setType("string");
		auserDAO.addUserAttribute(userAttribute);
		
		userAttribute.setKey("encrypted");
		userAttribute.setValue("false");
		userAttribute.setType("boolean");
		auserDAO.addUserAttribute(userAttribute);
		userService.deleteUserCache(account);
		
		String information = auserDAO.findUserAttribute(user.getId(), "information");
		if ("false".equals(information)) {
			userAttribute.setKey("firstLoginFlag");
			userAttribute.setValue("false");
			userAttribute.setType("boolean");
			auserDAO.editUserAttribute(userAttribute);
			userService.deleteUserCache(account);
			return authService.readUserAuthorizationToken(account);
		}else {
			return "未填写个人信息";
		}
	}

	// 查询密保问题
	@Override
	public List<String> findSecurity() throws BusinessException {
		List<Security> security = new ArrayList<Security>();
		List<String> list = new ArrayList<String>();
		security = auserDAO.findSecurity();
		if (security == null) {
			throw new BusinessException(607,"密保问题为空");
		}
		for (int i = 0; i < security.size(); i++) {
			list.add(security.get(i).getSecurityQuestion());
		}
		return list;
	}

	// 获取用户密保问题
	@Override
	public String findSecurityQuestion() throws BusinessException {
		String account = WebUtils.getCurrentAccout();
		String question = auserDAO.findSecurityQuestion(account);
		if (StringUtils.isBlank(question)) {
			throw new BusinessException(ResultCode.ACCOUNT_NOT_EXIST);
		}
		return question;
	}

	// 获取用户密保问题答案
	@Override
	public String findSecurityAnswer(String answer) throws BusinessException {
		if (StringUtils.isBlank(answer)) {
			throw new BusinessException(ResultCode.PARAM_NULL);
		}
		String account = WebUtils.getCurrentAccout();
		String securityAnswer = auserDAO.findSecurityAnswer(account);
		if (StringUtils.isBlank(securityAnswer)) {
			throw new BusinessException(ResultCode.ACCOUNT_NOT_EXIST);
		}
		if (!securityAnswer.equals(SHAUtils.SHA1(answer))) {
			throw new BusinessException(605, "密保问题答案错误");
		}
		return authService.readUserAuthorizationToken(account);
	}

	// 设置新密保问题及答案
	@Override
	public void updateSecurityAnswer(String oldAnswer, String newQuestion,
			String newAnswer) throws BusinessException {
		String answer = null;
		String oldSecurityAnswer = null;
		String newSecurityAnswer = null;
		String account = WebUtils.getCurrentAccout();
		AUser user = new AUser();
		if (StringUtils.isNotBlank(account)
				&& StringUtils.isNotBlank(oldAnswer)
				&& StringUtils.isNotBlank(newAnswer)
				&& StringUtils.isNotBlank(newAnswer)) {
			oldSecurityAnswer = SHAUtils.SHA1(oldAnswer);
			newSecurityAnswer = SHAUtils.SHA1(newAnswer);
		} else {
			throw new BusinessException(ResultCode.KEYWORD_NOT_NULL);
		}
		answer = auserDAO.findSecurityAnswer(account);
		if (StringUtils.isBlank(answer)) {
			throw new BusinessException(ResultCode.ACCOUNT_NOT_EXIST);
		}
		if (!oldSecurityAnswer.equals(answer)) {
			throw new BusinessException(605, "密保问题答案错误");
		}
		user.setAccount(account);
		user.setQuestion(newQuestion);
		user.setAnswer(newSecurityAnswer);
		user.setDeleteFlag(null);
		user.setFirstLoginFlag(null);
		auserDAO.updateUser(user);
		userService.deleteUserCache(account);
	}

	// 通过旧密码设置新密码
	@Override
	public void updatePassword(String oldPassword, String newPassword)
			throws BusinessException {
		AUser user = new AUser();
		String oldEndecryptPassword = null;
		String newEndecryptPassword = null;
		String account = WebUtils.getCurrentAccout();
		user = auserDAO.findUser(account);
		if (user == null) {
			throw new BusinessException(ResultCode.ACCOUNT_NOT_EXIST);
		}
		if (StringUtils.isNotBlank(account)
				&& StringUtils.isNotBlank(oldPassword)
				&& StringUtils.isNotBlank(newPassword)) {
			oldEndecryptPassword = EndecryptUtils.encryptPassword(oldPassword,
					user.getSalt());
			newEndecryptPassword = EndecryptUtils.encryptPassword(newPassword,
					user.getSalt());
		} else {
			throw new BusinessException(ResultCode.KEYWORD_NOT_NULL);
		}
		if (!(user.getPassword()).equals(oldEndecryptPassword)) {
			throw new BusinessException(ResultCode.ACCOUNT_PASSWORD_NOT_MATCH);
		}
		user.setPassword(newEndecryptPassword);
		auserDAO.updateUser(user);
		userService.deleteUserCache(account);
	}

	// 忘记密码，设置新密码
	@Override
	public void updateNewPassword(String newPassword) throws BusinessException {
		String account = WebUtils.getCurrentAccout();
		AUser user = new AUser();
		if (StringUtils.isBlank(newPassword) || StringUtils.isBlank(account)) {
			throw new BusinessException(ResultCode.KEYWORD_NOT_NULL);
		}
		user = auserDAO.findUser(account);
		if (user == null) {
			throw new BusinessException(ResultCode.ACCOUNT_NOT_EXIST);
		}
		user.setPassword(EndecryptUtils.encryptPassword(newPassword, user.getSalt()));
		auserDAO.updateUser(user);
		userService.deleteUserCache(account);
	}

	//验证电话号码并发送验证码
	@Override
	public void validation(String phone) throws BusinessException {
		AUser user = new AUser();
		if (StringUtils.isBlank(phone)) {
			throw new BusinessException(ResultCode.PARAM_NULL);
		}
		if (!RegexUtils.isMoblePhone(phone)) {
			throw new BusinessException(ResultCode.PARAM_FORMAT_ERROR);
		}
		if (RedisClusterUtils.getInstance().get("user:cache:phone:"+ phone) != null) {
			throw new BusinessException(655,"不能连续发送验证码");
		}
		user = auserDAO.findUserByPhone(phone);
		if (user == null) {
			throw new BusinessException(656,"电话号码不匹配");
		}
		String code = verification();
		messageService.sendVerification(user.getMobilePhone(),code);
		RedisClusterUtils.getInstance().set("user:cache:" + user.getAccount() +":"+ user.getMobilePhone(), code,180);
		RedisClusterUtils.getInstance().set("user:cache:phone:"+ phone, phone,60);
	}

	//首次登陆发送验证码
	@Override
	public void sendValidation(String phone) throws BusinessException {
		if (StringUtils.isBlank(phone)) {
			throw new BusinessException(ResultCode.PARAM_NULL);
		}
		if (!RegexUtils.isMoblePhone(phone)) {
			throw new BusinessException(ResultCode.PARAM_FORMAT_ERROR);
		}
		AUser user = new AUser();
		user = auserDAO.findUserByPhone(phone);
		if (user != null) {
			throw new BusinessException(669,"电话号码已存在,请更换电话号码");
		}
			String code = verification();
			if (StringUtils.isBlank(code) || code.length() != 4) {
				throw new BusinessException(ResultCode.PARAM_FORMAT_ERROR);
			}
			if (RedisClusterUtils.getInstance().get("user:cache:phone:"+ phone) != null) {
				throw new BusinessException(655,"不能连续发送验证码");
			}
			messageService.sendVerification(phone,code);
			RedisClusterUtils.getInstance().set("user:cache:" + WebUtils.getCurrentAccout() +":"+ phone, code,300);
			RedisClusterUtils.getInstance().set("user:cache:phone:"+ phone, phone,60);
	}
	
	//验证码判断
	@Override
	public String judgeValidationCode(String phone,String verificationCode)
			throws BusinessException {
		AUser user = new AUser();
		judgeValidation(phone, verificationCode);
		user = auserDAO.findUserByPhone(phone);
		return authService.readUserAuthorizationToken(user.getAccount());
	}
	
		//判断验证码
		private void judgeValidation(String phone,String verificationCode)
				throws BusinessException {
		if (StringUtils.isBlank(phone)) {
			throw new BusinessException(ResultCode.PARAM_NULL);
		}
		if (!RegexUtils.isMoblePhone(phone)) {
			throw new BusinessException(ResultCode.PARAM_FORMAT_ERROR);
		}
		if (StringUtils.isBlank(verificationCode)) {
			throw new BusinessException(ResultCode.PARAM_NULL);
		}
		String account = WebUtils.getCurrentAccout();
		if (StringUtils.isBlank(account)) {
			AUser user = new AUser();
			user = auserDAO.findUserByPhone(phone);
			account = user.getAccount();
		}
		String code = (String) RedisClusterUtils.getInstance().get("user:cache:" + account +":"+ phone);
		if (StringUtils.isBlank(code)) {
			throw new BusinessException(ResultCode.VERIFICATION_CODE_FAILURE);
		}
		if (!code.equals(verificationCode)) {
			throw new BusinessException(ResultCode.VERIFICATION_CODE_FAILURE);
		}
	}
	
	//生成短信验证码
		private String verification() {
			Random random = new Random();
			int num1 = random.nextInt(9);
			int num2 = random.nextInt(9);
			int num3 = random.nextInt(9);
			int num4 = random.nextInt(9);
			String verification = num1 + "" + num2 + "" + num3 + "" + num4 ;
			return verification;
		}

		//修改手机号码
		@Override
		public void updatePhone(String phone,String verificationCode) throws BusinessException {
			judgeValidationCode(phone, verificationCode);
			AUser user = new AUser();
			String account = WebUtils.getCurrentAccout();
			user = auserDAO.findUser(account);
			if (user == null) {
				throw new BusinessException(ResultCode.ACCOUNT_NOT_EXIST);
			}
			String code = (String) RedisClusterUtils.getInstance().get("user:cache:" + WebUtils.getCurrentAccout() +":"+ phone);
			if (StringUtils.isBlank(code)) {
				throw new BusinessException(ResultCode.VERIFICATION_CODE_FAILURE);
			}
			if (!code.equals(verificationCode)) {
				throw new BusinessException(ResultCode.VERIFICATION_CODE_FAILURE);
			}
			user.setMobilePhone(phone);
			auserDAO.updateUser(user);
		}
		
		// 获取用户电话号码
		@Override
		public String findMobilePhone() throws BusinessException {
			AUser user = new AUser();
			String account = WebUtils.getCurrentAccout();
			user = auserDAO.findUser(account);
			if (user == null) {
				throw new BusinessException(ResultCode.ACCOUNT_NOT_EXIST);
			}
			return user.getMobilePhone();
		}

		//获取用户信息
		@Override
		public Map<String, String> findUser() throws BusinessException {
			Map<String, String> map = new HashMap<String, String>();
			String account = WebUtils.getCurrentAccout();
			AUser user = new AUser();
			user = auserDAO.findUser(account);
			if (user ==null) {
				throw new BusinessException(ResultCode.ACCOUNT_NOT_EXIST);
			}
			StringBuilder sb = new StringBuilder();
			String phone = user.getMobilePhone();
				sb.append(phone);
				sb.replace(3, 7, "****");
			map.put("姓名", user.getRealName());
			map.put("账号", user.getAccount());
			map.put("电话号码", sb.toString());
			return map;
		}

}
