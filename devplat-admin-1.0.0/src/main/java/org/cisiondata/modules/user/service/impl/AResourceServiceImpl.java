package org.cisiondata.modules.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.auth.entity.UserResource;
import org.cisiondata.modules.user.dao.AResourcesDAO;
import org.cisiondata.modules.user.entity.APermission;
import org.cisiondata.modules.user.entity.AResource;
import org.cisiondata.modules.user.entity.AUserResource;
import org.cisiondata.modules.user.service.IAResourceService;
import org.cisiondata.modules.user.utils.Head;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("aResourceService")
public class AResourceServiceImpl implements IAResourceService, InitializingBean {

	@Resource(name = "AResourcesDao")
	private AResourcesDAO resourceDao = null;
	private List<Object> heads = new ArrayList<Object>();

	@Override
	public void afterPropertiesSet() throws Exception {
		Head head = new Head();
//		head.setField("userId");
//		head.setFieldName("用户ID");
//		heads.add(head);
		head = new Head();
		head.setField("account");
		head.setFieldName("用户名");
		heads.add(head);
//		head = new Head();
//		head.setField("resourceId");
//		head.setFieldName("资源ID");
//		heads.add(head);
		head = new Head();
		head.setField("resourceName");
		head.setFieldName("资源名称");
		heads.add(head);
		head = new Head();
		head.setField("deleteFlag");
		head.setFieldName("停用");
		heads.add(head);
	}

	// 查询已授权资源
	public List<AResource> selResourceById(Long roleId) throws BusinessException {
		List<AResource> list = resourceDao.selResource();
		List<AResource> listNew = resourceDao.selResourceById(roleId);
		if (list.size() == 0)
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		if (listNew.size() > 0) {
			for (int i = 0, len = list.size(); i < len; i++) {
				for (int j = 0, lens = listNew.size(); j < lens; j++) {
					if (list.get(i).getId() == listNew.get(j).getId()) {
						list.get(i).setCheck(true);
					}
				}
			}
		}
		return list;
	}

	// 保存资源
	@SuppressWarnings("static-access")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String addPermission(String resourceId, Long roleId) throws BusinessException {
		List<String> list = stringToList(resourceId);
		List<APermission> listPS = new ArrayList<APermission>();
		String result = null;
		boolean isSuccess = false;
		try {
			int code = resourceDao.delPermission(roleId);
			if (code >= 0 && list.size() == 0)
				result = "保存成功";
			if (code >= 0 && list.size() > 0) {
				for (int i = 0, len = list.size(); i < len; i++) {
					APermission ps = new APermission();
					ps.setResourceId(Long.valueOf(list.get(i)));
					ps.setPrincipalType(ps.PRINCIPAL_TYPE_ROLE);
					ps.setPrincipalId(roleId);
					listPS.add(ps);
				}
				isSuccess = resourceDao.addPermission(listPS) > 0;
				if (isSuccess) {
					result = "保存成功";
				} else {
					result = "保存失败";
					throw new BusinessException(ResultCode.DATABASE_OPERATION_FAIL);
				}
			}
		} catch (Exception e) {
			result = "保存失败";
			new BusinessException(ResultCode.DATABASE_OPERATION_FAIL);
		}
		return result;
	}

