package org.cisiondata.modules.websocket.config;

import javax.annotation.Resource;

import org.cisiondata.modules.websocket.handler.MessagePushHandler;
import org.cisiondata.modules.websocket.interceptor.MessagePushHandshakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
@Configuration  
@EnableWebMvc  
@EnableWebSocket
public class WebSocketConfigurerAdapter extends WebMvcConfigurerAdapter implements WebSocketConfigurer {  
  
	@Resource(name = "messagePushHandler")
	private MessagePushHandler messagePushHandler = null;
	
	@Resource(name = "messagePushHandshakeInterceptor")
	private MessagePushHandshakeInterceptor messagePushHandshakeInterceptor = null;
	
    @Override  
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {  
    	/** websocket形式连接, client连接 */
        registry.addHandler(messagePushHandler, "/socketserver").addInterceptors(messagePushHandshakeInterceptor);  
        /** 不支持websocket的，采用sockjs */
        registry.addHandler(messagePushHandler, "/socketserver/sockjs").addInterceptors(messagePushHandshakeInterceptor)
        	.setAllowedOrigins("*").withSockJS();  
    }  
      
    @Bean  
    public WebSocketHandler messagePushHandler(){  
        return new MessagePushHandler();  
    }  
    
    @Bean
    public HandshakeInterceptor messagePushHandshakeInterceptor() {
    	return new MessagePushHandshakeInterceptor();
    }

}
