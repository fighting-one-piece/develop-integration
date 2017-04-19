package org.cisiondata.modules.user.service;

import java.util.Map;

public interface IRoleUserService {
	// 新增角色
	public String addRole(String name, String identity, String desc);

	// 删除角色
	public String updateRoleByFlag(int id,Boolean deleteFlag);

	// 修改角色
	public String updateRole(String name, String identity, String desc, int id);

	// 分页查询角色
	public Map<String, Object> selRoleByPage(int index, int pageSize);

	// 角色添加用户
	public String addUserRoles(long role_id, String usrId, String priority);

	/* API角色 */
	// 删除API角色
	public String updateApiRoleByFlag(int  id,Boolean deleteFlag);

	// 修改API角色
	public String updateApiRole(String name, String identity, String desc, int id);

	// 分页查询API角色
	public Map<String, Object> selApiRoleByPage(int index, int pageSize);

	/* 警友通 */
	// 删除JYT角色
	public String updateJYTRoleByFlag(int  id,Boolean deleteFlag);

	// 修改JYT角色
	public String updateJYTRole(String name, String identity, String desc, int id);

	// 分页查询JYT角色
	public Map<String, Object> selJYTRoleByPage(int index, int pageSize);
}
