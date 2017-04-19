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
import org.cisiondata.modules.auth.entity.ResourceInterfaceField;
import org.cisiondata.modules.user.dao.ResourceAttributeDAO;
import org.cisiondata.modules.user.service.IResourceAttributeService;
import org.cisiondata.modules.user.utils.Head;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.json.GsonUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("iResourceAttributeService")
public class ResourceServiceAttributeImpl implements IResourceAttributeService,InitializingBean {

	private static String key="fields";
	
	@Resource(name="aresourceAttributeDAO")
	private ResourceAttributeDAO aresourceAttributeDAO;
	
	private List<Object> heads = new ArrayList<Object>();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Head head=new Head();
		head.setField("id");
		head.setFieldName("ID");
		heads.add(head);
		head=new Head();
		head.setField("resourceId");
		head.setFieldName("资源ID");
		heads.add(head);
		head=new Head();
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
	
	
	//根据id查询
	@Override
	public Map<String, Object> findByIdCondition(Long resourceid) throws BusinessException {
		if(resourceid==null) throw new BusinessException(ResultCode.PARAM_NULL);
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("resourceId", resourceid);
		params.put("key", key);
		List<ResourceAttribute> list=aresourceAttributeDAO.findByCondition(params);
		if(list.size()==0)throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		List<ResourceInterfaceField> lists=new ArrayList<ResourceInterfaceField>();
		for (ResourceAttribute resourceAttribute : list) {
			String value=resourceAttribute.getValue();
			lists = GsonUtils.fromJsonToList(value, ResourceInterfaceField.class);
			for (int i = 0,len = lists.size(); i < len; i++) {
				lists.get(i).setId(resourceAttribute.getId());
				lists.get(i).setResourceId(resourceAttribute.getResourceId());
			}
		}
		Map<String, Object> reMap=new HashMap<String,Object>();
		reMap.put("head", heads);
		reMap.put("data", lists);
		return reMap;
	}
	
	//根据resourceId和key修改
	@Override
	public void updateResourceAttributeByresourceId(Long resourceid,String fields ) throws BusinessException {
		if(StringUtils.isBlank(fields)||resourceid==null) throw new BusinessException(ResultCode.PARAM_NULL);
		List<ResourceInterfaceField> list = null;
		try {
			list = formatFields(fields);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ResultCode.PARAM_FORMAT_ERROR);
		}
		String value=GsonUtils.fromListToJson(list);
		ResourceAttribute resourceAttribute=new ResourceAttribute();
		resourceAttribute.setResourceId(resourceid);
		resourceAttribute.setKey(key);
		resourceAttribute.setValue(value);
		int i=aresourceAttributeDAO.updateResourceAttributeByresourceId(resourceAttribute);
		if(i==0) throw new BusinessException(ResultCode.FAILURE);
	}
	
	//新增
	@Override
	public long addResourceAttributeById(ResourceAttribute attribute) throws BusinessException {
		
		return 0;
	}

	//根据id删除
	@Override
	public void deleteById(Long id) throws BusinessException {

	}
	
	//解析字符串
	private List<ResourceInterfaceField> formatFields(String fields){
		fields = fields.trim();
		String[] fieldsStr = fields.split(";");
		List<ResourceInterfaceField> list = new ArrayList<ResourceInterfaceField>();
		for (String s : fieldsStr) {
			ResourceInterfaceField re = new ResourceInterfaceField();
			int jj=s.indexOf(",",s.indexOf(",")+1);
			String ff=s.substring(0,jj+1);
			String ss=s.replace(ff, "");
			String[] field = ss.split(",");
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
	// 费用查询
	@Override
	public List<ResourceCharging> findByIdResourceAttribute(Long id) throws BusinessException {
		if(id==null) throw new BusinessException(ResultCode.PARAM_NULL);
		ResourceAttribute resourceAttribute=	aresourceAttributeDAO.findByIdResourceAttribute(id);
		List<ResourceCharging> list=GsonUtils.fromJsonToList(resourceAttribute.getValue(), ResourceCharging.class);
		return list;
		
		
	}
	//费用保存
	@Override
	public String updateResourceAttribute(Long resourceid,String chargings) throws BusinessException {
		String result="";
		
		if(StringUtils.isBlank(chargings)||resourceid==null) throw new BusinessException(ResultCode.PARAM_NULL);
		String[] charging=chargings.split(":");
		List<ResourceCharging> list=new ArrayList<ResourceCharging>();
		for (String string : charging) {
			ResourceCharging charging2=new ResourceCharging();
			String[] str=string.split(",");
			charging2.setResultCode(Integer.parseInt(str[0]));
			charging2.setCharingType(Integer.parseInt(str[1]));
			charging2.setCost(Double.parseDouble(str[2]));
			list.add(charging2);
		}
		String value=GsonUtils.fromListToJson(list);
		ResourceAttribute resourceAttribute=new ResourceAttribute();
		resourceAttribute.setResourceId(resourceid);
		resourceAttribute.setKey("chargings");
		resourceAttribute.setValue(value);
		int i=	aresourceAttributeDAO.updateResourceAttribute(resourceAttribute);
		if(i>0){
			result="保存成功";
		}else{
			result="保存失败";
		}
		return result;
	}

	

}
