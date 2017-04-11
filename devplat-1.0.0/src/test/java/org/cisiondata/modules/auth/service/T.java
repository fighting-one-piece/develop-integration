package org.cisiondata.modules.auth.service;

import java.util.List;
import java.util.Map;

import org.cisiondata.utils.http.HttpUtils;
import org.cisiondata.utils.json.GsonUtils;

public class T {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String url = "http://map.baidu.com/?newmap=1&reqflag=pcmap&biz=1&from=webmap&da_par=direct&pcevaname=pc4.1&qt=con&from=webmap&c=75&wd=%E5%B0%8F%E5%8C%BA&wd2=&pn=1";
		String response = HttpUtils.sendGet(url);
		Map<String, Object> map = GsonUtils.fromJsonToMap(response);
		String content = String.valueOf(map.get("content"));
		System.out.println(content);
		Object[] objs = new Object[]{1,2,new Integer[]{3,4}};
		String json = GsonUtils.builder().toJson(objs);
		System.out.println(json);
		List<Object> list = GsonUtils.builder().fromJson(json, List.class);
		for (Object l : list) {
			System.out.println(l);
		}
	}
	
}
