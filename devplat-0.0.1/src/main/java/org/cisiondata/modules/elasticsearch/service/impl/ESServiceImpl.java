package org.cisiondata.modules.elasticsearch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.elasticsearch.client.ESClient;
import org.cisiondata.modules.elasticsearch.service.IESService;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.message.MessageUtils;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.carrotsearch.hppc.ObjectLookupContainer;
import com.carrotsearch.hppc.cursors.ObjectCursor;
import com.carrotsearch.hppc.cursors.ObjectObjectCursor;

@Service("esService")
public class ESServiceImpl implements IESService {
	
	protected Logger LOG = LoggerFactory.getLogger(getClass());

	protected static final int ES_10 = 1;
	protected static final int ES_105 = 2;
	
	protected static Set<String> indices_10 = null;
	protected static Set<String> indices_105 = null;
	protected static Set<String> types_10 = null;
	protected static Set<String> types_105 = null;
	protected static Set<String> name_attributes = null;
	protected static Set<String> phone_attributes = null;
	protected static Set<String> idcard_attributes = null;
	protected static Set<String> chinese_attributes = null;
	protected static Set<String> identity_attributes = null;
	protected static Set<String> location_attributes = null;
	protected static Set<String> all_attributes = null;
	protected static Map<String, List<String>> index_types_map = null;
	
	@PostConstruct
	public void postConstruct() {
		initAttributeCache();
		initESIndicesTypesCache();
		initES10IndicesTypesCache();
		initES105IndicesTypesCache();
	}
	
	private void initAttributeCache() {
		all_attributes = new HashSet<String>();
		name_attributes = new HashSet<String>();
		phone_attributes = new HashSet<String>();
		idcard_attributes = new HashSet<String>();
		chinese_attributes = new HashSet<String>();
		identity_attributes = new HashSet<String>();
		location_attributes = new HashSet<String>();
		index_types_map = new HashMap<String, List<String>>();
	}
	
	private void filterAttributeCache(String attribute) {
		String attributeLowerCase = attribute.toLowerCase();
		if (attributeLowerCase.indexOf("name") != -1) {
			name_attributes.add(attribute);
			identity_attributes.add(attribute);
		} else if (attributeLowerCase.indexOf("call") != -1) {
			phone_attributes.add(attribute);
			identity_attributes.add(attribute);
		} else if (attributeLowerCase.indexOf("phone") != -1) {
			phone_attributes.add(attribute);
			identity_attributes.add(attribute);
		} else if (attributeLowerCase.indexOf("email") != -1) {
			identity_attributes.add(attribute);
		} else if (attributeLowerCase.indexOf("idcard") != -1) {
			idcard_attributes.add(attribute);
			identity_attributes.add(attribute);
		} else if (attributeLowerCase.indexOf("qq") != -1) {
			identity_attributes.add(attribute);
		} else if (attributeLowerCase.indexOf("account") != -1) {
			identity_attributes.add(attribute);
		} else if (attributeLowerCase.indexOf("password") != -1) {
			identity_attributes.add(attribute);
		} else if (attributeLowerCase.indexOf("address") != -1) {
			location_attributes.add(attribute);
		} else if (attributeLowerCase.indexOf("company") != -1) {
			location_attributes.add(attribute);
		} else if (attributeLowerCase.indexOf("province") != -1) {
			location_attributes.add(attribute);
		} else if (attributeLowerCase.indexOf("city") != -1) {
			location_attributes.add(attribute);
		}
		chinese_attributes.addAll(name_attributes);
		chinese_attributes.addAll(location_attributes);
		all_attributes.addAll(identity_attributes);
		all_attributes.addAll(location_attributes);
	}
	
	private void initES10IndicesTypesCache() {
		indices_10 = new HashSet<String>();
		types_10 = new HashSet<String>();
		initESIndicesTypesCache(ES_10, indices_10, types_10);
	}
	
	private void initES105IndicesTypesCache() {
		indices_105 = new HashSet<String>();
		types_105 = new HashSet<String>();
		initESIndicesTypesCache(ES_105, indices_105, types_105);
	}
	
