package org.cisiondata.modules.auth.service;

import java.util.Set;

import javax.annotation.Resource;

import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.utils.endecrypt.EndecryptUtils;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class AuthServiceTest {

	@Resource(name = "userService")
	private IUserService userService = null;
	
	@Resource(name = "roleService")
	private IRoleService roleService = null;
	
	@Resource(name = "authService")
	private IAuthService authService = null;
	
	@Resource(name = "accessUserService")
	private IAccessUserService accessUserService = null;
	
	@Test
	public void testUserServiceReadUserByAccount() {
		User user = userService.readUserByAccount("admin");
		System.out.println(user.getNickname());
	}
	
	@Test
	public void testUserServiceReadUserByAccountAndPassword() {
		String usernameText = "TeSt".toLowerCase().trim();
        String passwordText = "@#test456";
        String passwordCipherText = EndecryptUtils.encryptPassword(usernameText, passwordText);
        User user = authService.readUserByAccountAndPassword(usernameText, passwordCipherText);
        System.out.println(user.getId() + ":" + user.getIdentity());
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
	
	@Test
	public void testReadAccessKeyByAccessId() {
		String accessKey = accessUserService.readAccessKeyByAccessId("Fc89A13022BfdD2f");
		Assert.assertEquals("F0de5A94aEb07f3a6F0959060fc4B697", accessKey);
	}
	
	@Test
	public void testA() {
		int status = (int) RedisClusterUtils.getInstance().get("BankQueryQuota");
		System.out.println("status: " + status);
	}
	
}
