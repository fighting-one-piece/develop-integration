package org.cisiondata.modules.auth.dao;

import java.util.List;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.auth.entity.Group;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("groupDAO")
public interface GroupDAO extends GenericDAO<Group, Long> {

	/**
	 * 根据用户ID读取组列表
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Group> readDataListByUserId(Long userId) throws DataAccessException;
	
}
