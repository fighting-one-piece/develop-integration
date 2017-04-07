package org.cisiondata.modules.system.service;


import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.auth.entity.Resource;
import org.cisiondata.utils.exception.BusinessException;

public interface IResourceService {

	/**
	 * 添加资源
	 * @param name
	 * @param type
	 * @param identity
	 * @param url
	 * @param icon
	 * @param priority
	 * @param parentId
	 * @param deleteFlag
	 * @param fields
	 * @param chargings
	 * @return
	 * @throws BusinessException
	 */
	public int addResource(String name,Integer type,String identity,String url,String icon,Integer priority,Long parentId,Boolean deleteFlag) throws BusinessException;
	
	/**
	 * 修改资源
	 * @param name
	 * @param type
	 * @param identity
	 * @param url
	 * @param icon
	 * @param priority
	 * @param parentId
	 * @param deleteFlag
	 * @param fields
	 * @param chargings
	 * @throws BusinessException
	 */
	public void editResource(Long id,String name,Integer type,String identity,String url,String icon,Integer priority,Long parentId,Boolean deleteFlag) throws BusinessException;
	
	/**
	 * 查询所有资源
	 * @return
	 * @throws BusinessException
	 */
	public QueryResult<Resource> qureyAllResource(Integer page, Integer pageSize) throws BusinessException;
	
	/**
	 * 通过ID启用、关闭资源
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public void deleteResource(Long id,Boolean deleteFlag) throws BusinessException;
	
	
}
