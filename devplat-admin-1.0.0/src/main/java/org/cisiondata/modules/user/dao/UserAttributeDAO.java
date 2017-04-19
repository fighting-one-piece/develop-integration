package org.cisiondata.modules.user.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.cisiondata.modules.auth.entity.UserAttribute;
import org.cisiondata.modules.user.entity.AUser;
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
	 * 根据USER_ID和KEY查询
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
	
	/**
	 * 根据条件查询
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	
	public List<UserAttribute> findByCondition(Map<String,Object> params) throws DataAccessException;
	/**
	 * 查询全部用户
	 * @return
	 */
	public List<AUser> findallAuser(Map<String,Object> params)throws DataAccessException;
	/**
	 * 修改用户余额
	 * @param userId
	 * @param money
	 * @return
	 * @throws DataAccessException
	 */
	public int updateRemainingMoney(@Param("userId") Long userId ,@Param("money")String money)throws DataAccessException;
	
}
