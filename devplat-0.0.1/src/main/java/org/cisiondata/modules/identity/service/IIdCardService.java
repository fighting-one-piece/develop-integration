package org.cisiondata.modules.identity.service;

import java.util.List;
import java.util.Map;

public interface IIdCardService {
	
	public List<Map<String,Object>> readCardDatas(String idcard);
}
