package org.cisiondata.modules.elasticsearch.service;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.utils.exception.BusinessException;
import org.elasticsearch.index.query.QueryBuilder;

public interface IESService {
	
	/**
	 * 根据条件全索引读取数据
	 * @param query
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> readDataListByCondition(QueryBuilder query) throws BusinessException;
	
	/**
	 * 根据条件指定索引读取数据
	 * @param index
	 * @param type
	 * @param query
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> readDataListByCondition(String index, String type, 
			QueryBuilder query) throws BusinessException;
	
	/**
	 * 根据条件指定索引读取数据
	 * @param index
	 * @param type
	 * @param query
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> readDataListByCondition(String index, String[] types, 
			QueryBuilder query) throws BusinessException;
	
	/**
	 * 根据条件全索引读取分页数据
	 * @param query
	 * @param scrollId
	 * @param size
	 * @return
	 * @throws BusinessException
	 */
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(QueryBuilder query, 
			String scrollId, int size) throws BusinessException;
	
	/**
	 * 根据条件指定索引读取分页数据
	 * @param index
	 * @param type
	 * @param query
	 * @param scrollId
	 * @param size
	 * @return
	 * @throws BusinessException
	 */
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, String type, 
			QueryBuilder query, String scrollId, int size) throws BusinessException;
	
	/**
	 * 根据条件读取分页数据
	 * @param index
	 * @param type
	 * @param query
	 * @param scrollId
	 * @param size
	 * @return
	 * @throws BusinessException
	 */
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, String[] types, 
			QueryBuilder query, String scrollId, int size) throws BusinessException;
	
}
