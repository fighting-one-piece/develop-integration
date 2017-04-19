package org.cisiondata.modules.user.service.impl;

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
import org.cisiondata.modules.user.dao.RoleUserDAO;
import org.cisiondata.modules.user.dao.UserAttributeDAO;
import org.cisiondata.modules.user.entity.AUser;
import org.cisiondata.modules.user.entity.AUserRole;
import org.cisiondata.modules.user.service.IAUserService;
import org.cisiondata.modules.user.utils.Head;
import org.cisiondata.utils.endecrypt.EndecryptUtils;
import org.cisiondata.utils.endecrypt.IDUtils;
import org.cisiondata.utils.endecrypt.MD5Utils;
import org.cisiondata.utils.endecrypt.SHAUtils;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("aUserService")
public class AUserServiceImpl implements IAUserService,InitializingBean {

	@Resource(name="auserDAO")
	private AUserDAO auserDAO;
	
	@Resource(name="auserAttributeDAO")
	private UserAttributeDAO userAttributeDAO;
	@Resource(name="roleUserDAO")
	private RoleUserDAO roleUserDao;
	private static String key1="accessId";
	private static String key2="accessKey";
	
	private List<Object> heads = new ArrayList<Object>();
	
	/**
	 * 初始化资源
	 * @throws Exception
	 */
	public void afterPropertiesSet() throws Exception {
		Head head=new Head();
		head.setField("id");
		head.setFieldName("ID");
		heads.add(head);
		head=new Head();
		head.setField("account");
		head.setFieldName("用户账号");
		heads.add(head);
		head=new Head();
		head.setField("password");
		head.setFieldName("用户密码");
		heads.add(head);
		head=new Head();
		head.setField("identity");
		head.setFieldName("用户标识");
		heads.add(head);
		head=new Head();
		head.setField("deleteFlag");
		head.setFieldName("删除标识");
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
		head.setField("check");
		head.setFieldName("是否被选中");
		heads.add(head);
	}
	
	//新增用户
	@Override
	@Transactional
	public void addAUser(String account,String password,String pwd,String nickname,String identity,String expireTime) throws BusinessException {
		if(StringUtils.isBlank(account)
			||StringUtils.isBlank(password)
			||StringUtils.isBlank(pwd)
			||StringUtils.isBlank(nickname)
			||expireTime==null)
			throw new BusinessException(ResultCode.PARAM_NULL);
		if(!password.equals(pwd)) throw new BusinessException(ResultCode.TWO_PASSWORDS_ARE_INCONSISTENT);
		String salt =  IDUtils.genUUID();
		AUser selectauser=new AUser();
		selectauser=auserDAO.findaccountAuser(account);
		if(selectauser!=null){
			if(selectauser.getAccount().equals(account)) throw new BusinessException(ResultCode.DATA_EXISTED);
		}else{
			AUser auser=new AUser();
			String pawd=EndecryptUtils.encryptPassword(password,salt);
			auser.setPassword(pawd);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date extime=null;
			try {
				extime = dateFormat.parse(expireTime);
			} catch (Exception e) {
				e.printStackTrace();
				throw new BusinessException(ResultCode.PARAM_FORMAT_ERROR);
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
			attribute.setKey("money");
			attribute.setValue("0");
			attribute.setType("Double");
			userAttributeDAO.addUserAttribte(attribute);
			attribute.setKey("remainingMoney");
			attribute.setValue("0");
			attribute.setType("Double");
			userAttributeDAO.addUserAttribte(attribute);
		}
	}


	//修改
	@Override
	public void updateAUser(AUser auser) throws BusinessException {
		if(StringUtils.isBlank(auser.getPassword())&&StringUtils.isBlank(auser.getIdentity())
		   &&StringUtils.isBlank(auser.getNickname())&&auser.getDeleteFlag()==null
		   &&StringUtils.isBlank(auser.getEmail())&&auser.getExpireTime()==null
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
	public Map<String,Object> findAuser(Integer page, Integer pageSize,String identity) throws BusinessException {
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
				mapList.put("password", "****");
				String cra=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(list.get(i).getCreateTime());  
				mapList.put("createTime", cra);
				String exp=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(list.get(i).getExpireTime());
				mapList.put("expireTime", exp);
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


	@Override
	public Map<String, Object> findAllUser(Long id, Long identity)
			throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		List<AUser> list = auserDAO.findAllAuser(identity);
		List<AUserRole> role = roleUserDao.findAllbyId(id);
		List<Long> userid = new ArrayList<Long>();
		for (AUserRole userrole : role) {
			userid.add(userrole.getAuserId());
		}
		if (list.size() == 0)
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		for (AUser aUser : list) {
			aUser.setPassword("******");
			if (userid.contains(aUser.getId())) {
				aUser.setCheck(true);
			}
		}
		QueryResult<AUser> qr = new QueryResult<AUser>();
		qr.setResultList(list);
		result.put("head", heads);
		result.put("data", qr);
		return result;
	}
	
	//启停用
	@Override
	public void updateAUserqt(Long id) throws BusinessException {
		if(id==null) throw new BusinessException(ResultCode.PARAM_NULL);
		AUser aUser=new AUser();
		aUser=auserDAO.findIdAuser(id);
		if(aUser.getDeleteFlag()){
			aUser.setId(id);
			aUser.setDeleteFlag(false);
			auserDAO.updateAUser(aUser);
		}else{
			aUser.setId(id);
			aUser.setDeleteFlag(true);
			auserDAO.updateAUser(aUser);
		}
	}


	
}
