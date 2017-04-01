package org.cisiondata.modules.search.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.cisiondata.utils.exception.BusinessException;

public interface ILabelsService {
	
	/**
	 * 
	 * @param HttpServletRequest
	 * @param index
	 * @param type
	 * @param query
	 * @param scrollId
	 * @param size
	 * @return
	 * @throws BusinessException
	 */
	public Map<String,Object> readPaginationDataListByCondition(HttpServletRequest req,String index, String type, 
			String query, String scrollId,int size) throws BusinessException;
	
	/**
	 * 标签查询
	 * @param query
	 * @param includeTypes
	 * @return
	 * @throws BusinessException
	 */
	public Map<String,Object> readLabelsAndHitsAndClassifiedIncludeTypes(String query, String... includeTypes) throws BusinessException;
}
