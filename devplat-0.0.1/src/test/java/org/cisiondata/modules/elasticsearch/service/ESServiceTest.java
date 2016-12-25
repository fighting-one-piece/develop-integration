package org.cisiondata.modules.elasticsearch.service;

import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.datada.service.IDatadaService;
import org.cisiondata.modules.elasticsearch.plugins.stconverter.analysis.STConvertType;
import org.cisiondata.modules.elasticsearch.plugins.stconverter.analysis.STConverter;
import org.cisiondata.modules.identity.service.IMobileService;
import org.cisiondata.utils.spring.SpringContext;
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
