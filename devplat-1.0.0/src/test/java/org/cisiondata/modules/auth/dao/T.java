package org.cisiondata.modules.auth.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class T {

	public static void main(String[] args) {
		String key = "/index/{index}/type/{type}/labels\b";
		if (key.contains("{") && key.contains("}")) {
			key = key.substring(0, key.indexOf("\b"));
			List<String> names = new ArrayList<String>();
			Pattern pattern = Pattern.compile("\\{[^/]+\\}");
			Matcher matcher = pattern.matcher(key);
			StringBuffer sb = new StringBuffer();
			while (matcher.find()) {
				names.add(matcher.group().replaceAll("\\{([^/]+)\\}", "$1"));
				matcher.appendReplacement(sb, matcher.group().replaceAll("\\{[^/]+\\}", "([^/]+)"));
			}
			matcher.appendTail(sb);
			key = sb.toString() + "\b" + StringUtils.join(names, ",");
		}
		String url = "/index/financial/type/logistics/labels";
		String[] names = key.substring(key.indexOf("\b") + 1).split(",");
		key = key.substring(0, key.indexOf("\b"));
		if (url.matches(key)) {
			Map<String, String> params = new HashMap<String, String>();
			for (int i = 0; i < names.length; i++) {
				String tmp = url.replaceAll(key, "$" + (i + 1));
				System.out.println(tmp);
				params.put(names[i], tmp);
			}
		}
	}
	
}
