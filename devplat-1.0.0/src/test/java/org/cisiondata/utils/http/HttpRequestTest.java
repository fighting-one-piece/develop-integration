package org.cisiondata.utils.http;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class HttpRequestTest {

	@Test
	public void testLogin() {
		String url = "http://192.168.0.198:8080/devplat/api/v1/login";
		Map<String, String> params = new HashMap<String, String>();
		params.put("account", "test");
		params.put("password", "@#test456");
		params.put("verificationCode", "gd2e");
		params.put("uuid", "2db55bb5-938d-499d-aa21-8d536792f1fa");
		String response = HttpUtils.sendPost(url, params);
		System.out.println(response);
	}
	
	@Test
	public void testLoginDev() {
		String url = "http://localhost:8080/devplat/api/v1/login";
		Map<String, String> params = new HashMap<String, String>();
		params.put("account", "dev3");
		params.put("password", "123456");
		params.put("verificationCode", "gd2e");
		params.put("uuid", "2db55bb5-938d-499d-aa21-8d536792f1fa");
		String response = HttpUtils.sendPost(url, params);
		System.out.println(response);
	}
	
	@Test
	public void testSecurity() {
		String url = "http://localhost:8080/devplat/api/v1/users/settings/security/verify?answer=1";
		String[] headers = new String[]{"accessToken", "a395f83ea703c1def55acc3460117a8f"};
		String response = HttpUtils.sendGet(url,headers);
		System.out.println(response);
	}
	
	@Test
	public void testLogout() {
		String url = "http://localhost:8080/devplat/api/v1/logout";
		Map<String, String> params = new HashMap<String, String>();
		String response = HttpUtils.sendPost(url, params, "accessToken", "df6a53b392494cb24292550a33feb84f");
		System.out.println(response);
	}
	
	@Test
	public void testMetadatasIndices() {
		String url = "http://192.168.0.198:8080/devplat/api/v1/metadatas/indices/types/metadatas";
		String[] headers = new String[]{"accessToken", "b833556ed45081e1161df56648e6bc79"};
		String response = HttpUtils.sendGet(url, headers);
		System.out.println(response);
	}
	
	@Test
	public void testIndexTypeSearch() {
		String url = "http://localhost:8080/devplat/api/v1/labels/indices/operator/types/telecom?query=13512345678&scrollId=1&rowNumPerPage=40";
		String[] headers = new String[]{"accessToken", "4b6939eeb8b5962ea58048904889bc16"};
		String response = HttpUtils.sendGet(url, headers);
		System.out.println(response);
	}
	
	@Test
	public void testReadUserLoginLog() {
		String url = "http://localhost:8080/devplat/api/v1/userLoginLogs?account=test&index=1&pageSize=5";
		String[] headers = new String[]{"accessToken", "ea7505a5cd29739e6da5050b7f65d964"};
		String response = HttpUtils.sendGet(url, headers);
		System.out.println(response);
	}
	
	@Test
	public void testAdvancedSearch() {
		String url = "http://localhost:8080/devplat/api/v1/search/multi/scroll";
		String[] headers = new String[]{"accessToken", "4b6939eeb8b5962ea58048904889bc16"};
		Map<String, String> map = new HashMap<String, String>();
		map.put("esindex", "financial");
		map.put("estype", "logistics");
		map.put("mobilePhone", "13512345678");
		map.put("pageSize", "10");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("map", map);
		String response = HttpUtils.sendGet(url, params, headers);
		System.out.println(response);
	}
	
}
