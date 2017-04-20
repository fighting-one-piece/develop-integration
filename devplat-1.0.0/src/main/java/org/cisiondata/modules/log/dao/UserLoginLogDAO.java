package org.cisiondata.modules.log.dao;

import java.util.List;

import org.cisiondata.modules.log.entity.UserLoginLog;
import org.springframework.stereotype.Repository;

@Repository("userLoginLogDAO")
public interface UserLoginLogDAO {
	// 增加
	public int addLoginLog(UserLoginLog log);

	// 查询用户上一次登录时间
	public UserLoginLog selLoginLog(String account);

	// 查询用户登录日志总条数
	public List<UserLoginLog> selLoginLogCount(String account);

	// 查询用户登录日志进行分页
	public List<UserLoginLog> selLoginLogPage(String account, int index, int pageSize);
}
