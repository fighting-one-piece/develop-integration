package org.cisiondata.modules.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.user.dao.RoleUserDAO;
import org.cisiondata.modules.user.entity.ARoleUser;
import org.cisiondata.modules.user.entity.AUserRole;
import org.cisiondata.modules.user.service.IRoleUserService;
import org.cisiondata.modules.user.utils.Head;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
@Service("roleUserService")
public class RoleUserServiceImpl implements IRoleUserService,InitializingBean{
	@Resource(name="roleUserDAO")
	private RoleUserDAO roledao = null;
	List<Object> heads = new ArrayList<Object>();
	@Override
	public void afterPropertiesSet() throws Exception {
		Head head = new Head();
		head.setField("id");
		head.setFieldName("ID");
		heads.add(head);
		head = new Head();
		head.setField("name");
		head.setFieldName("角色名");
		heads.add(head);
		head = new Head();
		head.setField("identity");
		head.setFieldName("角色标识");
		heads.add(head);
		head = new Head();
		head.setField("desc");
		head.setFieldName("角色描述");
		heads.add(head);
		head = new Head();
		head.setField("deleteFlag");
		head.setFieldName("删除标识");
		heads.add(head);
	}
	public String addRole(String name, String identity, String desc) throws BusinessException{
		int code = roledao.selRoleByName(name);
		String result = null;
		if(code <= 0){
			ARoleUser role = new ARoleUser();
			role.setName(name);
			role.setIdentity(identity);
			role.setDesc(desc);
			role.setDeleteFlag(false);
			int num = roledao.addRole(role);
			if(num > 0){
				result = "新增成功";
			}else{
				result = "新增失败";
				throw new BusinessException(ResultCode.DATABASE_OPERATION_FAIL);
			}
		}else{
			result = "新增失败";
			throw new BusinessException(ResultCode.DATA_EXISTED);
		}
		return result;
	}
	
