package org.cisiondata.modules.login.controller;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.login.service.ILoginService;
import org.cisiondata.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
	private Logger LOG = LoggerFactory.getLogger(LoginController.class);
	
	@Resource(name = "loginService")
	private ILoginService loginService;
	
	@ResponseBody
    @RequestMapping(value = {"/login"}, method = RequestMethod.POST, headers = "Accept=application/json")
    public WebResult login(String account, String password, @RequestBody User user) {
        WebResult webResult = new WebResult();
        try {
        	if (StringUtils.isBlank(account) && StringUtils.isBlank(password) && null != user) {
        		account = user.getAccount();
        		password = user.getPassword();
        	}
        	webResult.setData(loginService.login(account, password));
        	webResult.setCode(ResultCode.SUCCESS.getCode());
        	
        } catch (BusinessException be) {
        	LOG.error(be.getMessage(), be);
        	webResult.setCode(be.getCode());
        	webResult.setFailure(be.getDefaultMessage());
        } catch (Exception e) {
        	LOG.error(e.getMessage(), e);
        	webResult.setResultCode(ResultCode.FAILURE);
        	webResult.setFailure(e.getMessage());
        }
        return webResult;
    }
}
