package org.cisiondata.modules.bootstrap.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.bootstrap.config.JDBCConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/demo")
public class DemoController {

	@Resource(name = "jdbcConfiguration")
	private JDBCConfiguration jdbcConfiguration = null; 
	
	@ResponseBody
	@RequestMapping(value = "/hello")
	public String sayHello() {
		return "hello!" + jdbcConfiguration.getUsername();
	}
	
}
