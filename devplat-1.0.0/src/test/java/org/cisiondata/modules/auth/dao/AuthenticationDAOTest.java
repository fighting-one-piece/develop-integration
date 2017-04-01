package org.cisiondata.modules.auth.dao;

import java.util.List;

import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.auth.entity.Resource;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.auth.entity.UserAttribute;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class AuthenticationDAOTest {

	@javax.annotation.Resource(name = "userDAO")
	private UserDAO userDAO = null;
	
	@javax.annotation.Resource(name = "userIntegrationDAO")
	private UserIntegrationDAO userIntegrationDAO = null;
	
	@javax.annotation.Resource(name = "resourceIntegrationDAO")
	private ResourceIntegrationDAO resourceIntegrationDAO = null;
	
	@Test
	public void testAddUser() {
		User user = new User();
		user.setAccount("zhangsan");
		user.setPassword("zhangsan");
		user.setSalt("cisiondata");
		user.setFirstLoginFlag(false);
		user.setQuestion("what is your name?");
		userIntegrationDAO.insert(user);
	}
	
	@Test
	public void testReadUserById() {
		User user = userIntegrationDAO.readDataByPK(89L);
		System.out.println(user.getAccount());
		System.out.println(user.getPassword());
		System.out.println(user.getSalt());
		System.out.println(user.getFirstLoginFlag());
		System.out.println(user.getQuestion());
		for (UserAttribute attribute : user.getAttributes()) {
			System.out.println("attribute id: " + attribute.getUserId());
			System.out.println("attribute key: " + attribute.getKey());
			System.out.println("attribute value: " + attribute.getValue());
			System.out.println("attribute type: " + attribute.getType());
		}
	}
	
	@Test
	public void testReadUserByAccount() {
		Query query = new Query();
		query.addCondition("account", "zhangsan");
		User user = userIntegrationDAO.readDataByCondition(query);
		System.out.println(user.getAccount());
		System.out.println(user.getPassword());
		System.out.println(user.getSalt());
		System.out.println(user.getFirstLoginFlag());
		System.out.println(user.getQuestion());
		for (UserAttribute attribute : user.getAttributes()) {
			System.out.println("attribute id: " + attribute.getUserId());
			System.out.println("attribute key: " + attribute.getKey());
			System.out.println("attribute value: " + attribute.getValue());
			System.out.println("attribute type: " + attribute.getType());
		}
	}
	
	@Test
	public void testReadUserListByCondition() {
		Query query = new Query();
		List<User> users = userIntegrationDAO.readDataListByCondition(query);
		for (User user : users) {
			System.out.println(user.getAccount());
			System.out.println(user.getPassword());
			System.out.println(user.getSalt());
			System.out.println(user.getFirstLoginFlag());
			System.out.println(user.getQuestion());
			for (UserAttribute attribute : user.getAttributes()) {
				System.out.println("attribute id: " + attribute.getUserId());
				System.out.println("attribute key: " + attribute.getKey());
				System.out.println("attribute value: " + attribute.getValue());
				System.out.println("attribute type: " + attribute.getType());
			}
			userIntegrationDAO.update(user);
			System.out.println("#######");
		}
	}
	
	@Test
	public void testReadResourceListByCondition() {
		Query query = new Query();
		query.addCondition("deleteFlag", false);
		List<Resource> resources = resourceIntegrationDAO.readDataListByCondition(query);
		for (int i = 0, len = resources.size(); i < len; i++) {
			Resource resource = resources.get(i);
			System.out.println(resource.getUrl());
			System.out.println(resource.getName());
			System.out.println(resource.getIdentity());
		}
	}
}
