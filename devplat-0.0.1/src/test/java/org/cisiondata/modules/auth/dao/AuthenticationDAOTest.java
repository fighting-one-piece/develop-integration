package org.cisiondata.modules.auth.dao;

import java.util.List;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.auth.dao.UserDAO;
import org.cisiondata.modules.auth.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class AuthenticationDAOTest {

	@Resource(name = "userDAO")
	private UserDAO userDAO = null;
	
	@Test
	public void testUserDAORead() {
		Query query = new Query();
		List<User> users = userDAO.readDataListByCondition(query);
		System.out.println(users);
		for (User user : users) {
			System.out.println(user);
		}
	}
	
}
