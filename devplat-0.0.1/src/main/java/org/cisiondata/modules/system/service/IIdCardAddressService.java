package org.cisiondata.modules.system.service;

import org.cisiondata.utils.exception.BusinessException;

public interface IIdCardAddressService {

	/**
	 * 根据身份证读取归属地
	 * @param idCard
	 * @return
	 * @throws BusinessException
	 */
	public String readAttributionByIdCard(String idCard) throws BusinessException;
	
}
