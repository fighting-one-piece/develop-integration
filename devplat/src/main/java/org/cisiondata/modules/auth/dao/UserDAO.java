package org.cisiondata.modules.auth.dao;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.auth.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("userDAO")
public interface UserDAO extends GenericDAO<User, Long> {
	
	/**
	 * 修改用户
	 * @param user
	 */
	public void updateUserPassword(User user) throws DataAccessException;
	
	/**
	 * 通过账号查询用户
	 * @param account
	 * @return
	 */
	public User findUserByAccount(String account) throws DataAccessException;
}
