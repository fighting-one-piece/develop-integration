package org.cisiondata.modules.authentication.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.abstr.service.impl.GenericServiceImpl;
import org.cisiondata.modules.authentication.dao.PermissionDAO;
import org.cisiondata.modules.authentication.entity.Permission;
import org.cisiondata.modules.authentication.service.IPermissionService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("permissionService")
public class PermissionServiceImpl extends GenericServiceImpl<Permission, Long> implements IPermissionService {

	@Resource(name = "permissionDAO")
	private PermissionDAO permissionDAO = null;
	
	@Override
	public GenericDAO<Permission, Long> obtainDAOInstance() {
		return permissionDAO;
	}

	@Override
	public List<Permission> readPermissionsByPrincipalTypeAndPrincipalId(
			Integer principalType, Long principalId) throws BusinessException {
		Query query = new Query();
		query.addCondition("principalType", principalType);
		query.addCondition("principalId", principalId);
		return permissionDAO.readDataListByCondition(query);
	}
	
}
