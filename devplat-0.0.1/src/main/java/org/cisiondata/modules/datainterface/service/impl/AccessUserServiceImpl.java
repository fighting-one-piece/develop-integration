package org.cisiondata.modules.datainterface.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.abstr.service.impl.GenericServiceImpl;
import org.cisiondata.modules.datainterface.dao.AccessUserDAO;
import org.cisiondata.modules.datainterface.entity.AccessUser;
import org.cisiondata.modules.datainterface.service.IAccessUserService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("accessUserService")
public class AccessUserServiceImpl extends GenericServiceImpl<AccessUser, Long> implements IAccessUserService {
	
	@Resource(name = "accessUserDAO")
	private AccessUserDAO accessUserDAO = null;

	@Override
	public GenericDAO<AccessUser, Long> obtainDAOInstance() {
		return accessUserDAO;
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
	

}
