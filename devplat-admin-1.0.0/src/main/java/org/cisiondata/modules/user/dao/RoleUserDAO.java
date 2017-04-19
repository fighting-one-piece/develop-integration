package org.cisiondata.modules.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.cisiondata.modules.user.entity.ARoleUser;
import org.cisiondata.modules.user.entity.AUserRole;
import org.springframework.stereotype.Repository;

@Repository("roleUserDAO")
public interface RoleUserDAO {
	// 新增角色
	public int addRole(ARoleUser role);

	// 查询角色是否存在
	public int selRoleByName(String name);

	// 查询所有角色
	public List<ARoleUser> selRole();

	// 删除角色
	public int updateRoleByFlag(int  id);
	
	public int updateRoleFlag(int id);
	// 修改角色
	public int updateRole(Map<String, Object> map);

	// 分页查询角色
	public List<ARoleUser> selRoleByPage(int index, int pageSize);

	// 角色添加用户
	public int addUserRoles(List<AUserRole> userRoleList);

	// 角色删除用户
	public void deleteserRoles(@Param("id") long id, @Param("priority") String priority);
	//根据id查询
	public List<AUserRole> findAllbyId(Long id);

	/* API角色 */
	// 查询API角色是否存在
	public int selApiRoleByName(String name);

	// 查询所有API角色
	public List<ARoleUser> selApiRole();

	// 删除API角色
	public int updateApiRoleByFlag(int  id);
	public int updateApiRoleFlag(int  id);
	// 修改API角色
	public int updateApiRole(Map<String, Object> map);

	// 分页查询API角色
	public List<ARoleUser> selApiRoleByPage(int index, int pageSize);
	
	//根据id和identity查询角色
	public String findIdinentity(Map<String, Object> mmp);

	/** 警友通 */
	// 查询JYT角色是否存在
	public int selJYTRoleByName(String name);

	// 查询所有JYT角色
	public List<ARoleUser> selJYTRole();

	// 删除JYT角色
	public int updateJYTRoleByFlag(int  id);
	public int updateJYTRoleFlag(int  id);
	// 修改JYT角色
	public int updateJYTRole(Map<String, Object> map);

	// 分页查询JYT角色
	public List<ARoleUser> selJYTRoleByPage(int index, int pageSize);
}
