package org.cisiondata.modules.auth.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.auth.Constants;
import org.cisiondata.modules.auth.entity.Group;
import org.cisiondata.modules.auth.entity.Permission;
import org.cisiondata.modules.auth.entity.Role;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.auth.service.IAuthService;
import org.cisiondata.modules.auth.service.IGroupService;
import org.cisiondata.modules.auth.service.IPermissionService;
import org.cisiondata.modules.auth.service.IResourceService;
import org.cisiondata.modules.auth.service.IRoleService;
import org.cisiondata.modules.auth.service.IUserService;
import org.cisiondata.modules.auth.web.WebContext;
import org.cisiondata.utils.date.DateFormatter;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.cisiondata.utils.token.TokenUtils;
import org.cisiondata.utils.web.IPUtils;
import org.springframework.stereotype.Service;

@Service("authService")
public class AuthServiceImpl implements IAuthService {
	
	@Resource(name = "userService")
	private IUserService userService = null;

	@Resource(name = "roleService")
	private IRoleService roleService = null;
	
	@Resource(name = "groupService")
	private IGroupService groupService = null;
	
	@Resource(name = "resourceService")
	private IResourceService resourceService = null;
	
	@Resource(name = "permissionService")
	private IPermissionService permissionService = null;
	
	@Override
	public User readUserByAccount(String account) throws BusinessException {
		return userService.readUserByAccount(account);
	}
	
	@Override
	public String readUserAccessKeyByAccessId(String accessId) throws BusinessException {
		return userService.readUserAccessKeyByAccessId(accessId);
	}

	@Override
	public User readUserByAccountAndPassword(String account, String password) throws BusinessException {
		return userService.readUserByAccountAndPassword(account, password);
	}
	
	@Override
	public User readUserAuthenticationInfo(String account, String password) throws BusinessException {
		User user = userService.readUserByAccountAndPassword(account, password);
		if (!user.isValid()) throw new BusinessException(ResultCode.ACCOUNT_EXPIRED_OR_DELETED);
		String macAddress = IPUtils.getMACAddress(WebContext.get().getRequest());
		String currentDate = DateFormatter.DATE.get().format(new Date());
		String accessToken = TokenUtils.genAuthenticationMD5Token(account, 
				user.getPassword(), macAddress, currentDate);
		user.setAccessToken(accessToken);
		WebContext.get().getSession().getManager().setCookieSecure(true);
		WebContext.get().getSession().setAttribute(Constants.SESSION_CURRENT_USER, user);
		WebContext.get().getSession().setAttribute(Constants.SESSION_CURRENT_USER_ACCOUNT, account);
		RedisClusterUtils.getInstance().set(accessToken, WebContext.get().getSession().getId(), 1800);
		return user;
	}
	
	@Override
	public User readUserAuthorizationInfo(String account) throws BusinessException {
		User user = userService.readUserByAccount(account);
		if (!user.isValid()) throw new BusinessException(ResultCode.ACCOUNT_EXPIRED_OR_DELETED);
		String macAddress = IPUtils.getMACAddress(WebContext.get().getRequest());
		String currentDate = DateFormatter.DATE.get().format(new Date());
		String accessToken = TokenUtils.genAuthorizationMD5Token(account, 
				user.getPassword(), macAddress, currentDate);
		RedisClusterUtils.getInstance().delete(user.getAccessToken());
		user.setAccessToken(accessToken);
		WebContext.get().getSession().getManager().setCookieSecure(true);
		WebContext.get().getSession().setAttribute(Constants.SESSION_CURRENT_USER, user);
		WebContext.get().getSession().setAttribute(Constants.SESSION_CURRENT_USER_ACCOUNT, account);
		RedisClusterUtils.getInstance().set(accessToken, WebContext.get().getSession().getId(), 1800);
		return user;
	}
	
	@Override
	public String readUserAuthorizationToken(String account) throws BusinessException {
		return readUserAuthorizationInfo(account).getAccessToken();
	}
	
	@Override
	public boolean readUserAuthorizationInfo(String account, String url) throws BusinessException {
		return false;
	}

