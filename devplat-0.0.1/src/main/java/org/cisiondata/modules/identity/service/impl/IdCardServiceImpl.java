package org.cisiondata.modules.identity.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.cisiondata.modules.elasticsearch.service.IESService;
import org.cisiondata.modules.identity.service.IIdCardService;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

@Service("idCardService")
public class IdCardServiceImpl implements IIdCardService{
	@Resource(name = "esService")
	private IESService esService = null;
	
	@Override
	public List<Map<String, Object>> readCardDatas(String idcard) {
		String[] type = {"student","resume"};
		return esService.readDataListByCondition("work", type, QueryBuilders.prefixQuery("idCard", idcard));
	}
}
