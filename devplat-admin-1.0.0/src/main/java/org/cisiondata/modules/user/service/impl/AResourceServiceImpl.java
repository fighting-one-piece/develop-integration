package org.cisiondata.modules.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.user.dao.AResourcesDAO;
import org.cisiondata.modules.user.entity.APermission;
import org.cisiondata.modules.user.entity.AResources;
import org.cisiondata.modules.user.service.IAResourceService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("aResourceService")
public class AResourceServiceImpl implements IAResourceService {

	@Resource(name = "AResourcesDao")
	private AResourcesDAO resourceDao = null;

	// 查询全部资源
	public List<AResources> selResource() throws BusinessException {
		List<AResources> list = resourceDao.selResource();
		if (list.size() == 0)
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		return list;
	}

	// 查询已授权资源
	public List<AResources> selResourceById(Long roleId) throws BusinessException {
		List<AResources> list = resourceDao.selResourceById(roleId);
		if (list.size() == 0)
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		return list;
	}

	// 保存资源
	@SuppressWarnings("static-access")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String addPermission(String resourceId, Long roleId) throws BusinessException {
		List<String> list = stringToList(resourceId);
		String result = null;
		try {
			int code = resourceDao.delPermission(roleId);
			if(code >= 0 && list.size() == 0) result = "保存成功";
			if (code >= 0 && list.size() > 0) {
				for (int i = 0, len = list.size(); i < len; i++) {
					APermission ps = new APermission();
					ps.setResourceId(Long.valueOf(list.get(i)));
					ps.setPrincipalType(ps.PRINCIPAL_TYPE_ROLE);
					ps.setPrincipalId(roleId);
					resourceDao.addPermission(ps);
					result = "保存成功";
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

}
