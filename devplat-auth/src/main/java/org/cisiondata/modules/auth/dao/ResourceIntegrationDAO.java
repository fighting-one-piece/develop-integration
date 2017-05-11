package org.cisiondata.modules.auth.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cisiondata.modules.abstr.dao.impl.GenericDAOImpl;
import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.auth.dao.utils.EntityAttributeUtils;
import org.cisiondata.modules.auth.entity.Resource;
import org.cisiondata.modules.auth.entity.ResourceAttribute;
import org.cisiondata.modules.auth.entity.UserAttribute;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("resourceIntegrationDAO")
public class ResourceIntegrationDAO extends GenericDAOImpl<Resource, Long> {
	
	@javax.annotation.Resource(name = "resourceDAO")
	private ResourceDAO resourceDAO = null;
	
	@javax.annotation.Resource(name = "resourceAttributeDAO")
	private ResourceAttributeDAO resourceAttributeDAO = null;

	@Override
	public void insert(Resource resource) throws DataAccessException {
		resourceDAO.insert(resource);
		List<ResourceAttribute> attributes = EntityAttributeUtils.extractAttributes(resource, ResourceAttribute.class);
		if (attributes.size() > 0) resourceAttributeDAO.insertBatch(attributes);
	}
	
	@Override
	public void insert(List<Resource> resources) throws DataAccessException {
		for (int i = 0, len = resources.size(); i < len; i++) {
			insert(resources.get(i));
		}
	}
	
	@Override
	public void update(Resource resource) throws DataAccessException {
		if (EntityAttributeUtils.isNeedUpdateEntity(resource)) resourceDAO.update(resource);
		List<ResourceAttribute> attributes = EntityAttributeUtils.extractAttributes(resource, UserAttribute.class);
		if (null == attributes || attributes.size() == 0) return;
		Query query = new Query();
		query.addCondition("userId", resource.getId());
		List<ResourceAttribute> dbAttributes = resourceAttributeDAO.readDataListByCondition(query);
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0, len = dbAttributes.size(); i < len; i++) {
			ResourceAttribute attribute = dbAttributes.get(i);
			map.put(attribute.getKey(), attribute.getValue());
		}
		List<ResourceAttribute> insertAttributes = new ArrayList<ResourceAttribute>();
		List<ResourceAttribute> updateAttributes = new ArrayList<ResourceAttribute>();
		for (int i =0, len = attributes.size(); i < len; i++) {
			ResourceAttribute attribute = attributes.get(i);
			Object value = map.get(attribute.getKey());
			if (null == value) {
				insertAttributes.add(attribute);
			} else {
				if (value.equals(attribute.getValue())) continue;
				updateAttributes.add(attribute);
			}
		}
		if (insertAttributes.size() > 0) resourceAttributeDAO.insertBatch(insertAttributes);
		if (updateAttributes.size() > 0) {
			for (int i = 0, len = updateAttributes.size(); i < len; i++) {
				resourceAttributeDAO.update(updateAttributes.get(i));
			}
		}
	}
	
	@Override
	public Resource readDataByPK(Long pk) throws DataAccessException {
		Resource resource = resourceDAO.readDataByPK(pk);
		if(null == resource) return resource;
		EntityAttributeUtils.fillEntity(resource.getAttributes(), resource);
		return resource;
	}
	
	@Override
	public Resource readDataByCondition(Query query) throws DataAccessException {
		Resource resource = resourceDAO.readDataByCondition(query);
		if(null == resource) return resource;
		EntityAttributeUtils.fillEntity(resource.getAttributes(), resource);
		return resource;
	}
	
	@Override
	public List<Resource> readDataListByCondition(Query query) throws DataAccessException {
		List<Resource> resources = resourceDAO.readDataListByCondition(query);
		for (int i = 0, len = resources.size(); i < len; i++) {
			Resource resource = resources.get(i);
			EntityAttributeUtils.fillEntity(resource.getAttributes(), resource);
		}
		return resources;
	}
	
}
