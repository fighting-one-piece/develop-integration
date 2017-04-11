package org.cisiondata.modules.auth.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.auth.dao.UserDAO;
import org.cisiondata.modules.auth.entity.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Resource(name = "userDAO")
	private UserDAO userDAO = null;
	
	@ResponseBody
	@RequestMapping(value = "/users/{userId}")
	public User readUserById(@PathVariable Long userId) {
		return userDAO.readDataByPK(userId);
	}
	
}
