package org.cisiondata.modules.system.controller;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/verification")
public class VerificationTokenController {

	/**
	 * 验证token接口
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/token")
	public WebResult getProvince() {
		WebResult result = new WebResult();
		result.setCode(ResultCode.SUCCESS.getCode());
		return result;
	}
}
