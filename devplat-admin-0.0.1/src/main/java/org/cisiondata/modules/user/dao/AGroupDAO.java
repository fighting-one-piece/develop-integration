package org.cisiondata.modules.user.dao;

import java.util.List;

import org.cisiondata.modules.user.entity.AGroup;
import org.springframework.stereotype.Repository;

@Repository("groupdao")
public interface AGroupDAO {
	//查询全部
	public List<AGroup> selAll();
	//增加群组
	public String addGroup(AGroup group);
	//根据id获取数据
	public List<AGroup> getIdGroup(Long id);
	//修改群组
	public int updateGroup(AGroup group);
	//查询是否存在该名称
	public int selGroup(String name);
	//根据ID删除与群组相关联用户信息
	public int delGUser(Long id);
	//根据ID删除与群组相关联角色信息
	public int delGRole(Long id);
	//根据ID删除群组信息
	public int delGroup(Long id);
	//根据群组ID获取用户名
	public List<AGroup> getByIdUser(Long id);
	//获取不属于当前群组的用户名
	public List<AGroup> getByIdNotUser(Long id);
}
