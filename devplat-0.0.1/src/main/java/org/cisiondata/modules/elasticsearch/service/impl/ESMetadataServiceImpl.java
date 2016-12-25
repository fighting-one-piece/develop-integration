package org.cisiondata.modules.elasticsearch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.cisiondata.modules.elasticsearch.client.ESClient;
import org.cisiondata.modules.elasticsearch.dao.ESMetadataDAO;
import org.cisiondata.modules.elasticsearch.entity.ESMetadata;
import org.cisiondata.modules.elasticsearch.service.IESMetadataService;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.message.MessageUtils;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisCluster;

import com.carrotsearch.hppc.ObjectLookupContainer;
import com.carrotsearch.hppc.cursors.ObjectCursor;
import com.carrotsearch.hppc.cursors.ObjectObjectCursor;

@Service("esMetadataService")
public class ESMetadataServiceImpl implements IESMetadataService, InitializingBean {
	
	private Logger LOG = LoggerFactory.getLogger(ESMetadataServiceImpl.class);
	
	private Map<String, List<Map<String, Map<String, String>>>> metadatas = null;
	
	private Map<String, List<String>> indexTypes = null;
	
	private Map<String, Long> datas = new HashMap<String, Long>();
	
	private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

	@Resource(name = "jedisCluster")
	private JedisCluster jedisCluster = null;
	
