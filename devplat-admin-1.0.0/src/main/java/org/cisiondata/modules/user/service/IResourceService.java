package org.cisiondata.modules.user.service;

import java.util.Map;

import org.cisiondata.utils.exception.BusinessException;

public interface IResourceService {
	// 添加资源
	public void addResource(String name, Integer type, String identity, String url) throws BusinessException;

	// 修改资源
	public void editResource(Long id, String name, Integer type, String identity, String url) throws BusinessException;

	// 查询所有资源
	public Map<String, Object> qureyAllResource(Integer page, Integer pageSize, Integer type) throws BusinessException;

	// 通过ID启用、关闭资源
	public void deleteResource(Long id, Boolean deleteFlag, Integer type) throws BusinessException;
}
