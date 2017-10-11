package org.cisiondata.utils.web;

import java.util.HashSet;
import java.util.Set;

public class URLFilter {
	
	public interface NOT_AUTH {
		public static final String URL_01 = "/login";
		public static final String URL_02 = "/jcaptcha.jpg";
		public static final String URL_03 = "/jcaptcha-validate";
		public static final String URL_04 = "/verificationCode.jpg";
		public static final String URL_05 = "/users/sms/verify";
		public static final String URL_06 = "/users/account/verify";
	}
	
	public interface MUST_AUTH {
		public static final String URL_01 = "/users/sms";
		public static final String URL_02 = "/users/settings/profile";
		public static final String URL_03 = "/users/settings/security";
		public static final String URL_04 = "/users/security/questions";
		public static final String URL_05 = "/users/settings/security/verify";
		public static final String URL_06 = "/users/settings/security/question";
		public static final String URL_07 = "/users/settings/ip/sms";
		public static final String URL_08 = "/users/ip/verify";
	}
	
	public interface MQ {
		public static final String URL_01 = "/supersearch/exportexcel";
	}
	
	private static Set<String > notAuthenticationUrls = new HashSet<String>();
	
	private static Set<String > mustAuthenticationUrls = new HashSet<String>();
	
	private static Set<String> mqticationUrls = new HashSet<String>();
	
	static {
		notAuthenticationUrls.add(NOT_AUTH.URL_01);
		notAuthenticationUrls.add(NOT_AUTH.URL_02);
		notAuthenticationUrls.add(NOT_AUTH.URL_03);
		notAuthenticationUrls.add(NOT_AUTH.URL_04);
		notAuthenticationUrls.add(NOT_AUTH.URL_05);
		notAuthenticationUrls.add(NOT_AUTH.URL_06);
		
		mustAuthenticationUrls.add(MUST_AUTH.URL_01);
		mustAuthenticationUrls.add(MUST_AUTH.URL_02);
		mustAuthenticationUrls.add(MUST_AUTH.URL_03);
		mustAuthenticationUrls.add(MUST_AUTH.URL_04);
		mustAuthenticationUrls.add(MUST_AUTH.URL_05);
		mustAuthenticationUrls.add(MUST_AUTH.URL_06);
		mustAuthenticationUrls.add(MUST_AUTH.URL_07);
		mustAuthenticationUrls.add(MUST_AUTH.URL_08);
		
		mqticationUrls.add(MQ.URL_01);
	}
	
	public static Set<String> notAuthenticationUrls() {
		return notAuthenticationUrls;
	}
	
	public static Set<String> mustAuthenticationUrls() {
		return mustAuthenticationUrls;
	}
	
	public static boolean mqFilterUrl(String url) {
		return mqticationUrls.contains(url) ? false : true;
	}

}
