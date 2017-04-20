package org.cisiondata.modules.log.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.log.service.IUserAccessLogService;
import org.cisiondata.modules.log.service.IUserLoginLogService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LogController {
	
	@Resource(name = "userLoginLogService")
	private IUserLoginLogService userLoginLogService;
	
	@Resource(name = "userAccessLogService")
	private IUserAccessLogService userAccessLogService;
	
	@ResponseBody
	@RequestMapping(value = "/userAccessLogs",method = RequestMethod.GET)
	public WebResult getUserAccessLog(String params,Date startTime,Date endTime,int index,int pageSize){
		WebResult result = new WebResult();
		try {
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(userAccessLogService.selAccessLog(params, startTime, endTime, index, pageSize));
		}catch(BusinessException bs){
			result.setCode(bs.getCode());
			result.setFailure(bs.getDefaultMessage());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value="/userLoginLogs",method = RequestMethod.GET)
	public WebResult selLoginLogPage(String account,int index,int pageSize){
		WebResult result = new WebResult();
		try {
			result.setData(userLoginLogService.selLoginLogPage(account, index, pageSize));
			result.setCode(ResultCode.SUCCESS.getCode());
		}catch(BusinessException bs){
			result.setCode(bs.getCode());
			result.setFailure(bs.getDefaultMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
}
