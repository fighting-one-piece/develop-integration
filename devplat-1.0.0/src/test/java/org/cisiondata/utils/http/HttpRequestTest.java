package org.cisiondata.utils.http;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class HttpRequestTest {

	@Test
	public void testLogin() {
		String url = "http://localhost:8080/devplat/api/v1/login";
		Map<String, String> params = new HashMap<String, String>();
		params.put("account", "test");
		params.put("password", "@#test456");
		params.put("verificationCode", "nrk4");
		String response = HttpUtils.sendPost(url, params);
		System.out.println(response);
	}
	
	@Test
	public void testMetadatasIndices() {
		String url = "http://localhost:8080/devplat/api/v1/metadatas/indices";
		String[] headers = new String[]{"accessToken", "df6a53b392494cb24292550a33feb84f"};
		String response = HttpUtils.sendGet(url, headers);
		System.out.println(response);
	}
	
	@Test
	public void testIndexTypeSearch() {
		String url = "http://localhost:8080/devplat/api/v1/index/operator/type/telecom/search?query=13512345678&scrollId=3&rowNumPerPage=40";
		String[] headers = new String[]{"accessToken", "df6a53b392494cb24292550a33feb84f"};
		String response = HttpUtils.sendGet(url, headers);
		System.out.println(response);
	}
	
	@Test
	public void testAdvancedSearch() {
		String url = "http://localhost:8080/devplat/api/v1/search/multi/scroll";
		String[] headers = new String[]{"accessToken", "df6a53b392494cb24292550a33feb84f"};
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
