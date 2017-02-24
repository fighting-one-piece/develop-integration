package org.cisiondata.modules.datainterface.service;

import java.util.List;

import org.cisiondata.modules.datainterface.entity.AccessUserInterfaceMoney;
import org.cisiondata.utils.exception.BusinessException;

public interface IAccessUserInterfaceMoneyService {
	//查看
	public List<AccessUserInterfaceMoney> findAccessUserInterfaceMoney(Long userInterfaceId)  throws BusinessException;
	//修改
	public void updateAccessUserInterfaceMoney(Long id,Long userInterfaceId,int responseCode,Double money)  throws BusinessException;
	//增加
	public void addAccessUserInterfaceMoney(AccessUserInterfaceMoney access)  throws BusinessException;
	//删除标示
	public void deleteAccessUserInterfaceMoney(Long userInterfaceId,int responseCode,boolean deleteFlag)  throws BusinessException;
	
}
