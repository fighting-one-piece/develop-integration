package org.cisiondata.modules.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.user.dao.AGroupDAO;
import org.cisiondata.modules.user.dao.AUserAGroupDAO;
import org.cisiondata.modules.user.entity.AGroup;
import org.cisiondata.modules.user.entity.AUserAGroup;
import org.cisiondata.modules.user.service.IAGroupService;
import org.cisiondata.utils.excel.PoiExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AGroupServiceImpl implements IAGroupService{
	@Autowired
	private AGroupDAO groupDAO;
	@Resource(name ="user_groupdao")
	private AUserAGroupDAO agroupdao;
	@Override
	public List<AGroup> selAll() {
		return groupDAO.selAll();
	}
	@Override
	public String addGroup(AGroup group) {
		String resultCode = null;
		String name = group.getName();
		group.setCreateTime(new Date());
		int code = selGroup(name);
		if(code <= 0){
			resultCode = groupDAO.addGroup(group);
		}
		return resultCode;
	}
	@Override
	public List<AGroup> getIdGroup(Long id) {
		return groupDAO.getIdGroup(id);
	}
	@Override
	public int updateGroup(AGroup group) {
		return groupDAO.updateGroup(group);
	}
	@Override
	public int selGroup(String name) {
		return groupDAO.selGroup(name);
	}
	@Override
	public int delGUser(Long id) {
		return groupDAO.delGUser(id);
	}
	@Override
	public int delGRole(Long id) {
		return groupDAO.delGRole(id);
	}
	@Override
	public int delGroup(Long id) {
		int groupCode = 0; 
		//获取删除与用户相关联信息
		int userCode = delGUser(id);
		//获取删除与角色相关联信息
		int roleCode = delGRole(id);
		if(userCode >=0 && roleCode >= 0){
			 groupCode = groupDAO.delGroup(id);
		}
		return groupCode;
	}
	@Override
	public List<AGroup> getByIdUser(Long id) {
		return groupDAO.getByIdUser(id);
	}
	@Override
	public List<AGroup> getByIdNotUser(Long id) {
		return groupDAO.getByIdNotUser(id);
	}
	@Override
	public void addGUser(String user_id, Long group_id) {
		AUserAGroup aGroup = new AUserAGroup();
		//页面传回ID
		List<String> userid = PoiExcelUtils.stringToList(user_id);
		List<AGroup> listGroup = groupDAO.getByIdUser(group_id);
		//原群组用户ID
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < listGroup.size(); i++) {
			list.add(String.valueOf(listGroup.get(i).getUserId()));
		}
		//增加
		for (int i = 0; i < userid.size(); i++) {
			if(!list.contains(userid.get(i))){
				aGroup.setAuser(Long.parseLong(userid.get(i)));
				aGroup.setAgroup(group_id);
				agroupdao.addGUser(aGroup);
			}
		}
		//删除
		for (int i = 0; i < list.size(); i++) {
			if (!userid.contains(list.get(i))){
				agroupdao.delGUser(Long.parseLong(list.get(i)), group_id);
			}
		}
	}
	//根据ID查询
	public Map<String, Object> getById(Long id) {
		Map<String, Object> map = new  HashMap<String, Object>();
		List<AGroup> listUser = getByIdUser(id);
		List<AGroup> listNot = getByIdNotUser(id);
		map.put("user", listUser);
		map.put("notUser", listNot);
		return map;
	}

}
