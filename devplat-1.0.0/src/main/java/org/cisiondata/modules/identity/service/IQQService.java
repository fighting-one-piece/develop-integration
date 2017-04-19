package org.cisiondata.modules.identity.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.cisiondata.modules.abstr.entity.QueryResult;

public interface IQQService {
	
	//根据QQ号得到QQ群信息
	public Map<String, Object> readQQDatas (String qq,HttpServletRequest request);
	//根据QQ群号查询
	public Map<String, Object> readQQqun(String qunNum);
	//根据QQ群得到对应群的信息
	public Map<String, Object> readQQqunDatas(String qunNum ,HttpServletRequest request);
	//根据QQ号得到QQ基本信息
	public Map<String, Object> readQQData(String qq ,HttpServletRequest request);
	//根据QQ昵称得到QQ基本信息
	public QueryResult<Map<String, Object>> readQQNickData(String qqnick,String scrollId, int rowNumPerPage ,HttpServletRequest request);
	
}
