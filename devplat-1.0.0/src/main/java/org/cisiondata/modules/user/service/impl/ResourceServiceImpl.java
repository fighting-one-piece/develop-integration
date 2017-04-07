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
import org.cisiondata.modules.auth.web.WebUtils;
import org.cisiondata.modules.user.dao.ResourceAttributeDAO;
import org.cisiondata.modules.user.dao.ResourceDAO;
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
	
	@Override
	public List<ResourceInterfaceField> findAttributeByUrl(HttpServletRequest req) throws BusinessException {
		String url = req.getServletPath();//url
		if (url.startsWith("/api/v1")) url = url.replace("/api/v1", "");
		String account = WebUtils.getCurrentAccout();//账号
		if(StringUtils.isBlank(account)) throw new BusinessException(ResultCode.FAILURE);
		// TODO 此处应先查找user_resource_attribute表中是否有数据，没有才使用resource_attribute表中的默认配置
		
		
		//查询默认配置
		Map<String,Object> resourceMap = new HashMap<String,Object>();
		resourceMap.put("url", url);
		List<Resource> resourceList = resourceDAO.findByCondition(resourceMap);
		if (resourceList.size() != 1) return new ArrayList<ResourceInterfaceField>();
		
		Resource resource = resourceList.get(0);
		
		Map<String,Object> resourceAttributeMap = new HashMap<String,Object>();
		resourceAttributeMap.put("resourceId", resource.getId());
		resourceAttributeMap.put("key", "fields");
		List<ResourceAttribute> resourceAttributeList = resourceAttributeDAO.findByCondition(resourceAttributeMap);
		if (resourceAttributeList.size() != 1) return new ArrayList<ResourceInterfaceField>();
		
		ResourceAttribute resourceAttribute = resourceAttributeList.get(0);
		String fields = resourceAttribute.getValue();
		if(StringUtils.isBlank(fields)) return new ArrayList<ResourceInterfaceField>();
		try{
			List<ResourceInterfaceField> list = GsonUtils.fromJsonToList(fields, ResourceInterfaceField.class);
			return list;
		} catch(Exception e){
			return new ArrayList<ResourceInterfaceField>();
		}
		
	}

	@Override
	public List<ResourceInterfaceField> findAttributeByIdentity(String identity) throws BusinessException {
		String account = WebUtils.getCurrentAccout();//账号
		if(StringUtils.isBlank(account)) throw new BusinessException(ResultCode.FAILURE);
		// TODO 此处应先查找user_resource_attribute表中是否有数据，没有才使用resource_attribute表中的默认配置
		
		
		//查询默认配置
		Map<String,Object> resourceMap = new HashMap<String,Object>();
		resourceMap.put("identity", identity);
		List<Resource> resourceList = resourceDAO.findByCondition(resourceMap);
		if (resourceList.size() != 1) return new ArrayList<ResourceInterfaceField>();
		
		Resource resource = resourceList.get(0);
		
		Map<String,Object> resourceAttributeMap = new HashMap<String,Object>();
		resourceAttributeMap.put("resourceId", resource.getId());
		resourceAttributeMap.put("key", "fields");
		List<ResourceAttribute> resourceAttributeList = resourceAttributeDAO.findByCondition(resourceAttributeMap);
		if (resourceAttributeList.size() != 1) return new ArrayList<ResourceInterfaceField>();
		
		ResourceAttribute resourceAttribute = resourceAttributeList.get(0);
		String fields = resourceAttribute.getValue();
		if(StringUtils.isBlank(fields)) return new ArrayList<ResourceInterfaceField>();
		try{
			List<ResourceInterfaceField> list = GsonUtils.fromJsonToList(fields, ResourceInterfaceField.class);
			return list;
		} catch(Exception e){
			return new ArrayList<ResourceInterfaceField>();
		}
	}

}
