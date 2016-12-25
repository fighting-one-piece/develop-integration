package org.cisiondata.modules.authentication.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.abstr.service.impl.GenericServiceImpl;
import org.cisiondata.modules.authentication.dao.UserDAO;
import org.cisiondata.modules.authentication.entity.User;
import org.cisiondata.modules.authentication.service.IUserService;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends GenericServiceImpl<User, Long> implements IUserService {
	
	@Resource(name = "userDAO")
	private UserDAO userDAO = null;

	@Override
	public GenericDAO<User, Long> obtainDAOInstance() {
		return userDAO;
	}
	
	@Override
	public User readUserByAccount(String account) throws BusinessException {
		if (StringUtils.isBlank(account)) return null;
		Object value = RedisClusterUtils.getInstance().get(account);
		if (null != value) return (User) value;
		Query query = new Query();
		query.addCondition("account", account);
		User user = userDAO.readDataByCondition(query);
		if (null != user) RedisClusterUtils.getInstance().set(account, user, 300);
		return user;
	}
	
	@Override
	public User readUserByAccountAndPassword(String account, String password)
			throws BusinessException {
		Query query = new Query();
		query.addCondition("account", account);
		query.addCondition("password", password);
		return userDAO.readDataByCondition(query);
	}

}
