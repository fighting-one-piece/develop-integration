package org.cisiondata.modules.identity.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.cisiondata.modules.es.service.IESService;
import org.cisiondata.modules.identity.service.IMobileIdCardService;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.message.MessageUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

@Service("mobileIdCardService")
public class MobileIdCardServiceImpl implements IMobileIdCardService {

	@Resource(name = "esService")
	private IESService esService = null;
	
	@Override
	public Map<String, Object> readStatisticsAndLabels(String identity) throws BusinessException {
		Set<String> names = new HashSet<String>();
		Set<String> mobiles = new HashSet<String>();
		Set<String> idcards = new HashSet<String>();
		Set<String> labels = new HashSet<String>();
		boolean phoneFlag = true;
		boolean idCardFlag = true;
		
		if(identity.length()==11){
			phoneFlag = false;
			mobiles.add(identity);
		}else if(identity.length()==18 || identity.length()==15){
			idCardFlag = false;
			idcards.add(identity);
		}
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		Set<String> identityAttributes = esService.readIdentityAttributes();
		for (String identityAttribute : identityAttributes) {
			boolQueryBuilder.should(QueryBuilders.termQuery(identityAttribute, identity));			
		}
		List<Map<String, Object>> resultList = esService.readDataListByCondition(boolQueryBuilder, 1000, false);
		for (int i = 0, len = resultList.size(); i < len; i++) {
			Map<String, Object> result = resultList.get(i);	
			//遍历数据
			for(String key:result.keySet()){
				if(phoneFlag && (key.contains("电话") || key.contains("手机"))){
					mobiles.add((String)result.get(key)); 
				}else if(idCardFlag && key.contains("身份证")){
					idcards.add((String) result.get(key));
				}else if("业主".equals(key) || "寄件人".equals(key) || "收件人".equals(key) || key.contains("姓名")){
					names.add((String) result.get(key)); 
				}
				 }
			String code = result.get("index") + "." + result.get("type");
			labels.add(result.get("index") + ":" + result.get("type") + ":" + MessageUtils.getInstance().getMessage(code));
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("names", names);
		result.put("mobiles", mobiles);
		result.put("idcards", idcards);
		result.put("labels", labels);
		return result;
	}	
	
	/**
	 * 标签查询
	 */
	public List<Map<String, Object>> readClassifiedQuery(String index, String type, String identity) throws BusinessException {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		if(identity.length()==18 || identity.length()==15){
			boolQueryBuilder.should(QueryBuilders.prefixQuery("idCard", identity));
		}else if(identity.length()==11){
			boolQueryBuilder.should(QueryBuilders.termQuery("phone", identity));
			boolQueryBuilder.should(QueryBuilders.termQuery("mobilePhone", identity));			
			boolQueryBuilder.should(QueryBuilders.termQuery("telePhone", identity));		
		}
		List<Map<String, Object>> list = esService.readDataListByCondition(index, type, boolQueryBuilder, true);
		for (Map<String, Object> map : list) {
			map.remove("index");
			map.remove("type");
			map.remove("源文件");
			map.remove("录入时间");
			map.remove("插入时间");
		}
		System.out.println(list.size());
		return list;
	}	
}
