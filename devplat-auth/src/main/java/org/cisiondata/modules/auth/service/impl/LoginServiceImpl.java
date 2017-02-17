package org.cisiondata.modules.auth.service.impl;

import javax.annotation.Resource;

import org.cisiondata.modules.auth.dto.UserDTO;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.auth.service.ILoginService;
import org.cisiondata.modules.auth.service.IUserService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements ILoginService {
	
	@Resource(name = "userService")
	private IUserService userService = null;
	
	public UserDTO readUserLoginInfoByAccountAndPassowrd(String account, String password) throws BusinessException {
		User user = userService.readUserByAccountAndPassword(account, password);
		UserDTO userDTO = new UserDTO();
		return userDTO;
	}

	
	
}
