package org.cisiondata.modules.datainterface.service.impl;

import javax.annotation.Resource;

import org.cisiondata.modules.datainterface.dao.AccessUserControlDao;
import org.cisiondata.modules.datainterface.entity.AccessUserControl;
import org.cisiondata.modules.datainterface.service.IAccessUserControlService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("aaccessUserControlService")
public class AccessUserControlServiceImpl implements IAccessUserControlService {

	@Resource(name = "aaccessUserControlDao")
	private AccessUserControlDao accessUserControlDao = null;
	
	private static final String numRex = "^\\d+$";
	
	@Override
	public int updateAccessUserControlCount(Long changeCount,String type,String account) throws BusinessException {
		if(!changeCount.toString().matches(numRex)){
			throw new BusinessException("修改条数必须为纯数字！");
		}
		//查询是否存在记录
		AccessUserControl accessUserControl = accessUserControlDao.findAccessControlByAccount(account);
		if (accessUserControl == null || accessUserControl.getAccount() == null){//不存在，新增
			AccessUserControl access = new AccessUserControl();
			access.setAccount(account);
			if("1".equals(type)){
				access.setCount(changeCount);
				access.setRemainingCount(changeCount);
			} else {
				access.setCount(0L);
				access.setRemainingCount(0L);
			}
			return accessUserControlDao.addAccessUserControl(access);
		} else {//修改
			AccessUserControl access = new AccessUserControl();
			access.setAccount(account);
			if(accessUserControl.getCount() == null){//没有count字段
				if("1".equals(type)){
					access.setCount(changeCount);
					access.setRemainingCount(changeCount);
				} else {
					access.setCount(0L);
					access.setRemainingCount(0L);
				}
			} else {//有count字段
				if("1".equals(type)){
					access.setCount(changeCount + accessUserControl.getCount());
					access.setRemainingCount(changeCount + accessUserControl.getRemainingCount());
				} else {
					access.setRemainingCount(accessUserControl.getRemainingCount() - changeCount < 0L ? 0L : accessUserControl.getRemainingCount() - changeCount);
					access.setCount(accessUserControl.getRemainingCount() - changeCount < 0L ? accessUserControl.getCount() - accessUserControl.getRemainingCount() : accessUserControl.getCount() - changeCount);
				}
			}
			return accessUserControlDao.updateAccessControlByAccount(access);
		}
	}

	@Override
	public int updateMoney(Double changeCount, String type, String account) throws BusinessException {
		AccessUserControl accessUserControl = accessUserControlDao.findAccessControlByAccount(account);
		AccessUserControl access = new AccessUserControl();
		access.setAccount(account);
		if("1".equals(type)){
			access.setMoney(changeCount + accessUserControl.getMoney());
			access.setRemainingMoney(changeCount + accessUserControl.getRemainingMoney());
		} else {
			access.setRemainingMoney(accessUserControl.getRemainingMoney() - changeCount < 0 ? 0 : accessUserControl.getRemainingMoney() - changeCount);
			access.setMoney(accessUserControl.getRemainingMoney() - changeCount < 0 ? accessUserControl.getMoney() - accessUserControl.getRemainingMoney() : accessUserControl.getMoney() - changeCount);
		}
		return accessUserControlDao.updateMoneyByAccount(access);
	}

}
