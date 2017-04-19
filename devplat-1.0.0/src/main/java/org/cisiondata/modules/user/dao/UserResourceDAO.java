package org.cisiondata.modules.user.dao;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.auth.entity.UserResource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("auserResourceDAO")
public interface UserResourceDAO {

	/**
	 * 根据条件查询 。map中可放实体类中所有字段,以及begin(起始条数),pageSize(查询多少条),orderBy(排序规则)
	 * @param params
	 * @return
	 */
	public List<UserResource> findByCondition(Map<String,Object> params) throws DataAccessException;
	
	/**
	 * 根据条件查询总条数。 map中可放实体类中所有字段,以及begin(起始条数),pageSize(查询多少条),orderBy(排序规则)
	 * @param params
	 * @return 条数
	 */
	public int findCountByCondition(Map<String,Object> params) throws DataAccessException;
	
	/**
	 * 根据id修改
	 * @param userResource
	 * @return
	 */
	public int updateUserResourceById(UserResource userResource) throws DataAccessException;
	
	/**
	 * 新增
	 * @param userResource
	 * @return 新增成功后的id
	 */
	public long addUserResource (UserResource userResource) throws DataAccessException;
}
