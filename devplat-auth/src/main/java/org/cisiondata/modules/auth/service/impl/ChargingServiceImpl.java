package org.cisiondata.modules.auth.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.auth.dao.ResourceAttributeDAO;
import org.cisiondata.modules.auth.dao.ResourceDAO;
import org.cisiondata.modules.auth.dao.UserAttributeDAO;
import org.cisiondata.modules.auth.dao.UserIntegrationDAO;
import org.cisiondata.modules.auth.dao.UserResourceAttributeDAO;
import org.cisiondata.modules.auth.dao.UserResourceDAO;
import org.cisiondata.modules.auth.entity.ResourceAttribute;
import org.cisiondata.modules.auth.entity.ResourceCharging;
import org.cisiondata.modules.auth.entity.ResourceChargingType;
import org.cisiondata.modules.auth.entity.ResourceType;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.auth.entity.UserAttribute;
import org.cisiondata.modules.auth.entity.UserResource;
import org.cisiondata.modules.auth.entity.UserResourceAttribute;
import org.cisiondata.modules.auth.service.IResourceService;
import org.cisiondata.modules.auth.web.WebUtils;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.json.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("chargingService")
public class ChargingServiceImpl extends RequestServiceImpl {

	private Logger LOG = LoggerFactory.getLogger(ChargingServiceImpl.class);
	
	@Resource(name = "userIntegrationDAO")
	private UserIntegrationDAO userIntegrationDAO = null;
	
	@Resource(name = "userAttributeDAO")
	private UserAttributeDAO userAttributeDAO = null;
	
	@Resource(name = "resourceService")
	private IResourceService resourceService = null;
	
	@Resource(name = "resourceDAO")
	private ResourceDAO resourceDAO = null;
	
	@Resource(name = "resourceAttributeDAO")
	private ResourceAttributeDAO resourceAttributeDAO = null;
	
	@Resource(name = "userResourceDAO")
	private UserResourceDAO userResourceDAO = null;
	
