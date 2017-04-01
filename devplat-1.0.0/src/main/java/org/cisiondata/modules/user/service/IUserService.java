package org.cisiondata.modules.user.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.cisiondata.utils.exception.BusinessException;


public interface IUserService {
	
	//通过账号添加个人信息
	public void updateUserSetting(String realName,String mobilePhone,
			String newPassword,String verificationCode,HttpServletRequest request)throws BusinessException;
	//通过账号添加密保
	public String updateUserSecurity(String question,String answer)throws BusinessException;
	//查询密保问题
	public List<String> findSecurity() throws BusinessException;
	//获取用户密保问题
	public String findSecurityQuestion() throws BusinessException;
	//获取用户密保问题答案
	public String findSecurityAnswer(String answer) throws BusinessException;
	//设置新密保问题及答案
	public void updateSecurityAnswer(String oldAnswer,String newQuestion,String newAnswer) throws BusinessException;
	//通过旧密码设置新密码
	public void updatePassword(String oldPassword,String newPassword) throws BusinessException;
	//忘记密码，设置新密码
	public void updateNewPassword(String newPassword) throws BusinessException;
	//验证电话号码并发送验证码
	public void validation(String phone) throws BusinessException;
	//首次登陆发送验证码
	public void sendValidation(String phone) throws BusinessException;
	//验证码判断
	public String judgeValidationCode(String phone,String verificationCode) throws BusinessException;
	//修改手机号码
	public void updatePhone(String phone,String verificationCode) throws BusinessException;
	//获取用户电话号码
	public String findMobilePhone() throws BusinessException;
	//获取用户信息
	public Map<String, String> findUser() throws BusinessException;
}
