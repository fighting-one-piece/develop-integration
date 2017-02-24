package org.cisiondata.utils.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.cisiondata.utils.exception.BusinessException;

public class ChargingServiceImpl implements IChargingService {

	@Override
	public Object charge(ProceedingJoinPoint proceedingJoinPoint) throws BusinessException {
		return null;
	}

}
