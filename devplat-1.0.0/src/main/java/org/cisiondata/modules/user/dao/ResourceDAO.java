package org.cisiondata.modules.user.dao;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.auth.entity.Resource;
import org.springframework.stereotype.Repository;

@Repository("aresourceDAO")
public interface ResourceDAO {

	/**
	 * 根据条件查询 。map中可放实体类中所有字段,以及begin(起始条数),pageSize(查询多少条),orderBy(排序规则)
	 * @param params
	 * @return
	 */
	public List<Resource> findByCondition(Map<String,Object> params);
	
	/**
	 * 根据条件查询总条数。 map中可放实体类中所有字段,以及begin(起始条数),pageSize(查询多少条),orderBy(排序规则)
	 * @param params
	 * @return 条数
	 */
	public int findCountByCondition(Map<String,Object> params);
	
	/**
	 * 根据id修改
	 * @param resource
	 * @return
	 */
	public int updateResourceById(Resource resource);
	
	/**
	 * 新增
	 * @param resource
	 * @return 新增成功后的id
	 */
	public int addResource (Resource resource);
}
