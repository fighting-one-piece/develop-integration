package org.cisiondata.modules.user.service;

import java.util.Map;

import org.cisiondata.utils.exception.BusinessException;

public interface AUserAttributeService {

	/**
	 * 根据user_id查询
	 * @param user_id
	 * @return
	 * @throws BusinessException
	 */
	public Map<String,Object> findAUserAttribte(Long userId) throws BusinessException;
	
	/**
	 * 根据user_id修改
	 * @param user_id
	 * @return
	 * @throws BusinessException
	 */
	public void updateUserAttribte(Double changeCount, Long userId) throws BusinessException;
	/**
	 * 查询api金额
	 * @param begin
	 * @param pageSize
	 * @return
	 * @throws BusinessException
	 */
	public Map<String,Object> findAPIAUserMoney(Integer begin,Integer pageSize)throws BusinessException;
	/**
	 * 修改用户余额
	 * @param userId
	 * @param changMoney
	 * @return
	 * @throws BusinessException
	 */
	public void updateRemaining_money(Long userId,String changMoney)throws BusinessException;
}
