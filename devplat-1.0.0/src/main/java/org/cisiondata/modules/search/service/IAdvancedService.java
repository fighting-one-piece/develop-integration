package org.cisiondata.modules.search.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.cisiondata.utils.exception.BusinessException;

public interface IAdvancedService {

	/**
	 * 
	 * @param HttpServletRequest
	 * @param index
	 * @param type
	 * @param query
	 * @param scrollId
	 * @param rowNumPerPage
	 * @return
	 * @throws BusinessException
	 */
	public Map<String,Object> readPaginationDataListByMultiCondition(HttpServletRequest req,String index, String type, 
			String query, String scrollId,Integer rowNumPerPage) throws BusinessException;
}
