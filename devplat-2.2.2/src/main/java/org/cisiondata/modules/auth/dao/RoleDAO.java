package org.cisiondata.modules.auth.dao;

import java.util.List;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.auth.entity.AuthRole;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("authRoleDAO")
public interface RoleDAO extends GenericDAO<AuthRole, Long> {

	/**
	 * 根据用户ID读取角色列表
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	public List<AuthRole> readDataListByUserId(Long userId) throws DataAccessException;
	
	/**
	 * 根据组ID读取角色列表
	 * @param groupId
	 * @return
	 * @throws DataAccessException
	 */
	public List<AuthRole> readDataListByGroupId(Long groupId) throws DataAccessException;
	
}
