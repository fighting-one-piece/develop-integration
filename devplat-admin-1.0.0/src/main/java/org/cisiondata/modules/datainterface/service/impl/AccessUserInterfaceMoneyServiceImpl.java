package org.cisiondata.modules.datainterface.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.cisiondata.modules.datainterface.dao.AccessUserInterfaceMoneyDao;
import org.cisiondata.modules.datainterface.entity.AccessInterface;
import org.cisiondata.modules.datainterface.entity.AccessUserInterfaceMoney;
import org.cisiondata.modules.datainterface.service.IAccessUserInterfaceMoneyService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("accessUserInterfaceMoneyService")
public class AccessUserInterfaceMoneyServiceImpl implements
		IAccessUserInterfaceMoneyService {

	@Resource(name = "accessUserInterfaceMoneyDao")
	private AccessUserInterfaceMoneyDao accessUserInterfaceMoneyDao;

	// 查看
	@Override
	public List<AccessUserInterfaceMoney> findAccessUserInterfaceMoney(
			Long userInterfaceId) throws BusinessException {
			List<AccessUserInterfaceMoney> accessList = new ArrayList<AccessUserInterfaceMoney>();
			accessList = accessUserInterfaceMoneyDao.findAccessUserInterfaceMoney(userInterfaceId);
		return accessList;
	}

	// 修改
	@Override
	public void updateAccessUserInterfaceMoney(Long id, Long userInterfaceId,
			int responseCode, Double money) throws BusinessException {
		accessUserInterfaceMoneyDao.updateAccessUserInterfaceMoney(id, userInterfaceId, responseCode, money);
	}

	// 增加校验
	@Override
	public void findAccessUserInterfaceMoneyByID(
			Long userInterfaceId, int responseCode) throws BusinessException {
		List<AccessUserInterfaceMoney> access = new ArrayList<AccessUserInterfaceMoney>();
		access = accessUserInterfaceMoneyDao.findAccessUserInterfaceMoney(userInterfaceId);
		for (int i = 0; i < access.size(); i++) {
			if (responseCode == access.get(i).getResponseCode()) {
				throw new BusinessException("已存在");
			}
		}
	}
	
	// 增加
	@Override
	public void addAccessUserInterfaceMoney(AccessUserInterfaceMoney access) throws BusinessException {
		accessUserInterfaceMoneyDao.addAccessUserInterfaceMoney(access);
	}
	
	//删除
	@Override
	public void deleteAccessUserInterfaceMoney(Long userInterfaceId,
			int responseCode,boolean deleteFlag) throws BusinessException {
		if (deleteFlag == true) {
			deleteFlag = false;
		}else {
			deleteFlag = true;
		}
		accessUserInterfaceMoneyDao.deleteAccessUserInterfaceMoney(userInterfaceId,responseCode,deleteFlag);
	}
	
	//默认增加
	@Override
	public void addAccessUserInterface(Long userInterfaceId,Long interfaceId) throws BusinessException {
		AccessUserInterfaceMoney access = new AccessUserInterfaceMoney();
		AccessInterface moneys = new AccessInterface();
		moneys = accessUserInterfaceMoneyDao.findAccessUserInterfaceByID(interfaceId);
		access.setMoney(moneys.getMoney());
		System.out.println(moneys.getMoney());
		access.setUserInterfaceId(userInterfaceId);
		access.setResponseCode(1);
		accessUserInterfaceMoneyDao.addAccessUserInterfaceMoney(access);
	}
}
