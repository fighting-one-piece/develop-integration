package org.cisiondata.modules.search.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.auth.entity.ResourceInterfaceField;
import org.cisiondata.modules.search.service.ILabelsService;
import org.cisiondata.modules.search.util.FieldsUtils;
import org.cisiondata.modules.user.service.IResourceService;
import org.cisiondata.utils.endecrypt.MD5Utils;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.message.MessageUtils;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("labelsService")
public class LabelsServiceImpl extends ESBizServiceImpl implements ILabelsService,InitializingBean {
	
	@Resource(name = "aresourceService")
	private IResourceService resourceService;
	
	private ExecutorService executorService = Executors.newCachedThreadPool();
	
	/** 基础数据*/
	private List<String> basicData = new ArrayList<String>();
	/** 应用数据*/
	private List<String> applicationData = new ArrayList<String>();
	/** 地域数据*/
	private List<String> regionalData = new ArrayList<String>();
	
	private static final String NUM_REG = "\\d+";
	private static final String CN_REG = "[\\u4e00-\\u9fa5]+";
	
	@Override
	public void afterPropertiesSet() throws Exception {
		basicData.add("电信运营商");
		basicData.add("公积金信息");
		basicData.add("社保信息");
		basicData.add("社保信息");
		basicData.add("学生信息");
		basicData.add("老人信息");
		
		applicationData.add("QQ信息");
		applicationData.add("QQ群信息");
		applicationData.add("QQ群关系");
		applicationData.add("金融");
		applicationData.add("工作简历信息");
		applicationData.add("博彩数据");
		applicationData.add("账号信息");
		applicationData.add("台湾账号信息");
		applicationData.add("京东账号信息");
		
		regionalData.add("物流");
		regionalData.add("工商");
		regionalData.add("通讯录信息");
		regionalData.add("旅馆信息");
		regionalData.add("车主信息");
		regionalData.add("物业楼盘");
		regionalData.add("资格考试");
		regionalData.add("网吧信息");
		regionalData.add("航空信息");
		regionalData.add("医院信息");
		regionalData.add("保健品信息");
		regionalData.add("母婴信息");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> readLabelsAndHitsAndClassifiedIncludeTypes(String query, String... includeTypes)
			throws BusinessException {
		if (null == includeTypes || includeTypes.length == 0) includeTypes = defaultTypes();
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> basicList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> applicationList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> regionalList = new ArrayList<Map<String, Object>>();
		String labelsHitCacheKey = genLabelHitCacheKey(query, includeTypes);
		Object cacheObject = RedisClusterUtils.getInstance().get(labelsHitCacheKey);
		if (null != cacheObject) {
			List<Map<String, Object>> resultList = (List<Map<String, Object>>) cacheObject;
			if(resultList.size() == 0) throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
			for (Map<String,Object> map : resultList) {
				if(regionalData.contains((String)map.get("label"))){
					regionalList.add(map);
				} else if(applicationData.contains((String)map.get("label"))){
					applicationList.add(map);
				} else if(basicData.contains((String)map.get("label"))){
					basicList.add(map);
				}
			}
			resultMap.put("基础数据", basicList);
			resultMap.put("应用数据", applicationList);
			resultMap.put("地域数据", regionalList);
			return resultMap;
//			return (List<Map<String, Object>>) cacheObject;
		}
		LOG.info("read labels and hits method with cache not hit");
		Map<String, Future<Map<String, Object>>> fmap = new HashMap<String, Future<Map<String, Object>>>();
		for (int i = 0, len = includeTypes.length; i < len; i++) {
			String type = includeTypes[i];
			fmap.put(type, executorService.submit(new ReadLabelsAndHitsThread(
					type_index_mapping.get(type), type, query)));
		}
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (Map.Entry<String, Future<Map<String, Object>>> entry : fmap.entrySet()) {
			Future<Map<String, Object>> future = entry.getValue();
			Map<String, Object> result = null;
			try {
				result = future.get(20, TimeUnit.SECONDS);
			} catch (Exception e) {
				result = new HashMap<String, Object>();
				String type = entry.getKey();
				String index = type_index_mapping.get(type);
				result.put("hits", -1);
				result.put("index", index);
				result.put("type", type);
				result.put("label", MessageUtils.getInstance().getMessage(index + "." + type));
				LOG.error(e.getMessage(), e);
			}
			if (!result.isEmpty()) resultList.add(result);
		}
		RedisClusterUtils.getInstance().set(labelsHitCacheKey, resultList, 180);
		if(resultList.size() == 0) throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		for (Map<String,Object> map : resultList) {
			if(regionalData.contains((String)map.get("label"))){
				regionalList.add(map);
			} else if(applicationData.contains((String)map.get("label"))){
				applicationList.add(map);
			} else if(basicData.contains((String)map.get("label"))){
				basicList.add(map);
			}
		}
		resultMap.put("基础数据", basicList);
		resultMap.put("应用数据", applicationList);
		resultMap.put("地域数据", regionalList);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> readPaginationDataListByCondition(HttpServletRequest req,String index, String type, String query,
			String scrollId, int size) throws BusinessException {
		if(StringUtils.isBlank(query)) throw new BusinessException(ResultCode.PARAM_NULL);
		query = query.trim();
		if (!isParamterLegal(query)) throw new BusinessException(ResultCode.PARAM_ERROR);
		QueryResult<Map<String,Object>> qr = readPaginationDataListByCondition(index, type, query, scrollId, size, true);
		if(qr.getResultList().size() == 0) throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		Map<String,Object> map = new HashMap<String,Object>();
		
		
		
		List<ResourceInterfaceField> list = resourceService.findAttributeByUrl(req);
		if (null == list || list.size()==0) throw new BusinessException(ResultCode.DATABASE_READ_FAIL);
		Map<String,String> fieldsMap = FieldsUtils.getFieldsMessageSource(list);
		qr = FieldsUtils.filterQueryResultByFields(qr, list);
		
		map.put("data", qr);
		map.put("head", fieldsMap);
		return map;
	}
	
	/**
	 * 判断参数是否合法，数字大于5位，中文大于1位
	 * @return true合法 false不合法
	 */
	private boolean isParamterLegal(String query){
		query = query.trim();
		if (query.matches(NUM_REG)) {
			if(query.length() < 5) return false;
		} else if(query.matches(CN_REG)){
			if (query.length() < 1) return false;
		}
		return true;
	}

	private String genLabelHitCacheKey(String query, String... types) {
		StringBuilder sb = new StringBuilder(100).append(query);
		for (int i = 0, len = types.length; i < len; i++) {
			sb.append(types[i]);
		}
		return "labels:hit:" + MD5Utils.hash(sb.toString());
	}



}
