package org.cisiondata.modules.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.cisiondata.modules.user.dao.AUserDao;
import org.cisiondata.modules.user.dao.RoleDAO;
import org.cisiondata.modules.user.entity.AUser;
import org.cisiondata.modules.user.entity.AUserARole;
import org.cisiondata.modules.user.entity.RoleUser;
import org.cisiondata.modules.user.service.IRoleService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("reloe")
public class RoleServiceImpl   implements IRoleService{

	@Resource(name="RoleDAOS")
	private RoleDAO roledao=null;
	
	@Resource(name="aUserDao")
	private AUserDao auserdao=null;
	
	
	public List <RoleUser> readDataRole() {
		List<RoleUser> reads=roledao.readDataLsitroleid();
		return reads;
	}

	
	@Override
	public RoleUser readDataCertain(Long id) {
		 RoleUser Certain=roledao.readDataCertain(id);
		return Certain;
	}


	@Override
	public int readUpdata(RoleUser roleUser) {
		return roledao.readUpdata(roleUser);
	}


	@Override
	public int readDelet(Long id) {
		return roledao.readDelet(id);
	}


	@Override
	public int readuserDelet(Long id) {
		return roledao.readuserDelet(id);
	}


	@Override
	public int readgroupdelet(Long id) {
		return roledao.readgroupdelet(id);
	}


	@Override
	public List<AUser> getUser() {
		List<AUser>finduers=auserdao.findAllAUser();
		return finduers;
	}


	@Override
	public int addedRole(RoleUser addRoleuser) {
		// TODO Auto-generated method stub
		return roledao.addedRole(addRoleuser);
	}


	@Override
	public int roleuserID(AUserARole auserArole) {
		// TODO Auto-generated method stub
		return roledao.roleuserID(auserArole);
	}


	@Override
	public int seletName(String name) {
		// TODO Auto-generated method stub
		return roledao.selectName(name);
	}
	
	@Override
	public List<String> findID(String userROID,long Roleid) {
		//System.out.println(userROID);
		String url=String.valueOf(Roleid);
		String asd=null;
		List<String> Roeles=new ArrayList<>();
		String[] ROID=userROID.split(",");
		for (int i = 0; i < ROID.length; i++) {
			AUserARole userArole=new AUserARole();
			userArole.setArole(Roleid);
			userArole.setAuser(Long.parseLong(ROID[i]));
			//System.out.println("切割后"+Long.parseLong(ROID[i]));
			int roleUser=roledao.findID(userArole);
			//System.out.println("验证后的"+roleUser);
			//拼接数据
			asd=Long.parseLong(ROID[i])+","+url+","+roleUser;
			//System.out.println(asd);
			Roeles.add(asd);
		}
		return Roeles;
	}

	//新增
	@Override
	public List<String> AddDelete(String addDelete,long AddDeleteID) {
		List<String> adddele=new ArrayList<>();
		String [] addDele=addDelete.split(",");
		for (int i = 0; i < addDele.length; i++) {
			AUserARole userArole=new AUserARole();
			userArole.setArole(AddDeleteID);
			userArole.setAuser(Long.parseLong(addDele[i]));
			//创建一个方法验证是否存在 存在的就不能新增
			int results=roledao.findID(userArole);
			//System.out.println(results+"验证信息");
			System.out.println(results);
			if(results<=0){
				int Result =roledao.roleuserID(userArole);
				//System.out.println("新增"+Result);
				adddele.add(Integer.toString(Result));
			}else if(results>0){
				int modify=roledao.modify(userArole);
				//System.out.println("修改"+modify);
				adddele.add(Integer.toString(modify));
			}
		}
		return adddele;
	}


	@Override
	public List<String> Deleteadd(String Deleteadd, long DeleteaddID) {
			List<String>Deleteadds=new ArrayList<>();
			String [] Delete=Deleteadd.split(",");
			for (int i = 0; i < Delete.length; i++) {
				AUserARole userArole=new AUserARole();
				userArole.setArole(DeleteaddID);
				userArole.setAuser(Long.parseLong(Delete[i]));
				//小于一不能删除，大于1删除
				int results=roledao.findID(userArole);
				//System.out.println(results+"验证");
				if(results>0){
					//删除
					int result=roledao.Delet(userArole);
					Deleteadds.add(Integer.toString(result));
				}else if(results<=0){
					//不能删除
					continue;
				}
			}
		return Deleteadds;
	}

	//修改唯一判断
	@Override
	public int SoleJudgment(RoleUser roleUser) throws BusinessException{
		String name = roleUser.getName();
		int code = seletName(name);
		if (code <= 0) {
			return readUpdata(roleUser);
		}else{
			throw new BusinessException("修改失败");
		}
	}

	//删除的唯一判断
	@Override
	public int DeleteJudgment(Long id) throws BusinessException {
		int usercode = readuserDelet(id);
		int groupcode =readgroupdelet(id);
		if (usercode >= 0 && groupcode >= 0) {
			return readDelet(id);
		}else{
			throw new BusinessException("删除失败！");
		}
		
	}

	//新增的唯一判断
	@Override
	public int AddJudgment(RoleUser roleUser) throws BusinessException {
		String name=roleUser.getName();
		int code=seletName(name);
		if(code<=0){
			return addedRole(roleUser);
		}else{
			throw new BusinessException("新增失败");
		}
		
	}
}
