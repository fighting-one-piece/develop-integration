package org.cisiondata.modules.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cisiondata.modules.queue.service.IConsumerService;
import org.cisiondata.utils.http.HttpUtils;
import org.cisiondata.utils.json.GsonUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class T {

	@SuppressWarnings("unchecked")
	public static void t1() {
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
	
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
		Map<String, IConsumerService> map = ac.getBeansOfType(IConsumerService.class);
		System.out.println(map.size());
		List<IConsumerService> list = new ArrayList<IConsumerService>();
		for (Map.Entry<String, IConsumerService> entry : map.entrySet()) {
			list.add(entry.getValue());
		}
		System.out.println(list.size());
		for (IConsumerService consumerService : list) {
			System.out.println(consumerService.getClass());
		}
		
	}
	
}
