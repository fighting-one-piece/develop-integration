package org.cisiondata.modules.system.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.system.service.IProvinceCityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/provinceCity")
public class ProvinceCityController {

	private Logger LOG = LoggerFactory.getLogger(ProvinceCityController.class);
	
	@Resource(name = "provinceCityService")
	private IProvinceCityService provinceCityService;
	
	@ResponseBody
	@RequestMapping("/province")
	public WebResult getProvince() {
		WebResult result = new WebResult();
		try {
			result.setData(provinceCityService.findProvince());
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("/city")
	public WebResult getCityByProvince(String province) {
		WebResult result = new WebResult();
		try {
			result.setData(provinceCityService.findCity(province));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/phone/city")
	public WebResult getCityByPhone(String phone) {
		WebResult result = new WebResult();
		try {
			result.setData(provinceCityService.findPhoneCity(phone));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	
}
