package org.cisiondata.modules.user.service;

import org.cisiondata.utils.exception.BusinessException;

public interface IMessageService {
	//发送验证码
	public void sendVerification(String phone,String verification) throws BusinessException;
	//发送短信
	public void sendMessage(String phone,String account) throws BusinessException;
}
