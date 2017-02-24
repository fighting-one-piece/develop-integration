package org.cisiondata.modules.datainterface.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.datainterface.service.IAccessUserControlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccessUserControlControler {
	private Logger LOG = LoggerFactory.getLogger(AccessUserControlControler.class);
	
	@Resource(name = "aaccessUserControlService")
	private IAccessUserControlService accessUserControlService = null;
	
	@RequestMapping("/admin/accessUserControl/updateCount")
	@ResponseBody
	public WebResult changeCount(Long changeCount,String type,String account){
		WebResult result = new WebResult();
		try {
			result.setData(accessUserControlService.updateAccessUserControlCount(changeCount, type, account));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping("/admin/accessUserControl/updateMoney")
	@ResponseBody
	public WebResult changeCount(Double changeCount,String type,String account){
		WebResult result = new WebResult();
		try {
			result.setData(accessUserControlService.updateMoney(changeCount, type, account));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
}
