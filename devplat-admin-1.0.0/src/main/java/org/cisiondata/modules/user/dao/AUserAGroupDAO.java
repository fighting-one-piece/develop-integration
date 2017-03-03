package org.cisiondata.modules.user.dao;

import org.cisiondata.modules.user.entity.AUserAGroup;
import org.springframework.stereotype.Repository;

@Repository("user_groupdao")
public interface AUserAGroupDAO {
	//向群组新增用户
	public void addGUser(AUserAGroup aGroup);
	//移除用户
	public int delGUser(Long user_id,Long group_id);
}
