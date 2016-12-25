package org.cisiondata.utils.date;

import java.util.HashMap;
import java.util.Map;

public enum DateFormatter {

	TIME("yyyy-MM-dd HH:mm:ss"), DATE("yyyy-MM-dd"), MONTH("yyyy-MM");

	private String formatStr;
	private static Map<String, ThreadLocal<java.text.SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<java.text.SimpleDateFormat>>();

	private DateFormatter(String s) {
		this.formatStr = s;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public java.text.SimpleDateFormat get() {
		ThreadLocal<java.text.SimpleDateFormat> res = (ThreadLocal) sdfMap.get(this.formatStr);
		if (null == res) {
			synchronized (sdfMap) {
				if (null == res) {
					res = new ThreadLocal() {
						protected java.text.SimpleDateFormat initialValue() {
							return new java.text.SimpleDateFormat(
									DateFormatter.this.formatStr);
						}
					};
					sdfMap.put(this.formatStr, res);
				}
			}
		}
		return (java.text.SimpleDateFormat) res.get();
	}

}
