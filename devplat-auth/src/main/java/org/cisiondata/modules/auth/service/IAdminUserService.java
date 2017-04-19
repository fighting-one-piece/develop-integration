package org.cisiondata.modules.auth.service;

import org.cisiondata.modules.auth.entity.AAdminUser;
import org.cisiondata.utils.exception.BusinessException;

public interface IAdminUserService {

	/**
	 * 根据用户账号和密码读取用户数据
	 * @param account
	 * @param password
	 * @return
	 * @throws BusinessException
	 */
	public AAdminUser readUserByAccountAndPassword(String account, String password) throws BusinessException;
	
	/**
	 * 根据用户账号读取用户数据
	 * @param account
	 * @return
	 * @throws BusinessException
	 */
	public AAdminUser readUserByAccount(String account) throws BusinessException;
	
}
