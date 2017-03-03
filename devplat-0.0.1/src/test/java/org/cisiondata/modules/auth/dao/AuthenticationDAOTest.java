package org.cisiondata.modules.auth.dao;

import java.util.List;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.auth.entity.AccessInterface;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class AuthenticationDAOTest {

	@Resource(name = "userDAO")
	private UserDAO userDAO = null;
	
	@Resource(name = "accessInterfaceDAO")
	private AccessInterfaceDAO accessInterfaceDAO = null;
	
	@Test
	public void testUserDAORead() {
		Query query = new Query();
		List<User> users = userDAO.readDataListByCondition(query);
		System.out.println(users);
		for (User user : users) {
			System.out.println(user);
		}
	}
	
	@Test
	public void testReadAccessInterfaceList() {
		List<AccessInterface> list = accessInterfaceDAO.readDataListByCondition(new Query());
		for (AccessInterface accessInterface : list) {
			String urlCacheKey = "interface:url:" + accessInterface.getUrl();
			String identityCacheKey = "interface:identity:" + accessInterface.getIdentity();
			System.out.println("urlCacheKey: " + urlCacheKey + " identityCacheKey: " + identityCacheKey);
			RedisClusterUtils.getInstance().delete(urlCacheKey);
			RedisClusterUtils.getInstance().delete(identityCacheKey);
		}
	}
	
}
