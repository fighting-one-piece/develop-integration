package org.cisiondata.modules.auth.dao;

import org.cisiondata.modules.auth.entity.AuthCompanyRole;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("authCompanyRoleDAO")
public interface CompanyRoleDAO {

	/**
	 * 条件查询
	 * 
	 * @param companyRole
	 * @return
	 * @throws DataAccessException
	 */
	public AuthCompanyRole readCompanyRole(AuthCompanyRole companyRole) throws DataAccessException;

}
