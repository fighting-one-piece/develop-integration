package org.cisiondata.modules.identity.service;

import java.util.Map;

import org.cisiondata.utils.exception.BusinessException;

public interface IMobileAddressService {
	/**
	 * 读取手机号归属地
	 * @param mobile
	 * @return
	 * @throws BusinessException
	 */
	public Map<String,Object> readAddressFromMoblie(String mobile) throws BusinessException;
}
