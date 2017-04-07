package org.cisiondata.modules.system.dao;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.auth.entity.Resource;
import org.cisiondata.modules.auth.entity.UserRestriction;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("auserRestrictionDAO")
public interface UserRestrictionDAO {

	/**
	 * 查询，map中可放实体类中所有字段，以及begin（起始条数），pageSize（查询多少条），orderBy（排序规则）
	 * @param params
	 * @return
	 */
	public List<UserRestriction> findByCondition(Map<String,Object> params) throws DataAccessException;
	
	/**
	 * 根据id修改
	 * @param resource
	 * @return
	 */
	public int updateUserRestrictionById(Resource resource) throws DataAccessException;
	
	/**
	 * 新增
	 * @param resource
	 * @return 新增成功后的id
	 */
	public long addUserRestriction (Resource resource) throws DataAccessException;
}
