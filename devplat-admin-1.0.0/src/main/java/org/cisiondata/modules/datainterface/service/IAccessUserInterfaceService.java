package org.cisiondata.modules.datainterface.service;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.datainterface.entity.AccessInterface;
import org.cisiondata.modules.datainterface.entity.AccessUserInterface;
import org.cisiondata.utils.exception.BusinessException;

/**
 * 用户接口表接口
 * @author Administrator
 *
 */
public interface IAccessUserInterfaceService {
	//分页数据
	Map<String,Object>Pagination(int page,int pageSize)throws BusinessException;
	//通过ID查询数据
	public AccessUserInterface QueryId(long id);
	
	//修改用户接口表的状态
	public int Revamp(int Number,long id);
	
	//删除用户
	public int RemoveUser(long id);
	
	//账号数据
	public List<String> userAccount();
	
	//接口数据
	public List<AccessInterface> hickey();
	
	//增加账户数据
	public int addUserAccount(AccessUserInterface UserInterface);
	
	//自动批量赋予
	public int giveHickey(String user);
}
