package org.cisiondata.modules.user.dao;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.user.entity.RoleUser;
import org.springframework.stereotype.Repository;

@Repository("roleUserDAO")
public interface RoleUserDAO {
	//新增角色
	public int addRole(RoleUser role);
	//查询角色是否存在
	public int selRoleByName(String name);
	//查询所有角色
	public List<RoleUser> selRole();
	//删除角色
	public int updateRoleByFlag(String name);
	//修改角色
	public int updateRole(Map<String, Object> map);
	//分页查询角色
	public List<RoleUser> selRoleByPage(int index,int pageSize);
}
