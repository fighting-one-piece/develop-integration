package org.cisiondata.modules.user.service;

import java.util.Map;

import org.cisiondata.utils.exception.BusinessException;

public interface IUserResourceFieldsService {

	/**
	 * 根据资源ID和用户ID查找字段信息
	 * @param userId
	 * @param resourceId
	 * @return
	 * @throws BusinessException
	 */
	public Map<String,Object> findFieldsByUserIdAndResourceId(Long userId ,Long resourceId, Integer type) throws BusinessException;
	
	/**
	 * 根据资源ID和用户ID修改字段信息
	 * @param userId
	 * @param resourceId
	 * @param fields
	 * @return
	 * @throws BusinessException
	 */
	public void updateFieldsByUserIdAndResourceId(Long userId,Long resourceId,String fields, Integer type) throws BusinessException;
}
