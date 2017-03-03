package org.cisiondata.modules.datainterface.dao;

import java.util.List;

import org.cisiondata.modules.datainterface.entity.AccessInterface;
import org.springframework.stereotype.Repository;

@Repository("accessInterfaceDao")
public interface AccessInterfaceDao {

	//新增
	public int addAccessInterface(AccessInterface accessInterface);
	
	//修改
	public int updateAccessInterface(AccessInterface accessInterface);
	
	//修改DELETE_FLAG
	public int updateAccessInterfaceDeleteFlag(AccessInterface accessInterface);
	
	//查找所有未删除
	public List<AccessInterface> findAll();
	
	//查询数据库中一共有多少条数据
	public int findCount();
	
	//分页查询接口
	public List<AccessInterface> findByPage(int begin ,int pageSize);
}

