package org.cisiondata.modules.analysis.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.abstr.service.impl.GenericServiceImpl;
import org.cisiondata.modules.analysis.dao.EventExtendDAO;
import org.cisiondata.modules.analysis.entity.EventExtend;
import org.cisiondata.modules.analysis.service.IBatchQueryService;
import org.cisiondata.modules.elasticsearch.service.IESService;
import org.cisiondata.utils.exception.BusinessException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service("batchQueryService")
public class BatchQueryServiceImpl extends GenericServiceImpl<EventExtend, Long> implements IBatchQueryService{
	
	@Resource(name="eventExtendDAO")
	private EventExtendDAO eventExtendDAO = null;
	
	@Resource(name = "esService")
	private IESService esService = null;	
	@Override
	public GenericDAO<EventExtend, Long> obtainDAOInstance() {
		return eventExtendDAO;
	}
	//全局匹配操作
	@SuppressWarnings("unchecked")
	@Override
	public List<String> updateStatistics(String type, String fileName) throws BusinessException {
		
		//获取全部数据
		List<EventExtend> list=eventExtendDAO.readEventInfo(fileName);
		Gson gson = new Gson(); 
		//判断改变状态
		if(list!=null && list.size() > 0){
			int code=eventExtendDAO.findbaseID(fileName);
			System.out.println(code);
			int updateIdentity=eventExtendDAO.codes(code);
			if(updateIdentity>0){
				System.out.println("执行成功！");
			}
		}
		if("phone".equals(type)){ 
			       	System.out.println(type+"手机");
			            for (int i = 0; i < list.size(); i++) {
			             EventExtend eventExtend =list.get(i);
			             Map<String, Object> map = gson.fromJson(eventExtend.getExtendInfo1(), Map.class);
			                System.out.println(map);
			                String mobilePhone = String.valueOf(map.get("电话号码"));
			                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			                Set<String> identityAttributes=esService.readIdentityAttributes();
			                for (String identityAttribute:identityAttributes) {
			                    boolQueryBuilder.should(QueryBuilders.termQuery(identityAttribute, mobilePhone));
			                }
			                List<Map<String,Object>> resultList = esService.readDataListByCondition(boolQueryBuilder,false);
			                
			                System.out.println("数据"+resultList);
			                //移除元素
			                for (Map<String, Object>  rmes : resultList) {
			                    rmes.remove("index");
			                    rmes.remove("type");
			                    rmes.remove("插入时间");
			                    rmes.remove("录入时间");
			                    rmes.remove("源文件");
			                }
			                //转换写入
			                String resultListJson = gson.toJson(resultList);
			                if(resultList !=null){
			                    eventExtendDAO.Updata(resultListJson,eventExtend.getEventId());
			                }
			               } 
			            }else if("idCard".equals(type)){                	 
			           	 System.out.println(type+"身份证");
			                for (int i = 0; i < list.size(); i++) {
			                    EventExtend eventExtend = list.get(i);
			                    Map<String, Object> map = gson.fromJson(eventExtend.getExtendInfo1(), Map.class);
			                    System.out.println(map);
			                    String idCard = String.valueOf(map.get("身份证号码"));
			                    BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			                    Set<String> identityAttributes=esService.readIdentityAttributes();
			                    for (String identityAttribute:identityAttributes) {
			                        boolQueryBuilder.should(QueryBuilders.termQuery(identityAttribute, idCard));    
			                    }
			                    List<Map<String,Object>> resultList = esService.readDataListByCondition(boolQueryBuilder,false);
			                    //遍历去掉高亮
			                    for(Map<String, Object>  rmes : resultList){
			                        rmes.remove("index");
			                        rmes.remove("type");
			                        rmes.remove("插入时间");
			                        rmes.remove("录入时间");
			                        rmes.remove("源文件");
			                    }
			                    String resultListJson = gson.toJson(resultList);
			                    if(resultList !=null){
			                        eventExtendDAO.Updata(resultListJson,eventExtend.getEventId());
			                    } 
			                }
			            }else if("mailbox".equals(type)){
			            	for (int i = 0; i < list.size(); i++) {
			                    EventExtend eventExtend = list.get(i);
			                    Map<String, Object> map = gson.fromJson(eventExtend.getExtendInfo1(), Map.class);
			                    System.out.println(map);
			                    String mailbox = String.valueOf(map.get("邮箱地址"));
			                    BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			                    Set<String> identityAttributes=esService.readIdentityAttributes();
			                    for (String identityAttribute:identityAttributes) {
			                        boolQueryBuilder.should(QueryBuilders.termQuery(identityAttribute, mailbox));    
			                    }
			                    List<Map<String,Object>> resultList = esService.readDataListByCondition(boolQueryBuilder,false);
			                    //遍历去掉高亮
			                    for(Map<String, Object>  rmes : resultList){
			                        rmes.remove("index");
			                        rmes.remove("type");
			                        rmes.remove("插入时间");
			                        rmes.remove("录入时间");
			                        rmes.remove("源文件");
			                    }
			                    String resultListJson = gson.toJson(resultList);
			                    if(resultList !=null){
			                        eventExtendDAO.Updata(resultListJson,eventExtend.getEventId());
			                    } 
			                }				            	
			            }
			//读取数据库的数据 存入result 直接返回给前台
			//匹配名字，获得对应的ID值
			int ID=eventExtendDAO.findbaseID(fileName);
			List<String> result =eventExtendDAO.readall(ID);
			return result;
		}
					

      

