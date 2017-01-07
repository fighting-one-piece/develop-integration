package org.cisiondata.modules.datada.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.datada.service.IDatadaService;
import org.cisiondata.utils.encryption.SHAUtils;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.http.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service("datadaService")
public class DatadaServiceImpl implements IDatadaService {

	private Logger LOG = LoggerFactory.getLogger(DatadaServiceImpl.class);

	private static final String URL = "https://datada.cn/openapi.php/dso/supersearch";

	private static final String ACCESS_KEY_ID = "FrRPmYfb9RkDrvdT";

	private static final String ACCESS_KEY_SECRET = "qDASfw8QMEZQr3CF4rpzwv5F7ZU9AYAT";

	private Gson gson = new Gson();

	@Override
	public Map<String, Object> readDatadaDatas(String query) throws BusinessException {
		String dateline = String.valueOf(Calendar.getInstance().getTime().getTime()).substring(0, 10);
		return crawlDatada(query, "0", dateline).getResultList().get(0);
	}

	@Override
	public QueryResult<Map<String, Object>> readDatadaDatas(String query, String dateline, String searchToken)
			throws BusinessException {
		if (StringUtils.isBlank(dateline) || "null".equals(dateline)) {
			dateline = String.valueOf(Calendar.getInstance().getTime().getTime()).substring(0, 10);
		}
		if (StringUtils.isBlank(searchToken)) {
			searchToken = "0";
		}
		QueryResult<Map<String, Object>> finalResult = new QueryResult<Map<String, Object>>();
		QueryResult<Map<String, Object>> tempResult = crawlDatada(query, searchToken, dateline);
		int item_count = 0;
		// Set<String> itemIds = new HashSet<String>();
		while (null != tempResult.getScrollId() && item_count < 10) {
			List<Map<String, Object>> resultList = tempResult.getResultList();
			if (!resultList.isEmpty()) {
				for (int i = 0, len = resultList.size(); i < len; i++) {
					Map<String, Object> result = resultList.get(i);
					// String itemId = (String) result.get("itemId");
					// if (itemIds.contains(itemId)) continue;
					// itemIds.add(itemId);
					finalResult.getResultList().add(result);
					item_count++;
				}
				finalResult.setScrollId(tempResult.getScrollId());
				finalResult.setTotalRowNum(tempResult.getTotalRowNum());
			}
			tempResult = crawlDatada(query, tempResult.getScrollId(), dateline);
		}
		return finalResult;
	}

	private QueryResult<Map<String, Object>> crawlDatada(String query, String searchToken, String dateline) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("access_key_id", ACCESS_KEY_ID);
			params.put("access_key_secret", ACCESS_KEY_SECRET);
			params.put("size", "5");
			params.put("kw", query);
			params.put("dateline", dateline);
			params.put("searchToken", searchToken);
			List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(params.entrySet());
			Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
				@Override
				public int compare(Entry<String, String> o1, Entry<String, String> o2) {
					return o1.getKey().compareTo(o2.getKey());
				}
			});
			StringBuffer sb = new StringBuffer();
			StringBuffer all_sb = new StringBuffer();
			for (Map.Entry<String, String> entry : list) {
				String paramName = entry.getKey();
				if (!"access_key_secret".equals(paramName)) {
					sb.append(paramName).append("=").append(entry.getValue()).append("&");
				}
				all_sb.append(paramName).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8")).append("&");
			}
			if (all_sb.length() > 0)
				all_sb.deleteCharAt(all_sb.length() - 1);
			sb.append("signature=").append(SHAUtils.SHA1(all_sb.toString()));
			String json = HttpUtils.sendPost(URL, sb.toString());
			DatadaResponse datadaResponse = gson.fromJson(json, DatadaResponse.class);
			QueryResult<Map<String, Object>> qr = new QueryResult<Map<String, Object>>();
			if (null == datadaResponse) return qr;
			DataResponse dataResponse = datadaResponse.getData();
			qr.setScrollId(dataResponse.getSearchToken());
			qr.setTotalRowNum(Long.parseLong(dateline));
			if (null != dataResponse) {
				List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
				for (ResArrayResponse item : dataResponse.getResArray()) {
					Map<String, Object> result = new HashMap<String, Object>();
					result.put("itemId", item.getItemId());
					result.put("fields", item.getFields());
					result.put("data", item.getData());
					resultList.add(result);
				}
				qr.setResultList(resultList);
			}
			return qr;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return new QueryResult<Map<String, Object>>();
		}
	}

	// 查询所有集群中是否有该手机号码
	@Override
	public boolean readPhoneIsExists(String phone) throws BusinessException {
		boolean existFlag = false;
		Pattern p = Pattern.compile("1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}");
		Matcher m = p.matcher(phone);
		if (m.matches()) {
		}
		return existFlag;
	}

	// 查询身份证是否在集群中
	@Override
	public boolean readIdCardIsExists(String idCard) throws BusinessException {
		boolean existFlag = false;
		Pattern p = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
		Matcher m = p.matcher(idCard);
		if (m.matches()) {
		}
		return existFlag;
	}

	public static void main(String[] args) {
		String query = "13512345678";
		IDatadaService datadaService = new DatadaServiceImpl();
		QueryResult<Map<String, Object>> qr = datadaService.readDatadaDatas(query, null, "0");
		for (Map<String, Object> result : qr.getResultList()) {
			for (String data : (String[]) result.get("data")) {
				System.out.print(data + " ");
			}
		}
	}

	class DatadaResponse {

		private Long ret = null;
		private String msg = null;
		private DataResponse data = null;

		public Long getRet() {
			return ret;
		}

		public void setRet(Long ret) {
			this.ret = ret;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public DataResponse getData() {
			return data;
		}

		public void setData(DataResponse data) {
			this.data = data;
		}
	}

	class DataResponse {

		private String searchToken = null;
		private ResArrayResponse[] resArray = null;

		public String getSearchToken() {
			return searchToken;
		}

		public void setSearchToken(String searchToken) {
			this.searchToken = searchToken;
		}

		public ResArrayResponse[] getResArray() {
			return resArray;
		}

		public void setResArray(ResArrayResponse[] resArray) {
			this.resArray = resArray;
		}
	}

	class ResArrayResponse {

		private String[] data = null;
		private String[] fields = null;
		private String itemId = null;

		public String[] getData() {
			return data;
		}

		public void setData(String[] data) {
			this.data = data;
		}

		public String[] getFields() {
			return fields;
		}

		public void setFields(String[] fields) {
			this.fields = fields;
		}

		public String getItemId() {
			return itemId;
		}

		public void setItemId(String itemId) {
			this.itemId = itemId;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("{itemId:").append(itemId).append(",data:[");
			for (int i = 0, len = data.length; i < len; i++) {
				sb.append(data[i]).append(",");
			}
			sb.append("],fields:[");
			for (int j = 0, len = fields.length; j < len; j++) {
				sb.append(fields[j]).append(",");
			}
			sb.append("]}");
			return sb.toString();
		}

	}

}
