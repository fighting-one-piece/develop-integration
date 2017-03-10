package org.cisiondata.modules.system.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.cisiondata.utils.exception.BusinessException;

public interface ISecCodeService {
	//获取验证码
	public void getCodeVali(String time, HttpServletRequest request, HttpServletResponse response) throws IOException;
	//判断验证码
	public Boolean secCode(HttpSession session,String code) throws BusinessException;
}
