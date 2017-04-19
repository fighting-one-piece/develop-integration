package org.cisiondata.modules.auth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.auth.dao.AdminUserDAO;
import org.cisiondata.modules.auth.entity.AAdminUser;
import org.cisiondata.modules.auth.service.IAdminUserService;
import org.cisiondata.utils.endecrypt.EndecryptUtils;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.springframework.stereotype.Service;

@Service("adminuserService")
public class AdminUserServiceImpl implements IAdminUserService {

	@Resource(name = "adminUserDAO")
	private AdminUserDAO adminUserDAO;
	
	@Override
	public AAdminUser readUserByAccountAndPassword(String account, String password) throws BusinessException {
		if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
			throw new BusinessException(ResultCode.PARAM_NULL.getCode(), "账号或密码不能为空");
		}
		AAdminUser adminUser = readUserByAccount(account);
		if (null == adminUser) {
			throw new BusinessException(ResultCode.ACCOUNT_PASSWORD_NOT_MATCH);
		}
		String encryptPassword = EndecryptUtils.encryptPassword(password, adminUser.getSalt());
		if (!encryptPassword.equals(adminUser.getPassword())) {
			throw new BusinessException(ResultCode.ACCOUNT_PASSWORD_NOT_MATCH);
		}
		return adminUser;
	}

	@Override
	public AAdminUser readUserByAccount(String account) throws BusinessException {
		if (StringUtils.isBlank(account)) throw new BusinessException(ResultCode.ACCOUNT_NOT_EXIST);
		String accountCacheKey = genAccountCacheKey(account);
		Object value = RedisClusterUtils.getInstance().get(accountCacheKey);
		AAdminUser adminUser = new AAdminUser();
		if (null != value) {
			adminUser = (AAdminUser) value;
		} else {
			Map<String,Object> params = new HashMap<String ,Object>();
			params.put("account", account);
			List<AAdminUser> list =  adminUserDAO.findByCondition(params);
			if (list.size() < 0) return null;
			adminUser = list.get(0);
			if (null != adminUser && null != adminUser.getId()) {
				RedisClusterUtils.getInstance().set(accountCacheKey, adminUser, 1800);
			}
		}
		return adminUser;
	}

	
	private String genAccountCacheKey(String account) {
		return "adminuser:cache:account:" + account;
	}
}
