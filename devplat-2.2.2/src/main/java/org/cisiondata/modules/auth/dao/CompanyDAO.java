package org.cisiondata.modules.auth.dao;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.auth.entity.AuthCompany;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;


@Repository("authCompanyDAO")
public interface CompanyDAO {
	
	
	public List<AuthCompany> readCompany(Map<String ,Object> params)throws DataAccessException;

}
