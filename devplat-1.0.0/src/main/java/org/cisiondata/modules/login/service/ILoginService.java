package org.cisiondata.modules.login.service;

import org.cisiondata.modules.auth.dto.UserDTO;
import org.cisiondata.utils.exception.BusinessException;

public interface ILoginService {

	/**
	 * 读取用户登录信息
	 * @param account
	 * @param password
	 * @return
	 * @throws BusinessException
	 */
	public UserDTO readUserLoginInfoByAccountAndPassowrd(String account, String password) throws BusinessException;
	
}
