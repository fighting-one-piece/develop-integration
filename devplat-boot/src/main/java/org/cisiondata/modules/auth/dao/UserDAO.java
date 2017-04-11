package org.cisiondata.modules.auth.dao;

import org.cisiondata.modules.auth.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("userDAO")
public interface UserDAO {

	public User readDataByPK(Long id) throws DataAccessException;
	
}
