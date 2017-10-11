package org.cisiondata.modules.auth.service;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.abstr.service.IGenericService;
import org.cisiondata.modules.auth.entity.AuthPermission;
import org.cisiondata.utils.exception.BusinessException;

public interface IPermissionService extends IGenericService<AuthPermission, Long> {

	/**
	 * 根据标识类型和标识ID获取权限列表
	 * 
	 * @param principalType
	 * @param principalId
	 * @return
	 * @throws BusinessException
	 */
	public List<AuthPermission> readPermissionsByPrincipalTypeAndPrincipalId(Integer principalType, Long principalId)
			throws BusinessException;

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
	 * 通过账号读取用户菜单权限
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Boolean> readUserMenuPermission() throws BusinessException;
}
