package org.cisiondata.modules.log.dao;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.log.entity.UserAccessLog;
import org.springframework.stereotype.Repository;

@Repository("userAccessLogDAO")
public interface UserAccessLogDAO {
	//查询
	public List<UserAccessLog> selCount(Map<String,Object> map);
	//新增日志
	public void addAccessLog(UserAccessLog log);
}
