package org.cisiondata.modules.auth.dao;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.auth.entity.AccessUserControl;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("accessUserControlDAO")
public interface AccessUserControlDAO extends GenericDAO<AccessUserControl, Long> {

	/**
	 * 更新用户剩余查询条数
	 * @param accessUserControl
	 * @throws DataAccessException
	 */
	public void updateRemainingCount(AccessUserControl accessUserControl) throws DataAccessException;
	
	/**
	 * 更新用户剩余查询金额
	 * @param accessUserControl
	 * @throws DataAccessException
	 */
	public void updateRemainingMoney(AccessUserControl accessUserControl) throws DataAccessException;
	
	/**
	 * 更新用户剩余查询金额
	 * @param accessUserControl
	 * @throws DataAccessException
	 */
	public void updateRemainingMoneyIncOrDec(AccessUserControl accessUserControl) throws DataAccessException;
	
}
