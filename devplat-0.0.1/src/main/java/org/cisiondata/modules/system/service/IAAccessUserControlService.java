package org.cisiondata.modules.system.service;

import org.cisiondata.modules.system.entity.AAccessUserControl;
import org.cisiondata.utils.exception.BusinessException;

public interface IAAccessUserControlService {

	//查询剩余条数
	public Long selectQueryTimes(String account) throws BusinessException;
	//修改查询剩余条数
	public void updateQueryTimes(AAccessUserControl accessUserControl) throws BusinessException;
	
}
