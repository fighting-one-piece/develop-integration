package org.cisiondata.modules.datainterface;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 获取地址
 * 
 * @author fb
 *
 */
public class URLUtil {
	// MD5
	public static String MD5(String md5) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 得到sign
	@SuppressWarnings("deprecation")
	public static String gen_sign(String secret, HashMap<String, String> data) {
		List<String> list = new ArrayList<String>();
		for (HashMap.Entry<String, String> entry : data.entrySet()) {
			String value = entry.getValue();
			String key = entry.getKey();
			System.out.println("当前key:" + key);
			list.add(key + URLEncoder.encode(value).replace("+", "%20"));
		}
		Collections.sort(list);
		String str = secret + String.join("", list) + secret;
		System.out.println("sign::" + str);
		return MD5(str);
	}

	// 得到url
	public static String querystring(String secret, HashMap<String, String> data) throws NoSuchAlgorithmException {
		String sign = gen_sign(secret, data);
		data.put("sign", sign);
		List<String> list = new ArrayList<String>();
		for (HashMap.Entry<String, String> entry : data.entrySet()) {
			String value = entry.getValue();
			String key = entry.getKey();
			list.add(key + "=" + value);
		}
		return String.join("&", list);
	}


//	// md5
//	public static String MD5(String md5) {
//		try {
//			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
//			byte[] array = md.digest(md5.getBytes());
//			StringBuffer sb = new StringBuffer();
//			for (int i = 0; i < array.length; ++i) {
//				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
//			}
//			return sb.toString();
//		} catch (java.security.NoSuchAlgorithmException e) {
//		}
//		return null;
//	}
//
//	// generate sign
//	public static String gen_sign(String secret, HashMap<String, String> data) {
//		List<String> l = new ArrayList<String>();
//		for (HashMap.Entry<String, String> entry : data.entrySet()) {
//			String v = entry.getValue();
//			String k = entry.getKey();
//			System.out.println("当前key:" + k);
//			l.add(k + URLEncoder.encode(v).replace("+", "%20"));
//		}
//		Collections.sort(l);
//		String s = secret + String.join("", l) + secret;
//		System.out.println("sign::" + s);
//		return MD5(s);
//	}
//
//	// generate query uri
//	public static String querystring(String secret, HashMap<String, String> data) throws NoSuchAlgorithmException {
//		List<String> liest = new ArrayList<String>();
//		for (HashMap.Entry<String, String> entry : data.entrySet()) {
//			String v = entry.getValue();
//			String k = entry.getKey();
//			System.out.println("当前key:" + k);
//			liest.add(k + URLEncoder.encode(v).replace("+", "%20"));
//		}
//		Collections.sort(liest);
//		String s = secret + String.join("", liest) + secret;
//		System.out.println("sign::" + s);
//		StringBuffer sb = new StringBuffer();
//		java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
//		byte[] array = md.digest(s.getBytes());
//		for (int i = 0; i < array.length; ++i) {
//			sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
//		}
//		String sign = sb.toString();
//		data.put("sign", sign);
//		List<String> l = new ArrayList<String>();
//		for (HashMap.Entry<String, String> entry : data.entrySet()) {
//			String v = entry.getValue();
//			String k = entry.getKey();
//			l.add(k + "=" + v);
//		}
//		return String.join("&", l);
//	}
//
//	public static void main(String[] args) throws NoSuchAlgorithmException {
//		String app_key = "3926502285";
//		String app_secret = "g4hyxjqrkcmlqe9g1meb74qlo2ix4j95";
//		HashMap<String, String> data = new HashMap<String, String>();
//		long date = System.currentTimeMillis() / 1000L;
//		data.put("enc_m", "13522842086");
//		data.put("t", String.valueOf(date));
//		data.put("app_key", app_key);
//		System.out.println("生成的URL后段：" + querystring(app_secret, data));
//	}
}
