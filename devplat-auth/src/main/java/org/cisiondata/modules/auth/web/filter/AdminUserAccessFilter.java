package org.cisiondata.modules.auth.web.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.auth.Constants;
import org.cisiondata.modules.auth.entity.AAdminUser;
import org.cisiondata.modules.auth.web.session.SessionManager;
import org.cisiondata.utils.date.DateFormatter;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.cisiondata.utils.token.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AdminUserAccessFilter implements Filter {
	
	private Logger LOG = LoggerFactory.getLogger(AdminUserAccessFilter.class);
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private SessionManager sessionManager = null;
	
	private Set<String> notAuthenticationUrls = null;
	
	public void init(FilterConfig config) throws ServletException {
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		sessionManager = wac.getBean(SessionManager.class);
		initializeFilteredRequestUrl();
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String requestUrl = httpServletRequest.getServletPath();
		LOG.info("client request url: {}", requestUrl);
//		LOG.info("current access account: {}", WebUtils.getCurrentAdminAccout());
		if (requestUrl.startsWith("/api/v1")) requestUrl = requestUrl.replace("/api/v1", "");
		if (!notAuthenticationUrls.contains(requestUrl)) {
			if (!authenticationRequest(requestUrl, httpServletRequest, httpServletResponse)) {
				writeResponse(httpServletResponse, wrapperFailureWebResult(ResultCode.VERIFICATION_USER_FAIL,"用户认证失败,请重新登录"));
				return;
			}
			if (!authorizationRequest(requestUrl)) {
				
			}
		}
		
		chain.doFilter(request, response);
	}
	
	public void destroy() {
	}
	
	private void initializeFilteredRequestUrl() {
		notAuthenticationUrls = new HashSet<String>();
		notAuthenticationUrls.add("/login");
	}
	
	private boolean authenticationRequest(String requestUrl, HttpServletRequest request, HttpServletResponse response) {
		return authenticationWebRequest(requestUrl, request, response);
	}
	
	private boolean authenticationWebRequest(String requestUrl, HttpServletRequest request, HttpServletResponse response) {
		String accessToken = request.getHeader("accessToken");
		LOG.info("client access token: {}", accessToken);
		Object cacheObject = RedisClusterUtils.getInstance().get(accessToken);
		if (null == cacheObject) return false;
		String sessionId = (String) cacheObject;
		try {
			Object userObject = sessionManager.getStorageHandler().getAttribute(sessionId, 
					request, response, Constants.SESSION_CURRENT_USER);
			if (null == userObject) return false;
			AAdminUser adminUser = (AAdminUser) userObject;
//			String macAddress = IPUtils.getMACAddress(request);
			String currentDate = DateFormatter.DATE.get().format(new Date());
			if (!TokenUtils.isAdminAuthenticationMD5Token(accessToken, adminUser.getAccount(), currentDate)) {
				return false;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
	private boolean authorizationRequest(String requestUrl) {
		return true;
	}
	
	private void writeResponse(HttpServletResponse response, Object result) 
			throws JsonGenerationException, JsonMappingException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		response.getWriter().write(objectMapper.writeValueAsString(result));
	}
	
	private WebResult wrapperFailureWebResult(int code, String failure) {
		return new WebResult().buildFailure(code, failure);
	}
	
	private WebResult wrapperFailureWebResult(ResultCode resultCode, String failure) {
		return wrapperFailureWebResult(resultCode.getCode(), failure);
	}
	
	
}
