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
}
