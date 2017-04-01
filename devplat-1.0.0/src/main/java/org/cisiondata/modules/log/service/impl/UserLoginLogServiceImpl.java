package org.cisiondata.modules.log.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.auth.web.WebUtils;
import org.cisiondata.modules.log.dao.UserLoginLogDAO;
import org.cisiondata.modules.log.entity.UserLoginLog;
import org.cisiondata.modules.log.service.IUserLoginLogService;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.web.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("loginLogService")
public class UserLoginLogServiceImpl implements IUserLoginLogService{
	
	@Autowired
	private UserLoginLogDAO logDAO = null;
	
	//新增
	public void addUserLoginLog(String account,HttpServletRequest request,int status) throws BusinessException {
		UserLoginLog log = new UserLoginLog();
		//获取当前IP
		String ip = IPUtils.getIPAddress(request);
		if(StringUtils.isBlank(account)){
			account = WebUtils.getCurrentAccout();
		}
		if(!ip.equals("0:0:0:0:0:0:0:1") && StringUtils.isNotBlank(account)){
			log.setAccount(account);
			log.setIp(ip);
			log.setCurrentTime(new Date());
			log.setStatus(status);
			logDAO.addLoginLog(log);
		}
	}
	
	//查询用户上一次登录时间
	public String readUserLoginLog(String account) throws BusinessException {
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		UserLoginLog userLoginLog = logDAO.selLoginLog(account);
		if (null == userLoginLog) return dateFormater.format(new Date());
		return dateFormater.format(userLoginLog.getCurrentTime());
	}

}
