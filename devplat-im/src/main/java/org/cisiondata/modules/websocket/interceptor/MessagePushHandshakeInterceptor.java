package org.cisiondata.modules.websocket.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Component("messagePushHandshakeInterceptor")
public class MessagePushHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
	
	private Logger LOG = LoggerFactory.getLogger(MessagePushHandshakeInterceptor.class);

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		LOG.info("start message push handshake interceptor");
		// 解决The extension [x-webkit-deflate-frame] is not supported问题
        if (request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
            request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
        }
        List<String> values = request.getHeaders().get("accessToken");
        for (String value : values) {
        	System.out.println(value);
        }
        attributes.put("query", request.getURI().getQuery());
        attributes.put("remoteIp", request.getRemoteAddress().getAddress().getHostAddress());
		return super.beforeHandshake(request, response, wsHandler, attributes);
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		LOG.info("end message push handshake interceptor");
		super.afterHandshake(request, response, wsHandler, ex);
	}
	
	private HttpSession getSession(ServerHttpRequest request) {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
			return serverRequest.getServletRequest().getSession(isCreateSession());
		}
		return null;
	}
	
}
