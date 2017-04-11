package org.cisiondata.modules.user.service;

import java.util.Map;

public interface IRoleUserService {
	//新增角色
	public String addRole(String name,String identity,String desc);
	//删除角色
	public String updateRoleByFlag(String name);
	//修改角色
	public String updateRole(String name,String identity,String desc,int id);
	//分页查询角色
	public Map<String,Object> selRoleByPage(int index,int pageSize);
}
