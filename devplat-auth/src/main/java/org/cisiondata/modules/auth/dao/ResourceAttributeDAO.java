package org.cisiondata.modules.auth.dao;

import java.util.List;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.auth.entity.ResourceAttribute;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("resourceAttributeDAO")
public interface ResourceAttributeDAO extends GenericDAO<ResourceAttribute, Long> {
	
	/**
	 * 批量插入资源属性
	 * @param attributes
	 * @throws DataAccessException
	 */
	public void insertBatch(List<ResourceAttribute> attributes) throws DataAccessException;
	
}
