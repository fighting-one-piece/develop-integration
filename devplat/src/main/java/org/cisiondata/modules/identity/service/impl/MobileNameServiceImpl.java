package org.cisiondata.modules.identity.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.cisiondata.modules.identity.service.IMobileNameService;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.mongodb.MongoDBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Service("mobileNameService")
public class MobileNameServiceImpl implements IMobileNameService {

	private Logger LOG = LoggerFactory.getLogger(MobileNameServiceImpl.class);

	@Override
	public String readNameFromMobile(String mobile) throws BusinessException {
		MongoClient mongoClient = MongoDBUtils.getInstance().getClient();
		MongoDatabase database = mongoClient.getDatabase("mobile");
		MongoCollection<Document> collection = database.getCollection("mobile");
		Document filter = new Document();
		filter.put("mobile", mobile);
		FindIterable<Document> docs = collection.find(filter);
		Document record = docs.first();
		if (null != record) {
			String name = record.getString("name");
			return StringUtils.isBlank(name) ? "暂无记录" : name;
		}
		return invokeScrapyCrawlerFetchName(mobile);
	}

	private String invokeScrapyCrawlerFetchName(String mobile) {
		String shFilePath = MobileNameServiceImpl.class.getClassLoader()
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
			return "暂无记录";
		} finally {
			try {
				if (null != in) in.close();
				if (null != br) br.close();
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
				return "暂无记录";
			}
		}
		MongoClient mongoClient = MongoDBUtils.getInstance().getClient();
		MongoDatabase database = mongoClient.getDatabase("mobile");
		MongoCollection<Document> collection = database.getCollection("mobile");
		Document filter = new Document();
		filter.put("mobile", mobile);
		FindIterable<Document> docs = collection.find(filter);
		Document record = docs.first();
		if (null != record) {
			String name = record.getString("name");
			return StringUtils.isBlank(name) ? "暂无记录" : name;
		}
		return "暂无记录";
	}

}
