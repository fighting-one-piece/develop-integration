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
	/**
	 * 新增用户
	 * @param adminUser
	 * @return
	 * @throws BusinessException
	 */
	public boolean addAdminUser(AdminUser adminUser)throws BusinessException;
	/**
	 * 根据id修改用户
	 * @param adminUser
	 * @return
	 * @throws BusinessException
	 */
	public boolean updateAdminUser(AdminUser user)throws BusinessException;
	/**
	 * 根据id删除用户
	 * @throws BusinessException
	 */
	public boolean deleteAdminUser(long id)throws BusinessException;


}
