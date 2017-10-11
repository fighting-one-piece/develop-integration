package org.cisiondata.modules.auth.service;

import org.cisiondata.modules.abstr.service.IGenericService;
import org.cisiondata.modules.auth.entity.AuthResource;
import org.cisiondata.utils.exception.BusinessException;

public interface IResourceService extends IGenericService<AuthResource, Long> {

	/**
	 * 根据ID读取标识
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public String readIdentityById(Long id) throws BusinessException;
	
	/**
	 * 根据URL读取标识
	 * @param url
	 * @return
	 * @throws BusinessException
	 */
	public String readIdentityByUrl(String url,Integer type) throws BusinessException;
	
	/**
	 * 判断该url是否在resource表中
	 * @param url
	 * @param type
	 * @return
	 * @throws BusinessException
	 */
	public boolean readResource(String url,Integer type) throws BusinessException;
	
	/**
	 * 根据url和type获取内存中的resource
	 * @param url
	 * @param type
	 * @return
	 * @throws BusinessException
	 */
	public AuthResource readResourceFromCache(String url,Integer type) throws BusinessException;
}
