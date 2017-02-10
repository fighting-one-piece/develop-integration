package org.cisiondata.utils.aspect;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.auth.entity.AccessUserControl;
import org.cisiondata.modules.auth.service.IAccessUserService;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class WebLayerAspect {

	private Logger LOG = LoggerFactory.getLogger("MODULE_LOG");

	private static final String EXECUTION = "execution(* org.cisiondata.modules.datada.controller.*.*(..)) or "
			+ "execution(* org.cisiondata.modules.datainterface.controller.*.*(..)) or "
			+ "execution(* org.cisiondata.modules.identity.controller.*.*(..)) or "
			+ "execution(* org.cisiondata.modules.elasticsearch.controller.ESController.*(..))";
	
	@Resource(name = "accessUserService")
	private IAccessUserService accessUserService = null;

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
		long startTime = System.currentTimeMillis();
		try {
			judgeSensitiveWord(proceedingJoinPoint.getArgs());
			String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
			String methodName = proceedingJoinPoint.getSignature().getName();
			LOG.info("------{} {} Log Start", className, methodName);
			Subject subject = SecurityUtils.getSubject();
			if (null != subject) {
				String account = (String) subject.getPrincipal();
				AccessUserControl accessUserControl = accessUserService.readAccessUserControlByAccount(account);
				long remainingCount = accessUserControl.getRemainingCount();
				if (remainingCount <= 0) throw new BusinessException("账户剩余查询条数不足");
				Object result = proceedingJoinPoint.proceed();
				long incOrDec = parseReturnResultCount(result);
				LOG.info("incOrDec : {}", incOrDec);
				accessUserService.updateRemainingCount(account, remainingCount, -incOrDec);
				return result;
			}
			LOG.info("------{} {} Log End ! Spend Time: {} s", className, methodName, 
					(System.currentTimeMillis() - startTime) / 1000);
		} catch (BusinessException be) {
			WebResult webResult = new WebResult();
			webResult.setCode(ResultCode.FAILURE.getCode());
			webResult.setFailure(be.getMessage());
			return webResult;
		}
		return null;
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private long parseReturnResultCount(Object result) {
    	if (result instanceof WebResult) {
    		WebResult webResult = (WebResult) result;
    		Object data = webResult.getData();
    		if (data instanceof List) {
    			return ((List) data).size();
    		} else if (data instanceof QueryResult) {
    			QueryResult queryResult = (QueryResult) data;
    			return queryResult.getResultList().size();
    		} else if (data instanceof Map) {
    			Map<String, Object> map = (Map<String, Object>) data;
    			long count = 0;
    			for (Map.Entry<String, Object> entry : map.entrySet()) {
    				Object value = entry.getValue();
    				if (value instanceof List) {
    					count += ((List) value).size();
    				}
    			}
    			return count;
    		}
    	}
    	return 0;
    }

}
