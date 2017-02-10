package org.cisiondata.modules.log.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cisiondata.modules.log.dao.LogMapper;
import org.cisiondata.modules.log.entity.LogModel;
import org.cisiondata.modules.log.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LogServiceImpl implements ILogService{
	
	@Autowired
	private LogMapper logMapper;
	//分页
	int pageCount = 0; //总页数
	int count = 10;  //每页显示的条数
	int page = 0; //计算每页从哪里开始读取数据
	
	public void addLog(LogModel log) {
		
		 logMapper.addLog(log);
	}

	public boolean delLog(String keyword) {
		
		return logMapper.delLog(keyword);
	}


	public List<LogModel> findAll() {
		
		List<LogModel> findAllList = logMapper.findAll();
		return findAllList;
	}

	public List<LogModel> selectByPage(int startPos, int pageSize) {
		List<LogModel> selectPage = logMapper.selectByPage(startPos, pageSize);
		return selectPage;
	}

	@Override
	public List<LogModel> count() {
		List<LogModel> count = logMapper.count();
		return count;
	}

	@Override
	public Map<String, Object> countPage(int index) {
		Map<String, Object> map = new HashMap<String,Object>();
		List<LogModel> list = count();
		if(list != null && list.size() > 0){
			pageCount =  list.size() % 10 == 0 ? list.size() / 10 : list.size() / 10 + 1;
		}
		//计算当前页数数据量
		page= (index -1) * 10;
		List<LogModel> countPage = logMapper.countPage(page, count);
		map.put("data", countPage);
		map.put("pageCount", pageCount);
		return map;
	}

	@Override
	public List<LogModel> selectByKey(String keyword) {
		List<LogModel> selectKey = logMapper.selectByKey(keyword);
		return selectKey;
	}

	@Override
	public Map<String, Object> keyByPage(int index,String keyword) {
		Map<String, Object> map = new HashMap<String,Object>();
		List<LogModel> list = selectByKey(keyword);
		if(list != null && list.size() > 0){
			pageCount = list.size() % 10 == 0? list.size() / 10 : list.size() /10 + 1;
		}
		//计算当前页数数据量
		page = (index -1) * 10;
		List<LogModel> keyPage = logMapper.keyByPage(keyword, page, count);
		map.put("data", keyPage);
		map.put("pageCount", pageCount);
		return map;
	}

	@Override
	public List<LogModel> selectByorderTime(int startPos, int pageSize) {
		List<LogModel> selectordertime = logMapper.selectByorderTime(startPos, pageSize);
		return selectordertime;
	}

	
}
