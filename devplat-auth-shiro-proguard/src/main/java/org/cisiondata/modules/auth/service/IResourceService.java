package org.cisiondata.modules.auth.service;

import java.util.List;

import org.cisiondata.modules.abstr.service.IGenericService;
import org.cisiondata.modules.auth.entity.Resource;
import org.cisiondata.utils.exception.BusinessException;

public interface IResourceService extends IGenericService<Resource, Long> {

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
	public String readIdentityByUrl(String url) throws BusinessException;
	
	/**
	 * 获取用户菜单
	 * @return
	 * @throws BusinessException
	 */
	public List<Resource> readResourceByType() throws BusinessException;
}
