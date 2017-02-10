package org.cisiondata.modules.analysis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
	@Override
	public List<String> updateStatistics(String type, String fileName) throws BusinessException {
		
		//获取全部数据
		List<EventExtend> list =eventExtendDAO.readEventInfo(fileName);
		//判断改变状态
		if(list!=null){
			//替代之前的多线程操作
		    ExecuteTask exec=new ExecuteTask();
		    exec.init(list, type);
		    boolean isSec=exec.doMethod();
			if(isSec){
				System.out.println("执行完毕！");
			}
			int code=eventExtendDAO.findbaseID(fileName);
			int updateIdentity=eventExtendDAO.codes(code);
			if(updateIdentity>0){
				System.out.println("执行成功！");
			}
			System.out.println(updateIdentity);
		}
		
		//读取数据库的数据 存入result 直接返回给前台
		//匹配名字，获得对应的ID值
		int ID=eventExtendDAO.findbaseID(fileName);
		List<String> result =eventExtendDAO.readall(ID);
		return result;
	}
	
     class ExecuteTask{
         //待执行列表
         List<EventExtend> allList=new ArrayList<>();
         //每次最多多少个线程去跑数据
         int threadSize=5;
         //每次线程最多跑多少数据
         int sunSize=1000;
         //当前装载起始位置
         int now=0;
         //线程池,这个作用是先完成数据装配 最后统一执行,也作为执行完毕的标志,线程执行完了将在池中删除自身
         Map<String,Thread> pool=new HashMap<>();
         
         String type=null;
         //初始化数据
         void init(List<EventExtend> ss,String t){
        	 this.type=t;
             this.allList=ss;
         }
         
         //采用递归的方式来执行,每次先分配数据,分配完了执行
       
         boolean  doMethod(){
             //进行数据分配
             for (int i = 0; i < threadSize; i++) {
                 //获取起始位置
                 now=i*sunSize;
                 //计算结束位置
                 int formSize=now+sunSize;
                 //如果起始位置已经大于数据列总长,则说明已经添加完了.
                 if(now>allList.size()){
                     break;
                 }
                 //如果结束位置超出总长则置为总长
                 if(formSize>allList.size()){
                     formSize=allList.size();
                 }
                 //添加数据
                 List<EventExtend> sunList=allList.subList(now,formSize);
                 
                 //添加待执行线程
                 pool.put(String.valueOf(i),new Thread(new runTh(i,sunList,pool,type)));
             }
             //统一执行
             Iterator<Map.Entry<String,Thread>> it = pool.entrySet().iterator();  
             while(it.hasNext()){  
                 Map.Entry<String,Thread> entry=it.next();  
                   entry.getValue().start();
             }
             //阻塞主线程,直到池中所有子线程执行完毕
             boolean isSec=false;
             while(!isSec){
                 if(pool.isEmpty()){
                     isSec=true;
                 }
             }
             //当所有子线程都执行完毕了但是数据列还没跑完则递归当前方法;
             if(now<allList.size()){
                 return doMethod();
             }else{
                 return true;
             }
         }
      }
      
     class runTh implements Runnable{
          //当前标志,用于输出时区分
         int id;
         //分配到的数据列
         List<EventExtend> list = null;
         //线程池的引用
         Map<String,Thread> pool=null;
         
         String type=null;
         
         
         public runTh(int i,List<EventExtend> l, Map<String,Thread> p,String t) {
             this.id=i;
             this.list = l;
             this.pool=p;
             this.type=t;
         }

         @SuppressWarnings("unchecked")
		@Override
         public void run() {
         Gson gson = new Gson(); 

             if("phone".equals(type)){   
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
                 //执行完毕后通过线程池的引用删除自身在线程池中的引用
                 }else if("idCard".equals(type)){
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
                 }
                 pool.remove(String.valueOf(id));
             }
     }

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
				@SuppressWarnings("unchecked")
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
				@SuppressWarnings("unchecked")
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
		}
		//读取数据库的数据 存入result 直接返回给前台
		//匹配名字，获得对应的ID值
		int ID=eventExtendDAO.findbaseID(filename);
		List<String> result =eventExtendDAO.readall(ID);
		return result;
	}
	
	
}
