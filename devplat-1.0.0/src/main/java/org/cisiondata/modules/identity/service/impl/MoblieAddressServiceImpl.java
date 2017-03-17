package org.cisiondata.modules.identity.service.impl;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.identity.service.IMobileAddressService;
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

@Service("mobileAddressService")
public class MoblieAddressServiceImpl implements IMobileAddressService {
	
	private Logger LOG = LoggerFactory.getLogger(MobileParseServiceImpl.class);
	
	@Resource(name="mobileParseService")
	private IMobileParseService mobileParseService = null;
	
	/**
	 * 根据手机号码读取MongoDB记录，如果记录存在，分为两种情况：
	 * 一种是有归属地的，就直接返回，注意name字段，如果没有数据就返回暂无记录
	 * 一种是无归属地的，就需要去获取手机归属地相关信息，并更新MongoDB记录
	 * 如果记录不存在，就需要去获取手机归属地的相关信息，返回客户端
	 */
	@Override
	public Map<String, Object> readAddressFromMoblie (String mobile)throws BusinessException{
		Pattern p = Pattern.compile("1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}");
		Matcher m = p.matcher(mobile);
		if (!m.matches()) {
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA.getCode(),ResultCode.NOT_FOUNT_DATA.getDesc());
		}
		MongoClient mongoClient = MongoDBUtils.getInstance().getClient();
		MongoDatabase database = mongoClient.getDatabase("mobile");
		MongoCollection<Document> collection = database.getCollection("imcaller");
		Document filter = new Document();
		filter.put("mobile", mobile);
		FindIterable<Document> docs = collection.find(filter);
		Document record = docs.first();
		TreeMap<String, Object> map = new TreeMap<String,Object>();	
		if (null != record) {
			map.put("手机号码", record.get("mobile"));
			Object nameObj = record.get("name");
			String name = null != nameObj && StringUtils.isNotBlank((String) nameObj) ?
					(String) nameObj : "暂无记录";
			map.put("姓名", name);		
			map.put("手机号码号段", record.get("prefix"));
			map.put("地址", record.get("address"));
			map.put("号码类型",record.get("cardType"));
			map.put("区号", record.get("area"));
			map.put("邮政编码", record.get("zipCode"));
			map.put("运营商",  record.get("operators"));
		} else {
			try {
				Map<String, String> mobileAddressMap = mobileParseService.readDataFromK780(mobile);
				map.put("手机号码", mobileAddressMap.get("phone"));
				map.put("手机号码号段", mobileAddressMap.get("prefix"));
				map.put("地址", mobileAddressMap.get("address"));
				map.put("号码类型", mobileAddressMap.get("cardType"));
				map.put("区号", mobileAddressMap.get("area"));
				map.put("邮政编码", mobileAddressMap.get("zipCode"));
				map.put("运营商", mobileAddressMap.get("operators"));
				Map<String, Object> mobileNameMap = null;
				try {
					mobileNameMap = mobileParseService.readDataFromImcaller(mobile);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
				if (null != mobileNameMap) {
					Document insertDocument = new Document();  
					String name = (String) mobileNameMap.get("name");
					map.put("姓名", StringUtils.isBlank(name) ? "暂无记录" : name);
					insertDocument.put("mobile", mobileNameMap.get("mobile"));
					insertDocument.put("name", mobileNameMap.get("name"));
					insertDocument.put("jsonstr", mobileNameMap.get("jsonstr"));
					insertDocument.put("prefix", mobileAddressMap.get("prefix"));
					insertDocument.put("address", mobileAddressMap.get("address"));
					insertDocument.put("cardType", mobileAddressMap.get("cardType"));
					insertDocument.put("area", mobileAddressMap.get("area"));
					insertDocument.put("zipCode", mobileAddressMap.get("zipCode"));
					insertDocument.put("operators", mobileAddressMap.get("operators"));
					collection.insertOne(insertDocument);
				} else {
					map.put("姓名", "暂无记录");
				}
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
			}
		}
		return map;				
	}
	
}
