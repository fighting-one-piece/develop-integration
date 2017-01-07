package org.cisiondata.modules.elasticsearch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.elasticsearch.service.IESBizService;
import org.cisiondata.utils.encryption.MD5Utils;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.message.MessageUtils;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

@Service("esBizService")
public class ESBizServiceImpl extends ESServiceImpl implements IESBizService {
	
	private static final String CN_REG = "[\\u4e00-\\u9fa5]+";
	
	@Override
	public List<Map<String, Object>> readDataListByCondition(String query, int size) 
			throws BusinessException {
		return readDataListByCondition(buildBoolQuery(query), size);
	}
	
	@Override
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(
			String query, String scrollId, int size) throws BusinessException {
		return readPaginationDataListByCondition(buildBoolQuery(query), scrollId, size);
	}

	@Override
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, String type, 
			String query, String scrollId, int size) throws BusinessException {
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		for (String attribute : identity_attributes) {
			queryBuilder.should(QueryBuilders.termQuery(attribute, query));
		}
		return readPaginationDataListByCondition(index, type, queryBuilder, scrollId, size);
	}
	
	private BoolQueryBuilder buildBoolQuery(String keyword) {
		if (keyword.trim().indexOf(" ") == -1) return buildSBoolQuery(keyword);
		return buildMBoolQuery(keyword);
	}
	
	private BoolQueryBuilder buildMBoolQuery(String keyword) {
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		String[] keywords = keyword.trim().split(" ");
		for (int i = 0, len = keywords.length; i < len; i++) {
			boolQueryBuilder.must(buildSBoolQuery(keywords[i]));
		}
		return boolQueryBuilder;
	}
	
	private BoolQueryBuilder buildSBoolQuery(String keyword) {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		Pattern pattern = Pattern.compile(CN_REG);
		boolean isChinese = pattern.matcher(keyword).find();
		if (isChinese) {
			for (String attribute : chinese_attributes) {
				boolQueryBuilder.should(QueryBuilders.matchPhraseQuery(attribute, keyword));
			}
		} else {
			for (String attribute : identity_attributes) {
				boolQueryBuilder.should(QueryBuilders.termQuery(attribute, keyword));
			}
		}
		return boolQueryBuilder;
	}

	@Override
	public QueryResult<Map<String, Object>> readPaginationDataListByIndexType(Map<String, String> map)
			throws BusinessException {
		String scrollId = map.remove("scrollId");
		Integer size = Integer.parseInt(map.remove("pageSize"));
		String index = map.remove("esindex");
		String type = map.remove("estype");
		
		BoolQueryBuilder qb = QueryBuilders.boolQuery();
		Pattern pattern = Pattern.compile(CN_REG);
		for (Map.Entry<String, String> entry : map.entrySet()){
			if(entry.getValue()==""||entry.getValue()==null) continue;
			boolean isChinese = pattern.matcher(entry.getValue()).find();
			if (isChinese) {
				qb.must(QueryBuilders.matchPhraseQuery(entry.getKey(), entry.getValue()));
			} else{
				qb.must(QueryBuilders.termQuery(entry.getKey(), entry.getValue()));
			}
		}
		return readPaginationDataListByCondition(index, type, qb, scrollId, size);
	}
	
	private ExecutorService executorService = Executors.newCachedThreadPool();

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> readLabelsAndHitsByCondition(String query) throws BusinessException {
		String labelHitCacheKey = genLabelHitCacheKey(query);
		Object cacheObject = RedisClusterUtils.getInstance().get(labelHitCacheKey);
		if (null != cacheObject) {
			return (List<Map<String, Object>>) cacheObject;
		}
		LOG.info("read labels and hits method not hit");
		Map<String, Future<QueryResult<Map<String, Object>>>> fs = 
				new HashMap<String, Future<QueryResult<Map<String, Object>>>>();
		BoolQueryBuilder queryBuilder = buildBoolQuery(query);
		for (Map.Entry<String, List<String>> index_types : index_types_map.entrySet()) {
			String index = index_types.getKey();
			List<String> types = index_types.getValue();
			for (int i = 0, len = types.size(); i < len; i++) {
				String type = types.get(i);
				fs.put(index + ":" + type, executorService.submit(
						new ReadPaginationDataListTask(index, type, queryBuilder)));
			}
		}
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		try {
			for (Map.Entry<String, Future<QueryResult<Map<String, Object>>>> entry : fs.entrySet()) {
				Future<QueryResult<Map<String, Object>>> f = entry.getValue();
				while (!f.isDone()) {}
				QueryResult<Map<String, Object>> qr = f.get();
				if (qr.getTotalRowNum() == 0) continue;
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("hits", qr.getTotalRowNum());
				String index_type = entry.getKey();
				String[] index_type_array = index_type.split(":");
				result.put("index", index_type_array[0]);
				result.put("type", index_type_array[1]);
				String label = MessageUtils.getInstance().getMessage(index_type.replace(":", "."));
				result.put("label", label);
				resultList.add(result);
				qr.setResultList(new ArrayList<Map<String, Object>>(qr.getResultList()));
				RedisClusterUtils.getInstance().set(genLabelDatasCacheKey(index_type_array[0], 
						index_type_array[1],label, query), qr, 120);
			}
			RedisClusterUtils.getInstance().set(labelHitCacheKey, resultList, 60);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} 
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<Map<String, Object>> readLabelPaginationDataList(String index, String type, String query,
			String scrollId, int size) throws BusinessException {
		String label = MessageUtils.getInstance().getMessage(index + "." + type);
		String labelDatasCacheKey = genLabelDatasCacheKey(index, type, label, query);
		Object cacheObject = RedisClusterUtils.getInstance().get(labelDatasCacheKey);
		QueryResult<Map<String, Object>> qr = new QueryResult<Map<String,Object>>();
		if (null != cacheObject) {
			qr = (QueryResult<Map<String, Object>>) cacheObject;
		} else {
			qr = readPaginationDataListByCondition(index, type, query, null, 100);
			qr.setResultList(new ArrayList<Map<String, Object>>(qr.getResultList()));
			RedisClusterUtils.getInstance().set(labelDatasCacheKey, qr, 120);
		}
		long totalRowNum = qr.getTotalRowNum();
		int from = StringUtils.isBlank(scrollId) ? 1 : Integer.parseInt(scrollId);
		qr.setScrollId(String.valueOf(from + 1));
		int fromIndex = (from - 1) * size;
		int toIndex = (fromIndex + size) > totalRowNum ? (int) totalRowNum : (fromIndex + size);
		LOG.info("fromIndex {} toIndex {}", fromIndex, toIndex);
		if (fromIndex <= toIndex) {
			qr.setResultList(qr.getResultList().subList(fromIndex, toIndex));
		} else {
			qr.getResultList().clear();
		}
		return qr;
	}
	
	private String genLabelHitCacheKey(String query) {
		return "label:hit:" + MD5Utils.hash(query);
	}
	
	private String genLabelDatasCacheKey(String index, String type, String label, String query) {
		return index + ":" + type + ":" + label + ":" + MD5Utils.hash(query);
	}
	
	@Override
	public List<Map<String, Object>> readLogisticsDataList(String query) throws BusinessException {
		return readDataListByCondition("financial", "logistics", buildBoolQuery(query));
	}
	
	class ReadPaginationDataListTask implements Callable<QueryResult<Map<String, Object>>> {
		
		private String index = null;
		
		private String type = null;
		
		private QueryBuilder query = null;
		
		public ReadPaginationDataListTask(String index, String type, QueryBuilder query) {
			this.index = index;
			this.type = type;
			this.query = query;
		}

		@Override
		public QueryResult<Map<String, Object>> call() throws Exception {
			return readPaginationDataListByCondition(index, type, query, null, 100);
		}
		
	}
	
}
