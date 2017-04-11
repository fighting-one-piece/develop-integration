package org.cisiondata.modules.user.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.user.entity.AdminUser;
import org.cisiondata.modules.user.service.IAdminUserService;
import org.cisiondata.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/bkuser")
public class AdminUserController {
	private Logger LOG = LoggerFactory.getLogger(AdminUserController.class);
	
	@Resource(name = "adminUserService")
	private IAdminUserService adminUserService;
	
	@RequestMapping(value="/adminUsers",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readAdminUsers(Integer page,Integer pageSize){
		WebResult result = new WebResult();
		try {
			result.setData(adminUserService.findAdminUsersByPage(page, pageSize));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	@RequestMapping(value="/add",method = RequestMethod.POST, headers = "Accept=application/json")
	public WebResult addAdminUser(String account,String password,String identity,String nickName,boolean deleteFlag,String salt,String mobilePhone){
		AdminUser adminUser =new AdminUser();
		adminUser.setAccount(account);
		adminUser.setPassword(password);
		adminUser.setIdentity(identity);
		adminUser.setNickName(nickName);
		adminUser.setDeleteFlag(deleteFlag);
		adminUser.setSalt(salt);
		adminUser.setMobilePhone(mobilePhone);
		WebResult result = new WebResult();
		try {
			result.setData(adminUserService.addAdminUser(adminUser));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
		
	}
	@RequestMapping(value="/delete",method = RequestMethod.POST, headers = "Accept=application/json")
	public WebResult deleteAdminUser(long id){
		WebResult result = new WebResult();
		try {
			result.setData(adminUserService.deleteAdminUser(id));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
		
	}
	@RequestMapping(value="/update",method = RequestMethod.POST, headers = "Accept=application/json")
	public WebResult updateAdminUser(String account,String password,String identity,String nickName,boolean deleteFlag,String salt,String mobilePhone,long id){
		WebResult result = new WebResult();
		AdminUser adminUser =new AdminUser();
		adminUser.setAccount(account);
		adminUser.setPassword(password);
		adminUser.setIdentity(identity);
		adminUser.setNickName(nickName);
		adminUser.setDeleteFlag(deleteFlag);
		adminUser.setSalt(salt);
		adminUser.setMobilePhone(mobilePhone);
		adminUser.setId(id);
		try {
			result.setData(adminUserService.updateAdminUser(adminUser));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
		
	}
}
