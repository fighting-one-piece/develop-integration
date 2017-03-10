package org.cisiondata.modules.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;





import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.user.dao.UserDAO;
import org.cisiondata.modules.user.entity.Security;
import org.cisiondata.modules.user.entity.User;
import org.cisiondata.modules.user.service.IUserService;
import org.cisiondata.utils.endecrypt.EndecryptUtils;
import org.cisiondata.utils.endecrypt.SHAUtils;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("auserService")
public class UserServiceImpl implements IUserService{
	
	@Resource(name="auserDAO")
	private UserDAO auserDAO;
	
	//通过账号添加个人信息
	@Override
	public void updateUserSetting(String account, String realName, String mobilePhone,String newPassword) throws BusinessException {
		User user = new User();
		if (! StringUtils.isNotBlank(account) || ! StringUtils.isNotBlank(realName) || ! StringUtils.isNotBlank(mobilePhone) || ! StringUtils.isNotBlank(newPassword)) {
			throw new BusinessException(ResultCode.PARAM_ERROR);
		}
		try {
			auserDAO.findUser(account);
		} catch (Exception e) {
			throw new BusinessException(ResultCode.DATABASE_OPERATION_FAIL);
		}
		user.setRealName(realName);
		user.setMobilePhone(mobilePhone);
		user.setPassword(EndecryptUtils.encryptPassword(newPassword, user.getSalt()));
		try {
			auserDAO.updateUser(user);
		} catch (Exception e) {
			throw new BusinessException(ResultCode.DATABASE_OPERATION_FAIL);
		}
	}
	
	//通过账号添加密保
	@Override
	public void updateUserSecurity(String account,String question,String answer) throws BusinessException {
		User user = new User();
		if (! StringUtils.isNotBlank(account) || ! StringUtils.isNotBlank(answer) || ! StringUtils.isNotBlank(question)) {
			throw new BusinessException(ResultCode.PARAM_ERROR);
		}
		user.setAccount(account);
		user.setAnswer(SHAUtils.SHA1(answer));
		user.setQuestion(question);
		user.setDeleteFlag(null);
		user.setFirstLoginFlag(false);
		try {
			auserDAO.updateUser(user);
		} catch (Exception e) {
			throw new BusinessException(ResultCode.DATABASE_OPERATION_FAIL);
		}
	}
	
	//查询密保问题
	@Override
	public List<String> findSecurity() throws BusinessException {
		List<Security> security = new ArrayList<Security>();
		List<String> list = new ArrayList<String>();
		try {
			security = auserDAO.findSecurity();
			for (int i = 0; i < security.size(); i++) {
				list.add(security.get(i).getSecurityQuestion());
			}
		} catch (Exception e) {
			throw new BusinessException(ResultCode.DATABASE_OPERATION_FAIL);
		}
		return list;
	}

	//获取用户密保问题
	@Override
	public String findSecurityQuestion(String account) throws BusinessException {
		String question = null;
		try {
			question = auserDAO.findSecurityQuestion(account);
		} catch (Exception e) {
			throw new BusinessException(ResultCode.DATABASE_OPERATION_FAIL);
		}
		return question;
	}

	//获取用户密保问题答案
	@Override
	public void findSecurityAnswer(String account,String answer) throws BusinessException {
		String securityAnswer = null;
		try {
			securityAnswer = auserDAO.findSecurityAnswer(account);
		} catch (Exception e) {
			throw new BusinessException(ResultCode.DATABASE_OPERATION_FAIL);
		}
		if (! StringUtils.isNotBlank(securityAnswer)) {
			throw new BusinessException(ResultCode.PARAM_NULL);
		}else if(! StringUtils.isNotBlank(answer)){
			throw new BusinessException(ResultCode.PARAM_NULL);
		}else if(! securityAnswer.equals(SHAUtils.SHA1(answer))){
			throw new BusinessException(605,"密保问题答案错误");
		}
	}

	//设置新密保问题及答案
	@Override
	public void updateSecurityAnswer(String account,String oldAnswer, String newQuestion, String newAnswer)
			throws BusinessException {
		String answer = null;
		String oldSecurityAnswer = null;
		String newSecurityAnswer = null;
		User user = new User();
		if (StringUtils.isNotBlank(account) && StringUtils.isNotBlank(oldAnswer) && StringUtils.isNotBlank(newAnswer) && StringUtils.isNotBlank(newAnswer)) {
			oldSecurityAnswer = SHAUtils.SHA1(oldAnswer);
			newSecurityAnswer = SHAUtils.SHA1(newAnswer);
		}else {
			throw new BusinessException(ResultCode.KEYWORD_NOT_NULL);	
		}
		try {
			answer = auserDAO.findSecurityAnswer(account);
		} catch (Exception e) {
			throw new BusinessException(ResultCode.DATABASE_OPERATION_FAIL);
		}
		if (! oldSecurityAnswer.equals(answer)) {
			throw new BusinessException(605,"密保问题答案错误");
		}
		user.setAccount(account);
		user.setQuestion(newQuestion);
		user.setAnswer(newSecurityAnswer);
		user.setDeleteFlag(null);
		user.setFirstLoginFlag(null);
		try {
			auserDAO.updateUser(user);
		} catch (Exception e) {
			throw new BusinessException(ResultCode.DATABASE_OPERATION_FAIL);
		}
	}

	//通过旧密码设置新密码
	@Override
	public void updatePassword(String account, String oldPassword,
			String newPassword) throws BusinessException {
		User user = new User();
		String oldEndecryptPassword =null;
		String newEndecryptPassword = null;
		try {
			user = auserDAO.findUser(account);
		} catch (Exception e) {
			throw new BusinessException(ResultCode.DATABASE_OPERATION_FAIL);
		}
		if (StringUtils.isNotBlank(account) && StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)) {
			oldEndecryptPassword = EndecryptUtils.encryptPassword(oldPassword, user.getSalt());
			newEndecryptPassword = EndecryptUtils.encryptPassword(newPassword, user.getSalt());
		}else {
			throw new BusinessException(ResultCode.KEYWORD_NOT_NULL);
		}
		if (! (user.getPassword()).equals(oldEndecryptPassword)) {
			throw new BusinessException(ResultCode.ACCOUNT_PASSWORD_NOT_MATCH);
		}
		user.setPassword(newEndecryptPassword);
		try {
			auserDAO.updateUser(user);
		} catch (Exception e) {
			throw new BusinessException(ResultCode.DATABASE_OPERATION_FAIL);
		}
	}

}
