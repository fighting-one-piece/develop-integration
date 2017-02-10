package org.cisiondata.modules.elasticsearch.service;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.elasticsearch.entity.ESMetadata;
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
	 * 清除缓存
	 * @throws BusinessException
	 */
	public void updateMetadatasNull() throws BusinessException;
	
	/**
	 * 统计ES信息
	 */
	public Map<String, Long> readESStatisticsDatas() throws BusinessException;
	
	/**
	 * 根据Type 查询
	 * @param type
	 * @return
	 */
	public List<ESMetadata> findType(String type);
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	public int deleteId(int id);
	
	/**
	 * 添加
	 * @param esmetdata
	 * @return 
	 */
	public void save(ESMetadata metadata);
	
	/**
	 * 根据ID修改
	 * @param metadata
	 */
	public int updateId(ESMetadata metadata);
	
	
	/**
	 * 读取源数据信息的index和type
	 * @return
	 * @throws BusinessException
	 */

}
