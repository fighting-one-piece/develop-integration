package org.cisiondata.modules.identity.service;

import java.util.List;
import java.util.Map;

public interface IIdCardService {
	//查询ES库
	public List<Map<String,Object>> readCardDatas(String idcard);
	//查询ES和第三方库
	public List<Map<String,Object>> readCard(String name,String idcard,String university) throws Exception;
	//根据姓名、学校、学院、专业、学历、入学年份
	public List<Map<String,Object>> readESCard(String name,String university,String department,String major,String beginTime);
}
