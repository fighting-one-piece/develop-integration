package org.cisiondata.modules.boot.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({JDBCConfiguration.class})
public class MainConfiguration {
	
	

}
