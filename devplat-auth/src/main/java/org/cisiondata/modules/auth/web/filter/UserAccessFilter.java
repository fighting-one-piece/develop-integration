package org.cisiondata.modules.auth.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import org.cisiondata.modules.auth.Constants;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.auth.service.IAuthService;
import org.cisiondata.modules.auth.service.IPermissionService;
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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserAccessFilter implements Filter {
	
	private Logger LOG = LoggerFactory.getLogger(UserAccessFilter.class);
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private IAuthService authService = null;
	
	private SessionManager sessionManager = null;
	
	private IPermissionService permissionService = null;
	
	private Set<String> notAuthenticationUrls = null;
	
	private Set<String> mustAuthenticationUrls = null;
	
	public void init(FilterConfig config) throws ServletException {
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		authService = wac.getBean(IAuthService.class);
		sessionManager = wac.getBean(SessionManager.class);
		permissionService = wac.getBean(IPermissionService.class);
		initializeFilteredRequestUrl();
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String requestUrl = httpServletRequest.getServletPath();
		LOG.info("client request url: {}", requestUrl);
		LOG.info("current access account: {}", WebUtils.getCurrentAccout());
		if (requestUrl.startsWith("/api/v1")) requestUrl = requestUrl.replace("/api/v1", "");
		if (!notAuthenticationUrls.contains(requestUrl)) {
			if (!authenticationRequest(requestUrl, httpServletRequest, httpServletResponse)) {
				writeResponse(httpServletResponse, wrapperFailureWebResult(ResultCode.VERIFICATION_USER_FAIL,"用户认证失败,请重新登录"));
				return;
			}
			if (!authorizationRequest(requestUrl)) {
				
			}
			if (!jugdeUserPermission(requestUrl, httpServletRequest, httpServletResponse)) {
				writeResponse(httpServletResponse, wrapperFailureWebResult(999,"此路径没有权限"));
				return;
			}
		}
		chain.doFilter(request, response);
	}
	
	public void destroy() {
	}
	
	private void initializeFilteredRequestUrl() {
		notAuthenticationUrls = new HashSet<String>();
		notAuthenticationUrls.add("/login");
		notAuthenticationUrls.add("/jcaptcha.jpg");
		notAuthenticationUrls.add("/jcaptcha-validate");
		notAuthenticationUrls.add("/verificationCode.jpg");
		notAuthenticationUrls.add("/users/sms/verify");
		notAuthenticationUrls.add("/users/account/verify");
		mustAuthenticationUrls = new HashSet<String>();
		mustAuthenticationUrls.add("/users/sms");
		mustAuthenticationUrls.add("/users/settings/profile");
		mustAuthenticationUrls.add("/users/settings/security");
		mustAuthenticationUrls.add("/users/security/questions");
		mustAuthenticationUrls.add("/users/settings/security/verify");
		mustAuthenticationUrls.add("/users/settings/security/question");
	}
	private boolean jugdeUserPermission(String requestUrl, HttpServletRequest request, HttpServletResponse response){
		if (requestUrl.startsWith("/app")) {
			return true;
		} else if (requestUrl.startsWith("/ext")) {
			return true;
		} else {
			return permissionService.findPermissionCisionData(requestUrl, request, response);
	}
	}
	private boolean authenticationRequest(String requestUrl, HttpServletRequest request, HttpServletResponse response) {
		if (requestUrl.startsWith("/app")) {
			return authenticationAppRequest(request, response);
		} else if (requestUrl.startsWith("/ext")) {
			return authenticationExternalRequest(request, response);
		} else {
			return authenticationWebRequest(requestUrl, request, response);
		}
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
			User user = (User) userObject;
			String macAddress = IPUtils.getMACAddress(request);
			String currentDate = DateFormatter.DATE.get().format(new Date());
			if (mustAuthenticationUrls.contains(requestUrl) && TokenUtils.isAuthenticationMD5Token(
					accessToken, user.getAccount(), user.getPassword(), macAddress, currentDate)) {
					return true;
			} 
			if (!TokenUtils.isAuthorizationMD5Token(accessToken, user.getAccount(), 
					user.getPassword(), macAddress, currentDate)) {
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
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		response.getWriter().write(objectMapper.writeValueAsString(result));
	}
	
	private WebResult wrapperFailureWebResult(int code, String failure) {
		return new WebResult().buildFailure(code, failure);
	}
	
	private WebResult wrapperFailureWebResult(ResultCode resultCode, String failure) {
		return wrapperFailureWebResult(resultCode.getCode(), failure);
	}
	
	@SuppressWarnings("unused")
	private WebResult wrapperFailureWebResult(String failure) {
		return wrapperFailureWebResult(ResultCode.FAILURE, failure);
	}
	
}