	// 将字符串以逗号分隔后存入List
	public static List<String> stringToList(String str) {
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str, ",");
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		return list;
	}

	// 查询角色已授权资源
	public List<AResource> selResourceByUr(Long userId) throws BusinessException {
		List<AResource> list = resourceDao.selResource();
		List<AResource> listNew = resourceDao.selResourceByUr(userId);
		if (list.size() == 0)
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		if (listNew.size() > 0) {
			for (int i = 0, len = list.size(); i < len; i++) {
				for (int j = 0, lens = listNew.size(); j < lens; j++) {
					if (list.get(i).getId() == listNew.get(j).getId()) {
						list.get(i).setCheck(true);
					}
				}
			}
		}
		return list;
	}

	// 保存用户定制资源
	public String addResourceByUr(Long userId, String resourceId) throws BusinessException {
		List<String> list = stringToList(resourceId);
		List<UserResource> upUr = new ArrayList<UserResource>();
		String result = null;
		boolean isSuccess = false;
		int code = resourceDao.updateByUser(userId);
		if (code >= 0 && list.size() == 0)
			result = "保存成功";
		if (code >= 0 && list.size() > 0) {
			for (int i = 0, len = list.size(); i < len; i++) {
				UserResource ur = new UserResource();
				ur.setUserId(userId);
				ur.setResourceId(Long.valueOf(list.get(i)));
				ur.setPriority(3);
				upUr.add(ur);
			}
			if (upUr.size() > 0) {
				isSuccess = resourceDao.addUserResource(upUr) > 0;
			}
			if (isSuccess) {
				result = "保存成功";
			} else {
				result = "保存失败";
				throw new BusinessException(ResultCode.DATABASE_OPERATION_FAIL);
			}
		}
		return result;
	}

	// 分页查询--用户资源关系
	public Map<String, Object> selByUR(int index, int pageSize) throws BusinessException {
		QueryResult<Map<String, Object>> result = new QueryResult<Map<String, Object>>();
		Map<String, Object> mapResult = new HashMap<String, Object>();
		if (index <= 0)
			throw new BusinessException(ResultCode.PARAM_ERROR);
		int pageCount = 0;
		int page = (index - 1) * pageSize;
		List<AUserResource> list = resourceDao.selByUrPage();
		if (list != null && list.size() > 0) {
			pageCount = list.size() % pageSize == 0 ? list.size() / pageSize : list.size() / pageSize + 1;
		}
		List<AUserResource> listPage = resourceDao.selByUR(page, pageSize);
		if (listPage != null && listPage.size() > 0) {
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			for (int i = 0, len = listPage.size(); i < len; i++) {
				Map<String, Object> mapList = new HashMap<String, Object>();
				mapList.put("userId", listPage.get(i).getUserId());
				mapList.put("account", listPage.get(i).getAccount());
				mapList.put("resourceId", listPage.get(i).getResourceId());
				mapList.put("resourceName", listPage.get(i).getResourceName());
				mapList.put("deleteFlag", listPage.get(i).getDeleteFlag());
				listMap.add(mapList);
			}
			result.setTotalRowNum(pageCount);
			result.setResultList(listMap);
			mapResult.put("head", heads);
			mapResult.put("data", result);
		} else {
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		}
		return mapResult;
	}

	// 更新删除标识
	public String updateURByFlag(int userId, int resourceId, Boolean deleteFlag) {
		int code = 0;
		String result = null;
		if(deleteFlag){
			code = resourceDao.updateURFlag(userId, resourceId);
		}else{
			
			code = resourceDao.updateURByFlag(userId, resourceId);
		}
		if(code > 0){
			result = "更新成功";
		}else{
			result = "更新失败";
			throw new BusinessException(ResultCode.FAILURE);
		}
		return result;
	}

	// 分页查询--根据账号查询用户定制资源的关系
	public Map<String, Object> selUrByName(String name, int index, int pageSize) throws BusinessException {
		QueryResult<Map<String, Object>> result = new QueryResult<Map<String, Object>>();
		Map<String, Object> mapResult = new HashMap<String, Object>();
		if (index <= 0)
			throw new BusinessException(ResultCode.PARAM_ERROR);
		int pageCount = 0;
		int page = (index - 1) * pageSize;
		List<AUserResource> list = resourceDao.selUrByPage(name);
		if (list != null && list.size() > 0) {
			pageCount = list.size() % pageSize == 0 ? list.size() / pageSize : list.size() / pageSize + 1;
		}
		List<AUserResource> listPage = resourceDao.selUrByName(name, page, pageSize);
		if (listPage != null && listPage.size() > 0) {
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			for (int i = 0, len = listPage.size(); i < len; i++) {
				Map<String, Object> mapList = new HashMap<String, Object>();
				mapList.put("userId", listPage.get(i).getUserId());
				mapList.put("account", listPage.get(i).getAccount());
				mapList.put("resourceId", listPage.get(i).getResourceId());
				mapList.put("resourceName", listPage.get(i).getResourceName());
				mapList.put("deleteFlag", listPage.get(i).getDeleteFlag());
				listMap.add(mapList);
			}
			result.setTotalRowNum(pageCount);
			result.setResultList(listMap);
			mapResult.put("head", heads);
			mapResult.put("data", result);
		} else {
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		}
		return mapResult;
	}

	/** API */

	// 查询当前角色已授权的资源API
	public List<AResource> selApiResourceById(Long roleId) throws BusinessException {
		List<AResource> list = resourceDao.selApiResource();
		List<AResource> listNew = resourceDao.selApiResourceById(roleId);
		if (list.size() == 0)
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		if (listNew.size() > 0) {
			for (int i = 0, len = list.size(); i < len; i++) {
				for (int j = 0, lens = listNew.size(); j < lens; j++) {
					if (list.get(i).getId() == listNew.get(j).getId()) {
						list.get(i).setCheck(true);
					}
				}
			}
		}
		return list;
	}

	// 保存资源API
	@SuppressWarnings("static-access")
	public String addApiPermission(String resourceId, Long roleId) throws BusinessException {
		List<String> list = stringToList(resourceId);
		List<APermission> listPS = new ArrayList<APermission>();
		String result = null;
		boolean isSuccess = false;
		try {
			int code = resourceDao.delApiPermission(roleId);
			if (code >= 0 && list.size() == 0)
				result = "保存成功";
			if (code >= 0 && list.size() > 0) {
				for (int i = 0, len = list.size(); i < len; i++) {
					APermission ps = new APermission();
					ps.setResourceId(Long.valueOf(list.get(i)));
					ps.setPrincipalType(ps.PRINCIPAL_TYPE_API);
					ps.setPrincipalId(roleId);
					listPS.add(ps);
				}
				isSuccess = resourceDao.addPermission(listPS) > 0;
				if (isSuccess) {
					result = "保存成功";
				} else {
					result = "保存失败";
					throw new BusinessException(ResultCode.DATABASE_OPERATION_FAIL);
				}
			}
		} catch (Exception e) {
			result = "保存失败";
			new BusinessException(ResultCode.DATABASE_OPERATION_FAIL);
		}
		return result;
	}

	// 查询角色已授权资源
	public List<AResource> selApiResourceByUr(Long userId) throws BusinessException {
		List<AResource> list = resourceDao.selApiResource();
		List<AResource> listNew = resourceDao.selApiResourceByUr(userId);
		if (list.size() == 0)
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		if (listNew.size() > 0) {
			for (int i = 0, len = list.size(); i < len; i++) {
				for (int j = 0, lens = listNew.size(); j < lens; j++) {
					if (list.get(i).getId() == listNew.get(j).getId()) {
						list.get(i).setCheck(true);
					}
				}
			}
		}
		return list;
	}

	// 分页查询--用户资源关系API
	public Map<String, Object> selByApiUR(int index, int pageSize) throws BusinessException {
		QueryResult<Map<String, Object>> result = new QueryResult<Map<String, Object>>();
		Map<String, Object> mapResult = new HashMap<String, Object>();
		if (index <= 0)
			throw new BusinessException(ResultCode.PARAM_ERROR);
		int pageCount = 0;
		int page = (index - 1) * pageSize;
		List<AUserResource> list = resourceDao.selByApiUrPage();
		if (list != null && list.size() > 0) {
			pageCount = list.size() % pageSize == 0 ? list.size() / pageSize : list.size() / pageSize + 1;
		}
		List<AUserResource> listPage = resourceDao.selByApiUR(page, pageSize);
		if (listPage != null && listPage.size() > 0) {
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			for (int i = 0, len = listPage.size(); i < len; i++) {
				Map<String, Object> mapList = new HashMap<String, Object>();
				mapList.put("userId", listPage.get(i).getUserId());
				mapList.put("account", listPage.get(i).getAccount());
				mapList.put("resourceId", listPage.get(i).getResourceId());
				mapList.put("resourceName", listPage.get(i).getResourceName());
				mapList.put("deleteFlag", listPage.get(i).getDeleteFlag());
				listMap.add(mapList);
			}
			result.setTotalRowNum(pageCount);
			result.setResultList(listMap);
			mapResult.put("head", heads);
			mapResult.put("data", result);
		} else {
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		}
		return mapResult;
	}

	// 更新删除标识
	public String updateApiURByFlag(int userId, int resourceId, Boolean deleteFlag) {
		int code = 0;
		String result = null;
		if(deleteFlag){
			code = resourceDao.updateApiURFlag(userId, resourceId);
		}else{
			
			code = resourceDao.updateApiURByFlag(userId, resourceId);
		}
		if(code > 0){
			result = "更新成功";
		}else{
			result = "更新失败";
			throw new BusinessException(ResultCode.FAILURE);
		}
		return result;
	}

	// 新增资源API
	public String addApiUserResource(Long userId, String resourceId) throws BusinessException {
		List<String> list = stringToList(resourceId);
		List<UserResource> upUr = new ArrayList<UserResource>();
		String result = null;
		boolean isSuccess = false;
		int code = resourceDao.updateApiByUser(userId);
		if (code >= 0 && list.size() == 0)
			result = "保存成功";
		if (code >= 0 && list.size() > 0) {
			for (int i = 0, len = list.size(); i < len; i++) {
				UserResource ur = new UserResource();
				ur.setUserId(userId);
				ur.setResourceId(Long.valueOf(list.get(i)));
				ur.setPriority(5);
				upUr.add(ur);
			}
			if (upUr.size() > 0) {
				isSuccess = resourceDao.addApiUserResource(upUr) > 0;
			}
			if (isSuccess) {
				result = "保存成功";
			} else {
				result = "保存失败";
				throw new BusinessException(ResultCode.DATABASE_OPERATION_FAIL);
			}
		}
		return result;
	}

	/** 警友通 */
	// 查询全部资源
	public List<AResource> selJYTResource() throws BusinessException {
		List<AResource> list = resourceDao.selJYTResource();
		if (list.size() == 0)
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		return list;
	}

	// 查询已授权资源
	public List<AResource> selJYTResourceById(Long roleId) throws BusinessException {
		List<AResource> list = resourceDao.selJYTResource();
		List<AResource> listNew = resourceDao.selJYTResourceById(roleId);
		if (list.size() == 0)
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		if (listNew.size() > 0) {
			for (int i = 0, len = list.size(); i < len; i++) {
				for (int j = 0, lens = listNew.size(); j < lens; j++) {
					if (list.get(i).getId() == listNew.get(j).getId()) {
						list.get(i).setCheck(true);
					}
				}
			}
		}
		return list;
	}

	// 保存资源
	@SuppressWarnings("static-access")
	public String addJYTPermission(String resourceId, Long roleId) throws BusinessException {
		List<String> list = stringToList(resourceId);
		List<APermission> listPS = new ArrayList<APermission>();
		String result = null;
		boolean isSuccess = false;
		try {
			int code = resourceDao.delJYTPermission(roleId);
			if (code >= 0 && list.size() == 0)
				result = "保存成功";
			if (code >= 0 && list.size() > 0) {
				for (int i = 0, len = list.size(); i < len; i++) {
					APermission ps = new APermission();
					ps.setResourceId(Long.valueOf(list.get(i)));
					ps.setPrincipalType(ps.PRINCIPAL_TYPE_JYT);
					ps.setPrincipalId(roleId);
					listPS.add(ps);
				}
				isSuccess = resourceDao.addJYTPermission(listPS) > 0;
				if (isSuccess) {
					result = "保存成功";
				} else {
					result = "保存失败";
					throw new BusinessException(ResultCode.DATABASE_OPERATION_FAIL);
				}
			}
		} catch (Exception e) {
			result = "保存失败";
			new BusinessException(ResultCode.DATABASE_OPERATION_FAIL);
		}
		return result;
	}

}
