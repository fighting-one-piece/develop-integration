package org.cisiondata.modules.auth.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.auth.service.ILoginService;
import org.cisiondata.modules.auth.web.jcaptcha.JCaptcha;
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
    public WebResult login(HttpServletRequest request, String username, String password) {
        WebResult webResult = new WebResult();
        try {
        	webResult.setData(loginService.readUserLoginInfoByAccountAndPassowrd(username, password));
        	webResult.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
        	LOG.error(e.getMessage(), e);
        	webResult.setResultCode(ResultCode.FAILURE);
        	webResult.setFailure(e.getMessage());
        }
        return webResult;
    }
    
    @RequestMapping(value = "/jcaptcha-validate", method = RequestMethod.GET)
    public Object jqueryValidationEngineValidate (HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "fieldId", required = false) String fieldId,
            @RequestParam(value = "fieldValue", required = false) String fieldValue) {
        ValidateResponse validateResponse = ValidateResponse.newInstance();
        if (JCaptcha.hasCaptcha(request, fieldValue) == false) {
        	validateResponse.validateFail(fieldId, messageSource.getMessage("jcaptcha.validate.error", null, null));
        } else {
        	validateResponse.validateSuccess(fieldId, messageSource.getMessage("jcaptcha.validate.success", null, null));
        }
        return validateResponse.result();
    }

}

class ValidateResponse {
    /**
     * 验证成功
     */
    private static final Integer OK = 1;
    /**
     * 验证失败
     */
    private static final Integer FAIL = 0;

    private List<Object> results = new ArrayList<Object>();

    private ValidateResponse() {
    }

    public static ValidateResponse newInstance() {
        return new ValidateResponse();
    }

    /**
     * 验证成功（使用前台alertTextOk定义的消息）
     *
     * @param fieldId 验证成功的字段名
     */
    public void validateFail(String fieldId) {
        validateFail(fieldId, "");
    }

    /**
     * 验证成功
     *
     * @param fieldId 验证成功的字段名
     * @param message 验证成功时显示的消息
     */
    public void validateFail(String fieldId, String message) {
        results.add(new Object[]{fieldId, FAIL, message});
    }

    /**
     * 验证成功（使用前台alertTextOk定义的消息）
     *
     * @param fieldId 验证成功的字段名
     */
    public void validateSuccess(String fieldId) {
        validateSuccess(fieldId, "");
    }

    /**
     * 验证成功
     *
     * @param fieldId 验证成功的字段名
     * @param message 验证成功时显示的消息
     */
    public void validateSuccess(String fieldId, String message) {
        results.add(new Object[]{fieldId, OK, message});
    }

    /**
     * 返回验证结果
     * @return
     */
    public Object result() {
        if (results.size() == 1) {
            return results.get(0);
        }
        return results;
    }

}
