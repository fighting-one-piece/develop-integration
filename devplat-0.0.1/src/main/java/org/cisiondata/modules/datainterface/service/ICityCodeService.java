package org.cisiondata.modules.datainterface.service;

import java.util.List;

import org.cisiondata.modules.datainterface.entity.CityCode;
import org.cisiondata.utils.exception.BusinessException;

public interface ICityCodeService {
	
	//通过code获取city
	public List<CityCode> findAllCity() throws BusinessException;
	//通过code获取city
	public String findCityByCode(String code) throws BusinessException;
}
