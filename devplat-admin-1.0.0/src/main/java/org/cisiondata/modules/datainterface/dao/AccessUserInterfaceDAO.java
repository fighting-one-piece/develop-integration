package org.cisiondata.modules.datainterface.dao;

import java.util.List;

import org.cisiondata.modules.datainterface.entity.AccessUserInterface;
import org.springframework.stereotype.Repository;

/**
 * 用户接口表 dao 层接口
 * @author Administrator
 *
 */
@Repository("AccessUserInterfaceDAO")
public interface AccessUserInterfaceDAO {
	
	//计算数据
	public int contun();
	//分页数据
	public List<AccessUserInterface> Pagination(int page,int pageSize);

	//通过id查询
	public AccessUserInterface QueryId(long id);
	
	//修改用户状态表 
	public int Revamp(int Number,long id);
	
	//删除用户
	public int RemoveUser(long id);
	
	//增加用户
	public int addUserAccount(AccessUserInterface UserInterface);
	
	//批量增加用户接口
	public int giveHickey(String account,Long infeace);
	
	//查找相对应的账号ID值
	public Long countId(String account);
	
}
