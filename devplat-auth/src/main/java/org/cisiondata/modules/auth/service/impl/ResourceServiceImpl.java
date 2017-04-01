package org.cisiondata.modules.auth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.abstr.service.impl.GenericServiceImpl;
import org.cisiondata.modules.auth.dao.ResourceIntegrationDAO;
import org.cisiondata.modules.auth.entity.Resource;
import org.cisiondata.modules.auth.service.IResourceService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("resourceService")
public class ResourceServiceImpl extends GenericServiceImpl<Resource, Long> implements IResourceService {
	
	@javax.annotation.Resource(name = "resourceIntegrationDAO")
	private ResourceIntegrationDAO resourceIntegrationDAO = null;
	
	private static Map<String, Resource> resourcesCache = null;
	
	@PostConstruct
	public void postConstruct() {
		resourcesCache = new HashMap<String, Resource>();
		initResourcesCache();
	}
	
	@Override
	public GenericDAO<Resource, Long> obtainDAOInstance() {
		return resourceIntegrationDAO;
	}
	
	@Override
	public String readIdentityById(Long id) throws BusinessException {
		Resource resource = resourceIntegrationDAO.readDataByPK(id);
		return null == resource ? null : resource.getIdentity();
	}
	
	@Override
	public String readIdentityByUrl(String url) throws BusinessException {
		Resource resource = resourcesCache.get(url);
		if (null != resource) return resource.getIdentity();
		Query query = new Query();
		query.addCondition("url", url);
		query.addCondition("deleteFlag", false);
		resource = resourceIntegrationDAO.readDataByCondition(query);
		if (null == resource) return null;
		resourcesCache.put(resource.getUrl(), resource);
		return resource.getIdentity();
	}
	
	private void initResourcesCache() {
		Query query = new Query();
		query.addCondition("deleteFlag", false);
		List<Resource> resources = resourceIntegrationDAO.readDataListByCondition(query);
		for (int i = 0, len = resources.size(); i < len; i++) {
			Resource resource = resources.get(i);
			resourcesCache.put(resource.getUrl(), resource);
		}
	}

}
