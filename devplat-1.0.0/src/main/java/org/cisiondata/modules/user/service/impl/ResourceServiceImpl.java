package org.cisiondata.modules.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.auth.entity.Resource;
import org.cisiondata.modules.auth.entity.ResourceAttribute;
import org.cisiondata.modules.auth.entity.ResourceInterfaceField;
import org.cisiondata.modules.auth.entity.UserResource;
import org.cisiondata.modules.auth.entity.UserResourceAttribute;
import org.cisiondata.modules.auth.web.WebUtils;
import org.cisiondata.modules.user.dao.ResourceAttributeDAO;
import org.cisiondata.modules.user.dao.ResourceDAO;
import org.cisiondata.modules.user.dao.UserDAO;
import org.cisiondata.modules.user.dao.UserResourceAttributeDAO;
import org.cisiondata.modules.user.dao.UserResourceDAO;
import org.cisiondata.modules.user.entity.AUser;
import org.cisiondata.modules.user.service.IResourceService;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.json.GsonUtils;
import org.springframework.stereotype.Service;

@Service("aresourceService")
public class ResourceServiceImpl implements IResourceService {

	@javax.annotation.Resource(name = "aresourceDAO")
	private ResourceDAO resourceDAO;
	
	@javax.annotation.Resource(name = "aresourceAttributeDAO")
	private ResourceAttributeDAO resourceAttributeDAO;
	
	@javax.annotation.Resource(name = "auserResourceDAO")
	private UserResourceDAO userResourceDAO;
	
	@javax.annotation.Resource(name = "auserResourceAttributeDAO")
	private UserResourceAttributeDAO userResourceAttributeDAO;
	
	@javax.annotation.Resource(name = "auserDAO")
	private UserDAO userDAO;
	
	
	@Override
	public List<ResourceInterfaceField> findAttributeByUrl(HttpServletRequest req) throws BusinessException {
		//获取用户类型
		String type = (String)req.getAttribute("accessUserType");
		//获取访问url
		String url = req.getServletPath();//url
		if (url.startsWith("/api/v1")) url = url.replace("/api/v1", "");
		if("app".equals(type)){
			return handleAppResultByUrl(url);
		} else if ("ext".equals(type)) {
			String accessId =  req.getParameter("accessId");
			return handleExtResultByUrl(accessId, url);
		} else if ("org".equals(type)) {
			String account = WebUtils.getCurrentAccout();//账号
			if(StringUtils.isBlank(account)) throw new BusinessException(ResultCode.FAILURE);
			return handleOrgResultByUrl(account, url);
		}
		return null; 
	}

	@Override
	public List<ResourceInterfaceField> findAttributeByIdentity(HttpServletRequest req,String identity) throws BusinessException {
		//获取用户类型
		String type = (String)req.getAttribute("accessUserType");
		if("app".equals(type)){
			return handleAppResultByIdentity(identity);
		} else if ("ext".equals(type)) {
			String accessId =  req.getParameter("accessId");
			return handleExtResultByIdentity(accessId, identity);
		} else if ("org".equals(type)) {
			String account = WebUtils.getCurrentAccout();//账号
			if(StringUtils.isBlank(account)) throw new BusinessException(ResultCode.FAILURE);
			return handleOrgResultByIdentity(account, identity);
		}
		return null; 
	}

