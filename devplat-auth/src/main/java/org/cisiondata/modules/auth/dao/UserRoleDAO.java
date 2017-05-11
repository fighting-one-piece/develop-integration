package org.cisiondata.modules.auth.dao;

import java.util.List;

import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.auth.entity.BUserRole;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("buserRoleDAO")
public interface UserRoleDAO{
	public List<BUserRole> readDataListByCondition(Query query) throws DataAccessException;
}
