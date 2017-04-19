package org.cisiondata.modules.user.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
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

	@RequestMapping(value = "/adminUsers", method = RequestMethod.GET)
	@ResponseBody
	public WebResult readAdminUsers(Integer page, Integer pageSize) {
		WebResult result = new WebResult();
		try {
			result.setData(adminUserService
					.findAdminUsersByPage(page, pageSize));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(), bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json")
	public WebResult addAdminUser(String account, String password,
			String identity, String nickName, 
			String mobilePhone) {
		
		WebResult result = new WebResult();
		try {
			result.setData(adminUserService.addAdminUser(account, password,
					identity, nickName, mobilePhone));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;

	}
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = "Accept=application/json")
	public WebResult deleteAdminUser(Long id) {
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
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST, headers = "Accept=application/json")
	public WebResult updateAdminUser(String account, String password,String identity, String nickName,String mobilePhone, Long id) {
		WebResult result = new WebResult();
		try {
			result.setData(adminUserService.updateAdminUser(account,password,identity,nickName,mobilePhone,id));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;

	}
}
