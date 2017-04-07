package org.cisiondata.modules.login.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.login.dto.LoginDTO;
import org.cisiondata.modules.login.service.ILoginService;
import org.cisiondata.modules.user.dao.AdminUserDAO;
import org.cisiondata.modules.user.entity.AdminUser;
import org.cisiondata.utils.endecrypt.EndecryptUtils;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements ILoginService {

	@Resource(name = "aadminUserDAO")
	private AdminUserDAO adminUserDAO;
	
	@Override
	public LoginDTO login(String account, String password) throws BusinessException {
		if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) throw new BusinessException(ResultCode.PARAM_ERROR);
		account = account.trim();
		password = password.trim();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("account", account);
		List<AdminUser> adminUsers = adminUserDAO.findByCondition(params);
		if (adminUsers.size() == 0) throw new BusinessException(ResultCode.ACCOUNT_NOT_EXIST);
		AdminUser adminUser = adminUsers.get(0);
		if(!adminUser.getPassword().equals(EndecryptUtils.encryptPassword(password, adminUser.getSalt())))
			throw new BusinessException(ResultCode.ACCOUNT_PASSWORD_NOT_MATCH);
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setAccount(adminUser.getAccount());
		loginDTO.setNickname(adminUser.getNickName());
		loginDTO.setToken("tokeninfo");
		return loginDTO;
	}

	@Override
	public void doUserLogout() throws BusinessException {
		// TODO Auto-generated method stub

	}

}
