package org.cisiondata.modules.auth.service;

import java.util.Set;

import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.utils.exception.BusinessException;

public interface IAuthService {

	/**
	 * 根据用户账号读取用户数据
	 * @param account
	 * @return
	 * @throws BusinessException
	 */
	public User readUserByAccount(String account) throws BusinessException;
	
	/**
	 * 根据用户账号和密码读取用户数据
	 * @param account
	 * @param password
	 * @return
	 * @throws BusinessException
	 */
	public User readUserByAccountAndPassword(String account, String password) throws BusinessException;
	
	/**
	 * 根据用户ID读取角色标识集合
	 * @param userId
	 * @return
	 * @throws BusinessException
	 */
	public Set<String> readRoleIdentitiesByUserId(Long userId) throws BusinessException;
	
	/**
	 * 根据用户ID读取权限标识集合
	 * @param userId
	 * @return
	 * @throws BusinessException
	 */
	public Set<String> readPermissionIdentitiesByUserId(Long userId) throws BusinessException;
	
	/**
	 * 根据资源URL读取标识
	 * @param url
	 * @return
	 * @throws BusinessException
	 */
	public String readResourceReadIdentityByUrl(String url) throws BusinessException;

}
