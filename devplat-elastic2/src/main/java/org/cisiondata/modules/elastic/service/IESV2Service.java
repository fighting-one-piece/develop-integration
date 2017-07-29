package org.cisiondata.modules.elastic.service;

import java.util.Map;

import org.cisiondata.modules.abstr.entity.QueryResult;

public interface IESV2Service {
	
	/**
	 * 查询数据
	 * @param indices
	 * @param types
	 * @param keywords
	 * @param highLight
	 * @return
	 * @throws RuntimeException
	 */
	public QueryResult<Map<String, Object>> readDataList(String indices, String types, 
			String keywords, boolean highLight) throws RuntimeException;
	
	/**
	 * 查询指定字段数据
	 * @param indices
	 * @param types
	 * @param fields
	 * @param keywords
	 * @param highLight
	 * @return
	 * @throws RuntimeException
	 */
	public QueryResult<Map<String, Object>> readDataList(String indices, String types, 
			String fields, String keywords, boolean highLight) throws RuntimeException;

}
