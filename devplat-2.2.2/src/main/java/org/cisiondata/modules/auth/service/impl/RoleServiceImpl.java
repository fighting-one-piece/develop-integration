package org.cisiondata.modules.auth.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.abstr.service.impl.GenericServiceImpl;
import org.cisiondata.modules.auth.dao.RoleDAO;
import org.cisiondata.modules.auth.entity.AuthRole;
import org.cisiondata.modules.auth.service.IRoleService;
import org.cisiondata.utils.ds.DataSource;
import org.cisiondata.utils.ds.TargetDataSource;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("authRoleService")
public class RoleServiceImpl extends GenericServiceImpl<AuthRole, Long> implements IRoleService {
	
	@Resource(name = "authRoleDAO")
	private RoleDAO roleDAO = null;

	@Override
	public GenericDAO<AuthRole, Long> obtainDAOInstance() {
		return roleDAO;
	}
	
	@Override
	@TargetDataSource(DataSource.SLAVE1)
	public List<AuthRole> readRolesByUserId(Long userId) throws BusinessException {
		return roleDAO.readDataListByUserId(userId);
	}
	
	@Override
	@TargetDataSource(DataSource.SLAVE1)
	public List<AuthRole> readRolesByGroupId(Long groupId) throws BusinessException {
		return roleDAO.readDataListByGroupId(groupId);
	}
	
	@Override
	@TargetDataSource(DataSource.SLAVE1)
	public Set<String> readRoleIdentitiesByUserId(Long userId) throws BusinessException {
		List<AuthRole> roles = roleDAO.readDataListByUserId(userId);
		Set<String> identities = new HashSet<String>();
		for (int i = 0, len = roles.size(); i < len; i++) {
			identities.add(roles.get(i).getIdentity());
		}
		return identities;
	}
	

}
