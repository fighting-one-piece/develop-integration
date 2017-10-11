package org.cisiondata.modules.auth.dao;

import java.util.List;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.auth.entity.AuthResourceAttribute;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("authResourceAttributeDAO")
public interface ResourceAttributeDAO extends GenericDAO<AuthResourceAttribute, Long> {
	
	/**
	 * 批量插入资源属性
	 * @param attributes
	 * @throws DataAccessException
	 */
	public void insertBatch(List<AuthResourceAttribute> attributes) throws DataAccessException;
	
}
