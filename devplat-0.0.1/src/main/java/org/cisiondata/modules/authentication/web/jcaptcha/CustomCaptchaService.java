package org.cisiondata.modules.authentication.web.jcaptcha;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;

public class CustomCaptchaService {

//	private static CaptchaService captchaService = new DefaultManageableImageCaptchaService(
//			new FastHashMapCaptchaStore(), new GMailEngine(), 180, 100000, 75000);

	private static CustomManageableCaptchaService captchaService = new CustomManageableCaptchaService(
			new FastHashMapCaptchaStore(), new GMailEngine(), 180, 100000, 75000);
	
	private CustomCaptchaService(){}
	
	public static BufferedImage generate(String sessionId) throws IOException {
//        return ((AbstractManageableImageCaptchaService) captchaService).getImageChallengeForID(sessionId);
        return captchaService.getImageChallengeForID(sessionId);
    }
	
    public static boolean validate(String sessionId, String authCode) {
        return captchaService.validateResponseForID(sessionId, authCode);
    }
    
    public static void remove(String sessionId) {
    	captchaService.removeCaptcha(sessionId);
    }
	
}
