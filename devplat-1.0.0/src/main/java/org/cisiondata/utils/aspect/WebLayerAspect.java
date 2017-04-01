package org.cisiondata.utils.aspect;

import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Aspect
@Component
public class WebLayerAspect {

	private Logger LOG = LoggerFactory.getLogger("MODULE_LOG");

	private static final String EXECUTION = "execution(* org.cisiondata.modules.datainterface.controller.*.*(..)) or "
			+ "execution(* org.cisiondata.modules.identity.controller.*.*(..)) or "
			+ "execution(* org.cisiondata.modules.search.controller.ESController.*(..))";
	
	@Before(EXECUTION)
	public void before(JoinPoint joinPoint){
	}

	@After(EXECUTION)
	public void after(JoinPoint joinPoint){
	}

	@AfterReturning(pointcut = EXECUTION, returning = "result")
	public void afterReturn(JoinPoint joinPoint, Object result) {
	}

	@AfterThrowing(pointcut = EXECUTION, throwing = "exception")
	public void afterThrowing(JoinPoint joinPoint, Throwable exception){
		LOG.error(exception.getMessage(), exception);
	}

	@Around(EXECUTION)
	public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Signature signature = proceedingJoinPoint.getSignature();
		if (signature instanceof MethodSignature) {
			MethodSignature methodSignature = (MethodSignature) signature;
			Class<?> returnType = methodSignature.getReturnType();
			if (ModelAndView.class.isAssignableFrom(returnType)) {
				return proceedingJoinPoint.proceed();
			}
		}
		try {
			judgeSensitiveWord(proceedingJoinPoint.getArgs());
			return proceedingJoinPoint.proceed();
		} catch (BusinessException be) {
			WebResult webResult = new WebResult();
			webResult.setCode(ResultCode.FAILURE.getCode());
			webResult.setFailure(be.getMessage());
			return webResult;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void judgeSensitiveWord(Object[] args) throws BusinessException {
		if (null != args && args.length != 0) {
			for (int i = 0, len = args.length; i < len; i++) {
				Object arg = args[i];
				if (arg instanceof Map) {
					Map<String, Object> map = (Map<String, Object>) arg;
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						judgeSensitiveWord(entry.getValue());
					}
				} else {
					judgeSensitiveWord(arg);
				}
			}
		}
	}
	
	private void judgeSensitiveWord(Object arg) throws BusinessException {
		if (arg instanceof String) {
			String queryTxt = String.valueOf(arg);
			String[] keywords = queryTxt.indexOf(" ") == -1 ? new String[]{queryTxt} : queryTxt.split(" ");
			for (int i = 0, len = keywords.length; i < len; i++) {
				if (RedisClusterUtils.getInstance().sismember("sensitive_word", keywords[i])) {
					throw new BusinessException("抱歉!该查询涉及敏感信息");
				}
			}
		}
	}
	
}
