package org.cisiondata.elasticsearch.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cisiondata.modules.elastic.client.ESClient;
import org.cisiondata.modules.elastic.plugins.stconverter.analysis.STConvertType;
import org.cisiondata.modules.elastic.plugins.stconverter.analysis.STConverter;
import org.cisiondata.utils.date.DateFormatter;
import org.cisiondata.utils.file.FileUtils;
import org.cisiondata.utils.file.LineHandler;
import org.cisiondata.utils.json.GsonUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ElasticSearchTest {
	
	private static Gson gson = new GsonBuilder()
			.serializeSpecialFloatingPointValues()
			.setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	public void stconverter() {
		STConverter converter = new STConverter();
		String t = converter.convert("龙", STConvertType.SIMPLE_2_TRADITIONAL);
		System.out.println(t);
		String s = converter.convert("劉,邝,粱,闾,黃,馮,馬,韓,陳,赫,許,苑,頋,趙,謝", STConvertType.TRADITIONAL_2_SIMPLE);
		System.out.println(s);
	}
	
	public static void t1() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("n1", "追夢");
		map.put("i1", "13512345678");
		map.put("c138", DateFormatter.TIME.get().format(new Date()));
		Map<String, Object> cmap = new HashMap<String, Object>();
		cmap.put("idcard", "123123123123123123");
		cmap.put("old", "刘大鹏");
		map.put("c139", cmap);
		String json = gson.toJson(map, Map.class);
//		String json = GsonUtils.fromMapToJson(map);
		System.err.println("json: " + json);
		
//		Map<String, Object> fmap = gson.fromJson(json, Map.class);
		Map<String, Object> fmap = GsonUtils.fromJsonToMap(json);
		System.err.println("fmap: " + fmap);
		for (Map.Entry<String, Object> entry : fmap.entrySet()) {
			System.err.println(entry.getKey() + " : " + entry.getValue() + " : "  + entry.getValue().getClass());
		}
		String cnoteJson = (String) fmap.get("c139");
		Map<String, Object> cnote = GsonUtils.fromJsonToMap(cnoteJson);
		System.err.println("cnote: " + cnote);
		
		Client client = ESClient.getInstance().getClient();
		IndexRequestBuilder irb = client.prepareIndex("resume-v1", "resume", "stconvert000001");
		irb.setSource(fmap);
		IndexResponse ir = irb.execute().actionGet();
		System.err.println("created: " + ir.isCreated());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void t2() {
		try {
			List<Map<String, Object>> mapList = FileUtils.readFromAbsolute("F:\\hdfs.txt", new LineHandler() {

				@Override
				public Object handle(String line) {
					return GsonUtils.fromJsonToMap(line);
				}

				@Override
				public boolean filter(Object t) {
					return false;
				}
				
			});
			Client client = ESClient.getInstance().getClient();
			BulkRequestBuilder bulkRequest = client.prepareBulk();
			for (Map<String, Object> map : mapList) {
				System.err.println(map);
				IndexRequestBuilder irb = client.prepareIndex("resume-v1", "resume", (String) map.remove("_id"));
				irb.setSource(map);
				bulkRequest.add(irb);
			}
			BulkResponse bulkResponse = bulkRequest.execute().actionGet();
			if (bulkResponse.hasFailures()) {
				System.err.println(bulkResponse.buildFailureMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
	}
	
}
