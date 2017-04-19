package org.cisiondata.modules.identity.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.identity.service.IMobileAddressService;
import org.cisiondata.modules.identity.service.IMobileNameService;
import org.cisiondata.modules.identity.service.IMobileService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/mobile")
public class MobileController  {
	
	@Resource(name = "mobileService")
	private IMobileService mobileService = null;
	
	@Resource(name = "mobileNameService")
	private IMobileNameService mobileNameService = null;
	
	@Resource(name="mobileAddressService")
	private IMobileAddressService mobileAddressService = null;
	
	@ResponseBody
	@RequestMapping(value = "/name/{mobile}", method = RequestMethod.GET)
	public WebResult readMobileName(@PathVariable String mobile) {
		WebResult result = new WebResult();
		try {
			result.setData(mobileNameService.readNameFromMobile(mobile));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/address/{mobile}", method = RequestMethod.GET)
	public WebResult readMobileAddress(@PathVariable String mobile) {
		WebResult result = new WebResult();
		try {
			result.setData(mobileAddressService.readAddressFromMoblie(mobile));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/{mobile}", method = RequestMethod.GET)
	public WebResult readMobileInfo(@PathVariable String mobile) {
		WebResult result = new WebResult();
		try {
			result.setData(mobileService.readMobileInfo(mobile));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	/**
	 * 查询手机归属地  ----fb
	 * @param mobile
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{mobile}/MobileAttribution", method = RequestMethod.GET)
	public WebResult readMobileAttribution(@PathVariable String mobile){
		WebResult result = new WebResult();
		try {
			result.setData(mobileService.selByDnseg(mobile));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch(BusinessException bu){
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}

}
