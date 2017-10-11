package org.cisiondata.modules.auth.dao;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.auth.entity.AuthUser;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("authUserDAO")
public interface UserDAO extends GenericDAO<AuthUser, Long> {
	/**
	 * 根据条件查询，包括ID
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public AuthUser readDataByConditions(Query query) throws DataAccessException;
}
