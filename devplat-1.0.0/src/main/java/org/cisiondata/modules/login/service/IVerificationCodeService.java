package org.cisiondata.modules.login.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cisiondata.utils.exception.BusinessException;

public interface IVerificationCodeService {
	
	//获取验证码
	public void readVerificationCode(String time, HttpServletRequest request, 
			HttpServletResponse response) throws IOException;
	
	//判断验证码
	public Boolean validateVerificationCode(String uuid, String verificationCode) throws BusinessException;
}
