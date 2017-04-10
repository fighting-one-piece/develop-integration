package org.cisiondata.modules.identity.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.auth.entity.ResourceInterfaceField;
import org.cisiondata.modules.identity.service.IQQService;
import org.cisiondata.modules.search.service.IESBizService;
import org.cisiondata.modules.search.util.FieldsUtils;
import org.cisiondata.modules.user.service.IResourceService;
import org.cisiondata.utils.exception.BusinessException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

@Service("qqService")
public class QQServiceImpl implements IQQService {
	
	@Resource(name = "esBizService")
	private IESBizService esBizService = null;
	@Resource(name = "aresourceService")
	private IResourceService resourceService = null;
	//根据QQ号码得到群详情和群昵称
	@Override
	public Map<String, Object> readQQDatas(String qq) throws BusinessException {
		String  regex ="[1-9][0-9]{4,14}";
		Pattern p = Pattern.compile(regex);  
		Matcher m = p.matcher(qq); 
		List<String> listqun = new ArrayList<String>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		QueryResult<Map<String, Object>> qr = new QueryResult<Map<String, Object>>();
		if(m.matches()){
			List<String> listQ = AllList(qq);
			if (listQ.size() > 0) {
				BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
				for (int i = 0; i < listQ.size(); i++) {
					boolQueryBuilder.should(QueryBuilders.termQuery("qunNum", listQ.get(i)));
				}
				listMap = esBizService.readDataListByCondition("qq", "qqqundata", boolQueryBuilder, true);
				for (int i = 0; i < listMap.size(); i++) {
					listqun.add(listMap.get(i).get("qunNum").toString());
					list.add(listMap.get(i));
				}
				// 开始
				if (listqun.size() > 0) {
					for (int i = 0; i < listqun.size(); i++) {
						BoolQueryBuilder qb = QueryBuilders.boolQuery();
						qb.must(QueryBuilders.termQuery("qunNum", listqun.get(i)))
						.must(QueryBuilders.termQuery("qqNum", qq));
						List<Map<String, Object>> listnick = esBizService.readDataListByCondition(
								"qq", "qqqunrelation", qb, true);
						for (int j = 0; j < listnick.size(); j++) {
							list.get(i).put("nick", listnick.get(j).get("nick"));
						}
					}
				}
			}else{
				throw new BusinessException(ResultCode.NOT_FOUNT_DATA.getCode(),ResultCode.NOT_FOUNT_DATA.getDesc());
			}
			for (int i = 0,len = list.size(); i < len; i++) {
				list.get(i).remove("sourceFile");
				list.get(i).remove("index");
				list.get(i).remove("type");
				list.get(i).remove("clazz");
			}
			List<ResourceInterfaceField> lists = resourceService.findAttributeByIdentity("QQController_readQQqunData");
			if (null == lists || lists.size()==0) throw new BusinessException(ResultCode.DATABASE_READ_FAIL);
			Map<String,String> fieldsMap = FieldsUtils.getFieldsMessageSource(lists);
			qr.setResultList(list);
			qr = FieldsUtils.filterQueryResultByFields(qr, lists);
			map.put("data", qr);
			map.put("head", fieldsMap);
		}else{
			throw new BusinessException(ResultCode.PARAM_ERROR);
		}
		if(list.size() == 0){
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA.getCode(),ResultCode.NOT_FOUNT_DATA.getDesc());
		}
		return map;
	}
	//根据QQ群号获取QQ群名称、QQ群通知字段
	public Map<String, Object> readQQqun(String qunNum){
		Map<String, Object> map = new HashMap<String,Object>();
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.should(QueryBuilders.termQuery("qunNum", qunNum));
		listMap = esBizService.readDataListByCondition("qq", "qqqundata", boolQueryBuilder, true);
		for (int i = 0; i < listMap.size(); i++) {
			map.put("title", listMap.get(i).get("title").toString());
			map.put("qunText", listMap.get(i).get("qunText").toString());
		}
		return map;
	}
	// 根据QQ号获取QQ群号
	private List<String> AllList(String qq) {
		List<Map<String, Object>> listMap = esBizService.readDataListByCondition(
				"qq", "qqqunrelation", QueryBuilders.termQuery("qqNum", qq), true);
		List<String> list = new ArrayList<String>();
		for (int i = 0, len = listMap.size(); i < len; i++) {
			list.add(listMap.get(i).get("qunNum").toString());
		}
		return list;
	}

	@Override
	// 根据QQ群号得到对应的群信息
	public Map<String, Object> readQQqunDatas(String qunNum) {
		String  regex ="[1-9][0-9]{4,14}";
		Pattern p = Pattern.compile(regex);  
		Matcher m = p.matcher(qunNum);
		List<Map<String, Object>> list = esBizService.readDataListByCondition(
				"qq", "qqqunrelation", QueryBuilders.termQuery("qunNum", qunNum), true);
		Map<String,Object> maps = new HashMap<String,Object>();
		QueryResult<Map<String, Object>> qr = new QueryResult<Map<String, Object>>();
		if(m.matches()){
			Map<String, Object> map = readQQqun(qunNum);
			for (int i = 0; i < list.size(); i++) {
				list.get(i).remove("sourceFile");
				list.get(i).remove("index");
				list.get(i).remove("type");
				for (Map.Entry<String, Object> entry:map.entrySet()) {
					list.get(i).put(entry.getKey(), entry.getValue());
				}
			}
			List<ResourceInterfaceField> lists = resourceService.findAttributeByIdentity("QQController_readQQqunDatas");
			if (null == lists || lists.size()==0) throw new BusinessException(ResultCode.DATABASE_READ_FAIL);
			Map<String,String> fieldsMap = FieldsUtils.getFieldsMessageSource(lists);
			qr.setResultList(list);
			qr = FieldsUtils.filterQueryResultByFields(qr, lists);
			maps.put("data", qr);
			maps.put("head", fieldsMap);
		}else{
			throw new BusinessException(ResultCode.PARAM_ERROR);
		}
		if(list.size() == 0){
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA.getCode(),ResultCode.NOT_FOUNT_DATA.getDesc());
		}
		return maps;
	}

	@Override
	// 根据QQ号得到基本信息
	public Map<String, Object> readQQData(String qq) throws BusinessException {
		String  regex ="[1-9][0-9]{4,14}";
		Pattern p = Pattern.compile(regex);  
		Matcher m = p.matcher(qq); 
		List<Map<String, Object>> list = esBizService.readDataListByCondition(
				"qq", "qqdata", QueryBuilders.termQuery("qqNum", qq), true);
		Map<String,Object> maps = new HashMap<String,Object>();
		QueryResult<Map<String, Object>> qr = new QueryResult<Map<String, Object>>();
		if(m.matches()){
			for (int i = 0; i < list.size(); i++) {
				list.get(i).remove("sourceFile");
				list.get(i).remove("index");
				list.get(i).remove("type");
			}
			List<ResourceInterfaceField> lists = resourceService.findAttributeByIdentity("QQController_readQQ");
			if (null == lists || lists.size()==0) throw new BusinessException(ResultCode.DATABASE_READ_FAIL);
			Map<String,String> fieldsMap = FieldsUtils.getFieldsMessageSource(lists);
			qr.setResultList(list);
			qr = FieldsUtils.filterQueryResultByFields(qr, lists);
			maps.put("data", qr);
			maps.put("head", fieldsMap);
		}else{
			throw new BusinessException(ResultCode.PARAM_ERROR);
		}
		if(list.size() == 0){
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA.getCode(),ResultCode.NOT_FOUNT_DATA.getDesc());
		}
		return maps;
	}

	//通过QQ昵称查询
	@Override
	@SuppressWarnings("unchecked")
	public QueryResult<Map<String, Object>> readQQNickData(String nick, String scrollId, int rowNumPerPage) {
		if (! StringUtils.isNotBlank(nick)) {
			throw new BusinessException(ResultCode.PARAM_NULL);
		}
		QueryResult<Map<String, Object>> qr = esBizService.readPaginationDataListByCondition("qq", "qqqunrelation", 
				QueryBuilders.matchPhraseQuery("nick", nick), scrollId, rowNumPerPage, true);
		List<Map<String, Object>> resultList = qr.getResultList();
		for (int i = 0; i < resultList.size(); i++) {
			Map<String, Object> data =(Map<String, Object>) resultList.get(i).get("data");
			String qunNum = (String) data.get("qunNum");
			//通过qunNum获取群名称 群通知
			List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			boolQueryBuilder.should(QueryBuilders.termQuery("qunNum", qunNum));
			listMap = esBizService.readDataListByCondition("qq", "qqqundata", boolQueryBuilder, true);
			for (int j = 0; j < listMap.size(); j++) {
				data.put("群名称", listMap.get(j).get("title").toString());
				data.put("群通知", listMap.get(j).get("qunText").toString());
			}
		}
		return qr;
	}
	
}