	@SuppressWarnings("unchecked")
	@Override
	public List<String> Classifi(String Classification, String index, String type,String filename) throws BusinessException {
		//获取原数据
		List<EventExtend> list =eventExtendDAO.readEventInfo(filename);
		if(list!=null){
			int code=eventExtendDAO.findbaseID(filename);
			int updateIdentity=eventExtendDAO.codes(code);
			if(updateIdentity>0){
				System.out.println("执行成功！");
			}
			System.out.println(updateIdentity);
		}
		Gson gson = new Gson();
		if("phone".equals(Classification)){
			for (int i = 0; i < list.size(); i++) {
				EventExtend eventExtend = list.get(i);
				Map<String, Object> map = gson.fromJson(eventExtend.getExtendInfo1(), Map.class);
				System.out.println(map);
				String idCard = String.valueOf(map.get("电话号码"));
				BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
				Set<String> identityAttributes=esService.readIdentityAttributes();
				for (String identityAttribute:identityAttributes) {
					boolQueryBuilder.should(QueryBuilders.termQuery(identityAttribute, idCard));	
				}
				List<Map<String,Object>> resultList = esService.readDataListByCondition(index,type,boolQueryBuilder,false);
				System.out.println(resultList);
				//遍历去掉高亮
				for(Map<String, Object>  rmes : resultList){
					rmes.remove("index");
					rmes.remove("type");
					rmes.remove("插入时间");
					rmes.remove("录入时间");
					rmes.remove("源文件");
				}
				String resultListJson = gson.toJson(resultList);
				if(resultList !=null){
					eventExtendDAO.Updata(resultListJson,eventExtend.getEventId());
				}
			}
		}else if("idCard".equals(Classification)){
			for (int i = 0; i < list.size(); i++) {
				EventExtend eventExtend = list.get(i);
				Map<String, Object> map = gson.fromJson(eventExtend.getExtendInfo1(), Map.class);
				System.out.println(map);
				String idCard = String.valueOf(map.get("身份证号码"));
				BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
				Set<String> identityAttributes=esService.readIdentityAttributes();
				for (String identityAttribute:identityAttributes) {
					boolQueryBuilder.should(QueryBuilders.termQuery(identityAttribute, idCard));	
				}
				List<Map<String,Object>> resultList = esService.readDataListByCondition(index,type,boolQueryBuilder,false);
				System.out.println(resultList);
				//遍历去掉高亮
				for(Map<String, Object>  rmes : resultList){
					rmes.remove("index");
					rmes.remove("type");
					rmes.remove("插入时间");
					rmes.remove("录入时间");
					rmes.remove("源文件");
				}
				String resultListJson = gson.toJson(resultList);
				if(resultList !=null){
					eventExtendDAO.Updata(resultListJson,eventExtend.getEventId());
				}
			}
		}else if("mailbox".equals(type)){
        	for (int i = 0; i < list.size(); i++) {
                EventExtend eventExtend = list.get(i);
                Map<String, Object> map = gson.fromJson(eventExtend.getExtendInfo1(), Map.class);
                System.out.println(map);
                String mailbox = String.valueOf(map.get("邮箱地址"));
                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                Set<String> identityAttributes=esService.readIdentityAttributes();
                for (String identityAttribute:identityAttributes) {
                    boolQueryBuilder.should(QueryBuilders.termQuery(identityAttribute, mailbox));    
                }
                List<Map<String,Object>> resultList = esService.readDataListByCondition(boolQueryBuilder,false);
                //遍历去掉高亮
                for(Map<String, Object>  rmes : resultList){
                    rmes.remove("index");
                    rmes.remove("type");
                    rmes.remove("插入时间");
                    rmes.remove("录入时间");
                    rmes.remove("源文件");
                }
                String resultListJson = gson.toJson(resultList);
                if(resultList !=null){
                    eventExtendDAO.Updata(resultListJson,eventExtend.getEventId());
                } 
            }				            	
        }
		//读取数据库的数据 存入result 直接返回给前台
		//匹配名字，获得对应的ID值
		int ID=eventExtendDAO.findbaseID(filename);
		List<String> result =eventExtendDAO.readall(ID);
		return result;
	}
	
	
}
