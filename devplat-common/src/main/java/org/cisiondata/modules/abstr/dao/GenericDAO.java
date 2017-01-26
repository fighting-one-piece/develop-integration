package org.cisiondata.modules.abstr.dao;

import java.io.Serializable;
import java.util.List;

import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.utils.exception.DataException;

public interface GenericDAO <Entity extends Serializable, PK extends Serializable> {

	/**
	 * 插入实体对象 
	 * @param entity
	 * @throws DataException
	 */
	public void insert(Entity entity) throws DataException;
	
	/**
	 * 批量插入实体对象
	 * @param entities
	 * @throws DataException
	 */
	public void insert(List<Entity> entities) throws DataException;

	/**
	 * 更新实体对象
	 * @param entity
	 * @throws DataException
	 */
	public void update(Entity entity) throws DataException;
	
	/**
	 * 批量更新实体对象
	 * @param entities
	 * @throws DataException
	 */
	public void update(List<Entity> entities) throws DataException;

	/**
	 * 删除实体对象
	 * @param entity
	 * @throws DataException
	 */
	public void delete(Entity entity) throws DataException;

	/**
	 * 根据主键删除
	 * @param pk
	 * @throws DataException
	 */
	public void deleteByPK(PK pk) throws DataException;

	/**
	 * 根据主键读取实体对象
	 * @param pk
	 * @return
	 * @throws DataException
	 */
	public Entity readDataByPK(PK pk) throws DataException;

	/**
	 * 根据条件读取实体对象
	 * @param query
	 * @return
	 * @throws DataException
	 */
	public Entity readDataByCondition(Query query) throws DataException;
	
	/**
	 * 根据条件读取实体对象列表
	 * @param query
	 * @return
	 * @throws DataException
	 */
	public List<Entity> readDataListByCondition(Query query) throws DataException;

	/**
	 * 根据条件读取实体对象分页列表
	 * @param query
	 * @return
	 * @throws DataException
	 */
	public QueryResult<Entity> readDataPaginationByCondition(Query query) throws DataException;
	
	/**
	 * 根据条件读取数据数量
	 * @param query
	 * @return
	 * @throws DataException
	 */
	public Long readCountByCondition(Query query) throws DataException;

	/**
	 * 刷新
	 * @throws DataException
	 */
	public void flush() throws DataException;
	
}
