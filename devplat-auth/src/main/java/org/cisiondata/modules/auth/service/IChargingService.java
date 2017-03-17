package org.cisiondata.modules.auth.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.cisiondata.utils.exception.BusinessException;

public interface IChargingService {

	/**
	 * 计费
	 * @param proceedingJoinPoint
	 * @return
	 * @throws BusinessException
	 */
	public Object charge(ProceedingJoinPoint proceedingJoinPoint) throws BusinessException;
	
	/**
	 * 计费
	 * @param account
	 * @param requestUrl
	 * @param result
	 * @return
	 * @throws BusinessException
	 */
	public boolean charge(String account, String requestUrl, Object result) throws BusinessException;
}
