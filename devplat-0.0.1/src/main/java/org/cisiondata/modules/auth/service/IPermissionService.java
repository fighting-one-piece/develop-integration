package org.cisiondata.modules.auth.service;

import java.util.List;

import org.cisiondata.modules.abstr.service.IGenericService;
import org.cisiondata.modules.auth.entity.Permission;
import org.cisiondata.utils.exception.BusinessException;

public interface IPermissionService extends IGenericService<Permission, Long> {
	
	/**
	 * 根据标识类型和标识ID获取权限列表
	 * @param principalType
	 * @param principalId
	 * @return
	 * @throws BusinessException
	 */
	public List<Permission> readPermissionsByPrincipalTypeAndPrincipalId(
			Integer principalType, Long principalId) throws BusinessException;

}
