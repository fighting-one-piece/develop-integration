package org.cisiondata.modules.login.service.impl;

import javax.annotation.Resource;

import org.cisiondata.modules.auth.entity.AAdminUser;
import org.cisiondata.modules.auth.service.IAdminAuthService;
import org.cisiondata.modules.login.dto.LoginDTO;
import org.cisiondata.modules.login.service.ILoginService;
import org.cisiondata.modules.user.dao.AdminUserDAO;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements ILoginService {

	@Resource(name = "aadminUserDAO")
	private AdminUserDAO adminUserDAO;
	
	@Resource(name = "adminAuthService")
	private IAdminAuthService adminAuthService;
	
	@Override
	public LoginDTO login(String account, String password) throws BusinessException {
		AAdminUser adminUser = adminAuthService.readAdminUserAuthenticationInfo(account, password);
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setAccount(adminUser.getAccount());
		loginDTO.setNickname(adminUser.getNickName());
//		loginDTO.setToken(adminUser.getAccessToken());
		loginDTO.setToken("63a94a6c501dd5890f63e086d0dbad60");
		return loginDTO;
	}

	@Override
	public void doUserLogout() throws BusinessException {
		// TODO Auto-generated method stub

	}

}
