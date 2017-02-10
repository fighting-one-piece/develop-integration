package org.cisiondata.modules.log.service;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.log.entity.LogModel;

public interface ILogService {
	// 增加
	public void addLog(LogModel log);

	// 删除
	public boolean delLog(String keyword);

	// 查询全部
	public List<LogModel> findAll();

	// 分页
	public List<LogModel> selectByPage(int startPos, int pageSize);

	// 统计关键字
	public List<LogModel> count();

	// 统计关键字并进行分页
	public Map<String, Object> countPage(int index);

	// 查询某个关键字
	public List<LogModel> selectByKey(String keyword);

	// 查询到某个关键字进行分页
	public Map<String, Object> keyByPage(int index,String keyword);
	
	// 按时间分页
		public List<LogModel> selectByorderTime(int startPos, int pageSize);

}
