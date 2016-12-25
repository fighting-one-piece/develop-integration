package org.cisiondata.modules.authentication.web.jcaptcha;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import com.octo.captcha.image.ImageCaptcha;

public class CustomGimpy extends ImageCaptcha implements Serializable {

	private static final long serialVersionUID = 1L;

	private String response = null;

	public CustomGimpy(String question, BufferedImage challenge, String response) {
		super(question, challenge);
		this.response = response;
	}

	public final Boolean validateResponse(final Object response) {
		return (null != response && response instanceof String) ? validateResponse((String) response) : Boolean.FALSE;
	}

	private final Boolean validateResponse(final String response) {
		return new Boolean(response.equalsIgnoreCase(this.response));
	}

}
