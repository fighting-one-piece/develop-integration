package org.cisiondata.modules.auth.service;

import org.cisiondata.modules.abstr.service.IGenericService;
import org.cisiondata.modules.auth.entity.AccessInterface;
import org.cisiondata.modules.auth.entity.AccessUser;
import org.cisiondata.modules.auth.entity.AccessUserControl;
import org.cisiondata.modules.auth.entity.AccessUserInterface;
import org.cisiondata.utils.exception.BusinessException;

public interface IAccessUserService extends IGenericService<AccessUser, Long> {
	
	/**
	 * 更新账号的剩余查询数据
	 * @param account
	 * @param remainingCount
	 * @param incOrDecCount
	 * @param remainingMoney
	 * @param incOrDecMoney
	 * @throws BusinessException
	 */
	public void updateRemaining(String account, long remainingCount, long incOrDecCount, 
			double remainingMoney, double incOrDecMoney) throws BusinessException;
	
	/**
	 * 更新账号的剩余查询数量
	 * @param account
	 * @param remainingCount
	 * @param IncOrDec
	 * @throws BusinessException
	 */
	public void updateRemainingCount(String account, long remainingCount, long incOrDec) 
			throws BusinessException;
	
	/**
	 * 更新账号的剩余查询金额
	 * @param account
	 * @param remainingMoney
	 * @param IncOrDec
	 * @throws BusinessException
	 */
	public void updateRemainingMoney(String account, double remainingMoney, double incOrDec) 
			throws BusinessException;

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
	
	/**
	 * 根据identity读取AccessInterface
	 * @param identity
	 * @return
	 * @throws BusinessException
	 */
	public AccessInterface readAccessInterfaceByIdentity(String identity) throws BusinessException;
	
	/**
	 * 根据account和interfaceId读取AccessUserInterface
	 * @param account
	 * @param interfaceId
	 * @return
	 * @throws BusinessException
	 */
	public AccessUserInterface readAccessUserInterfaceByAccountAndInterfaceId(String account, 
			Long interfaceId) throws BusinessException;
	
}
