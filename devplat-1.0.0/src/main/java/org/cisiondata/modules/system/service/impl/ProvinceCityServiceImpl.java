package org.cisiondata.modules.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.system.dao.ProvinceCityDAO;
import org.cisiondata.modules.system.service.IProvinceCityService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("provinceCityService")
public class ProvinceCityServiceImpl implements IProvinceCityService, InitializingBean {

	@Resource(name = "provinceCityDAO")
	private ProvinceCityDAO provinceCityDAO = null;
	
	private List<String> provinceList = null;
	
	private Map<String,List<String>> cityMap = null;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		provinceList = provinceCityDAO.findProvince();
		initCityMap();
	}
	
	@Override
	public List<String> findProvince() throws BusinessException {
		if(null != provinceList && provinceList.size()>0){
			return provinceList;
		}
		return provinceCityDAO.findProvince();
	}

	@Override
	public List<String> findCity(String province) throws BusinessException {
		if ("".equals(province.trim()) || province == null){
			return new ArrayList<String>();
		} else {
			if(null != cityMap && cityMap.containsKey(province)){
				return cityMap.get(province);
			}
			return provinceCityDAO.findCityByProvince(province);
		}
	}

	@Override
	public String findPhoneCity(String phone) throws BusinessException {
		if ("".equals(phone.trim()) || phone == null){
			return "";
		} else {
			phone = phone.trim().substring(0, 7);
			String city = provinceCityDAO.findCityByReginNum(phone);
			return city == null? "" : city;
		}
	}
	
	private void initCityMap(){
		cityMap = new HashMap<String ,List<String>>();
		for(int i = 0; i < provinceList.size(); i++){
			String province = provinceList.get(i);
			List<String> cities = provinceCityDAO.findCityByProvince(province);
			cityMap.put(provinceList.get(i), cities);
		}
	}

}
