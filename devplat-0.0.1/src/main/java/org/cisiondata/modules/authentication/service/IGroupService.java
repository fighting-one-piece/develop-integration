package org.cisiondata.modules.authentication.service;

import java.util.List;
import java.util.Set;

import org.cisiondata.modules.abstr.service.IGenericService;
import org.cisiondata.modules.authentication.entity.Group;
import org.cisiondata.utils.exception.BusinessException;

public interface IGroupService extends IGenericService<Group, Long> {
	
	/**
	 * 根据用户ID读取组集合
	 * @param userId
	 * @return
	 * @throws BusinessException
	 */
	public List<Group> readGroupsByUserId(Long userId) throws BusinessException;

	/**
	 * 根据用户ID读取组标识集合
	 * @param userId
	 * @return
	 * @throws BusinessException
	 */
	public Set<String> readGroupIdentitiesByUserId(Long userId) throws BusinessException;
	
}
