package org.cisiondata.modules.datainterface.service;

import org.cisiondata.modules.abstr.service.IGenericService;
import org.cisiondata.modules.datainterface.entity.AccessUser;
import org.cisiondata.utils.exception.BusinessException;

public interface IAccessUserService extends IGenericService<AccessUser, Long> {

	/**
	 * 根据accessId读取accessKey
	 * @param accessId
	 * @return
	 * @throws BusinessException
	 */
	public String readAccessKeyByAccessId(String accessId) throws BusinessException;
	
}
