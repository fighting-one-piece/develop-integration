package org.cisiondata.modules.auth.web.filter;

import java.io.IOException;

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
import org.cisiondata.utils.pub.SystemUtils;
import org.cisiondata.utils.web.IPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 只针对用户访问
 */
public class UserAccessFilter implements Filter {

	public Logger LOG = LoggerFactory.getLogger(UserAccessFilter.class);
	
	private String currentEnv = null;

	private ObjectMapper objectMapper = new ObjectMapper();

	public void init(FilterConfig config) throws ServletException {
		this.currentEnv = SystemUtils.getCurrentEnv();
		LOG.info("current env: {}", currentEnv);
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String ip = IPUtils.getIPAddress(httpServletRequest);
		String requestUrl = httpServletRequest.getServletPath();
		if ("production".equals(currentEnv) && requestUrl.startsWith("/app/api/v1")) {
			if (!("120.76.47.147".equals(ip) || "120.24.239.141".equals(ip))) {
				writeResponse(httpServletResponse, wrapperFailureWebResult(ResultCode.IP_NOT_BINDING, ResultCode.IP_NOT_BINDING.getDesc()));
				return;
			}
		}
		// ip为国外IP则限制访问
		boolean isForeign = false;
		if (isForeign) {
			writeResponse(httpServletResponse, wrapperFailureWebResult(ResultCode.IP_NOT_ACCESS, ResultCode.IP_NOT_ACCESS.getDesc()));
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
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

}
