package org.cisiondata.utils.regex;

public class RegexUtils {
	//手机号码
	public static final String isMoblePhone = 
			"1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])(\\d{8}|\\*{4}\\d{4}|\\*{5}\\d{3}|\\*{8})";
	public static boolean isMoblePhone(String phone) {
		if (phone.matches(isMoblePhone)) {
			return true;
		}
		return false;
	}
}
