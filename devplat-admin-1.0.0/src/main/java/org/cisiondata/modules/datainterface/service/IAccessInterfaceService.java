package org.cisiondata.modules.datainterface.service;

import java.util.Map;

import org.cisiondata.modules.datainterface.entity.AccessInterface;
import org.cisiondata.utils.exception.BusinessException;

public interface IAccessInterfaceService {

	/**
	 * 分页查询接口
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws BusinessException
	 */
	public Map<String,Object> findAccessInterfaceByPage(int page,int pageSize)throws BusinessException;
	
	/**
	 * 修改DELETE_FLAG
	 * @param accessInterface
	 * @return
	 * @throws BusinessException
	 */
	public int updateDeleteFlag(AccessInterface accessInterface) throws BusinessException;
	
	/**
	 * 添加接口
	 * @param accessInterface
	 * @return
	 * @throws BusinessException
	 */
	public int addAccessInterface(AccessInterface accessInterface) throws BusinessException;
	
	/**
	 * 修改接口
	 * @param accessInterface
	 * @return
	 * @throws BusinessException
	 */
	public int updateAccessInterface(AccessInterface accessInterface) throws BusinessException;
}
