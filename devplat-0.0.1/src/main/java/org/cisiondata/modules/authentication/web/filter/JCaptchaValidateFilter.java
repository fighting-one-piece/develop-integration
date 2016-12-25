package org.cisiondata.modules.authentication.web.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.cisiondata.modules.authentication.web.jcaptcha.JCaptcha;

/**
 * 验证码过滤器
 */
public class JCaptchaValidateFilter extends AccessControlFilter {

	//是否开启验证码支持  
    private boolean jcaptchaEbabled = true;
    //验证码参数名  
    private String jcaptchaParam = "jcaptchaCode";
    //验证失败后存储到的属性名
    private String failureKeyAttribute = "shiroLoginFailure"; 

    private String jcapatchaErrorUrl = null;

    public void setJcaptchaEbabled(boolean jcaptchaEbabled) {
        this.jcaptchaEbabled = jcaptchaEbabled;
    }

    public void setJcaptchaParam(String jcaptchaParam) {
        this.jcaptchaParam = jcaptchaParam;
    }

    public void setJcapatchaErrorUrl(String jcapatchaErrorUrl) {
        this.jcapatchaErrorUrl = jcapatchaErrorUrl;
    }

    public void setFailureKeyAttribute(String failureKeyAttribute) {
		this.failureKeyAttribute = failureKeyAttribute;
	}

	public String getJcapatchaErrorUrl() {
        return jcapatchaErrorUrl;
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
    	//设置验证码是否开启属性，页面可以根据该属性来决定是否显示验证码
        request.setAttribute("jcaptchaEbabled", jcaptchaEbabled);
        return super.onPreHandle(request, response, mappedValue);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest httpServletRequest =  WebUtils.toHttp(request);
        //验证码禁用或不是表单提交允许访问
        if (jcaptchaEbabled == false || !"post".equals(httpServletRequest.getMethod().toLowerCase())) {
            return true;
        }
        //此时是表单提交，验证验证码是否正确
        return JCaptcha.validateResponse(httpServletRequest, httpServletRequest.getParameter(jcaptchaParam));
    }


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//    	redirectToLogin(request, response);
//      WebUtils.getAndClearSavedRequest(request);
//    	WebUtils.redirectToSavedRequest(request, response, getJcapatchaErrorUrl());
    	//如果验证码失败了，存储失败key属性  
        request.setAttribute(failureKeyAttribute, "验证码不正确"); 
        return true;
    }

    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        WebUtils.issueRedirect(request, response, getJcapatchaErrorUrl());
    }

}
