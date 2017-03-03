package org.cisiondata.modules.analysis.service;

import java.util.List;
import java.util.Map;

import org.cisiondata.utils.exception.BusinessException;

public interface IDBService {

	/**
	 * 获取数据库表数据信息
	 * @param dbName
	 * @return
	 * @throws BusinessException
	 */
	public List<Map.Entry<String, Long>> readDBTables(String dbName) throws BusinessException;
	
	/**
	 * 获取指定主机数据库表数据信息
	 * @param dbHost
	 * @param dbName
	 * @return
	 * @throws BusinessException
	 */
	public List<Map.Entry<String, Long>> readDBTables(String dbHost, String dbName) throws BusinessException;
	
}
