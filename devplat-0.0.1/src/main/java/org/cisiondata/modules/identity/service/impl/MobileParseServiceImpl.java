package org.cisiondata.modules.identity.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.cisiondata.modules.identity.service.IMobileParseService;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.mongodb.MongoDBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Service("mobileParseService")
public class MobileParseServiceImpl implements IMobileParseService {

	private Logger LOG = LoggerFactory.getLogger(MobileParseServiceImpl.class);

	@Override
	public Map<String, Object> readDataFromImcaller(String mobile) throws BusinessException {
		MongoClient mongoClient = MongoDBUtils.getInstance().getClient();
		MongoDatabase database = mongoClient.getDatabase("mobile");
		MongoCollection<Document> collection = database.getCollection("imcaller");
		Document filter = new Document();
		filter.put("mobile", mobile);
		FindIterable<Document> docs = collection.find(filter);
		Document record = docs.first();
		return null == record ? onlineParseFromImcaller(mobile) : wrapperDocument(record);
	}

	private Map<String, Object> onlineParseFromImcaller(String mobile) {
		String shFilePath = MobileParseServiceImpl.class.getClassLoader()
				.getResource("crawler/mobile_crawler.sh").getPath();
		LOG.info("sh file path: {}", shFilePath);
		Runtime runtime = Runtime.getRuntime();
		String[] commands = new String[] {"sh", shFilePath, mobile};
		InputStream in = null;
		BufferedReader br = null;
		try {
			Process process = runtime.exec(commands);
			in = process.getInputStream();
			br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = br.readLine()) != null) {
				LOG.info(line);
			}
			process.waitFor();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			try {
				if (null != in) in.close();
				if (null != br) br.close();
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
			}
		}
		MongoClient mongoClient = MongoDBUtils.getInstance().getClient();
		MongoDatabase database = mongoClient.getDatabase("mobile");
		MongoCollection<Document> collection = database.getCollection("imcaller");
		Document filter = new Document();
		filter.put("mobile", mobile);
		FindIterable<Document> docs = collection.find(filter);
		Document record = docs.first();
		return null == record ? null : wrapperDocument(record);
	}
	
	private Map<String, Object> wrapperDocument(Document record) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", record.get("name"));
		map.put("mobile", record.get("mobile"));
		map.put("jsonstr", record.get("jsonstr"));
		return map;
	}

}
