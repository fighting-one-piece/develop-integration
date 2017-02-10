package org.cisiondata.modules.elasticsearch.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cisiondata.utils.exception.BusinessException;

public interface IESExportService {
	
	/**
	 * 根据条件读取数据
	 * @param query
	 * @param scrollId
	 * @param size
	 * @return
	 * @throws BusinessException
	 * @throws IOException 
	 */
	public void readDataListForExport(HttpServletRequest req, HttpServletResponse resp, String query) throws BusinessException, IOException;
	
	public List<Map<String,Object>> readPaginationDataListByExs(String query, String scrollId, int size) throws BusinessException;
}
