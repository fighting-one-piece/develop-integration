package org.cisiondata.modules.elasticsearch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.elasticsearch.action.search.MultiSearchRequestBuilder;
import org.elasticsearch.action.search.MultiSearchResponse.Item;
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
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
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
	
	/** ES TYPE INDEX 映射关系*/
	protected static Map<String, String> type_index_mapping = null;
	
	/** ES INDEX TYPE 映射关系*/
	protected static Map<String, List<String>> index_types_mapping = null;
	
	/** ES INDEX TYPE ATTRIBUTES 映射关系*/
	protected static Map<String, Map<String, Set<String>>> index_type_attributes_mapping = null;
	
	/** 属性字段过滤*/
	protected static Set<String> filter_attributes = null;
	/** 标识属性字段过滤*/
	protected static Set<String> identity_filter_attributes = null;
	
	@PostConstruct
	public void postConstruct() {
		initAttributeCache();
		initESIndicesTypesAttributesCache();
	}
	
	protected String[] defaultIndices() {
		return indices.toArray(new String[0]);
	}
	
	protected String[] defaultTypes() {
		return types.toArray(new String[0]);
	}
	
	private void initAttributeCache() {
		all_attributes = new HashSet<String>();
		name_attributes = new HashSet<String>();
		phone_attributes = new HashSet<String>();
		idcard_attributes = new HashSet<String>();
		chinese_attributes = new HashSet<String>();
		identity_attributes = new HashSet<String>();
		location_attributes = new HashSet<String>();
		type_index_mapping = new HashMap<String, String>();
		index_types_mapping = new HashMap<String, List<String>>();
		index_type_attributes_mapping = new HashMap<String, Map<String, Set<String>>>();
		filter_attributes = new HashSet<String>();
		filter_attributes.add("site");
		filter_attributes.add("cnote");
		filter_attributes.add("insertTime");
		filter_attributes.add("updateTime");
		filter_attributes.add("inputPerson");
		identity_filter_attributes = new HashSet<String>();
		identity_filter_attributes.add("phoneAssociation");
		identity_filter_attributes.add("phonePackage");
		identity_filter_attributes.add("phoneModel");
		identity_filter_attributes.add("phoneState");
		identity_filter_attributes.add("phonePrice");
		identity_filter_attributes.add("phoneType");
		identity_filter_attributes.add("qqPoint");
		identity_filter_attributes.add("qqCoin");
		identity_filter_attributes.add("mastQQ");
		identity_filter_attributes.add("cityId");
		identity_filter_attributes.add("carName");
		identity_filter_attributes.add("cardName");
		identity_filter_attributes.add("bankName");
		identity_filter_attributes.add("payName");
		identity_filter_attributes.add("provinceId");
		identity_filter_attributes.add("ipAddress");
		identity_filter_attributes.add("companyNo");
		identity_filter_attributes.add("companyCode");
		identity_filter_attributes.add("companyType");
		identity_filter_attributes.add("companyNature");
		identity_filter_attributes.add("companyZipcode");
		identity_filter_attributes.add("emailSuffix");
		identity_filter_attributes.add("accountType");
		identity_filter_attributes.add("gameAreaName");
		identity_filter_attributes.add("manufactoryName");
		identity_filter_attributes.add("registionAccount");
	}
	
	private void filterAttributeCache(String attribute, Map<String, Set<String>> indexTypeAttributes) {
		String attributeLowerCase = attribute.toLowerCase();
		if (identity_filter_attributes.contains(attribute)) return;
		if (attributeLowerCase.indexOf("name") != -1 && 
				attributeLowerCase.indexOf("alias") == -1) {
			name_attributes.add(attribute);
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
		} else if (attributeLowerCase.indexOf("company") != -1 && 
				attributeLowerCase.indexOf("alias") == -1) {
			location_attributes.add(attribute);
		} else if (attributeLowerCase.indexOf("province") != -1) {
			location_attributes.add(attribute);
		} else if (attributeLowerCase.indexOf("city") != -1) {
			location_attributes.add(attribute);
		} else if (attributeLowerCase.indexOf("county") != -1) {
			location_attributes.add(attribute);
		}
		chinese_attributes.addAll(name_attributes);
		chinese_attributes.addAll(location_attributes);
		all_attributes.addAll(name_attributes);
		all_attributes.addAll(identity_attributes);
		all_attributes.addAll(location_attributes);
		Set<String> it_identity_attributes = indexTypeAttributes.get("identity_attributes");
		if (null == it_identity_attributes) {
			it_identity_attributes = new HashSet<String>();
			indexTypeAttributes.put("identity_attributes", it_identity_attributes);
		}
		if (identity_attributes.contains(attribute)) it_identity_attributes.add(attribute);
		Set<String> it_chinese_attributes = indexTypeAttributes.get("chinese_attributes");
		if (null == it_chinese_attributes) {
			it_chinese_attributes = new HashSet<String>();
			indexTypeAttributes.put("chinese_attributes", it_chinese_attributes);
		}
		if (chinese_attributes.contains(attribute)) it_chinese_attributes.add(attribute);
	}
	
	@SuppressWarnings("unchecked")
	private void initESIndicesTypesAttributesCache() {
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
				String index = objectObjectCursor.key;
				if (index.startsWith(".marvel-es")) continue;
				indices.add(index);
				List<String> indexTypes = index_types_mapping.get(index);
				if (null == indexTypes) {
					indexTypes = new ArrayList<String>();
					index_types_mapping.put(index, indexTypes);
				}
				ImmutableOpenMap<String, MappingMetaData> immutableOpenMap = objectObjectCursor.value;
				ObjectLookupContainer<String> keys = immutableOpenMap.keys();
				Iterator<ObjectCursor<String>> keysIterator = keys.iterator();
				while(keysIterator.hasNext()) {
					String type = keysIterator.next().value;
					types.add(type);
					indexTypes.add(type);
					type_index_mapping.put(type, index);
					String indexType = index + type;
					Map<String, Set<String>> index_type_attributes = index_type_attributes_mapping.get(indexType);
					if (null == index_type_attributes_mapping.get(indexType)) {
						index_type_attributes = new HashMap<String, Set<String>>();
						index_type_attributes_mapping.put(indexType, index_type_attributes);
					}
					MappingMetaData mappingMetaData = immutableOpenMap.get(type);
					Map<String, Object> mapping = mappingMetaData.getSourceAsMap();
					if (mapping.containsKey("properties")) {
						Map<String, Object> properties = (Map<String, Object>) mapping.get("properties");
						for (String attribute : properties.keySet()) {
							filterAttributeCache(attribute, index_type_attributes);
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} 
	}
	
	@Override
	public Set<String> readNameAttributes() throws BusinessException {
		return name_attributes;
	}
	
	@Override
	public Set<String> readIdentityAttributes() throws BusinessException {
		return identity_attributes;
	}
	
	@Override
	public Set<String> readLocationAttributes() throws BusinessException {
		return location_attributes;
	}
	
	@Override
	public List<Map<String, Object>> readDataListByCondition(QueryBuilder query) throws BusinessException {
		return readDataListByCondition(query, true);
	}
	
	@Override
	public List<Map<String, Object>> readDataListByCondition(QueryBuilder query, boolean highLight)
			throws BusinessException {
		return readDataListByCondition(null, new String[0], query, highLight);
	}
	
	@Override
	public List<Map<String, Object>> readDataListByCondition(QueryBuilder query, int size) 
			throws BusinessException {
		return readDataListByCondition(query, size, true);
	}
	
	@Override
	public List<Map<String, Object>> readDataListByCondition(QueryBuilder query, int size, 
			boolean isHighLight) throws BusinessException {
		return readDataList(null, new String[0], query, size, isHighLight);
	}

	@Override
	public List<Map<String, Object>> readDataListByCondition(String index, String type, 
			QueryBuilder query) throws BusinessException {
		return readDataListByCondition(index, type, query, true);
	}
	
	@Override
	public List<Map<String, Object>> readDataListByCondition(String index, String type, 
			QueryBuilder query, boolean isHighLight) throws BusinessException {
		return readDataListByCondition(index, new String[]{type}, query, isHighLight);
	}
	
	@Override
	public List<Object> readDataListByCondition(String index, String type, String attribute, 
			List<String> attributeValues, int size) throws BusinessException {
		Client client = ESClient.getInstance().getClient();
		MultiSearchRequestBuilder msrb = client.prepareMultiSearch();
		for (int i = 0, len = attributeValues.size(); i < len; i++) {
			SearchRequestBuilder srb = client.prepareSearch(index).setTypes(type);
			srb.setQuery(QueryBuilders.termQuery(attribute, attributeValues.get(i)));
			srb.setSearchType(SearchType.QUERY_AND_FETCH);
			srb.setSize(size).setExplain(false);
			msrb.add(srb);
		}
		Item[] items = msrb.execute().actionGet().getResponses();
		List<Object> resultList = new ArrayList<Object>();
		for (int i = 0, len = items.length; i < len; i++) {
			SearchHits searchHits = items[i].getResponse().getHits();
			List<Map<String, Object>> hitResultList = new ArrayList<Map<String, Object>>(); 
			if (searchHits.getTotalHits() == 0) {
				hitResultList.add(new HashMap<String, Object>());
			} else {
				SearchHit[] hits = searchHits.getHits();
				for (int j = 0, jLen = hits.length; j < jLen; j++) {
					hitResultList.add(hits[j].getSource());
				}
			}
			resultList.add(hitResultList);
		}
		return resultList;
	}
	
	@Override
	public List<Map<String, Object>> readDataListByCondition(String index, String[] types, 
			QueryBuilder query) throws BusinessException {
		return readDataListByCondition(index, types, query, true);
	}
	
	@Override
	public List<Map<String, Object>> readDataListByCondition(String index, String[] types, 
			QueryBuilder query, boolean isHighLight) throws BusinessException {
		return readDataList(index, types, query, 200, isHighLight);
	}
	
	@Override
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(QueryBuilder query, 
			String scrollId, int size) throws BusinessException {
		return readPaginationDataListByCondition(null, new String[0], query, scrollId, size);
	}
	
	@Override
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, String type, 
			QueryBuilder query, String scrollId, int size) throws BusinessException {
		return readPaginationDataListByCondition(index, type, query, scrollId, size, true);
	}
	
	@Override
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, String type,
			QueryBuilder query, String scrollId, int size, boolean isHighLight) throws BusinessException {
		return readPaginationDataListByCondition(index, new String[]{type}, query, scrollId, size, isHighLight);
	}
	
	@Override
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, String type, 
			QueryBuilder query, SearchType searchType, String scrollId, int size, boolean isHighLight) 
				throws BusinessException {
		return readPaginationDataListByCondition(index, new String[]{type}, query, 
				searchType, scrollId, size, isHighLight);
	}
	
	@Override
	public QueryResult<Map<String, Object>> readPaginationDataListByConditionWithScore(String index, 
			String type, QueryBuilder query, String scrollId, int size) throws BusinessException {
		return readPaginationDataListByConditionWithScore(index, new String[]{type}, query, scrollId, size);
	}
	
	@Override
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, String[] types, 
			QueryBuilder query, String scrollId, int size) throws BusinessException {
		return readPaginationDataList(index, types, query, scrollId, size, true, false);
	}
	
	@Override
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, String[] types,
			QueryBuilder query, String scrollId, int size, boolean isHighLight) throws BusinessException {
		return readPaginationDataList(index, types, query, scrollId, size, isHighLight, false);
	}
	
	@Override
	public QueryResult<Map<String, Object>> readPaginationDataListByCondition(String index, String[] types,
			QueryBuilder query, SearchType searchType, String scrollId, int size, 
				boolean isHighLight) throws BusinessException {
		return readPaginationDataList(index, types, query, searchType, scrollId, size, isHighLight, false);
	}
	
	@Override
	public QueryResult<Map<String, Object>> readPaginationDataListByConditionWithScore(String index, 
			String[] types, QueryBuilder query, String scrollId, int size) throws BusinessException {
		return readPaginationDataList(index, types, query, SearchType.DFS_QUERY_AND_FETCH, 
				scrollId, size, true, true);
	}
	
	private String[] buildIndices(String index, Set<String> defaultIndices) {
		return (StringUtils.isBlank(index)) ? defaultIndices.toArray(new String[0]) : new String[]{index};
	}
	
	private String[] buildTypes(String[] types, Set<String> defaultTypes) {
		return (null == types || types.length == 0) ? defaultTypes.toArray(new String[0]) : types;
	}
	
	private SearchRequestBuilder buildSearchRequestBuilder(String qindex, String[] qtypes) {
		return ESClient.getInstance().getClient().prepareSearch(
				buildIndices(qindex, indices)).setTypes(buildTypes(qtypes, types));
	}
	
	private void wrapperSearchRequestBuilder(SearchRequestBuilder searchRequestBuilder) {
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
	
	private List<Map<String, Object>> readDataList(String index, String[] types, 
			QueryBuilder query, int size, boolean isHighLight) throws BusinessException {
		if (!indicesExists(index)) return new ArrayList<Map<String,Object>>();
		SearchRequestBuilder searchRequestBuilder = buildSearchRequestBuilder(index, types);
		searchRequestBuilder.setQuery(query);
		searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		wrapperSearchRequestBuilder(searchRequestBuilder);
		searchRequestBuilder.setSize(size);
		SearchResponse response = searchRequestBuilder.execute().actionGet();
		SearchHit[] hitArray = response.getHits().getHits();
		return buildResultList(hitArray, false, isHighLight, false);
	}
	
	private QueryResult<Map<String, Object>> readPaginationDataList(String index, String[] types,
			QueryBuilder query, String scrollId, int size, boolean isHighLight, 
				boolean isReturnScore) throws BusinessException {
		return readPaginationDataList(index, types, query, SearchType.DFS_QUERY_THEN_FETCH, 
				scrollId, size, isHighLight, isReturnScore);
	}
	
	private QueryResult<Map<String, Object>> readPaginationDataList(String index, String[] types,
			QueryBuilder query, SearchType searchType, String scrollId, int size, boolean isHighLight, 
				boolean isReturnScore) throws BusinessException {
		if (!indicesExists(index)) return new QueryResult<Map<String,Object>>();
		SearchRequestBuilder searchRequestBuilder = buildSearchRequestBuilder(index, types);
		searchRequestBuilder.setQuery(query);
		searchRequestBuilder.setSearchType(searchType);
		wrapperSearchRequestBuilder(searchRequestBuilder);
        searchRequestBuilder.setScroll(TimeValue.timeValueMinutes(3));
        searchRequestBuilder.setSize(size);
		SearchResponse response = searchRequestBuilder.execute().actionGet();
		if (StringUtils.isNotBlank(scrollId)) {
			response = ESClient.getInstance().getClient().prepareSearchScroll(scrollId)
					.setScroll(TimeValue.timeValueMinutes(3)).execute().actionGet();
		}
		SearchHit[] hitArray = response.getHits().getHits();
		QueryResult<Map<String, Object>> qr = new QueryResult<Map<String, Object>>();
		qr.setTotalRowNum(response.getHits().getTotalHits());
		qr.setScrollId(response.getScrollId());
		qr.setResultList(buildResultList(hitArray, true, isHighLight, isReturnScore));
		return qr;
	}
	
	private List<Map<String, Object>> buildResultList(SearchHit[] hitArray, boolean isPagination, 
			boolean isHighLight, boolean isReturnScore) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		SearchHit hit = null;
		String key = null;
		Object value = null;
		Map<String, Object> source = null;
		Map<String, Object> replaceSource = null;
		for (int i = 0, len = hitArray.length; i < len; i++) {
			hit = hitArray[i];
			source = hit.getSource();
			if (isHighLight) {
				wrapperHighLight(source, hit.getHighlightFields());
			}
			replaceSource = new HashMap<String, Object>();
			String prefix = hit.getIndex() + "." + hit.getType();
			for (Map.Entry<String, Object> entry : source.entrySet()) {
				key = entry.getKey();
				if (filter_attributes.contains(key) || key.endsWith("Alias")) continue;
				value = wrapperValue(key, entry.getValue());
				String chValue = MessageUtils.getInstance().getMessage(prefix + "." + key);
				replaceSource.put(StringUtils.isBlank(chValue) ? key : chValue, value);
			}
			if (isReturnScore) {
				replaceSource.put("score", hit.getScore());
			}
			if (isPagination) {
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("index", MessageUtils.getInstance().getMessage(hit.getIndex()));
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
	
	private void wrapperHighLight(Map<String, Object> source, Map<String, HighlightField> highLightFields) {
		String entryKey = null;
		Object entryValue = null;
		for (Map.Entry<String, Object> entry : source.entrySet()) {
			entryKey = entry.getKey();
			if (!highLightFields.containsKey(entryKey)) continue;
			Text[] texts = highLightFields.get(entryKey).getFragments();
			StringBuilder highLightText = new StringBuilder(100);
			for (int i = 0, tlen = texts.length; i < tlen; i++) {
				highLightText.append(texts[i]);
			}
			if (highLightText.length() > 0) entryValue = highLightText.toString();
			entry.setValue(entryValue);
		}
	}
	
	protected Object wrapperValue(String key, Object value) {
		return value;
	}
	
}


