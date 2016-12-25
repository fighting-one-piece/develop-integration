package org.cisiondata.modules.authentication.dao;

import java.util.List;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.authentication.entity.Role;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("roleDAO")
public interface RoleDAO extends GenericDAO<Role, Long> {

	/**
	 * 根据用户ID读取角色列表
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Role> readDataListByUserId(Long userId) throws DataAccessException;
	
	/**
	 * 根据组ID读取角色列表
	 * @param groupId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Role> readDataListByGroupId(Long groupId) throws DataAccessException;
	
}
