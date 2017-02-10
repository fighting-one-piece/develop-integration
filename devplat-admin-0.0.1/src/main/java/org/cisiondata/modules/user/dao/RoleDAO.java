package org.cisiondata.modules.user.dao;

import java.util.List;

import org.cisiondata.modules.user.entity.AUserARole;
import org.cisiondata.modules.user.entity.RoleUser;
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
	
	/**
	 * 刪除本表數據
	 */
	public int readDelet(Long id);
	
	/**
	 * 刪除用戶關係表數據
	 */
	public int readuserDelet(Long id);
	
	/**
	 * 刪除與群組關係的數據
	 */
	public int readgroupdelet(Long id);
	
	/**
	 * 添加角色
	 */
	public int addedRole(RoleUser addRoleuser);
	
	/**
	 * userID ROleID
	 */
	public int roleuserID(AUserARole auserarole);
	
	/**
	 * 查询角色是否存在
	 */
	public int selectName(String name);
	
	/**
	 * 指定ID查询数据
	 */
	public int  findID(AUserARole ROlesID);
	
	/**
	 * 中间表新增
	 */
	public int AddDelete(AUserARole wholeID);
	
	/**
	 * 存在就修改
	 */
	public int modify(AUserARole modify);
	
	/**
	 * 中间表删除操作
	 */
	public int Delet(AUserARole Delet);
	
}
