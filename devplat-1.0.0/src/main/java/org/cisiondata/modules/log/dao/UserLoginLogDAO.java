package org.cisiondata.modules.log.dao;

import org.cisiondata.modules.log.entity.UserLoginLog;
import org.springframework.stereotype.Repository;
@Repository("userLoginLogDAO")
public interface UserLoginLogDAO {
	//增加
	public int addLoginLog(UserLoginLog log);
	//查询用户上一次登录时间
	public UserLoginLog selLoginLog(String account);
}
