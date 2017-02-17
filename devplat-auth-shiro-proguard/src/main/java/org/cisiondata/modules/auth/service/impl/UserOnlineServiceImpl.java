package org.cisiondata.modules.auth.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.abstr.service.impl.GenericServiceImpl;
import org.cisiondata.modules.auth.dao.UserOnlineDAO;
import org.cisiondata.modules.auth.entity.UserOnline;
import org.cisiondata.modules.auth.service.IUserOnlineService;
import org.springframework.stereotype.Service;

@Service("userOnlineService")
public class UserOnlineServiceImpl extends GenericServiceImpl<UserOnline, Long> implements IUserOnlineService {

	@Resource(name = "userOnlineDAO")
	private UserOnlineDAO userOnlineDAO = null;

	@Override
	public GenericDAO<UserOnline, Long> obtainDAOInstance() {
		return userOnlineDAO;
	}
	
	@Override
	public void insertOnline(UserOnline userOnline) {
		insert(userOnline);
	}
	
	@Override
	public void deleteOnline(String id) {
	}
	
	@Override
	public void deleteOnlineBatch(List<String> needOfflineIdList) {
		for (String id : needOfflineIdList) {
			deleteOnline(id);
		}
	}
	
	@Override
	public QueryResult<UserOnline> findExpiredUserOnlineList(Date expiredDate) {
		return null;
	}
	
}