	private void initESIndicesTypesCache() {
		try {
			IndicesAdminClient indicesAdminClient = buildClient(ES_10).admin().indices();
			GetMappingsResponse getMappingsResponse = indicesAdminClient.getMappings(new GetMappingsRequest()).get();
			ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> mappings = 
					getMappingsResponse.getMappings();
			Iterator<ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>>> 
			mappingIterator = mappings.iterator();
			while (mappingIterator.hasNext()) {
				ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>>
				objectObjectCursor = mappingIterator.next();
				String index = objectObjectCursor.key;
				List<String> types = index_types_map.get(index);
				if (null == types) {
					types = new ArrayList<String>();
					index_types_map.put(index, types);
				}
				ImmutableOpenMap<String, MappingMetaData> immutableOpenMap = objectObjectCursor.value;
				ObjectLookupContainer<String> keys = immutableOpenMap.keys();
				Iterator<ObjectCursor<String>> keysIterator = keys.iterator();
				while(keysIterator.hasNext()) {
					types.add(keysIterator.next().value);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} 
	}
	
	@SuppressWarnings("unchecked")
	private void initESIndicesTypesCache(int es, Set<String> indexs_cache, Set<String> types_cache) {
		try {
			IndicesAdminClient indicesAdminClient = buildClient(es).admin().indices();
			GetMappingsResponse getMappingsResponse = indicesAdminClient.getMappings(new GetMappingsRequest()).get();
			ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> mappings = 
					getMappingsResponse.getMappings();
			Iterator<ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>>> 
			mappingIterator = mappings.iterator();
			while (mappingIterator.hasNext()) {
				ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>>
				objectObjectCursor = mappingIterator.next();
				indexs_cache.add(objectObjectCursor.key);
				ImmutableOpenMap<String, MappingMetaData> immutableOpenMap = objectObjectCursor.value;
				ObjectLookupContainer<String> keys = immutableOpenMap.keys();
				Iterator<ObjectCursor<String>> keysIterator = keys.iterator();
				while(keysIterator.hasNext()) {
					String type = keysIterator.next().value;
					types_cache.add(type);
					MappingMetaData mappingMetaData = immutableOpenMap.get(type);
					Map<String, Object> mapping = mappingMetaData.getSourceAsMap();
					if (mapping.containsKey("properties")) {
						Map<String, Object> properties = (Map<String, Object>) mapping.get("properties");
						for (String attribute : properties.keySet()) {
							filterAttributeCache(attribute);
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} 
	}
	
	private ExecutorService executorService = Executors.newCachedThreadPool();
	
	@Override
	public List<Map<String, Object>> readDataListByCondition(QueryBuilder query) throws BusinessException {
		return readDataListByCondition(null, new String[0], query);
	}

	@Override
	public List<Map<String, Object>> readDataListByCondition(final String index, final String type, 
			final QueryBuilder query) throws BusinessException {
		return readDataListByCondition(null, new String[]{type}, query);
	}
	
	@Override
	public List<Map<String, Object>> readDataListByCondition(final String index, final String[] types, 
			final QueryBuilder query) throws BusinessException {
		List<Future<List<Map<String, Object>>>> fs = new ArrayList<Future<List<Map<String, Object>>>>();
		for (int i = ES_10; i <= ES_105; i++) {
			fs.add(executorService.submit(new ReadDataListTask(i, index, types, query)));
		}
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		try {
			for (int i = 0, len = fs.size(); i < len; i++) {
				Future<List<Map<String, Object>>> f = fs.get(i);
				while (!f.isDone()) {}
				resultList.addAll(f.get());
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return resultList;
	}
	
	@Override
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(final QueryBuilder query, 
			String scrollId, int size) throws BusinessException {
		return readPaginationDataListByCondition(null, new String[0], query, scrollId, size);
	}
	
	@Override
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(final String index, final String type, 
			QueryBuilder query, String scrollId, int size) throws BusinessException {
		return readPaginationDataListByCondition(null, new String[]{type}, query, scrollId, size);
	}
	
	@Override
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(final String index, final String[] types, 
			final QueryBuilder query, String scrollId, int size) throws BusinessException {
		List<Future<QueryResult<Map<String, Object>>>> fs = new ArrayList<Future<QueryResult<Map<String, Object>>>>();
		for (int i = ES_10; i <= ES_105; i++) {
			fs.add(executorService.submit(new ReadPaginationDataListTask(i, index, types, query, null, 50)));
		}
		
		long totalRowNum = 0;
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		try {
			for (int i = 0, len = fs.size(); i < len; i++) {
				Future<QueryResult<Map<String, Object>>> f = fs.get(i);
				while (!f.isDone()){}
				QueryResult<Map<String, Object>> qr = f.get();
				totalRowNum = totalRowNum + qr.getTotalRowNum();
				resultList.addAll(qr.getResultList());
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		QueryResult<Map<String, Object>> qr = new QueryResult<Map<String,Object>>();
		qr.setTotalRowNum(totalRowNum);
		int from = StringUtils.isBlank(scrollId) ? 1 : Integer.parseInt(scrollId);
		qr.setScrollId(String.valueOf(from + 1));
		int fromIndex = (from - 1) * size;
		int targetRowNum = totalRowNum > resultList.size() ? resultList.size() : (int) totalRowNum;
		int toIndex = (fromIndex + size) > targetRowNum ? (int) targetRowNum : (fromIndex + size);
		LOG.info("totalRowNum:{} fromIndex:{} toIndex:{} targetRowNum:{}", totalRowNum, fromIndex, toIndex, targetRowNum);
		if (fromIndex <= toIndex) {
			qr.setResultList(resultList.subList(fromIndex, toIndex));
		}
		return qr;
	}
	
	private Client buildClient(int es) {
		Client client = null;
		switch (es) {
			case ES_10:
				client = ESClient.getInstance().getClient10();
				break;
			case ES_105:	
				client = ESClient.getInstance().getClient105();
				break;
			default:
				break;
		}
		return client;
	}
	
	private String[] buildIndices(Set<String> defaultIndices, String index) {
		return (StringUtils.isBlank(index)) ? defaultIndices.toArray(new String[0]) : new String[]{index};
	}
	
	private String[] buildTypes(Set<String> defaultTypes, String[] types) {
		return (null == types || types.length == 0) ? defaultTypes.toArray(new String[0]) : types;
	}
	
	private SearchRequestBuilder buildSearchRequestBuilder(int es, String index, String[] types) {
		Client targetClient = null;
		String[] targetIndices = null;
		String[] targetTypes = null;
		switch (es) {
			case ES_10:
				targetClient = ESClient.getInstance().getClient10();
				targetIndices = buildIndices(indices_10, index);
				targetTypes = buildTypes(types_10, types);
				break;
			case ES_105:	
				targetClient = ESClient.getInstance().getClient105();
				targetIndices = buildIndices(indices_105, index);
				targetTypes = buildTypes(types_105, types);
				break;
			default:
				break;
		}
		return targetClient.prepareSearch(targetIndices).setTypes(targetTypes);
	}
	
	private void wrapperSearchRequestBuilder(SearchRequestBuilder searchRequestBuilder) {
		searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		searchRequestBuilder.setExplain(false);
        searchRequestBuilder.setHighlighterPreTags("<span style=\"color:red\">");
        searchRequestBuilder.setHighlighterPostTags("</span>");
        for (String attri : all_attributes) {
        	searchRequestBuilder.addHighlightedField(attri);
        }
	}
	
	private boolean indicesExists(int es, String index) {
		if (StringUtils.isBlank(index)) return true;
		IndicesExistsRequest indicesExistsRequest = new IndicesExistsRequest(index);
		IndicesExistsResponse indicesExistsResponse = buildClient(es).admin().indices()
				.exists(indicesExistsRequest).actionGet();
		return indicesExistsResponse.isExists();
	}
	
	private List<Map<String, Object>> readIndexsTypesDatas(int es, String index, 
			String[] types, QueryBuilder query) throws BusinessException {
		if (!indicesExists(es, index)) return new ArrayList<Map<String,Object>>();
		SearchRequestBuilder searchRequestBuilder = buildSearchRequestBuilder(es, index, types);
		searchRequestBuilder.setQuery(query);
		wrapperSearchRequestBuilder(searchRequestBuilder);
		searchRequestBuilder.setSize(100);
		SearchResponse response = searchRequestBuilder.execute().actionGet();
		SearchHit[] hitArray = response.getHits().getHits();
		return buildResultList(hitArray, false);
	}
	
	private QueryResult<Map<String, Object>> readIndexsTypesDatas(int es, String index, String[] types,
			QueryBuilder query, String scrollId, int size) throws BusinessException {
		if (!indicesExists(es, index)) return new QueryResult<Map<String,Object>>();
		SearchRequestBuilder searchRequestBuilder = buildSearchRequestBuilder(es, index, types);
		searchRequestBuilder.setQuery(query);
		wrapperSearchRequestBuilder(searchRequestBuilder);
        searchRequestBuilder.setScroll(TimeValue.timeValueMinutes(3));
        searchRequestBuilder.setSize(size);
		SearchResponse response = searchRequestBuilder.execute().actionGet();
		if (StringUtils.isNotBlank(scrollId)) {
			response = buildClient(es).prepareSearchScroll(scrollId).setScroll(
					TimeValue.timeValueMinutes(3)).execute().actionGet();
		}
		SearchHit[] hitArray = response.getHits().getHits();
		QueryResult<Map<String, Object>> qr = new QueryResult<Map<String,Object>>();
		qr.setTotalRowNum(response.getHits().getTotalHits());
		qr.setScrollId(response.getScrollId());
		qr.setResultList(buildResultList(hitArray, true));
		return qr;
	}
	
	private List<Map<String, Object>> buildResultList(SearchHit[] hitArray, boolean isPagination) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		SearchHit hit = null;
		StringBuilder prefix = null;
		StringBuilder en = null;
		String key = null;
		Object value = null;
		HighlightField highLightField = null;
		Map<String, Object> source = null;
		Map<String, Object> replaceSource = null;
		Map<String, HighlightField> highLightFields = null;
		for (int i = 0, len = hitArray.length; i < len; i++) {
			hit = hitArray[i];
			highLightFields = hit.getHighlightFields();
			source = hit.getSource();
			replaceSource = new HashMap<String, Object>();
			prefix = new StringBuilder();
			prefix.append(hit.getIndex());
			prefix.append(".").append(hit.getType());
			for (Map.Entry<String, Object> entry : source.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				highLightField = highLightFields.get(key);
				if (null != highLightField) {
					Text[] texts = highLightField.getFragments();
					StringBuilder content = new StringBuilder();
					for (int t = 0, tlen = texts.length; t < tlen; t++) {
						content.append(texts[t]);
					}
					if (content.length() > 0) value = content.toString();
				}
				en = new StringBuilder(prefix.toString()).append(".").append(key);
				String ch = MessageUtils.getInstance().getMessage(en.toString());
				replaceSource.put(StringUtils.isBlank(ch) ? key : ch, value);
			}
			if (isPagination) {
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("index", MessageUtils.getInstance().getMessage(prefix.toString()));
				result.put("type", MessageUtils.getInstance().getMessage(prefix.toString()));
				result.put("data", replaceSource);
				resultList.add(result);
			} else {
				replaceSource.put("index", hit.getIndex());
				replaceSource.put("type", hit.getType());
				resultList.add(replaceSource);
			}
		}
		return resultList;
	}
	
	class ReadDataListTask implements Callable<List<Map<String, Object>>> {
		
		private int es = 0;
		
		private String index = null;
		
		private String[] types = null;
		
		private QueryBuilder query = null;
		
		public ReadDataListTask(int es, String index, String[] types, QueryBuilder query) {
			this.es = es;
			this.index = index;
			this.types = types;
			this.query = query;
		}
		
		@Override
		public List<Map<String, Object>> call() throws Exception {
			return readIndexsTypesDatas(es, index, types, query);
		}
		
	}
	
	class ReadPaginationDataListTask implements Callable<QueryResult<Map<String, Object>>> {
		
		private int es = 0;
		
		private String index = null;
		
		private String[] types = null;
		
		private QueryBuilder query = null;
		
		private String scrollId = null;
		
		private int size = 50;
		
		public ReadPaginationDataListTask(int es, String index, String[] types, 
				QueryBuilder query, String scrollId, int size) {
			this.es = es;
			this.index = index;
			this.types = types;
			this.query = query;
			this.scrollId = scrollId;
			this.size = size;
		}
		
		@Override
		public QueryResult<Map<String, Object>> call() throws Exception {
			return readIndexsTypesDatas(es, index, types, query, scrollId, size);
		}
		
	}

}


