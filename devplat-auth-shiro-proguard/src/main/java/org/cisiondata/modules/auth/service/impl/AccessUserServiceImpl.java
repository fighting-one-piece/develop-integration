package org.cisiondata.modules.auth.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.abstr.service.impl.GenericServiceImpl;
import org.cisiondata.modules.auth.dao.AccessUserControlDAO;
import org.cisiondata.modules.auth.dao.AccessUserDAO;
import org.cisiondata.modules.auth.entity.AccessUser;
import org.cisiondata.modules.auth.entity.AccessUserControl;
import org.cisiondata.modules.auth.service.IAccessUserService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("accessUserService")
public class AccessUserServiceImpl extends GenericServiceImpl<AccessUser, Long> implements IAccessUserService {
	
	@Resource(name = "accessUserDAO")
	private AccessUserDAO accessUserDAO = null;
	
	@Resource(name = "accessUserControlDAO")
	private AccessUserControlDAO accessUserControlDAO = null;

	@Override
	public GenericDAO<AccessUser, Long> obtainDAOInstance() {
		return accessUserDAO;
	}
	
	@Override
	public void updateRemainingCount(String account, long remainingCount, long incOrDec) throws BusinessException {
		if ((remainingCount + incOrDec) < 0) {
			throw new BusinessException("账户剩余查询条数不足");
		}
		AccessUserControl accessUserControl = new AccessUserControl();
		accessUserControl.setAccount(account);
		accessUserControl.setRemainingCount(remainingCount + incOrDec);
		accessUserControlDAO.updateRemainingCount(accessUserControl);
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
	

}
