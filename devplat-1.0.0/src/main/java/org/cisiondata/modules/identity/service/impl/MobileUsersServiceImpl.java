package org.cisiondata.modules.identity.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.elasticsearch.service.IESService;
import org.cisiondata.modules.identity.service.IMobileUsersService;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

/**
 * 实现查询的方法  后期加入通讯录
 * @author Administrator
 */
@Service("mobileUsersService")
public class MobileUsersServiceImpl   implements IMobileUsersService {
	
	@Resource(name = "esService")
	private IESService esService = null;
	
	/**
	 * 实现
	 */
	@Override
	public List<Map<String, Object>> readphoneNUmbers(String phone){
		List<Map<String, Object>> list = esService.readDataListByCondition(QueryBuilders.termQuery("phone", phone));
		for (Map<String, Object> map : list) {
			map.remove("index");
			map.remove("type");
		}
		return list; 
	}
	
}
