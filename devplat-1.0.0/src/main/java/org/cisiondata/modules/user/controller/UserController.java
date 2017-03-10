package org.cisiondata.modules.user.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.user.service.IUserService;
import org.cisiondata.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	
	private Logger LOG = LoggerFactory.getLogger(UserController.class);
	
	@Resource(name="auserService")
	private IUserService auserService;
	
	//通过账号添加个人信息
	@ResponseBody
	@RequestMapping(value="/users/{account}/settings",method=RequestMethod.POST)
	public WebResult updateUserSetting(@PathVariable String account,String realName,String mobilePhone,String newPassword){
		WebResult result  = new WebResult();
		try {
			result.setResultCode(ResultCode.SUCCESS);
			auserService.updateUserSetting(account, realName, mobilePhone, newPassword);
			result.setData("信息更新完成");
		}catch (BusinessException bu) {
			result.setCode((bu.getCode()));
			result.setFailure(bu.getMessage());
			LOG.error(bu.getMessage(), bu);
		} catch (Exception e) {
			result.setResultCode(ResultCode.FAILURE);
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	//通过账号添加密保
	@ResponseBody
	@RequestMapping(value="/users/{account}/security",method=RequestMethod.POST)
	public WebResult updateUserSecurity(@PathVariable String account,String question,String answer){
		WebResult result  = new WebResult();
		try {
			auserService.updateUserSecurity(account, question, answer);
			result.setResultCode(ResultCode.SUCCESS);
			result.setData("信息更新完成");
		}catch (BusinessException bu) {
			result.setCode((bu.getCode()));
			result.setFailure(bu.getMessage());
			LOG.error(bu.getMessage(), bu);
		}catch (Exception e) {
			result.setResultCode(ResultCode.FAILURE);
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	//查询密保问题
	@ResponseBody
	@RequestMapping(value="/users/security/questions",method=RequestMethod.GET)
	public WebResult findSecurity(){
		WebResult result  = new WebResult();
		try {
			result.setResultCode(ResultCode.SUCCESS);
			result.setData(auserService.findSecurity());
		}catch (BusinessException bu) {
			result.setCode((bu.getCode()));
			result.setFailure(bu.getMessage());
			LOG.error(bu.getMessage(), bu);
		} catch (Exception e) {
			result.setResultCode(ResultCode.FAILURE);
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	//获取用户密保问题
		@ResponseBody
		@RequestMapping(value="/users/{account}/security/question",method=RequestMethod.GET)
		public WebResult findSecurityQuestion(@PathVariable String account){
			WebResult result  = new WebResult();
			try {
				result.setResultCode(ResultCode.SUCCESS);
				result.setData(auserService.findSecurityQuestion(account));
			}catch (BusinessException bu) {
				result.setCode((bu.getCode()));
				result.setFailure(bu.getMessage());
				LOG.error(bu.getMessage(), bu);
			} catch (Exception e) {
				result.setResultCode(ResultCode.FAILURE);
				result.setFailure(e.getMessage());
				LOG.error(e.getMessage(), e);
			}
			return result;
		}
		
		//获取用户密保问题答案
		@ResponseBody
		@RequestMapping(value="/users/{account}/security/verify",method=RequestMethod.GET)
		public WebResult findSecurityAnswer(@PathVariable String account,String answer){
			WebResult result  = new WebResult();
			try {
				result.setResultCode(ResultCode.SUCCESS);
				auserService.findSecurityAnswer(account, answer);
				result.setData("密保答案正确");
			}catch (BusinessException bu) {
				result.setCode((bu.getCode()));
				result.setFailure(bu.getMessage());
				LOG.error(bu.getMessage(), bu);
			} catch (Exception e) {
				result.setResultCode(ResultCode.FAILURE);
				result.setFailure(e.getMessage());
				LOG.error(e.getMessage(), e);
			}
			return result;
		}
		
		//获取用户密保问题答案
		@ResponseBody
		@RequestMapping(value="/users/{account}/security/settings",method=RequestMethod.POST)
		public WebResult updateSecurityAnswer(@PathVariable String account,String oldAnswer,String newQuestion,String newAnswer){
			WebResult result  = new WebResult();
			try {
				result.setResultCode(ResultCode.SUCCESS);
				auserService.updateSecurityAnswer(account, oldAnswer, newQuestion, newAnswer);
				result.setData("密保修改完成");
			}catch (BusinessException bu) {
				result.setCode((bu.getCode()));
				result.setFailure(bu.getMessage());
				LOG.error(bu.getMessage(), bu);
			} catch (Exception e) {
				result.setResultCode(ResultCode.FAILURE);
				result.setFailure(e.getMessage());
				LOG.error(e.getMessage(), e);
			}
			return result;
		}
		
		//通过旧密码设置新密码
		@ResponseBody
		@RequestMapping(value="/users/{account}/password/settings",method=RequestMethod.POST)
		public WebResult updatePassword(@PathVariable String account,String oldPassword,String newPassword){
			WebResult result  = new WebResult();
			try {
				result.setResultCode(ResultCode.SUCCESS);
				auserService.updatePassword(account, oldPassword, newPassword);
				result.setData("密码修改成功");
			}catch (BusinessException bu) {
				result.setCode((bu.getCode()));
				result.setFailure(bu.getMessage());
				LOG.error(bu.getMessage(), bu);
			} catch (Exception e) {
				result.setResultCode(ResultCode.FAILURE);
				result.setFailure(e.getMessage());
				LOG.error(e.getMessage(), e);
			}
			return result;
		}
}
