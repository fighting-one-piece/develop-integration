package org.cisiondata.modules.auth.service;

import org.cisiondata.modules.auth.entity.AAdminUser;
import org.cisiondata.utils.exception.BusinessException;

public interface IAdminAuthService {

	/**
	 * 根据用户账号读取用户数据
	 * @param account
	 * @return
	 * @throws BusinessException
	 */
	public AAdminUser readAdminUserByAccount(String account) throws BusinessException;
	
	/**
	 * 根据用户账号和密码读取用户数据
	 * @param account
	 * @param password
	 * @return
	 * @throws BusinessException
	 */
	public AAdminUser readAdminUserByAccountAndPassword(String account, String password) throws BusinessException;
	
	/**
	 * 根据用户账号和密码读取用户认证信息
	 * @param account
	 * @param password
	 * @return
	 * @throws BusinessException
	 */
	public AAdminUser readAdminUserAuthenticationInfo(String account, String password) throws BusinessException;
	
	/**
	 * 根据用户账号和密码读取用户认证Token
	 * @param account
	 * @param password
	 * @return
	 * @throws BusinessException
	 */
	public String readAdminUserAuthenticationToken(String account) throws BusinessException;
}
