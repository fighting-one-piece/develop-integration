package org.cisiondata.modules.identity.service;

import org.cisiondata.utils.exception.BusinessException;

public interface IMobileNameService {

	/**
	 * 读取手机号真实姓名
	 * @param mobile
	 * @return
	 * @throws BusinessException
	 */
	public String readNameFromMobile(String mobile) throws BusinessException;
	
}
