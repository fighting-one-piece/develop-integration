package org.cisiondata.modules.user.service;

import java.util.Map;

import org.cisiondata.utils.exception.BusinessException;

public interface APIAUserService {

	/**
	 * 根据条件分页查询
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public Map<String,Object> findAPIAuser(Integer page,Integer pageSize,String identity) throws BusinessException;
	
}
