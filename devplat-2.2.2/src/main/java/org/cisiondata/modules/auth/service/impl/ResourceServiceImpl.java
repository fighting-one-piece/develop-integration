package org.cisiondata.modules.auth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.abstr.service.impl.GenericServiceImpl;
import org.cisiondata.modules.auth.dao.ResourceIntegrationDAO;
import org.cisiondata.modules.auth.entity.AuthResource;
import org.cisiondata.modules.auth.service.IResourceService;
import org.cisiondata.utils.ds.DataSource;
import org.cisiondata.utils.ds.TargetDataSource;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("authResourceService")
public class ResourceServiceImpl extends GenericServiceImpl<AuthResource, Long> implements IResourceService {
	
	@javax.annotation.Resource(name = "authResourceIntegrationDAO")
	private ResourceIntegrationDAO resourceIntegrationDAO = null;
	
	private static Map<String, AuthResource> resourcesCache = null;
	
	@PostConstruct
	public void postConstruct() {
		resourcesCache = new HashMap<String, AuthResource>();
		initResourcesCache();
	}
	
	@Override
	public GenericDAO<AuthResource, Long> obtainDAOInstance() {
		return resourceIntegrationDAO;
	}
	
	@Override
	@TargetDataSource(DataSource.SLAVE1)
	public String readIdentityById(Long id) throws BusinessException {
		AuthResource resource = resourceIntegrationDAO.readDataByPK(id);
		return null == resource ? null : resource.getIdentity();
	}
	
	@Override
	@TargetDataSource(DataSource.SLAVE1)
	public String readIdentityByUrl(String url,Integer type) throws BusinessException {
		AuthResource resource = resourcesCache.get(url+type);
		if (null != resource) return resource.getIdentity();
		Query query = new Query();
		query.addCondition("url", url);
		query.addCondition("type", type);
		query.addCondition("deleteFlag", false);
		resource = resourceIntegrationDAO.readDataByCondition(query);
		if (null == resource) return null;
		resourcesCache.put(resource.getUrl()+resource.getType(), resource);
		return resource.getIdentity();
	}
	
	private void initResourcesCache() {
		Query query = new Query();
		query.addCondition("deleteFlag", false);
		List<AuthResource> resources = resourceIntegrationDAO.readDataListByCondition(query);
		for (int i = 0, len = resources.size(); i < len; i++) {
			AuthResource resource = resources.get(i);
			resourcesCache.put(resource.getUrl()+resource.getType(), resource);
		}
	}

	@Override
	@TargetDataSource(DataSource.SLAVE1)
	public boolean readResource(String url, Integer type) throws BusinessException {
		AuthResource resource = resourcesCache.get(url+type);
		if (null != resource) return true;
		Query query = new Query();
		query.addCondition("url", url);
		query.addCondition("type", type);
		query.addCondition("deleteFlag", false);
		resource = resourceIntegrationDAO.readDataByCondition(query);
		if (null == resource) return false;
		resourcesCache.put(resource.getUrl()+resource.getType(), resource);
		return true;
	}

	@Override
	@TargetDataSource(DataSource.SLAVE1)
	public AuthResource readResourceFromCache(String url, Integer type) throws BusinessException {
		AuthResource resource = resourcesCache.get(url+type);
		if (null != resource) return resource;
		Query query = new Query();
		query.addCondition("url", url);
		query.addCondition("type", type);
		query.addCondition("deleteFlag", false);
		resource = resourceIntegrationDAO.readDataByCondition(query);
		if (null == resource) return null;
		resourcesCache.put(resource.getUrl()+resource.getType(), resource);
		return resource;
	}

	
	
}
