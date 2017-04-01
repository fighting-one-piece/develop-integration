package org.cisiondata.modules.identity.service;

import org.cisiondata.utils.exception.BusinessException;

public interface IMobileAttributionService {
	
	/**
	 * 根据手机号读取归属地
	 * @param mobile
	 * @return
	 * @throws BusinessException
	 */
	public String readAttributionByMobile(String mobile) throws BusinessException;
	
}
