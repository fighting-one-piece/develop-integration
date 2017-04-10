package org.cisiondata.modules.login.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.auth.service.IAuthService;
import org.cisiondata.modules.auth.service.IUserService;
import org.cisiondata.modules.auth.web.WebUtils;
import org.cisiondata.modules.log.service.IUserLoginLogService;
import org.cisiondata.modules.login.dto.LoginDTO;
import org.cisiondata.modules.login.service.ILoginService;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements ILoginService {
	
	private Logger LOG = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	@Resource(name = "loginLogService")
	private IUserLoginLogService userLoginLogService;
	
	@Resource(name = "userService")
	private IUserService userService;
	
	@Resource(name = "authService")
	private IAuthService authService = null;
	
	public LoginDTO readUserLoginInfoByAccountAndPassowrd(String account, String password) throws BusinessException {
		LOG.info("account: {} password: {}", account, password);
		User user = authService.readUserAuthenticationInfo(account, password);
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setAccount(account);
		loginDTO.setNickname(user.getNickname());
		loginDTO.setAccessToken(user.getAccessToken());
		loginDTO.setFirstLoginFlag(user.getFirstLoginFlag());
		loginDTO.setLastLoginTime(userLoginLogService.readUserLoginLog(account));
		loginDTO.setMacAddress(user.getMacAddress());
		loginDTO.setEncryptedFlag(user.getEncryptedFlag());
		loginDTO.setInformationFlag(user.getInformationFlag());
		return loginDTO;
	}
	
	@Override
	public void doUserLogout() throws BusinessException {
		String account = WebUtils.getCurrentAccout();
		if (StringUtils.isNotBlank(account)) userService.deleteUserCache(account);
		String accessToken = WebUtils.getAccessTokenFromHead();
		RedisClusterUtils.getInstance().delete(accessToken);
	}
	
}
