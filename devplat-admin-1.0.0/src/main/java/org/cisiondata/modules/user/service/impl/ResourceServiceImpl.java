package org.cisiondata.modules.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.auth.entity.Resource;
import org.cisiondata.modules.user.dao.ResourceAttributeDAO;
import org.cisiondata.modules.user.dao.ResourceDAO;
import org.cisiondata.modules.user.service.IResourceService;
import org.cisiondata.modules.user.utils.Head;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("aresourceService")
public class ResourceServiceImpl implements IResourceService, InitializingBean{

	@javax.annotation.Resource(name = "aresourceDAO")
	private ResourceDAO resourceDAO;

	@javax.annotation.Resource(name = "aresourceAttributeDAO")
	private ResourceAttributeDAO resourceAttributeDAO;
	
	private List<Object> heads = new ArrayList<Object>();
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Head head = new Head();
		head.setField("id");
		head.setFieldName("ID");
		heads.add(head);
		head = new Head();
		head.setField("type");
		head.setFieldName("类型");
		heads.add(head);
		head = new Head();
		head.setField("name");
		head.setFieldName("名称");
		heads.add(head);
		head = new Head();
		head.setField("identity");
		head.setFieldName("标识");
		heads.add(head);
		head = new Head();
		head.setField("url");
		head.setFieldName("URL");
		heads.add(head);
		head = new Head();
		head.setField("icon");
		head.setFieldName("图标");
		heads.add(head);
		head = new Head();
		head.setField("priority");
		head.setFieldName("优先权");
		heads.add(head);
		head = new Head();
		head.setField("parentId");
		head.setFieldName("父资源ID");
		heads.add(head);
		head = new Head();
		head.setField("deleteFlag");
		head.setFieldName("是否删除");
		heads.add(head);
		
	}
	
	// 添加资源
	@Override
	public void addResource(String name, Integer type, String identity,
			String url) throws BusinessException {
		if (StringUtils.isBlank(name) || null == type
				|| StringUtils.isBlank(identity) || StringUtils.isBlank(url))
			throw new BusinessException(ResultCode.PARAM_ERROR);
		Resource resource = new Resource();
		resource.setName(name.trim());
		resource.setType(type);
		resource.setIdentity(identity.trim());
		resource.setUrl(url.trim());
		resource.setDeleteFlag(false);
		resourceDAO.addResource(resource);
	}
	// 修改资源
	@Override
	public void editResource(Long id,String name, Integer type, String identity,
			String url)throws BusinessException {
		if (StringUtils.isBlank(name) || StringUtils.isBlank(identity) || StringUtils.isBlank(url) 
				 || null == id || null == type) {
			throw new BusinessException(ResultCode.PARAM_ERROR);
		}
		Resource resource = new Resource();
		resource.setId(id);
		resource.setName(name.trim());
		resource.setType(type);
		resource.setIdentity(identity.trim());
		resource.setUrl(url.trim());
		resource.setDeleteFlag(null);
		resourceDAO.updateResourceById(resource);
		
	}
	
	// 查询所有资源
	@Override
	public Map<String, Object> qureyAllResource(Integer page, Integer pageSize,Integer type)
			throws BusinessException {
		 Map<String, Object> result = new HashMap<String, Object>();
		if (page < 1 || pageSize < 1)
			throw new BusinessException(ResultCode.PARAM_ERROR);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		int count = resourceDAO.findCountByCondition(params);
		int pageCount = count % pageSize == 0 ? count / pageSize : count
				/ pageSize + 1;
		int begin = (page - 1) * pageSize;
		params = new HashMap<String, Object>();
		params.put("begin", begin);
		params.put("pageSize", pageSize);
		params.put("type", type);
		params.put("orderBy", "ID");
		List<Resource> list = resourceDAO.findByCondition(params);
		if (list.size() == 0)
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		QueryResult<Resource> data = new QueryResult<Resource>();
		data.setResultList(list);
		data.setTotalRowNum(pageCount);
		result.put("head", heads);
		result.put("data", data);
		return result;
	}

	// 通过ID启用、关闭资源
	@Override
	public void deleteResource(Long id, Boolean deleteFlag,Integer type)
			throws BusinessException {
		Resource resource = new Resource();
		resource.setId(id);
		resource.setType(type);
		if (null != deleteFlag)
			resource.setDeleteFlag(deleteFlag);
		resourceDAO.updateResourceById(resource);
	}

}
