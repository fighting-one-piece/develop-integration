package org.cisiondata.utils.aspect;

import java.util.HashMap;
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
import org.cisiondata.modules.auth.entity.AccessInterface;
import org.cisiondata.modules.auth.entity.AccessUserControl;
import org.cisiondata.modules.auth.entity.AccessUserInterface;
import org.cisiondata.modules.auth.entity.AccessUserInterfaceMoney;
import org.cisiondata.modules.auth.service.IAccessUserService;
import org.cisiondata.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("moneyChargingService")
public class MoneyChargingServiceImpl implements IChargingService {
	
	private Logger LOG = LoggerFactory.getLogger(MoneyChargingServiceImpl.class);
	
	@Resource(name = "accessUserService")
	private IAccessUserService accessUserService = null;

	@Override
	public Object charge(ProceedingJoinPoint proceedingJoinPoint) throws BusinessException {
		Signature signature = proceedingJoinPoint.getSignature();
		long startTime = System.currentTimeMillis();
		Object result = null;
		try {
			String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
			String methodName = signature.getName();
			LOG.info("------Class {} Method {} Log Start", className, methodName);
			Subject subject = SecurityUtils.getSubject();
			if (null != subject) {
				String account = (String) subject.getPrincipal();
				AccessUserControl accessUserControl = accessUserService.readAccessUserControlByAccount(account);
				double remainingMoney = accessUserControl.getRemainingMoney();
				if (remainingMoney <= 0) throw new BusinessException("账户剩余查询金额不足");
				result = proceedingJoinPoint.proceed();
				double incOrDec = parseReturnResultMoney(account, className + "_" + methodName, result);
				LOG.info("incOrDec : {}", incOrDec);
				if (incOrDec != 0) {
					accessUserService.updateRemainingMoney(account, remainingMoney, -incOrDec);
				}
			}
			LOG.info("------Class {} Method {} Log End ! Spend Time: {} s", className, methodName, 
					(System.currentTimeMillis() - startTime) / 1000);
		} catch (Throwable e) {
			LOG.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private double parseReturnResultMoney(String account, String interfaceIdentity, Object result) {
		if (!(result instanceof WebResult)) return 0;
		WebResult webResult = (WebResult) result;
		int resultCode = webResult.getCode();
		if (resultCode == ResultCode.FAILURE.getCode()) return 0;
		AccessInterface accessInterface = accessUserService.readAccessInterfaceByIdentity(interfaceIdentity);
		if (null == accessInterface) return 0;
		AccessUserInterface accessUserInterface = accessUserService
				.readAccessUserInterfaceByAccountAndInterfaceId(account, accessInterface.getId());
		List<AccessUserInterfaceMoney> monies = accessUserInterface.getMonies();
		if (monies.size() == 0) return 0;
		Map<Integer, Double> codeMoneyMap = new HashMap<Integer, Double>();
		for (int i = 0, len = monies.size(); i < len; i++) {
			AccessUserInterfaceMoney accessUserInterfaceMoney = monies.get(i);
			codeMoneyMap.put(accessUserInterfaceMoney.getResponseCode(), accessUserInterfaceMoney.getMoney());
		}
		int dataCount = 0;
		if (resultCode == ResultCode.SUCCESS.getCode()) {
    		Object data = webResult.getData();
    		if (data instanceof List) {
    			dataCount = ((List) data).size();
    		} else if (data instanceof QueryResult) {
    			QueryResult queryResult = (QueryResult) data;
    			dataCount = queryResult.getResultList().size();
    		} else if (data instanceof Map) {
    			Map<String, Object> map = (Map<String, Object>) data;
    			int valueCount = 0;
    			boolean valueIsList = false;
    			for (Map.Entry<String, Object> entry : map.entrySet()) {
    				Object value = entry.getValue();
    				if (value instanceof List) {
    					valueCount += ((List) value).size();
    					valueIsList = true;
    				}
    			}
    			dataCount = valueIsList ? valueCount : 1;
    		} else if (data instanceof String) {
    			dataCount =  1;
    		}
		} else if (resultCode == ResultCode.NOT_BINDING_QQ.getCode()) {
			dataCount =  1;
		}
		return null == codeMoneyMap.get(resultCode) ? 0 : codeMoneyMap.get(resultCode) * dataCount;
	}

}
