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
import org.cisiondata.utils.http.HttpUtils;
import org.cisiondata.utils.mongodb.MongoDBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

@Service("mobileAddressService")
public class MoblieAddressServiceImpl implements IMobileAddressService {
													//真实名字类
	private Logger LOG = LoggerFactory.getLogger(MobileParseServiceImpl.class);
	
	@Resource(name="mobileParseService")
	private IMobileParseService MobileParseService;
	
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
			String prefix = record.getString("prefix");
			if (StringUtils.isNotBlank(prefix)) {
				map.put("手机号码", record.get("mobile"));
				if(record.get("name")!= null && !record.get("name").equals("")){
					map.put("姓名",  record.get("name"));
				}else{
					map.put("姓名", "暂无记录");
				}
				map.put("手机号码号段", record.get("prefix"));
				map.put("地址", record.get("address"));
				map.put("号码类型",record.get("cardType"));
				map.put("区号", record.get("area"));
				map.put("邮政编码", record.get("zipCode"));
				map.put("运营商",  record.get("operators"));
			} else {
				try {
					String xml = HttpUtils.sendGet("http://api.k780.com:88/?app=phone.get&phone="+mobile+"&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=xml");
					org.dom4j.Document document = org.dom4j.DocumentHelper.parseText(xml);
					org.dom4j.Element root = document.getRootElement();
					String msg = root.element("result").elementText("msg");
					if (msg == null) {
						//手机号码号段
						prefix = root.element("result").elementText("prefix");
						//地址
						String address = root.element("result").elementText("att");
						//号码类型
						String cardType = root.element("result").elementText("ctype");
						//区号
						String area = root.element("result").elementText("area");
						//邮政编码
						String zipCode = root.element("result").elementText("postno");
						//运营商
						String operators = root.element("result").elementText("operators");
						//手机号码
						String phone=root.element("result").elementText("phone");
						map.put("手机号码", phone);
						map.put("姓名", "暂无记录");
						map.put("手机号码号段", prefix);
						map.put("地址", address);
						map.put("号码类型", cardType);
						map.put("区号", area);
						map.put("邮政编码", zipCode);
						map.put("运营商", operators);
						
						Document ufilter = new Document();  
				        ufilter.put("mobile", mobile);
				        Document updateContent = new Document(); 
				        updateContent.put("prefix", prefix);
				        updateContent.put("address", address);
				        updateContent.put("cardType", cardType);
				        updateContent.put("area", area);
				        updateContent.put("zipCode", zipCode);
				        updateContent.put("operators", operators);
				        
				        BasicDBObject update = new BasicDBObject("$set", updateContent);   
				        UpdateResult result1 = collection.updateOne(filter, update);
				        System.out.println(result1.wasAcknowledged());
					} else {
						throw new BusinessException(ResultCode.NOT_FOUNT_DATA.getCode(),ResultCode.NOT_FOUNT_DATA.getDesc());
					}
				} catch (BusinessException bu){
					throw new BusinessException(ResultCode.NOT_FOUNT_DATA.getCode(),ResultCode.NOT_FOUNT_DATA.getDesc());
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}
			
		}
		return map;				
	}
	
	/**
	 * 根据手机号码读取MongoDB记录，如果记录存在，分为两种情况：
	 * 一种是有归属地的，就直接返回，注意name字段，如果没有数据就返回暂无记录
	 * 一种是无归属地的，就需要去获取手机归属地相关信息，并更新MongoDB记录
	 * 如果记录不存在，就需要去获取手机归属地的相关信息，返回客户端
	 */
	public Map<String, Object> readAddressFromMoblieOld(String mobile)throws BusinessException{
		Pattern p = Pattern.compile("1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}");
		Matcher m = p.matcher(mobile);
		if (!m.matches()) {
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA.getCode(),ResultCode.NOT_FOUNT_DATA.getDesc());
		}
		//需要在此处查询mongodb里面是否存在数据，有了返回，没有了返回给下面的方法，查询
		MongoClient mongoClient = MongoDBUtils.getInstance().getClient();
		MongoDatabase database = mongoClient.getDatabase("mobile");
		MongoCollection<Document> collection = database.getCollection("imcaller");
		Document filter = new Document();
		filter.put("mobile", mobile);
		FindIterable<Document> docs = collection.find(filter);
		Document record = docs.first();
		if (null != record) {
			String prefi = record.getString("prefix");
			if (StringUtils.isNotBlank(prefi)) {
				TreeMap<String, Object> map = new TreeMap<String,Object>();	
				map.put("手机号码", record.get("mobile"));
				if(record.get("name")!=null && !record.get("name").equals("")){
					map.put("姓名",  record.get("name"));
				}else{
					Map<String,Object>mesh=MobileParseService.readDataFromImcaller(mobile);
					for (Object key : mesh.keySet()) {
						   if(key.equals("name")){
							   map.put("姓名", mesh.get(key));
							 }else{
						       map.put("姓名", "暂无记录");
							 }
						  }
				}
				map.put("手机号码号段", record.get("prefix"));
				map.put("地址", record.get("address"));
				map.put("号码类型",record.get("cardType"));
				map.put("区号", record.get("area"));
				map.put("邮政编码", record.get("zipCode"));
				map.put("运营商",  record.get("operators"));
				System.out.println(map);
				BasicDBObject update = new BasicDBObject("$set", map);   
				UpdateResult result1 = collection.updateOne(filter, update);
				System.out.println(result1.wasAcknowledged());
				return map;
			} else {
				return readAddressFromMoblies(mobile, true);
			}
			
		}
		return readAddressFromMoblies(mobile, false);				
	}
	//直接从网上获取(mongoDB 里面有禁止进入)
	private  Map<String, Object> readAddressFromMoblies(String mobile, boolean isUpdate)throws BusinessException {
		TreeMap<String, Object> finalResult = new TreeMap<String, Object>();
		
		//设置为全局变量
		String zipCode=null;
		String address=null;
		String cardType=null;
		String area=null;
		String operators=null;
		String prefix=null;
		//爬下来的名字
		String name="暂无记录";
		//手机号码
		String phone=null;
		//测试使用
		
		try {
			String xml = HttpUtils.sendGet("http://api.k780.com:88/?app=phone.get&phone="+mobile+"&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=xml");
			org.dom4j.Document document = org.dom4j.DocumentHelper.parseText(xml);
			org.dom4j.Element root = document.getRootElement();
			String msg = root.element("result").elementText("msg");
			if (msg == null) {
				//地址
				address = root.element("result").elementText("att");
				//号码类型
				cardType = root.element("result").elementText("ctype");
				//区号
				area = root.element("result").elementText("area");
				//邮政编码
				zipCode = root.element("result").elementText("postno");
				//运营商
				operators = root.element("result").elementText("operators");
				//手机号码号段
				prefix=root.element("result").elementText("prefix");
				
				//手机号码
				phone=root.element("result").elementText("phone");
				
				//姓名
				if(root.element("result").elementText("name")!=null && !root.element("result").elementText("name").equals("")){
				}
				Map<String,Object> mesh=MobileParseService.readDataFromImcaller(mobile);
				if(mesh!=null){
					for (Object key : mesh.keySet()) {
						if(key.equals("name")){
							name = (String)mesh.get(key);
						}else{
							name = "暂无记录";
						}
					}					
				}				
				finalResult.put("手机号码", phone);
				finalResult.put("姓名", name);
				finalResult.put("手机号码号段", prefix);
				finalResult.put("地址", address);
				finalResult.put("号码类型", cardType);
				finalResult.put("区号", area);
				finalResult.put("邮政编码", zipCode);
				finalResult.put("运营商", operators);
				
			} else {
				throw new BusinessException(ResultCode.NOT_FOUNT_DATA.getCode(),ResultCode.NOT_FOUNT_DATA.getDesc());
			}
		} catch (BusinessException bu){
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA.getCode(),ResultCode.NOT_FOUNT_DATA.getDesc());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		//update更新刷新mongoDB 
		if (isUpdate) {
			MongoClient mongoClient = MongoDBUtils.getInstance().getClient();  
	        MongoDatabase database = mongoClient.getDatabase("mobile");  
	        MongoCollection<Document> collection = database.getCollection("imcaller");  
	        Document filter = new Document();  
	        filter.put("mobile", mobile);
	        Document updateContent = new Document(); 
	        updateContent.put("prefix", prefix);
	        updateContent.put("address", address);
	        updateContent.put("cardType", cardType);
	        updateContent.put("area", area);
	        updateContent.put("zipCode", zipCode);
	        updateContent.put("operators", operators);
	        updateContent.put("phone", phone);
	        updateContent.put("name", name);
	        
	        BasicDBObject update = new BasicDBObject("$set", updateContent);   
	        UpdateResult result1 = collection.updateOne(filter, update);
	        System.out.println(result1.wasAcknowledged());
	        
		}
		return finalResult;
	}

}
