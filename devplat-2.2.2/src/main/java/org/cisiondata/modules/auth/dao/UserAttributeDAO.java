package org.cisiondata.modules.auth.dao;

import java.util.List;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.auth.entity.AuthUserAttribute;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("authUserAttributeDAO")
public interface UserAttributeDAO extends GenericDAO<AuthUserAttribute, Long> {
	
	/**
	 * 批量插入用户属性
	 * @param attributes
	 * @throws DataAccessException
	 */
	public void insertBatch(List<AuthUserAttribute> attributes) throws DataAccessException;
	
}
