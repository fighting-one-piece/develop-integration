package org.cisiondata.modules.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.user.dao.APermissionDao;
import org.cisiondata.modules.user.dao.AResourceDao;
import org.cisiondata.modules.user.entity.APermission;
import org.cisiondata.modules.user.entity.AResource;
import org.cisiondata.modules.user.service.IAResourceService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("aResourceService")
public class AResourceServiceImpl implements IAResourceService {

	@Resource(name = "aResourceDao")
	private AResourceDao aResourceDao;
	
	@Resource(name = "aPermissionDao")
	private APermissionDao aPermissionDao;

	@Override
	public List<AResource> findAll() throws BusinessException {
		return aResourceDao.findAllAResource();
	}

	@Override
	public int addLink(AResource aResource) throws BusinessException {
		return aResourceDao.addAResource(aResource);
	}

	@Override
	public List<AResource> findByDeleteFlag() throws BusinessException {
		return aResourceDao.findByDeleteFlag();
	}

	@Override
	@Transactional
	public int deleteById(Long id) throws BusinessException {
		aResourceDao.updateParentIdByParentId(id);
		return aResourceDao.updateDeleteFlagById(id);
	}

	@Override
	public List<AResource> findMenuLink() throws BusinessException {
		return aResourceDao.findByType(2);
	}

	@Override
	public AResource findById(Long id) throws BusinessException {
		return aResourceDao.findById(id);
	}

	@Override
	public int updateById(AResource aResource) throws BusinessException {
		
		return aResourceDao.updateAResource(aResource);
	}

	@Override
	public List<AResource> findByType(int type) throws BusinessException {
		return aResourceDao.findByType(type);
	}

	@Override
	public Map<String, Object> findByPage( int page,int pageSize) throws BusinessException {
		int count = aResourceDao.findCount();
		int pageCount = count % pageSize == 0 ? count/pageSize : count/pageSize + 1;
		int begin = (page-1) * pageSize;
		List<AResource> resourceList = aResourceDao.findByPage(begin, pageSize);
		Map<String, Object> map = new HashMap<String ,Object>();
		map.put("data", resourceList);
		map.put("pageNum", page);
		map.put("pageCount", pageCount);
		return map;
	}

	@Override
	public List<AResource> findAdminMenu(AResource aResource) throws BusinessException {
		aResource.setType(1);
		return aResourceDao.findByTypeAndParentId(aResource);
	}

	@Override
	public List<AResource> findResourceTree() throws BusinessException {
//		List<AResource> list = new ArrayList<AResource>();
		//获取第一级目录
		List<AResource> listFather = aResourceDao.findByParentId("");
		if (listFather.size() == 0){
			return new ArrayList<AResource>();
		}
		for (AResource aResourceOne : listFather){
			String parentId = String.valueOf(aResourceOne.getId());
			if (parentId == null) continue;
			aResourceOne = getSonResource(aResourceOne);
		}
		return listFather;
	}
	private AResource getSonResource(AResource resource){
		if (resource.getId() == null){
			return null;
		}
		List<AResource> list = aResourceDao.findByParentId(String.valueOf(resource.getId()));
		if (list.size() == 0){
			return resource;
		}
		for (AResource aResource : list){
			aResource = getSonResource(aResource);
		}
		resource.setChildren(list);
		return resource;
		
	}

	@Override
	@Transactional
	public void updatePermission(String param) throws BusinessException {
		String[] params = param.split("\\|");
		for (int i = 0; i < params.length ; i++){
			String[] permissionArr = params[i].split(",");
			APermission permission = new APermission();
			permission.setResourceId(Long.parseLong(permissionArr[2]));
			permission.setPrincipalId(Long.parseLong(permissionArr[3]));
			permission.setPrincipalType(Integer.parseInt(permissionArr[4]));
			permission.setAuthStatus(APermission.EXTENDS_NO);
			APermission oldPermission = aPermissionDao.findPermission(permission);
			if ("true".equals(permissionArr[0])) {
				permission.setPermisssion(APermission.AUTH_READ, true);
			}
			if ("true".equals(permissionArr[1])) {
				permission.setPermisssion(APermission.AUTH_UPDATE, true);
				permission.setPermisssion(APermission.AUTH_DELETE, true);
				permission.setPermisssion(APermission.AUTH_CREATE, true);
				
			}
			if (oldPermission == null || oldPermission.getId() == null) {
				aPermissionDao.addPermission(permission);
			} else {
				aPermissionDao.updatePermission(permission);
			}
		}
	}

	@Override
	public Map<String,Object> findPermissions(APermission permission) throws BusinessException {
		List<APermission> list = aPermissionDao.findByPrincipalTypeAndPrincipalId(permission);
		Map<String,Object> map = new HashMap<String,Object>();
		for (APermission aPermission : list ) {
			List<String> authStatus = aPermission.obtainOperateIdentities();
			if(!authStatus.isEmpty()){
				map.put(String.valueOf(aPermission.getResourceId()), authStatus);
			}
		}
		return map;
		
	}

}
