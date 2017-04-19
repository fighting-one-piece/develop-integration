package org.cisiondata.modules.auth.service.impl;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.abstr.service.impl.GenericServiceImpl;
import org.cisiondata.modules.auth.dao.UserIntegrationDAO;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.auth.service.IUserService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends GenericServiceImpl<User, Long> implements IUserService {
	
	@Resource(name = "userIntegrationDAO")
	private UserIntegrationDAO userIntegrationDAO = null;

	@Override
	public GenericDAO<User, Long> obtainDAOInstance() {
		return userIntegrationDAO;
	}
	
	@Override
	protected void postHandle(Object object) throws BusinessException {
	}
	
	@Override
	public void insertUser(User user) throws BusinessException {
		super.insert(user);
	}

	@Override
	public void updateUser(User user) throws BusinessException {
		super.insert(user);
	}
	
	

}
