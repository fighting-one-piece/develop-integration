package org.cisiondata.utils.pub;

import org.cisiondata.utils.properties.PropertiesUtils;

public class SystemUtils {
	
	/**
	 * 获取系统变量
	 * @param key
	 * @return
	 */
	public static String getSysProperty(String key) {
		return System.getProperty(key);
	}
	
	/**
	 * 获取环境变量
	 * @param key
	 * @return
	 */
	public static String getEnvProperty(Object key) {
		return System.getenv().get(key);
	}
	
	public static String getCurrentEnv() {
		return PropertiesUtils.getProperty("spring/applicationContext-config.properties", "target-env");
	}
	
}
