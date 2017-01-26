package org.cisiondata.modules.auth.service;

import org.cisiondata.modules.abstr.service.IGenericService;
import org.cisiondata.modules.auth.entity.AccessUser;
import org.cisiondata.modules.auth.entity.AccessUserControl;
import org.cisiondata.utils.exception.BusinessException;

public interface IAccessUserService extends IGenericService<AccessUser, Long> {
	
	/**
	 * 更新账号的剩余查询数量
	 * @param account
	 * @param remainingCount
	 * @param IncOrDec
	 * @throws BusinessException
	 */
	public void updateRemainingCount(String account, long remainingCount, long incOrDec) throws BusinessException;

	/**
	 * 根据accessId读取accessKey
	 * @param accessId
	 * @return
	 * @throws BusinessException
	 */
	public String readAccessKeyByAccessId(String accessId) throws BusinessException;
	
	/**
	 * 根据account读取AccessUserControl
	 * @param account
	 * @return
	 * @throws BusinessException
	 */
	public AccessUserControl readAccessUserControlByAccount(String account) throws BusinessException;
	
	
	
}
