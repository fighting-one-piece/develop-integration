package org.cisiondata.modules.elasticsearch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
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

	/** ES INDEX */
	protected static Set<String> indices = null;
	/** ES TYPE */
	protected static Set<String> types = null;
	
	/** ES 搜索属性字段*/
	protected static Set<String> name_attributes = null;
	protected static Set<String> phone_attributes = null;
	protected static Set<String> idcard_attributes = null;
	protected static Set<String> chinese_attributes = null;
	protected static Set<String> identity_attributes = null;
	protected static Set<String> location_attributes = null;
	protected static Set<String> all_attributes = null;
	
	/** ES INDEX TYPE 映射关系*/
	protected static Map<String, List<String>> index_types_map = null;
	
	/** 属性字段过滤*/
	protected static Set<String> filter_attributes = null;
	
	@PostConstruct
	public void postConstruct() {
		initAttributeCache();
		initESIndicesTypesCache();
		initES10IndicesTypesCache();
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
		filter_attributes = new HashSet<String>();
		filter_attributes.add("insertTime");
		filter_attributes.add("updateTime");
		filter_attributes.add("sourceFile");
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
	
	@SuppressWarnings("unchecked")
	private void initES10IndicesTypesCache() {
		indices = new HashSet<String>();
		types = new HashSet<String>();
		try {
			IndicesAdminClient indicesAdminClient = ESClient.getInstance().getClient().admin().indices();
			GetMappingsResponse getMappingsResponse = indicesAdminClient.getMappings(new GetMappingsRequest()).get();
			ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> mappings = 
					getMappingsResponse.getMappings();
			Iterator<ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>>> 
			mappingIterator = mappings.iterator();
			while (mappingIterator.hasNext()) {
				ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>>
				objectObjectCursor = mappingIterator.next();
				if (objectObjectCursor.key.startsWith(".marvel-es")) continue;
				indices.add(objectObjectCursor.key);
				ImmutableOpenMap<String, MappingMetaData> immutableOpenMap = objectObjectCursor.value;
				ObjectLookupContainer<String> keys = immutableOpenMap.keys();
				Iterator<ObjectCursor<String>> keysIterator = keys.iterator();
				while(keysIterator.hasNext()) {
					String type = keysIterator.next().value;
					types.add(type);
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
	
	private void initESIndicesTypesCache() {
		try {
			IndicesAdminClient indicesAdminClient = ESClient.getInstance().getClient().admin().indices();
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
	
	@Override
	public List<Map<String, Object>> readDataListByCondition(QueryBuilder query) throws BusinessException {
		return readDataListByCondition(null, new String[0], query);
	}
	
	@Override
	public List<Map<String, Object>> readDataListByCondition(QueryBuilder query, int size) 
			throws BusinessException {
		return readDataListByCondition(null, new String[0], query, size);
	}

	@Override
	public List<Map<String, Object>> readDataListByCondition(String index, String type, 
			QueryBuilder query) throws BusinessException {
		return readDataListByCondition(index, new String[]{type}, query);
	}
	
	@Override
	public List<Map<String, Object>> readDataListByCondition(String index, String[] types, 
			QueryBuilder query) throws BusinessException {
		return readDataListByCondition(index, types, query, 200);
	}
	
	@Override
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(QueryBuilder query, 
			String scrollId, int size) throws BusinessException {
		return readPaginationDataListByCondition(null, new String[0], query, scrollId, size);
	}
	
	@Override
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, String type, 
			QueryBuilder query, String scrollId, int size) throws BusinessException {
		return readPaginationDataListByCondition(index, new String[]{type}, query, scrollId, size);
	}
	
	@Override
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, String[] types, 
			QueryBuilder query, String scrollId, int size) throws BusinessException {
		return readIndexsTypesPaginationDatas(index, types, query, scrollId, size);
	}
	
	private String[] buildIndices(Set<String> defaultIndices, String index) {
		return (StringUtils.isBlank(index)) ? defaultIndices.toArray(new String[0]) : new String[]{index};
	}
	
	private String[] buildTypes(Set<String> defaultTypes, String[] types) {
		return (null == types || types.length == 0) ? defaultTypes.toArray(new String[0]) : types;
	}
	
	private SearchRequestBuilder buildSearchRequestBuilder(String index, String[] ctypes) {
		Client targetClient = ESClient.getInstance().getClient();
		String[] targetIndices = buildIndices(indices, index);
		String[] targetTypes = buildTypes(types, ctypes);
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
	
	private boolean indicesExists(String index) {
		if (StringUtils.isBlank(index)) return true;
		IndicesExistsRequest indicesExistsRequest = new IndicesExistsRequest(index);
		IndicesExistsResponse indicesExistsResponse = ESClient.getInstance().getClient()
				.admin().indices().exists(indicesExistsRequest).actionGet();
		return indicesExistsResponse.isExists();
	}
	
	private List<Map<String, Object>> readDataListByCondition(String index, String[] types, 
			QueryBuilder query, int size) throws BusinessException {
		if (!indicesExists(index)) return new ArrayList<Map<String,Object>>();
		SearchRequestBuilder searchRequestBuilder = buildSearchRequestBuilder(index, types);
		searchRequestBuilder.setQuery(query);
		wrapperSearchRequestBuilder(searchRequestBuilder);
		searchRequestBuilder.setSize(size);
		SearchResponse response = searchRequestBuilder.execute().actionGet();
		SearchHit[] hitArray = response.getHits().getHits();
		return buildResultList(hitArray, false);
	}
	
	private QueryResult<Map<String, Object>> readIndexsTypesPaginationDatas(String index, String[] types,
			QueryBuilder query, String scrollId, int size) throws BusinessException {
		if (!indicesExists(index)) return new QueryResult<Map<String,Object>>();
		SearchRequestBuilder searchRequestBuilder = buildSearchRequestBuilder(index, types);
		searchRequestBuilder.setQuery(query);
		wrapperSearchRequestBuilder(searchRequestBuilder);
        searchRequestBuilder.setScroll(TimeValue.timeValueMinutes(3));
        searchRequestBuilder.setSize(size);
		SearchResponse response = searchRequestBuilder.execute().actionGet();
		if (StringUtils.isNotBlank(scrollId)) {
			response = ESClient.getInstance().getClient().prepareSearchScroll(scrollId)
					.setScroll(TimeValue.timeValueMinutes(3)).execute().actionGet();
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
		String key = null;
		Object value = null;
		Map<String, Object> source = null;
		Map<String, Object> replaceSource = null;
		Map<String, HighlightField> highLightFields = null;
		for (int i = 0, len = hitArray.length; i < len; i++) {
			hit = hitArray[i];
			highLightFields = hit.getHighlightFields();
			source = hit.getSource();
			replaceSource = new HashMap<String, Object>();
			String prefix = hit.getIndex() + "." + hit.getType();
			for (Map.Entry<String, Object> entry : source.entrySet()) {
				key = entry.getKey();
				if (filter_attributes.contains(key)) continue;
				value = entry.getValue();
				if (highLightFields.containsKey(key)) {
					Text[] texts = highLightFields.get(key).getFragments();
					StringBuilder highLightText = new StringBuilder();
					for (int t = 0, tlen = texts.length; t < tlen; t++) {
						highLightText.append(texts[t]);
					}
					if (highLightText.length() > 0) value = highLightText.toString();
				}
				String account = (String) SecurityUtils.getSubject().getPrincipal();
				value = key.toLowerCase().indexOf("password") != -1 
						&& !"liqien".equals(account) ? "********" : value;
				String chValue = MessageUtils.getInstance().getMessage(prefix + "." + key);
				replaceSource.put(StringUtils.isBlank(chValue) ? key : chValue, value);
			}
			if (isPagination) {
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("index", MessageUtils.getInstance().getMessage(prefix));
				result.put("type", MessageUtils.getInstance().getMessage(prefix));
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
		
		private String index = null;
		
		private String[] types = null;
		
		private QueryBuilder query = null;
		
		public ReadDataListTask(String index, String[] types, QueryBuilder query) {
			this.index = index;
			this.types = types;
			this.query = query;
		}
		
		@Override
		public List<Map<String, Object>> call() throws Exception {
			return readDataListByCondition(index, types, query);
		}
		
	}
	
	class ReadPaginationDataListTask implements Callable<QueryResult<Map<String, Object>>> {
		
		private String index = null;
		
		private String[] types = null;
		
		private QueryBuilder query = null;
		
		private String scrollId = null;
		
		private int size = 50;
		
		public ReadPaginationDataListTask(String index, String[] types, 
				QueryBuilder query, String scrollId, int size) {
			this.index = index;
			this.types = types;
			this.query = query;
			this.scrollId = scrollId;
			this.size = size;
		}
		
		@Override
		public QueryResult<Map<String, Object>> call() throws Exception {
			return readIndexsTypesPaginationDatas(index, types, query, scrollId, size);
		}
		
	}

}


