package org.cisiondata.modules.datainterface.dao;


import java.util.List;

import org.cisiondata.modules.datainterface.entity.AccessUserInterfaceMoney;
import org.springframework.stereotype.Repository;

@Repository("accessUserInterfaceMoneyDao")
public interface AccessUserInterfaceMoneyDao {
	//查看
	public List<AccessUserInterfaceMoney> findAccessUserInterfaceMoney(Long userInterfaceId);
	//查看价格
	public org.cisiondata.modules.datainterface.entity.AccessInterface findAccessUserInterfaceByID(Long interfaceId);
	//修改
	public void updateAccessUserInterfaceMoney(Long id, Long userInterfaceId,
			int responseCode, Double money);
	//增加
	public void addAccessUserInterfaceMoney(AccessUserInterfaceMoney access);
	//删除标示
	public void deleteAccessUserInterfaceMoney(Long userInterfaceId,int responseCode,boolean deleteFlag);
}
