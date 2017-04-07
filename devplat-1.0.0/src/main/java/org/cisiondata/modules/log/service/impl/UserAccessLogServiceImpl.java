package org.cisiondata.modules.log.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.auth.web.WebUtils;
import org.cisiondata.modules.log.dao.UserAccessLogDAO;
import org.cisiondata.modules.log.entity.UserAccessLog;
import org.cisiondata.modules.log.service.IUserAccessLogService;
import org.cisiondata.modules.queue.entity.MQueue;
import org.cisiondata.modules.queue.entity.RequestMessage;
import org.cisiondata.modules.queue.service.impl.ConsumerServiceImpl;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service("userAccessLogService")
public class UserAccessLogServiceImpl extends ConsumerServiceImpl implements IUserAccessLogService{
	@Resource(name = "userAccessLogDAO")
	private UserAccessLogDAO userAccessLogDAO;
	
	public Map<String,Object> selAccessLog(String params, Date startTime, Date endTime, int index,int pageSize) throws BusinessException{
		QueryResult<Map<String,Object>> result = new QueryResult<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String,Object>();
		Map<String,Object> maps = new HashMap<String,Object>();
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String, String> mapHead = new HashMap<String,String>();
		mapHead.put("total", "数据信息总计");
		mapHead.put("accessTime", "查询时间");
		mapHead.put("params", "关键字");
		mapHead.put("ip", "IP地址");
		String startDate = null;
		String endDate = null;
		if(startTime != null && endTime != null){
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			startDate = sdf.format(startTime);  
			endDate = sdf.format(endTime);  
		}
		if(index > 0){
			int pageCount = 0;
			int page = (index - 1) * pageSize; 
			if(StringUtils.isNotBlank(WebUtils.getCurrentAccout())){
				String account = WebUtils.getCurrentAccout();
				map.put("account", account);
				map.put("params", params);
				map.put("startTime", startDate);
				map.put("endTime", endDate);
				map.put("startPos", page);
				map.put("pageSize", pageSize);
				maps.put("account", account);
				maps.put("params", params);
				maps.put("startTime", startDate);
				maps.put("endTime", endDate);
				List<UserAccessLog> lists = userAccessLogDAO.selCount(maps);
				List<UserAccessLog> list = userAccessLogDAO.selCount(map);
				if(lists != null && lists.size() > 0){
					pageCount = lists.size() % pageSize == 0 ? lists.size() / pageSize : lists.size() / pageSize + 1;
				}
				if(list.size() > 0){
					List<Map<String,Object>>  listMap = new ArrayList<Map<String,Object>>();
					for (int i = 0,len = list.size(); i < len; i++) {
						Map<String,Object> mapList = new HashMap<String,Object>();
						mapList.put("ip", list.get(i).getIp());
						mapList.put("params", list.get(i).getParams());
						mapList.put("accessTime",list.get(i).getAccessTime());
						mapList.put("total", list.get(i).getTotal());
						listMap.add(mapList);
					}
					result.setTotalRowNum(pageCount);
					result.setResultList(listMap);
					mapResult.put("data", result);
					mapResult.put("head", mapHead);
				}else{
					throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
				}
			}else{
				throw new BusinessException(ResultCode.ACCOUNT_NOT_EXIST);
			}
		}else{
			throw new BusinessException(ResultCode.PARAM_ERROR);
		}
		
		return mapResult;
	}

	@Override
	protected String getRoutingKey() {
		return MQueue.REQUEST_ACCESS_QUEUE.getRoutingKey();
	}

	@Override
	public void handleMessage(Object message) {
		if (message instanceof RequestMessage) {
			RequestMessage requestMessage = (RequestMessage) message;
			if(judgeUrl(requestMessage.getUrl())){
				Map<String,String> map = requestMessage.getParams();
				String params = map.get("query");
				if(StringUtils.isNotBlank(params)){
					try {
						UserAccessLog log = new UserAccessLog();
						Gson gson = new Gson();
						Object result = requestMessage.getReturnResult();
						log.setIp(requestMessage.getIpAddress());
						log.setParams(params);
						log.setTotal(String.valueOf(this.parseReturnResultCount(result)));
						log.setUrl(requestMessage.getUrl());
						log.setAccount(requestMessage.getAccount());
						log.setAccessTime(requestMessage.getTime());
						log.setResult(gson.toJson(result));
						userAccessLogDAO.addAccessLog(log);
					} catch (Exception e) {
						e.getMessage();
					}
				}
			}
		}
	}
	//过滤请求
	private Boolean judgeUrl(String url){
		if(url.equals("/login")) return false;
		if(url.equals("/userAccessLogs")) return false;
		if(url.indexOf("/users/") != -1) return false;
		return true;
	}
	//获取条数
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private long parseReturnResultCount(Object result){
		if(result instanceof WebResult){
			WebResult webResult = (WebResult) result;
			Object data = webResult.getData();
			if(data instanceof List){
				return ((List)data).size();
			}else if(data instanceof QueryResult){
				QueryResult queryResult = (QueryResult) data;
				return queryResult.getResultList().size();
			}else if(data instanceof Map){
				Map<String,Object> map = (Map<String, Object>) data;
				long count = 0;
				for(Map.Entry<String, Object> entry : map.entrySet()){
					Object value = entry.getValue();
					if(value instanceof List){
						count += ((List) value).size();
					}
					if(entry.getKey().equals("data")){
						Object values = entry.getValue();
						if(values instanceof QueryResult){
							QueryResult queryResult = (QueryResult) values;
							return queryResult.getResultList().size();
						}
					}
				}
				return count;
			}
		}
		return 0;
	}
}
