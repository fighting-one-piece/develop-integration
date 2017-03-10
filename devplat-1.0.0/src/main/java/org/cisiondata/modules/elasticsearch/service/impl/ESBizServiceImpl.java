package org.cisiondata.modules.elasticsearch.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.elasticsearch.service.IESBizService;
import org.cisiondata.utils.endecrypt.MD5Utils;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.message.MessageUtils;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

@Service("esBizService")
public class ESBizServiceImpl extends ESServiceImpl implements IESBizService {
	
	private static final String CN_REG = "[\\u4e00-\\u9fa5]+";
	
	private ExecutorService executorService = Executors.newCachedThreadPool();
	
	@Override
	protected Object wrapperValue(String key, Object value) {
		//TODO
//		String account = (String) SecurityUtils.getSubject().getPrincipal();
//		return key.toLowerCase().indexOf("password") != -1 && !"liqien".equals(account) ? "********" : value;
		return value;
	}
	
	@Override
	public List<Map<String, Object>> readDataListByCondition(String query, int size) 
			throws BusinessException {
		return readDataListByCondition(buildBoolQuery(query), size);
	}
	
	@Override
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(
			String query, String scrollId, int size) throws BusinessException {
//		return readPaginationDataListWithThread(query, scrollId, size);
		return readPaginationDataListByCondition(buildBoolQuery(query), scrollId, size);
	}