	/**
	 * 根据镜世界用户账号和url获取定制字段
	 * @param account
	 * @param url
	 * @return
	 */
	private List<ResourceInterfaceField> handleOrgResultByUrl(String account,String url){
		//1.获取用户id
		AUser user = userDAO.findUser(account);
		if (null == user.getId()) throw new BusinessException(ResultCode.VERIFICATION_USER_FAIL);
		Long userId = user.getId();
		//2.获取resourceId
		Map<String,Object> resourceMap = new HashMap<String,Object>();
		resourceMap.put("url", url);
		resourceMap.put("type", new Integer(3));
		resourceMap.put("deleteFlag", Boolean.valueOf(false));
		List<Resource> resourceList = resourceDAO.findByCondition(resourceMap);
		if (resourceList.size() != 1) throw new BusinessException(ResultCode.RESOURCE_NOT_EXIST);
		Resource resource = resourceList.get(0);
		Long resourceId = resource.getId();
		if (null == resourceId) throw new BusinessException(ResultCode.RESOURCE_NOT_EXIST);
		
		//3.userResource表中是否有记录
		Map<String,Object> userResourceMap = new HashMap<String, Object>();
		userResourceMap.put("userId",userId );
		userResourceMap.put("resourceId", resourceId);
		userResourceMap.put("priority", new Integer(3));
		userResourceMap.put("deleteFlag", Boolean.valueOf(false));
		List<UserResource> userResourceList = userResourceDAO.findByCondition(userResourceMap);
		if (userResourceList.size() == 0 || null == userResourceList.get(0).getId()) {
			//返回默认
			Map<String,Object> resourceAttributeMap = new HashMap<String,Object>();
			resourceAttributeMap.put("resourceId", resourceId);
			resourceAttributeMap.put("key", "fields");
			List<ResourceAttribute> resourceAttributeList = resourceAttributeDAO.findByCondition(resourceAttributeMap);
			if (resourceAttributeList.size() == 0) return new ArrayList<ResourceInterfaceField>();
			ResourceAttribute resourceAttribute = resourceAttributeList.get(0);
			try {
				return GsonUtils.fromJsonToList(resourceAttribute.getValue(), ResourceInterfaceField.class);
			} catch (Exception e) {
				return new ArrayList<ResourceInterfaceField>();
			}
		} else {
			//返回定制
			UserResource userResource= userResourceList.get(0);
			Long userResourceId = userResource.getId();
			Map<String,Object> userResourceAttributeMap = new HashMap<String,Object>();
			userResourceAttributeMap.put("userResourceId", userResourceId);
			userResourceAttributeMap.put("key", "fields");
			List<UserResourceAttribute> userResourceAttributeList = userResourceAttributeDAO.findByCondition(userResourceAttributeMap);
			if (userResourceAttributeList.size() != 1) return new ArrayList<ResourceInterfaceField>();
			UserResourceAttribute userResourceAttribute = userResourceAttributeList.get(0);
			try {
				return GsonUtils.fromJsonToList(userResourceAttribute.getValue(), ResourceInterfaceField.class);
			} catch (Exception e) {
				return new ArrayList<ResourceInterfaceField>();
			}
		}
	}
	
	/**
	 * 根据accessId和url获取定制字段，适用于API用户
	 * @param accessId
	 * @param url
	 * @return
	 */
	private List<ResourceInterfaceField> handleExtResultByUrl(String accessId,String url){
		//1.获取用户id
		Long userId = userDAO.findUserIdByAccessId(accessId);
		if (null == userId) throw new BusinessException(ResultCode.VERIFICATION_USER_FAIL);
		
		//2.获取resourceId
		Map<String,Object> resourceMap = new HashMap<String,Object>();
		resourceMap.put("url", url);
		resourceMap.put("type", new Integer(5));
		resourceMap.put("deleteFlag", Boolean.valueOf(false));
		List<Resource> resourceList = resourceDAO.findByCondition(resourceMap);
		if (resourceList.size() != 1) throw new BusinessException(ResultCode.RESOURCE_NOT_EXIST);
		Resource resource = resourceList.get(0);
		Long resourceId = resource.getId();
		if (null == resourceId) throw new BusinessException(ResultCode.RESOURCE_NOT_EXIST);
		
		//3.userResource表中是否有记录
		Map<String,Object> userResourceMap = new HashMap<String, Object>();
		userResourceMap.put("userId",userId );
		userResourceMap.put("resourceId", resourceId);
		userResourceMap.put("priority", new Integer(5));
		userResourceMap.put("deleteFlag", Boolean.valueOf(false));
		List<UserResource> userResourceList = userResourceDAO.findByCondition(userResourceMap);
		if (userResourceList.size() == 0 || null == userResourceList.get(0).getId()) {
			//返回默认
			Map<String,Object> resourceAttributeMap = new HashMap<String,Object>();
			resourceAttributeMap.put("resourceId", resourceId);
			resourceAttributeMap.put("key", "fields");
			List<ResourceAttribute> resourceAttributeList = resourceAttributeDAO.findByCondition(resourceAttributeMap);
			if (resourceAttributeList.size() == 0) return new ArrayList<ResourceInterfaceField>();
			ResourceAttribute resourceAttribute = resourceAttributeList.get(0);
			try {
				return GsonUtils.fromJsonToList(resourceAttribute.getValue(), ResourceInterfaceField.class);
			} catch (Exception e) {
				return new ArrayList<ResourceInterfaceField>();
			}
		} else {
			//返回定制
			UserResource userResource= userResourceList.get(0);
			Long userResourceId = userResource.getId();
			Map<String,Object> userResourceAttributeMap = new HashMap<String,Object>();
			userResourceAttributeMap.put("userResourceId", userResourceId);
			userResourceAttributeMap.put("key", "fields");
			List<UserResourceAttribute> userResourceAttributeList = userResourceAttributeDAO.findByCondition(userResourceAttributeMap);
			if (userResourceAttributeList.size() != 1) return new ArrayList<ResourceInterfaceField>();
			UserResourceAttribute userResourceAttribute = userResourceAttributeList.get(0);
			try {
				return GsonUtils.fromJsonToList(userResourceAttribute.getValue(), ResourceInterfaceField.class);
			} catch (Exception e) {
				return new ArrayList<ResourceInterfaceField>();
			}
		}
	}
	/**
	 * 根据url获取定制字段，适用于app用户
	 * @param url
	 * @return
	 */
	private List<ResourceInterfaceField> handleAppResultByUrl(String url){
		//1.获取resourceId
		Map<String,Object> resourceMap = new HashMap<String,Object>();
		resourceMap.put("url", url);
		resourceMap.put("type", new Integer(6));
		resourceMap.put("deleteFlag", Boolean.valueOf(false));
		List<Resource> resourceList = resourceDAO.findByCondition(resourceMap);
		if (resourceList.size() != 1) throw new BusinessException(ResultCode.RESOURCE_NOT_EXIST);
		Resource resource = resourceList.get(0);
		Long resourceId = resource.getId();
		if (null == resourceId) throw new BusinessException(ResultCode.RESOURCE_NOT_EXIST);
		
		//返回默认
		Map<String,Object> resourceAttributeMap = new HashMap<String,Object>();
		resourceAttributeMap.put("resourceId", resourceId);
		resourceAttributeMap.put("key", "fields");
		List<ResourceAttribute> resourceAttributeList = resourceAttributeDAO.findByCondition(resourceAttributeMap);
		if (resourceAttributeList.size() == 0) return new ArrayList<ResourceInterfaceField>();
		ResourceAttribute resourceAttribute = resourceAttributeList.get(0);
		try {
			return GsonUtils.fromJsonToList(resourceAttribute.getValue(), ResourceInterfaceField.class);
		} catch (Exception e) {
			return new ArrayList<ResourceInterfaceField>();
		}
	}
	
