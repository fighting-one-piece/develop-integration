package org.cisiondata.modules.bootstrap.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.bootstrap.config.JDBCConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/demo")
public class DemoController {

	@Resource(name = "jdbcConfiguration")
	private JDBCConfiguration jdbcConfiguration = null; 
	
	@ApiOperation(value="Say Hello", notes="requires noting")
	@ResponseBody
	@RequestMapping(value = "/hello")
	public String sayHello() {
		return "hello!" + jdbcConfiguration.getUsername();
	}
	
}
