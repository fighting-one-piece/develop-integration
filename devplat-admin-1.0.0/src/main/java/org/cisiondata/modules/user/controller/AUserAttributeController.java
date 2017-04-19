package org.cisiondata.modules.user.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.user.service.AUserAttributeService;
import org.cisiondata.modules.user.service.impl.IAUserShowServiceImpl;
import org.cisiondata.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/fund")
public class AUserAttributeController {

	private Logger LOG = LoggerFactory.getLogger(AUserAttributeController.class);
	
	@Resource(name = "aUserAttributeService")
	private AUserAttributeService auserAttributeService;
	
	@Resource(name = "iAUserShowServiceImpl")
	private IAUserShowServiceImpl iAUserShowService;
	
	private static String identity="3";
	
	
	//查询用户金额
	@RequestMapping(value = "/finduserAttribute", method = RequestMethod.GET)
	@ResponseBody
	public WebResult readAdminUsers(Integer page,Integer pageSize) {
		WebResult result = new WebResult();
		try {
			result.setData(iAUserShowService.findAUsermoney(page, pageSize, identity));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(), bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	//修改用户金额
	@RequestMapping(value="/updateuserAttribute",method = RequestMethod.POST)
	@ResponseBody
	public WebResult updateuserAttribute(Double changeCount, Long userId) {
		WebResult result = new WebResult();
		try {
			auserAttributeService.updateUserAttribte(changeCount,userId);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData("修改成功");
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(), bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
}
