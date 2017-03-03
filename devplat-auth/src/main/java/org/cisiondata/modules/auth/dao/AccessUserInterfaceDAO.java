package org.cisiondata.modules.auth.dao;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.auth.entity.AccessUserInterface;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("accessUserInterfaceDAO")
public interface AccessUserInterfaceDAO extends GenericDAO<AccessUserInterface, Long> {

	/**
	 * 读取用户接口信息
	 * @param accessUserInterface
	 * @return
	 * @throws DataAccessException
	 */
	public AccessUserInterface readDataByUserInterface(AccessUserInterface accessUserInterface) 
			throws DataAccessException;
	
}