	@Autowired
	private ESMetadataDAO esMetadataDAO = null;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		initMetadataCache();
		long result = jedisCluster.setnx("es:statistics:datas:lock", "ok");
		LOG.info("es:statistics:datas:lock : {}", result);
		if (result == 0) return;
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				LOG.info("start initialize es statistics datas schedule task");
				long startTime = System.currentTimeMillis();
				initializeDatas();
				LOG.info("end initialize es statistics datas schedule task , spend time {} s",
						(System.currentTimeMillis() - startTime) / 1000);
				jedisCluster.del("es:statistics:datas:lock");
			}
		}, 1, 60 * 12, TimeUnit.MINUTES);
	}
	
	private void initializeDatas() {
		datas.clear();
		initMetadataIndexTypes10(datas);
		initMetadataIndexTypes105(datas);
	}
	
	@Override
	public Map<String, Long> readESStatisticsDatas() throws BusinessException {
		if (datas.isEmpty()) initializeDatas();
		return datas;
	}
	
	//查询10集群index和type
	private void initMetadataIndexTypes10(Map<String, Long> datas) {
		indexTypes = new HashMap<String, List<String>>();
		Client client = ESClient.getInstance().getClient10();
		try {
			IndicesAdminClient indicesAdminClient = client.admin().indices();
			GetMappingsResponse getMappingsResponse = indicesAdminClient.getMappings(new GetMappingsRequest()).get();
			ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> mappings = 
					getMappingsResponse.getMappings();
			Iterator<ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>>> 
			mappingIterator = mappings.iterator();
			while (mappingIterator.hasNext()) {
				ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>>
				objectObjectCursor = mappingIterator.next();
				String index = objectObjectCursor.key;
				List<String> types = indexTypes.get(index);
				if (null == types) {
					types = new ArrayList<String>();
					indexTypes.put(index, types);
				}
				ImmutableOpenMap<String, MappingMetaData> immutableOpenMap = objectObjectCursor.value;
				ObjectLookupContainer<String> keys = immutableOpenMap.keys();
				Iterator<ObjectCursor<String>> keysIterator = keys.iterator();
				while(keysIterator.hasNext()) {
					String type = keysIterator.next().value;
					types.add(type);
					}
				}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		for (Entry<String, List<String>> entry : indexTypes.entrySet()) { 
		    List<String> data = entry.getValue();
		    for(int i = 0;i<data.size();i++){
		    	String values = data.get(i);
		    	 handleIndexTypeCount(client, entry.getKey(), values, datas);
		    }
		}  
	}
	
	//查询105集群index和type
	private void initMetadataIndexTypes105(Map<String, Long> datas) {
		indexTypes = new HashMap<String, List<String>>();
		Client client = ESClient.getInstance().getClient105();
		try {
			IndicesAdminClient indicesAdminClient = client.admin().indices();
			GetMappingsResponse getMappingsResponse = indicesAdminClient.getMappings(new GetMappingsRequest()).get();
			ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> mappings = 
					getMappingsResponse.getMappings();
			Iterator<ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>>> 
			mappingIterator = mappings.iterator();
			while (mappingIterator.hasNext()) {
				ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>>
				objectObjectCursor = mappingIterator.next();
				String index = objectObjectCursor.key;
				List<String> types = indexTypes.get(index);
				if (null == types) {
					types = new ArrayList<String>();
					indexTypes.put(index, types);
				}
				ImmutableOpenMap<String, MappingMetaData> immutableOpenMap = objectObjectCursor.value;
				ObjectLookupContainer<String> keys = immutableOpenMap.keys();
				Iterator<ObjectCursor<String>> keysIterator = keys.iterator();
				while(keysIterator.hasNext()) {
					String type = keysIterator.next().value;
					types.add(type);
					}
				}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		for (Entry<String, List<String>> entry : indexTypes.entrySet()) { 
		    List<String> data = entry.getValue();
		    for(int i = 0;i<data.size();i++){
		    	String values = data.get(i);
		    	 handleIndexTypeCount(client, entry.getKey(), values, datas);
		    }
		}  
	}

	public long readIndexTypeCount(Client client, String index, String type) {
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type);
		searchRequestBuilder.setQuery(QueryBuilders.matchAllQuery());
		searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH);
		searchRequestBuilder.setExplain(false);
		SearchResponse response = searchRequestBuilder.execute().actionGet();
		return response.getHits().getTotalHits();
	}
	
	public void handleIndexTypeCount(Client client, String index, String type, Map<String, Long> datas) {
		long current_count = readIndexTypeCount(client, index, type);
		Long logistics_count = datas.get(type);
		if (null == logistics_count) {
			datas.put(type, current_count);
		} else {
			datas.put(type, logistics_count + current_count);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void initMetadataCache() {
		metadatas = new HashMap<String, List<Map<String, Map<String, String>>>>(); 
		try {
			Client client = ESClient.getInstance().getClient105();
			IndicesAdminClient indicesAdminClient = client.admin().indices();
			GetMappingsResponse getMappingsResponse = indicesAdminClient.getMappings(new GetMappingsRequest()).get();
			ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> mappings = 
					getMappingsResponse.getMappings();
			Iterator<ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>>> 
			mappingIterator = mappings.iterator();
			while (mappingIterator.hasNext()) {
				ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>>
				objectObjectCursor = mappingIterator.next();
				String index = objectObjectCursor.key;
				List<Map<String, Map<String, String>>> indexMetadatas = metadatas.get(index);
				if (null == indexMetadatas) {
					indexMetadatas = new ArrayList<Map<String, Map<String, String>>>();
					metadatas.put(index, indexMetadatas);
				}
				ImmutableOpenMap<String, MappingMetaData> immutableOpenMap = objectObjectCursor.value;
				ObjectLookupContainer<String> keys = immutableOpenMap.keys();
				Iterator<ObjectCursor<String>> keysIterator = keys.iterator();
				while(keysIterator.hasNext()) {
					String type = keysIterator.next().value;
					Map<String, Map<String, String>> typeMetadatas = new HashMap<String, Map<String, String>>();
					MappingMetaData mappingMetaData = immutableOpenMap.get(type);
					Map<String, Object> mapping = mappingMetaData.getSourceAsMap();
					if (mapping.containsKey("properties")) {
						Map<String, String> attributeMetadatas = new HashMap<String, String>();
						Map<String, Object> properties = (Map<String, Object>) mapping.get("properties");
						for (String attribute : properties.keySet()) {
							String code = index + "." + type + "." + attribute;
							attributeMetadatas.put(attribute, MessageUtils.getInstance().getMessage(code));
						}
						typeMetadatas.put(type, attributeMetadatas);
						indexMetadatas.add(typeMetadatas);
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}
	
	
	@Override
	public Object readMetadatas() throws BusinessException {
		if (null == metadatas) initMetadataCache();
		return metadatas;
	}
	
	@Override
	public Map<String, String> readIndices() throws BusinessException {
		Map<String, String> indices = new HashMap<String, String>();
		for (Map.Entry<String, List<Map<String, Map<String, String>>>> entry : metadatas.entrySet()) {
			indices.put(entry.getKey(), MessageUtils.getInstance().getMessage(entry.getKey()));
		}
		return indices;
	}
	
	@Override
	public Map<String, String> readIndexTypes(String index) throws BusinessException {
		Map<String, String> types = new HashMap<String, String>();
		for (Map.Entry<String, List<Map<String, Map<String, String>>>> entry : metadatas.entrySet()) {
			if (!index.equalsIgnoreCase(entry.getKey())) continue;
			List<Map<String, Map<String, String>>> typesMappings = entry.getValue();
			for (Map<String, Map<String, String>> typeMappings : typesMappings) {
				if (typeMappings.size() == 1) {
					String type = new ArrayList<String>(typeMappings.keySet()).get(0);
					types.put(type, MessageUtils.getInstance().getMessage(index + "." + type));
				}
			}
		}
		return types;
	}
	
	@Override
	public Map<String, List<String>> readIndicesTypes() throws BusinessException {
		Map<String, List<String>> indicesTypes = new HashMap<String, List<String>>();
		for (Map.Entry<String, List<Map<String, Map<String, String>>>> entry : metadatas.entrySet()) {
			String index = entry.getKey();
			List<String> types = indicesTypes.get(index);
			if (null == types) {
				types = new ArrayList<String>();
			}
			List<Map<String, Map<String, String>>> typesMappings = entry.getValue();
			for (Map<String, Map<String, String>> typeMappings : typesMappings) {
				if (typeMappings.size() == 1) {
					types.add(new ArrayList<String>(typeMappings.keySet()).get(0));
				}
			}
			indicesTypes.put(index, types);
		}
		return indicesTypes;
	}
	
	@Override
	public Map<String, String> readIndexTypeAttributes(String index, String type)
			throws BusinessException {
		List<Map<String, Map<String, String>>> typesMappings = metadatas.get(index);
		for (Map<String, Map<String, String>> typeMappings : typesMappings) {
			if (typeMappings.size() != 1) continue;
			String typeName = new ArrayList<String>(typeMappings.keySet()).get(0);
			if (!typeName.equalsIgnoreCase(type)) continue;
			return typeMappings.get(type);
		}
		return new HashMap<String, String>();
	}
		
	@Override
	public void updateMetadatasNull() throws BusinessException {
		metadatas.clear();
		metadatas = null;
	}
	
	//根据type查询
	@Override
	public List<ESMetadata> findType(String type) {
		List<ESMetadata> list =new  ArrayList<ESMetadata>();
		list = esMetadataDAO.findType(type);
		return list;
	}
	
	//根据ID删除
	@Override
	public int deleteId(int id) {
		int num = esMetadataDAO.deleteId(id);
		return num;
	}
	
	//添加
	@Override
	public void save(ESMetadata metadata) {
		esMetadataDAO.save(metadata);
	}

	//根据ID修改
	@Override
	public int updateId(ESMetadata metadata) {
		int num = esMetadataDAO.updateId(metadata);
		return num;
	}
}