	@Resource(name = "userResourceAttributeDAO")
	private UserResourceAttributeDAO userResourceAttributeDAO = null;
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Object[] preHandle(HttpServletRequest request) throws BusinessException {
		String path = request.getServletPath();
		if (path.startsWith("/app")) {
			path = path.replace("/app", "");
			request.setAttribute("accessUserType", "app");
		} else if (path.startsWith("/ext")) {
			path = path.replace("/ext", "");
			request.setAttribute("accessUserType", "ext");
		} else if (path.startsWith("/api/v1")) {
			path = path.replace("/api/v1", "");
			request.setAttribute("accessUserType", "org");
		} else {
			request.setAttribute("accessUserType", "org");
		}
		String type = (String)request.getAttribute("accessUserType");
		
		if("org".equals(type)){
			if (!resourceService.isInResource(path, ResourceType.INTERFACE.value())) {
				request.setAttribute("isInResource", Boolean.valueOf(false));
				return new Object[]{true};
			}
			String account = WebUtils.getCurrentAccout();
			if (StringUtils.isBlank(account)) return new Object[]{false,new BusinessException(ResultCode.VERIFICATION_USER_FAIL)};
			Double money = readRemainingMoneyByAccount(account);
			if(null == money || money <= 0) return new Object[]{false,new BusinessException(ResultCode.ACCOUNT_BALANCE_INSUFFICIENT)};
			request.setAttribute("isInResource", Boolean.valueOf(true));
			return new Object[]{true};
		} else if("app".equals(type)){
			request.setAttribute("isInResource", Boolean.valueOf(false));
			return new Object[]{true};
		} else if ("ext".equals(type)){
			if (!resourceService.isInResource(path, ResourceType.API.value())) return new Object[]{false,new BusinessException(ResultCode.RESOURCE_NOT_EXIST)};
			Map<String, String[]> requestParams = request.getParameterMap();
			String accessId = requestParams.get("accessId")[0];
			Double money = readReaminingMoneyByAccessId(accessId);
			if(null == money || money <= 0 ) return new Object[]{false,new BusinessException(ResultCode.ACCOUNT_BALANCE_INSUFFICIENT)};
			request.setAttribute("isInResource", Boolean.valueOf(true));
			return new Object[]{true};
		} 
		
		return new Object[]{false};
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object[] postHandle(HttpServletRequest request, Object result) throws BusinessException {
		if (!(Boolean)request.getAttribute("isInResource")) return new Object[]{true};
		String path = request.getServletPath().replace("/app", "").replace("/ext", "").replace("/api/v1", "");
		String type = (String)request.getAttribute("accessUserType");
		if("org".equals(type)){
			org.cisiondata.modules.auth.entity.Resource resource = resourceService.readResourceFromCache(path, Integer.valueOf(3));
			User user = WebUtils.getCurrentUser();
			if (null == user || null == user.getId()) return new Object[]{false,new BusinessException(ResultCode.VERIFICATION_USER_FAIL)};
			String account = user.getAccount();
			if (StringUtils.isBlank(account)) return new Object[]{false,new BusinessException(ResultCode.VERIFICATION_USER_FAIL)};
			Long userId = user.getId();
			Long resourceId = resource.getId();
			List<ResourceCharging> chargingList = readResourceChargingsByUserIdAndResourceId(userId, resourceId, Integer.valueOf(3));
			double incOrDec = calculateIncOrDec(result, chargingList);
			LOG.info("incOrDec : {}", incOrDec);
			//修改金额
			Double remainingMoney = readRemainingMoneyByAccount(account);
			if(remainingMoney <= 0) return new Object[]{false,new BusinessException(ResultCode.ACCOUNT_BALANCE_INSUFFICIENT)};
			updateRemainingMoneyByUserId(userId,remainingMoney - incOrDec);
			
		} else if("app".equals(type)){
			return new Object[]{true};
		} else if ("ext".equals(type)){
			org.cisiondata.modules.auth.entity.Resource resource = resourceService.readResourceFromCache(path, Integer.valueOf(5));
			Map<String, String[]> requestParams = request.getParameterMap();
			String accessId = requestParams.get("accessId")[0];
			Long userId = userIntegrationDAO.readUserIdByAttribute("accessId", accessId);
			Long resourceId = resource.getId();
			List<ResourceCharging> chargingList = null;
			try {
				chargingList = readResourceChargingsByUserIdAndResourceId(userId, resourceId, Integer.valueOf(5));
			} catch (BusinessException bu) {
				return new Object[]{false,bu};
			} catch (Exception e) {
				return new Object[]{false, new BusinessException(ResultCode.SYSTEM_IS_BUSY)};
			}
			double incOrDec = calculateIncOrDec(result, chargingList);
			LOG.info("incOrDec : {}", incOrDec);
			Double remainingMoney = readReaminingMoneyByAccessId(accessId);
			if(remainingMoney <= 0) return new Object[]{false,new BusinessException(ResultCode.ACCOUNT_BALANCE_INSUFFICIENT)};
			updateRemainingMoneyByUserId(userId,remainingMoney - incOrDec);
		} 
		
		return new Object[]{true};
	}
	
	private void updateRemainingMoneyByUserId(Long userId,Double remainingMoney){
		UserAttribute userAttribute = new UserAttribute();
		userAttribute.setUserId(userId);
		userAttribute.setKey("remainingMoney");
		userAttribute.setValue(String.valueOf(remainingMoney));
		userAttributeDAO.update(userAttribute);
	}
	
	public Double readRemainingMoneyByAccount(String account){
		Query query = new Query();
		query.addCondition("account", account);
		query.addCondition("identity", "3");
		query.addCondition("deleteFlag", Boolean.valueOf(false));
		User user = userIntegrationDAO.readDataByCondition(query);
		if (null == user) return null;
		return user.getRemainingMoney();
	}
	
	public Double readReaminingMoneyByAccessId(String accessId){
		Long userId = userIntegrationDAO.readUserIdByAttribute("accessId", accessId);
		if(userId == null) return null;
		Query query = new Query();
		query.addCondition("id", userId);
		query.addCondition("identity", "5");
		query.addCondition("deleteFlag", Boolean.valueOf(false));
		User user = userIntegrationDAO.readDataByCondition(query);
		if (null == user) return null;
		return user.getRemainingMoney();
	}
	
	public List<ResourceCharging> readResourceChargingsByUserIdAndResourceId(Long userId,Long resourceId,Integer priority){
		Query query = new Query();
		query.addCondition("userId", userId);
		query.addCondition("resourceId", resourceId);
		query.addCondition("deleteFlag", Boolean.valueOf(false));
		query.addCondition("priority", priority);
		UserResource userResource = userResourceDAO.readDataByCondition(query);
		if (null == userResource || null == userResource.getId()) {
			//使用默认
			query = new Query();
			query.addCondition("resourceId", resourceId);
			query.addCondition("key", "chargings");
			ResourceAttribute resourceAttribute = resourceAttributeDAO.readDataByCondition(query);
			if(null == resourceAttribute || null == resourceAttribute.getId()) throw new BusinessException(ResultCode.RESOURCE_NOT_EXIST);
			String chargings = resourceAttribute.getValue();
			List<ResourceCharging> list = new ArrayList<ResourceCharging>();
			try {
				list = GsonUtils.fromJsonToList(chargings, ResourceCharging.class);
			} catch (Exception e) {
				throw new BusinessException(ResultCode.SYSTEM_IS_BUSY);
			}
			return list;
		} else {
			//使用定制价格
			Long userResourceId = userResource.getId();
			query = new Query();
			query.addCondition("userResourceId", userResourceId);
			query.addCondition("key", "chargings");
			UserResourceAttribute userResourceAttribute = userResourceAttributeDAO.readDataByCondition(query);
			if (null == userResourceAttribute || null == userResourceAttribute.getId()) {
				//使用默认价格
				query = new Query();
				query.addCondition("resourceId", resourceId);
				query.addCondition("key", "chargings");
				ResourceAttribute resourceAttribute = resourceAttributeDAO.readDataByCondition(query);
				if(null == resourceAttribute || null == resourceAttribute.getId()) throw new BusinessException(ResultCode.RESOURCE_NOT_EXIST);
				String chargings = resourceAttribute.getValue();
				List<ResourceCharging> list = new ArrayList<ResourceCharging>();
				try {
					list = GsonUtils.fromJsonToList(chargings, ResourceCharging.class);
				} catch (Exception e) {
					throw new BusinessException(ResultCode.SYSTEM_IS_BUSY);
				}
				return list;
			}
			String chargings = userResourceAttribute.getValue();
			List<ResourceCharging> list = new ArrayList<ResourceCharging>();
			try {
				list = GsonUtils.fromJsonToList(chargings, ResourceCharging.class);
			} catch (Exception e) {
				throw new BusinessException(ResultCode.SYSTEM_IS_BUSY);
			}
			return list;
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
    				if("head".equals(entry.getKey())) continue;
    				Object value = entry.getValue();
    				if (value instanceof QueryResult){
    					QueryResult queryResult = (QueryResult) value;
    					return queryResult.getResultList().size();
    				}
    				if (value instanceof List) {
    					count += ((List) value).size();
    				}
    			}
    			return count;
    		}
    	}
    	return 0;
    }
	
	private ResourceCharging parseResultPrice(Object result,List<ResourceCharging> list){
		if(null == list) return null;
		if (result instanceof WebResult) {
    		WebResult webResult = (WebResult) result;
    		Integer code = webResult.getCode();
    		for (ResourceCharging rc : list){
    			if(code == rc.getResultCode()) return rc;
    		}
		}
		return null;
	}
	
	private double calculateIncOrDec(Object result,List<ResourceCharging> list){
		ResourceCharging resourceCharging = parseResultPrice(result,list);
		if (null == resourceCharging) return 0;
		if (ResourceChargingType.RESULT_COUNT.value() == resourceCharging.getCharingType()){
			Long count = parseReturnResultCount(result);
			return count * resourceCharging.getCost();
		} else if (ResourceChargingType.QUERY_TIMES.value() == resourceCharging.getCharingType()) {
			return resourceCharging.getCost();
		} else {
			return 0;
		}
	}
	
}
