package org.cisiondata.modules.auth.service;

import org.cisiondata.utils.exception.BusinessException;

public interface IShiroFilterChainService {

	public void initFilterChains() throws BusinessException;
	
}