	@Override
	public Set<String> readRoleIdentitiesByUserId(Long userId) throws BusinessException {
		Set<String> roleIdentities = roleService.readRoleIdentitiesByUserId(userId);
		List<Group> groups = groupService.readGroupsByUserId(userId);
		List<Role> roles = null;
		for (int i = 0, iLen = groups.size(); i < iLen; i++) {
			roles = roleService.readRolesByGroupId(groups.get(i).getId());
			for (int j = 0, jLen = roles.size(); j < jLen; j++) {
				roleIdentities.add(roles.get(i).getIdentity());
			}
		}
		return roleIdentities;
	}
	
	@Override
	public Set<String> readPermissionIdentitiesByUserId(Long userId) throws BusinessException {
		Set<Permission> allPermissions = new HashSet<Permission>();
		
		List<Permission> userPermissions = permissionService
				.readPermissionsByPrincipalTypeAndPrincipalId(Permission.PRINCIPAL_TYPE_USER, userId);
		allPermissions.addAll(userPermissions);
		
		Set<Role> allRoles = new HashSet<Role>();
		
		/**
		List<Group> groups = groupService.readGroupsByUserId(userId);
		List<Role> groupRoles = null;
		List<Permission> groupPermissions = null;
		for (int i = 0, len = groups.size(); i < len; i++) {
			Long groupId = groups.get(i).getId();
			groupRoles = roleService.readRolesByGroupId(groupId);
			allRoles.addAll(groupRoles);
			groupPermissions = permissionService.readPermissionsByPrincipalTypeAndPrincipalId(
					Permission.PRINCIPAL_TYPE_GROUP, groupId);
			allPermissions.addAll(groupPermissions);
		}
		**/
		
		List<Role> roles = roleService.readRolesByUserId(userId);
		allRoles.addAll(roles);
		List<Permission> rolePermissions = null;
		for (int i = 0, len = roles.size(); i < len; i++) {
			rolePermissions = permissionService.readPermissionsByPrincipalTypeAndPrincipalId(
					Permission.PRINCIPAL_TYPE_ROLE, roles.get(i).getId());
			allPermissions.addAll(rolePermissions);
		}
		
		Set<String> permissions = new HashSet<String>();
		for (Permission permission : allPermissions) {
			String resourceIdentity = resourceService.readIdentityById(permission.getResourceId());
			List<String> operateIdentities = permission.obtainOperateIdentities();
			for (int i = 0, len = operateIdentities.size(); i < len; i++) {
				permissions.add(resourceIdentity + ":" + operateIdentities.get(i));
			}
		}
		return permissions;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<String> readPermissionIdentitiesByAccount(String account) throws BusinessException {
		String userPermissionsCacheKey = genUserPermissionsCacheKey(account);
		Object cacheValue = RedisClusterUtils.getInstance().get(userPermissionsCacheKey);
		if (null != cacheValue) return (Set<String>) cacheValue;
		User user = userService.readUserByAccount(account);
		if (null == user) throw new BusinessException(ResultCode.ACCOUNT_NOT_EXIST);
		if (!user.isValid()) throw new BusinessException(ResultCode.ACCOUNT_EXPIRED_OR_DELETED);
		Set<String> permissions = readPermissionIdentitiesByUserId(user.getId());
		RedisClusterUtils.getInstance().set(userPermissionsCacheKey, permissions, 1800);
		return permissions;
	}
	
	@Override
	public boolean judgeUserPermission(String account, String url) throws BusinessException {
		Set<String> permissions = readPermissionIdentitiesByAccount(account);
		String permission = readResourceReadIdentityByUrl(url);
		return permissions.contains(permission);
	}
	
	@Override
	public String readResourceReadIdentityByUrl(String url) throws BusinessException {
		String identity = resourceService.readIdentityByUrl(url);
		return StringUtils.isBlank(identity) ? null : identity + ":" + Permission.READ;
	}
	
	private String genUserPermissionsCacheKey(String account) {
		return "user:cache:account:" +account+ ":permissions";
	}
	
}
