package org.cisiondata.modules.user.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.user.service.IResourceAttributeService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/jytresourcepubfee")
public class JYTResourcepubfeeController {
	@Resource(name = "iResourceAttributeService")
	private IResourceAttributeService iResourceAttributeService = null;

	// 查看费用
	@ResponseBody
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public WebResult findResourceAttribute(Long resourceId) {
		WebResult result = new WebResult();
		try {
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(iResourceAttributeService
					.findByIdResourceAttribute(resourceId));
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setData(bu.getDefaultMessage());
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;

	}
	// 保存费用
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = "Accept=application/json")
	public WebResult updateResourceAttribute(Long resourceid, String chargings) {
		WebResult result = new WebResult();
		try {
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(iResourceAttributeService.updateResourceAttribute(
					resourceid, chargings));
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setData(bu.getDefaultMessage());
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}

	
}
