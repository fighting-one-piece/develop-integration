package org.cisiondata.utils.regex;

public class RegexUtils {
	//手机号码
	public static final String isMoblePhone = "1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}";
	//座机号码
	public static final String isTelePhone ="(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)";

	//邮箱
	public static final String isEmail = "[\\w!#$%&'*+//=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+//=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
	
	public static boolean isMoblePhone(String phone) {
		if (phone.matches(isMoblePhone)) {
			return true;
		}
		return false;
	}
	
	public static boolean isTelePhone(String phone) {
		if (phone.matches(isTelePhone)) {
			return true;
		}
		return false;
	}
	
	public static boolean isEmail(String email) {
		if (email.matches(isEmail)) {
			return true;
		}
		return false;
	}
	
}
