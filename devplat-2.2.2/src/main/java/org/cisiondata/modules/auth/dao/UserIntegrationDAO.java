package org.cisiondata.modules.auth.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.dao.impl.GenericDAOImpl;
import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.auth.dao.utils.EntityAttributeUtils;
import org.cisiondata.modules.auth.entity.AuthUser;
import org.cisiondata.modules.auth.entity.AuthUserAttribute;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("authUserIntegrationDAO")
public class UserIntegrationDAO extends GenericDAOImpl<AuthUser, Long> {
	
	@Resource(name = "authUserDAO")
	private UserDAO userDAO = null;
	
	@Resource(name = "authUserAttributeDAO")
	private UserAttributeDAO userAttributeDAO = null;

	@Override
	public void insert(AuthUser user) throws DataAccessException {
		userDAO.insert(user);
		List<AuthUserAttribute> attributes = EntityAttributeUtils.extractAttributes(user, AuthUserAttribute.class);
		if (attributes.size() > 0) userAttributeDAO.insertBatch(attributes);
	}
	
	@Override
	public void insert(List<AuthUser> users) throws DataAccessException {
		for (int i = 0, len = users.size(); i < len; i++) {
			insert(users.get(i));
		}
	}
	
	@Override
	public void update(AuthUser user) throws DataAccessException {
		if (EntityAttributeUtils.isNeedUpdateEntity(user)) userDAO.update(user);
		List<AuthUserAttribute> attributes = EntityAttributeUtils.extractAttributes(user, AuthUserAttribute.class);
		if (null == attributes || attributes.size() == 0) return;
		Query query = new Query();
		query.addCondition("userId", user.getId());
		List<AuthUserAttribute> dbAttributes = userAttributeDAO.readDataListByCondition(query);
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0, len = dbAttributes.size(); i < len; i++) {
			AuthUserAttribute attribute = dbAttributes.get(i);
			map.put(attribute.getKey(), attribute.getValue());
		}
		List<AuthUserAttribute> insertAttributes = new ArrayList<AuthUserAttribute>();
		List<AuthUserAttribute> updateAttributes = new ArrayList<AuthUserAttribute>();
		for (int i =0, len = attributes.size(); i < len; i++) {
			AuthUserAttribute attribute = attributes.get(i);
			Object value = map.get(attribute.getKey());
			if (null == value) {
				insertAttributes.add(attribute);
			} else {
				if (value.equals(attribute.getValue())) continue;
				updateAttributes.add(attribute);
			}
		}
		if (insertAttributes.size() > 0) userAttributeDAO.insertBatch(insertAttributes);
		if (updateAttributes.size() > 0) {
			for (int i = 0, len = updateAttributes.size(); i < len; i++) {
				userAttributeDAO.update(updateAttributes.get(i));
			}
		}
	}
	
	@Override
	public AuthUser readDataByPK(Long pk) throws DataAccessException {
		AuthUser user = userDAO.readDataByPK(pk);
		EntityAttributeUtils.fillEntity(user.getAttributes(), user);
		return user;
	}
	
	@Override
	public AuthUser readDataByCondition(Query query) throws DataAccessException {
		AuthUser user = userDAO.readDataByCondition(query);
		if (null == user || null == user.getId()) return null;
		EntityAttributeUtils.fillEntity(user.getAttributes(), user);
		return user;
	}
	
	@Override
	public List<AuthUser> readDataListByCondition(Query query) throws DataAccessException {
		List<AuthUser> users = userDAO.readDataListByCondition(query);
		for (int i = 0, len = users.size(); i < len; i++) {
			AuthUser user = users.get(i);
			EntityAttributeUtils.fillEntity(user.getAttributes(), user);
		}
		return users;
	}
	
	public AuthUser readDataByConditions(Query query) throws DataAccessException {
		AuthUser user = userDAO.readDataByConditions(query);
		if (null == user || null == user.getId()) return null;
		EntityAttributeUtils.fillEntity(user.getAttributes(), user);
		return user;
	}
	
	public Object readUserAttributeValue(Long userId, String attributeName) throws DataAccessException {
		Query query = new Query();
		query.addCondition("userId", userId);
		query.addCondition("key", attributeName);
		AuthUserAttribute attribute = userAttributeDAO.readDataByCondition(query);
		return null == attribute ? null : EntityAttributeUtils.extractValue(attribute.getType(), attribute.getValue());
	}
	
	public Long readUserIdByAttribute(String attributeName, Object attributeValue) throws DataAccessException {
		Query query = new Query();
		query.addCondition("key", attributeName);
		String[] valueAndKind = EntityAttributeUtils.extractValueAndKind(attributeValue);
		query.addCondition("value", valueAndKind[0]);
		query.addCondition("type", valueAndKind[1]);
		AuthUserAttribute attribute = userAttributeDAO.readDataByCondition(query);
		return null != attribute ? attribute.getUserId() : null;
	}
	
}
