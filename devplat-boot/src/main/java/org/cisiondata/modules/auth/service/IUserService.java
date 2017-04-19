package org.cisiondata.modules.auth.service;

import org.cisiondata.modules.abstr.service.IGenericService;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.utils.exception.BusinessException;

public interface IUserService extends IGenericService<User, Long> {
	
	/**
	 * 新增用户
	 * @param user
	 * @throws BusinessException
	 */
	public void insertUser(User user) throws BusinessException;

	/**
	 * 修改用户信息
	 * @param user
	 * @throws BusinessException
	 */
	public void updateUser(User user) throws BusinessException;
	
}
