package org.cisiondata.modules.admin.auth.service.impl;

import java.util.List;

import org.cisiondata.modules.admin.auth.dao.AGroupDAO;
import org.cisiondata.modules.admin.auth.entity.AGroup;
import org.cisiondata.modules.admin.auth.service.IAGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AGroupServiceImpl implements IAGroupService{
	@Autowired
	private AGroupDAO groupDAO;
	@Override
	public List<AGroup> selAll() {
		// TODO Auto-generated method stub
		return groupDAO.selAll();
	}
	@Override
	public String addGroup(AGroup group) {
		// TODO Auto-generated method stub
		return groupDAO.addGroup(group);
	}
	@Override
	public List<AGroup> getIdGroup(Long id) {
		// TODO Auto-generated method stub
		return groupDAO.getIdGroup(id);
	}
	@Override
	public int updateGroup(AGroup group) {
		// TODO Auto-generated method stub
		return groupDAO.updateGroup(group);
	}
	@Override
	public int selGroup(String name) {
		// TODO Auto-generated method stub
		return groupDAO.selGroup(name);
	}
	@Override
	public int delGUser(Long id) {
		// TODO Auto-generated method stub
		return groupDAO.delGUser(id);
	}
	@Override
	public int delGRole(Long id) {
		// TODO Auto-generated method stub
		return groupDAO.delGRole(id);
	}
	@Override
	public int delGroup(Long id) {
		// TODO Auto-generated method stub
		return groupDAO.delGroup(id);
	}
	@Override
	public List<AGroup> getByIdUser(Long id) {
		// TODO Auto-generated method stub
		return groupDAO.getByIdUser(id);
	}
	@Override
	public List<AGroup> getByIdNotUser(Long id) {
		// TODO Auto-generated method stub
		return groupDAO.getByIdNotUser(id);
	}

}
