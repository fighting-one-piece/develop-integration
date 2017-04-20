package org.cisiondata.modules.log.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.cisiondata.utils.exception.BusinessException;

public interface IUserLoginLogService {
	
	//增加
	public void addUserLoginLog(String account,HttpServletRequest request,int status) throws BusinessException;
	
	//查询用户上一次登录时间
	public String readUserLoginLog(String account) throws BusinessException;
	
	public Map<String, Object> selLoginLogPage(String account,int index,int pageSize);
}
