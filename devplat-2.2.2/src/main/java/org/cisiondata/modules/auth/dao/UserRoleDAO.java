package org.cisiondata.modules.auth.dao;

import java.util.List;

import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.auth.entity.AuthUserRole;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("authUserRoleDAO")
public interface UserRoleDAO {
	public List<AuthUserRole> readDataListByCondition(Query query) throws DataAccessException;
}
