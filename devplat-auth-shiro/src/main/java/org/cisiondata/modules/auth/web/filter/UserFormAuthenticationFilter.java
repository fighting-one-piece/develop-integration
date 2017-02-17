package org.cisiondata.modules.auth.web.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.cisiondata.modules.auth.service.IUserService;
import org.cisiondata.modules.auth.web.jcaptcha.JCaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 1、onLoginFailure 时把异常添加到request attribute中 而不是异常类名
 * 2、登录成功时：成功页面重定向：
 * 2.1、如果前一个页面是登录页面，-->2.3
 * 2.2、如果有SavedRequest 则返回到SavedRequest
 * 2.3、否则根据当前登录的用户决定返回到管理员首页/前台首页
 */
public class UserFormAuthenticationFilter extends FormAuthenticationFilter {

	private Logger LOG = LoggerFactory.getLogger(UserFormAuthenticationFilter.class);
	
    @SuppressWarnings("unused")
	private IUserService userService = null;
    
    /** 默认的成功地址 */
    private String defaultSuccessUrl = null;
    /** 管理员默认的成功地址 */
    private String adminDefaultSuccessUrl = null;
    
    public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setDefaultSuccessUrl(String defaultSuccessUrl) {
        this.defaultSuccessUrl = defaultSuccessUrl;
    }

    public void setAdminDefaultSuccessUrl(String adminDefaultSuccessUrl) {
        this.adminDefaultSuccessUrl = adminDefaultSuccessUrl;
    }

    @Override
    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        request.setAttribute(getFailureKeyAttribute(), ae);
    }
    
    @Override
    protected AuthenticationToken createToken(String username, String password,
    		boolean rememberMe, String host) {
    	LOG.info("username:{} password:{} remberme:{} host:{}", username, password, rememberMe, host);
    	if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
    		throw new AuthenticationException("用户名或密码不能为空");
    	}
    	return super.createToken(username, password, rememberMe, host);
    }
    
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, 
    		ServletRequest request, ServletResponse response) throws Exception {
    	JCaptcha.captchaService.removeCaptcha(((HttpServletRequest) request).getRequestedSessionId());
    	WebUtils.getAndClearSavedRequest(request);
//    	WebUtils.redirectToSavedRequest(request, response, getSuccessUrl());
    	String redirectUrl = request.getParameter("redirectUrl");
    	if (StringUtils.isBlank(redirectUrl)) {
    		redirectUrl = getSuccessUrl();
    	}
    	WebUtils.redirectToSavedRequest(request, response, redirectUrl);
    	return false;
    }
    
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, 
    		Object mappedValue) throws Exception {
    	if(request.getAttribute(getFailureKeyAttribute()) != null) {  
            return true;  
        }
    	return super.onAccessDenied(request, response, mappedValue);
    }
    
    protected void saveRequest(ServletRequest request, String backUrl, String redirectUrl) {
    	Subject subject = SecurityUtils.getSubject();
    	Session session = subject.getSession();
    	session.setAttribute("authc.redirectUrl", redirectUrl);
    	HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
    	SavedRequest savedRequest = new SavedRequest(httpServletRequest);
    	session.setAttribute(WebUtils.SAVED_REQUEST_KEY, savedRequest);
    }
    
    protected String getDefaultBackUrl(HttpServletRequest request) {
    	String scheme = request.getScheme();
    	String domain = request.getServerName();  
        int port = request.getServerPort();  
        String contextPath = request.getContextPath();  
        StringBuilder backUrl = new StringBuilder(scheme);  
        backUrl.append("://").append(domain);  
        if("http".equalsIgnoreCase(scheme) && port != 80) {  
            backUrl.append(":").append(String.valueOf(port));  
        } else if("https".equalsIgnoreCase(scheme) && port != 443) {  
            backUrl.append(":").append(String.valueOf(port));  
        }  
        backUrl.append(contextPath).append(getSuccessUrl());  
        return backUrl.toString();  
    }
    
    /**
     * 根据用户选择成功地址
     * @return
     */
    @Override
    public String getSuccessUrl() {
    	boolean hasAdmin = SecurityUtils.getSubject().hasRole("admin");
    	return hasAdmin ? adminDefaultSuccessUrl : defaultSuccessUrl;
    }
    
}
