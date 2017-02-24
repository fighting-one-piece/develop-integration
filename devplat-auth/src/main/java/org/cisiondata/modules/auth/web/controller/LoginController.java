package org.cisiondata.modules.auth.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.auth.service.ILoginService;
import org.cisiondata.modules.auth.web.jcaptcha.JCaptcha;
import org.cisiondata.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	
	private Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private MessageSource messageSource = null;
    
    @Resource(name = "loginService")
    private ILoginService loginService = null;

    @RequestMapping(value = {"/{login:login;?.*}"}, method = RequestMethod.POST)
    public WebResult login(HttpServletRequest request, String account, String password) {
        WebResult webResult = new WebResult();
        try {
        	webResult.setData(loginService.readUserLoginInfoByAccountAndPassowrd(account, password));
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
