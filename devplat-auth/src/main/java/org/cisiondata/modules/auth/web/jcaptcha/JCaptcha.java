package org.cisiondata.modules.auth.web.jcaptcha;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;

public class JCaptcha {
	
	private static Logger LOG = LoggerFactory.getLogger(JCaptcha.class);
	
//    public static final CustomManageableImageCaptchaService captchaService
//            = new CustomManageableImageCaptchaService(new FastHashMapCaptchaStore(), new GMailEngine(), 180, 100000, 75000);

	public static final CustomManageableCaptchaService captchaService = 
			new CustomManageableCaptchaService(new FastHashMapCaptchaStore(), new GMailEngine(), 180, 100000, 75000);
	
    /**
     * 验证当前请求输入的验证码否正确；并从CaptchaService中删除已经生成的验证码
     * @param request
     * @param userCaptchaResponse
     * @return
     */
    public static boolean validateResponse(HttpServletRequest request, String userCaptchaResponse) {
        if (request.getSession(false) == null) return false;
        boolean validated = false;
        try {
            String id = request.getSession().getId();
            validated = captchaService.validateResponseForID(id, userCaptchaResponse).booleanValue();
        } catch (CaptchaServiceException e) {
            LOG.error(e.getMessage(), e);
        }
        return validated;
    }

    /**
     * 验证当前请求输入的验证码是否正确；但不从CaptchaService中删除已经生成的验证码（比如Ajax验证时可以使用，防止多次生成验证码）
     * @param request
     * @param userCaptchaResponse
     * @return
     */
    public static boolean hasCaptcha(HttpServletRequest request, String userCaptchaResponse) {
        if (request.getSession(false) == null) return false;
        boolean validated = false;
        try {
            String id = request.getSession().getId();
            validated = captchaService.hasCapcha(id, userCaptchaResponse);
        } catch (CaptchaServiceException e) {
        	LOG.error(e.getMessage(), e);
        }
        return validated;
    }

}
