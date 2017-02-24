package org.cisiondata.utils.aspect;

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
	
}
