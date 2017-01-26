package org.cisiondata.modules.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("provinceCityDao")
public interface ProvinceCityDao {

	public List<String> findProvince();
	
	public List<String> findCityByProvince(String province);
	
	public String findCityByReginNum(String reginNum); 
}
