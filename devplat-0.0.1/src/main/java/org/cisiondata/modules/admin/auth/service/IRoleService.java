package org.cisiondata.modules.admin.auth.service;

import java.util.List;

import org.cisiondata.modules.admin.auth.entity.RoleUser;

public interface IRoleService{
	
	/**
	 * 全部角色
	 * @return
	 */
	public List<RoleUser> readDataRole();
	
	/**
	 * 查询某一条id的数据
	 */
	public RoleUser readDataCertain(Long id);
	
	/**
	 * 修改
	 */
	public int readUpdata(RoleUser roleUser);
	
	/**
	 * 增加角色类
	 */
}
