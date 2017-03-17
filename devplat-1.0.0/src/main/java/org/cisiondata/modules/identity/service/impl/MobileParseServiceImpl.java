package org.cisiondata.modules.identity.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.identity.service.IMobileParseService;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.http.HttpUtils;
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
		MongoCollection<Document> collection = database.getCollection("imcaller_temp");
		Document filter = new Document();
		filter.put("mobile", mobile);
		FindIterable<Document> docs = collection.find(filter);
		Document record = docs.first();
		return null == record ? onlineParseFromImcaller(mobile) : wrapperDocument(record);
	}
	
	@Override
	public Map<String, String> readDataFromK780(String mobile) throws BusinessException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String url = "http://api.k780.com:88/?app=phone.get&phone="+mobile+"&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=xml";
			String xml = HttpUtils.sendGet(url);
			org.dom4j.Document document = org.dom4j.DocumentHelper.parseText(xml);
			org.dom4j.Element root = document.getRootElement();
			String msg = root.element("result").elementText("msg");
			if (msg == null) {
				//手机号码号段
				map.put("prefix", root.element("result").elementText("prefix"));
				//地址
				map.put("address", root.element("result").elementText("att"));
				//号码类型
				map.put("cardType", root.element("result").elementText("ctype"));
				//区号
				map.put("area", root.element("result").elementText("area"));
				//邮政编码
				map.put("zipCode", root.element("result").elementText("postno"));
				//运营商
				map.put("operators", root.element("result").elementText("operators"));
				//手机号码
				map.put("phone", root.element("result").elementText("phone"));
			} else {
				throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
			}
		} catch (Exception e){
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		}
		return map;
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
		MongoCollection<Document> collection = database.getCollection("imcaller_temp");
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
