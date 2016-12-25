package org.cisiondata.modules.authentication.service;

import java.util.List;
import java.util.Set;

import org.cisiondata.modules.abstr.service.IGenericService;
import org.cisiondata.modules.authentication.entity.Role;
import org.cisiondata.utils.exception.BusinessException;

public interface IRoleService extends IGenericService<Role, Long> {
	
	/**
	 * 根据用户ID读取角色集合
	 * @param userId
	 * @return
	 * @throws BusinessException
	 */
	public List<Role> readRolesByUserId(Long userId) throws BusinessException;
	
	/**
	 * 根据组ID读取角色集合
	 * @param groupId
	 * @return
	 * @throws BusinessException
	 */
	public List<Role> readRolesByGroupId(Long groupId) throws BusinessException;

	/**
	 * 根据用户ID读取角色标识集合
	 * @param userId
	 * @return
	 * @throws BusinessException
	 */
	public Set<String> readRoleIdentitiesByUserId(Long userId) throws BusinessException;
	
}
