package org.cisiondata.modules.system.dao;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.auth.entity.ResourceAttribute;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("aresourceAttributeDAO")
public interface ResourceAttributeDAO {

	/**
	 * 根据条件查询 。map中可放实体类中所有字段,以及begin(起始条数),pageSize(查询多少条),orderBy(排序规则)
	 * @param params
	 * @return List<ResourceAttribute>
	 */
	public List<ResourceAttribute> findByCondition(Map<String,Object> params) throws DataAccessException;
	
	/**
	 * 根据条件查询总条数。 map中可放实体类中所有字段,以及begin(起始条数),pageSize(查询多少条),orderBy(排序规则)
	 * @param params
	 * @return 条数
	 */
	public int findCountByCondition(Map<String,Object> params) throws DataAccessException;
	
	/**
	 * 根据id修改
	 * @param resourceAttribute
	 * @return
	 */
	public int updateResourceAttributeById(ResourceAttribute resourceAttribute) throws DataAccessException;
	
	/**
	 * 新增
	 * @param resourceAttribute
	 * @return 新增成功后的id
	 */
	public long addResourceAttribute (ResourceAttribute resourceAttribute) throws DataAccessException;
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	public int deleteById(Long id) throws DataAccessException;
}
