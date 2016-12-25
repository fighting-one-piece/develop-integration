package org.cisiondata.modules.admin.auth.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.cisiondata.modules.admin.auth.dao.RoleDAO;
import org.cisiondata.modules.admin.auth.entity.RoleUser;
import org.cisiondata.modules.admin.auth.service.IRoleService;
import org.springframework.stereotype.Service;

@Service("reloe")
public class RoleServiceImpl   implements IRoleService{

	@Resource(name="RoleDAOS")
	private RoleDAO roledao=null;
	
	
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

	
	

}
