package org.cisiondata.modules.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.auth.entity.ResourceAttribute;
import org.cisiondata.modules.auth.entity.ResourceInterfaceField;
import org.cisiondata.modules.auth.entity.UserResource;
import org.cisiondata.modules.auth.entity.UserResourceAttribute;
import org.cisiondata.modules.user.dao.ResourceAttributeDAO;
import org.cisiondata.modules.user.dao.ResourceDAO;
import org.cisiondata.modules.user.dao.UserResourceAttributeDAO;
import org.cisiondata.modules.user.dao.UserResourceDAO;
import org.cisiondata.modules.user.service.IUserResourceFieldsService;
import org.cisiondata.modules.user.utils.Head;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.json.GsonUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("userResourceFieldsService")
public class UserResourceFieldsServiceImpl implements IUserResourceFieldsService,InitializingBean {

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
		Head head=new Head();
		head.setField("fieldEN");
		head.setFieldName("接口域英文");
		heads.add(head);
		head=new Head();
		head.setField("fieldCH");
		head.setFieldName("接口域中文");
		heads.add(head);
		head=new Head();
		head.setField("isDefault");
		head.setFieldName("是否显示");
		heads.add(head);
		head=new Head();
		head.setField("isLink");
		head.setFieldName("是否可链接");
		heads.add(head);
		head=new Head();
		head.setField("isLinkMap");
		head.setFieldName("是否可链接地图");
		heads.add(head);
		head=new Head();
		head.setField("encryptType");
		head.setFieldName("加密类型");
		heads.add(head);
		
	}
	
	@Override
	public Map<String,Object> findFieldsByUserIdAndResourceId(Long userId, Long resourceId ,Integer type) throws BusinessException {
		if (null == userId || null == resourceId) throw new BusinessException(ResultCode.PARAM_ERROR);
		
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("head", heads);
		
		//1.查询resource表中是否有此资源，并且deleteFlag为false
		Map<String,Object> resourceParams = new HashMap<String, Object>();
		resourceParams.put("id", resourceId);
		resourceParams.put("deleteFlag", false);
		resourceParams.put("type", type);
		List<org.cisiondata.modules.auth.entity.Resource> resourceList = resourceDAO.findByCondition(resourceParams);
		if(resourceList.size() == 0) throw new BusinessException(ResultCode.FAILURE.getCode(),"不存在此资源或此资源不可用");
		
		//2.查询resourceAttribute表中fields
		Map<String,Object> resourceAttributeParams = new HashMap<String,Object>();
		resourceAttributeParams.put("resourceId", resourceId);
		resourceAttributeParams.put("key", "fields");
		List<ResourceAttribute> resourceAttributeList = resourceAttributeDAO.findByCondition(resourceAttributeParams);
		if (resourceAttributeList.size() == 0) throw new BusinessException(ResultCode.FAILURE.getCode(),"不存在此资源或此资源不可用");
		ResourceAttribute resourceAttribute = resourceAttributeList.get(0);
		String resourceFields = resourceAttribute.getValue().trim();
		if (null == resourceFields || "".equals(resourceFields)) throw new BusinessException(ResultCode.FAILURE.getCode(),"不存在此资源或此资源不可用");
		//2.2将fields字符串转换为对象集合
		List<ResourceInterfaceField> commonFields = GsonUtils.fromJsonToList(resourceFields, ResourceInterfaceField.class);
		
		//3.查询userResource表中id
		Map<String,Object> userResourceParams = new HashMap<String, Object>();
		userResourceParams.put("userId", userId);
		userResourceParams.put("resourceId", resourceId);
		userResourceParams.put("priority", type);
		List<UserResource> userResourceList = userResourceDAO.findByCondition(userResourceParams);
		if (userResourceList.size() == 0) throw new BusinessException(ResultCode.FAILURE.getCode(),"未找到资源");
		Long userResourceId = userResourceList.get(0).getId();
		
		//4.查询userResourceAttribute表中fields属性
		Map<String,Object> userResourceAttributeParams = new HashMap<String, Object>();
		userResourceAttributeParams.put("userResourceId", userResourceId);
		userResourceAttributeParams.put("key", "fields");
		List<UserResourceAttribute> userResourceAttributeList = userResourceAttributeDAO.findByCondition(userResourceAttributeParams);
		
		
		//5.如果没有定制资源，直接返回公共模块中的字段
		if (userResourceAttributeList.size() == 0 ){
			result.put("data", commonFields);
			for (ResourceInterfaceField fi : commonFields) {
				fi.setId(userId);
				fi.setResourceId(resourceId);
			}
			return result;
		} else {
			//6.如果有定制资源，则将定制资源和公共模块合并，以定制资源为主
			UserResourceAttribute userResourceAttribute = userResourceAttributeList.get(0);
			String fields = userResourceAttribute.getValue();
			List<ResourceInterfaceField> userResourceFields = null;
			if (null == fields || "".equals(fields)) {
				userResourceFields = new ArrayList<ResourceInterfaceField>();
			} else {
				userResourceFields = GsonUtils.fromJsonToList(fields, ResourceInterfaceField.class);
			}
			Map<String,ResourceInterfaceField> userMap = new HashMap<String, ResourceInterfaceField>();
			for (ResourceInterfaceField fi : userResourceFields) {
				userMap.put(fi.getFieldEN(), fi);
			}
			for (ResourceInterfaceField fi : commonFields) {
				if (!userMap.containsKey(fi.getFieldEN())){
					ResourceInterfaceField field = new ResourceInterfaceField();
					field.setFieldCH(fi.getFieldCH());
					field.setFieldEN(fi.getFieldEN());
					field.setEncryptType(fi.getEncryptType());
					userResourceFields.add(field);
				}
			}
			for (ResourceInterfaceField fi : userResourceFields) {
				fi.setId(userId);
				fi.setResourceId(resourceId);
			}
			result.put("data", userResourceFields);
			return result;
		}
	}

	@Override
	public void updateFieldsByUserIdAndResourceId(Long userId, Long resourceId, String fields,Integer type) throws BusinessException {
		if (null == userId || null == resourceId || StringUtils.isBlank(fields)) throw new BusinessException(ResultCode.PARAM_ERROR);
		List<ResourceInterfaceField> list = null;
		try {
			list = formatFields(fields);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ResultCode.PARAM_FORMAT_ERROR);
		}
		String finalFields = GsonUtils.fromListToJson(list);
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("resourceId", resourceId);
		params.put("priority", type);
		List<UserResource> userResourceList = userResourceDAO.findByCondition(params);
		if (userResourceList.size() == 0) throw new BusinessException(ResultCode.FAILURE.getCode(),"不存在此资源或此资源不可用");
		Long userResourceId = userResourceList.get(0).getId();
		
		params = new HashMap<String, Object>();
		params.put("userResourceId", userResourceId);
		params.put("key", "fields");
		List<UserResourceAttribute> userResourceAttributeList = userResourceAttributeDAO.findByCondition(params);
		if (userResourceAttributeList.size() == 0){
			//新增
			UserResourceAttribute userResourceAttribute = new UserResourceAttribute();
			userResourceAttribute.setKey("fields");
			userResourceAttribute.setType("String");
			userResourceAttribute.setUserResourceId(userResourceId);
			userResourceAttribute.setValue(finalFields);
			userResourceAttributeDAO.addUserResourceAttribute(userResourceAttribute);
		} else {
			//修改
			UserResourceAttribute userResourceAttribute = new UserResourceAttribute();
			userResourceAttribute.setId(userResourceAttributeList.get(0).getId());
			userResourceAttribute.setValue(finalFields);
			userResourceAttributeDAO.updateUserResourceAttributeById(userResourceAttribute);
		}
		
	}

	private List<ResourceInterfaceField> formatFields(String fields){
		fields = fields.trim();
		String[] fieldsStr = fields.split(";");
		List<ResourceInterfaceField> list = new ArrayList<ResourceInterfaceField>();
		for (String s : fieldsStr) {
			ResourceInterfaceField re = new ResourceInterfaceField();
			String[] field = s.split(",");
			re.setFieldEN(field[0]);
			re.setFieldCH(field[1]);
			if ("true".equals(field[2])){
				re.setIsDefault(true);
			}
			if ("true".equals(field[3])) {
				re.setIsLink(true);
			}
			if ("true".equals(field[4])) {
				re.setIsLinkMap(true);
			}
			re.setEncryptType(Integer.valueOf(field[5]));
			list.add(re);
		}
		
		return list;
	}
	
}
