package org.cisiondata.modules.auth.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.dao.impl.GenericDAOImpl;
import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.auth.dao.utils.EntityAttributeUtils;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.auth.entity.UserAttribute;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("userIntegrationDAO")
public class UserIntegrationDAO extends GenericDAOImpl<User, Long> {
	
	@Resource(name = "userDAO")
	private UserDAO userDAO = null;
	
	@Resource(name = "userAttributeDAO")
	private UserAttributeDAO userAttributeDAO = null;

	@Override
	public void insert(User user) throws DataAccessException {
		userDAO.insert(user);
		List<UserAttribute> attributes = EntityAttributeUtils.extractAttributes(user, UserAttribute.class);
		if (attributes.size() > 0) userAttributeDAO.insertBatch(attributes);
	}
	
	@Override
	public void insert(List<User> users) throws DataAccessException {
		for (int i = 0, len = users.size(); i < len; i++) {
			insert(users.get(i));
		}
	}
	
	@Override
	public void update(User user) throws DataAccessException {
		if (EntityAttributeUtils.isNeedUpdateEntity(user)) userDAO.update(user);
		List<UserAttribute> attributes = EntityAttributeUtils.extractAttributes(user, UserAttribute.class);
		if (null == attributes || attributes.size() == 0) return;
		Query query = new Query();
		query.addCondition("userId", user.getId());
		List<UserAttribute> dbAttributes = userAttributeDAO.readDataListByCondition(query);
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0, len = dbAttributes.size(); i < len; i++) {
			UserAttribute attribute = dbAttributes.get(i);
			map.put(attribute.getKey(), attribute.getValue());
		}
		List<UserAttribute> insertAttributes = new ArrayList<UserAttribute>();
		List<UserAttribute> updateAttributes = new ArrayList<UserAttribute>();
		for (int i =0, len = attributes.size(); i < len; i++) {
			UserAttribute attribute = attributes.get(i);
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
	public User readDataByPK(Long pk) throws DataAccessException {
		User user = userDAO.readDataByPK(pk);
		EntityAttributeUtils.fillEntity(user.getAttributes(), user);
		return user;
	}
	
	@Override
	public User readDataByCondition(Query query) throws DataAccessException {
		User user = userDAO.readDataByCondition(query);
		EntityAttributeUtils.fillEntity(user.getAttributes(), user);
		return user;
	}
	
	@Override
	public List<User> readDataListByCondition(Query query) throws DataAccessException {
		List<User> users = userDAO.readDataListByCondition(query);
		for (int i = 0, len = users.size(); i < len; i++) {
			User user = users.get(i);
			EntityAttributeUtils.fillEntity(user.getAttributes(), user);
		}
		return users;
	}
	
	public Object readUserAttributeValue(Long userId, String attributeName) throws DataAccessException {
		Query query = new Query();
		query.addCondition("userId", userId);
		query.addCondition("key", attributeName);
		UserAttribute attribute = userAttributeDAO.readDataByCondition(query);
		return null == attribute ? null : EntityAttributeUtils.extractValue(attribute.getType(), attribute.getValue());
	}
	
	public Long readUserIdByAttribute(String attributeName, Object attributeValue) throws DataAccessException {
		Query query = new Query();
		query.addCondition("key", attributeName);
		String[] valueAndKind = EntityAttributeUtils.extractValueAndKind(attributeValue);
		query.addCondition("value", valueAndKind[0]);
		query.addCondition("type", valueAndKind[1]);
		UserAttribute attribute = userAttributeDAO.readDataByCondition(query);
		return null != attribute ? attribute.getUserId() : null;
	}
	
}
