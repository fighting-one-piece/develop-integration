package org.cisiondata.modules.auth.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
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

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.auth.Constants;
import org.cisiondata.modules.auth.entity.AuthUser;
import org.cisiondata.modules.auth.service.IAuthService;
import org.cisiondata.modules.auth.web.session.SessionManager;
import org.cisiondata.utils.cache.CacheKey;
import org.cisiondata.utils.cache.CacheUtils;
import org.cisiondata.utils.date.DateFormatter;
import org.cisiondata.utils.endecrypt.SHAUtils;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.cisiondata.utils.token.TokenUtils;
import org.cisiondata.utils.web.IPUtils;
import org.cisiondata.utils.web.URLFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 只针对用户的认证、授权
 */
public class UserAuthFilter implements Filter {

	private Logger LOG = LoggerFactory.getLogger(UserAuthFilter.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	private IAuthService authService = null;

	private SessionManager sessionManager = null;

	private Set<String> notAuthenticationUrls = null;

	private Set<String> mustAuthenticationUrls = null;

	public void init(FilterConfig config) throws ServletException {
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		authService = wac.getBean(IAuthService.class);
		sessionManager = wac.getBean(SessionManager.class);
		notAuthenticationUrls = URLFilter.notAuthenticationUrls();
		mustAuthenticationUrls = URLFilter.mustAuthenticationUrls();
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String requestUrl = httpServletRequest.getServletPath();
		LOG.info("client current request url: {}", requestUrl);
		if (requestUrl.startsWith("/api/v1")) requestUrl = requestUrl.replace("/api/v1", "");
		if (!notAuthenticationUrls.contains(requestUrl)) {
			try {
				if (!authenticationRequest(requestUrl, httpServletRequest, httpServletResponse)) {
					writeResponse(httpServletResponse, wrapperFailureWebResult(
							ResultCode.VERIFICATION_USER_FAIL.getCode(), ResultCode.VERIFICATION_USER_FAIL.getDesc()));
					return;
				}
			} catch (BusinessException be) {
				writeResponse(httpServletResponse, wrapperFailureWebResult(be.getCode(), be.getMessage()));
				return;
			}
			if (!authorizationRequest(requestUrl, httpServletRequest, httpServletResponse)) {
				writeResponse(httpServletResponse, wrapperFailureWebResult(ResultCode.RESOURCE_NOT_PERMISSION.getCode(), ResultCode.RESOURCE_NOT_PERMISSION.getDesc()));
				return;
			}
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
	}

	private boolean authenticationRequest(String requestUrl, HttpServletRequest request, 
			HttpServletResponse response) throws BusinessException {
		if (requestUrl.startsWith("/app/api/v1")) {
			return authenticationExternalRequest(request, response);
		} else if (requestUrl.startsWith("/ext")) {
			return authenticationExternalRequest(request, response);
		} else {
			return authenticationWebRequest(requestUrl, request, response);
		}
	}

	private boolean authenticationWebRequest(String requestUrl, HttpServletRequest request,
			HttpServletResponse response) throws BusinessException {
		String accessToken = request.getHeader("accessToken");
		LOG.info("client access token: {}", accessToken);
		try {
			Object cacheObject = RedisClusterUtils.getInstance().get(accessToken);
			if (null == cacheObject) return false;
			String sessionId = (String) cacheObject;
			Object userObject = sessionManager.getStorageHandler().getAttribute(sessionId, request, 
					response, Constants.SESSION_CURRENT_USER);
			if (null == userObject) return false;
			AuthUser user = (AuthUser) userObject;
			String account = user.getAccount();
			String ip = IPUtils.getIPAddress(request);
			String currentDate = DateFormatter.DATE.get().format(new Date());
			String cacheKey = CacheUtils.genCacheKey(CacheKey.USER.ACCOUNT, account);
			LOG.info("account: " + account + " IP: " + ip);
			if (mustAuthenticationUrls.contains(requestUrl) && TokenUtils.isAuthenticationMD5Token(
					accessToken, account, user.getPassword(), ip, currentDate)) {
				RedisClusterUtils.getInstance().expire(cacheKey, 1800);
				RedisClusterUtils.getInstance().expire(accessToken, 1800);
				return true;
			}
			String authorizationToken = TokenUtils.genAuthorizationMD5Token(account, 
					user.getPassword(), ip, currentDate);
			if (!accessToken.startsWith(authorizationToken)) {
				return false;
			}
			String userSessionId = (String) RedisClusterUtils.getInstance().hget(cacheKey, "sessionId");
			if (!accessToken.equals(authorizationToken + TokenUtils.gen16MD5Token(userSessionId))) {
				throw new BusinessException(ResultCode.VERIFICATION_USER_SESSION_FAIL);
			}
			RedisClusterUtils.getInstance().expire(cacheKey, 1800);
			RedisClusterUtils.getInstance().expire(accessToken, 1800);
			return true;
		} catch (BusinessException be) {
			LOG.error(be.getMessage(), be);
			throw be;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	private boolean authenticationExternalRequest(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String[]> requestParams = request.getParameterMap();
		Map<String, String> params = new HashMap<String, String>();
		for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
			params.put(entry.getKey(), entry.getValue()[0]);
		}
		String accessId = params.get("accessId");
		String accessKey = "8C76B8c03109a971153C887C965eA008";
		try {
			if (!"6DD6539EC4E92759".equals(accessId)) {
				accessKey = authService.readUserAccessKeyByAccessId(accessId);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return false;
		}
		params.put("accessKey", accessKey);
		params.put("date", DateFormatter.DATE.get().format(Calendar.getInstance().getTime()).replace("-", ""));
		List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(params.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
			@Override
			public int compare(Entry<String, String> o1, Entry<String, String> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		String accessToken = null;
		StringBuilder sb = new StringBuilder(100);
		for (Map.Entry<String, String> entry : list) {
			String paramName = entry.getKey();
			if ("token".equalsIgnoreCase(paramName)) {
				accessToken = entry.getValue();
				continue;
			}
			sb.append(paramName).append("=").append(entry.getValue()).append("&");
		}
		if (sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		LOG.info("access token: {}", SHAUtils.SHA1(sb.toString()));
		return !SHAUtils.SHA1(sb.toString()).equals(accessToken) ? false : true;
	}
	
	@SuppressWarnings("unchecked")
	private boolean authorizationRequest(String requestUrl, HttpServletRequest request, HttpServletResponse response) {
		if (requestUrl.startsWith("/app")) {
			return true;
		} else if (requestUrl.startsWith("/ext")) {
			return authService.readPermissionFromExternalRequest(requestUrl, request.getParameterMap());
		} else {
			return authService.readPermissionFromWebRequest(requestUrl);
		}
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
