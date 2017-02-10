package org.cisiondata.modules.user.service;

import java.util.List;

import org.cisiondata.modules.user.entity.AUser;
import org.cisiondata.modules.user.entity.AUserARole;
import org.cisiondata.modules.user.entity.RoleUser;

public interface IRoleService{
	
	/**
	 * 全部角色
	 * @return
	 */
	public List<RoleUser> readDataRole();
	
	/**
	 * 查询某一条id的数据
	 */
	public RoleUser readDataCertain(Long id);
	
	/**
	 * 修改
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
	 * 查詢所有用戶數據
	 */
	public List<AUser>getUser();
	
	/**
	 * 增加角色类
	 */
	public int addedRole(RoleUser addRoleuser);
	
	/**
	 * 	//userID RoleID
	 */
	public int roleuserID(AUserARole auserArole);
	
	/**
	 * 查询name是否存在
	 */
	public int seletName(String name);
	
	/**
	 * 链表查询符合ID的数据
	 */
	public List<String> findID(String userROID,long Roleid);
	
	/**
	 * 中间表新增
	 */
	public List<String> AddDelete(String addDelete,long AddDeleteID);
	
	/**
	 * 中间表删除
	 */
	public List<String>Deleteadd(String Deleteadd,long DeleteaddID);
}
