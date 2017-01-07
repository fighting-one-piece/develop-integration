package org.cisiondata.modules.auth.web.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.auth.service.IUserService;
import org.cisiondata.utils.encryption.EndecryptUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	
	@Resource(name="userService")
	private IUserService userService;
	
	//用户修改密码
	@RequestMapping("/user/password")
	@ResponseBody
	public WebResult updatepassword(String account,String originalpassword,String newpassword) {
		WebResult result = new WebResult();
		try {
			User user = userService.readUserByAccount(account);
			String password = EndecryptUtils.encryptPassword(account,originalpassword);
			if (! password.equals(user.getPassword())) {
				result.setData("原密码不正确！");
			}else {
				user.setPassword(newpassword);
				userService.updateUserPassword(user);
				result.setData("密码已修改！");
			}
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setData(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="/user/updatepassword",method=RequestMethod.GET)
	public String updatepassword() {
		return "user/updatepassword";
	}
}
