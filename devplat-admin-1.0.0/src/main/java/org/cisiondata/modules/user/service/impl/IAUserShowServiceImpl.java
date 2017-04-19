package org.cisiondata.modules.user.service.impl;

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
import org.cisiondata.modules.user.service.IAUserShowService;
import org.cisiondata.modules.user.utils.Head;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("iAUserShowServiceImpl")
public class IAUserShowServiceImpl implements IAUserShowService,InitializingBean{
	
	@Resource(name="auserDAO")
	private AUserDAO auserDAO;
	
	@Resource(name="roleUserDAO")
	private RoleUserDAO roleUserDAO;
	
	@Resource(name="auserAttributeDAO")
	private UserAttributeDAO userAttributeDAO;
	
	private static String key1="money";
	private static String key2="remainingMoney";
	
	private List<Object> heads = new ArrayList<Object>();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Head head=new Head();
		head.setField("userId");
		head.setFieldName("用户ID");
		heads.add(head);
		head=new Head();
		head.setField("account");
		head.setFieldName("用户账号");
		heads.add(head);
		head=new Head();
		head.setField("realname");
		head.setFieldName("用户真名");
		heads.add(head);
		head=new Head();
		head.setField("role");
		head.setFieldName("用户角色");
		heads.add(head);
		head=new Head();
		head.setField("money");
		head.setFieldName("充值金额");
		heads.add(head);
		head=new Head();
		head.setField("remainingMoney");
		head.setFieldName("剩余金额");
		heads.add(head);
	}
	
	//查询用户金额
	@Override
	public Map<String, Object> findAUsermoney(Integer page, Integer pageSize, String identity)
			throws BusinessException {
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
				mapList.put("userId", list.get(i).getId());
				mapList.put("account", list.get(i).getAccount());
				mapList.put("realname", list.get(i).getRealname());
				AUser aUser=new AUser();
				aUser.setId(list.get(i).getId());
				aUser.setIdentity(identity);
				List<Long> roleid=auserDAO.findAuserRole(aUser);
				StringBuffer pj = new StringBuffer();  
				int acount=0;
				for (int h=0;h<roleid.size();h++){
					Map<String, Object> mmp=new HashMap<String,Object>();
					mmp.put("id", roleid.get(h));
					mmp.put("identity", identity);
					String jj=roleUserDAO.findIdinentity(mmp);
					acount++;
					if(acount==1){
						pj.append(jj);
					}else{
						pj.append(","+jj);
					}
				}
				String js=pj.toString();
				mapList.put("role", js);
				UserAttribute attribute=new UserAttribute();
				attribute.setUserId(list.get(i).getId());
				attribute.setKey(key1);
				UserAttribute attribute1=new UserAttribute();
				attribute1=userAttributeDAO.findAUserAttribte(attribute);
				if(attribute1==null){
					String ye=null;
					mapList.put("money", ye);
				}else{
					String ye=attribute1.getValue();
					mapList.put("money", ye);
				}
				attribute.setKey(key2);
				UserAttribute attribute2=new UserAttribute();
				attribute2=userAttributeDAO.findAUserAttribte(attribute);
				if(attribute2==null){
					String ye=null;
					mapList.put("remainingMoney", ye);
				}else{
					String ye=attribute2.getValue();
					mapList.put("remainingMoney", ye);
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
