package org.cisiondata.modules.auth.dao;

import java.util.List;

import org.cisiondata.modules.auth.entity.AuthCompanyResource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("authCompanyResourceDAO")
public interface CompanyResourceDAO {

	/**
	 * 根据条件查询
	 * 
	 * @param AuthCompanyResource
	 * @return
	 * @throws DataAccessException
	 */
	public List<AuthCompanyResource> readCompanyResource(AuthCompanyResource companyResource)
			throws DataAccessException;
	
	/**
	 * 
	 * @author 11064
	 * @function 判断单位限制是否存在
	 * @param CompanyId
	 * @return
	 * @throws DataAccessException
	 */
	public List<AuthCompanyResource> judgeCompanyResourceLimit(Long CompanyId) throws DataAccessException;	
}
