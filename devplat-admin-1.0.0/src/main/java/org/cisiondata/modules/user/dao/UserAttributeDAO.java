package org.cisiondata.modules.user.dao;


import org.cisiondata.modules.auth.entity.UserAttribute;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("auserAttributeDAO")
public interface UserAttributeDAO {
	
	/**
	 * 增加用户属性
	 * @param attribute
	 * @throws DataAccessException
	 */
	public int addUserAttribte(UserAttribute attribute) throws DataAccessException;
		
	/**
	 * 根据USER_ID查询是否存在KEY=ACCESS_ID
	 * @param attribute
	 * @return
	 * @throws DataAccessException
	 */
	public UserAttribute findAUserAttribte(UserAttribute attribute) throws DataAccessException;
	
	/**
	 * 根据USER_ID和KEY修改VALUE
	 * @param attribute
	 * @return
	 * @throws DataAccessException
	 */
	public int updateUserAttribte(UserAttribute attribute) throws DataAccessException;
}
