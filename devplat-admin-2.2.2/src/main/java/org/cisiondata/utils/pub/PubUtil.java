package org.cisiondata.utils.pub;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.entity.Header;
import org.cisiondata.utils.web.Head;

public class PubUtil {
	/**
	 * @desc 查询head公共方法
	 * @author libo
	 * @date 20170526
	 * @parameter list
	 * @return list
	 * @edit
	 * @editdate
	 */
	public static List<Object> getHeadList(List<Object> list) {
		List<Object> listHead = new ArrayList<Object>();
		Head head = new Head();
		for (int i = 0; i < list.size(); i++) {
			String headStr = list.get(i).toString();
			String[] headStrAtt = headStr.split(",");
			head = new Head();
			head.setField(headStrAtt[0]);
			head.setFieldName(headStrAtt[1]);
			listHead.add(head);
		}
		return listHead;
	}
	
	/**
	 * 查询headers公共方法
	 * @param list
	 * @return
	 */
	public static List<Header> getHeaderList(List<String> list) {
		List<Header> headers = new ArrayList<Header>();
		Header header = new Header();
		for (int i = 0; i < list.size(); i++) {
			String headerStr = list.get(i);
			String[] headStrAtt = headerStr.split(",");
			header = new Header();
			header.setFieldEN(headStrAtt[0]);
			header.setFieldCH(headStrAtt[1]);
			headers.add(header);
		}
		return headers;
	}

	/**
	 * 判断字符是否是手机号码
	 * 
	 * @param phone
	 *            需要判断是否是手机号码的字符
	 * @return
	 */
	public static boolean isMobilePhone(String mobilePhone) {
		if (StringUtils.isBlank(mobilePhone)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$");
		Matcher matcher = pattern.matcher(mobilePhone);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符是否是身份证
	 * 
	 * @param idCard
	 *            需要判断是否是身份证的字符
	 * @return
	 */
	public boolean isIdCard(String idCard) {
		if (StringUtils.isBlank(idCard)) {
			return false;
		}
		Pattern pattern1 = Pattern
				.compile("^[1-9]\\d{7}((0[1-9])||(1[0-2]))((0[1-9])||(1\\d)||(2\\d)||(3[0-1]))\\d{3}$");
		Pattern pattern2 = Pattern
				.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$");
		Matcher matcher = pattern1.matcher(idCard);
		Matcher matcher2 = pattern2.matcher(idCard);
		if (matcher.find() || matcher2.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 验证字符串是否全为中文 位数在2-10位
	 * 
	 * @param str
	 *            需要判断是否全为中文的字符
	 * @return
	 */
	public static boolean isChineseName(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]){2,10}$");
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 验证是否为邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (StringUtils.isBlank(email)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
		Matcher matcher = pattern.matcher(email);
		if (matcher.find()) {
			return true;
		}
		return false;
	}
	
	public static boolean isNum(String num) {
		if (StringUtils.isBlank(num))
			return false;
		Pattern pattern = Pattern.compile("^[0-9]+$");
		Matcher matcher = pattern.matcher(num);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

}
