package org.cisiondata.modules.datainterface.controller;


import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.datainterface.entity.AccessInterface;
import org.cisiondata.modules.datainterface.service.IAccessInterfaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccessInterfaceController {
	private Logger LOG = LoggerFactory.getLogger(AccessInterfaceController.class);
	
	@Resource(name = "accessInterfaceService")
	private IAccessInterfaceService accessInterfaceService = null;

	@RequestMapping("/admin/accessInterface")
	public ModelAndView readAccessInterfaceView(){
		return new ModelAndView("admin/accessInterface");
	}
	
	@RequestMapping("/admin/accessInterface/find")
	@ResponseBody
	public WebResult getAccessInterface(int page,int pageSize){
		WebResult result = new WebResult();
		try {
			result.setData(accessInterfaceService.findAccessInterfaceByPage(page, pageSize));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping("/admin/accessInterface/update/deleteFlag")
	@ResponseBody
	public WebResult changeDeleteFlag(AccessInterface accessInterface){
		WebResult result = new WebResult();
		try {
			result.setData(accessInterfaceService.updateDeleteFlag(accessInterface));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping("/admin/accessInterface/add")
	@ResponseBody
	public WebResult addAccessInterface(AccessInterface accessInterface){
		WebResult result = new WebResult();
		try {
			result.setData(accessInterfaceService.addAccessInterface(accessInterface));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping("/admin/accessInterface/update")
	@ResponseBody
	public WebResult updateAccessInterface(AccessInterface accessInterface){
		System.out.println(accessInterface);
		WebResult result = new WebResult();
		try {
			result.setData(accessInterfaceService.updateAccessInterface(accessInterface));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
}
