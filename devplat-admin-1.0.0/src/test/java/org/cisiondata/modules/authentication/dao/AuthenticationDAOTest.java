package org.cisiondata.modules.authentication.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.auth.dao.UserDAO;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.user.dao.AdminUserDAO;
import org.cisiondata.modules.user.dao.TestInfoDAO;
import org.cisiondata.modules.user.entity.AdminUser;
import org.cisiondata.modules.user.entity.TestInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext.xml"})
public class AuthenticationDAOTest {

	@Resource(name = "userDAO")
	private UserDAO userDAO = null;


	@Resource(name = "aadminUserDAO")
	private AdminUserDAO adminUserDAO = null;
	

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
	public void test1() {
		Map<String,Object> params = null;
		params = new HashMap<String,Object>();
		params.put("begin", 1);
		params.put("pageSize", 3);
		List<AdminUser> list = adminUserDAO.findByCondition(params);
		System.out.println(111);

	}

	
}
