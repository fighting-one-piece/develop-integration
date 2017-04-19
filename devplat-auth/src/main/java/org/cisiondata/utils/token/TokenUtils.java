package org.cisiondata.utils.token;

import org.cisiondata.utils.endecrypt.Base32Utils;
import org.cisiondata.utils.endecrypt.Base64Utils;
import org.cisiondata.utils.endecrypt.MD5Utils;
import org.cisiondata.utils.endecrypt.SHAUtils;

public class TokenUtils {
	
	/** 认证KEY*/
	public static final String AUTHENTICATION = "authentication";
	/** 授权KEY*/
	public static final String AUTHORIZATION = "authorization";
	
	/** admin认证KEY*/
	public static final String ADMIN_AUTHENTICATION = "adminauthentication";
	
	/**
	 * MD5摘要
	 * @param params
	 * @return
	 */
	public static String genMD5Token(String... params) {
		if (null == params || params.length == 0) return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0, len = params.length; i < len; i++) {
			sb.append(params[i]);
		}
		return MD5Utils.hash(SHAUtils.SHA512(sb.toString()));
    }
	
	/**
	 * 认证MD5摘要
	 * @param params
	 * @return
	 */
	public static String genAuthenticationMD5Token(String... params) {
		if (null == params || params.length == 0) return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0, len = params.length; i < len; i++) {
			sb.append(params[i]);
		}
		sb.append(AUTHENTICATION);
		return MD5Utils.hash(SHAUtils.SHA512(sb.toString()));
    }
	
	/**
	 * 授权MD5摘要
	 * @param params
	 * @return
	 */
	public static String genAuthorizationMD5Token(String... params) {
		if (null == params || params.length == 0) return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0, len = params.length; i < len; i++) {
			sb.append(params[i]);
		}
		sb.append(AUTHORIZATION);
		return MD5Utils.hash(SHAUtils.SHA512(sb.toString()));
    }
	
	/**
	 * Base32摘要
	 * @param params
	 * @return
	 */
	public static String genBase32Token(String... params) {
		if (null == params || params.length == 0) return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0, len = params.length; i < len; i++) {
			sb.append(params[i]);
		}
		return Base32Utils.encode(SHAUtils.SHA512(sb.toString()));
    }

	/**
	 * Base64摘要
	 * @param params
	 * @return
	 */
	public static String genBase64Token(String... params) {
		if (null == params || params.length == 0) return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0, len = params.length; i < len; i++) {
			sb.append(params[i]);
		}
		return Base64Utils.encode(SHAUtils.SHA512(sb.toString()));
    }
	
	/**
	 * 验证Token是否正确
	 * @param token
	 * @param params
	 * @return
	 */
	public static boolean authenticationMD5Token(String token, String... params) {
		return token.equals(genMD5Token(params)) ? true : false;
	}
	
	/**
	 * 验证是否是认证Token
	 * @param token
	 * @param params
	 * @return
	 */
	public static boolean isAuthenticationMD5Token(String token, String... params) {
		return token.equals(genAuthenticationMD5Token(params)) ? true : false;
	}
	
	/**
	 * 验证是否是授权Token
	 * @param token
	 * @param params
	 * @return
	 */
	public static boolean isAuthorizationMD5Token(String token, String... params) {
		return token.equals(genAuthorizationMD5Token(params)) ? true : false;
	}
	
	/**
	 * admin认证MD5摘要
	 * @param params
	 * @return
	 */
	public static String genAdminAuthenticationMD5Token(String... params) {
		if (null == params || params.length == 0) return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0, len = params.length; i < len; i++) {
			sb.append(params[i]);
		}
		sb.append(ADMIN_AUTHENTICATION);
		return MD5Utils.hash(SHAUtils.SHA512(sb.toString()));
    }
	
	/**
	 * 验证是否是admin认证Token
	 * @param token
	 * @param params
	 * @return
	 */
	public static boolean isAdminAuthenticationMD5Token(String token, String... params) {
		return token.equals(genAdminAuthenticationMD5Token(params)) ? true : false;
	}
	
	
	
	
	public static void main(String[] args) {
		String account = "test";
		String password = "5cecc5b1dedbfcb56942f253dbaf6b84297ccd2baec1be4f7ca23fc6928a608b0de58b0dba3feaf872549a6933668c385700546e8c6ea48cb93650aa2ce9f48a";
		String ip = "192.168.0.1";
		System.out.println(genMD5Token(account, password));
		System.out.println("######");
		System.out.println(genBase32Token(account, password));
		System.out.println("######");
		System.out.println(genBase64Token(account, password));
		System.out.println("######");
		System.out.println(genMD5Token(account, password, ip));
		System.out.println("######");
		System.out.println(genBase32Token(account, password, ip));
		System.out.println("######");
		System.out.println(genBase64Token(account, password, ip));
	}
	
}
