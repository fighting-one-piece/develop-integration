package org.cisiondata.modules.datainterface.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.datainterface.service.IWLService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WLContorller {
	@Resource(name = "wlService")
	private IWLService service = null;
	@ResponseBody
	@RequestMapping(value="wlQuery")
	public WebResult readData(String phone){
		WebResult result = new WebResult();
		try {
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(service.readData(phone));
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	@RequestMapping(value = "/wlPhone", method = RequestMethod.GET)
	public ModelAndView ExecutedPeople() {
		return new ModelAndView("lemoncome/wlPhone");
	}
}
