package org.cisiondata.modules.auth.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.abstr.service.impl.GenericServiceImpl;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.auth.dao.UserDAO;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.auth.service.IUserService;
import org.cisiondata.utils.endecrypt.EndecryptUtils;
import org.cisiondata.utils.endecrypt.IDUtils;
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
	protected void postHandle(Object object) throws BusinessException {
		if (object instanceof User) {
			User user = (User) object;
			RedisClusterUtils.getInstance().delete(genAccountCacheKey(user.getAccount()));
		}
	}
	
	@Override
	public void insertUser(User user) throws BusinessException {
		String salt = IDUtils.genUUID();
		user.setSalt(salt);
		user.setPassword(EndecryptUtils.encryptPassword(user.getPassword(), salt));
		super.insert(user);
	}

	@Override
	public void updateUser(User user) throws BusinessException {
		super.insert(user);
	}

	@Override
	public User readUserByAccount(String account) throws BusinessException {
		if (StringUtils.isBlank(account)) throw new BusinessException("账号不能为空");
		String accountCacheKey = genAccountCacheKey(account);
		Object value = RedisClusterUtils.getInstance().get(accountCacheKey);
		if (null != value) return (User) value;
		Query query = new Query();
		query.addCondition("account", account);
		User user = userDAO.readDataByCondition(query);
		if (null != user) RedisClusterUtils.getInstance().set(accountCacheKey, user, 1800);
		return user;
	}
	
	@Override
	public User readUserByAccountAndPassword(String account, String password) throws BusinessException {
		if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
			throw new BusinessException(ResultCode.PARAM_NULL.getCode(), "账号或密码不能为空");
		}
		User user = readUserByAccount(account);
		if (null == user) {
			throw new BusinessException(ResultCode.ACCOUNT_NOT_EXIST);
		}
		String encryptPassword = EndecryptUtils.encryptPassword(password, user.getSalt());
		if (!encryptPassword.equals(user.getPassword())) {
			throw new BusinessException(ResultCode.ACCOUNT_PASSWORD_NOT_MATCH);
		}
		return user;
	}
	
	@Override
	public void updateUserPassword(String account, String originalPassword, String newPassword)
			throws BusinessException {
		User user = readUserByAccount(account);
		String password = EndecryptUtils.encryptPassword(originalPassword, user.getSalt());
		if (!password.equals(user.getPassword())) {
			throw new BusinessException("原密码不正确!");
		}
		User updateUser = new User();
		user.setAccount(account);
		user.setPassword(EndecryptUtils.encryptPassword(newPassword, user.getSalt()));
		userDAO.update(updateUser);
		RedisClusterUtils.getInstance().delete(genAccountCacheKey(account));
	}
	
	private String genAccountCacheKey(String account) {
		return "user:cache:account:" + account;
	}

}
