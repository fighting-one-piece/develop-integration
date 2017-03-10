package org.cisiondata.modules.auth.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.abstr.service.impl.GenericServiceImpl;
import org.cisiondata.modules.auth.dao.AccessInterfaceDAO;
import org.cisiondata.modules.auth.dao.AccessUserControlDAO;
import org.cisiondata.modules.auth.dao.AccessUserDAO;
import org.cisiondata.modules.auth.dao.AccessUserInterfaceDAO;
import org.cisiondata.modules.auth.entity.AccessInterface;
import org.cisiondata.modules.auth.entity.AccessUser;
import org.cisiondata.modules.auth.entity.AccessUserControl;
import org.cisiondata.modules.auth.entity.AccessUserInterface;
import org.cisiondata.modules.auth.service.IAccessUserService;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.springframework.stereotype.Service;

@Service("accessUserService")
public class AccessUserServiceImpl extends GenericServiceImpl<AccessUser, Long> implements IAccessUserService {
	
	@Resource(name = "accessUserDAO")
	private AccessUserDAO accessUserDAO = null;
	
	@Resource(name = "accessInterfaceDAO")
	private AccessInterfaceDAO accessInterfaceDAO = null;
	
	@Resource(name = "accessUserControlDAO")
	private AccessUserControlDAO accessUserControlDAO = null;
	
	@Resource(name = "accessUserInterfaceDAO")
	private AccessUserInterfaceDAO accessUserInterfaceDAO = null;
	
	@Override
	public GenericDAO<AccessUser, Long> obtainDAOInstance() {
		return accessUserDAO;
	}
	
	@Override
	public void updateRemaining(String account, long remainingCount, long incOrDecCount, 
			double remainingMoney, double incOrDecMoney) throws BusinessException {
		if ((remainingCount + incOrDecCount) < 0) {
			throw new BusinessException("账户剩余查询条数不足");
		}
		if ((remainingMoney + incOrDecMoney) < 0) {
			throw new BusinessException("账户剩余查询金额不足");
		}
		AccessUserControl accessUserControl = new AccessUserControl();
		accessUserControl.setAccount(account);
		accessUserControl.setRemainingCount(remainingCount + incOrDecCount);
		accessUserControl.setRemainingMoney(remainingMoney + incOrDecMoney);
		accessUserControlDAO.update(accessUserControl);
	}
	
	@Override
	public void updateRemainingCount(String account, long remainingCount, long incOrDec) 
			throws BusinessException {
		if ((remainingCount + incOrDec) < 0) {
			throw new BusinessException("账户剩余查询条数不足");
		}
		AccessUserControl accessUserControl = new AccessUserControl();
		accessUserControl.setAccount(account);
		accessUserControl.setRemainingCount(remainingCount + incOrDec);
		accessUserControlDAO.updateRemainingCount(accessUserControl);
	}
	
	@Override
	public void updateRemainingMoney(String account, double remainingMoney, double incOrDec) 
			throws BusinessException {
		AccessUserControl accessUserControl = new AccessUserControl();
		accessUserControl.setAccount(account);
		accessUserControl.setRemainingMoney(remainingMoney + incOrDec);
		accessUserControlDAO.updateRemainingMoney(accessUserControl);
	}
	
	@Override
	public void updateRemainingMoney(String account, double incOrDec) throws BusinessException {
		AccessUserControl accessUserControl = new AccessUserControl();
		accessUserControl.setAccount(account);
		accessUserControl.setRemainingMoney(incOrDec);
		accessUserControlDAO.updateRemainingMoneyIncOrDec(accessUserControl);
	}
	
	@Override
	public String readAccessKeyByAccessId(String accessId) throws BusinessException {
		if (StringUtils.isBlank(accessId)) throw new BusinessException("accessId不能为空");
		Query query = new Query();
		query.addCondition("accessId", accessId);
		query.addCondition("deleteFlag", false);
		AccessUser accessUser = accessUserDAO.readDataByCondition(query);
		if (null == accessUser) throw new BusinessException("未找到对应accessKey或accessKey无效");
		return accessUser.getAccessKey();
	}
	
	@Override
	public AccessUserControl readAccessUserControlByAccount(String account) throws BusinessException {
		if (StringUtils.isBlank(account)) throw new BusinessException("账号不能为空");
		Query query = new Query();
		query.addCondition("account", account);
		query.addCondition("deleteFlag", false);
		AccessUserControl accessUserControl = accessUserControlDAO.readDataByCondition(query);
		if (null == accessUserControl) throw new BusinessException("账号未初始化或账号无效");
		return accessUserControl;
	}
	
	@Override
	public AccessInterface readAccessInterfaceByUrl(String url) throws BusinessException {
		if (StringUtils.isBlank(url)) throw new BusinessException("URL不能为空");
		String interfaceCacheKey = genInterfaceUrlCacheKey(url);
		Object interfaceCacheObject = RedisClusterUtils.getInstance().get(interfaceCacheKey);
		if (null != interfaceCacheObject) return (AccessInterface) interfaceCacheObject;
		Query query = new Query();
		query.addCondition("url", url);
		query.addCondition("deleteFlag", false);
		AccessInterface accessInterface = accessInterfaceDAO.readDataByCondition(query);
		if (null != accessInterface) RedisClusterUtils.getInstance().set(interfaceCacheKey, accessInterface);
		return accessInterface;
	}
	
	@Override
	public AccessInterface readAccessInterfaceByIdentity(String identity) throws BusinessException {
		if (StringUtils.isBlank(identity)) throw new BusinessException("标识不能为空");
		String interfaceCacheKey = genInterfaceIdentityCacheKey(identity);
		Object interfaceCacheObject = RedisClusterUtils.getInstance().get(interfaceCacheKey);
		if (null != interfaceCacheObject) return (AccessInterface) interfaceCacheObject;
		Query query = new Query();
		query.addCondition("identity", identity);
		query.addCondition("deleteFlag", false);
		AccessInterface accessInterface = accessInterfaceDAO.readDataByCondition(query);
		if (null != accessInterface) RedisClusterUtils.getInstance().set(interfaceCacheKey, accessInterface);
		return accessInterface;
	}
	
	@Override
	public AccessUserInterface readAccessUserInterfaceByAccountAndInterfaceId(String account, 
			Long interfaceId) throws BusinessException {
		if (StringUtils.isBlank(account) || null == interfaceId) {
			throw new BusinessException("账号或接口不能为空");
		}
		AccessUserInterface userInterface = new AccessUserInterface();
		userInterface.setAccount(account);
		userInterface.setInterfaceId(interfaceId);
		userInterface.setChargeFlag(true);
		userInterface.setDeleteFlag(false);
		return accessUserInterfaceDAO.readDataByUserInterface(userInterface);
	}
	
	private String genInterfaceIdentityCacheKey(String identity) {
		return "interface:identity:" + identity;
	}
	
	private String genInterfaceUrlCacheKey(String url) {
		return "interface:url:" + url;
	}

}
