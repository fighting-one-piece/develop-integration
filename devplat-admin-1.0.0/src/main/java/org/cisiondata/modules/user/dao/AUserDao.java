package org.cisiondata.modules.user.dao;

import java.util.List;

import org.cisiondata.modules.user.entity.AUser;
import org.cisiondata.modules.user.entity.AUserAGroup;
import org.cisiondata.modules.user.entity.AUserARole;
import org.springframework.stereotype.Repository;


@Repository("aUserDao")
public interface AUserDao {
	//增加用户
	public void addAUser(AUser auser);
	//删除用户
	public void deleteAUser(long id);
	//修改用户
	public void updateAUser(AUser auser);
	//通过账号查询用户
	public AUser findAuser(String account);
	//通过ID查询用户
	public AUser findAuserByID(Long id);
	//查询所以用户
	public List<AUser> findAllAUser();
	//查询所以用户
	public List<AUser> findAUserByPage(int start,int endpage);
	//插入用户与组的关系
	public void insertgroup(AUserAGroup auseragroup);
	//插入角色与用户的关系
	public void insertrole(AUserARole auserarole);
	//删除用户与组的关系
	public void deletegroupuser(Long auser,Long agroup);
	//删除角色与用户的关系
	public void deleteroleuser(Long auser,Long arole);
	//查询用户与组的关系
	public List<AUserAGroup> findgroupuser(Long userid);
	//查询角色与用户的关系
	public List<AUserARole> findroleuser(Long userid);
}
