package org.cisiondata.modules.search.service;

import java.util.List;
import java.util.Map;

import org.cisiondata.utils.exception.BusinessException;

public interface IESMetadataService {
	
	/**
	 * 读取源数据信息
	 * @return
	 * @throws BusinessException
	 */
	public Object readMetadatas() throws BusinessException;
	
	/**
	 * 读取集群的所有Index
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, String> readIndices() throws BusinessException;
	
	/**
	 * 读取Index的所有Types
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, String> readIndexTypes(String index) throws BusinessException;
	
	/**
	 * 读取索引类型数据
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, List<String>> readIndicesTypes() throws BusinessException;
	
	/**
	 * 读取指定索引类型的属性列表
	 * @param index
	 * @param type
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, String> readIndexTypeAttributes(String index, String type) 	
			throws BusinessException;
	
	/**
	 * 读取索引类型数据,type包含中英文
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> readIndicesTypesCN() throws BusinessException;
	
	/**
	 * 清除缓存
	 * @throws BusinessException
	 */
	public void updateMetadatasNull() throws BusinessException;
	
}
