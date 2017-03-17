package org.cisiondata.modules.auth.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.auth.Constants.SessionName;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.auth.service.IAuthService;
import org.cisiondata.modules.auth.web.WebContext;
import org.cisiondata.modules.auth.web.WebUtils;
import org.cisiondata.modules.auth.web.session.SessionManager;
import org.cisiondata.utils.date.DateFormatter;
import org.cisiondata.utils.endecrypt.SHAUtils;
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
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String requestUrl = httpServletRequest.getServletPath();
		if (requestUrl.startsWith("/api/v1")) requestUrl = requestUrl.replace("/api/v1", "");
		LOG.info("client request url: {}", requestUrl);
		LOG.info("current access account: {}", WebUtils.getCurrentAccout());
		LOG.info("ipAddress: {} macAddress: {}", IPUtils.getIPAddress(WebContext.get().getRequest()), 
				IPUtils.getMACAddress(WebContext.get().getRequest()));
		if (!filteredRequestUrls.contains(requestUrl)) {
			if (!authenticationRequest(httpServletRequest, httpServletResponse)) {
				writeResponse(httpServletResponse, wrapperFailureWebResult("用户认证失败,请重新登录"));
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
		filteredRequestUrls = new HashSet<String>();
		filteredRequestUrls.add("/login");
		filteredRequestUrls.add("/jcaptcha.jpg");
		filteredRequestUrls.add("/jcaptcha-validate");
		filteredRequestUrls.add("/verificationCode.jpg");
	}
	
	private boolean authenticationRequest(HttpServletRequest request, HttpServletResponse response) {
		String requestUrl = request.getServletPath();
		if (requestUrl.startsWith("/api/v1")) requestUrl = requestUrl.replace("/api/v1", "");
		if (requestUrl.startsWith("/app")) {
			return authenticationAppRequest(request, response);
		} else if (requestUrl.startsWith("/ext")) {
			return authenticationExternalRequest(request, response);
		} else {
			return authenticationWebRequest(request, response);
		}
	}
	
	private boolean authenticationWebRequest(HttpServletRequest request, HttpServletResponse response) {
		String accessToken = request.getHeader("accessToken");
		LOG.info("client access token: {}", accessToken);
		Object cacheObject = RedisClusterUtils.getInstance().get(accessToken);
		if (null == cacheObject) return false;
		String sessionId = (String) cacheObject;
		try {
			Object userObject = sessionManager.getStorageHandler().getAttribute(sessionId, 
					request, response, SessionName.CURRENT_USER);
			if (null == userObject) return false;
			User user = (User) userObject;
			String macAddress = IPUtils.getMACAddress(request);
			if (!TokenUtils.authenticationMD5Token(accessToken, user.getAccount(), user.getPassword(), macAddress)) {
				return false;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
	private boolean authenticationAppRequest(HttpServletRequest request, HttpServletResponse response)  {
    	String accessToken = request.getParameter("token");
		return StringUtils.isBlank(accessToken) || !"cisiondata".equals(accessToken) ? false : true;
    }
    
    @SuppressWarnings("unchecked")
	private boolean authenticationExternalRequest(HttpServletRequest request, HttpServletResponse response) {
    	Map<String, String[]> requestParams = request.getParameterMap();
    	Map<String, String> params = new HashMap<String, String>();
    	for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
			params.put(entry.getKey(), entry.getValue()[0]);
    	}
    	try {
	    	String accessKey = authService.readUserAccessKeyByAccessId(params.get("accessId"));
	    	params.put("accessKey", accessKey);
    	} catch (Exception e) {
    		LOG.error(e.getMessage(), e);
    		return false;
    	}
    	params.put("date", DateFormatter.DATE.get().format(Calendar.getInstance().getTime()).replace("-", ""));
    	List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(params.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
			@Override
			public int compare(Entry<String, String> o1, Entry<String, String> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		String accessToken = null;
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : list) {
			String paramName = entry.getKey();
			if ("token".equalsIgnoreCase(paramName)) {
				accessToken = entry.getValue();
				continue;
			}
			sb.append(paramName).append("=").append(entry.getValue()).append("&");
		}
		if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
		LOG.info("access token: {}", SHAUtils.SHA1(sb.toString()));
		return !SHAUtils.SHA1(sb.toString()).equals(accessToken) ? false : true;
    }
	
	private boolean authorizationRequest(String requestUrl) {
		return true;
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
	
	private WebResult wrapperFailureWebResult(String failure) {
		return wrapperFailureWebResult(ResultCode.FAILURE, failure);
	}
	
}
