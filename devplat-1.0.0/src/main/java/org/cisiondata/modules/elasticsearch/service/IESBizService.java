package org.cisiondata.modules.elasticsearch.service;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.utils.exception.BusinessException;

public interface IESBizService extends IESService {
	
	
	/**
	 * 根据条件读取数据列表
	 * @param query
	 * @param size
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> readDataListByCondition(String query, int size) 
			throws BusinessException;
	
	/**
	 * 根据条件读取分页数据列表
	 * @param query
	 * @param scrollId
	 * @param size
	 * @return
	 * @throws BusinessException
	 */
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String query, 
			String scrollId, int size) throws BusinessException;
	
	/**
	 * 根据条件读取数据
	 * @param index
	 * @param type
	 * @param query
	 * @param scrollId
	 * @param size
	 * @return
	 * @throws BusinessException
	 */
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, String type, 
			String query, String scrollId, int size) throws BusinessException;
	
	/**
	 * 根据条件读取数据
	 * @param index
	 * @param type
	 * @param query
	 * @param scrollId
	 * @param size
	 * @param isHighLight
	 * @return
	 * @throws BusinessException
	 */
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, String type, 
			String query, String scrollId, int size, boolean isHighLight) throws BusinessException;

	/**
	 * 多条件查询
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	public QueryResult<Map<String, Object>> readPaginationDataListByMultiCondition(String scrollId,Integer rowNumPerPage,String index,String type,String query) 
			throws BusinessException;
	
	/**
	 * 根据条件读取标签和命中数
	 * @param query
	 * @param includeTypes 指定表集合
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> readLabelsAndHitsIncludeTypes(String query, String... includeTypes) 
			throws BusinessException;
	
	/**
	 * 根据条件读取标签和命中数
	 * @param query
	 * @param excludeTypes 指定排除表集合
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> readLabelsAndHitsExcludeTypes(String query, String... excludeTypes) 
			throws BusinessException;
	
	/**
	 * 根据条件读取标签数据
	 * @param index
	 * @param type
	 * @param query
	 * @param scrollId
	 * @param size
	 * @return
	 * @throws BusinessException
	 */
	public QueryResult<Map<String, Object>> readLabelPaginationDataList(String index, String type, 
			String query, String scrollId, int size) throws BusinessException;
	
	/**
	 * 根据条件读取物流数据列表
	 * @param query
	 * @param isHighLight 返回是否高亮
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> readLogisticsDataList(String query, boolean isHighLight) 
			throws BusinessException;
	
	/**
	 * 根据条件读取物流过滤数据列表
	 * @param query
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> readLogisticsFilterDataList(String query) throws BusinessException;
	
	/**
	 * 根据条件读取物流关系数据列表
	 * @param query
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> readLogisticsRelationsDataList(String query) throws BusinessException;
	
}
