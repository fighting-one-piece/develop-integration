package org.cisiondata.modules.log.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.cisiondata.utils.web.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.GsonBuilder;

public class AccessLogFilter implements Filter {
	
	private Logger LOG = LoggerFactory.getLogger("ACCESS_LOG");
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String jsessionId = httpServletRequest.getRequestedSessionId();
        String ip = IpUtils.getIpAddr(httpServletRequest);
        String accept = httpServletRequest.getHeader("accept");
        String userAgent = httpServletRequest.getHeader("User-Agent");
        String url = httpServletRequest.getRequestURI();
        String params = getParams(httpServletRequest);
        String headers = getHeaders(httpServletRequest);

        StringBuilder sb = new StringBuilder();
        sb.append(getBlock(jsessionId));
        sb.append(getBlock(ip));
        sb.append(getBlock(accept));
        sb.append(getBlock(userAgent));
        sb.append(getBlock(url));
        sb.append(getBlock(params));
        sb.append(getBlock(headers));
        sb.append(getBlock(httpServletRequest.getHeader("Referer")));
        LOG.info(sb.toString());
        
        chain.doFilter(request, response);
	}
	
	 public static String getBlock(Object msg) {
	        if (msg == null) {
	            msg = "";
	        }
	        return "[" + msg.toString() + "]";
	    }

    @SuppressWarnings("unchecked")
	protected static String getParams(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        return new GsonBuilder().serializeSpecialFloatingPointValues()
				.setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(params);
    }

	@SuppressWarnings("unchecked")
	private static String getHeaders(HttpServletRequest request) {
        Map<String, List<String>> headers = Maps.newHashMap();
        Enumeration<String> namesEnumeration = request.getHeaderNames();
        while(namesEnumeration.hasMoreElements()) {
            String name = namesEnumeration.nextElement();
            Enumeration<String> valueEnumeration = request.getHeaders(name);
            List<String> values = Lists.newArrayList();
            while(valueEnumeration.hasMoreElements()) {
                values.add(valueEnumeration.nextElement());
            }
            headers.put(name, values);
        }
        return new GsonBuilder().serializeSpecialFloatingPointValues()
				.setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(headers);
    }


}
