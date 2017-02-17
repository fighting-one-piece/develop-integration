package org.cisiondata.modules.auth.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.abstr.service.impl.GenericServiceImpl;
import org.cisiondata.modules.auth.dao.UserDAO;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.auth.service.IUserService;
import org.cisiondata.utils.endecrypt.EndecryptUtils;
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
		String accountCacheKey = genAccountCacheKey(account);
		Object value = RedisClusterUtils.getInstance().get(accountCacheKey);
		if (null != value) return (User) value;
		Query query = new Query();
		query.addCondition("account", account);
		User user = userDAO.readDataByCondition(query);
		if (null != user) RedisClusterUtils.getInstance().set(accountCacheKey, user, 300);
		return user;
	}
	
	@Override
	public User readUserByAccountAndPassword(String account, String password) throws BusinessException {
		Query query = new Query();
		query.addCondition("account", account);
		query.addCondition("password", password);
		return userDAO.readDataByCondition(query);
	}
	
	@Override
	public void updateUserPassword(String account, String originalPassword, String newPassword)
			throws BusinessException {
		User user = readUserByAccount(account);
		String password = EndecryptUtils.encryptPassword(account, originalPassword);
		if (!password.equals(user.getPassword())) {
			throw new BusinessException("原密码不正确！");
		}
		user.setPassword(EndecryptUtils.encryptPassword(account, newPassword));
		userDAO.updateUserPassword(user);
	}
	
	private String genAccountCacheKey(String account) {
		return "user:account:" + account;
	}

}
