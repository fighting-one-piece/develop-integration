package org.cisiondata.modules.identity.service.impl;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.cisiondata.modules.datainterface.service.IDatadaService;
import org.cisiondata.modules.elasticsearch.service.IESService;
import org.cisiondata.modules.identity.service.IIdCardService;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("idCardService")
public class IdCardServiceImpl implements IIdCardService{
	@Resource(name = "esService")
	private IESService esService = null;
	@Autowired
	private IDatadaService datadaService = null;
	@Override
	public List<Map<String, Object>> readCardDatas(String idcard) {
		//定义判别身份证号的正则表达式
		Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
		//通过Pattern获得Matcher
		Matcher idNumMatcher = idNumPattern.matcher(idcard);
		List<Map<String, Object>> list = null;
		//判断是否为身份证号
		if(idNumMatcher.matches()){
			String[] type = {"student","resume"};
			list = esService.readDataListByCondition("work", type, QueryBuilders.prefixQuery("idCard", idcard));
			List<Map<String, Object>> listmap = datadaService.readDatadaDatas(idcard);
			if(listmap.size() > 0){
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
}
