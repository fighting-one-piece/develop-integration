package org.cisiondata.modules.authentication.service;

import java.util.Set;

import javax.annotation.Resource;

import org.cisiondata.modules.authentication.entity.User;
import org.cisiondata.modules.authentication.service.IAuthService;
import org.cisiondata.modules.authentication.service.IRoleService;
import org.cisiondata.modules.authentication.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class AuthenticationServiceTest {

	@Resource(name = "userService")
	private IUserService userService = null;
	
	@Resource(name = "roleService")
	private IRoleService roleService = null;
	
	@Resource(name = "authService")
	private IAuthService authService = null;
	
	@Test
	public void testUserServiceReadUserByAccount() {
		User user = userService.readUserByAccount("admin");
		System.out.println(user.getNickname());
	}
	
	@Test
	public void testRoleServiceReadIdentitiesByUserId() {
		Set<String> identities = roleService.readRoleIdentitiesByUserId(1L);
		System.out.println("identities length: " + identities.size());
		for (String identity : identities) {
			System.out.println(identity);
		}
	}
	
	@Test
	public void testAuthServiceReadPermissionsByUserId() {
		Set<String> identities = authService.readPermissionIdentitiesByUserId(1L);
		System.out.println("identities length: " + identities.size());
		for (String identity : identities) {
			System.out.println(identity);
		}
	}
	
}
