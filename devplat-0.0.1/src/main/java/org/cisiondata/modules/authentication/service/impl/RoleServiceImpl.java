package org.cisiondata.modules.authentication.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.abstr.service.impl.GenericServiceImpl;
import org.cisiondata.modules.authentication.dao.RoleDAO;
import org.cisiondata.modules.authentication.entity.Role;
import org.cisiondata.modules.authentication.service.IRoleService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl extends GenericServiceImpl<Role, Long> implements IRoleService {
	
	@Resource(name = "roleDAO")
	private RoleDAO roleDAO = null;

	@Override
	public GenericDAO<Role, Long> obtainDAOInstance() {
		return roleDAO;
	}
	
	@Override
	public List<Role> readRolesByUserId(Long userId) throws BusinessException {
		return roleDAO.readDataListByUserId(userId);
	}
	
	@Override
	public List<Role> readRolesByGroupId(Long groupId) throws BusinessException {
		return roleDAO.readDataListByGroupId(groupId);
	}
	
	@Override
	public Set<String> readRoleIdentitiesByUserId(Long userId) throws BusinessException {
		List<Role> roles = roleDAO.readDataListByUserId(userId);
		Set<String> identities = new HashSet<String>();
		for (int i = 0, len = roles.size(); i < len; i++) {
			identities.add(roles.get(i).getIdentity());
		}
		return identities;
	}
	

}
