package org.cisiondata.modules.datainterface.dao;

import java.util.List;

import org.cisiondata.modules.datainterface.entity.CityCode;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("cityCodeDAO")
public interface CityCodeDAO {
	
	//通过code获取city
	public List<CityCode> findCityByCode() throws DataAccessException; 
}
