package org.cisiondata.modules.elasticsearch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.es.plugins.stconverter.analysis.STConvertType;
import org.cisiondata.modules.es.plugins.stconverter.analysis.STConverter;
import org.cisiondata.modules.es.service.IESService;
import org.cisiondata.modules.search.service.IESBizService;
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
	public void testReadPaginationDataListByCondition() {
		QueryResult<Map<String, Object>> qr = esBizService
				.readPaginationDataListByCondition("15811024484", null, 100);
//		QueryResult<Map<String, Object>> qr = esBizService.readPaginationDataListByCondition(
//				"account", "account", "15811024484", null, 100);
		System.out.println(qr.getTotalRowNum());
		for (Map<String, Object> r : qr.getResultList()) {
			System.out.println(r);
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
