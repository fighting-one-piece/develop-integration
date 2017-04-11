package org.cisiondata.modules.user.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.auth.entity.UserAttribute;
import org.cisiondata.modules.user.dao.AUserDAO;
import org.cisiondata.modules.user.dao.UserAttributeDAO;
import org.cisiondata.modules.user.entity.AUser;
import org.cisiondata.modules.user.service.IAUserService;
import org.cisiondata.utils.endecrypt.EndecryptUtils;
import org.cisiondata.utils.endecrypt.MD5Utils;
import org.cisiondata.utils.endecrypt.SHAUtils;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("aUserService")
public class AUserServiceImpl implements IAUserService {

	@Resource(name="auserDAO")
	private AUserDAO auserDAO;
	
	@Resource(name="auserAttributeDAO")
	private UserAttributeDAO userAttributeDAO;
	
	private static String key1="accessId";
	private static String key2="accessKey";
	//新增用户
	@Override
	@Transactional
	public void addAUser(String account,String password,String identity,String nickname,String expireTime,String salt) throws BusinessException {
		if(StringUtils.isBlank(account)
			||StringUtils.isBlank(password)
			||StringUtils.isBlank(identity)
			||StringUtils.isBlank(nickname)
			||StringUtils.isBlank(expireTime)
			||StringUtils.isBlank(salt)) 
			throw new BusinessException(ResultCode.PARAM_NULL);
		AUser selectauser=new AUser();
		selectauser=auserDAO.findaccountAuser(account);
		if(selectauser!=null){
			if(selectauser.getAccount().equals(account)) throw new BusinessException(ResultCode.DATA_EXISTED);
		}else{
			AUser auser=new AUser();
			String pawd=EndecryptUtils.encryptPassword(password,salt);
			auser.setPassword(pawd);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date extime=null;
			try {
				extime = dateFormat.parse(expireTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			auser.setExpireTime(extime);
			auser.setAccount(account);
			auser.setIdentity(identity);
			auser.setNickname(nickname);
			auser.setSalt(salt);
			auser.setStatus(0);
			auser.setDeleteFlag(false);
			Date createTime=new Date();
			auser.setCreateTime(createTime);
			auserDAO.addAUser(auser);
			UserAttribute attribute = new UserAttribute();
			attribute.setUserId(auser.getId());
			attribute.setKey("firstLoginFlag");
			attribute.setValue("true");
			attribute.setType("boolean");
			userAttributeDAO.addUserAttribte(attribute);
			attribute.setKey("informationFlag");
			attribute.setValue("true");
			attribute.setType("boolean");
			userAttributeDAO.addUserAttribte(attribute);
			attribute.setKey("encryptedFlag");
			attribute.setValue("true");
			attribute.setType("boolean");
			userAttributeDAO.addUserAttribte(attribute);
		}
	}


	//修改
	@Override
	public void updateAUser(AUser auser) throws BusinessException {
		if(StringUtils.isBlank(auser.getAccount())
		   &&StringUtils.isBlank(auser.getPassword())&&StringUtils.isBlank(auser.getIdentity())
		   &&StringUtils.isBlank(auser.getNickname())&&StringUtils.isBlank(auser.getExpireTime().toString())
		   &&StringUtils.isBlank(auser.getEmail())&&StringUtils.isBlank(auser.getDeleteFlag().toString())
		   &&StringUtils.isBlank(auser.getStatus().toString())&&StringUtils.isBlank(auser.getSalt())) throw new BusinessException(ResultCode.PARAM_NULL);
		if(StringUtils.isNotBlank(auser.getPassword())){
			AUser ausera=auserDAO.findIdAuser(auser.getId());
			String pawd=EndecryptUtils.encryptPassword(auser.getPassword(),ausera.getSalt());
			auser.setPassword(pawd);
		}
		auserDAO.updateAUser(auser);
	}

	//分页查询
	@Override
	public Map<String,Object> findAuser(Integer page, Integer pageSize) throws BusinessException {
		QueryResult<Map<String,Object>> result = new QueryResult<Map<String,Object>>();
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object> mapHead = new HashMap<String,Object>();
		mapHead.put("id", "ID");
		mapHead.put("account", "用户账号");
		mapHead.put("password", "用户密码");
		mapHead.put("identity", "用户标识");
		mapHead.put("deleteFlag", "删除标识");
		mapHead.put("nickname", "用户昵称");
		mapHead.put("email", "用户邮箱");
		mapHead.put("status", "用户状态");
		mapHead.put("createTime", "创建时间");
		mapHead.put("expireTime", "过期时间");
		if(page < 1 || pageSize < 1) throw new BusinessException(ResultCode.PARAM_ERROR);
		Map<String,Object> params = new HashMap<String,Object>();
		long counts = auserDAO.findCountAuser(params);
		int count=(int)counts;
		int pageCount = count % pageSize == 0 ? count/pageSize : count/pageSize + 1;
		int begin = (page-1) * pageSize;
		params = new HashMap<String,Object>();
		params.put("begin", begin);
		params.put("pageSize", pageSize);
		List<AUser> list = auserDAO.findAuser(params);
		if(list != null  && list.size() > 0){
			List<Map<String,Object>>  listMap = new ArrayList<Map<String,Object>>();
			for(int i = 0,len = list.size(); i < len;i++){
				Map<String,Object> mapList = new HashMap<String,Object>();
				mapList.put("id", list.get(i).getId());
				mapList.put("account", list.get(i).getAccount());
				mapList.put("password", list.get(i).getPassword());
				mapList.put("identity", list.get(i).getIdentity());
				mapList.put("deleteFlag", list.get(i).getDeleteFlag());
				mapList.put("nickname", list.get(i).getNickname());
				mapList.put("email", list.get(i).getEmail());
				mapList.put("status", list.get(i).getStatus());
				mapList.put("createTime", list.get(i).getCreateTime());
				mapList.put("expireTime", list.get(i).getExpireTime());
				listMap.add(mapList);
			}
			result.setTotalRowNum(pageCount);
			result.setResultList(listMap);
			mapResult.put("head", mapHead);
			mapResult.put("data", result);
		}else{
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		}
		return mapResult;
	}

	//添加秘钥
	@Override
	public void addkeyAuser(AUser auser) throws BusinessException {
		if(StringUtils.isBlank(auser.getAccount())) throw new BusinessException(ResultCode.PARAM_NULL);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("account", auser.getAccount());
		List<AUser> list=auserDAO.findAuser(params);
		if(list.size()==0) throw new BusinessException(ResultCode.ACCOUNT_NOT_EXIST);
		UserAttribute attribute=new UserAttribute();
		Date date = new Date();
		String accessId=null;
		String accessKey=null;
		for (AUser aUser2 : list) {
			accessId = MD5Utils.hash(SHAUtils.SHA1(aUser2.getAccount() + date.toString())).substring(8, 24);
			accessKey = MD5Utils.hash(aUser2.getAccount() + date.toString() + accessId);
			attribute.setUserId(aUser2.getId());
			attribute.setKey(key1);
			UserAttribute uab=userAttributeDAO.findAUserAttribte(attribute);
			if(uab==null){
				attribute.setUserId(aUser2.getId());
				attribute.setType("string");
				attribute.setKey(key1);
				attribute.setValue(accessId);
				userAttributeDAO.addUserAttribte(attribute);
				attribute.setKey(key2);
				attribute.setValue(accessKey);
				userAttributeDAO.addUserAttribte(attribute);
			}else{
				attribute.setUserId(aUser2.getId());
				attribute.setKey(key1);
				attribute.setValue(accessId);
				userAttributeDAO.updateUserAttribte(attribute);
				attribute.setUserId(aUser2.getId());
				attribute.setKey(key2);
				attribute.setValue(accessKey);
				userAttributeDAO.updateUserAttribte(attribute);
			}
		}
		
		
	}



	
}
