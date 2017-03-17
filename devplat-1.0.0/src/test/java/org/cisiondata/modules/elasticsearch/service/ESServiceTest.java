package org.cisiondata.modules.elasticsearch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.elasticsearch.plugins.stconverter.analysis.STConvertType;
import org.cisiondata.modules.elasticsearch.plugins.stconverter.analysis.STConverter;
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
	
	@Resource(name = "esService")
	private IESService esService = null;
	
	@Resource(name = "esBizService")
	private IESBizService esBizService = null;
	
	@Test
	public void testReadDataListByQQ() {
		List<Map<String, Object>> listMap = esService.readDataListByCondition(
				"qq", "qqqunrelation", QueryBuilders.termQuery("qqNum", "445385318"));
		List<String> list = new ArrayList<String>();
		for (int i = 0, len = listMap.size(); i < len; i++) {
			System.out.println(listMap.get(i));
			list.add(listMap.get(i).get("QQ群号").toString());
		}
	}
	
	@Test
	public void testReadDataListByAttributeValues() {
		List<String> qqNumList = new ArrayList<String>();
		long qqNum = 471246431L;
		for (int i = 0; i < 100; i++) {
			qqNumList.add(String.valueOf(qqNum + i));
		}
		List<Object> resultList = esService.readDataListByCondition("qq", "qqdata", "qqNum", qqNumList, 1);
		for (Object result : resultList) {
			System.out.println(result);
		}
	}
	
	@Test
	public void testReadLabelsAndHits() {
		String query = "13512345678";
		String[] includeTypes = new String[]{"car","house","telecom","business","hotel"};
		List<Map<String, Object>> r1 = esBizService.readLabelsAndHitsIncludeTypes(query, includeTypes);
		for (Map<String, Object> r : r1) {
			System.out.println(r);
		}
		System.out.println("#######");
		String[] excludeTypes = new String[]{"account","accountjd","accountht","email","logistics"};
		List<Map<String, Object>> r2 = esBizService.readLabelsAndHitsExcludeTypes(query, excludeTypes);
		for (Map<String, Object> r : r2) {
			System.out.println(r);
		}
	}
	
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
	public void testReadPaginationDataListByConditionNoMessageSource() {
		QueryResult<Map<String, Object>> qr = esService.readPaginationDataListByCondition("financial", "logistics", 
				QueryBuilders.termQuery("mobilePhone", "13512345678"), null, 10, true, true);
		System.out.println(qr.getTotalRowNum());
		for (Map<String, Object> result : qr.getResultList()) {
			System.out.println(result);
		}
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
