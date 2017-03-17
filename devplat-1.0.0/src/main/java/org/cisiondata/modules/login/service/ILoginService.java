package org.cisiondata.modules.login.service;

import org.cisiondata.modules.login.dto.LoginDTO;
import org.cisiondata.utils.exception.BusinessException;

public interface ILoginService {

	/**
	 * 读取用户登录信息
	 * @param account
	 * @param password
	 * @return
	 * @throws BusinessException
	 */
	public LoginDTO readUserLoginInfoByAccountAndPassowrd(String account, String password) throws BusinessException;
	
	/**
	 * 用户登出
	 * @throws BusinessException
	 */
	public void doUserLogout() throws BusinessException;
	
}
