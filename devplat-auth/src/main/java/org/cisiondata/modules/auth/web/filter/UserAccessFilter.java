package org.cisiondata.modules.auth.web.filter;

import java.io.IOException;
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
import org.cisiondata.modules.auth.Constants.SessionName;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.auth.service.IAuthService;
import org.cisiondata.modules.auth.web.WebContext;
import org.cisiondata.modules.auth.web.WebUtils;
import org.cisiondata.modules.auth.web.session.SessionManager;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.cisiondata.utils.token.TokenUtils;
import org.cisiondata.utils.web.IPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserAccessFilter implements Filter {
	
	private Logger LOG = LoggerFactory.getLogger(UserAccessFilter.class);
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@SuppressWarnings("unused")
	private IAuthService authService = null;
	
	private SessionManager sessionManager = null;
	
	private Set<String> filteredRequestUrls = null;

	public void init(FilterConfig config) throws ServletException {
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		authService = wac.getBean(IAuthService.class);
		sessionManager = wac.getBean(SessionManager.class);
		initializeFilteredRequestUrl();
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String requestUrl = httpServletRequest.getServletPath();
		LOG.info("client request url: {}", requestUrl);
		LOG.info("current access account: {}", WebUtils.getCurrentAccout());
		LOG.info("ipAddress: {} macAddress: {}", IPUtils.getIPAddress(WebContext.get().getRequest()), 
				IPUtils.getMACAddress(WebContext.get().getRequest()));
		if (!filteredRequestUrls.contains(requestUrl)) {
			String accessToken = httpServletRequest.getHeader("accessToken");
			LOG.info("client access token: {}", accessToken);
			Object cacheObject = RedisClusterUtils.getInstance().get(accessToken);
			if (null == cacheObject) {
				writeResponse((HttpServletResponse) response, 
						wrapperFailureWebResult(ResultCode.FAILURE, "用户认证失败,请重新登录"));
				return;
			} else {
				LOG.info("cacheObject : {}", cacheObject);
				String sessionId = (String) cacheObject;
				try {
					Object userObject = sessionManager.getStorageHandler().getAttribute(sessionId, 
							httpServletRequest, (HttpServletResponse) response, SessionName.CURRENT_USER);
					if (null == userObject) {
						writeResponse((HttpServletResponse) response, 
								wrapperFailureWebResult(ResultCode.FAILURE, "用户认证失败,请重新登录"));
						return;
					} 
					User user = (User) userObject;
					String macAddress = IPUtils.getMACAddress(httpServletRequest);
					if (!TokenUtils.authenticationMD5Token(accessToken, user.getAccount(), user.getPassword(), macAddress)) {
						writeResponse((HttpServletResponse) response, 
								wrapperFailureWebResult(ResultCode.FAILURE, "用户认证失败,请重新登录"));
						return;
					}
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
					writeResponse((HttpServletResponse) response, 
							wrapperFailureWebResult(ResultCode.FAILURE, e.getMessage()));
					return;
				}
			}
		}
		
		chain.doFilter(request, response);
	}
	
	public void destroy() {
	}
	
	private void initializeFilteredRequestUrl() {
		filteredRequestUrls = new HashSet<String>();
		filteredRequestUrls.add("/login");
		filteredRequestUrls.add("/jcaptcha.jpg");
		filteredRequestUrls.add("/jcaptcha-validate");
	}
	
	private void writeResponse(HttpServletResponse response, Object result) 
			throws JsonGenerationException, JsonMappingException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		//objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
		response.getWriter().write(objectMapper.writeValueAsString(result));
	}
	
	private WebResult wrapperFailureWebResult(int code, String failure) {
		return new WebResult().buildFailure(code, failure);
	}
	
	private WebResult wrapperFailureWebResult(ResultCode resultCode, String failure) {
		return wrapperFailureWebResult(resultCode.getCode(), failure);
	}
	
}
