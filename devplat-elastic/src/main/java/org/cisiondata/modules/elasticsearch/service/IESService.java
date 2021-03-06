package org.cisiondata.modules.elasticsearch.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.utils.exception.BusinessException;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilder;

public interface IESService {
	
	/**
	 * 读取名字属性
	 * @return
	 * @throws BusinessException
	 */
	public Set<String> readNameAttributes() throws BusinessException;
	
	/**
	 * 读取标识属性
	 * @return
	 * @throws BusinessException
	 */
	public Set<String> readIdentityAttributes() throws BusinessException;
	
	/**
	 * 读取位置属性
	 * @return
	 * @throws BusinessException
	 */
	public Set<String> readLocationAttributes() throws BusinessException;
	
	/**
	 * 根据条件全索引读取数据
	 * @param query
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> readDataListByCondition(QueryBuilder query) throws BusinessException;
	
	/**
	 * 根据条件全索引读取数据
	 * @param query
	 * @param highLight 是否高亮
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> readDataListByCondition(QueryBuilder query, 
			boolean highLight) throws BusinessException;
	
	/**
	 * 根据条件全索引读取数据
	 * @param query
	 * @param size 返回结果集条数
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> readDataListByCondition(QueryBuilder query, int size) 
			throws BusinessException;
	
	/**
	 * 根据条件全索引读取数据
	 * @param query
	 * @param size 返回结果集条数
	 * @param highLight 是否高亮
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> readDataListByCondition(QueryBuilder query, int size,
			boolean isHighLight) throws BusinessException;
	
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
	 * @param size
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> readDataListByCondition(String index, String type, 
			QueryBuilder query, int size) throws BusinessException;
	
	/**
	 * 根据条件指定索引读取数据
	 * @param index
	 * @param type
	 * @param query
	 * @param isHighLight 是否高亮
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> readDataListByCondition(String index, String type, 
			QueryBuilder query, boolean isHighLight) throws BusinessException;
	
	/**
	 * 根据条件指定索引读取数据
	 * @param index
	 * @param type
	 * @param query
	 * @param isHighLight 是否高亮
	 * @param isMessageSource 是否国际化
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> readDataListByCondition(String index, String type, 
			QueryBuilder query, boolean isHighLight, boolean isMessageSource) throws BusinessException;
	
	/**
	 * 根据属性值列表指定索引读取数据
	 * @param index
	 * @param type
	 * @param attribute 属性
	 * @param attributeValues 属性值列表
	 * @param size 返回条数
	 * @return
	 * @throws BusinessException
	 */
	public List<Object> readDataListByCondition(String index, String type, String attribute, 
			List<String> attributeValues, int size) throws BusinessException;
	
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
	 * 根据条件指定索引读取数据
	 * @param index
	 * @param type
	 * @param query
	 * @param isHighLight 是否高亮
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> readDataListByCondition(String index, String[] types, 
			QueryBuilder query, boolean isHighLight) throws BusinessException;
	
	/**
	 * 根据条件指定索引读取数据
	 * @param index
	 * @param type
	 * @param query
	 * @param isHighLight 是否高亮
	 * @param isMessageSource 是否国际化
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> readDataListByCondition(String index, String[] types, 
			QueryBuilder query, boolean isHighLight, boolean isMessageSource) throws BusinessException;
	
	/**
	 * 根据条件全索引读取分页数据
	 * @param query
	 * @param scrollId
	 * @param size 返回结果集条数
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
	 * @param size 返回结果集条数
	 * @return
	 * @throws BusinessException
	 */
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, String type, 
			QueryBuilder query, String scrollId, int size) throws BusinessException;
	
	/**
	 * 根据条件指定索引读取分页数据
	 * @param index
	 * @param type
	 * @param query
	 * @param scrollId
	 * @param size 返回结果集条数
	 * @param isHighLight 是否高亮
	 * @return
	 * @throws BusinessException
	 */
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, String type, 
			QueryBuilder query, String scrollId, int size, boolean isHighLight) throws BusinessException;
	
	/**
	 * 根据条件指定索引读取分页数据
	 * @param index
	 * @param type
	 * @param query
	 * @param scrollId
	 * @param size 返回结果集条数
	 * @param isHighLight 是否高亮
	 * @param isMessageSource 是否国际化
	 * @return
	 * @throws BusinessException
	 */
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, String type, 
			QueryBuilder query, String scrollId, int size, boolean isHighLight, boolean isMessageSource) 
					throws BusinessException;
	
	/**
	 * 根据条件指定索引读取分页数据
	 * @param index
	 * @param type
	 * @param query
	 * @param queryType 
	 * @param scrollId
	 * @param size 返回结果集条数
	 * @param isHighLight 是否高亮
	 * @return
	 * @throws BusinessException
	 */
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, 
			String type, QueryBuilder query, SearchType searchType, String scrollId, 
				int size, boolean isHighLight) throws BusinessException;
	
	/**
	 * 根据条件指定索引读取分页数据带score数据
	 * @param index
	 * @param type
	 * @param query
	 * @param scrollId
	 * @param size 返回结果集条数
	 * @return
	 * @throws BusinessException
	 */
	public QueryResult<Map<String, Object>> readPaginationDataListByConditionWithScore(String index, 
			String type, QueryBuilder query, String scrollId, int size) throws BusinessException;
	
	/**
	 * 根据条件读取分页数据
	 * @param index
	 * @param type
	 * @param query
	 * @param scrollId
	 * @param size 返回结果集条数
	 * @return
	 * @throws BusinessException
	 */
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, String[] types, 
			QueryBuilder query, String scrollId, int size) throws BusinessException;
	
	/**
	 * 根据条件读取分页数据
	 * @param index
	 * @param type
	 * @param query
	 * @param scrollId
	 * @param size 返回结果集条数
	 * @param isHighLight 是否高亮
	 * @return
	 * @throws BusinessException
	 */
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, String[] types, 
			QueryBuilder query, String scrollId, int size, boolean isHighLight) throws BusinessException;
	
	/**
	 * 根据条件读取分页数据
	 * @param index
	 * @param type
	 * @param query
	 * @param scrollId
	 * @param size 返回结果集条数
	 * @param isHighLight 是否高亮
	 * @param isMessageSource 是否国际化
	 * @return
	 * @throws BusinessException
	 */
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, String[] types, 
			QueryBuilder query, String scrollId, int size, boolean isHighLight, boolean isMessageSource) 
					throws BusinessException;
	
	/**
	 * 根据条件读取分页数据
	 * @param index
	 * @param type
	 * @param query
	 * @param searchType
	 * @param scrollId
	 * @param size 返回结果集条数
	 * @param isHighLight 是否高亮
	 * @return
	 * @throws BusinessException
	 */
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, String[] types, 
			QueryBuilder query, SearchType searchType, String scrollId, int size, 
				boolean isHighLight) throws BusinessException;
	
	/**
	 * 根据条件读取分页数据带score数据
	 * @param index
	 * @param type
	 * @param query
	 * @param scrollId
	 * @param size 返回结果集条数
	 * @return
	 * @throws BusinessException
	 */
	public QueryResult<Map<String, Object>> readPaginationDataListByConditionWithScore(String index, 
			String[] types, QueryBuilder query, String scrollId, int size) throws BusinessException;
	
}
