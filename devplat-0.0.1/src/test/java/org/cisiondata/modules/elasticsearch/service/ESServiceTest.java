package org.cisiondata.modules.elasticsearch.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.datada.service.IDatadaService;
import org.cisiondata.modules.elasticsearch.plugins.stconverter.analysis.STConvertType;
import org.cisiondata.modules.elasticsearch.plugins.stconverter.analysis.STConverter;
import org.cisiondata.modules.identity.service.IMobileIdCardService;
import org.cisiondata.modules.identity.service.IMobileService;
import org.cisiondata.utils.spring.SpringContext;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.JedisCluster;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class ESServiceTest {
	
	@Resource(name = "datadaService")
	private IDatadaService datadaService = null;
	
	@Resource(name = "mobileService")
	private IMobileService mobileService = null;

	@Resource(name = "mobileIdCardService")
	private IMobileIdCardService mobileIdCardService = null;
	
	@Resource(name = "esService")
	private IESService esService = null;
	
	@Resource(name = "esBizService")
	private IESBizService esBizService = null;
	
	@Test
	public void testReadDataListByCondition() {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.should(QueryBuilders.termQuery("phone", "15811024484"));
		boolQueryBuilder.should(QueryBuilders.termQuery("mobilePhone", "15811024484"));			
		boolQueryBuilder.should(QueryBuilders.termQuery("telePhone", "15811024484"));		
		List<Map<String, Object>> list = esService.readDataListByCondition("account", "account", boolQueryBuilder);
		System.out.println("datalist size: " + list.size());
		QueryResult<Map<String, Object>> qr = esService.readPaginationDataListByCondition(
				"account", "account", boolQueryBuilder, null, 100);
		System.out.println("pagination list size: " + qr.getTotalRowNum());
		QueryResult<Map<String, Object>> qr1 = esBizService.readPaginationDataListByCondition(
				"account", "account", "782369901", null, 100);
		for (Map<String, Object> map : qr1.getResultList()) {
			System.out.println(map);
		}
		System.out.println("pagination list 1 size: " + qr1.getTotalRowNum());
	}
	
	@Test
	public void testReadPaginationDataListByCondition() {
		QueryResult<Map<String, Object>> qr = esBizService
				.readPaginationDataListByCondition("15811024484", null, 100);
//		QueryResult<Map<String, Object>> qr = esBizService.readPaginationDataListByCondition(
//				"account", "account", "15811024484", null, 100);
		System.out.println(qr.getTotalRowNum());
	}
	
	@Test
	public void testReadClassifiedQuery() {
		List<Map<String, Object>> resultList = mobileIdCardService.readClassifiedQuery(
				"account", "account", "15811024484");
		System.out.println("result list size: " + resultList.size());
	}
	
	@Test
	public void readDatadaData() {
		QueryResult<Map<String, Object>> data = datadaService.readDatadaDatas("15809911987", null, null);
		for (Map<String, Object> result : data.getResultList()) {
			System.out.println(result);
		}
		String scrollId = data.getScrollId();
		while (null != scrollId) {
			data = datadaService.readDatadaDatas("15809911987", null, scrollId);
			for (Map<String, Object> result : data.getResultList()) {
				System.out.println(result);
			}
		}
	}

	@Test
	public void testAspect() {
		System.out.println(SpringContext.get("logAspect"));
	}
	
	@Resource(name = "jedisCluster")
	private JedisCluster jedisCluster = null;
	
	@Test
	public void testGet() {
//		System.out.println(jedisCluster.setnx("es:lock", "ok"));
//		System.out.println(jedisCluster.setnx("es:lock", "ok"));
//		jedisCluster.set("es:name", "zhangsan");
		jedisCluster.del("es:statistics:datas:lock");
		System.out.println("vvvvvv: " + jedisCluster.get("es:statistics:datas:lock"));
//		long result = jedisCluster.setnx("es:statistics:datas:lock", "ok");
//		System.out.println("result: " + result);
	}
	
	@Test
	public void testSTPlugin() {
		STConverter stConverter = new STConverter();
        String str = stConverter.convert(STConvertType.TRADITIONAL_2_SIMPLE, "先禮後兵");
        String str1 = stConverter.convert(STConvertType.SIMPLE_2_TRADITIONAL, "非诚勿扰");
        System.out.printf(str);
        System.out.println();
        System.out.printf(str1);
	}
	
	
}
