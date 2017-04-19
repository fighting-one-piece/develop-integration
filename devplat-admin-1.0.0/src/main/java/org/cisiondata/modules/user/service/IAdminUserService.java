package org.cisiondata.modules.user.service;

import java.util.Map;

import org.cisiondata.utils.exception.BusinessException;

public interface IAdminUserService {

	/**
	 * 分页查询
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Map<String,Object> findAdminUsersByPage(Integer page,Integer pageSize) throws BusinessException;
	/**
	 * 新增用户
	 * @param adminUser
	 * @return
	 * @throws BusinessException
	 */
	public String addAdminUser(String account,String password,String identity,String nickName,String mobilePhone)throws BusinessException;
	/**
	 * 根据id修改用户
	 * @param adminUser
	 * @return
	 * @throws BusinessException
	 */
	public boolean updateAdminUser(String account, String password,String identity, String nickName,String mobilePhone, long id)throws BusinessException;
	/**
	 * 根据id删除用户
	 * @throws BusinessException
	 */
	public String deleteAdminUser(Long id)throws BusinessException;


}
