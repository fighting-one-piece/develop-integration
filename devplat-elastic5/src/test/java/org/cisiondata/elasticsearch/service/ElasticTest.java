package org.cisiondata.elasticsearch.service;

import org.cisiondata.modules.es.client.ESClientHelper;

public class ElasticTest {

	public static void main(String[] args) {
		ESClientHelper.createIndexType("logistics-v2", "logistics", "logistics-v1_logistics.json");
	}
	
}
