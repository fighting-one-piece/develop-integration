package org.cisiondata.modules.datainterface.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.datainterface.entity.AccessUser;
import org.cisiondata.modules.datainterface.service.IAccessUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccessUserController {
	private Logger LOG = LoggerFactory.getLogger(AccessUserController.class);

	@Resource(name = "aaccessUserService")
	private IAccessUserService accessUserService;
	
	@RequestMapping("/admin/accessUser/add")
	@ResponseBody
	public WebResult addAccessControl(AccessUser accessControl){
		WebResult result = new WebResult();
		try {
			result.setData(accessUserService.addAccessControl(accessControl));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping("/admin/accessUser/find")
	@ResponseBody
	public WebResult getAccessUser(int page,int pageSize){
		WebResult result = new WebResult();
		try {
			result.setData(accessUserService.findAccessUserByPage(page, pageSize));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping("/admin/accessUser/update/deleteFlag")
	@ResponseBody
	public WebResult updateAccessUserDeleteFlag(AccessUser accessUser){
		WebResult result = new WebResult();
		try {
			result.setData(accessUserService.updateDeleteFlag(accessUser));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping("/admin/accessControl")
	public ModelAndView readAddAResourceView() {
		return new ModelAndView("admin/accessControl");
	}
}
