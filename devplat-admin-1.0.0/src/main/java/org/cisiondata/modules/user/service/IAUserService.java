package org.cisiondata.modules.user.service;


import java.util.Map;

import org.cisiondata.modules.user.entity.AUser;
import org.cisiondata.utils.exception.BusinessException;

public interface IAUserService {

	/**
	 * 新增用户
	 * @param account
	 * @param password
	 * @param identity
	 * @param nickname
	 * @param expireTime
	 * @param expireTime2 
	 * @param salt
	 * @return
	 * @throws BusinessException
	 */
	public void addAUser(String account,String password,String pwd,String nickname,String identity,String expireTime) throws BusinessException;
	
	/**
	 * 根据条件分页查询
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public Map<String,Object> findAuser(Integer page,Integer pageSize,String identity) throws BusinessException;
	
	/**
	 * 根据ID修改
	 * @param auser
	 * @throws BusinessException
	 */
	public void updateAUser(AUser auser) throws BusinessException;
	
	/**
	 * 启停用
	 * @param id
	 * @throws BusinessException
	 */
	public void updateAUserqt(Long id) throws BusinessException;
	
	
	/**
	 * 根据账号查询添加或修改秘钥
	 * @param auser
	 * @return
	 * @throws BusinessException
	 */
	public void addkeyAuser(AUser auser) throws BusinessException;
	/**
	 * 查询全部用户
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> findAllUser(Long id,Long identity) throws BusinessException;
}
