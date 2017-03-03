package org.cisiondata.modules.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.auth.entity.AccessInterface;
import org.cisiondata.modules.auth.entity.AccessUserControl;
import org.cisiondata.modules.auth.entity.AccessUserInterface;
import org.cisiondata.modules.auth.entity.AccessUserInterfaceMoney;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.utils.endecrypt.EndecryptUtils;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.cisiondata.utils.serde.SerializerUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.Assert;
import redis.clients.jedis.BinaryJedisPubSub;

@SuppressWarnings("deprecation")
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
		User user = userService.readUserByAccount("test");
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
	public void testReadAccessUserControlByAccount() {
		AccessUserControl accessUserControl = accessUserService.readAccessUserControlByAccount("34E5A90E71D5eD93");
		double remainingMoney = accessUserControl.getRemainingMoney();
		System.out.println("remainingMoney: " + remainingMoney);
	}
	
	@Test
	public void testReadAccessInterfaceByIdentity() {
		AccessInterface accessInterface = accessUserService
				.readAccessInterfaceByIdentity("ESController_readFinancialLogisticsFilterDatas");
		System.out.println("id: " + accessInterface.getId());
		System.out.println("url: " + accessInterface.getUrl());
		System.out.println("money: " + accessInterface.getMoney());
	}
	
	@Test
	public void testReadAccessInterfaceByUrl() {
		AccessInterface accessInterface = accessUserService.readAccessInterfaceByUrl("/logistics/search");
		System.out.println("id: " + accessInterface.getId());
		System.out.println("url: " + accessInterface.getUrl());
		System.out.println("money: " + accessInterface.getMoney());
	}
	
	@Test
	public void testReadAccessUserInterface() {
		AccessUserInterface accessUserInterface = accessUserService
				.readAccessUserInterfaceByAccountAndInterfaceId("test", 1L);
		System.out.println(accessUserInterface.getMonies().size());
		for (AccessUserInterfaceMoney money : accessUserInterface.getMonies()) {
			System.out.println("responseCode: " + money.getResponseCode());
			System.out.println("money: " + money.getMoney());
		}
	}
	
	@Test
	public void testA() {
		RedisClusterUtils.getInstance().listPush("listOne", "test");
		System.out.println("list data: " + (String) RedisClusterUtils.getInstance().listPop("listOne"));
		WebResult webResult = new WebResult();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", new User());
		data.add(map);
		webResult.setData(data);
		RedisClusterUtils.getInstance().listPush("listOne", webResult);
		System.out.println(RedisClusterUtils.getInstance().listLength("listOne"));
		WebResult returnObject = (WebResult) RedisClusterUtils.getInstance().listPop("listOne");
		System.out.println(returnObject.getData());
		System.out.println("!@!!@@");
		try {
			RedisClusterUtils.getInstance().getJedisCluster().subscribe(
					new BinaryJedisPubSub() {
						@Override
						public void onMessage(byte[] channel, byte[] message) {
							System.out.println("channel: " + new String(channel));
							try {
								System.out.println("message: " + SerializerUtils.read(message));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
					}, SerializerUtils.write("webresult"));
			Long code = RedisClusterUtils.getInstance().getJedisCluster().publish(
					SerializerUtils.write("webresult"), SerializerUtils.write(webResult));
			System.out.println("code: " + code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testB() {
//		int status = (int) RedisClusterUtils.getInstance().get("education_organizeD");
//		System.err.println(status);
//		RedisClusterUtils.getInstance().set("education_organizeD", 1);
//		int status1 = (int) RedisClusterUtils.getInstance().get("education_organizeD");
//		int status2 = (int) RedisClusterUtils.getInstance().get("PhoneBank11_14");
//		int status3 = (int) RedisClusterUtils.getInstance().get("readMultiPlatform");
//		System.err.println(status1);
//		System.err.println(status2);
//		System.err.println(status3);
		Set<String> sensitiveWords = RedisClusterUtils.getInstance().getJedisCluster()
				.smembers("sensitive_word");
		for (String sensitiveWord : sensitiveWords) {
			System.out.println(sensitiveWord);
		}
	}
	
}
