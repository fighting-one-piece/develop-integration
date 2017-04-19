package org.cisiondata.modules.user.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.user.service.IUserResourceChargingService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/resourcedffee")
public class UserResourceChargingsController {

	@Resource(name = "userResourceChargingService")
	private IUserResourceChargingService userResourceChargingService;
	
	@ResponseBody
	@RequestMapping(value="/query",method=RequestMethod.GET)
	public WebResult QueryUserResourceFields(Long userId,Long resourceId) {
		WebResult result = new WebResult();
		try {
			result.setData(userResourceChargingService.findChargingsByUserIdAndResourceId(userId, resourceId,3));
			result.setResultCode(ResultCode.SUCCESS);
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getMessage());
		}catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value="/save",method=RequestMethod.GET)
	public WebResult UpdateUserResourceFields(Long userId,Long resourceId,String chargings) {
		WebResult result = new WebResult();
		try {
			userResourceChargingService.updateChargingsByUserIdAndResourceId(userId, resourceId, chargings,3);
			result.setData("修改成功");
			result.setResultCode(ResultCode.SUCCESS);
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getMessage());
		}catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	
}
