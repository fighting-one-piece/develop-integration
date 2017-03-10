package org.cisiondata.modules.boot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component("jdbcConfiguration")
@PropertySource("classpath:database/jdbc.properties")
@ConfigurationProperties(prefix = "jdbc")
public class JDBCConfiguration {

	private String username = null;
	
	private String password = null;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
