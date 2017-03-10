package org.cisiondata.modules.identity.controller;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.identity.service.IIdCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/")
public class IdCardController {
	
	@Autowired
	private IIdCardService idCardService = null;
	@ResponseBody
	@RequestMapping(value="/educations")
	public WebResult readTypesDatas(String name,String idCard){
		WebResult result = new WebResult();
		try {
			result.setData(idCardService.readCard(name,idCard));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
}
