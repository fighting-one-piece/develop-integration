package org.cisiondata.modules.elastic.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.elastic.client.ESClient;
import org.cisiondata.modules.elastic.service.IESV3Service;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

public class ESV3ServiceImpl extends AbstractESServiceV3Impl implements IESV3Service {

	@Override
	public QueryResult<Map<String, Object>> readDataList(String keywords) throws RuntimeException {
		if (StringUtils.isBlank(keywords)) throw new RuntimeException("keywords is null");
		QueryResult<Map<String, Object>> qr = new QueryResult<>();
		try {
			SearchRequestBuilder searchRequestBuilder = ESClient.getInstance().getClient()
					.prepareSearch(defaultIndices()).setTypes(defaultTypes());
			searchRequestBuilder.setQuery(buildBoolQuery(keywords));
			searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
			searchRequestBuilder.setSize(1000);
			searchRequestBuilder.setExplain(false);
			SearchResponse response = searchRequestBuilder.execute().actionGet();
			SearchHit[] hits = response.getHits().getHits();
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for (int i = 0, len = hits.length; i < len; i++) {
				resultList.add(hits[i].getSource());
			}
			qr.setResultList(resultList);
			qr.setTotalRowNum(response.getHits().getTotalHits());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return qr;
	}

	private BoolQueryBuilder buildBoolQuery(String keywords) {
		String[] keywordArray = keywords.trim().indexOf(" ") == -1 ? new String[]{keywords} : keywords.split(" ");
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		String[] types = defaultTypes();
		for (int i = 0, len = keywordArray.length; i < len; i++) {
			String keyword = keywordArray[i];
			BoolQueryBuilder subBoolQueryBuilder = new BoolQueryBuilder();
			for (int j =0, jLen = types.length; j < jLen; j++) {
				Set<String> attributes = type_attributes_mapping.get(types[j]);
				for (String attribute : attributes) {
					boolQueryBuilder.should(QueryBuilders.matchPhraseQuery(attribute, keyword));
				}
			}
			boolQueryBuilder.must(subBoolQueryBuilder);
		}
		return boolQueryBuilder;
	}
	
}
