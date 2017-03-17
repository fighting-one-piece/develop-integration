package org.cisiondata.utils.json;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GsonUtils {

	private static Gson gson = null;
	
	static {
		gson = new GsonBuilder()
				.serializeSpecialFloatingPointValues()
				.setDateFormat("yyyy-MM-dd HH:mm:ss")
				.registerTypeAdapter(new TypeToken<List<Object>>(){}.getType(), new ListDeserializer())
				.registerTypeAdapter(new TypeToken<Map<String, Object>>(){}.getType(), new MapDeserializer())
				.create();
	}
	
	public static Gson builder() {
		return gson;
	}
	
	/**
	 * List 转换 JSON
	 * @param list
	 * @return
	 */
	public static String fromListToJson(List<Object> list) {
		return builder().toJson(list, new TypeToken<List<Object>>(){}.getType());
	}
	
	/** 
	 * JSON 转换  List
	 * @param json
	 * @return
	 */
	public static List<Object> fromJsonToList(String json) {
		return builder().fromJson(json, new TypeToken<List<Object>>(){}.getType());
	}
	
	/** 
	 * JSON 转换  Map
	 * @param json
	 * @return
	 */
	public static Map<String, Object> fromJsonToMap(String json) {
		return builder().fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
	}
	
	/**
	 * Map 转换 JSON
	 * @param map
	 * @return
	 */
	public static String fromMapToJson(Map<String, Object> map) {
		return builder().toJson(map, new TypeToken<Map<String, Object>>(){}.getType());
	}
	
}
