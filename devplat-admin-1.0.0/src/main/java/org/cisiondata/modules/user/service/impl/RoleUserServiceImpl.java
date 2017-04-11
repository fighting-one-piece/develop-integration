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
import org.cisiondata.modules.user.entity.RoleUser;
import org.cisiondata.modules.user.service.IRoleUserService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;
@Service("roleUserService")
public class RoleUserServiceImpl implements IRoleUserService{
	@Resource(name="roleUserDAO")
	private RoleUserDAO roledao = null;
	
	public String addRole(String name, String identity, String desc) throws BusinessException{
		int code = roledao.selRoleByName(name);
		String result = null;
		if(code <= 0){
			RoleUser role = new RoleUser();
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
	public String updateRoleByFlag(String name) throws BusinessException{
		int code = roledao.updateRoleByFlag(name);
		String result = null;
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
	public Map<String,Object> selRoleByPage(int index, int pageSize) {
		QueryResult<Map<String,Object>> result = new QueryResult<Map<String,Object>>();
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object> mapHead = new HashMap<String,Object>();
		mapHead.put("roleid", "ID");
		mapHead.put("name", "角色名");
		mapHead.put("identity", "角色标识");
		mapHead.put("desc", "角色描述");
		mapHead.put("deleteFlag", "删除标识");
		if(index <= 0) throw new BusinessException(ResultCode.PARAM_ERROR);
		int pageCount = 0;
		int page = (index - 1) * pageSize;
		List<RoleUser> list = roledao.selRole();
		if(list != null  && list.size() > 0){
			pageCount = list.size() % pageSize == 0 ? list.size() / pageSize : list.size() / pageSize + 1;
		}
		List<RoleUser> listPage = roledao.selRoleByPage(page, pageSize);
		if(listPage != null  && listPage.size() > 0){
			List<Map<String,Object>>  listMap = new ArrayList<Map<String,Object>>();
			for(int i = 0,len = listPage.size(); i < len;i++){
				Map<String,Object> mapList = new HashMap<String,Object>();
				mapList.put("roleid", listPage.get(i).getRoleid());
				mapList.put("name", listPage.get(i).getName());
				mapList.put("identity", listPage.get(i).getIdentity());
				mapList.put("desc", listPage.get(i).getDesc());
				mapList.put("deleteFlag", listPage.get(i).getDeleteFlag());
				listMap.add(mapList);
			}
			result.setTotalRowNum(pageCount);
			result.setResultList(listMap);
			mapResult.put("head", mapHead);
			mapResult.put("data", result);
		}else{
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		}
		return mapResult;
	}

}
