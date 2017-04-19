package org.cisiondata.modules.user.service;

import java.util.Map;

import org.cisiondata.utils.exception.BusinessException;

public interface IUserResourceChargingService {

	/**
	 * 根据用户ID和资源ID查询收费信息
	 * @param userId
	 * @param resourceId
	 * @return
	 * @throws BusinessException
	 */
	public Map<String,Object> findChargingsByUserIdAndResourceId(Long userId, Long resourceId ,Integer type) throws BusinessException;
	
	/**
	 * 根据资源ID和用户ID修改收费信息
	 * @param userId
	 * @param resourceId
	 * @param chargings
	 * @throws BusinessException
	 */
	public void updateChargingsByUserIdAndResourceId(Long userId, Long resourceId, String chargings,Integer type) throws BusinessException;
	
}
