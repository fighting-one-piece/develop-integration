package org.cisiondata.modules.auth.service.impl;

import javax.annotation.Resource;

import org.cisiondata.modules.auth.Constants.SessionName;
import org.cisiondata.modules.auth.dto.UserDTO;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.auth.service.ILoginService;
import org.cisiondata.modules.auth.service.IUserService;
import org.cisiondata.modules.auth.web.WebContext;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.cisiondata.utils.token.TokenUtils;
import org.cisiondata.utils.web.IPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements ILoginService {
	
	private Logger LOG = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	@Resource(name = "userService")
	private IUserService userService = null;
	
	public UserDTO readUserLoginInfoByAccountAndPassowrd(String account, String password) throws BusinessException {
		User user = userService.readUserByAccountAndPassword(account, password);
		String macAddress = IPUtils.getMACAddress(WebContext.get().getRequest());
		String accessToken = TokenUtils.genMD5Token(account, user.getPassword(), macAddress);
		LOG.info("account {} login session id : {}", account, WebContext.get().getSession().getId());
		RedisClusterUtils.getInstance().set(accessToken, WebContext.get().getSession().getId(), 1800);
		WebContext.get().getSession().getManager().setCookieSecure(true);
		WebContext.get().getSession().setAttribute(SessionName.CURRENT_USER_ACCOUNT, account);
		WebContext.get().getSession().setAttribute(SessionName.CURRENT_USER, user);
		UserDTO userDTO = new UserDTO();
		userDTO.setAccount(account);
		userDTO.setNickname(user.getNickname());
		userDTO.setAccessToken(accessToken);
		return userDTO;
	}

	
	
}
