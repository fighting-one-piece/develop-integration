package org.cisiondata.modules.auth.web.controller;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.auth.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	
	@Resource(name="userService")
	private IUserService userService = null;
	
	//用户修改密码
	@RequestMapping("/user")
	@ResponseBody
	public WebResult currentUser() {
		WebResult result = new WebResult();
		try {
			Object accountObject = SecurityUtils.getSubject().getPrincipal();
			if (null != accountObject) {
				User user = userService.readUserByAccount((String) accountObject);
				result.setData(user);
			}
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setData(e.getMessage());
		}
		return result;
	}
	
	//用户修改密码
	@RequestMapping("/user/password")
	@ResponseBody
	public WebResult updatepassword(String account, String originalpassword, String newpassword) {
		WebResult result = new WebResult();
		try {
			userService.updateUserPassword(account, originalpassword, newpassword);
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setData(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="/user/updatepassword",method=RequestMethod.GET)
	public ModelAndView updatepassword() {
		return new ModelAndView("user/updatepassword");
	}
}
