package org.cisiondata.modules.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.user.service.IUserService;
import org.cisiondata.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
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
	@RequestMapping(value="/users/settings/profile",method=RequestMethod.POST)
	public WebResult updateUserSetting(String realName,String mobilePhone,
			String newPassword,String verificationCode,HttpServletRequest request){
		WebResult result  = new WebResult();
		try {
			result.setResultCode(ResultCode.SUCCESS);
			auserService.updateUserSetting(realName,mobilePhone, newPassword,verificationCode,request);
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
	@RequestMapping(value="/users/settings/security",method=RequestMethod.POST)
	public WebResult updateUserSecurity(String question,String answer){
		WebResult result  = new WebResult();
		try {
			result.setResultCode(ResultCode.SUCCESS);
			result.setData(auserService.updateUserSecurity(question, answer));
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
		@RequestMapping(value="/users/settings/security/question",method=RequestMethod.GET)
		public WebResult findSecurityQuestion(){
			WebResult result  = new WebResult();
			try {
				result.setResultCode(ResultCode.SUCCESS);
				result.setData(auserService.findSecurityQuestion());
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
		@RequestMapping(value="/users/settings/security/verify",method=RequestMethod.GET)
		public WebResult findSecurityAnswer(String answer){
			WebResult result  = new WebResult();
			try {
				result.setResultCode(ResultCode.SUCCESS);
				result.setData(auserService.findSecurityAnswer(answer));
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
		@RequestMapping(value="/users/settings/newsecurity",method=RequestMethod.POST)
		public WebResult updateSecurityAnswer(String oldAnswer,String newQuestion,String newAnswer){
			WebResult result  = new WebResult();
			try {
				result.setResultCode(ResultCode.SUCCESS);
				auserService.updateSecurityAnswer(oldAnswer, newQuestion, newAnswer);
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
		@RequestMapping(value="/users/settings/newpassword",method=RequestMethod.POST)
		public WebResult updatePassword(String oldPassword,String newPassword){
			WebResult result  = new WebResult();
			try {
				result.setResultCode(ResultCode.SUCCESS);
				auserService.updatePassword(oldPassword, newPassword);
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
		//忘记密码，设置新密码
		@ResponseBody
		@RequestMapping(value="/users/settings/forget/newpassword",method=RequestMethod.POST)
		public WebResult updateNewPassword(String newPassword){
			WebResult result  = new WebResult();
			try {
				result.setResultCode(ResultCode.SUCCESS);
				auserService.updateNewPassword(newPassword);
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
		
		//验证电话号码并发送验证码
		@ResponseBody
		@RequestMapping(value="/users/account/verify",method=RequestMethod.GET)
		public WebResult validation(String phone){
			WebResult result  = new WebResult();
			try {
				result.setResultCode(ResultCode.SUCCESS);
				auserService.validation(phone);
				result.setData("手机号码验证成功,验证码已发送");
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
		
		//首次登陆发送验证码
		@ResponseBody
		@RequestMapping(value="/users/sms",method=RequestMethod.GET)
		public WebResult sendValidation(String phone){
			WebResult result  = new WebResult();
			try {
				result.setResultCode(ResultCode.SUCCESS);
				auserService.sendValidation(phone);
				result.setData("验证码已发送");
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
		
		//验证码判断
		@ResponseBody
		@RequestMapping(value="/users/sms/verify",method=RequestMethod.POST)
		public WebResult judgeValidation(String phone,String verificationCode){
			WebResult result  = new WebResult();
			try {
				result.setResultCode(ResultCode.SUCCESS);
				auserService.judgeValidationCode(phone, verificationCode);
				result.setData("验证码正确");
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
		
		//修改手机号码
		@ResponseBody
		@RequestMapping(value="/users/settings/newphone",method=RequestMethod.POST)
		public WebResult updatePhone(String phone,String verificationCode){
			WebResult result  = new WebResult();
			try {
				result.setResultCode(ResultCode.SUCCESS);
				auserService.updatePhone(phone, verificationCode);
				result.setData("修改电话号码成功");
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

		//获取用户信息
				@ResponseBody
				@RequestMapping(value="/users/current",method=RequestMethod.GET)
				public WebResult findUser(){
					WebResult result  = new WebResult();
					try {
						result.setResultCode(ResultCode.SUCCESS);
						result.setData(auserService.findUser());
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
