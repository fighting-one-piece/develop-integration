package org.cisiondata.modules.auth.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.auth.Constants;
import org.cisiondata.modules.auth.entity.AAdminUser;
import org.cisiondata.modules.auth.service.IAdminAuthService;
import org.cisiondata.modules.auth.service.IAdminUserService;
import org.cisiondata.modules.auth.web.WebContext;
import org.cisiondata.utils.date.DateFormatter;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.cisiondata.utils.token.TokenUtils;
import org.springframework.stereotype.Service;

@Service("adminAuthService")
public class AdminAuthServiceImpl implements IAdminAuthService {
	
	@Resource(name = "adminuserService")
	private IAdminUserService adminUserService;	

	@Override
	public AAdminUser readAdminUserByAccount(String account) throws BusinessException {
		return adminUserService.readUserByAccount(account);
	}

	@Override
	public AAdminUser readAdminUserByAccountAndPassword(String account, String password) throws BusinessException {
		return adminUserService.readUserByAccountAndPassword(account, password);
	}

	@Override
	public AAdminUser readAdminUserAuthenticationInfo(String account, String password) throws BusinessException {
		AAdminUser adminUser = adminUserService.readUserByAccountAndPassword(account, password);
		if (!adminUser.isValid()) throw new BusinessException(ResultCode.ACCOUNT_EXPIRED_OR_DELETED);
//		String macAddress = IPUtils.getMACAddress(WebContext.get().getRequest());
		String currentDate = DateFormatter.DATE.get().format(new Date());
		String accessToken = TokenUtils.genAdminAuthenticationMD5Token(account, currentDate);
		adminUser.setAccessToken(accessToken);
		WebContext.get().getSession().getManager().setCookieSecure(true);
		WebContext.get().getSession().setAttribute(Constants.SESSION_CURRENT_USER, adminUser);
		WebContext.get().getSession().setAttribute(Constants.SESSION_CURRENT_USER_ACCOUNT, account);
		RedisClusterUtils.getInstance().set(accessToken, WebContext.get().getSession().getId(), 1800);
		return adminUser;
	}


	@Override
	public String readAdminUserAuthenticationToken(String account) throws BusinessException {
		return adminUserService.readUserByAccount(account).getAccessToken();
	}

}
