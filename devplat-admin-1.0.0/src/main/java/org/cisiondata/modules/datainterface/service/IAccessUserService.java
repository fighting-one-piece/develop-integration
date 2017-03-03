package org.cisiondata.modules.datainterface.service;

import java.util.Map;

import org.cisiondata.modules.abstr.service.IGenericService;
import org.cisiondata.modules.datainterface.entity.AccessUser;
import org.cisiondata.utils.exception.BusinessException;

public interface IAccessUserService extends IGenericService<AccessUser, Long> {

	public int addAccessControl(AccessUser accessControl) throws BusinessException;
	
	public Map<String,Object> findAccessUserByPage(int page,int pageSize)throws BusinessException;
	
	public int updateDeleteFlag(AccessUser accessUser) throws BusinessException;
	
	/**
	 * 根据accessId读取accessKey
	 * @param accessId
	 * @return
	 * @throws BusinessException
	 */
	public String readAccessKeyByAccessId(String accessId) throws BusinessException;
}
