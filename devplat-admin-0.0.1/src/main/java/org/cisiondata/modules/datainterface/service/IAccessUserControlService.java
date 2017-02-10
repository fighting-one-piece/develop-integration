package org.cisiondata.modules.datainterface.service;

import org.cisiondata.utils.exception.BusinessException;

public interface IAccessUserControlService {

	public int updateAccessUserControlCount(Long changeCount,String type,String account) throws BusinessException;
	
}
