package org.cisiondata.modules.user.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.auth.entity.UserAttribute;
import org.cisiondata.modules.user.dao.UserAttributeDAO;
import org.cisiondata.modules.user.entity.APIAuserAttribute;
import org.cisiondata.modules.user.entity.AUser;
import org.cisiondata.modules.user.service.AUserAttributeService;
import org.cisiondata.modules.user.utils.Head;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("aUserAttributeService")
public class AUserAttributeServiceImpl implements AUserAttributeService,InitializingBean{

	@Resource(name="auserAttributeDAO")
	private UserAttributeDAO userAttributeDAO;
	
	private static String key1="money";
	private static String key2="remainingMoney";
	
	private List<Object> heads = new ArrayList<Object>();
	private List<Object> heads1 = new ArrayList<Object>();
	@Override
	public void afterPropertiesSet() throws Exception {
		Head head=new Head();
		head.setField("userId");
		head.setFieldName("用户ID");
		heads.add(head);
		head=new Head();
		head.setField("remainingMoney");
		head.setFieldName("用户余额");
		heads.add(head);
		head=new Head();
		head.setField("accessId");
		head.setFieldName("accessId");
		heads1.add(head);
		head=new Head();
		head.setField("accessKey");
		head.setFieldName("accessKey");
		heads1.add(head);
		head=new Head();
		head.setField("money");
		head.setFieldName("充值总金额");
		heads1.add(head);
		head=new Head();
		head.setField("remainingMsoney");
		head.setFieldName("剩余金额");
		heads1.add(head);
	}
	
	
	//根据user_id查询余额
	@Override
	public Map<String,Object> findAUserAttribte(Long userId) throws BusinessException {
		if(userId==null) throw new BusinessException(ResultCode.PARAM_NULL);
		Map<String,Object> mapResult = new HashMap<String,Object>();
		UserAttribute attribute=new UserAttribute();
		attribute.setUserId(userId);
		attribute.setKey(key2);
		attribute=userAttributeDAO.findAUserAttribte(attribute);
		if(attribute==null) throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		mapResult.put("userId", attribute.getUserId());
		mapResult.put("remaining_money", attribute.getValue());
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("head", heads);
		result.put("data", mapResult);
		return result;
	}

	//根据user_id修改
	@Override
	public void updateUserAttribte(Double changeCount, Long userId) throws BusinessException {
		if(changeCount==null||userId==null) throw new BusinessException(ResultCode.PARAM_NULL);
		UserAttribute attribute=new UserAttribute();
		attribute.setUserId(userId);
		//查询总金额
		attribute.setKey(key1);
		UserAttribute attribute1=userAttributeDAO.findAUserAttribte(attribute);
		//没有总金额属性增加一个
		if(attribute1==null){
			attribute1=new UserAttribute();
			attribute1.setUserId(userId);
			attribute1.setKey(key1);
			attribute1.setValue("0");
			attribute1.setType("Double");
			userAttributeDAO.addUserAttribte(attribute1);
		}
		//查询余额
		attribute.setKey(key2);
		UserAttribute attribute2=userAttributeDAO.findAUserAttribte(attribute);
		if(attribute2==null){
			attribute2=new UserAttribute();
			attribute2.setUserId(userId);
			attribute2.setKey(key2);
			attribute2.setValue("0");
			attribute2.setType("Double");
			userAttributeDAO.addUserAttribte(attribute2);
		}
		UserAttribute attribute3=userAttributeDAO.findAUserAttribte(attribute);
		UserAttribute attribute4=userAttributeDAO.findAUserAttribte(attribute);
		//从数据库读出余额和总金额
		String pd="0";
		//总金额
		double cha=0;
		if(pd.equals(attribute3.getValue())){
			cha=0;
		}else{
			cha=Double.valueOf(attribute3.getValue()).doubleValue();
		}
		//余额
		double yu=0;
		if(pd.equals(attribute4.getValue())){
			yu=0;
		}else{
			yu=Double.valueOf(attribute4.getValue()).doubleValue();
		}
		//根据输入的金额是大于还是小于0来判断增加还是减少
		if(changeCount>0){
			cha=cha+changeCount;
			String value=""+cha;
			attribute.setKey(key1);
			attribute.setValue(value);
			userAttributeDAO.updateUserAttribte(attribute);
			//增加余额
			yu=yu+changeCount;
			String yue=""+yu;
			attribute.setKey(key2);
			attribute.setValue(yue);
			userAttributeDAO.updateUserAttribte(attribute);
		}else{
			if(yu+changeCount>0){
				//减少总金额
				cha=cha+changeCount;
				String value=""+cha;
				attribute.setKey(key1);
				attribute.setValue(value);
				userAttributeDAO.updateUserAttribte(attribute);
				//减少余额
				yu=yu+changeCount;
				String yue=""+yu;
				attribute.setKey(key2);
				attribute.setValue(yue);
				userAttributeDAO.updateUserAttribte(attribute);
			}else{
				//减少总金额
				cha=cha-yu;
				String value=""+cha;
				attribute.setKey(key1);
				attribute.setValue(value);
				userAttributeDAO.updateUserAttribte(attribute);
				//减少余额
				yu=0;
				String yue=""+yu;
				attribute.setKey(key2);
				attribute.setValue(yue);
				userAttributeDAO.updateUserAttribte(attribute);
			}
			
		}
		
	}


