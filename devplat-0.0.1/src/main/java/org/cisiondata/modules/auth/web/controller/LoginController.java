package org.cisiondata.modules.auth.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.cisiondata.modules.auth.Constants;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.auth.web.jcaptcha.JCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

@Controller
public class LoginController {

    @Value(value = "${shiro.login.url}")
    private String loginUrl = null;

    @Autowired
    private MessageSource messageSource = null;

    @RequestMapping(value = {"/{login:login;?.*}"})
    public String loginForm(HttpServletRequest request, ModelMap model) {
        //表示退出
        if (!StringUtils.isEmpty(request.getParameter("logout"))) {
            model.addAttribute(Constants.MESSAGE, messageSource.getMessage("user.logout.success", null, null));
        }
        //表示用户删除了 @see org.apache.shiro.web.filter.user.SysUserFilter
        if (!StringUtils.isEmpty(request.getParameter("notfound"))) {
            model.addAttribute(Constants.ERROR, messageSource.getMessage("user.notfound", null, null));
        }
        //表示用户被管理员强制退出
        if (!StringUtils.isEmpty(request.getParameter("forcelogout"))) {
            model.addAttribute(Constants.ERROR, messageSource.getMessage("user.forcelogout", null, null));
        }
        //表示用户输入的验证码错误
        if (!StringUtils.isEmpty(request.getParameter("jcaptchaError"))) {
            model.addAttribute(Constants.ERROR, messageSource.getMessage("jcaptcha.validate.error", null, null));
        }
        //表示用户锁定了 @see org.apache.shiro.web.filter.user.SysUserFilter
        if (!StringUtils.isEmpty(request.getParameter("blocked"))) {
            User user = (User) request.getAttribute(Constants.CURRENT_USER);
            String reason = user.getAccount();
            model.addAttribute(Constants.ERROR, messageSource.getMessage("user.blocked", new Object[]{reason}, null));
        }
        if (!StringUtils.isEmpty(request.getParameter("unknown"))) {
            model.addAttribute(Constants.ERROR, messageSource.getMessage("user.unknown.error", null, null));
        }
        //登录失败了 提取错误消息
        Object authObject = request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        if (null != authObject) {
        	if (authObject instanceof Exception) {
        		Exception shiroLoginFailureEx = (Exception) authObject;
        		if (shiroLoginFailureEx != null) {
                    model.addAttribute(Constants.ERROR, shiroLoginFailureEx.getMessage());
                }
        	} else if (authObject instanceof String) {
        		model.addAttribute(Constants.ERROR, (String) authObject);
        	}
        }
        //如果用户直接到登录页面先退出一下
        //原因：isAccessAllowed实现是subject.isAuthenticated()---->即如果用户验证通过 就允许访问,这样会导致登录一直死循环
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.isAuthenticated()) {
            subject.logout();
        }
        //如果同时存在错误消息 和 普通消息  只保留错误消息
        if (model.containsAttribute(Constants.ERROR)) {
            model.remove(Constants.MESSAGE);
        }
        return "front/login";
    }
    
    @RequestMapping(value = "/unauthorized")
    public String unauthorizedView(HttpServletRequest request, ModelMap model) {
    	return "front/unauthorized";
    }
    
    @RequestMapping(value = "/jcaptcha-validate", method = RequestMethod.GET)
    @ResponseBody
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

    private List<Object> results = Lists.newArrayList();

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
