package org.cisiondata.modules.datainterface.dao;

import java.util.List;

import org.cisiondata.modules.datainterface.entity.AccessSwitch;
import org.springframework.stereotype.Repository;

@Repository("swithDAO")
public interface AccessSwitchDAO {
	
	//查询所有
	public List<AccessSwitch> findAll();
	
	//添加
	public void saveSwitch(AccessSwitch sSwitch);
	
	//根据标识删除
	public void deleteIdentity(String identity);

	//修改
	public int updateId(AccessSwitch sSwitch);
	
	//批量修改
	public void updateIdStatus(AccessSwitch sSwitch);
}