	@Override
	public Map<String,Object> findAPIAUserMoney(Integer page, Integer pageSize)
			throws BusinessException {
		if(page < 1 || pageSize < 1) throw new BusinessException(ResultCode.PARAM_ERROR);
		Map<String,Object> param=new HashMap<String, Object>();
		List<AUser> userlist=userAttributeDAO.findallAuser(param);
		int count=userlist.size();
		int pageCount = count % pageSize == 0 ? count/pageSize : count/pageSize + 1;
		int begin = (page-1) * pageSize;
		param = new HashMap<String,Object>();
		param.put("begin", begin);
		param.put("pageSize", pageSize);
		List<AUser>userlist1=userAttributeDAO.findallAuser(param);
		Map<String ,Object> params=new HashMap<String, Object>();
		QueryResult<APIAuserAttribute> qr=new QueryResult<APIAuserAttribute>();
		List<APIAuserAttribute>	list2=new ArrayList<APIAuserAttribute>();
		for (AUser aUser : userlist1) {
			APIAuserAttribute apiuser=new APIAuserAttribute();
			System.out.println(aUser.getId());
			params.put("userId", aUser.getId());
			List<UserAttribute>	list=userAttributeDAO.findByCondition(params);
			for (UserAttribute userAttribute : list) {
				if(userAttribute.getKey().equals("accessId"))apiuser.setAccessId(userAttribute.getValue());
				if(userAttribute.getKey().equals("accessKey"))apiuser.setAccessKey(userAttribute.getValue());
				if(userAttribute.getKey().equals("money"))apiuser.setMoney(userAttribute.getValue());
				if(userAttribute.getKey().equals("remainingMoney"))apiuser.setRemainingMoney(userAttribute.getValue());
			}
			list2.add(apiuser);
		}
		qr.setResultList(list2);
		qr.setTotalRowNum(pageCount);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("head", heads1);
		result.put("data", qr);
		return result;
	}


	@Override
	public void updateRemaining_money(Long userId, String changMoney) throws BusinessException {
		Map<String, Object> params =new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("key", "remainingMoney");
		List<UserAttribute> list=	userAttributeDAO.findByCondition(params);
		String remainingMoney=list.get(0).getValue();
		System.out.println(remainingMoney);
		String money=String.valueOf((Double.parseDouble(remainingMoney)+Double.parseDouble(changMoney)));
		System.out.println(money);
		userAttributeDAO.updateRemainingMoney(userId, money);
		
		
	}
		
		
		
		
		
		
		
	

	

}
