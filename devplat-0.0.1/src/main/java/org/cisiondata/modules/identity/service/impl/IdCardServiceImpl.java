package org.cisiondata.modules.identity.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.datainterface.service.IDatadaService;
import org.cisiondata.modules.datainterface.service.ILMInternetService;
import org.cisiondata.modules.elasticsearch.service.IESService;
import org.cisiondata.modules.identity.service.IIdCardService;
import org.cisiondata.utils.exception.BusinessException;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("idCardService")
public class IdCardServiceImpl implements IIdCardService {
	@Resource(name = "esService")
	private IESService esService = null;
	@Autowired
	private IDatadaService datadaService = null;
	@Resource(name = "lmInternetService")
	private ILMInternetService lmService = null;

	@Override
	public List<Map<String, Object>> readCardDatas(String idcard) {
		// 定义判别身份证号的正则表达式
		Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
		// 通过Pattern获得Matcher
		Matcher idNumMatcher = idNumPattern.matcher(idcard);
		List<Map<String, Object>> list = null;
		// 判断是否为身份证号
		if (idNumMatcher.matches()) {
			String[] type = { "student", "resume" };
			list = esService.readDataListByCondition("work", type, QueryBuilders.prefixQuery("idCard", idcard));
			List<Map<String, Object>> listmap = datadaService.readDatadaDatas(idcard);
			if (listmap.size() > 0) {
				for (int j = 0; j < listmap.size(); j++) {
					list.add(listmap.get(j));
				}
			}
			for (int i = 0; i < list.size(); i++) {
				list.get(i).remove("更新时间");
				list.get(i).remove("录入时间");
				list.get(i).remove("源文件");
				list.get(i).remove("index");
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> readCard(String name, String idcard,String university) throws Exception {
		// 定义判别身份证号的正则表达式
		Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
		// 通过Pattern获得Matcher
		Matcher idNumMatcher = idNumPattern.matcher(idcard);
		List<Map<String, Object>> list = null;
		List<Map<String, Object>> listReslut = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> newListMap = new LinkedList<Map<String, Object>>();
		Set<Map<String, Object>> setMap = new HashSet<Map<String, Object>>();
		if(StringUtils.isNotBlank(idcard)){
			// 判断是否为身份证号
			if (idNumMatcher.matches()) {
				String[] type = { "student", "resume" };
				list = esService.readDataListByCondition("work", type, QueryBuilders.prefixQuery("idCard", idcard));
				List<Map<String, Object>> listmap = datadaService.readDatadaDatas(idcard);
				if (listmap.size() > 0) {
					for (int j = 0; j < listmap.size(); j++) {
						list.add(listmap.get(j));
					}
				}
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> s = list.get(i);
					Map<String, Object> ss = new HashMap<String, Object>();
					for (Map.Entry<String, Object> entry : s.entrySet()) {
						if (entry.getKey().equals("姓名")) {
							ss.put(entry.getKey(), entry.getValue());
						}
						if (entry.getKey().equals("学历")) {
							ss.put(entry.getKey(), entry.getValue());
						}
						if (entry.getKey().equals("入学时间")) {
							ss.put(entry.getKey(), entry.getValue());
						}
						if (entry.getKey().equals("毕业学校")) {
							ss.put(entry.getKey(), entry.getValue());
						}
						if (entry.getKey().equals("院系")) {
							ss.put(entry.getKey(), entry.getValue());
						}
						if (entry.getKey().equals("专业")) {
							ss.put(entry.getKey(), entry.getValue());
						}
					}
					// 判断Map是否有数据
					if (ss.size() == 6) {
						listReslut.add(ss);
					}
				}
				if(StringUtils.isNotBlank(name)){
					try {
						Map<String, Object> map = lmService.education_organizeD(name, idcard);
						Map<String, Object> map1 = (Map<String, Object>) map.get("checkResult");
						Map<String,Object> mapdegree = (Map<String, Object>) map1.get("degree");
						Map<String,Object> mappersonBase = (Map<String, Object>) map1.get("personBase");
						Map<String,Object> newMap = new HashMap<String,Object>();
						for (Map.Entry<String, Object> entry : mapdegree.entrySet()) {
							if(entry.getKey().equals("startTime")){
								newMap.put("入学时间", entry.getValue());
							}
							if(entry.getKey().equals("specialty")){
								newMap.put("专业", entry.getValue());
							}
							if(entry.getKey().equals("college")){
								newMap.put("毕业学校", entry.getValue());
							}
						}
						for (Map.Entry<String, Object> entry : mappersonBase.entrySet()) {
							if(entry.getKey().equals("name")){
								newMap.put("姓名", entry.getValue());
							}
							if(entry.getKey().equals("degree")){
								newMap.put("学历", entry.getValue());
							}
						}
						if(newMap.size() == 5){
							newMap.put("院系", "");
							newListMap.add(newMap);
						}
					} catch (Exception e) {
						e.getMessage();
					}
					
				}
			}else{
				throw new BusinessException(ResultCode.PARAM_ERROR);
			}
		}
		if(StringUtils.isNotBlank(university)){
			List<Map<String, Object>> lists = null;
			String[] type = { "student", "resume" };
			lists = esService.readDataListByCondition("work", type, QueryBuilders.boolQuery()
					.must(QueryBuilders.termQuery("name", name))
					.must(QueryBuilders.matchPhraseQuery("university", university)),false);
			if(lists.size() > 0){
				for (int j = 0,length = lists.size(); j < length; j++) {
					lists.get(j).remove("更新时间");
					lists.get(j).remove("录入时间");
					lists.get(j).remove("源文件");
					lists.get(j).remove("index");
					lists.get(j).remove("type");
				}
				for (int i = 0,len=lists.size(); i < len; i++) {
					Map<String, Object> maps = new HashMap<String, Object>();
					Map<String,Object> map = lists.get(i);
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						if (entry.getKey().equals("姓名")) {
							maps.put(entry.getKey(), entry.getValue());
						}
						if (entry.getKey().equals("学历")) {
							maps.put(entry.getKey(), entry.getValue());
						}
						if (entry.getKey().equals("入学时间")) {
							maps.put(entry.getKey(), entry.getValue());
						}
						if (entry.getKey().equals("毕业学校")) {
							maps.put(entry.getKey(), entry.getValue());
						}
						if (entry.getKey().equals("院系")) {
							maps.put(entry.getKey(), entry.getValue());
						}
						if (entry.getKey().equals("专业")) {
							maps.put(entry.getKey(), entry.getValue());
						}
					}
					if(maps.size()>0){
						if(!maps.containsKey("姓名")){
							maps.put("姓名", "");
						}
						if(!maps.containsKey("学历")){
							maps.put("学历", "");
						}
						if(!maps.containsKey("入学时间")){
							maps.put("入学时间", "");
						}
						if(!maps.containsKey("毕业学校")){
							maps.put("毕业学校", "");
						}
						if(!maps.containsKey("院系")){
							maps.put("院系", "");
						}
						if(!maps.containsKey("专业")){
							maps.put("专业", "");
						}
						listReslut.add(maps);
					}
				}
				if(listReslut.size() == 0){
					throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
				}
			}else{
				throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
			}
		}
		for (Map<String, Object> maps : listReslut) {
			if (setMap.add(maps)) {
				newListMap.add(maps);
			}
		}
		return newListMap;
	}

	

	//根据姓名、学校、学院、专业、学历、入学年份
	public List<Map<String, Object>> readESCard(String name, String university, String department, String major,String beginTime) {
		//判断参数是否为空
		List<Map<String, Object>> list = null;
		List<Map<String, Object>> listOne = null;
		List<Map<String, Object>> listReslut = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> newListMap = new LinkedList<Map<String, Object>>();
		Set<Map<String, Object>> setMap = new HashSet<Map<String, Object>>();
		if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(university) && StringUtils.isNotBlank(department) && StringUtils.isNotBlank(major) && StringUtils.isNotBlank(beginTime)){
			StringBuilder sb = new StringBuilder(department);
			sb.insert(0, "*");
			sb.insert(department.length()+1, "*");
			department = sb.toString();
			StringBuilder sb1 = new StringBuilder(major);
			sb1.insert(0, "*");
			sb1.insert(major.length()+1, "*");
			major = sb1.toString();
			list = esService.readDataListByCondition("work", "student", QueryBuilders.boolQuery()
					.must(QueryBuilders.termQuery("name", name))
					.must(QueryBuilders.matchPhraseQuery("university", university))
					.must(QueryBuilders.wildcardQuery("department", department))
					.must(QueryBuilders.matchPhrasePrefixQuery("beginTime", beginTime)),false);
			listOne = esService.readDataListByCondition("work", "student", QueryBuilders.boolQuery()
					.must(QueryBuilders.termQuery("name", name))
					.must(QueryBuilders.matchPhraseQuery("university", university))
					.must(QueryBuilders.wildcardQuery("major", major))
					.must(QueryBuilders.matchPhrasePrefixQuery("beginTime", beginTime)),false);
			if(list.size() > 0){
				for (int i = 0,len = list.size(); i < len; i++) {
					Map<String, Object> maps = new HashMap<String, Object>();
					Map<String,Object> map = list.get(i);
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						if (entry.getKey().equals("姓名")) {
							maps.put(entry.getKey(), entry.getValue());
						}
						if (entry.getKey().equals("身份证号")) {
							maps.put(entry.getKey(), entry.getValue());
						}
						if (entry.getKey().equals("学历")) {
							maps.put(entry.getKey(), entry.getValue());
						}
						if (entry.getKey().equals("入学时间")) {
							maps.put(entry.getKey(), entry.getValue());
						}
						if (entry.getKey().equals("毕业学校")) {
							maps.put(entry.getKey(), entry.getValue());
						}
						if (entry.getKey().equals("院系")) {
							maps.put(entry.getKey(), entry.getValue());
						}
						if (entry.getKey().equals("专业")) {
							maps.put(entry.getKey(), entry.getValue());
						}
					}
					if(maps.size()>0){
						if(!maps.containsKey("姓名")){
							maps.put("姓名", "");
						}
						if(!maps.containsKey("身份证号")){
							maps.put("身份证号", "");
						}
						if(!maps.containsKey("学历")){
							maps.put("学历", "");
						}
						if(!maps.containsKey("入学时间")){
							maps.put("入学时间", "");
						}
						if(!maps.containsKey("毕业学校")){
							maps.put("毕业学校", "");
						}
						if(!maps.containsKey("院系")){
							maps.put("院系", "");
						}
						if(!maps.containsKey("专业")){
							maps.put("专业", "");
						}
						listReslut.add(maps);
					}
				}
			}
			if(listOne.size() > 0){
				for (int i = 0,len = listOne.size(); i < len; i++) {
					Map<String, Object> maps = new HashMap<String, Object>();
					Map<String,Object> map = listOne.get(i);
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						if (entry.getKey().equals("姓名")) {
							maps.put(entry.getKey(), entry.getValue());
						}
						if (entry.getKey().equals("身份证号")) {
							maps.put(entry.getKey(), entry.getValue());
						}
						if (entry.getKey().equals("学历")) {
							maps.put(entry.getKey(), entry.getValue());
						}
						if (entry.getKey().equals("入学时间")) {
							maps.put(entry.getKey(), entry.getValue());
						}
						if (entry.getKey().equals("毕业学校")) {
							maps.put(entry.getKey(), entry.getValue());
						}
						if (entry.getKey().equals("院系")) {
							maps.put(entry.getKey(), entry.getValue());
						}
						if (entry.getKey().equals("专业")) {
							maps.put(entry.getKey(), entry.getValue());
						}
					}
					if(maps.size()>0){
						if(!maps.containsKey("姓名")){
							maps.put("姓名", "");
						}
						if(!maps.containsKey("身份证号")){
							maps.put("身份证号", "");
						}
						if(!maps.containsKey("学历")){
							maps.put("学历", "");
						}
						if(!maps.containsKey("入学时间")){
							maps.put("入学时间", "");
						}
						if(!maps.containsKey("毕业学校")){
							maps.put("毕业学校", "");
						}
						if(!maps.containsKey("院系")){
							maps.put("院系", "");
						}
						if(!maps.containsKey("专业")){
							maps.put("专业", "");
						}
						listReslut.add(maps);
					}
				}
			}
			if(listReslut.size() > 0){
				for (Map<String, Object> maps : listReslut) {
					if (setMap.add(maps)) {
						newListMap.add(maps);
					}
				}
			}else{
				throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
			}
		}else{
			throw new BusinessException(ResultCode.PARAM_ERROR);
		}
		return newListMap;
	}
}