	/**
	 * 根据账号和资源identity获取定制字段，适用于镜世界用户
	 * @param account
	 * @param identity
	 * @return
	 */
	private List<ResourceInterfaceField> handleOrgResultByIdentity(String account,String identity){
		//1.获取用户id
		AUser user = userDAO.findUser(account);
		if (null == user.getId()) throw new BusinessException(ResultCode.VERIFICATION_USER_FAIL);
		Long userId = user.getId();
		//2.获取resourceId
		Map<String,Object> resourceMap = new HashMap<String,Object>();
		resourceMap.put("identity", identity);
		resourceMap.put("type", new Integer(3));
		resourceMap.put("deleteFlag", Boolean.valueOf(false));
		List<Resource> resourceList = resourceDAO.findByCondition(resourceMap);
		if (resourceList.size() != 1) throw new BusinessException(ResultCode.RESOURCE_NOT_EXIST);
		Resource resource = resourceList.get(0);
		Long resourceId = resource.getId();
		if (null == resourceId) throw new BusinessException(ResultCode.RESOURCE_NOT_EXIST);
		
		//3.userResource表中是否有记录
		Map<String,Object> userResourceMap = new HashMap<String, Object>();
		userResourceMap.put("userId",userId );
		userResourceMap.put("resourceId", resourceId);
		userResourceMap.put("priority", new Integer(3));
		userResourceMap.put("deleteFlag", Boolean.valueOf(false));
		List<UserResource> userResourceList = userResourceDAO.findByCondition(userResourceMap);
		if (userResourceList.size() == 0 || null == userResourceList.get(0).getId()) {
			//返回默认
			Map<String,Object> resourceAttributeMap = new HashMap<String,Object>();
			resourceAttributeMap.put("resourceId", resourceId);
			resourceAttributeMap.put("key", "fields");
			List<ResourceAttribute> resourceAttributeList = resourceAttributeDAO.findByCondition(resourceAttributeMap);
			if (resourceAttributeList.size() == 0) return new ArrayList<ResourceInterfaceField>();
			ResourceAttribute resourceAttribute = resourceAttributeList.get(0);
			try {
				return GsonUtils.fromJsonToList(resourceAttribute.getValue(), ResourceInterfaceField.class);
			} catch (Exception e) {
				return new ArrayList<ResourceInterfaceField>();
			}
		} else {
			//返回定制
			UserResource userResource= userResourceList.get(0);
			Long userResourceId = userResource.getId();
			Map<String,Object> userResourceAttributeMap = new HashMap<String,Object>();
			userResourceAttributeMap.put("userResourceId", userResourceId);
			userResourceAttributeMap.put("key", "fields");
			List<UserResourceAttribute> userResourceAttributeList = userResourceAttributeDAO.findByCondition(userResourceAttributeMap);
			if (userResourceAttributeList.size() != 1) return new ArrayList<ResourceInterfaceField>();
			UserResourceAttribute userResourceAttribute = userResourceAttributeList.get(0);
			try {
				return GsonUtils.fromJsonToList(userResourceAttribute.getValue(), ResourceInterfaceField.class);
			} catch (Exception e) {
				return new ArrayList<ResourceInterfaceField>();
			}
		}
	}
	
