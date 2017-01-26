package org.cisiondata.modules.auth.web.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.cisiondata.modules.auth.Constants;
import org.cisiondata.modules.auth.entity.User;
import org.cisiondata.modules.auth.service.IAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAccessControlFilter extends AccessControlFilter {
	
	private Logger LOG = LoggerFactory.getLogger(UserAccessControlFilter.class);
	
	/** 认证授权服务*/
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
		String readIdentity = authService.readResourceReadIdentityByUrl(requestURI);
		if (StringUtils.isNotBlank(readIdentity)) {
			boolean isPermitted = SecurityUtils.getSubject().isPermitted(readIdentity);
			LOG.info("identity {} is permitted : {}", readIdentity, isPermitted);
			if (!isPermitted) {
				request.setAttribute(Constants.ERROR, "该请求被拒绝访问");
				saveRequestAndRedirectToLogin(request, response);
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
        String url = null == user || user.hasDeleted() ? this.userNotFoundUrl : this.userUnknownErrorUrl;
    	WebUtils.getAndClearSavedRequest(request);
    	WebUtils.redirectToSavedRequest(request, response, url);
    	/**
        WebUtils.issueRedirect(request, response, url);
    	*/
    }
    
}
