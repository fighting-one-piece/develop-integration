package org.cisiondata.utils.http;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class HttpTest {

	@Test
	public void testLogin() {
		String url = "http://localhost:8080/devplat/login";
		Map<String, String> params = new HashMap<String, String>();
		params.put("account", "test");
		params.put("password", "@#test456");
		String response = HttpUtils.sendPost(url, params);
		System.out.println(response);
	}
	
	@Test
	public void testProvince() {
		String url = "http://localhost:8080/devplat/provinceCity/province";
		String response = HttpUtils.sendGet(url, new String[]{"accessToken", "c0eb7435e2eecc35e4579b4af2a302e2"});
		System.out.println(response);
	}
	
	@Test
	public void testJcaptchaValidate() {
//		String url = "http://localhost:8080/devplat/jcaptcha-validate";
		String url = "http://192.168.0.114:8083/devplat/jcaptcha-validate";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fieldId", "");
		params.put("fieldValue", "loeer");
		String response = HttpUtils.sendGet(url, params);
		System.out.println(response);
	}
}
