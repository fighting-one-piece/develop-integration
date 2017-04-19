package org.cisiondata.modules.auth.dao;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.auth.entity.AAdminUser;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("adminUserDAO")
public interface AdminUserDAO {

	/**
	 * 根据条件分页查询
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public List<AAdminUser> findByCondition(Map<String,Object> params) throws DataAccessException;
	/**
	 * 根据条件查询总条数
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public int findCountByCondition(Map<String,Object> params) throws DataAccessException;
	/**
	 * 根据id修改
	 * @param adminUser
	 * @return
	 * @throws DataAccessException
	 */
	public int updateAdminUserById(AAdminUser adminUser) throws DataAccessException;
	/**
	 * 新增
	 * @param adminUser
	 * @return
	 * @throws DataAccessException
	 */
	public int addAdminUser(AAdminUser adminUser) throws DataAccessException;
}
