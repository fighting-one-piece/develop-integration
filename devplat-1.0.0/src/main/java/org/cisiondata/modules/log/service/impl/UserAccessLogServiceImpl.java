package org.cisiondata.modules.log.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cisiondata.modules.log.dao.UserAccessLogDAO;
import org.cisiondata.modules.log.entity.UserAccessLog;
import org.cisiondata.modules.log.service.IUserAccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service("userAccessLogService")
public class UserAccessLogServiceImpl implements IUserAccessLogService{
	
	@Autowired
	private UserAccessLogDAO userAccessLogDAO;
	//分页
	int pageCount = 0; //总页数
	int count = 10;  //每页显示的条数
	int page = 0; //计算每页从哪里开始读取数据
	
	public void addLog(UserAccessLog log) {
		 userAccessLogDAO.addLog(log);
	}

	public boolean delLog(String keyword) {
		return userAccessLogDAO.delLog(keyword);
	}

	public List<UserAccessLog> findAll() {
		
		List<UserAccessLog> findAllList = userAccessLogDAO.findAll();
		return findAllList;
	}

	public List<UserAccessLog> selectByPage(int startPos, int pageSize) {
		List<UserAccessLog> selectPage = userAccessLogDAO.selectByPage(startPos, pageSize);
		return selectPage;
	}

	@Override
	public List<UserAccessLog> count() {
		List<UserAccessLog> count = userAccessLogDAO.count();
		return count;
	}

	@Override
	public Map<String, Object> countPage(int index) {
		Map<String, Object> map = new HashMap<String,Object>();
		List<UserAccessLog> list = count();
		if(list != null && list.size() > 0){
			pageCount =  list.size() % 10 == 0 ? list.size() / 10 : list.size() / 10 + 1;
		}
		//计算当前页数数据量
		page= (index -1) * 10;
		List<UserAccessLog> countPage = userAccessLogDAO.countPage(page, count);
		map.put("data", countPage);
		map.put("pageCount", pageCount);
		return map;
	}

	@Override
	public List<UserAccessLog> selectByKey(String keyword) {
		List<UserAccessLog> selectKey = userAccessLogDAO.selectByKey(keyword);
		return selectKey;
	}

	@Override
	public Map<String, Object> keyByPage(int index,String keyword) {
		Map<String, Object> map = new HashMap<String,Object>();
		List<UserAccessLog> list = selectByKey(keyword);
		if(list != null && list.size() > 0){
			pageCount = list.size() % 10 == 0? list.size() / 10 : list.size() /10 + 1;
		}
		//计算当前页数数据量
		page = (index -1) * 10;
		List<UserAccessLog> keyPage = userAccessLogDAO.keyByPage(keyword, page, count);
		map.put("data", keyPage);
		map.put("pageCount", pageCount);
		return map;
	}

	@Override
	public List<UserAccessLog> selectByorderTime(int startPos, int pageSize) {
		List<UserAccessLog> selectordertime = userAccessLogDAO.selectByorderTime(startPos, pageSize);
		return selectordertime;
	}

	@Override
	public int selLogCount(String keyword) {
		
		return userAccessLogDAO.selLogCount(keyword);
	}

	@Override
	public int addLogCount(UserAccessLog log) {
		return userAccessLogDAO.addLogCount(log);
	}

	@Override
	public int upLogCount(UserAccessLog log) {
		return userAccessLogDAO.upLogCount(log);
	}

	//每天统计关键字
	@Scheduled(cron="0 0 8 * * ?")
	public void keywordCount() {
		List<UserAccessLog> list = userAccessLogDAO.count();
		for (int i = 0; i < list.size(); i++) {
			UserAccessLog logModel = new UserAccessLog();
			logModel.setKeyword(list.get(i).getKeyword());
			logModel.setCount(list.get(i).getCount());
			logModel.setAccessTime(new Date());
			if(selLogCount(list.get(i).getKeyword()) == 0){
				addLogCount(logModel);
			}
			if(selLogCount(list.get(i).getKeyword()) == 1){
				upLogCount(logModel);
			}
		}
	}

}