	@Override
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, String type, 
			String query, String scrollId, int size) throws BusinessException {
		return readPaginationDataListByCondition(index, type, query, scrollId, size, true);
	}
	
	@Override
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, String type, 
			String query, String scrollId, int size, boolean isHighLight) throws BusinessException {
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		for (String attribute : identity_attributes) {
			queryBuilder.should(QueryBuilders.termQuery(attribute, query));
		}
		for (String attribute : name_attributes) {
			queryBuilder.should(QueryBuilders.termQuery(attribute, query));
		}
		return readPaginationDataListByCondition(index, type, queryBuilder, scrollId, size, isHighLight);
	}
	
	@Override
	public QueryResult<Map<String, Object>> readPaginationDataListByMultiCondition(String scrollId,Integer rowNumPerPage,String index,String type,String query)
			throws BusinessException {
		if(StringUtils.isBlank(query)){
			throw new BusinessException(ResultCode.KEYWORD_NOT_NULL);
		}
		String[] qus = query.split(",");
		if(qus.length < 1 || rowNumPerPage == null || StringUtils.isBlank(index) || StringUtils.isBlank(type)){
			throw new BusinessException(ResultCode.PARAM_ERROR);
		}
		BoolQueryBuilder qb = QueryBuilders.boolQuery();
		Pattern pattern = Pattern.compile(CN_REG);
		for(int i = 0; i < qus.length; i++){
			String[] qu = qus[i].split(":");
			if(qu.length != 2) throw new BusinessException(ResultCode.PARAM_ERROR);
			boolean isChinese = pattern.matcher(qu[0]).find();
			if (isChinese) {
				qb.must(QueryBuilders.matchPhraseQuery(qu[0], qu[1]));
			} else{
				qb.must(QueryBuilders.termQuery(qu[0], qu[1]));
			}
		}
		QueryResult<Map<String, Object>> qr = readPaginationDataListByCondition(index, type, qb, scrollId, rowNumPerPage);
		if(qr.getResultList() == null || qr.getResultList().size() == 0)
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		return qr;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> readLabelsAndHitsIncludeTypes(String query, String... includeTypes)
			throws BusinessException {
		if (null == includeTypes || includeTypes.length == 0) includeTypes = defaultTypes();
		String labelsHitCacheKey = genLabelHitCacheKey(query, includeTypes);
		Object cacheObject = RedisClusterUtils.getInstance().get(labelsHitCacheKey);
		if (null != cacheObject) {
			List<Map<String, Object>> resultList = (List<Map<String, Object>>) cacheObject;
			if(resultList.size() == 0) throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
			return (List<Map<String, Object>>) cacheObject;
		}
		LOG.info("read labels and hits method with cache not hit");
		Map<String, Future<Map<String, Object>>> fmap = new HashMap<String, Future<Map<String, Object>>>();
		for (int i = 0, len = includeTypes.length; i < len; i++) {
			String type = includeTypes[i];
			fmap.put(type, executorService.submit(new ReadLabelsAndHitsThread(
					type_index_mapping.get(type), type, query)));
		}
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (Map.Entry<String, Future<Map<String, Object>>> entry : fmap.entrySet()) {
			Future<Map<String, Object>> future = entry.getValue();
			Map<String, Object> result = null;
			try {
				result = future.get(20, TimeUnit.SECONDS);
			} catch (Exception e) {
				result = new HashMap<String, Object>();
				String type = entry.getKey();
				String index = type_index_mapping.get(type);
				result.put("hits", -1);
				result.put("index", index);
				result.put("type", type);
				result.put("label", MessageUtils.getInstance().getMessage(index + "." + type));
				LOG.error(e.getMessage(), e);
			}
			if (!result.isEmpty()) resultList.add(result);
		}
		RedisClusterUtils.getInstance().set(labelsHitCacheKey, resultList, 180);
		if(resultList.size() == 0) throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		return resultList;
	}
	
	@Override
	public List<Map<String, Object>> readLabelsAndHitsExcludeTypes(String query, String... excludeTypes)
			throws BusinessException {
		if (null == excludeTypes || excludeTypes.length == 0) return readLabelsAndHitsIncludeTypes(query);
		List<String> excludeTypeList = Arrays.asList(excludeTypes);
		Set<String> includeTypeList = new HashSet<String>();
		for (String type : types) {
			if (excludeTypeList.contains(type)) continue;
			includeTypeList.add(type);
		}
		return readLabelsAndHitsIncludeTypes(query, includeTypeList.toArray(new String[0]));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<Map<String, Object>> readLabelPaginationDataList(String index, String type, String query,
			String scrollId, int size) throws BusinessException {
		if(StringUtils.isBlank(index) || StringUtils.isBlank(type) || size <=0)
			throw new BusinessException(ResultCode.PARAM_ERROR);
		if(StringUtils.isBlank(query))
			throw new BusinessException(ResultCode.KEYWORD_NOT_NULL);
		String labelDatasCacheKey = genLabelDatasCacheKey(index, type, query);
		Object cacheObject = RedisClusterUtils.getInstance().get(labelDatasCacheKey);
		QueryResult<Map<String, Object>> qr = new QueryResult<Map<String,Object>>();
		if (null != cacheObject) {
			qr = (QueryResult<Map<String, Object>>) cacheObject;
		} else {
			qr = readPaginationDataListByCondition(index, type, query, null, 100, false);
			qr.setResultList(new ArrayList<Map<String, Object>>(qr.getResultList()));
			RedisClusterUtils.getInstance().set(labelDatasCacheKey, qr, 120);
		}
		System.out.println("result size: " + qr.getResultList().size());
		long totalRowNum = qr.getTotalRowNum();
		int from = StringUtils.isBlank(scrollId) ? 1 : Integer.parseInt(scrollId);
		qr.setScrollId(String.valueOf(from + 1));
		int fromIndex = (from - 1) * size;
		int toIndex = (fromIndex + size) > totalRowNum ? (int) totalRowNum : (fromIndex + size);
		LOG.info("fromIndex {} toIndex {}", fromIndex, toIndex);
		List<Map<String, Object>> resultList = fromIndex <= toIndex ? 
				qr.getResultList().subList(fromIndex, toIndex) : new ArrayList<Map<String, Object>>();
		wrapperQQQunInformation(type, resultList);
		if(null == resultList || resultList.size() == 0) throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		qr.setResultList(resultList);
		return qr;
	}
	
	@Override
	public List<Map<String, Object>> readLogisticsDataList(String query, boolean isHighLight) 
			throws BusinessException {
		BoolQueryBuilder queryBuilder = null;
		if (query.trim().indexOf(" ") == -1) {
			queryBuilder = buildLogisticsSBoolQuery(query);
		} else {
			queryBuilder = new BoolQueryBuilder();
			String[] keywords = query.trim().split(" ");
			for (int i = 0, len = keywords.length; i < len; i++) {
				queryBuilder.must(buildLogisticsSBoolQuery(keywords[i]));
			}
		}
		return readDataListByCondition("financial", "logistics", queryBuilder, isHighLight);
	}
	
	@Override
	public List<Map<String, Object>> readLogisticsFilterDataList(String query) throws BusinessException {
		List<Map<String, Object>> logisticsFilterDataList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> logisticsDataList = readLogisticsDataList(query, false);
		Map<String, Object> logisticsFilterData = null;
		Map<String, Object> logisticsData = null;
		Set<String> locations = new HashSet<String>();
		for (int i = 0, len = logisticsDataList.size(); i < len; i++) {
			logisticsData = logisticsDataList.get(i);
			String senderProvince = null != logisticsData.get("寄件人省") ? (String)logisticsData.get("寄件人省") : "";
			String senderCity = null != logisticsData.get("寄件人市") ? (String)logisticsData.get("寄件人市") : "";
			String senderCounty = null != logisticsData.get("寄件人县") ? (String)logisticsData.get("寄件人县") : "";
			String senderAddress = null != logisticsData.get("寄件人地址") ? (String)logisticsData.get("寄件人地址") : "";
			String senderName = null != logisticsData.get("寄件人姓名") ? (String)logisticsData.get("寄件人姓名") : "";
			String receiverProvince = null != logisticsData.get("收件人省") ? (String)logisticsData.get("收件人省") : "";
			String receiverCity = null != logisticsData.get("收件人市") ? (String)logisticsData.get("收件人市") : "";
			String receiverCounty = null != logisticsData.get("收件人县") ? (String)logisticsData.get("收件人县") : "";
			String receiverAddress = null != logisticsData.get("收件人地址") ? (String)logisticsData.get("收件人地址") : "";
			String receiverName = null != logisticsData.get("收件人姓名") ? (String)logisticsData.get("收件人姓名") : "";
			String location = new StringBuilder().append(senderProvince).append(senderCity).append(senderCounty)
					.append(senderAddress).append(senderName).append(receiverProvince).append(receiverCity)
					.append(receiverCounty).append(receiverAddress).append(receiverName).toString();
			if (locations.contains(location)) continue;
			locations.add(location);
			logisticsFilterData = new HashMap<String, Object>();
			logisticsFilterData.put("寄件人省", senderProvince);
			logisticsFilterData.put("寄件人市", senderCity);
			logisticsFilterData.put("寄件人县", senderCounty);
			logisticsFilterData.put("寄件人地址", senderAddress);
			logisticsFilterData.put("寄件人姓名", senderName);
			logisticsFilterData.put("寄件人手机号", logisticsData.get("寄件人手机号"));
			logisticsFilterData.put("收件人省", receiverProvince);
			logisticsFilterData.put("收件人市", receiverCity);
			logisticsFilterData.put("收件人县", receiverCounty);
			logisticsFilterData.put("收件人地址", receiverAddress);
			logisticsFilterData.put("收件人姓名", receiverName);
			logisticsFilterData.put("收件人手机号", logisticsData.get("收件人手机号"));
			logisticsFilterData.put("下单日期", logisticsData.get("下单日期"));
			logisticsFilterData.put("下单时间", logisticsData.get("下单时间"));
			logisticsFilterDataList.add(logisticsFilterData);
		}
		return logisticsFilterDataList;
	}
	
	@Override
	public List<Map<String, Object>> readLogisticsRelationsDataList(String query) throws BusinessException {
		List<Map<String, Object>> dataList = readLogisticsDataList(query, false);
		Map<String, Object> data = null;
		String sName, sMobilePhone, rName, rMobilePhone = null;
		for (int i = 0, len = dataList.size(); i < len; i++) {
			data = dataList.get(i);
			sName = (String) data.get("寄件人姓名");
			sMobilePhone = (String) data.get("寄件人手机号");
			rName = (String) data.get("收件人姓名");
			rMobilePhone = (String) data.get("收件人手机号");
			if (judegEquals(sName, query) && judegEquals(sMobilePhone, query) &&
					judegEquals(rName, query) && judegEquals(rMobilePhone, query)) continue;
			
		}
		return null;
	}
	
	private boolean judegEquals(String s1, String s2) {
		if (StringUtils.isBlank(s1) || StringUtils.isBlank(s2)) return false;
		return s1.trim().equals(s2.trim()) ? true : false;
	}
	
	private BoolQueryBuilder buildBoolQuery(String index, String type, String keyword) {
		boolean isChineseWord = isChineseWord(keyword);
		Map<String, Set<String>> index_type_attributes = index_type_attributes_mapping.get(index + type);
		Set<String> q_identity_attributes = index_type_attributes.get("identity_attributes");
		if (!isChineseWord && q_identity_attributes.size() == 0) return null;
		Set<String> q_chinese_attributes = index_type_attributes.get("chinese_attributes");
		if (isChineseWord && q_chinese_attributes.size() == 0) return null;
		return buildBoolQuery(keyword, q_identity_attributes, q_chinese_attributes);
	}
	
	private BoolQueryBuilder buildBoolQuery(String keyword) {
		return buildBoolQuery(keyword, identity_attributes, chinese_attributes);
	}
	
	private BoolQueryBuilder buildBoolQuery(String keyword, Set<String> q_identity_attributes, 
			Set<String> q_chinese_attributes) {
		keyword = keyword.trim().toLowerCase();
		if (keyword.indexOf(" ") == -1) {
			return buildSingleBoolQuery(keyword, q_identity_attributes, q_chinese_attributes);
		}
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		String[] keywords = keyword.split(" ");
		for (int i = 0, len = keywords.length; i < len; i++) {
			boolQueryBuilder.must(buildSingleBoolQuery(keywords[i], q_identity_attributes, q_chinese_attributes));
		}
		return boolQueryBuilder;
	}
	
	private BoolQueryBuilder buildSingleBoolQuery(String keyword, Set<String> q_identity_attributes, 
			Set<String> q_chinese_attributes) {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolean isChineseWord = isChineseWord(keyword);
		Set<String> attributes = isChineseWord ? q_chinese_attributes : q_identity_attributes;
//		for (String attribute : attributes) {
//			boolQueryBuilder.should(isChineseWord ? QueryBuilders.matchPhraseQuery(attribute, keyword)
//					: QueryBuilders.termQuery(attribute, keyword));
//		}
		for (String attribute : attributes) {
			if (isChineseWord && attribute.indexOf("name") == -1) {
				boolQueryBuilder.should(QueryBuilders.matchPhraseQuery(attribute, keyword));
			} else {
				boolQueryBuilder.should(QueryBuilders.termQuery(attribute, keyword));
			}
		}
		return boolQueryBuilder;
	}
	
	private BoolQueryBuilder buildLogisticsSBoolQuery(String keyword) {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolean isChineseWord = isChineseWord(keyword);
		String[] attributes = isChineseWord ? new String[]{"linkName","linkProvince","linkCity","linkCounty",
				"linkAddress","name","nickname","province","city","county","address","good","expressCompany",
				"sendCompany","expressContent"}: new String[]{"linkMobilePhone","linkTelePhone","linkIdCard",
						"mobilePhone","telePhone","idCard","email","idCode"};
		for (int i = 0, len = attributes.length; i < len; i++) {
			boolQueryBuilder.should(isChineseWord ? QueryBuilders.matchPhraseQuery(attributes[i], keyword)
					: QueryBuilders.termQuery(attributes[i], keyword));
		}
		return boolQueryBuilder;
	}
	
	private boolean isChineseWord(String keyword) {
		return Pattern.compile(CN_REG).matcher(keyword).find();
	}
	
	@SuppressWarnings({ "unchecked" })
	public QueryResult<Map<String, Object>> readPaginationDataListWithThread(String query,
			String scrollId, int size) {
		List<Future<QueryResult<Map<String, Object>>>> fs = 
				new ArrayList<Future<QueryResult<Map<String, Object>>>>();
		for (Map.Entry<String, List<String>> index_types : index_types_mapping.entrySet()) {
			String index = index_types.getKey();
			List<String> types = index_types.getValue();
			for (int i = 0, len = types.size(); i < len; i++) {
				String type = types.get(i);
				fs.add(executorService.submit(new ReadPaginationDataListThread(
						index, type, query, null, 100)));
			}
		}
		long totalRowNum = 0;
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (int i = 0, len = fs.size(); i < len; i++) {
			try {
				Future<QueryResult<Map<String, Object>>> future = fs.get(i);
				QueryResult<Map<String, Object>> result = future.get(20, TimeUnit.SECONDS);
				totalRowNum += result.getTotalRowNum();
				resultList.addAll(result.getResultList());
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			} 
		}
		resultList.sort(new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				Object scoreObj1 = o1.get("score");
				Double score1 = null == scoreObj1 ? 0 : Double.valueOf(String.valueOf(scoreObj1));
				Object scoreObj2 = o2.get("score");
				Double score2 = null == scoreObj2 ? 0 : Double.valueOf(String.valueOf(scoreObj2));
				return score2.compareTo(score1);
			}
		});
		QueryResult<Map<String, Object>> qr = new QueryResult<Map<String, Object>>();
		int from = StringUtils.isBlank(scrollId) ? 1 : Integer.parseInt(scrollId);
		qr.setScrollId(String.valueOf(from + 1));
		int fromIndex = (from - 1) * size;
		int toIndex = (fromIndex + size) > totalRowNum ? (int) totalRowNum : (fromIndex + size);
		LOG.info("fromIndex {} toIndex {}", fromIndex, toIndex);
		List<Map<String, Object>> sliceResultList = fromIndex <= toIndex ? 
				resultList.subList(fromIndex, toIndex) : new ArrayList<Map<String, Object>>();
		for (int i = 0, len = sliceResultList.size(); i < len; i++) {
			((Map<String, Object>) sliceResultList.get(i).get("data")).remove("score");
		}
		qr.setTotalRowNum(totalRowNum);
		qr.setResultList(sliceResultList);
		return qr;
	}
	
	@SuppressWarnings("unchecked")
	private void wrapperQQQunInformation(String type, List<Map<String, Object>> resultList) {
		if (!"qqqunrelation".equalsIgnoreCase(type)) return;
		List<String> qunNumList = new ArrayList<String>();
		for (int i = 0, len = resultList.size(); i < len; i++) {
			Map<String, Object> result = resultList.get(i);
			qunNumList.add(String.valueOf(((Map<String, Object>) result.get("data")).get("QQ群号")));
		}
		BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
		for (int i = 0, len = qunNumList.size(); i < len; i++) {
			queryBuilder.should(QueryBuilders.termQuery("qunNum", qunNumList.get(i)));
		}
		List<Map<String, Object>> qunDataList = readDataListByCondition("qq", "qqqundata", queryBuilder);
		Map<String, Map<String, Object>> qunMappings = new HashMap<String, Map<String, Object>>();
		for (int i = 0, len = qunDataList.size(); i < len; i++) {
			Map<String, Object> qunData = qunDataList.get(i);
			qunMappings.put(String.valueOf(qunData.get("QQ群号")), qunData);
		}
		for (int i = 0, len = resultList.size(); i < len; i++) {
			Map<String, Object> data = (Map<String, Object>) resultList.get(i).get("data");
			Object qqQunNum = data.get("QQ群号");
			if (null == qqQunNum) continue;
			Map<String, Object> qunData = qunMappings.get(String.valueOf(qqQunNum));
			if (null == qunData) continue;
			data.put("群人数", qunData.get("群人数"));
			data.put("创建时间", qunData.get("创建时间"));
		}
	}
	
	private String genLabelHitCacheKey(String query, String... types) {
		StringBuilder sb = new StringBuilder(100).append(query);
		for (int i = 0, len = types.length; i < len; i++) {
			sb.append(types[i]);
		}
		return "labels:hit:" + MD5Utils.hash(sb.toString());
	}
	
	private String genLabelDatasCacheKey(String index, String type, String query) {
		return index + ":" + type + ":" + MD5Utils.hash(query);
	}
	
	class ReadPaginationDataListThread implements Callable<QueryResult<Map<String, Object>>> {
		
		private String index = null;
		
		private String type = null;
		
		private String query = null;
		
		private String scrollId = null;
		
		private int size = 100;
		
		public ReadPaginationDataListThread(String index, String type, String query, 
				String scrollId, int size) {
			this.index = index;
			this.type = type;
			this.query = query;
			this.scrollId = scrollId;
			this.size = size;
		}

		@Override
		public QueryResult<Map<String, Object>> call() throws Exception {
			BoolQueryBuilder queryBuilder = buildBoolQuery(index, type, query);
			return null == queryBuilder ? new QueryResult<Map<String, Object>>() :
				readPaginationDataListByConditionWithScore(index, type, queryBuilder, scrollId, size);
		}
	}
	
	class ReadLabelsAndHitsThread implements Callable<Map<String, Object>> {
		
		private String index = null;
		
		private String type = null;
		
		private String query = null;
		
		public ReadLabelsAndHitsThread(String index, String type, String query) {
			this.index = index;
			this.type = type;
			this.query = query;
		}

		@Override
		public Map<String, Object> call() throws Exception {
			Map<String, Object> result = new HashMap<String, Object>();
			BoolQueryBuilder queryBuilder = buildBoolQuery(index, type, query);
			if(null == queryBuilder) return result;
			QueryResult<Map<String, Object>> qr = readPaginationDataListByCondition(index, type, 
					queryBuilder, SearchType.QUERY_AND_FETCH, null, 100, false);
			if (qr.getTotalRowNum() == 0) return result;
			result.put("hits", qr.getTotalRowNum());
			result.put("index", index);
			result.put("type", type);
			String label = MessageUtils.getInstance().getMessage(index + "." + type);
			result.put("label", label);
			qr.setResultList(new ArrayList<Map<String, Object>>(qr.getResultList()));
			RedisClusterUtils.getInstance().set(genLabelDatasCacheKey(index, type, query), qr, 120);
			return result;
		}
	}
	
}