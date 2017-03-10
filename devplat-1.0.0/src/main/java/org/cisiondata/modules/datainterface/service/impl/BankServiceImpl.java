package org.cisiondata.modules.datainterface.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.datainterface.Constants;
import org.cisiondata.modules.datainterface.entity.SwitchStatus;
import org.cisiondata.modules.datainterface.service.IBankService;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.http.HttpUtils;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service("bankService")
public class BankServiceImpl implements IBankService {

	@SuppressWarnings("unchecked")
	public Map<String, Object> readBankPhone(String phone)
			throws BusinessException {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> data = new HashMap<String, Object>();
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(phone);
		if(m.matches()){
			map.put("phone", phone);
			try {
				int status = (int) RedisClusterUtils.getInstance().get(
						"PhoneBank11_14");
				SwitchStatus switchStatus = SwitchStatus.getStatus(status);
				String binddata = "";
				String activedata = "";
				String loaddata = "";
				String changeLine = "";
				switch (switchStatus) {
				case DEMO:
					binddata = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20170117163130305UfbcCDSEibceumc\",\"data\":{\"result\":{\"debitCardCount\":\"2\",\"creditCardAge\":{\"min\":0,\"max\":0},\"creditCardCount\":\"0\",\"creditCardAging\":{\"min\":0,\"max\":0}},\"flag\":\"1\"}}";
					activedata = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20160811101840779sEVSljbyBCjNiAC\",\"data\":{\"flag\":\"1\",\"result\":{\"paymentShortTermVolatilityIndex\":\"0\",\"depositShortTermVolatilityIndex\":\"-6\",\"depositLongTermVolatilityIndex\":\"1\"}}}";
					loaddata = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20160811101840779sEVSljbyBCjNiAC\",\"data\":{\"flag\":\"1\",\"result\":{\"currentOutstandingLoanCount\":\"1\",\"currentOutstandingLoanAmount\":{\"minIncluded\":\"是\",\"maxIncluded\":\"是\",\"min\":0,\"max\":100,\"unit\":\"元\"}}}}";
					changeLine = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20170117163130225pVMUMNPnxAHgDKQ\",\"data\":{\"result\":{\"debitCardPayment3m\":{\"min\":0,\"max\":0},\"debitCardPayment12m\":{\"min\":0,\"max\":0},\"debitCardDeposit3m\":{\"min\":0,\"max\":0},\"debitCardDeposit12m\":{\"min\":0,\"max\":0},\"debitCardRemainingSum\":{\"min\":0,\"unit\":\"元\",\"max\":1000,\"maxIncluded\":\"否\",\"minIncluded\":\"否\"}},\"flag\":\"1\"}}";
					break;
				case TEST:
					binddata = HttpUtils.sendPost(Constants.TEST_URL
							+ "/queryPhoneBankCardBindInfo", map,
							Constants.APP_KEY, Constants.APP_KEY_VALUE);
					activedata = HttpUtils.sendPost(Constants.TEST_URL
							+ "/queryActive", map, Constants.APP_KEY,
							Constants.APP_KEY_VALUE);
					loaddata = HttpUtils.sendPost(Constants.TEST_URL
							+ "/queryLoadInfo", map, Constants.APP_KEY,
							Constants.APP_KEY_VALUE);
					changeLine = HttpUtils.sendPost(Constants.TEST_URL
							+ "/queryAccountChangeLines", map, Constants.APP_KEY,
							Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					binddata = HttpUtils.sendPost(Constants.NORMAL_URL
							+ "/queryPhoneBankCardBindInfo", map,
							Constants.APP_KEY, Constants.APP_KEY_VALUE);
					activedata = HttpUtils.sendPost(Constants.NORMAL_URL
							+ "/queryActive", map, Constants.APP_KEY,
							Constants.APP_KEY_VALUE);
					loaddata = HttpUtils.sendPost(Constants.NORMAL_URL
							+ "/queryLoadInfo", map, Constants.APP_KEY,
							Constants.APP_KEY_VALUE);
					changeLine = HttpUtils.sendPost(Constants.NORMAL_URL
							+ "/queryAccountChangeLines", map, Constants.APP_KEY,
							Constants.APP_KEY_VALUE);
					break;
				}
				// 存储状态码
				Map<String, Object> mapcode = new HashMap<String, Object>();
				// 存储数据
				Map<String, Map<String, Object>> mapresult = new HashMap<String, Map<String, Object>>();
				Gson gson = new Gson();
				/**
				 * 11
				 */
				// 借记卡Map
				Map<String, Object> mapJ = new HashMap<String, Object>();
				// 信用卡Map
				Map<String, Object> mapX = new HashMap<String, Object>();
				
				mapcode = gson.fromJson(changeLine, Map.class);
				int code = (int) (Double.parseDouble(mapcode.get("errorCode")
						.toString()));
				mapresult = gson.fromJson(changeLine, Map.class);
				int flagcode = (int) (Double.parseDouble(mapresult.get("data")
						.get("flag").toString()));
				if (code == 200) {
					if (flagcode == 1) {
						Map<String, Map<String, Object>> maps = (Map<String, Map<String, Object>>) mapresult
								.get("data").get("result");
						for (Map.Entry<String, Map<String, Object>> entry : maps
								.entrySet()) {
							Map<String, Object> mapRs = new HashMap<String, Object>();
							if (entry.getKey().equals("debitCardPayment3m")) {
								mapRs = entry.getValue();
								for (Map.Entry<String, Object> en : mapRs
										.entrySet()) {
									if (en.getKey().equals("minIncluded")) {
										mapJ.put("3个月出账是否包含最小值", en.getValue());
									}
									if (en.getKey().equals("maxIncluded")) {
										mapJ.put("3个月出账是否包含最大值", en.getValue());
									}
									if (en.getKey().equals("min")) {
										mapJ.put("3个月出账最小值", en.getValue());
									}
									if (en.getKey().equals("max")) {
										mapJ.put("3个月出账最大值", en.getValue());
									}
									if (en.getKey().equals("unit")) {
										mapJ.put("3个月出账单位", en.getValue());
									}
								}
								
							}
							if (entry.getKey().equals("debitCardPayment12m")) {
								mapRs = entry.getValue();
								for (Map.Entry<String, Object> en : mapRs
										.entrySet()) {
									if (en.getKey().equals("minIncluded")) {
										mapJ.put("12个月出账是否包含最小值", en.getValue());
									}
									if (en.getKey().equals("maxIncluded")) {
										mapJ.put("12个月出账是否包含最大值", en.getValue());
									}
									if (en.getKey().equals("min")) {
										mapJ.put("12个月出账最小值", en.getValue());
									}
									if (en.getKey().equals("max")) {
										mapJ.put("12个月出账最大值", en.getValue());
									}
									if (en.getKey().equals("unit")) {
										mapJ.put("12个月出账单位", en.getValue());
									}
								}
							}
							if (entry.getKey().equals("debitCardDeposit3m")) {
								mapRs = entry.getValue();
								for (Map.Entry<String, Object> en : mapRs
										.entrySet()) {
									if (en.getKey().equals("minIncluded")) {
										mapJ.put("3个月入账是否包含最小值", en.getValue());
									}
									if (en.getKey().equals("maxIncluded")) {
										mapJ.put("3个月入账是否包含最大值", en.getValue());
									}
									if (en.getKey().equals("min")) {
										mapJ.put("3个月入账最小值", en.getValue());
									}
									if (en.getKey().equals("max")) {
										mapJ.put("3个月入账最大值", en.getValue());
									}
									if (en.getKey().equals("unit")) {
										mapJ.put("3个月入账单位", en.getValue());
									}
								}
							}
							if (entry.getKey().equals("debitCardDeposit12m")) {
								mapRs = entry.getValue();
								for (Map.Entry<String, Object> en : mapRs
										.entrySet()) {
									if (en.getKey().equals("minIncluded")) {
										mapJ.put("12个月入账是否包含最小值", en.getValue());
									}
									if (en.getKey().equals("maxIncluded")) {
										mapJ.put("12个月入账是否包含最大值", en.getValue());
									}
									if (en.getKey().equals("min")) {
										mapJ.put("12个月入账最小值", en.getValue());
									}
									if (en.getKey().equals("max")) {
										mapJ.put("12个月入账最大值", en.getValue());
									}
									if (en.getKey().equals("unit")) {
										mapJ.put("12个月入账单位", en.getValue());
									}
								}
							}
							if (entry.getKey().equals("debitCardRemainingSum")) {
								mapRs = entry.getValue();
								for (Map.Entry<String, Object> en : mapRs
										.entrySet()) {
									if (en.getKey().equals("minIncluded")) {
										mapJ.put("余额总量是否包含最小值", en.getValue());
									}
									if (en.getKey().equals("maxIncluded")) {
										mapJ.put("余额总量是否包含最大值", en.getValue());
									}
									if (en.getKey().equals("min")) {
										mapJ.put("余额总量最小值", en.getValue());
									}
									if (en.getKey().equals("max")) {
										mapJ.put("余额总量最大值", en.getValue());
									}
									if (en.getKey().equals("unit")) {
										mapJ.put("余额总量单位", en.getValue());
									}
								}
							}
						}
					}
				} else {
					throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
				}
				/**
				 * 12
				 */
				mapcode = gson.fromJson(loaddata, Map.class);
				int loadcode = (int) (Double.parseDouble(mapcode.get("errorCode")
						.toString()));
				mapresult = gson.fromJson(loaddata, Map.class);
				int loadflag = (int) (Double.parseDouble(mapresult.get("data")
						.get("flag").toString()));
				if (loadcode == 200) {
					if (loadflag == 1) {
						Map<String, Map<String, Object>> maps = (Map<String, Map<String, Object>>) mapresult
								.get("data").get("result");
						for (Map.Entry<String, Map<String, Object>> entry : maps
								.entrySet()) {
							Map<String, Object> mapRs = new HashMap<String, Object>();
							if (entry.getKey().equals(
									"currentOutstandingLoanAmount")) {
								mapRs = entry.getValue();
								for (Map.Entry<String, Object> en : mapRs
										.entrySet()) {
									if (en.getKey().equals("minIncluded")) {
										mapJ.put("未结清贷款总金额是否包含最小值", en.getValue());
									}
									if (en.getKey().equals("maxIncluded")) {
										mapJ.put("未结清贷款总金额是否包含最大值", en.getValue());
									}
									if (en.getKey().equals("min")) {
										mapJ.put("未结清贷款总金额最小值", en.getValue());
									}
									if (en.getKey().equals("max")) {
										mapJ.put("未结清贷款总金额最大值", en.getValue());
									}
									if (en.getKey().equals("unit")) {
										mapJ.put("未结清贷款总金额单位", en.getValue());
									}
								}
							}
							if (entry.getKey()
									.equals("currentOutstandingLoanCount")) {
								mapJ.put("未结清贷款总笔数", entry.getValue());
							}
						}
					}
				} else {
					throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
				}
				/**
				 * 13
				 */
				mapcode = gson.fromJson(activedata, Map.class);
				int activecode = (int) (Double.parseDouble(mapcode.get("errorCode")
						.toString()));
				mapresult = gson.fromJson(activedata, Map.class);
				int activeflag = (int) (Double.parseDouble(mapresult.get("data")
						.get("flag").toString()));
				if (activecode == 200) {
					if (activeflag == 1) {
						Map<String, Object> maps = (Map<String, Object>) mapresult
								.get("data").get("result");
						for (Map.Entry<String, Object> entry : maps.entrySet()) {
							if (entry.getKey().equals(
									"paymentShortTermVolatilityIndex")) {
								mapJ.put("出账短期波动指数", entry.getValue());
							}
							if (entry.getKey().equals(
									"depositShortTermVolatilityIndex")) {
								mapJ.put("入账短期波动指数", entry.getValue());
							}
							if (entry.getKey().equals(
									"depositLongTermVolatilityIndex")) {
								mapJ.put("入账长期波动指数", entry.getValue());
							}
						}
					}
				} else {
					throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
				}
				/**
				 * 14
				 */
				mapcode = gson.fromJson(binddata, Map.class);
				int bindcode = (int) (Double.parseDouble(mapcode.get("errorCode")
						.toString()));
				mapresult = gson.fromJson(binddata, Map.class);
				int bindflag = (int) (Double.parseDouble(mapresult.get("data")
						.get("flag").toString()));
				if (bindcode == 200) {
					if (bindflag == 1) {
						Map<String, Map<String, Object>> maps = (Map<String, Map<String, Object>>) mapresult
								.get("data").get("result");
						for (Map.Entry<String, Map<String, Object>> entry : maps
								.entrySet()) {
							Map<String, Object> mapRs = new HashMap<String, Object>();
							if (entry.getKey().equals("creditCardAging")) {
								mapRs = entry.getValue();
								for (Map.Entry<String, Object> en : mapRs
										.entrySet()) {
									if (en.getKey().equals("minIncluded")) {
										mapX.put("账龄是否包含最小值", en.getValue());
									}
									if (en.getKey().equals("maxIncluded")) {
										mapX.put("账龄是否包含最大值", en.getValue());
									}
									if (en.getKey().equals("min")) {
										mapX.put("账龄最小值", en.getValue());
									}
									if (en.getKey().equals("max")) {
										mapX.put("账龄最大值", en.getValue());
									}
									if (en.getKey().equals("unit")) {
										mapX.put("账龄单位", en.getValue());
									}
								}
							}
							if (entry.getKey().equals("creditCardAge")) {
								mapRs = entry.getValue();
								for (Map.Entry<String, Object> en : mapRs
										.entrySet()) {
									if (en.getKey().equals("minIncluded")) {
										mapX.put("卡龄是否包含最小值", en.getValue());
									}
									if (en.getKey().equals("maxIncluded")) {
										mapX.put("卡龄是否包含最大值", en.getValue());
									}
									if (en.getKey().equals("min")) {
										mapX.put("卡龄最小值", en.getValue());
									}
									if (en.getKey().equals("max")) {
										mapX.put("卡龄最大值", en.getValue());
									}
									if (en.getKey().equals("unit")) {
										mapX.put("卡龄单位", en.getValue());
									}
								}
							}
							if (entry.getKey().equals("debitCardCount")) {
								mapJ.put("借记卡数量", entry.getValue());
							}
							if (entry.getKey().equals("creditCardCount")) {
								mapX.put("信用卡数量", entry.getValue());
							}
						}
					}
				} else {
					throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
				}
				if (mapJ.size() > 0 || mapX.size() > 0) {
					data.put("phone", phone);
					data.put("借记卡", mapJ);
					data.put("信用卡", mapX);
				}
			} catch (Exception e) {
				e.getMessage();
				throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
			}
		}else{
			throw new BusinessException(ResultCode.PARAM_ERROR);
		}
		if (data.size() == 0) {
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		}
		return data;
	}

	// 银行卡消费信息查询
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, String> readqueryQuota1(String bankCard)
			throws BusinessException {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> result = new HashMap<String, String>();
		Map<String, Map<String, Map<String, String>>> data = new HashMap<String, Map<String, Map<String, String>>>();
		map.put("bankCard", bankCard);
		map.put("idCard", "511322198103024382");
		map.put("phone", "13512345678");
		map.put("name", "张三");
		// map.put("index",
		// "S0659,S0477,S0474,S0660,S0661,S0464,S0467,S0462,S0465,S0466,S0468,S0469,S0517,S0677,S0618,S0622,S0623,S0624,S0625,S0670,S0671,S0210,S0211,S0213,S0214,S0049,S0050,S0043,S0044,S0132,S0133,S0657,S0658,S0674,S0493,S0662,S0491,S0470,S0663,S0516,S0471,S0472,S0664,S0652,S0473,S0665,S0653,S0654,S0655,S0492,S0494,S0475,S0476,S0480,S0481,S0503,S0504,S0483,S0656,S0518,S0519,S0520,S0521,S0522,S0523,S0524,S0525,S0526,S0527,S0528,S0529,S0530,S0531,S0487,S0488,S0489,S0490,S0605,S0606,S0607,S0608,S0609,S0610,S0611,S0612,S0613,S0614,S0532,S0533,S0499,S0500,S0145,S0146,S0147,S0148,S0149,S0150,S0495");
		map.put("index",
				"S0660,S0661,S0464,S0462,S0467,S0465,S0468,S0466,S0469,S0549,S0517,S0574,S0576,S0575,S0279,S0583,S0618,S0622,S0082,S0148");
		String kk = "";
		try {
			int status = (int) RedisClusterUtils.getInstance().get(
					"BankQueryQuota");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			switch (switchStatus) {
			case DEMO:
				// kk =
				// "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20170117142917946vthcScvMzUIGmHU\",\"data\":{\"statCode\":\"1000\",\"statMsg\":\"查询成功\",\"validate\":\"1\",\"result\":{\"quota\":{\"S0520\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0521\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_800.00;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0528\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_河北永通电子科技有限公司;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0529\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_廊坊市中医医院;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0526\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0527\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0524\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0525\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0522\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0523\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_1;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0656\":\"29%\",\"S0579\":\"2900_1;1200_1;1000_1;1460_3\",\"S0578\":\"2900_1;1200_1;1000_1;1460_4\",\"S0577\":\"2900_0.00;1200_800.00;1000_1000.00;1460_3600.00\",\"S0519\":\"01_NA;02_NA;03_NA;04_NA;05_NA;06_NA;07_NA;08_NA;09_NA;10_NA;11_1;12_1;13_NA;14_NA;15_NA;16_NA;17_1;18_NA;19_NA;20_NA;21_NA;22_NA;23_NA;24_NA\",\"S0474\":\"11370.00\",\"S0570\":\"2400.00\",\"S0128\":\"90%\",\"S0573\":\"三线城市\",\"S0530\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_河北永通电子科技有限公司;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0531\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_廊坊市中医医院;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0537\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_1;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0078\":\"3\",\"S0538\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3;1606_2;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0539\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_4;1606_3;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0075\":\"810.00\",\"S0534\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_800.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0535\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_2990.00;1606_1600.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0536\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3000.00;1606_2400.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0567\":\"80%\",\"S0566\":\"75%\",\"S0131\":\"100%\",\"S0569\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_4;1606_3;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0568\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3000.00;1606_2400.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0467\":\"未知\",\"S0136\":\"2900\",\"S0468\":\"金穗通宝卡(银联卡)\",\"S0135\":\"20160728\",\"S0469\":\"农业银行\",\"S0134\":\"0.00\",\"S0464\":\"借记卡\",\"S0561\":\"100%;100%;100%;100%;100%;100%;100%;100%;100%;100%;100%;100%\",\"S0465\":\"62银联标准卡\",\"S0138\":\"银联智慧信息服务（上海）有限公司\",\"S0560\":\"100%;100%;100%;100%;100%;60%;100%;100%;100%;100%;100%;100%\",\"S0466\":\"未知\",\"S0563\":\"90%\",\"S0562\":\"100%;100%;100%;100%;25%;60%;100%;100%;100%;100%;100%;100%\",\"S0565\":\"90%\",\"S0462\":\"人民币卡\",\"S0564\":\"100%\",\"S0660\":\"未知\",\"S0661\":\"否\",\"S0087\":\"1200_1;1460_2\",\"S0503\":\"NA;NA;NA;NA;NA;廊坊市\",\"S0086\":\"1200_1;1460_1\",\"S0506\":\"20160415\",\"S0507\":\"20160728\",\"S0504\":\"廊坊市\",\"S0505\":\"3\",\"S0083\":\"1200_800.00;1460_0.00\",\"S0084\":\"1200_800.00;1460_0.00\",\"S0554\":\"3\",\"S0553\":\"2\",\"S0552\":\"0\",\"S0551\":\"810.00\",\"S0550\":\"800.00\",\"S0053\":\"1611_0.00,0.00,0.00,0.00,0.00,0.00;1610_0.00,0.00,0.00,0.00,0.00,0.00;1609_0.00,0.00,0.00,0.00,0.00,0.00;1608_0.00,0.00,0.00,0.00,0.00,0.00;1607_0.00,0.00,0.00,0.00,0.00,0.00;1606_0.00,0.00,0.00,800.00,0.00,0.00\",\"S0054\":\"1611_0,0,0,0,0,0;1610_0,0,0,0,0,0;1609_0,0,0,0,0,0;1608_0,0,0,0,0,0;1607_0,0,0,1,0,0;1606_0,0,0,1,0,0\",\"S0510\":\"银联智慧信息服务（上海）有限公司\",\"S0511\":\"810.00\",\"S0512\":\"3\",\"S0513\":\"7\",\"S0517\":\"未知\",\"S0518\":\"01_NA;02_NA;03_NA;04_NA;05_NA;06_NA;07_NA;08_NA;09_NA;10_NA;11_0.00;12_0.00;13_NA;14_NA;15_NA;16_NA;17_800.00;18_NA;19_NA;20_NA;21_NA;22_NA;23_NA;24_NA\",\"S0509\":\"0.00\",\"S0549\":\"0.00\",\"S0584\":\"1460;2900;1200\",\"S0508\":\"2900\",\"S0548\":\"1607;1604\",\"S0585\":\"1460;1200;2900\",\"S0545\":\"3\",\"S0580\":\"2900_0.00;1460_3600.00;1000_1000.00;1200_800.00\",\"S0544\":\"270.00\",\"S0581\":\"2900_1;1460_5;1000_1;1200_1\",\"S0547\":\"2\",\"S0582\":\"2900_1;1460_4;1000_1;1200_1\",\"S0546\":\"1607;1606;1604\",\"S0541\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_800.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0540\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_4;1606_2;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0483\":\"1460\",\"S0543\":\"1607;1604\",\"S0542\":\"270.00\",\"S0024\":\"01_50%;03_38%;07_12%\",\"S0021\":\"01;03;07\",\"S0277\":\"2\",\"S0276\":\"2\",\"S0279\":\"0.00\",\"S0278\":\"2\",\"S0572\":\"6011;8062;4814;6012\",\"S0571\":\"1611:NA;1610:NA;1609:NA;1608:NA;1607:2000.00_1460_20160704_6011_31253001廊坊市管道局新十区（银河大街129,0.00_1460_20160716_4814_河北永通电子科技有限公司,0.00_2900_20160728_6012_银联智慧信息服务（上海）有限公司,1000.00_1000_20160714_6011_中国建设银行;1606:800.00_1200_20160616_8062_廊坊市中医医院,1000.00_1460_20160622_6011_08378280廊坊市银河北路129号,600.00_1460_20160622_6011_31253001廊坊市管道局新十区（银河大街129;1605:NA;1604:0.00_1460_20160415_4814_河北永通电子科技有限公司;1603:NA;1602:NA;1601:NA;1512:NA\",\"S0616\":\"S22;S24;S31\",\"S0617\":\"61\",\"S0615\":\"3\",\"S0646\":\"6011;8062;4814;6012\",\"S0648\":\"4\",\"S0600\":\"0%\",\"S0590\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1000.00;1606_1800.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0629\":\"85%\",\"S0597\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0;1606_0;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0598\":\"0%\",\"S0595\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0;1606_0;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0596\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0559\":\"7;7.7%\",\"S0593\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_0;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0558\":\"149%\",\"S0635\":\"88%;NA\",\"S0634\":\"1607_2;1606_2;NA\",\"S0557\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_100.00%;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA\",\"S0594\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0633\":\"1607_3000.00;1606_1600.00;NA\",\"S0556\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_33.33%;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA\",\"S0591\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_2;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0632\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1000,1460;1606_1460;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0555\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_24.58%;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA\",\"S0592\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_2000.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0631\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_2;1606_2;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0630\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3000.00;1606_1600.00;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0599\":\"100%\",\"S0670\":\"0.00\",\"S0671\":\"0\",\"S0515\":\"75%\",\"S0281\":\"2000.00\",\"S0618\":\"1\",\"S0282\":\"0\",\"S0280\":\"2000.00\",\"S0626\":\"41\",\"S0018\":\"3\",\"S0625\":\"0\",\"S0284\":\"2\",\"S0628\":\"41\",\"S0586\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0627\":\"20161104\",\"S0283\":\"2\",\"S0587\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_0;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0622\":\"1\",\"S0110\":\"12\",\"S0621\":\"1611_3;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0624\":\"0\",\"S0623\":\"0\",\"S0620\":\"1611:41_3;1610:NA;1609:NA;1608:NA;1607:NA;1606:NA;1605:NA;1604:NA;1603:NA;1602:NA;1601:NA;1512:NA\",\"S0588\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_600.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0589\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0;1606_1;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0609\":\"廊坊市中医医院\",\"S0608\":\"河北永通电子科技有限公司\",\"S0607\":\"廊坊市中医医院\",\"S0238\":\"0\",\"S0682\":\"07_0.00_1;NA\",\"S0239\":\"0\",\"S0236\":\"0.00\",\"S0235\":\"0.00\",\"S0232\":\"0\",\"S0233\":\"0\",\"S0230\":\"0.00\",\"S0612\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0613\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0610\":\"河北永通电子科技有限公司\",\"S0611\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0614\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0651\":\"1\",\"S0650\":\"6011;4814;6012;8062;NA\",\"S0229\":\"0.00\",\"S0226\":\"0\",\"S0227\":\"0\",\"S0222\":\"0.00\",\"S0260\":\"0.00\",\"S0223\":\"0.00\",\"S0224\":\"0.00\",\"S0268\":\"0\",\"S0266\":\"0.00\",\"S0645\":\"6011;8062;4814;6012;NA\",\"S0265\":\"0.00\",\"S0647\":\"6011_4600.00;8062_800.00;4814_0.00;6012_0.00\",\"S0263\":\"0\",\"S0262\":\"0\",\"S0649\":\"6011_4;4814_2;6012_1;8062_1\",\"S0603\":\"2000.00;S24\",\"S0604\":\"2000.00;S24\",\"S0269\":\"0\",\"S0188\":\"0.00\",\"S0259\":\"0.00\",\"S0187\":\"0.00\",\"S0185\":\"0\",\"S0148\":\"0_0%\",\"S0184\":\"0\",\"S0149\":\"河北永通电子科技有限公司_50%;廊坊市中医医院_50%\",\"S0667\":\"25;29;14;19;17\",\"S0146\":\"廊坊市中医医院_100%\",\"S0668\":\"11_0.00;12_0.00;13_0.00;14_0.00;15_0.00;16_0.00;17_0.00;18_0.00;19_0.00;20_0.00;21_0.00;22_0.00;23_0.00;24_0.00;25_4600.00;26_0.00;27_0.00;28_0.00;29_800.00;30_0.00;31_0.00;32_0.00\",\"S0147\":\"廊坊市中医医院_100%\",\"S0253\":\"0.00\",\"S0145\":\"0_0%\",\"S0666\":\"11_0;12_0;13_0;14_2;15_0;16_0;17_0;18_0;19_0;20_0;21_0;22_0;23_0;24_0;25_4599;26_0;27_0;28_0;29_807;30_0;31_0;32_0\",\"S0256\":\"0\",\"S0257\":\"0\",\"S0254\":\"0.00\",\"S0669\":\"25;29;14;17;22\",\"S0182\":\"0.00\",\"S0181\":\"0.00\",\"S0199\":\"800.00\",\"S0200\":\"800.00\",\"S0196\":\"0\",\"S0672\":\"0.00\",\"S0673\":\"2\",\"S0197\":\"0\",\"S0241\":\"0.00\",\"S0678\":\"0.00\",\"S0242\":\"0.00\",\"S0514\":\"2000.00;S24\",\"S0679\":\"0.00\",\"S0244\":\"0\",\"S0245\":\"0\",\"S0114\":\"0.00\",\"S0150\":\"河北永通电子科技有限公司_67%;廊坊市中医医院_33%\",\"S0191\":\"0\",\"S0446\":\"0.00\",\"S0445\":\"0.00\",\"S0193\":\"0.00\",\"S0448\":\"0\",\"S0119\":\"1\",\"S0194\":\"0.00\",\"S0118\":\"1\",\"S0117\":\"0\",\"S0449\":\"0\",\"S0116\":\"800.00\",\"S0115\":\"800.00\",\"S0190\":\"0\",\"S0433\":\"0.00\",\"S0432\":\"0.00\",\"S0121\":\"75%\",\"S0431\":\"0\",\"S0120\":\"100%\",\"S0430\":\"0\",\"S0123\":\"100%\",\"S0122\":\"85%\",\"S0125\":\"100%\",\"S0124\":\"100%\",\"S0476\":\"0\",\"S0127\":\"80%\",\"S0439\":\"0.00\",\"S0438\":\"0.00\",\"S0437\":\"0\",\"S0436\":\"0\",\"S0435\":\"0\",\"S0434\":\"0.00\",\"S0132\":\"0\",\"S0422\":\"0.00\",\"S0130\":\"100%\",\"S0421\":\"0.00\",\"S0133\":\"0\",\"S0428\":\"0.00\",\"S0427\":\"0.00\",\"S0429\":\"0\",\"S0424\":\"0\",\"S0460\":\"0\",\"S0461\":\"0\",\"S0426\":\"0.00\",\"S0425\":\"0\",\"S0407\":\"0\",\"S0406\":\"0\",\"S0403\":\"0.00\",\"S0213\":\"0\",\"S0404\":\"0.00\",\"S0401\":\"0\",\"S0211\":\"0.00\",\"S0210\":\"0.00\",\"S0400\":\"0\",\"S0665\":\"浙江省建行营业部ATM_NA_1000.00\",\"S0214\":\"0\",\"S0085\":\"0_0\",\"S0451\":\"0.00\",\"S0455\":\"0\",\"S0454\":\"0\",\"S0452\":\"0.00\",\"S0082\":\"0_0.00\",\"S0109\":\"12\",\"S0108\":\"0\",\"S0458\":\"0.00\",\"S0457\":\"0.00\",\"S0492\":\"1\",\"S0416\":\"0.00\",\"S0418\":\"0\",\"S0419\":\"0\",\"S0412\":\"0\",\"S0413\":\"0\",\"S0480\":\"0.514\",\"S0415\":\"0.00\",\"S0410\":\"0.00\",\"S0516\":\"0.00\",\"S0113\":\"0\",\"S0112\":\"0\",\"S0440\":\"0.00\",\"S0111\":\"0\",\"S0442\":\"0\",\"S0441\":\"0\",\"S0444\":\"0.00\",\"S0443\":\"0\",\"S0481\":\"0.642\",\"S0447\":\"0\",\"S0409\":\"0.00\",\"S0023\":\"01_57%;03_29%;07_14%\",\"S0378\":\"0.00\",\"S0022\":\"00_0%\",\"S0020\":\"01;03;07\",\"S0237\":\"0\",\"S0234\":\"0.00\",\"S0379\":\"0.00\",\"S0231\":\"0\",\"S0166\":\"0\",\"S0167\":\"0\",\"S0532\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_河北永通电子科技有限公司;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0225\":\"0\",\"S0228\":\"0.00\",\"S0221\":\"0\",\"S0174\":\"0%\",\"S0175\":\"0%\",\"S0533\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_河北永通电子科技有限公司;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0220\":\"0\",\"S0420\":\"0.00\",\"S0267\":\"0\",\"S0264\":\"0.00\",\"S0261\":\"0\",\"S0423\":\"0\",\"S0408\":\"0.00\",\"S0390\":\"0.00\",\"S0405\":\"0\",\"S0391\":\"0.00\",\"S0258\":\"0.00\",\"S0189\":\"0\",\"S0392\":\"0.00\",\"S0186\":\"0.00\",\"S0402\":\"0.00\",\"S0217\":\"0.00\",\"S0252\":\"0.00\",\"S0216\":\"0.00\",\"S0250\":\"0\",\"S0251\":\"0\",\"S0219\":\"0\",\"S0218\":\"0.00\",\"S0255\":\"0\",\"S0450\":\"0.00\",\"S0453\":\"0\",\"S0459\":\"0\",\"S0398\":\"0.00\",\"S0495\":\"廊坊市_NA_银行银河路;廊坊市_广阳区_新华路\",\"S0397\":\"0.00\",\"S0183\":\"0\",\"S0180\":\"0.00\",\"S0399\":\"0\",\"S0456\":\"0.00\",\"S0394\":\"0\",\"S0393\":\"0\",\"S0396\":\"0.00\",\"S0003\":\"2\",\"S0395\":\"0\",\"S0247\":\"0.00\",\"S0248\":\"0.00\",\"S0381\":\"0\",\"S0417\":\"0\",\"S0249\":\"0\",\"S0195\":\"0\",\"S0198\":\"0.00\",\"S0202\":\"1\",\"S0414\":\"0.00\",\"S0201\":\"0\",\"S0204\":\"0.00\",\"S0240\":\"0.00\",\"S0203\":\"1\",\"S0205\":\"0.00\",\"S0411\":\"0\",\"S0243\":\"0\",\"S0208\":\"1\",\"S0151\":\"0\",\"S0207\":\"0\",\"S0152\":\"14;29\",\"S0153\":\"14;29\",\"S0246\":\"0.00\",\"S0019\":\"00\",\"S0389\":\"0_0\",\"S0192\":\"0.00\",\"S0388\":\"0_0\",\"S0387\":\"0_0\",\"S0386\":\"0_0.00\",\"S0385\":\"0_0.00\",\"S0384\":\"0_0.00\",\"S0051\":\"1611_0;1610_0;1609_0;1608_0;1607_1;1606_1;1605_0;1604_1;1603_0;1602_0;1601_0;1512_0\",\"S0382\":\"0\"},\"active\":null}}}";
				kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20170301160145932iCKqKKDEwVtVYmo\",\"data\":{\"statCode\":\"1000\",\"statMsg\":\"查询成功\",\"calCode\":2,\"validate\":\"1\",\"result\":{\"quota\":{\"S0279\":\"0.00\",\"S0622\":\"0\",\"S0618\":\"1\",\"S0517\":\"未知\"},\"active\":null}}}";
				break;
			case TEST:
				kk = HttpUtils.sendPost(Constants.TEST_URL + "/queryQuota",
						map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
				break;
			case NORMAL:
				kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryQuota",
						map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
				break;
			}
			// String kk = HttpUtils.sendPost(Constants.NORMAL_URL +
			// "/queryQuota", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);

		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		}
		Gson gson = new Gson();
		Map<String, Map<String, Map<String, Map<String, String>>>> re = gson
				.fromJson(kk, Map.class);
		Map<String, Object> gg = gson.fromJson(kk, Map.class);
		Map<String, Map<String, String>> ss = gson.fromJson(kk, Map.class);
		int sss = (int) (Double.parseDouble(gg.get("errorCode").toString()));
		if (sss == 200) {
			data = re.get("data");
			Map<String, Map<String, String>> maps = data.get("result");
			Map<String, String> getQuota = maps.get("quota");
			if (getQuota.containsKey("S0660")) {
				result.put("银行卡发卡地", getQuota.get("S0660"));
			}
			if (getQuota.containsKey("S0661")) {
				result.put("是否银联高端客户", getQuota.get("S0661"));
			}
			if (getQuota.containsKey("S0464")) {
				result.put("卡性质", getQuota.get("S0464"));
			}
			if (getQuota.containsKey("S0467")) {
				result.put("卡等级", getQuota.get("S0467"));
			}
			if (getQuota.containsKey("S0462")) {
				result.put("卡种", getQuota.get("S0462"));
			}
			if (getQuota.containsKey("S0465")) {
				result.put("卡品牌", getQuota.get("S0465"));
			}
			if (getQuota.containsKey("S0466")) {
				result.put("卡产品", getQuota.get("S0466"));
			}
			if (getQuota.containsKey("S0468")) {
				result.put("卡名称", getQuota.get("S0468"));
			}
			if (getQuota.containsKey("S0469")) {
				result.put("发卡行", getQuota.get("S0469"));
			}
			if (getQuota.containsKey("S0517")) {
				result.put("账户性质", getQuota.get("S0517"));
			}
			if (getQuota.containsKey("S0549")) {
				result.put("近3月交易总金额（消费类）", getQuota.get("S0549"));
			}
			if (getQuota.containsKey("S0574")) {
				result.put("近3月交易金额城市分布", getQuota.get("S0574"));
			}
			if (getQuota.containsKey("S0575")) {
				result.put("近3月交易笔数城市分布", getQuota.get("S0575"));
			}
			if (getQuota.containsKey("S0576")) {
				result.put("近3月交易天数城市分布", getQuota.get("S0576"));
			}
			if (getQuota.containsKey("S0583")) {
				result.put("近3月TOP3交易地列表", getQuota.get("S0583"));
			}
			if (getQuota.containsKey("S0279")) {
				result.put("近1月最大单日累计取现金额 ", getQuota.get("S0279"));
			}
			if (getQuota.containsKey("S0618")) {
				result.put("是否存在盗卡风险 ", getQuota.get("S0618"));
			}
			if (getQuota.containsKey("S0622")) {
				result.put("是否发生吞卡", getQuota.get("S0622"));
			}
			if (getQuota.containsKey("S0148")) {
				result.put("近1月消费高频商户分布", getQuota.get("S0148"));
			}
			if (getQuota.containsKey("S0082")) {
				result.put("近1月消费城市金额分布", getQuota.get("S0082"));
			}
			String statCode = ss.get("data").get("statCode");
			if (("2000").equals(statCode) || ("2001").equals(statCode)
					|| ("8000").equals(statCode) || ("8001").equals(statCode)
					|| ("8002").equals(statCode)) {
				result.put("银行卡状态", ss.get("data").get("statMsg"));
				result.put("银行卡卡号", bankCard);
			} else if (("1000").equals(statCode) || ("1001").equals(statCode)) {
				result.put("银行卡卡号", bankCard);
			} else {
				throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
			}
		} else {
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		}

		if (result.size() < 2) {
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		}
		return result;
	}

}
