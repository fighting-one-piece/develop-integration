package org.cisiondata.modules.auth.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.auth.entity.AccessUserControl;
import org.cisiondata.modules.auth.service.IAccessUserService;
import org.cisiondata.modules.auth.service.IChargingService;
import org.cisiondata.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("countChargingService")
public class CountChargingServiceImpl implements IChargingService {
	
	private Logger LOG = LoggerFactory.getLogger(CountChargingServiceImpl.class);
	
	@Resource(name = "accessUserService")
	private IAccessUserService accessUserService = null;

	@Override
	public Object charge(ProceedingJoinPoint proceedingJoinPoint) throws BusinessException {
		Signature signature = proceedingJoinPoint.getSignature();
		long startTime = System.currentTimeMillis();
		Object result = null;
		try {
			String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
			LOG.info("------Class {} Method {} Log Start", className, signature.getName());
			Subject subject = SecurityUtils.getSubject();
			if (null != subject) {
				String account = (String) subject.getPrincipal();
				AccessUserControl accessUserControl = accessUserService.readAccessUserControlByAccount(account);
				long remainingCount = accessUserControl.getRemainingCount();
				if (remainingCount <= 0) throw new BusinessException("账户剩余查询条数不足");
				result = proceedingJoinPoint.proceed();
				long incOrDec = parseReturnResultCount(result);
				LOG.info("incOrDec : {}", incOrDec);
				accessUserService.updateRemainingCount(account, remainingCount, -incOrDec);
			}
			LOG.info("------Class {} Method {} Log End ! Spend Time: {} s", className, signature.getName(), 
					(System.currentTimeMillis() - startTime) / 1000);
		} catch (Throwable e) {
			LOG.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		return result;
	}
	
	@Override
	public boolean charge(String account, String requestUrl, Object result) throws BusinessException {
		return true;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private long parseReturnResultCount(Object result) {
    	if (!(result instanceof WebResult)) return 0;
		WebResult webResult = (WebResult) result;
		int resultCode = webResult.getCode();
		if (resultCode == ResultCode.SUCCESS.getCode()) {
    		Object data = webResult.getData();
    		if (data instanceof List) {
    			return ((List) data).size();
    		} else if (data instanceof QueryResult) {
    			QueryResult queryResult = (QueryResult) data;
    			return queryResult.getResultList().size();
    		} else if (data instanceof Map) {
    			Map<String, Object> map = (Map<String, Object>) data;
    			long count = 0;
    			boolean valueIsList = false;
    			for (Map.Entry<String, Object> entry : map.entrySet()) {
    				Object value = entry.getValue();
    				if (value instanceof List) {
    					count += ((List) value).size();
    					valueIsList = true;
    				}
    			}
    			return valueIsList ? count : 1;
    		} else if (data instanceof String) {
    			return 1;
    		}
		} else if (resultCode == 601) {
			return 1;
		}
    	return 0;
    }

}
