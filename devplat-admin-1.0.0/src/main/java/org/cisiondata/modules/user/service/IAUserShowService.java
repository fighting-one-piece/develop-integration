package org.cisiondata.modules.user.service;

import java.util.Map;

import org.cisiondata.utils.exception.BusinessException;

public interface IAUserShowService {

	/**
	 * 查询金额
	 * @param page
	 * @param pageSize
	 * @param identity
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> findAUsermoney(Integer page,Integer pageSize,String identity)throws BusinessException;
	
}
