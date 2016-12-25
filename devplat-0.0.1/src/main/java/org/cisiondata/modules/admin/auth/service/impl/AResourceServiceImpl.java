package org.cisiondata.modules.admin.auth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.admin.auth.dao.AResourceDao;
import org.cisiondata.modules.admin.auth.entity.AResource;
import org.cisiondata.modules.admin.auth.service.IAResourceService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("aResourceService")
public class AResourceServiceImpl implements IAResourceService {

	@Resource(name = "aResourceDao")
	private AResourceDao aResourceDao;

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
		int end = begin + pageSize;
		List<AResource> resourceList = aResourceDao.findByPage(begin, end);
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

}
