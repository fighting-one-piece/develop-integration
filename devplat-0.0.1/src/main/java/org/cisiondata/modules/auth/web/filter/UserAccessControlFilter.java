package org.cisiondata.modules.auth.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.cisiondata.modules.auth.Constants;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.auth.service.IAuthService;
import org.cisiondata.utils.date.DateFormatter;
import org.cisiondata.utils.encryption.SHAUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAccessControlFilter extends AccessControlFilter {
	
	private Logger LOG = LoggerFactory.getLogger(UserAccessControlFilter.class);
	
    private IAuthService authService = null;

    /** 用户删除了后重定向的地址 */
    private String userNotFoundUrl = null;
    /** 用户锁定后重定向的地址 */
    @SuppressWarnings("unused")
	private String userBlockedUrl = null;
    /** 未知错误 */
    private String userUnknownErrorUrl = null;
    
	public void setAuthService(IAuthService authService) {
		this.authService = authService;
	}

    public void setUserNotFoundUrl(String userNotFoundUrl) {
        this.userNotFoundUrl = userNotFoundUrl;
    }

    public void setUserBlockedUrl(String userBlockedUrl) {
        this.userBlockedUrl = userBlockedUrl;
    }

    public void setUserUnknownErrorUrl(String userUnknownErrorUrl) {
        this.userUnknownErrorUrl = userUnknownErrorUrl;
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
    	Subject subject = getSubject(request, response);
        if (null != subject) {
            String account = (String) subject.getPrincipal();
            User user = authService.readUserByAccount(account);
            //把当前用户放到session中
            request.setAttribute(Constants.CURRENT_USER, user);
            ((HttpServletRequest)request).getSession().setAttribute(Constants.CURRENT_USER, user);
            ((HttpServletRequest)request).getSession().setAttribute(Constants.CURRENT_USERNAME, account);
            if (null != user && StringUtils.isNotBlank(user.getNickname())) {
            	((HttpServletRequest)request).getSession().setAttribute(Constants.CURRENT_NICKNAME, user.getNickname());
            }
        }
    	return super.onPreHandle(request, response, mappedValue);
    }

	@Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
    	HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    	String requestURI = httpServletRequest.getServletPath();
    	LOG.info("request path : {}", httpServletRequest.getServletPath());
    	if (requestURI.startsWith("/app")) {
    		if (!authenticationAppRequest(httpServletRequest)) {
    			throw new AuthenticationException("该请求被拒绝访问");
    		}
    	} else if (requestURI.startsWith("/ext")){
    		if (!authenticationExternalRequest(httpServletRequest)) {
    			throw new AuthenticationException("该请求被拒绝访问");
    		}
    	} else {
    		String readIdentity = authService.readResourceReadIdentityByUrl(requestURI);
    		if (StringUtils.isNotBlank(readIdentity)) {
    			boolean isPermitted = SecurityUtils.getSubject().isPermitted(readIdentity);
    			LOG.info("identity {} is permitted : {}", readIdentity, isPermitted);
    			if (!isPermitted) {
    				request.setAttribute("error", "该请求被拒绝访问");
    				saveRequestAndRedirectToLogin(request, response);
    			}
    		}
    	}
        User user = (User) request.getAttribute(Constants.CURRENT_USER);
        if (user == null) return true;
        if (Boolean.TRUE.equals(user.getDeleteFlag())) {
            getSubject(request, response).logout();
            saveRequestAndRedirectToLogin(request, response);
            return false;
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        getSubject(request, response).logout();
        saveRequestAndRedirectToLogin(request, response);
        return true;
    }

    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
    	User user = (User) request.getAttribute(Constants.CURRENT_USER);
        String url = user.hasDeleted() ? this.userNotFoundUrl : this.userUnknownErrorUrl;
    	WebUtils.getAndClearSavedRequest(request);
    	WebUtils.redirectToSavedRequest(request, response, url);
//        WebUtils.issueRedirect(request, response, url);
    }
    
    private boolean authenticationAppRequest(HttpServletRequest httpServletRequest) {
    	String token = httpServletRequest.getParameter("token");
		return (StringUtils.isBlank(token) || !"cisiondata".equals(token)) ? false : true; 
    }
    
    @SuppressWarnings("unchecked")
	private boolean authenticationExternalRequest(HttpServletRequest httpServletRequest) {
    	Map<String, String[]> requestParams = httpServletRequest.getParameterMap();
    	Map<String, String> params = new HashMap<String, String>();
    	for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
			params.put(entry.getKey(), entry.getValue()[0]);
    	}
    	params.put("accessKey", "cisiondata");
    	params.put("date", DateFormatter.DATE.get().format(Calendar.getInstance().getTime()).replace("-", ""));
    	List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(params.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
			@Override
			public int compare(Entry<String, String> o1, Entry<String, String> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		String token = null;
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : list) {
			String paramName = entry.getKey();
			if ("token".equalsIgnoreCase(paramName)) {
				token = entry.getValue();
				continue;
			}
			sb.append(paramName).append("=").append(entry.getValue()).append("&");
		}
		if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
		LOG.info("params: {}", sb.toString());
		LOG.info("token: {}", SHAUtils.SHA1(sb.toString()));
		return SHAUtils.SHA1(sb.toString()).equals(token) ? true : false;
    }

}
