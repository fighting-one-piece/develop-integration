package org.cisiondata.modules.admin.auth.service;

import java.util.List;

import org.cisiondata.modules.admin.auth.entity.AUser;


public interface IAUserService {
	//增加用户
	public void addAUser(AUser auser);
	//删除用户
	public void deleteAUser(long id);
	//修改用户
	public void updateAUser(AUser auser);
	//查询用户
	public AUser selectAUser(String account);
	//显示所有用户
	public List<AUser> selectAllAUser();
	//插入用户与组的关系
	public void insertgroup(String groupid);
	//插入用户与角色的关系
	public void insertrole(String roleid);
}
