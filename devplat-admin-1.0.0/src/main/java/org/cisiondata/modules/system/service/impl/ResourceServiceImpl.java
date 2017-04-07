package org.cisiondata.modules.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.auth.entity.Resource;
import org.cisiondata.modules.system.dao.ResourceAttributeDAO;
import org.cisiondata.modules.system.dao.ResourceDAO;
import org.cisiondata.modules.system.service.IResourceService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("aresourceService")
public class ResourceServiceImpl implements IResourceService {

	@javax.annotation.Resource(name = "aresourceDAO")
	private ResourceDAO resourceDAO;

	@javax.annotation.Resource(name = "aresourceAttributeDAO")
	private ResourceAttributeDAO resourceAttributeDAO;

	// 添加资源
	@Override
	@Transactional
	public int addResource(String name, Integer type, String identity,
			String url, String icon, Integer priority, Long parentId,
			Boolean deleteFlag) throws BusinessException {
		if (StringUtils.isBlank(name) || null == type
				|| StringUtils.isBlank(identity) || StringUtils.isBlank(url))
			throw new BusinessException(ResultCode.PARAM_ERROR);
		Resource resource = new Resource();
		resource.setName(name.trim());
		resource.setType(type);
		resource.setIdentity(identity.trim());
		resource.setUrl(url.trim());
		if (icon != null)
			resource.setIcon(icon.trim());
		resource.setPriority(priority);
		resource.setParentId(parentId);
		if (null != deleteFlag)
			resource.setDeleteFlag(deleteFlag);
		int status = resourceDAO.addResource(resource);
		return status;
	}

	// 修改资源
	@Override
	public void editResource(Long id,String name, Integer type, String identity,
			String url, String icon, Integer priority, Long parentId,
			Boolean deleteFlag)
			throws BusinessException {
		if (StringUtils.isBlank(name) || StringUtils.isBlank(identity) || StringUtils.isBlank(url) 
				|| StringUtils.isBlank(icon) || null == id || null == type) {
			throw new BusinessException(ResultCode.PARAM_ERROR);
		}
		Resource resource = new Resource();
		resource.setId(id);
		resource.setName(name.trim());
		resource.setType(type);
		resource.setIdentity(identity.trim());
		resource.setUrl(url.trim());
		if (icon != null)
			resource.setIcon(icon.trim());
		resource.setPriority(priority);
		resource.setParentId(parentId);
		if (null != deleteFlag)
			resource.setDeleteFlag(deleteFlag);
		resourceDAO.updateResourceById(resource);
		
	}

	// 查询所有资源
	@Override
	public QueryResult<Resource> qureyAllResource(Integer page, Integer pageSize)
			throws BusinessException {
		if (page < 1 || pageSize < 1)
			throw new BusinessException(ResultCode.PARAM_ERROR);
		Map<String, Object> params = new HashMap<String, Object>();
		int count = resourceDAO.findCountByCondition(params);
		int pageCount = count % pageSize == 0 ? count / pageSize : count
				/ pageSize + 1;
		int begin = (page - 1) * pageSize;
		params = new HashMap<String, Object>();
		params.put("begin", begin);
		params.put("pageSize", pageSize);
		List<Resource> list = resourceDAO.findByCondition(params);
		if (list.size() == 0)
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		QueryResult<Resource> qr = new QueryResult<Resource>();
		qr.setResultList(list);
		qr.setTotalRowNum(pageCount);
		return qr;
	}

	// 通过ID启用、关闭资源
	@Override
	public void deleteResource(Long id, Boolean deleteFlag)
			throws BusinessException {
		Resource resource = new Resource();
		resource.setId(id);
		if (null != deleteFlag)
			resource.setDeleteFlag(deleteFlag);
		resourceDAO.updateResourceById(resource);
	}

}
