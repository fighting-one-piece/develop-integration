package org.cisiondata.modules.login.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.auth.web.jcaptcha.JCaptcha;
import org.cisiondata.modules.log.service.IUserLoginLogService;
import org.cisiondata.modules.login.service.ILoginService;
import org.cisiondata.modules.login.service.IVerificationCodeService;
import org.cisiondata.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	
	private Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private MessageSource messageSource = null;
    
    @Resource(name = "loginService")
    private ILoginService loginService = null;
    
    @Resource(name = "verificationCodeService")
    private IVerificationCodeService verificationCodeService = null;
    
    @Resource(name = "loginLogService")
	private IUserLoginLogService loginLogService = null;
    
    @ResponseBody
    @RequestMapping(value = {"/{login:login;?.*}"}, method = RequestMethod.POST, headers = "Accept=application/json")
    public WebResult login(HttpServletRequest request, String account, String password, String uuid, 
    		String verificationCode, @RequestBody User user) {
        WebResult webResult = new WebResult();
        try {
        	if (StringUtils.isBlank(account) && StringUtils.isBlank(password)) {
        		account = user.getAccount();
        		password = user.getPassword();
        	}
        	boolean isSuccess = verificationCodeService.validateVerificationCode(uuid, verificationCode);
        	LOG.info("verification code validate success: {}", isSuccess);
        	if (isSuccess) {
	        	webResult.setData(loginService.readUserLoginInfoByAccountAndPassowrd(account, password));
	        	webResult.setResultCode(ResultCode.SUCCESS);
	        	loginLogService.addUserLoginLog(account,request, 0);
        	}
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
    
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public WebResult logout(HttpServletRequest request) {
        WebResult webResult = new WebResult();
        try {
        	loginLogService.addUserLoginLog("",request, 1);
        	loginService.doUserLogout();
        	webResult.setResultCode(ResultCode.SUCCESS);
        } catch (BusinessException be) {
        	LOG.error(be.getMessage(), be);
        	webResult.setCode(be.getCode());
        	webResult.setFailure(be.getMessage());
        } catch (Exception e) {
        	LOG.error(e.getMessage(), e);
        	webResult.setResultCode(ResultCode.FAILURE);
        	webResult.setFailure(e.getMessage());
        }
        return webResult;
    }
    
    @ResponseBody
    @RequestMapping(value = "/jcaptcha-validate", method = RequestMethod.GET)
    public WebResult jqueryValidationEngineValidate(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam(value = "verificationCode", required = true) String verificationCode) {
    	LOG.info("verificationCode : {}", verificationCode);
    	WebResult webResult = new WebResult();
    	try {
    		if (JCaptcha.hasCaptcha(request, verificationCode)) {
    			webResult.setData(messageSource.getMessage("jcaptcha.validate.success", null, null));
    		} else {
    			webResult.setData(messageSource.getMessage("jcaptcha.validate.error", null, null));
    		}
    		webResult.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
        	LOG.error(e.getMessage(), e);
        	webResult.setResultCode(ResultCode.FAILURE);
        	webResult.setFailure(e.getMessage());
        }
        return webResult;
    }
    
}
