package org.cisiondata.modules.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.datainterface.dao.AccessUserControlDao;
import org.cisiondata.modules.datainterface.entity.AccessUserControl;
import org.cisiondata.modules.datainterface.service.IAccessUserInterfaceService;
import org.cisiondata.modules.user.dao.AUserDao;
import org.cisiondata.modules.user.entity.AUser;
import org.cisiondata.modules.user.entity.AUserAGroup;
import org.cisiondata.modules.user.entity.AUserARole;
import org.cisiondata.modules.user.service.IAUserService;
import org.cisiondata.utils.endecrypt.EndecryptUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("aUserService")
public class AUserServiceImpl implements IAUserService{
	
	@Resource(name="aUserDao")
	private AUserDao aUserDao;
	
	@Resource(name = "aaccessUserControlDao")
	private AccessUserControlDao accessUserControlDao;
	
	@Resource(name = "accessUserInterfaceService")
	private IAccessUserInterfaceService accessUserInterfaceService = null;
	
	//分页
		int pageCount = 0; //总页数
		int count = 10;  //每页显示的条数
		int page = 0; //计算每页从哪里开始读取数据
	
		//显示所有用户及分页查询
		@Override
		public Map<String, Object> selectAllAUser(int index) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<AUser> aUser = new ArrayList<AUser>();
			aUser = aUserDao.findAllAUser();
			//计算总页数
			if(aUser != null && aUser.size() > 0){
				 pageCount =  aUser.size() % 10 == 0 ? aUser.size() / 10 : aUser.size() / 10 + 1;
			}
			//计算当前页显示的数据
			page = (index - 1) * 10;
			aUser = aUserDao.findAUserByPage(page, count);
			map.put("aUser", aUser);
			map.put("pageCount", pageCount);
			return map;
		}

	//增加用户
	@Override
	@Transactional
	public void addAUser(AUser auser) {
		String password = EndecryptUtils.encryptPassword(auser.getAccount(), auser.getPassword());
		auser.setPassword(password);
		Date createTime = new Date();
		auser.setCreateTime(createTime);
		aUserDao.addAUser(auser);
		//增加用户控制
		AccessUserControl accessUserControl = new AccessUserControl();
		accessUserControl.setAccount(auser.getAccount());
		accessUserControl.setCount(0L);
		accessUserControl.setRemainingCount(0L);
		accessUserControl.setMoney(0.0);
		accessUserControl.setRemainingMoney(0.0);
		accessUserControl.setSource(1);
		accessUserControlDao.addAccessUserControl(accessUserControl);
		//给用户赋所有接口价格
		
	}
	
	//删除用户
	@Override
	public void deleteAUser(long id) {
		aUserDao.deleteAUser(id);
	}
	
	//修改用户
	@Override
	public void updateAUser(AUser auser) {
		String password = EndecryptUtils.encryptPassword(auser.getAccount(), auser.getPassword());
		auser.setPassword(password);
		aUserDao.updateAUser(auser);
	}
	
	//查询用户
	@Override
	public AUser selectAUser(String account) {
		AUser selectauser = new AUser();
		selectauser = aUserDao.findAuser(account);
		return selectauser;
	}
	
	//用户与组的关系
	@Override
	public void insertgroup(String groupid) {
		String[] group = groupid.split(",");
		AUserAGroup auseragroup = new AUserAGroup();
		List<AUserAGroup> usergroup = new ArrayList<AUserAGroup>();
		List<Long> list = new ArrayList<Long>();
		List<Long> list1 = new ArrayList<Long>();
		usergroup =	aUserDao.findgroupuser(Long.parseLong(group[0]));
		auseragroup.setAuser(Long.parseLong(group[0]));
		for (int i = 0; i < group.length - 1; i++) {
			auseragroup.setAgroup(Long.parseLong(group[i+1]));
			list1.add(Long.parseLong(group[i+1]));
			int num = 0;
			for (int j = 0; j < usergroup.size(); j++) {
				Long kiss = Long.parseLong(group[i+1]);
				Long mark =usergroup.get(j).getAgroup();
				if ( kiss.equals(mark)) {
					num = 1;
				}
			}
			if (num == 0) {
				aUserDao.insertgroup(auseragroup);
			}
		}
		for (int j = 0; j < usergroup.size(); j++) {
			if (! list1.contains(usergroup.get(j).getAgroup())) {
				list.add(usergroup.get(j).getAgroup());
			}
		}
		for (int i = 0; i < list.size(); i++) {
			aUserDao.deletegroupuser(Long.parseLong(group[0]), list.get(i));
		}
	}

	//用户与角色的关系
	@Override
	public void insertrole(String roleid) {
		String[] role = roleid.split(",");
		AUserARole auserarole = new AUserARole();

		List<AUserARole> roleuser = new ArrayList<AUserARole>();
		List<Long> list = new ArrayList<Long>();
		List<Long> list1 = new ArrayList<Long>();
		roleuser =	aUserDao.findroleuser(Long.parseLong(role[0]));
		auserarole.setAuser(Long.parseLong(role[0]));
		for (int i = 0; i < role.length - 1; i++) {
			auserarole.setArole(Long.parseLong(role[i+1]));
			list1.add(Long.parseLong(role[i+1]));
			int num = 0;
			for (int j = 0; j < roleuser.size(); j++) {
				Long kiss = Long.parseLong(role[i+1]);
				Long mark =roleuser.get(j).getArole();
				if ( kiss.equals(mark)) {
					num = 1;
				}
			}
			if (num == 0) {
				aUserDao.insertrole(auserarole);
			}
		}
		for (int j = 0; j < roleuser.size(); j++) {
			if (! list1.contains(roleuser.get(j).getArole())) {
				list.add(roleuser.get(j).getArole());
			}
		}
		for (int i = 0; i < list.size(); i++) {
			aUserDao.deleteroleuser(Long.parseLong(role[0]), list.get(i));
		}
	}
	
	//修改用户密码
	@Override
	public String updatepassword(String account, String originalpassword,String newpassword) {
		String result = null;
		AUser auser = aUserDao.findAuser(account);
		String password = EndecryptUtils.encryptPassword(account,originalpassword);
		if (! password.equals(auser.getPassword())) {
			result = "原密码不正确！";
		}else {
			auser.setPassword(newpassword);
			aUserDao.updateAUser(auser);
			result  = "密码已修改！";
		}
		return result;
	}

	//查询用户与组的关系
	@Override
	public List<AUserAGroup> findusergroup(Long userid) {
		List<AUserAGroup> result = new ArrayList<AUserAGroup>();
		result = aUserDao.findgroupuser(userid);
		return result;
	}

	//查询用户与角色的关系
	@Override
	public List<AUserARole> findusertrole(Long userid) {
		List<AUserARole> result = new ArrayList<AUserARole>();
		result = aUserDao.findroleuser(userid);
		return result;
	}
	
}
