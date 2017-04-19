package org.cisiondata.modules.user.service;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.auth.entity.ResourceAttribute;
import org.cisiondata.modules.auth.entity.ResourceCharging;
import org.cisiondata.utils.exception.BusinessException;

public interface IResourceAttributeService {
	
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> findByIdCondition(Long resourceid) throws BusinessException;

	/**
	 * 根据resourceId和key修改
	 * @param fields
	 * @param resourceid
	 * @throws BusinessException
	 */
	public void updateResourceAttributeByresourceId(Long resourceid,String fields) throws BusinessException; 
	
	/**
	 * 新增
	 * @param attribute
	 * @return
	 * @throws BusinessException
	 */
	public long addResourceAttributeById(ResourceAttribute attribute) throws BusinessException;
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public void deleteById(Long id) throws BusinessException;
	/**
	 * 费用查询
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public List<ResourceCharging> findByIdResourceAttribute(Long resourceid) throws BusinessException;
	
	/**
	 * 更新资源
	 * @param resourceAttribute
	 * @return
	 * @throws BusinessException
	 */
	
	public String updateResourceAttribute(Long resource_id,String chargings) throws BusinessException;

	
}
