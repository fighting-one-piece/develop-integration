package org.cisiondata.modules.auth.service;

import java.util.Map;
import java.util.Set;

import org.cisiondata.modules.auth.entity.AuthUser;
import org.cisiondata.utils.exception.BusinessException;

public interface IAuthService {

	/**
	 * 根据用户账号读取用户数据
	 * @param account
	 * @return
	 * @throws BusinessException
	 */
	public AuthUser readUserByAccount(String account) throws BusinessException;
	
	/**
	 * 根据accessId读取accessKey
	 * @param accessId
	 * @return
	 * @throws BusinessException
	 */
	public String readUserAccessKeyByAccessId(String accessId) throws BusinessException;
	
	/**
	 * 根据用户账号和密码读取用户数据
	 * @param account
	 * @param password
	 * @return
	 * @throws BusinessException
	 */
	public AuthUser readUserByAccountAndPassword(String account, String password) throws BusinessException;
	
	/**
	 * 根据用户账号和密码读取用户认证信息
	 * @param account
	 * @param password
	 * @return
	 * @throws BusinessException
	 */
	public AuthUser readUserAuthenticationInfo(String account, String password) throws BusinessException;
	
	/**
	 * 根据用户账号和密码读取用户授权信息
	 * @param account
	 * @param password
	 * @return
	 * @throws BusinessException
	 */
	public AuthUser readUserAuthorizationInfo(String account) throws BusinessException;
	
	/**
	 * 根据用户账号和密码读取用户授权Token
	 * @param account
	 * @param password
	 * @return
	 * @throws BusinessException
	 */
	public String readUserAuthorizationToken(String account) throws BusinessException;
	
	/**
	 * 根据用户账号和资源读取用户授权信息
	 * @param account
	 * @param url
	 * @return
	 * @throws BusinessException
	 */
	public boolean readUserAuthorizationInfo(String account, String url) throws BusinessException;
	
	/**
	 * 根据用户ID读取权限标识集合
	 * @param userId
	 * @return
	 * @throws BusinessException
	 */
	public Set<String> readPermissionIdentitiesByUserId(Long userId) throws BusinessException;
	
	/**
	 * 根据用户账号读取权限标识集合
	 * @param account
	 * @return
	 * @throws BusinessException
	 */
	public Set<String> readPermissionIdentitiesByAccount(String account) throws BusinessException;
	
	
	/**
	 * 读取WEB请求URL访问权限
	 * @param requestUrl
	 * @return
	 * @throws BusinessException
	 */
	public boolean readPermissionFromWebRequest(String requestUrl) throws BusinessException;

	/**
	 * 读取第三方请求URL访问权限
	 * @param requestUrl
	 * @param requestParams
	 * @return
	 * @throws BusinessException
	 */
	public boolean readPermissionFromExternalRequest(String requestUrl, Map<String, String[]> requestParams) 
			throws BusinessException;
	
	/**
	 * 判断用户账号是否有权限访问资源
	 * @param account
	 * @param url
	 * @return
	 * @throws BusinessException
	 */
	public boolean judgeUserPermission(String account, String url) throws BusinessException;
	
	/**
	 * 根据资源URL读取标识
	 * @param url
	 * @return
	 * @throws BusinessException
	 */
	public String readResourceIdentityByUrl(String url,Integer type) throws BusinessException;
	
}
