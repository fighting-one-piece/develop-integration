package org.cisiondata.modules.log.dao;

import java.util.List;

import org.cisiondata.modules.log.entity.LogModel;
import org.springframework.stereotype.Repository;

@Repository("logMapper")
public interface LogMapper {
	//增加
	public void addLog(LogModel log);
	//查询
	public List<LogModel> findAll();
	//删除
	public boolean delLog(String keyword);
	//分页
	public List<LogModel> selectByPage(int startPos,int pageSize);
	//统计关键字
	public List<LogModel> count();
	//根据关键字进行分页
	public List<LogModel> countPage(int startPos,int pageSize);
	//查询某个关键字
	public List<LogModel> selectByKey(String keyword);
	//查询到某个关键字进行分页
	public List<LogModel> keyByPage(String keyword,int startPos,int pageSize);
	//按时间分页
	public List<LogModel> selectByorderTime(int startPos,int pageSize);
	//查询在统计关键字表中是否存在该关键字
	public int selLogCount(String keyword);
	//将该关键字存入统计关键字表中
	public int addLogCount(LogModel log);
	//修改已存在的关键字统计的个数
	public int upLogCount(LogModel log);
	//查询某个用户访问的资源总数
	public int selByAccount(String name);
	//查询某个用户访问的资源进行分页
	public List<LogModel> selByAcPage(String name,int startPos,int padeSize);
}
