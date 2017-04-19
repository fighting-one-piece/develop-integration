package org.cisiondata.modules.user.dao;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.user.entity.AUser;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("auserDAO")
public interface AUserDAO {

	/**
	 * 新增用户
	 * @param auser
	 * @return
	 * @throws DataAccessException
	 */
	public Long addAUser(AUser auser) throws DataAccessException;

	/**
	 * 根据账号查询用户
	 * @param account
	 * @return
	 * @throws DataAccessException
	 */
	public AUser findaccountAuser(String account) throws DataAccessException;
	
	/**
	 * 根据条件查询总条数
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public Long findCountAuser(Map<String,Object> params) throws DataAccessException;
	
	/**
	 * 根据条件分页查询
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public List<AUser> findAuser(Map<String,Object> params) throws DataAccessException;
	
	/**
	 * 根据ID修改
	 * @param auser
	 * @throws DataAccessException
	 */
	public void updateAUser(AUser auser) throws DataAccessException;
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	public AUser findIdAuser(Long id) throws DataAccessException;
	
	/**
	 * 查询全部用户
	 * @throws DataAccessException
	 */
	public List<AUser> findAllAuser(Long identity) throws DataAccessException;
	
	/**
	 * 查询用户角色id
	 * @param id
	 * @param identity
	 * @return
	 * @throws DataAccessException
	 */
	public List<Long> findAuserRole(AUser aUser) throws DataAccessException;
	
}
