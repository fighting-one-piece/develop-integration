package org.cisiondata.modules.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.cisiondata.modules.user.entity.AdminUser;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

@Repository("aadminUserDAO")
public interface AdminUserDAO {

	/**
	 * 根据条件分页查询
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public List<AdminUser> findByCondition(Map<String,Object> params) throws DataAccessException;
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
	public int updateAdminUserById(AdminUser adminUser) throws DataAccessException;
	/**
	 * 新增
	 * @param adminUser
	 * @return
	 * @throws DataAccessException
	 */
	public int addAdminUser(AdminUser adminUser) throws DataAccessException , DuplicateKeyException;
	/**
	 * 根据id删除
	 * @param adminUser
	 * @throws DataAccessException
	 */
	public int deleteAdminUserById(@Param("id") Long id,@Param("deleteFlag") Long deleteFlag )throws DataAccessException;
	/**
	 * 根据id查询
	 */
	public int querydeleteFlagById(Long id);
	
}
