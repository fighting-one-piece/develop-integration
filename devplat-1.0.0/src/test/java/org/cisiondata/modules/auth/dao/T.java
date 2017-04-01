package org.cisiondata.modules.auth.dao;

import java.util.HashMap;
import java.util.Map;

import org.cisiondata.utils.json.GsonUtils;

public class T {

	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("int", 1);
		map.put("long", 1L);
		String json = GsonUtils.fromMapToJson(map);
		Map<String, Object> newMap = GsonUtils.fromJsonToMap(json);
		System.out.println((int) newMap.get("long"));
		System.out.println((int) newMap.get("int"));
	}
	
}
