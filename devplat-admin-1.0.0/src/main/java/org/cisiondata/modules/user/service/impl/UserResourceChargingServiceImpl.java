package org.cisiondata.modules.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.auth.entity.ResourceAttribute;
import org.cisiondata.modules.auth.entity.ResourceCharging;
import org.cisiondata.modules.auth.entity.UserResource;
import org.cisiondata.modules.auth.entity.UserResourceAttribute;
import org.cisiondata.modules.user.dao.ResourceAttributeDAO;
import org.cisiondata.modules.user.dao.ResourceDAO;
import org.cisiondata.modules.user.dao.UserResourceAttributeDAO;
import org.cisiondata.modules.user.dao.UserResourceDAO;
import org.cisiondata.modules.user.service.IUserResourceChargingService;
import org.cisiondata.modules.user.utils.Head;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.json.GsonUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("userResourceChargingService")
public class UserResourceChargingServiceImpl implements IUserResourceChargingService,InitializingBean {

	@Resource(name = "aresourceDAO")
	private ResourceDAO resourceDAO;
	
	@Resource(name = "aresourceAttributeDAO")
	private ResourceAttributeDAO resourceAttributeDAO;
	
	@Resource(name = "auserResourceDAO")
	private UserResourceDAO userResourceDAO;
	
	@Resource(name = "auserResourceAttributeDAO")
	private UserResourceAttributeDAO userResourceAttributeDAO;
	
	private List<Object> heads = new ArrayList<Object>();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Head head = new Head();
		head.setField("resultCode");
		head.setFieldName("返回结果代码");
		heads.add(head);
		head = new Head();
		head.setField("charingType");
		head.setFieldName("收费类型");
		heads.add(head);
		head = new Head();
		head.setField("cost");
		head.setFieldName("费用");
		heads.add(head);
		
		
	}
	
	@Override
	public Map<String,Object> findChargingsByUserIdAndResourceId(Long userId, Long resourceId, Integer type)
			throws BusinessException {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("head", heads);
		//1.查询resource表中是否有此资源，并且deleteFlag为false
		Map<String,Object> resourceParams = new HashMap<String, Object>();
		resourceParams.put("id", resourceId);
		resourceParams.put("deleteFlag", false);
		resourceParams.put("type", type);
		List<org.cisiondata.modules.auth.entity.Resource> resourceList = resourceDAO.findByCondition(resourceParams);
		if(resourceList.size() == 0) throw new BusinessException(ResultCode.FAILURE.getCode(),"不存在此资源或此资源不可用");
		
		//2.查询userResource表中是否有此资源，有则获取userResourceId
		Map<String,Object> userResourceParams = new HashMap<String, Object>();
		userResourceParams.put("userId", userId);
		userResourceParams.put("resourceId", resourceId);
		userResourceParams.put("priority", type);
		List<UserResource> userResourceList = userResourceDAO.findByCondition(userResourceParams);
		if (userResourceList.size() == 0) throw new BusinessException(ResultCode.FAILURE.getCode(),"未找到资源");
		Long userResourceId = userResourceList.get(0).getId();
		
		//3.查询userResourceAttribute表中charging属性
		Map<String,Object> userResourceAttributeParams = new HashMap<String, Object>();
		userResourceAttributeParams.put("userResourceId", userResourceId);
		userResourceAttributeParams.put("key", "chargings");
		List<UserResourceAttribute> userResourceAttributeList = userResourceAttributeDAO.findByCondition(userResourceAttributeParams);
		
		//4.如果userResourceAttribute没有记录，则返回公共计费
		if (userResourceAttributeList.size() == 0) {
			Map<String,Object> resourceAttributeParams = new HashMap<String,Object>();
			resourceAttributeParams.put("resourceId", resourceId);
			resourceAttributeParams.put("key", "chargings");
			List<ResourceAttribute> resourceAttributeList = resourceAttributeDAO.findByCondition(resourceAttributeParams);
			if (resourceAttributeList.size() == 0) throw new BusinessException(ResultCode.FAILURE.getCode(),"不存在此资源或此资源不可用");
			ResourceAttribute resourceAttribute = resourceAttributeList.get(0);
			String resourceChargings = resourceAttribute.getValue().trim();
			if (null == resourceChargings || "".equals(resourceChargings)) throw new BusinessException(ResultCode.FAILURE.getCode(),"不存在此资源或此资源不可用");
			//2.2将fields字符串转换为对象集合
			result.put("data", GsonUtils.fromJsonToList(resourceChargings, ResourceCharging.class));
			return result;
		} else {
			//5.如果userResourceAttribute有记录，则直接返回
			String chargings = userResourceAttributeList.get(0).getValue();
			if (StringUtils.isBlank(chargings)){
				result.put("data", new ArrayList<ResourceCharging>());
				return result;
			}
			result.put("data", GsonUtils.fromJsonToList(chargings, ResourceCharging.class));
			return result;
		}
	}

	@Override
	public void updateChargingsByUserIdAndResourceId(Long userId, Long resourceId, String chargings,Integer type)
			throws BusinessException {
		if (null == userId || null == resourceId || StringUtils.isBlank(chargings)) throw new BusinessException(ResultCode.PARAM_ERROR);
		List<ResourceCharging> list = null;
		try {
			list = formatChargings(chargings);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ResultCode.PARAM_FORMAT_ERROR);
		}
		
		String finalChargings = GsonUtils.fromListToJson(list);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("resourceId", resourceId);
		params.put("priority", type);
		List<UserResource> userResourceList = userResourceDAO.findByCondition(params);
		if (userResourceList.size() == 0) throw new BusinessException(ResultCode.FAILURE.getCode(),"不存在此资源或此资源不可用");
		Long userResourceId = userResourceList.get(0).getId();
		
		params = new HashMap<String, Object>();
		params.put("userResourceId", userResourceId);
		params.put("key", "chargings");
		List<UserResourceAttribute> userResourceAttributeList = userResourceAttributeDAO.findByCondition(params);
		if (userResourceAttributeList.size() == 0){
			//新增
			UserResourceAttribute userResourceAttribute = new UserResourceAttribute();
			userResourceAttribute.setKey("chargings");
			userResourceAttribute.setType("String");
			userResourceAttribute.setUserResourceId(userResourceId);
			userResourceAttribute.setValue(finalChargings);
			userResourceAttributeDAO.addUserResourceAttribute(userResourceAttribute);
		} else {
			//修改
			UserResourceAttribute userResourceAttribute = new UserResourceAttribute();
			userResourceAttribute.setId(userResourceAttributeList.get(0).getId());
			userResourceAttribute.setValue(finalChargings);
			userResourceAttributeDAO.updateUserResourceAttributeById(userResourceAttribute);
		}
		
	}
	
	private List<ResourceCharging> formatChargings(String chargings){
		chargings = chargings.trim();
		String[] chargingsStr = chargings.split(";");
		List<ResourceCharging> list = new ArrayList<ResourceCharging>();
		for (String s : chargingsStr) {
			ResourceCharging re = new ResourceCharging();
			String[] charging = s.split(",");
			re.setResultCode(Integer.valueOf(charging[0]));
			re.setCharingType(Integer.valueOf(charging[1]));
			re.setCost(Double.valueOf(charging[2]));
			list.add(re);
		}
		return list;
	}

}
