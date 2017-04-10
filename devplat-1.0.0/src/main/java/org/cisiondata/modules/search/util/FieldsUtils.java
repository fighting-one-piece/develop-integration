package org.cisiondata.modules.search.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.auth.entity.FieldEncryptType;
import org.cisiondata.modules.auth.entity.ResourceInterfaceField;

public class FieldsUtils {
	
	private static final String BANK_CARD_REX = "\\d{10,19}";
	private static final String IDCARD_REX = "\\d{17}(\\d|X|x)|\\d{15}";
	private static final String QQ_REX = "\\d{5,14}";
	private static final Pattern highLightPattern = Pattern.compile("<span style=\"color:red\">[^</span>]*</span>");
	private static final String HIGH_LIGHT_PRE = "<span style=\"color:red\">";
	private static final String HIGH_LIGHT_END = "</span>";
	
	/**
	 * 获取返回的head
	 * @return key=字段名,value=字段中文名
	 */
	public static Map<String,String> getFieldsMessageSource(List<ResourceInterfaceField> list){
		Map<String,String> map = new HashMap<String,String>();
		for (ResourceInterfaceField re : list) {
			map.put(re.getFieldEN(), re.getFieldCH());
		}
		return map;
	}

	/**
	 * 根据字段过滤queryResult（结果数据）
	 * @param qr
	 * @param fields
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static QueryResult<Map<String,Object>> filterQueryResultByFields(QueryResult<Map<String,Object>> qr,List<ResourceInterfaceField> list){
		if(null == qr) return new QueryResult<Map<String,Object>>();
		if(null == list || list.size() == 0) return qr;
		List<Map<String,Object>> resultList = qr.getResultList();
		if(resultList.size() == 0) return qr;
		QueryResult<Map<String,Object>> newQr = new QueryResult<Map<String,Object>>();
		List<Map<String,Object>> newList = new ArrayList<Map<String,Object>>();
		newQr.setScrollId(qr.getScrollId());
		newQr.setTotalRowNum(qr.getTotalRowNum());
		for (Map<String,Object> map : resultList){
			Map<String,Object> resultMap = (Map<String,Object>)map.get("data");
			if(null == resultMap) resultMap = map;
			Map<String,Object> newResultMap = new HashMap<String,Object>();
			for(ResourceInterfaceField re : list){
				if (resultMap.containsKey(re.getFieldEN())) {
					newResultMap.put(re.getFieldEN(), encryptField((String)resultMap.get(re.getFieldEN()), re.getEncryptType()));
				} else {
					newResultMap.put(re.getFieldEN(), "");
				}
				//可链接
				if(re.getIsLink() == true) {
					newResultMap.put(re.getFieldEN()+"-linked", true);
				}
				//可链接到地图
				if(re.getIsLinkMap() == true) {
					newResultMap.put(re.getFieldEN()+"-linkedMap", true);
				}
			}
			newList.add(newResultMap);
		}
		newQr.setResultList(newList);
		return newQr;
	}
	
	
	/**
	 * 根据加密方法加密字段
	 * @param value
	 * @param status
	 * @return
	 */
	public static String encryptField(String value ,Integer status){
		if("NA".equals(value) || StringUtils.isBlank(value)) return value;
		value = value.trim();
		Matcher matcher = highLightPattern.matcher(value);
		switch (FieldEncryptType.getFieldEncryptType(status)) {
		case NO_ENCRYPT:
			return value;
		case ACCUMULATION_FUND_CARD_ENCRYPT:
			if(matcher.find()) {//没有高亮代码
				if (value.length() > 4){
					StringBuilder accumulation = new StringBuilder(value);
					accumulation.replace(accumulation.length() - 4, accumulation.length(), "****");
					return accumulation.toString();
				} else {
					return value;
				}
			} else {//有高亮代码
				return value;
			}
		case BANK_CARD_ENCRYPT:
			if(matcher.find()) {//没有高亮代码
				if(value.matches(BANK_CARD_REX)){
					StringBuilder bankcard = new StringBuilder(value);
					StringBuilder encryptionBankcard = new StringBuilder();
					encryptionBankcard.append(bankcard.substring(0, 6));
					int num = bankcard.length() - 9;
					for (int i = 0 ; i < num ; i++) {
						encryptionBankcard.append("*");
					}
					encryptionBankcard.append(bankcard.substring(bankcard.length() - 3, bankcard.length()));
					
					return encryptionBankcard.toString();
				} else {
					return value;
				}
			} else {//有高亮代码
				return value;
			}
		case ADDRESS_ENCRYPT:
			List<String> queryList = new ArrayList<String>();
			while(matcher.find()) {
				queryList.add(matcher.group().replaceAll(HIGH_LIGHT_PRE, "").replaceAll(HIGH_LIGHT_END, ""));
			}
			if (queryList.size() == 0){
				if(value.length() <= 6) return value;
				StringBuilder address = new StringBuilder(value);
				address.delete(6, address.length());
				address.append("***");
				return address.toString();
			} else {//有高亮代码
				value = value.replaceAll(HIGH_LIGHT_PRE, "").replaceAll(HIGH_LIGHT_END, "");
				StringBuilder address = new StringBuilder(value);
				if(value.length() <= 6){
					for (String query : queryList){
						value = value.replaceAll(query, HIGH_LIGHT_PRE + query + HIGH_LIGHT_END);
					}
					return value;
				} else {
					
					address.delete(6, address.length());
					address.append("***");
					value = address.toString();
					for (String query : queryList){
						value = value.replaceAll(query, HIGH_LIGHT_PRE + query + HIGH_LIGHT_END);
					}
					return value;
				}
			}
		case EMAIL_ENCRYPT:
			return value;
		case IDCARD_ENCRYPT:
			if (matcher.find()) {//没有高亮代码
				if(value.matches(IDCARD_REX)){
					StringBuilder idcard = new StringBuilder(value);
					if (value.length() == 18){
						idcard.replace(6, 14, "********");
						return idcard.toString();
					} else {
						idcard.replace(6, 12, "******");
						return idcard.toString();
					}
				} else {
					return value;
				}
			} else {//有高亮代码
				return value;
			}
		case MOBILE_ENCRYPT:
			StringBuilder sb = new StringBuilder(value);
			if(matcher.find()) {//没有高亮代码
				if (value.length() == 11) {
					if (value.startsWith("1")){
						sb.replace(3, 7, "****");
						return sb.toString();
					} else {
						sb.replace(sb.length()-4, sb.length(), "****");
						return sb.toString();
					}
				} else if(value.length() < 11 && value.length() > 4) {
					sb.replace(sb.length()-4, sb.length(), "****");
					return sb.toString();
				} else if(value.length() > 11) {
					if(value.contains(",")){
						sb.delete(0, sb.length());
						String[] phones = value.split(",");
						for(String phone : phones){
							StringBuilder sb1 = new StringBuilder();
							sb1.append(phone);
							if (phone.length() == 11) {
								if (phone.startsWith("1")){
									sb1.replace(3, 7, "****");
									if(sb.length() == 0){
										sb.append(sb1.toString());
									} else {
										sb.append(",").append(sb1.toString());
									}
								} else {
									sb1.replace(sb1.length()-4, sb1.length(), "****");
									if(sb.length() == 0){
										sb.append(sb1.toString());
									} else {
										sb.append(",").append(sb1.toString());
									}
								}
							} else if(phone.length() < 11 && phone.length() > 4) {
								sb1.replace(sb1.length()-4, sb1.length(), "****");
								if(sb.length() == 0){
									sb.append(sb1.toString());
								} else {
									sb.append(",").append(sb1.toString());
								}
							} 
						}
						return sb.toString();
						
					} else {
						return value;
					}
				} else if (value.length() < 4){
					return value;
				}
			} else {//有高亮代码
				return value;
			}
		case NAME_ENCRYPT:
			List<String> namequeryList = new ArrayList<String>();
			while(matcher.find()) {
				namequeryList.add(matcher.group().replaceAll(HIGH_LIGHT_PRE, "").replaceAll(HIGH_LIGHT_END, ""));
			}
			StringBuilder name = new StringBuilder(value);
			if (namequeryList.size() == 0) {//没有高亮代码
				if (value.length() <2) {
					return value;
				} else {
					for(int i = 1 ; i<name.length(); i++) {
						name.replace(i, i+1, "*");
					}
					return name.toString();
				}
			} else {//有高亮代码
				value = value.replaceAll(HIGH_LIGHT_PRE, "").replaceAll(HIGH_LIGHT_END, "");
				name = new StringBuilder(value);
				if (name.length() < 2) {
					for (String query : namequeryList){
						value = value.replaceAll(query, HIGH_LIGHT_PRE + query + HIGH_LIGHT_END);
					}
					return value;
				} else {
					for(int i = 1 ; i<name.length(); i++) {
						name.replace(i, i+1, "*");
					}
					value = name.toString();
					for (String query : namequeryList){
						value = value.replaceAll(query, HIGH_LIGHT_PRE + query + HIGH_LIGHT_END);
					}
					return value;
				}
			}
		case PASSWORD_ENCRYPT:
			return "*****";
		case PHOTO_ENCRYPT:
			return value;
		case QQ_ENCRYPT:
			if (matcher.find()) {//没有高亮代码
				
				if (value.matches(QQ_REX)) {
					StringBuilder qq = new StringBuilder(value);
					qq.replace(qq.length() - 4, qq.length(), "****");
					return qq.toString();
				} else {
					return value;
				}
			} else {//有高亮代码
				return value;
			}
		case TIME_ENCRYPT:
			return value;
		default:
			return value;
		}
		
	}
	
}
