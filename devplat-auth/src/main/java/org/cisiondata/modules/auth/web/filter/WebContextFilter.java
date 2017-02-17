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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class WebContextFilter implements Filter {
	
	private ServletContext ctx = null;
	private WebApplicationContext wac = null;

	public void init(FilterConfig config) throws ServletException {
		ctx = config.getServletContext();
		wac = WebApplicationContextUtils.getWebApplicationContext(ctx);
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		SessionManager sessionManager = wac.getBean(SessionManager.class);
		Session session = sessionManager.getSession((HttpServletRequest) req, (HttpServletResponse) res);
		WebContext instance = new WebContext((HttpServletRequest) req, (HttpServletResponse) res, session, ctx);
		try {
			WebContext.set(instance);
			chain.doFilter(req, res);
		} finally {
			WebContext.set(null);
		}
	}

}
