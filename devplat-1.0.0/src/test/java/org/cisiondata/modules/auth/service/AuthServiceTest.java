package org.cisiondata.modules.auth.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.Resource;

import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.login.dto.LoginDTO;
import org.cisiondata.modules.login.service.ILoginService;
import org.cisiondata.modules.queue.entity.MQueue;
import org.cisiondata.modules.queue.entity.RequestMessage;
import org.cisiondata.modules.queue.service.IRedisMQService;
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
	
	@Resource(name = "redisMQService")
	private IRedisMQService redisMQService = null;
	
	@Test
	public void testUserServiceReadUserByAccount() {
		User user = userService.readUserByAccount("dev11");
		System.out.println(user.getNickname());
		System.out.println("2: " + user.getId() + ":" + user.getIdentity() + ":" + user.getPassword());
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
		Object value = RedisClusterUtils.getInstance().get("user:account:test");
		System.out.println("value: " + ((User) value).getPassword());
		System.out.println("value: " + ((User) value).getSalt());
		RedisClusterUtils.getInstance().delete("user:account:test");
	}
	
	@Test
	public void testB() {
		Set<String> keys = RedisClusterUtils.getInstance().getJedisCluster().hkeys("cisiondata*");
		for (String key : keys) {
			System.out.println(key);
		}
	}
	
	@Test
	public void testC() {
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		RequestMessage requestMessage = new RequestMessage();
		requestMessage.setUrl("/login");
		requestMessage.setParams(new HashMap<String, String>());
		requestMessage.setIpAddress("192.168.0.1");
		requestMessage.setAccount("test");
		requestMessage.setTime(new Date());
		requestMessage.setReturnResult(null);
		redisMQService.sendMessage(MQueue.REQUEST_ACCESS_QUEUE.getRoutingKey(), requestMessage);
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
