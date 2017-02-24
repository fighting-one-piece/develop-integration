package org.cisiondata.modules.elasticsearch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.cisiondata.modules.elasticsearch.client.ESClient;
import org.cisiondata.modules.elasticsearch.service.IESMetadataService;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.message.MessageUtils;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.carrotsearch.hppc.ObjectLookupContainer;
import com.carrotsearch.hppc.cursors.ObjectCursor;
import com.carrotsearch.hppc.cursors.ObjectObjectCursor;

import redis.clients.jedis.JedisCluster;

@Service("esMetadataService")
public class ESMetadataServiceImpl implements IESMetadataService, InitializingBean {

	private Logger LOG = LoggerFactory.getLogger(ESMetadataServiceImpl.class);

	private Map<String, List<Map<String, Map<String, String>>>> metadatas = null;

	@Resource(name = "jedisCluster")
	private JedisCluster jedisCluster = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		initMetadataCache();
	}

	@SuppressWarnings("unchecked")
	private void initMetadataCache() {
		metadatas = new HashMap<String, List<Map<String, Map<String, String>>>>();
		try {
			Client client = ESClient.getInstance().getClient();
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
				if (index.startsWith(".marvel-es")) continue;
				List<Map<String, Map<String, String>>> indexMetadatas = metadatas.get(index);
				if (null == indexMetadatas) {
					indexMetadatas = new ArrayList<Map<String, Map<String, String>>>();
					metadatas.put(index, indexMetadatas);
				}
				ImmutableOpenMap<String, MappingMetaData> immutableOpenMap = objectObjectCursor.value;
				ObjectLookupContainer<String> keys = immutableOpenMap.keys();
				Iterator<ObjectCursor<String>> keysIterator = keys.iterator();
				while (keysIterator.hasNext()) {
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
			if (!index.equalsIgnoreCase(entry.getKey()))
				continue;
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
	public Map<String, String> readIndexTypeAttributes(String index, String type) throws BusinessException {
		List<Map<String, Map<String, String>>> typesMappings = metadatas.get(index);
		for (Map<String, Map<String, String>> typeMappings : typesMappings) {
			if (typeMappings.size() != 1)
				continue;
			String typeName = new ArrayList<String>(typeMappings.keySet()).get(0);
			if (!typeName.equalsIgnoreCase(type))
				continue;
			Map<String,String> map = typeMappings.get(type);
			Map<String,String> typeMap = new HashMap<String,String>();
			for(Entry<String,String> entry : map.entrySet()){
				if( entry.getKey().endsWith("inputPerson") || entry.getKey().endsWith("insertTime")
						|| entry.getKey().endsWith("updateTime") || entry.getKey().endsWith("sourceFile")
						|| entry.getKey().endsWith("cnote")
						|| ("email".equals(type) && "inputPersion".equals(entry.getKey())) 
						|| ("logistics".equals(type) && "expressCompanyName".equals(entry.getKey()))){
					continue;
				} else {
					typeMap.put(entry.getKey(), entry.getValue());
				}
			}
			return typeMap;
		}
		return new HashMap<String, String>();
	}

	@Override
	public void updateMetadatasNull() throws BusinessException {
		metadatas.clear();
		metadatas = null;
	}

}
