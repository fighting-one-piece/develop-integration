package org.cisiondata.modules.admin.auth.dao;

import java.util.List;

import org.cisiondata.modules.admin.auth.entity.AUser;
import org.cisiondata.modules.admin.auth.entity.AUserAGroup;
import org.cisiondata.modules.admin.auth.entity.AUserARole;
import org.springframework.stereotype.Repository;


@Repository("aUserDao")
public interface AUserDao {
	//增加用户
	public void addAUser(AUser auser);
	//删除用户
	public void deleteAUser(long id);
	//修改用户
	public void updateAUser(AUser auser);
	//查询用户
	public AUser findAuser(String account);
	//查询所以用户
	public List<AUser> findAllAUser();
	//插入用户与组的关系
	public void insertgroup(AUserAGroup auseragroup);
	//插入角色与组的关系
	public void insertrole(AUserARole auserarole);
}
