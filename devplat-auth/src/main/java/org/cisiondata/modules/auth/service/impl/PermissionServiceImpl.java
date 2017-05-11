package org.cisiondata.modules.auth.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.abstr.service.impl.GenericServiceImpl;
import org.cisiondata.modules.auth.dao.PermissionDAO;
import org.cisiondata.modules.auth.dao.ResourceDAO;
import org.cisiondata.modules.auth.dao.RoleDAO;
import org.cisiondata.modules.auth.dao.UserResourceDAO;
import org.cisiondata.modules.auth.dao.UserRoleDAO;
import org.cisiondata.modules.auth.entity.BUserRole;
import org.cisiondata.modules.auth.entity.Permission;
import org.cisiondata.modules.auth.entity.Role;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.auth.entity.UserResource;
import org.cisiondata.modules.auth.service.IPermissionService;
import org.cisiondata.modules.auth.web.WebUtils;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("permissionService")
public class PermissionServiceImpl extends GenericServiceImpl<Permission, Long> implements IPermissionService {

	@Resource(name = "permissionDAO")
	private PermissionDAO permissionDAO = null;
	
	@Resource(name = "userResourceDAO")
	private UserResourceDAO userResourceDAO = null;
	
	@Resource(name = "resourceDAO")
	private ResourceDAO resourceDAO = null;
	
	@Resource(name = "buserRoleDAO")
	private UserRoleDAO buserRoleDAO = null;
	
	@Resource(name = "roleDAO")
	private RoleDAO roleDAO = null;
	
	
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

	@Override
	public boolean findPermissionCisionData(String requestUrl, HttpServletRequest request, HttpServletResponse response)
			throws BusinessException {
		List<org.cisiondata.modules.auth.entity.Resource> list = findResource(requestUrl);
		Long ResourceId = null;
		if (list.size() < 1) {
			return true;
		}
		if (list.size() > 1) {
			throw new BusinessException(666,"资源URL重复");
		}
		ResourceId = list.get(0).getId();
		User user = WebUtils.getCurrentUser();
		Long userId = user.getId();
		Set<Long> set = new HashSet<>();
		List<UserResource> list2 = findUserResource(userId);
		for (int i = 0; i < list2.size(); i++) {
			set.add(list2.get(i).getResourceId());
		}
		if (set.contains(ResourceId)) {
			return true;
		}
		if (set.size() > 0) {
			return false;
		}
		List<BUserRole> userRole = findBUserRole(userId);
		set = new HashSet<>();
		for (int i = 0; i < userRole.size(); i++) {
			Long roleId = userRole.get(i).getRoleId();
			Role role = findRole(roleId);
			if (role.getDeleteFlag() == false) {
				List<Permission> list1 = readPermissionsByPrincipalTypeAndPrincipalId(3, roleId);
				for (int j = 0; j < list1.size(); j++) {
					set.add(list1.get(j).getResourceId());
				}
			}
		}
		for (int i = 0; i < list2.size(); i++) {
			set.add(list2.get(i).getResourceId());
		}
		if (set.contains(ResourceId)) {
			return true;
		}
		return false;
	}
	
	private List<org.cisiondata.modules.auth.entity.Resource> findResource(String requestUrl) {
		Query query = new Query();
		query.addCondition("url", requestUrl);
		query.addCondition("deleteFlag", false);
		List<org.cisiondata.modules.auth.entity.Resource> list = resourceDAO.readDataListByCondition(query);
		return list;
	}
	
	private List<BUserRole> findBUserRole(Long userId) {
		Query query = new Query();
		 query.addCondition("priority", 3);
		 query.addCondition("userId", userId);
		List<BUserRole> list1 = buserRoleDAO.readDataListByCondition(query);
		return list1;
	}
	
	
	private Role findRole(Long roleId) {
		Query query = new Query();
		 query.addCondition("id", roleId);
		return roleDAO.readDataByCondition(query);
	}
	
	private List<UserResource> findUserResource(Long userId) {
		Query query = new Query();
		query.addCondition("userId", userId);
		query.addCondition("priority", 3);
		query.addCondition("deleteFlag", false);
		List<UserResource> list2 = userResourceDAO.readDataListByCondition(query);
		return list2;
	}
}
