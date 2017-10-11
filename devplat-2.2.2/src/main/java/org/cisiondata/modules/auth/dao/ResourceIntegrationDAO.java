package org.cisiondata.modules.auth.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cisiondata.modules.abstr.dao.impl.GenericDAOImpl;
import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.auth.dao.utils.EntityAttributeUtils;
import org.cisiondata.modules.auth.entity.AuthResource;
import org.cisiondata.modules.auth.entity.AuthResourceAttribute;
import org.cisiondata.modules.auth.entity.AuthUserAttribute;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("authResourceIntegrationDAO")
public class ResourceIntegrationDAO extends GenericDAOImpl<AuthResource, Long> {
	
	@javax.annotation.Resource(name = "authResourceDAO")
	private ResourceDAO authResourceDAO = null;
	
	@javax.annotation.Resource(name = "authResourceAttributeDAO")
	private ResourceAttributeDAO authResourceAttributeDAO = null;

	@Override
	public void insert(AuthResource resource) throws DataAccessException {
		authResourceDAO.insert(resource);
		List<AuthResourceAttribute> attributes = EntityAttributeUtils.extractAttributes(resource, AuthResourceAttribute.class);
		if (attributes.size() > 0) authResourceAttributeDAO.insertBatch(attributes);
	}
	
	@Override
	public void insert(List<AuthResource> resources) throws DataAccessException {
		for (int i = 0, len = resources.size(); i < len; i++) {
			insert(resources.get(i));
		}
	}
	
	@Override
	public void update(AuthResource resource) throws DataAccessException {
		if (EntityAttributeUtils.isNeedUpdateEntity(resource)) authResourceDAO.update(resource);
		List<AuthResourceAttribute> attributes = EntityAttributeUtils.extractAttributes(resource, AuthUserAttribute.class);
		if (null == attributes || attributes.size() == 0) return;
		Query query = new Query();
		query.addCondition("userId", resource.getId());
		List<AuthResourceAttribute> dbAttributes = authResourceAttributeDAO.readDataListByCondition(query);
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0, len = dbAttributes.size(); i < len; i++) {
			AuthResourceAttribute attribute = dbAttributes.get(i);
			map.put(attribute.getKey(), attribute.getValue());
		}
		List<AuthResourceAttribute> insertAttributes = new ArrayList<AuthResourceAttribute>();
		List<AuthResourceAttribute> updateAttributes = new ArrayList<AuthResourceAttribute>();
		for (int i =0, len = attributes.size(); i < len; i++) {
			AuthResourceAttribute attribute = attributes.get(i);
			Object value = map.get(attribute.getKey());
			if (null == value) {
				insertAttributes.add(attribute);
			} else {
				if (value.equals(attribute.getValue())) continue;
				updateAttributes.add(attribute);
			}
		}
		if (insertAttributes.size() > 0) authResourceAttributeDAO.insertBatch(insertAttributes);
		if (updateAttributes.size() > 0) {
			for (int i = 0, len = updateAttributes.size(); i < len; i++) {
				authResourceAttributeDAO.update(updateAttributes.get(i));
			}
		}
	}
	
	@Override
	public AuthResource readDataByPK(Long pk) throws DataAccessException {
		AuthResource resource = authResourceDAO.readDataByPK(pk);
		if(null == resource) return resource;
		EntityAttributeUtils.fillEntity(resource.getAttributes(), resource);
		return resource;
	}
	
	@Override
	public AuthResource readDataByCondition(Query query) throws DataAccessException {
		AuthResource resource = authResourceDAO.readDataByCondition(query);
		if(null == resource) return resource;
		EntityAttributeUtils.fillEntity(resource.getAttributes(), resource);
		return resource;
	}
	
	@Override
	public List<AuthResource> readDataListByCondition(Query query) throws DataAccessException {
		List<AuthResource> resources = authResourceDAO.readDataListByCondition(query);
		for (int i = 0, len = resources.size(); i < len; i++) {
			AuthResource resource = resources.get(i);
			EntityAttributeUtils.fillEntity(resource.getAttributes(), resource);
		}
		return resources;
	}
	
}