	/**
	 * 根据accessId和资源identity获取定制字段，适用于API用户
	 * @param accessId
	 * @param identity
	 * @return
	 */
	private List<ResourceInterfaceField> handleExtResultByIdentity(String accessId,String identity){
		//1.获取用户id
		Long userId = userDAO.findUserIdByAccessId(accessId);
		if (null == userId) throw new BusinessException(ResultCode.VERIFICATION_USER_FAIL);
		
		//2.获取resourceId
		Map<String,Object> resourceMap = new HashMap<String,Object>();
		resourceMap.put("identity", identity);
		resourceMap.put("type", new Integer(5));
		resourceMap.put("deleteFlag", Boolean.valueOf(false));
		List<Resource> resourceList = resourceDAO.findByCondition(resourceMap);
		if (resourceList.size() != 1) throw new BusinessException(ResultCode.RESOURCE_NOT_EXIST);
		Resource resource = resourceList.get(0);
		Long resourceId = resource.getId();
		if (null == resourceId) throw new BusinessException(ResultCode.RESOURCE_NOT_EXIST);
		
		//3.userResource表中是否有记录
		Map<String,Object> userResourceMap = new HashMap<String, Object>();
		userResourceMap.put("userId",userId );
		userResourceMap.put("resourceId", resourceId);
		userResourceMap.put("priority", new Integer(5));
		userResourceMap.put("deleteFlag", Boolean.valueOf(false));
		List<UserResource> userResourceList = userResourceDAO.findByCondition(userResourceMap);
		if (userResourceList.size() == 0 || null == userResourceList.get(0).getId()) {
			//返回默认
			Map<String,Object> resourceAttributeMap = new HashMap<String,Object>();
			resourceAttributeMap.put("resourceId", resourceId);
			resourceAttributeMap.put("key", "fields");
			List<ResourceAttribute> resourceAttributeList = resourceAttributeDAO.findByCondition(resourceAttributeMap);
			if (resourceAttributeList.size() == 0) return new ArrayList<ResourceInterfaceField>();
			ResourceAttribute resourceAttribute = resourceAttributeList.get(0);
			try {
				return GsonUtils.fromJsonToList(resourceAttribute.getValue(), ResourceInterfaceField.class);
			} catch (Exception e) {
				return new ArrayList<ResourceInterfaceField>();
			}
		} else {
			//返回定制
			UserResource userResource= userResourceList.get(0);
			Long userResourceId = userResource.getId();
			Map<String,Object> userResourceAttributeMap = new HashMap<String,Object>();
			userResourceAttributeMap.put("userResourceId", userResourceId);
			userResourceAttributeMap.put("key", "fields");
			List<UserResourceAttribute> userResourceAttributeList = userResourceAttributeDAO.findByCondition(userResourceAttributeMap);
			if (userResourceAttributeList.size() != 1) return new ArrayList<ResourceInterfaceField>();
			UserResourceAttribute userResourceAttribute = userResourceAttributeList.get(0);
			try {
				return GsonUtils.fromJsonToList(userResourceAttribute.getValue(), ResourceInterfaceField.class);
			} catch (Exception e) {
				return new ArrayList<ResourceInterfaceField>();
			}
		}
	}
	
	/**
	 * 根据identity获取定制字段，适用于app用户
	 * @param identity
	 * @return
	 */
	private List<ResourceInterfaceField> handleAppResultByIdentity(String identity){
		//1.获取resourceId
		Map<String,Object> resourceMap = new HashMap<String,Object>();
		resourceMap.put("identity", identity);
		resourceMap.put("type", new Integer(6));
		resourceMap.put("deleteFlag", Boolean.valueOf(false));
		List<Resource> resourceList = resourceDAO.findByCondition(resourceMap);
		if (resourceList.size() != 1) throw new BusinessException(ResultCode.RESOURCE_NOT_EXIST);
		Resource resource = resourceList.get(0);
		Long resourceId = resource.getId();
		if (null == resourceId) throw new BusinessException(ResultCode.RESOURCE_NOT_EXIST);
		
		//返回默认
		Map<String,Object> resourceAttributeMap = new HashMap<String,Object>();
		resourceAttributeMap.put("resourceId", resourceId);
		resourceAttributeMap.put("key", "fields");
		List<ResourceAttribute> resourceAttributeList = resourceAttributeDAO.findByCondition(resourceAttributeMap);
		if (resourceAttributeList.size() == 0) return new ArrayList<ResourceInterfaceField>();
		ResourceAttribute resourceAttribute = resourceAttributeList.get(0);
		try {
			return GsonUtils.fromJsonToList(resourceAttribute.getValue(), ResourceInterfaceField.class);
		} catch (Exception e) {
			return new ArrayList<ResourceInterfaceField>();
		}
	}
	
}
