package org.cisiondata.modules.user.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.auth.entity.UserAttribute;
import org.cisiondata.modules.user.dao.AUserDAO;
import org.cisiondata.modules.user.dao.RoleUserDAO;
import org.cisiondata.modules.user.dao.UserAttributeDAO;
import org.cisiondata.modules.user.entity.AUser;
import org.cisiondata.modules.user.service.APIAUserService;
import org.cisiondata.modules.user.utils.Head;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
@Service("aPIAUserService")
public class APIAUserServiceImpl implements APIAUserService,InitializingBean{

	@Resource(name="auserDAO")
	private AUserDAO auserDAO;
	
	@Resource(name="auserAttributeDAO")
	private UserAttributeDAO userAttributeDAO;
	
	@Resource(name="roleUserDAO")
	private RoleUserDAO roleUserDao;
	
	private static String key1="accessId";
	private static String key2="accessKey";
	private List<Object> heads = new ArrayList<Object>();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Head head=new Head();
		head.setField("account");
		head.setFieldName("用户账号");
		heads.add(head);		
		head=new Head();
		head.setField("identity");
		head.setFieldName("用户标识");
		heads.add(head);
		head=new Head();
		head.setField("deleteFlag");
		head.setFieldName("停用");
		heads.add(head);
		head=new Head();
		head.setField("nickname");
		head.setFieldName("用户昵称");
		heads.add(head);
		head=new Head();
		head.setField("email");
		head.setFieldName("用户邮箱");
		heads.add(head);
		head=new Head();
		head.setField("status");
		head.setFieldName("用户状态");
		heads.add(head);
		head=new Head();
		head.setField("createTime");
		head.setFieldName("创建时间");
		heads.add(head);
		head=new Head();
		head.setField("expireTime");
		head.setFieldName("过期时间");
		heads.add(head);
		head=new Head();
		head.setField("accessId");
		head.setFieldName("ACCESS_ID");
		heads.add(head);
		head=new Head();
		head.setField("accessKey");
		head.setFieldName("ACCESS_KEY");
		heads.add(head);
	}
	
	@Override
	public Map<String, Object> findAPIAuser(Integer page, Integer pageSize, String identity) throws BusinessException {
		QueryResult<Map<String,Object>> result = new QueryResult<Map<String,Object>>();
		Map<String,Object> mapResult = new HashMap<String,Object>();
		if(page < 1 || pageSize < 1) throw new BusinessException(ResultCode.PARAM_ERROR);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("identity", identity);
		long counts = auserDAO.findCountAuser(params);
		int count=(int)counts;
		int pageCount = count % pageSize == 0 ? count/pageSize : count/pageSize + 1;
		int begin = (page-1) * pageSize;
		params = new HashMap<String,Object>();
		params.put("begin", begin);
		params.put("pageSize", pageSize);
		params.put("identity", identity);
		List<AUser> list = auserDAO.findAuser(params);
		if(list != null  && list.size() > 0){
			List<Map<String,Object>>  listMap = new ArrayList<Map<String,Object>>();
			for(int i = 0,len = list.size(); i < len;i++){
				Map<String,Object> mapList = new HashMap<String,Object>();
				mapList.put("id", list.get(i).getId());
				mapList.put("account", list.get(i).getAccount());
				mapList.put("identity", list.get(i).getIdentity());
				mapList.put("deleteFlag", list.get(i).getDeleteFlag());
				mapList.put("nickname", list.get(i).getNickname());
				mapList.put("email", list.get(i).getEmail());
				mapList.put("status", list.get(i).getStatus());
				String cra=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(list.get(i).getCreateTime());  
				mapList.put("createTime", cra);
				String exp=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(list.get(i).getExpireTime());
				mapList.put("expireTime", exp);
				UserAttribute attribute=new UserAttribute();
				attribute.setUserId(list.get(i).getId());
				attribute.setKey(key1);
				UserAttribute attribute1=userAttributeDAO.findAUserAttribte(attribute);
				if(attribute1==null){
					mapList.put("accessId", "null");
				}else{
					mapList.put("accessId", attribute1.getValue());
				}
				attribute.setKey(key2);
				UserAttribute attribute2=userAttributeDAO.findAUserAttribte(attribute);
				if(attribute2==null){
					mapList.put("accessKey", "null");
				}else{
					mapList.put("accessKey", attribute2.getValue());
				}
				listMap.add(mapList);
			}
			result.setTotalRowNum(pageCount);
			result.setResultList(listMap);
			mapResult.put("head", heads);
			mapResult.put("data", result);
		}else{
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		}
		return mapResult;
	}

	

}
