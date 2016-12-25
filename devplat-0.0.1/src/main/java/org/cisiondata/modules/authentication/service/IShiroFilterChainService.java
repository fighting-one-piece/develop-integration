package org.cisiondata.modules.authentication.service;

import org.cisiondata.utils.exception.BusinessException;

public interface IShiroFilterChainService {

	public void initFilterChains() throws BusinessException;
	
}
