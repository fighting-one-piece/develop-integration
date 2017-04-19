package org.cisiondata.modules.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.user.dao.AdminUserDAO;
import org.cisiondata.modules.user.entity.AdminUser;
import org.cisiondata.modules.user.service.IAdminUserService;
import org.cisiondata.modules.user.utils.Head;
import org.cisiondata.utils.endecrypt.EndecryptUtils;
import org.cisiondata.utils.endecrypt.IDUtils;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;


@Service("adminUserService")
public class AdminUserServiceImpl implements IAdminUserService,InitializingBean {

	@Resource(name = "aadminUserDAO")
	private AdminUserDAO adminUserDAO;
	
	private List<Object> heads = new ArrayList<Object>();
	
	/**
	 * 用于初始化资源
	 * @throws Exception
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Head head = new Head();
		head.setField("id");
		head.setFieldName("ID");
		heads.add(head);
		head = new Head();
		head.setField("account");
		head.setFieldName("账号");
		heads.add(head);
		head = new Head();
		head.setField("nickName");
		head.setFieldName("昵称");
		heads.add(head);
		head = new Head();
		head.setField("mobilePhone");
		head.setFieldName("手机号");
		heads.add(head);
		head = new Head();
		head.setField("identity");
		head.setFieldName("标识");
		heads.add(head);
		head = new Head();
		head.setField("deleteFlag");
		head.setFieldName("是否删除");
		heads.add(head);
	}
	
	@Override
	public Map<String,Object> findAdminUsersByPage(Integer page, Integer pageSize) throws BusinessException {
		if(page < 1 || pageSize < 1) throw new BusinessException(ResultCode.PARAM_ERROR);
		Map<String,Object> params = new HashMap<String,Object>();
		int count = adminUserDAO.findCountByCondition(params);
		int pageCount = count % pageSize == 0 ? count/pageSize : count/pageSize + 1;
		int begin = (page-1) * pageSize;
		params = new HashMap<String,Object>();
		params.put("begin", begin);
		params.put("pageSize", pageSize);
		List<AdminUser> list = adminUserDAO.findByCondition(params);
		if (list.size() == 0) throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		QueryResult<AdminUser> qr = new QueryResult<AdminUser>();
		qr.setResultList(list);
		qr.setTotalRowNum(pageCount);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("head", heads);
		result.put("data", qr);
		
		return result;
	}

	@Override
	public String addAdminUser(String account,String password,String identity,String nickName,String mobilePhone) throws BusinessException {
		String resut="添加失败";
		if(StringUtils.isBlank(account)||StringUtils.isBlank(password)||StringUtils.isBlank(identity)||StringUtils.isBlank(nickName) )throw new BusinessException(ResultCode.PARAM_NULL);
		String salt = IDUtils.genUUID();
		if(Pattern.matches("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", mobilePhone))throw new BusinessException("电话号码不符合规范");
		AdminUser adminUser =new AdminUser();
		adminUser.setAccount(account);
		password = EndecryptUtils.encryptPassword(password, salt);
		adminUser.setPassword(password);
		adminUser.setIdentity(identity);
		adminUser.setNickName(nickName);
		adminUser.setSalt(salt);
		adminUser.setMobilePhone(mobilePhone);
		try {
			int 	count=adminUserDAO.addAdminUser(adminUser);	
			if(count==1){
				resut="添加成功";
			}
		} catch (DuplicateKeyException e) {
			throw new BusinessException(ResultCode.DATA_EXISTED);
		}

		return resut;
	}

	
	@Override
	public String deleteAdminUser(Long id) throws BusinessException {
		String b ="false";
		int deleteflag=	adminUserDAO.querydeleteFlagById(id);
		System.out.println(deleteflag);
		Long flag=0L;
		if(deleteflag==1){
			flag=0L;
		}else if(deleteflag==0){
			flag=1L;
		}
		try {
			int i= adminUserDAO.deleteAdminUserById(id,flag);
			if(i>0){
				b="true";
			}
		} catch (Exception e) {
			throw new BusinessException(ResultCode.VERIFICATION_NO_EXIST);
		}
		return b;
		
		
	}

	@Override
	public boolean updateAdminUser(String account, String password,String identity, String nickName,String mobilePhone, long id) throws BusinessException {
		boolean b=false;
		AdminUser adminUser = new AdminUser();
		if(!StringUtils.isBlank(account)){
			adminUser.setAccount(account);
		}
		if(!StringUtils.isBlank(password)){
			adminUser.setPassword(password);
		}
		if(!StringUtils.isBlank(identity)){
			adminUser.setIdentity(identity);
		}
		if(!StringUtils.isBlank(nickName)){
			adminUser.setNickName(nickName);
		}
		if(!StringUtils.isBlank(mobilePhone)){
			adminUser.setMobilePhone(mobilePhone);
		}
		adminUser.setId(id);
		int count=adminUserDAO.updateAdminUserById(adminUser);
		if(count==1){
			b=true;
		}
		return b;
	}

	

}
