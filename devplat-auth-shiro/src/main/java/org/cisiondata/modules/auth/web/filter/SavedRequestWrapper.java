package org.cisiondata.modules.auth.web.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.util.SavedRequest;

public class SavedRequestWrapper extends SavedRequest {

	private static final long serialVersionUID = 1L;
	
	private String scheme = null;
	
	private String domain = null;
	
	private int port = 0;
	
	private String contextPath = null;
	
	private String redirectUrl = null;

	public SavedRequestWrapper(HttpServletRequest request, String redirectUrl) {
		super(request);
		this.scheme = request.getScheme();
		this.domain = request.getServerName();  
		this.port = request.getServerPort();
		this.contextPath = request.getContextPath();
		this.redirectUrl = redirectUrl;
	}
	
	public String getRequestUrl() {  
        String requestURI = getRequestURI();  
        
        if(null != redirectUrl) {
            if(redirectUrl.toLowerCase().startsWith("http://") || 
            		redirectUrl.toLowerCase().startsWith("https://")) {  
                return redirectUrl;  
            } else if(!redirectUrl.startsWith(contextPath)) { 
                requestURI = contextPath + redirectUrl;  
            } else {
                requestURI = redirectUrl;  
            }  
        }  
        StringBuilder requestUrl = new StringBuilder(scheme);
        requestUrl.append("://").append(domain);
        if("http".equalsIgnoreCase(scheme) && port != 80) {  
            requestUrl.append(":").append(String.valueOf(port));  
        } else if("https".equalsIgnoreCase(scheme) && port != 443) {  
            requestUrl.append(":").append(String.valueOf(port));  
        }  
        requestUrl.append(requestURI);  
        if (redirectUrl == null && getQueryString() != null) {  
            requestUrl.append("?").append(getQueryString());  
        }  
        return requestUrl.toString();  
    }  

}
