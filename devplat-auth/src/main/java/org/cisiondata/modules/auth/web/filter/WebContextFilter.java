package org.cisiondata.modules.auth.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cisiondata.modules.auth.web.WebContext;
import org.cisiondata.modules.auth.web.session.Session;
import org.cisiondata.modules.auth.web.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class WebContextFilter implements Filter {
	
	private Logger LOG = LoggerFactory.getLogger(WebContextFilter.class);
	
	private ServletContext ctx = null;
	private SessionManager sessionManager = null;

	public void init(FilterConfig config) throws ServletException {
		ctx = config.getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(ctx);
		sessionManager = wac.getBean(SessionManager.class);
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		Session session = sessionManager.getSession((HttpServletRequest) request, (HttpServletResponse) response);
		LOG.info("current session id : {}", session.getId());
		WebContext instance = new WebContext((HttpServletRequest) request, (HttpServletResponse) response, session, ctx);
		try {
			WebContext.set(instance);
			chain.doFilter(request, response);
		} finally {
			WebContext.set(null);
		}
	}
	
	public void destroy() {
	}

}
