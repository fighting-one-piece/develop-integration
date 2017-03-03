package org.cisiondata.modules.identity.service;

import java.util.List;
import java.util.Map;

public interface IIdCardService {
	//查询ES库
	public List<Map<String,Object>> readCardDatas(String idcard);
	//查询ES和第三方库
	public List<Map<String,Object>> readCard(String name,String idcard)throws Exception;
}