	//删除角色
	public String updateRoleByFlag(int id,Boolean deleteFlag) throws BusinessException{
		int code = 0;
		String result = null;
		if(deleteFlag){
			code = roledao.updateRoleFlag(id);
		}else{
			
			code = roledao.updateRoleByFlag(id);
		}
		if(code > 0){
			result = "删除成功";
		}else{
			result = "删除失败";
			throw new BusinessException(ResultCode.FAILURE);
		}
		return result;
	}
	//修改角色信息
	public String updateRole(String name, String identity, String desc,int id) throws BusinessException{
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("identity", identity);
		map.put("desc", desc);
		map.put("id", id);
		String result = null;
		if(StringUtils.isBlank(name) && StringUtils.isBlank(identity) && StringUtils.isBlank(desc)) throw new BusinessException(ResultCode.PARAM_NULL);
		int code = roledao.updateRole(map);
		if(code > 0){
			result = "修改成功";
		}else{
			result = "修改失败";
			throw new BusinessException(ResultCode.FAILURE);
		}
		return result;
	}
	//分页查询角色
	public Map<String,Object> selRoleByPage(int index, int pageSize) throws BusinessException{
		QueryResult<Map<String,Object>> result = new QueryResult<Map<String,Object>>();
		Map<String,Object> mapResult = new HashMap<String,Object>();
		if(index <= 0) throw new BusinessException(ResultCode.PARAM_ERROR);
		int pageCount = 0;
		int page = (index - 1) * pageSize;
		List<ARoleUser> list = roledao.selRole();
		if(list != null  && list.size() > 0){
			pageCount = list.size() % pageSize == 0 ? list.size() / pageSize : list.size() / pageSize + 1;
		}
		List<ARoleUser> listPage = roledao.selRoleByPage(page, pageSize);
		if(listPage != null  && listPage.size() > 0){
			List<Map<String,Object>>  listMap = new ArrayList<Map<String,Object>>();
			for(int i = 0,len = listPage.size(); i < len;i++){
				Map<String,Object> mapList = new HashMap<String,Object>();
				mapList.put("id", listPage.get(i).getRoleid());
				mapList.put("name", listPage.get(i).getName());
				mapList.put("identity", listPage.get(i).getIdentity());
				mapList.put("desc", listPage.get(i).getDesc());
				mapList.put("deleteFlag", listPage.get(i).getDeleteFlag());
				listMap.add(mapList);
			}
			result.setTotalRowNum(pageCount);
			result.setResultList(listMap);
			mapResult.put("head", heads);
			mapResult.put("data", result);
		}else{
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		}
		return mapResult;
	}
	//角色添加用户
	@Override
	public String addUserRoles(long role_id, String userId ,String priority) {
		String result="";
		if(StringUtils.isBlank(userId)){
			result="未添加用户";
			return result;
		}
		roledao.deleteserRoles(role_id,priority);
		String[] str=userId.split(",");
		List<AUserRole> list=new ArrayList<AUserRole>();
		for (String integer : str) {
			AUserRole  auserRole=	new AUserRole();
			auserRole.setAuserId(Long.parseLong(integer));
			auserRole.setAroleId((Long)role_id);
			auserRole.setPriority(priority);
			list.add(auserRole);
		}
		for (AUserRole aUserRole : list) {
			System.out.println("roleId"+aUserRole.getAroleId());
			System.out.println("userId"+aUserRole.getAuserId());
			System.out.println("priorit:"+aUserRole.getPriority());
			System.out.println("++++++++++++++++++++++++++++++++");
		}
		int code= roledao.addUserRoles(list);
		if(code > 0){
			result = "添加成功";
		}else{
			result = "添加失败";
			throw new BusinessException(ResultCode.FAILURE);
		}
		return result;
		
		
	}
	// 删除API角色
	public String updateApiRoleByFlag(int  id,Boolean deleteFlag) throws BusinessException{
		int code = 0;
		String result = null;
		if(deleteFlag){
			code = roledao.updateApiRoleFlag(id);
		}else{
			
			code = roledao.updateApiRoleByFlag(id);
		}
		if(code > 0){
			result = "删除成功";
		}else{
			result = "删除失败";
			throw new BusinessException(ResultCode.FAILURE);
		}
		return result;
	}
	// 修改API角色
	public String updateApiRole(String name, String identity, String desc, int id) throws BusinessException{
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("identity", identity);
		map.put("desc", desc);
		map.put("id", id);
		String result = null;
		if(StringUtils.isBlank(name) && StringUtils.isBlank(identity) && StringUtils.isBlank(desc)) throw new BusinessException(ResultCode.PARAM_NULL);
		int code = roledao.updateApiRole(map);
		if(code > 0){
			result = "修改成功";
		}else{
			result = "修改失败";
			throw new BusinessException(ResultCode.FAILURE);
		}
		return result;
	}
	// 分页查询API角色
	public Map<String, Object> selApiRoleByPage(int index, int pageSize) throws BusinessException{
		QueryResult<Map<String,Object>> result = new QueryResult<Map<String,Object>>();
		Map<String,Object> mapResult = new HashMap<String,Object>();
		if(index <= 0) throw new BusinessException(ResultCode.PARAM_ERROR);
		int pageCount = 0;
		int page = (index - 1) * pageSize;
		List<ARoleUser> list = roledao.selApiRole();
		if(list != null  && list.size() > 0){
			pageCount = list.size() % pageSize == 0 ? list.size() / pageSize : list.size() / pageSize + 1;
		}
		List<ARoleUser> listPage = roledao.selApiRoleByPage(page, pageSize);
		if(listPage != null  && listPage.size() > 0){
			List<Map<String,Object>>  listMap = new ArrayList<Map<String,Object>>();
			for(int i = 0,len = listPage.size(); i < len;i++){
				Map<String,Object> mapList = new HashMap<String,Object>();
				mapList.put("id", listPage.get(i).getRoleid());
				mapList.put("name", listPage.get(i).getName());
				mapList.put("identity", listPage.get(i).getIdentity());
				mapList.put("desc", listPage.get(i).getDesc());
				mapList.put("deleteFlag", listPage.get(i).getDeleteFlag());
				listMap.add(mapList);
			}
			result.setTotalRowNum(pageCount);
			result.setResultList(listMap);
			mapResult.put("head", heads);
			mapResult.put("data", result);
		}else{
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		}
		return mapResult;
	}
	/*警友通*/
	// 删除JYT角色
	public String updateJYTRoleByFlag(int  id,Boolean deleteFlag) throws BusinessException{
		int code = 0;
		String result = null;
		if(deleteFlag){
			code = roledao.updateJYTRoleFlag(id);
		}else{
			code = roledao.updateJYTRoleByFlag(id);
		}
		if(code > 0){
			result = "删除成功";
		}else{
			result = "删除失败";
			throw new BusinessException(ResultCode.FAILURE);
		}
		return result;
	}
	// 修改JYT角色
	public String updateJYTRole(String name, String identity, String desc, int id) throws BusinessException{
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("identity", identity);
		map.put("desc", desc);
		map.put("id", id);
		String result = null;
		if(StringUtils.isBlank(name) && StringUtils.isBlank(identity) && StringUtils.isBlank(desc)) throw new BusinessException(ResultCode.PARAM_NULL);
		int code = roledao.updateJYTRole(map);
		if(code > 0){
			result = "修改成功";
		}else{
			result = "修改失败";
			throw new BusinessException(ResultCode.FAILURE);
		}
		return result;
	}
	// 分页查询JYT角色
	public Map<String, Object> selJYTRoleByPage(int index, int pageSize) throws BusinessException{
		QueryResult<Map<String,Object>> result = new QueryResult<Map<String,Object>>();
		Map<String,Object> mapResult = new HashMap<String,Object>();
		if(index <= 0) throw new BusinessException(ResultCode.PARAM_ERROR);
		int pageCount = 0;
		int page = (index - 1) * pageSize;
		List<ARoleUser> list = roledao.selJYTRole();
		if(list != null  && list.size() > 0){
			pageCount = list.size() % pageSize == 0 ? list.size() / pageSize : list.size() / pageSize + 1;
		}
		List<ARoleUser> listPage = roledao.selJYTRoleByPage(page, pageSize);
		if(listPage != null  && listPage.size() > 0){
			List<Map<String,Object>>  listMap = new ArrayList<Map<String,Object>>();
			for(int i = 0,len = listPage.size(); i < len;i++){
				Map<String,Object> mapList = new HashMap<String,Object>();
				mapList.put("id", listPage.get(i).getRoleid());
				mapList.put("name", listPage.get(i).getName());
				mapList.put("identity", listPage.get(i).getIdentity());
				mapList.put("desc", listPage.get(i).getDesc());
				mapList.put("deleteFlag", listPage.get(i).getDeleteFlag());
				listMap.add(mapList);
			}
			result.setTotalRowNum(pageCount);
			result.setResultList(listMap);
			mapResult.put("head", heads);
			mapResult.put("data", result);
		}else{
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		}
		return mapResult;
	}

	

}
