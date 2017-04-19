package org.cisiondata.modules.datainterface.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.datainterface.dao.CityCodeDAO;
import org.cisiondata.modules.datainterface.entity.CityCode;
import org.cisiondata.modules.datainterface.service.ICityCodeService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("cityCodeService")
public class CityCodeServiceImpl implements ICityCodeService, InitializingBean{

	@Resource(name="cityCodeDAO")
	private CityCodeDAO cityCodeDAO = null;
	
	private static Map<String, String> cache = new HashMap<String, String>();
	
	//通过code获取city
	@Override
	public List<CityCode> findAllCity() throws BusinessException {
		List<CityCode> list = new ArrayList<>();
		list = cityCodeDAO.findCityByCode();
		return list;
	}
	@Override
	public String findCityByCode(String code) throws BusinessException {
		return cache.get(code);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		List<CityCode> list = new ArrayList<>();
		list = findAllCity();
		for (int i = 0; i < list.size(); i++) {
			cache.put(list.get(i).getCode().trim(), list.get(i).getCity().trim());
		}
	}
	
}
