package org.cisiondata.modules.identity.service;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.abstr.entity.QueryResult;

public interface IQQService {
	
	//根据QQ号得到QQ群信息
	public List<Map<String, Object>> readQQDatas (String qq);
	//根据QQ群得到对应群的信息
	public List<Map<String, Object>> readQQqunDatas(String qunNum);
	//根据QQ号得到QQ基本信息
	public List<Map<String, Object>> readQQData(String qq);
	//根据QQ号得到QQ基本信息
	public QueryResult<Map<String, Object>> readQQNickData(String qqnick,String scrollId, int rowNumPerPage);
}
