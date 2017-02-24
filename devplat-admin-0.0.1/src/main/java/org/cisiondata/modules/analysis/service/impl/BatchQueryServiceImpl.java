package org.cisiondata.modules.analysis.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
	@Override
	public  void updateStatistics(String type, String fileName) throws BusinessException {
		//获取全部数据
		List<EventExtend> list=eventExtendDAO.readEventInfo(fileName);
		@SuppressWarnings("unused")
		Gson gson = new Gson(); 
		int now=0;
		int sunSize=1000;
		ExecutorService service = Executors.newCachedThreadPool();
		 for (int i = 0; i <list.size(); i++) {
			   //获取起始位置
			   now=i*sunSize;
			   //计算结束位置
			   int formSize=now+sunSize+1;
			   //如果起始位置已经大于数据列总长,则说明已经添加完了.
			   if(now>list.size()){
				   break;
			   }
			   //如果结束位置等于总长则置为总长
			   if(formSize>list.size()){
				   formSize=list.size();
			   }
			   List<EventExtend> sunList=list.subList(now,formSize);
			   service.submit(new Producer(type, sunList));  	   
		} 
		 	service.shutdown();
			try {
				service.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
		}
	
	 class Producer implements Runnable {
		 
	        private List<EventExtend> sunList = null;
	
	        private String type = null;
	    	
	    	private Gson gson = null;

	        public Producer(String type, List<EventExtend> sunList) {
	            this.sunList = sunList;
	            this.type=type;
	            this.gson = new Gson();
	        }
	        @SuppressWarnings("unchecked")
			public void run() {
	            try {
	                	if("phone".equals(type)){ 
	    			 
	    			            for (int i = 0; i < sunList.size(); i++) {
	    			             EventExtend eventExtend =sunList.get(i);
	    			             Map<String, Object> map = gson.fromJson(eventExtend.getExtendInfo1(), Map.class);
	    			                System.out.println(map);
	    			                String mobilePhone = String.valueOf(map.get("电话号码"));
	    			                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
	    			                Set<String> identityAttributes=esService.readIdentityAttributes();
	    			                for (String identityAttribute:identityAttributes) {
	    			                    boolQueryBuilder.should(QueryBuilders.termQuery(identityAttribute, mobilePhone));
	    			                }
	    			                List<Map<String,Object>> resultList = esService.readDataListByCondition(boolQueryBuilder,false);
	    			                
	    			                System.out.println("匹配到的正确数据"+resultList);
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
	    			           	 
	    			                for (int i = 0; i < sunList.size(); i++) {
	    			                    EventExtend eventExtend = sunList.get(i);
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
	    			            	for (int i = 0; i < sunList.size(); i++) {
	    			                    EventExtend eventExtend = sunList.get(i);
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
	    			            }else if("company".equals(type)){
	    			            	for (int i = 0; i < sunList.size(); i++) {
	    			                    EventExtend eventExtend = sunList.get(i);
	    			                    Map<String, Object> map = gson.fromJson(eventExtend.getExtendInfo1(), Map.class);
	    			                    System.out.println(map);
	    			                    String mailbox = String.valueOf(map.get("公司信息"));
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
	                    Thread.sleep(500);  
	                
	            } catch (InterruptedException e1) {
	                e1.printStackTrace();
	            }

	        }
	    }					

	@SuppressWarnings("unchecked")
	@Override
	public List<String> Classifi(String Classification, String index, String type,String filename) throws BusinessException {
		//获取原数据
		List<EventExtend> list =eventExtendDAO.readEventInfo(filename);
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
        }else if("company".equals(type)){
        	for (int i = 0; i < list.size(); i++) {
                EventExtend eventExtend = list.get(i);
                Map<String, Object> map = gson.fromJson(eventExtend.getExtendInfo1(), Map.class);
                System.out.println(map);
                String mailbox = String.valueOf(map.get("公司信息"));
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
	//统计
	@Override
	public int hitRate(String filename) {
		int ID=eventExtendDAO.findbaseID(filename);
		int hitRate=eventExtendDAO.hitRate(ID);
		return hitRate;
	}
	//全局匹配结果
	@Override
	public List<String> result(String fileName) {
		
		//获得的名字下面绑定的ID值
		int ID=eventExtendDAO.findbaseID(fileName);
		//更新字段
		//上传数据
		int uploadStatistics=eventExtendDAO.uploadStatistics(ID);
		while (eventExtendDAO.statisIdentification(ID)<uploadStatistics) {
			
		}
		List<String>result=eventExtendDAO.readall(ID);	
		return result;
	}
	
	
}
