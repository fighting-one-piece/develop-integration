package org.cisiondata.modules.auth.service;

import java.util.Set;

import javax.annotation.Resource;

import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.auth.entity.UserAttribute;
import org.cisiondata.modules.login.dto.LoginDTO;
import org.cisiondata.modules.login.service.ILoginService;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext.xml"})
public class AuthServiceTest {

	@Resource(name = "userService")
	private IUserService userService = null;
	
	@Resource(name = "roleService")
	private IRoleService roleService = null;
	
	@Resource(name = "authService")
	private IAuthService authService = null;
	
	@Resource(name = "loginService")
	private ILoginService loginService = null;
	
	@Test
	public void testUserServiceReadUserByAccount() {
		User user = userService.readUserByAccount("dev5");
		System.out.println(user.getNickname());
		System.out.println(user.getFirstLoginFlag());
		System.out.println(user.getEncryptedFlag());
		System.out.println(user.getInformationFlag());
	}
	
	@Test
	public void testUserServiceReadUserByAccountAndPassword() {
        User user = authService.readUserByAccountAndPassword("dev22", "dev123456");
        System.out.println("1: " + user.getId() + ":" + user.getIdentity() + ":" + user.getPassword());
        userService.updateUserPassword("dev22", "dev123456", "dev123");
        user = authService.readUserByAccountAndPassword("dev22", "dev123");
        System.out.println("2: " + user.getId() + ":" + user.getIdentity() + ":" + user.getPassword());
	}
	
	@Test
	public void testUserServiceUpdate() {
		User user = new User();
		user.setAccount("dev77");
		user.setDeleteFlag(true);
		userService.update(user);
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
	public void testLoginServiceReadUserLoginInfo() {
		String account = "test";
		String password = "@#test456";
		LoginDTO userDTO = loginService.readUserLoginInfoByAccountAndPassowrd(account, password);
		System.out.println("accessToken: " + userDTO.getAccessToken());
	}
	
	@Test
	public void testA() {
		Object value = RedisClusterUtils.getInstance().get("user:cache:account:test");
		System.out.println("value: " + ((User) value).getPassword());
		System.out.println("value: " + ((User) value).getSalt());
		RedisClusterUtils.getInstance().delete("user:cache:account:test");
	}
	
	@Test
	public void testB() {
		User user = new User();
		user.setAccount("random");
		user.setAccessId("id1");
		user.setAccessKey("key1");
		UserAttribute ua1 = new UserAttribute();
		ua1.setKey("accessId");
		ua1.setValue("accessId1");
		user.getAttributes().add(ua1);
		UserAttribute ua2 = new UserAttribute();
		ua2.setKey("accessKey");
		ua2.setValue("accessKey1");
		user.getAttributes().add(ua2);
		RedisClusterUtils.getInstance().set("user:cache:account:random", user);
		User temp = (User) RedisClusterUtils.getInstance().get("user:cache:account:random");
		System.out.println(temp.getAttributes().size());
		temp.transientHandle();
		System.out.println("account: " + temp.getAccount());
		System.out.println("accessId: " + temp.getAccessId());
		System.out.println("accessKey: " + temp.getAccessKey());
	}
	
}
