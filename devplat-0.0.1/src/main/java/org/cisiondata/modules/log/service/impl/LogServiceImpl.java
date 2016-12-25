package org.cisiondata.modules.log.service.impl;


import java.util.List;

import org.cisiondata.modules.log.dao.LogMapper;
import org.cisiondata.modules.log.entity.LogModel;
import org.cisiondata.modules.log.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LogServiceImpl implements ILogService{
	
	@Autowired
	private LogMapper logMapper;
	
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
	public List<LogModel> countPage(int startPos, int pageSize) {
		List<LogModel> countPage = logMapper.countPage(startPos, pageSize);
		return countPage;
	}

	@Override
	public List<LogModel> selectByKey(String keyword) {
		List<LogModel> selectKey = logMapper.selectByKey(keyword);
		return selectKey;
	}

	@Override
	public List<LogModel> keyByPage(String keyword, int startPos, int pageSize) {
		List<LogModel> keyPage = logMapper.keyByPage(keyword, startPos, pageSize);
		return keyPage;
	}

	@Override
	public List<LogModel> selectByorderTime(int startPos, int pageSize) {
		List<LogModel> selectordertime = logMapper.selectByorderTime(startPos, pageSize);
		return selectordertime;
	}

	
}
