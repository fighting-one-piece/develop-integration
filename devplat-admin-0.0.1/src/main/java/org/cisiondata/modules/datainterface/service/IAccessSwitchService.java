package org.cisiondata.modules.datainterface.service;

import java.io.IOException;
import java.util.List;

import org.cisiondata.modules.datainterface.entity.AccessSwitch;


public interface IAccessSwitchService{
	/*查询所有*/
	public List<AccessSwitch> findAll(); 
	
	/*添加*/
	public void saveSwitch(AccessSwitch accessSwitch) throws IOException;
	
	/*删除*/
	public void deleteIdentity(String identity) throws IOException;
	
	/*修改*/
	public int updateId(AccessSwitch accessSwitch) throws IOException, Exception;
	
	/*批量修改*/
	public void updateIdStatus(String id,String identity,Integer status) throws IOException;
}
