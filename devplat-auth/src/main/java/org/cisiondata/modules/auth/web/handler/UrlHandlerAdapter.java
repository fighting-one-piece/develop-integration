package org.cisiondata.modules.auth.web.handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.auth.entity.AccessUserControl;
import org.cisiondata.modules.auth.service.IAccessUserService;
import org.cisiondata.modules.auth.web.handler.UrlMappingStorage.Mapper;
import org.cisiondata.utils.clazz.ClassScanner;
import org.cisiondata.utils.clazz.ClassScanner.ClassResourceHandler;
import org.cisiondata.utils.clazz.ObjectMethodParams;
import org.cisiondata.utils.clazz.ParameterBinder;
import org.cisiondata.utils.date.DateFormatter;
import org.cisiondata.utils.endecrypt.SHAUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.MethodCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UrlHandlerAdapter implements HandlerAdapter, ApplicationContextAware, InitializingBean {
	
	private Logger LOG = LoggerFactory.getLogger(UrlHandlerAdapter.class);

	private ObjectMapper objectMapper = new ObjectMapper();
	
	private ApplicationContext ctx = null;
	
	private IAccessUserService accessUserService = null;
	
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.ctx = ctx;
	}
	
	public boolean supports(Object handler) {
		return true;
	}

	public long getLastModified(HttpServletRequest request, Object handler) {
		return -1;
	}
	
	@Override
	public void afterPropertiesSet() {
		new ClassScanner(new String[]{"org.cisiondata.modules"}, new ClassResourceHandler() {
			
			@SuppressWarnings("rawtypes")
			public void handle(MetadataReader metadata) {
				String className = metadata.getClassMetadata().getClassName();
				String[] baseUrl = null;
				if (!metadata.getAnnotationMetadata().hasAnnotation(Controller.class.getName()) || 
						!metadata.getAnnotationMetadata().hasAnnotation(RequestMapping.class.getName())) return;
				Map<String, Object> attributes = metadata.getAnnotationMetadata()
						.getAnnotationAttributes(RequestMapping.class.getName());
				if (attributes != null) {
					baseUrl = (String[]) attributes.get("value");
				}
				try {
					Class clazz = Class.forName(className);
					final Mapper mapper = new Mapper(baseUrl, clazz.newInstance());
					ReflectionUtils.doWithMethods(clazz, new MethodCallback() {
						public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
							if (Modifier.isPublic(method.getModifiers()) && method.isAnnotationPresent(RequestMapping.class)) {
								mapper.add(method);
							}
						}
					});
					UrlMappingStorage.addMapper(mapper);
					ReflectionUtils.doWithFields(clazz, new FieldCallback() {
						public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
							if (ctx.containsBean(field.getName()) && ctx.isTypeMatch(field.getName(), field.getType())) {
								ReflectionUtils.makeAccessible(field);
								ReflectionUtils.setField(field, mapper.getController(), ctx.getBean(field.getName()));
							}
						}
					});
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}).scan();
		this.accessUserService = (IAccessUserService) ctx.getBean("accessUserService");
	}
	
	@Override
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
			String path = request.getRequestURI().replace("/devplat", "");
			LOG.info("url handler adapter request path: {}", path);
			if (path.startsWith("/app")) {
				if (authenticationAppRequest(request, response)) {
					Object result = handleExternalRequest(path.replace("/app", ""), request, response);
					writeResponse(response, result);
				}
			} else if (path.startsWith("/ext")) {
				if (authenticationExternalRequest(request, response)) {
					handleExternalRequestWithUserAccessControl(path.replace("/ext", ""), request, response);
				}
			} else {
				Object result = handleNormalRequest(path, request, response);
				writeResponse(response, result);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			writeResponse(response, wrapperFailureWebResult(ResultCode.FAILURE, e.getMessage()));
		}
		return null;
	}

	private WebResult wrapperFailureWebResult(int code, String failure) {
		return new WebResult().buildFailure(code, failure);
	}
	
	private WebResult wrapperFailureWebResult(ResultCode resultCode, String failure) {
		return wrapperFailureWebResult(resultCode.getCode(), failure);
	}

	@SuppressWarnings("unused")
	private Map<String, String> getParams(List<String> names, Map<String, String> params, HttpServletRequest request) {
		Map<String, String> result = new HashMap<String, String>();
		for (String name : names) {
			if (params != null && params.get(name) != null) {
				result.put(name, params.get(name));
			} else {
				String[] arr = request.getParameterValues(name);
				if (arr != null) {
					result.put(name, StringUtils.join(arr, "\b"));
				}
			}
		}
		return result;
	}

	private Object handleNormalRequest(String path, HttpServletRequest request, HttpServletResponse response) 
			throws UnsupportedEncodingException {
		String requestMethod = request.getMethod();
		String interfaceUrl = path.replaceAll("/+", "/").replaceAll("^/app", "").replaceAll("^/ext", "");
		ObjectMethodParams omp = UrlMappingStorage.getObjectMethod(interfaceUrl, requestMethod);
		if (omp == null) {
			return wrapperFailureWebResult(ResultCode.URL_ERROR, "No mapping found for HTTP request with URI " + path);
		}
		Method method = omp.getMethod();
		if (method == null) {
			return wrapperFailureWebResult(ResultCode.URL_ERROR, "No mapping found for HTTP request with URI " + path);
		}
		try {
			ParameterBinder parameterBinder = new ParameterBinder(ctx);
			Object[] params = parameterBinder.bindParameters(omp, omp.getParams(), request, response);
			Object result = ReflectionUtils.invokeMethod(method, omp.getObject(), params);
			return method.getReturnType() == void.class || response.isCommitted() ? "" : result;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			if (e.getCause() != null) e = (Exception) e.getCause();
			return wrapperFailureWebResult(ResultCode.FAILURE, e.getMessage());
		}
	}
	
	private void handleExternalRequestWithUserAccessControl(String interfaceUrl, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String account = request.getParameter("accessId");
		AccessUserControl accessUserControl = accessUserService.readAccessUserControlByAccount(account);
		long remainingCount = accessUserControl.getRemainingCount();
		if (remainingCount <= 0) {
			writeResponse(response, wrapperFailureWebResult(ResultCode.FAILURE, "账户剩余查询条数不足"));
		}
		Object result = handleExternalRequest(interfaceUrl, request, response);
		long incOrDec = parseReturnResultCount(result);
		LOG.info("incOrDec : {}", incOrDec);
		accessUserService.updateRemainingCount(account, remainingCount, -incOrDec);
		writeResponse(response, result);
	}
	
	private Object handleExternalRequest(String interfaceUrl, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		return handleExternalRequest(interfaceUrl, new HashMap<String, String>(), request, response);
	}

	private Object handleExternalRequest(String interfaceUrl, Map<String, String> paramMap, HttpServletRequest request, 
			HttpServletResponse response) throws UnsupportedEncodingException {
		ObjectMethodParams omp = UrlMappingStorage.getObjectMethod(interfaceUrl);
		if (omp == null) {
			return wrapperFailureWebResult(ResultCode.URL_ERROR, 
					"No mapping found for HTTP request with URI " + interfaceUrl);
		}
		Method method = omp.getMethod();
		if (method == null) {
			return wrapperFailureWebResult(ResultCode.URL_ERROR, 
					"No mapping found for HTTP request with URI " + interfaceUrl);
		}
		try {
			ParameterBinder parameterBinder = new ParameterBinder(ctx);
			paramMap.putAll(omp.getParams());
			Object[] params = parameterBinder.bindParameters(omp, paramMap, request, response);
			Object result = ReflectionUtils.invokeMethod(method, omp.getObject(), params);
			return method.getReturnType() == void.class || response.isCommitted() ? "" : result;

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			if (e.getCause() != null) {
				e = (Exception) e.getCause();
			}
			return wrapperFailureWebResult(ResultCode.FAILURE, e.getMessage());
		}
	}
	
	private void writeResponse(HttpServletResponse response, Object result) 
			throws JsonGenerationException, JsonMappingException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		//objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
		response.getWriter().write(objectMapper.writeValueAsString(result));
	}
	
	private boolean authenticationAppRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	String token = request.getParameter("token");
		if (StringUtils.isBlank(token) || !"cisiondata".equals(token)) {
			writeResponse(response, wrapperFailureWebResult(ResultCode.FAILURE, "该请求被拒绝访问"));
			return false;
		}
		return true;
    }
    
    @SuppressWarnings("unchecked")
	private boolean authenticationExternalRequest(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
    	Map<String, String[]> requestParams = request.getParameterMap();
    	Map<String, String> params = new HashMap<String, String>();
    	for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
			params.put(entry.getKey(), entry.getValue()[0]);
    	}
    	try {
	    	String accessKey = accessUserService.readAccessKeyByAccessId(params.get("accessId"));
	    	params.put("accessKey", accessKey);
    	} catch (Exception e) {
    		LOG.error(e.getMessage(), e);
    		writeResponse(response, wrapperFailureWebResult(ResultCode.FAILURE, e.getMessage()));
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
		LOG.info("access token: {}", SHAUtils.SHA1(sb.toString()));
		if (!SHAUtils.SHA1(sb.toString()).equals(token)) {
			writeResponse(response, wrapperFailureWebResult(ResultCode.FAILURE, "该请求被拒绝访问"));
			return false;
		}
		return true;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private long parseReturnResultCount(Object result) {
    	if (result instanceof WebResult) {
    		WebResult webResult = (WebResult) result;
    		Object data = webResult.getData();
    		if (data instanceof List) {
    			return ((List) data).size();
    		} else if (data instanceof QueryResult) {
    			QueryResult queryResult = (QueryResult) data;
    			return queryResult.getResultList().size();
    		} else if (data instanceof Map) {
    			Map<String, Object> map = (Map<String, Object>) data;
    			long count = 0;
    			for (Map.Entry<String, Object> entry : map.entrySet()) {
    				Object value = entry.getValue();
    				if (value instanceof List) {
    					count += ((List) value).size();
    				}
    			}
    			return count;
    		}
    	}
    	return 0;
    }
}
