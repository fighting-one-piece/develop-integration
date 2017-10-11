package org.cisiondata.modules.auth.dao;


import java.util.List;
import java.util.Map;

import org.cisiondata.modules.auth.entity.AuthCompanyLimit;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("authCompanyLimitDAO")
public interface CompanyLimitDAO {

	public List<AuthCompanyLimit> readConditions(Map<String ,Object> params)throws DataAccessException;
	
}
