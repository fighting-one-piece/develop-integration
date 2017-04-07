package org.cisiondata.modules.user.service;

import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.user.entity.AdminUser;
import org.cisiondata.utils.exception.BusinessException;

public interface IAdminUserService {

	/**
	 * 分页查询
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public QueryResult<AdminUser> findAdminUsersByPage(Integer page,Integer pageSize) throws BusinessException;
	
}
