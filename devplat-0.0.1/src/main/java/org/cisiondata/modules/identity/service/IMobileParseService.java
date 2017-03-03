package org.cisiondata.modules.identity.service;

import java.util.Map;

import org.cisiondata.utils.exception.BusinessException;

public interface IMobileParseService {

	/**
	 * 从imcaller解析手机号信息
	 * @param mobile
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> readDataFromImcaller(String mobile) throws BusinessException;
	
}
