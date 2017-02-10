package org.cisiondata.modules.user.service;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.user.entity.AUser;
import org.cisiondata.modules.user.entity.AUserAGroup;
import org.cisiondata.modules.user.entity.AUserARole;


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
	public Map<String, Object> selectAllAUser(int index);
	//插入用户与组的关系
	public void insertgroup(String groupid);
	//插入用户与角色的关系
	public void insertrole(String roleid);
	//查询用户与组的关系
	public List<AUserAGroup> findusergroup(Long userid);
	//查询用户与角色的关系
	public List<AUserARole> findusertrole(Long userid);
	//修改用户密码
	public String updatepassword(String account,String originalpassword,String newpassword);
}
