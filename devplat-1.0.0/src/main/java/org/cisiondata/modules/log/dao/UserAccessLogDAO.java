package org.cisiondata.modules.log.dao;

import java.util.List;

import org.cisiondata.modules.log.entity.UserAccessLog;
import org.springframework.stereotype.Repository;

@Repository("userAccessLogDAO")
public interface UserAccessLogDAO {
	
	//增加
	public void addLog(UserAccessLog log);
	//查询
	public List<UserAccessLog> findAll();
	//删除
	public boolean delLog(String keyword);
	//分页
	public List<UserAccessLog> selectByPage(int startPos,int pageSize);
	//统计关键字
	public List<UserAccessLog> count();
	//根据关键字进行分页
	public List<UserAccessLog> countPage(int startPos,int pageSize);
	//查询某个关键字
	public List<UserAccessLog> selectByKey(String keyword);
	//查询到某个关键字进行分页
	public List<UserAccessLog> keyByPage(String keyword,int startPos,int pageSize);
	//按时间分页
	public List<UserAccessLog> selectByorderTime(int startPos,int pageSize);
	//查询在统计关键字表中是否存在该关键字
	public int selLogCount(String keyword);
	//将该关键字存入统计关键字表中
	public int addLogCount(UserAccessLog log);
	//修改已存在的关键字统计的个数
	public int upLogCount(UserAccessLog log);
}
