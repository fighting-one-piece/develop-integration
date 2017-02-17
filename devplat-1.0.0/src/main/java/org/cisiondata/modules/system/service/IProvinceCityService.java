package org.cisiondata.modules.system.service;

import java.util.List;

import org.cisiondata.utils.exception.BusinessException;

public interface IProvinceCityService {

	
	public List<String> findProvince() throws BusinessException;
	
	public List<String> findCity(String province) throws BusinessException;
	
	public String findPhoneCity(String phone) throws BusinessException;
}
