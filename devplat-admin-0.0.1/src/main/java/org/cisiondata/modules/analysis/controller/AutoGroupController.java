package org.cisiondata.modules.analysis.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/excludegroup")
public class AutoGroupController {
	
	String[] name = new String[]{"刘~宇","方~斌","宋~彪","谢~鑫","王~旭","何~霞",
			"李~欣","肖~鹏","李建鹏","蒋声强","王仕恩","谭佳龙","何泽杰","杨仕伟"};
	
	private Map<String, List<String>> cache = new HashMap<String, List<String>>(); 
	
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String groupView() {
		return "/user/group";
	}
	
	@ResponseBody
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public WebResult groupCacheList() {
		WebResult result = new WebResult();
		try {
			result.setData(cache.get("distribute"));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/distribute", method = RequestMethod.POST)
	public WebResult groupCacheGenerate(int query) {
		WebResult result = new WebResult();
		List<String> list = group(query);
		try {
			cache.put("distribute", list);
			result.setData(list);
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	public List<String> group(int query) {
		 int[] array = new int[name.length];
		  int i = 1;
		  array[0] = (int)(Math.random()*name.length);
		  while(i < name.length){
		   int count = 0;
		   int now = i;
		   array[i] = (int)(Math.random()*name.length);
		   for(int j = 0; j < now; j++){
		    if(array[i] != array[j]) 
		     count++;
		    if(count == i) i++;
		   }
		  }
		  int num3 = query / (name.length);
		  int num2 = query % (name.length);
		  int num1 = (int)num3;
		  List<String> list = new ArrayList<String>();
		  String value = null;
			for (int j = 0; j < name.length - num2 ; j++) {
				 value =	name[array[j]] + ":序号  "+ (j * num1  + 1)+ "~" +((j + 1) * num1);
				 list.add(value);
			}
			for (int k = name.length - num2,m=1; k < name.length ; k++,m++) {
				 value = name[array[k]] + ":序号  " + (k * num1  + m)+ "~" +(((k + 1) * num1) + m);
				 list.add(value);
			} 
		return list;
		
	}
}
