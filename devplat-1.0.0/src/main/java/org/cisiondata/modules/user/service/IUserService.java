package org.cisiondata.modules.user.service;

import java.util.List;

import org.cisiondata.utils.exception.BusinessException;


public interface IUserService {
	//通过账号添加个人信息
	public void updateUserSetting(String account,String realName,String mobilePhone,String newPassword)throws BusinessException;
	//通过账号添加密保
	public void updateUserSecurity(String account,String question,String answer)throws BusinessException;
	//查询密保问题
	public List<String> findSecurity() throws BusinessException;
	//获取用户密保问题
	public String findSecurityQuestion(String account) throws BusinessException;
	//获取用户密保问题答案
	public void findSecurityAnswer(String account,String answer) throws BusinessException;
	//设置新密保问题及答案
	public void updateSecurityAnswer(String account,String oldAnswer,String newQuestion,String newAnswer) throws BusinessException;
	//通过旧密码设置新密码
	public void updatePassword(String account,String oldPassword,String newPassword) throws BusinessException;
}
