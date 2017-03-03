package org.cisiondata.modules.datainterface.dao;

import java.util.List;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.datainterface.entity.AccessUser;
import org.springframework.stereotype.Repository;

@Repository("aaccessUserDAO")
public interface AccessUserDAO extends GenericDAO<AccessUser, Long> {
	
	/**
	 * 新增接口使用用户
	 * @param accessUser
	 * @return
	 */
	public int addAccessUser(AccessUser accessUser);
	
	/**
	 * 根据name查询用户
	 * @param accessUser
	 * @return
	 */
	public List<AccessUser> findByName(AccessUser accessUser);
	
	/**
	 * 分页查询用户
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<AccessUser> findAccessByPage(int begin, int end);
	
	/**
	 * 查询总用户数
	 * @return
	 */
	public int findAccessCount();
	
	/**
	 * 修改deleteFlag
	 * @param accessUser
	 * @return
	 */
	public int updateDeleteFlag(AccessUser accessUser);

}
