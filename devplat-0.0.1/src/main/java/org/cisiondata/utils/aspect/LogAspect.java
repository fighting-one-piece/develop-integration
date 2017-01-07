package org.cisiondata.utils.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.cisiondata.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

	private Logger LOG = LoggerFactory.getLogger("MODULE_LOG");

	private static final String EXECUTION = "execution(* org.cisiondata.modules.*.*.impl.*.*(..))";

	@Before(EXECUTION)
	public void logBefore(JoinPoint joinPoint){
//		LOG.info("------Log Before Method------" + joinPoint.getSignature().getName());
	}

	@After(EXECUTION)
	public void logAfter(JoinPoint joinPoint){
//		LOG.info("------Log After Method------" + joinPoint.getSignature().getName());
	}

	@AfterReturning(pointcut = EXECUTION, returning = "result")
	public void logAfterReturn(JoinPoint joinPoint, Object result) {
//		LOG.info("------Log After Returning Method------" + joinPoint.getSignature().getName());
//		LOG.info("------Log After Returning Method Return Value------" + result);
	}

	@AfterThrowing(pointcut = EXECUTION, throwing = "exception")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable exception){
//		LOG.info("------Log After Throwing Method------" + joinPoint.getSignature().getName());
		LOG.error(exception.getMessage(), exception);
	}

	@Around(EXECUTION)
	public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		long startTime = System.currentTimeMillis();
		Object[] args = proceedingJoinPoint.getArgs();
		if (null != args && args.length != 0) {
			for (int i = 0, len = args.length; i < len; i++) {
				Object arg = args[i];
				if (arg instanceof String) {
					if (SensitiveWordUtils.isSensitiveWord(String.valueOf(arg))) {
						throw new BusinessException("抱歉!该查询涉及敏感信息");
					}
				}
			}
		}
		String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
		String methodName = proceedingJoinPoint.getSignature().getName();
		LOG.info("------{} {} Log Start", className, methodName);
		Object result = proceedingJoinPoint.proceed();
		LOG.info("------{} {} Log End ! Spend Time: {} s", className, methodName, (System.currentTimeMillis() - startTime) / 1000);
		return result;
	}


}
