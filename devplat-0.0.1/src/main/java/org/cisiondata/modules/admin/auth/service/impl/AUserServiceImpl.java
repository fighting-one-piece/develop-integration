package org.cisiondata.modules.admin.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.cisiondata.modules.admin.auth.dao.AUserDao;
import org.cisiondata.modules.admin.auth.entity.AUser;
import org.cisiondata.modules.admin.auth.entity.AUserAGroup;
import org.cisiondata.modules.admin.auth.entity.AUserARole;
import org.cisiondata.modules.admin.auth.service.IAUserService;
import org.springframework.stereotype.Service;

@Service("aUserService")
public class AUserServiceImpl implements IAUserService{
	
	@Resource(name="aUserDao")
	private AUserDao aUserDao;
	
	//增加用户
	@Override
	public void addAUser(AUser auser) {
		aUserDao.addAUser(auser);
	}
	
	//删除用户
	@Override
	public void deleteAUser(long id) {
		aUserDao.deleteAUser(id);
	}
	
	//修改用户
	@Override
	public void updateAUser(AUser auser) {
		aUserDao.updateAUser(auser);
	}
	
	//查询用户
	@Override
	public AUser selectAUser(String account) {
		AUser selectauser = new AUser();
		selectauser = aUserDao.findAuser(account);
		return selectauser;
	}

	@Override
	public List<AUser> selectAllAUser() {
		List<AUser> aUser = new ArrayList<AUser>();
		aUser = aUserDao.findAllAUser();
		return aUser;
	}

	//用户与组的关系
	@Override
	public void insertgroup(String groupid) {
		String[] group = groupid.split(",");
		AUserAGroup auseragroup = new AUserAGroup();
		
		for (int i = 0; i < group.length - 1; i++) {
			auseragroup.setAuser(Long.parseLong(group[0]));
			auseragroup.setAgroup(Long.parseLong(group[i+1]));
			aUserDao.insertgroup(auseragroup);
		}
		
	}

	//用户与角色的关系
	@Override
	public void insertrole(String roleid) {
		String[] role = roleid.split(",");
		AUserARole auserarole = new AUserARole();
		
		for (int i = 0; i < role.length - 1; i++) {
			auserarole.setAuser(Long.parseLong(role[0]));
			auserarole.setArole(Long.parseLong(role[i+1]));
			aUserDao.insertrole(auserarole);
		}
		
	}
	
}
