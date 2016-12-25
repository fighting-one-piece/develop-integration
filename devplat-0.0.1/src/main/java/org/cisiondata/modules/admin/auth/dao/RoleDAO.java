package org.cisiondata.modules.admin.auth.dao;

import java.util.List;

import org.cisiondata.modules.admin.auth.entity.RoleUser;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("RoleDAOS")
public interface RoleDAO {
	/**
	 * 读取所有的角色信息
	 */
	public List<RoleUser> readDataLsitroleid()throws DataAccessException;
	
	/**
	 * 根据ID查询某一条记录
	 */
	public  RoleUser readDataCertain(Long id);
	
	/**
	 * 根据ID修改数据
	 */
	public int readUpdata(RoleUser roleUser);
}
