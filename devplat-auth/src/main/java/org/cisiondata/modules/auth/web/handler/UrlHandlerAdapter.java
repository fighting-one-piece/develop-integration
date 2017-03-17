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
import java.util.Date;
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
import org.cisiondata.modules.auth.entity.RequestMessage;
import org.cisiondata.modules.auth.service.IAccessUserService;
import org.cisiondata.modules.auth.web.WebUtils;
import org.cisiondata.modules.auth.web.handler.UrlMappingStorage.Mapper;
import org.cisiondata.modules.rabbitmq.entity.MQueue;
import org.cisiondata.modules.rabbitmq.service.IMQService;
import org.cisiondata.utils.clazz.ClassScanner;
import org.cisiondata.utils.clazz.ClassScanner.ClassResourceHandler;
import org.cisiondata.utils.clazz.ObjectMethodParams;
import org.cisiondata.utils.clazz.ParameterBinder;
import org.cisiondata.utils.date.DateFormatter;
import org.cisiondata.utils.endecrypt.SHAUtils;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.cisiondata.utils.web.IPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.MethodCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
	
	private IMQService mqService = null;
	
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
				AnnotationMetadata annotationMetadata = metadata.getAnnotationMetadata();
				if (!annotationMetadata.hasAnnotation(Controller.class.getName()) &&
						!annotationMetadata.hasAnnotation(RestController.class.getName())) 
					return;
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
		this.mqService = (IMQService) ctx.getBean("mqService");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
			String path = request.getServletPath();
			LOG.info("url handler adapter request path: {}", path);
			judgeSensitiveWord(request.getParameterMap());
			if (path.startsWith("/app")) {
				path = path.replace("/app", "");
				Object result = handleExternalRequest(path, request, response);
				writeResponse(response, result);
			} else if (path.startsWith("/ext")) {
				path = path.replace("/ext", "");
				Object result = handleExternalRequestWithUserAccessControl(path, request, response);
				writeResponse(response, result);
			} else if (path.startsWith("/api/v1")) {
				path = path.replace("/api/v1", "");
				Object result = handleNormalRequest(path, request, response);
				writeResponse(response, result);
				/**
				sendMessage(request, path, result);
				**/
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

	private Object handleNormalRequest(String path, HttpServletRequest request, HttpServletResponse response) 
			throws UnsupportedEncodingException {
		String interfaceUrl = path.replaceAll("/+", "/").replaceAll("^/app", "").replaceAll("^/ext", "");
		ObjectMethodParams omp = UrlMappingStorage.getObjectMethod(interfaceUrl, request.getMethod());
		if (omp == null) {
			return wrapperFailureWebResult(ResultCode.URL_ERROR, "No mapping found for HTTP request with URI " + path);
		}
		Method method = omp.getMethod();
		if (method == null) {
			return wrapperFailureWebResult(ResultCode.URL_ERROR, "No mapping found for HTTP request with URI " + path);
		}
		try {
			ParameterBinder parameterBinder = new ParameterBinder();
			Object[] params = parameterBinder.bindParameters(omp, omp.getParams(), request, response);
			Object result = ReflectionUtils.invokeMethod(method, omp.getObject(), params);
			return method.getReturnType() == void.class || response.isCommitted() ? "" : result;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			if (e.getCause() != null) e = (Exception) e.getCause();
			return wrapperFailureWebResult(ResultCode.FAILURE, e.getMessage());
		}
	}
	
	private Object handleExternalRequestWithUserAccessControl(String interfaceUrl, 
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
		return result;
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
			ParameterBinder parameterBinder = new ParameterBinder();
			paramMap.putAll(omp.getParams());
			Object[] params = parameterBinder.bindParameters(omp, paramMap, request, response);
			judgeSensitiveWord(params);
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
	
	@SuppressWarnings("unused")
	private boolean authenticationAppRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	String token = request.getParameter("token");
		if (StringUtils.isBlank(token) || !"cisiondata".equals(token)) {
			writeResponse(response, wrapperFailureWebResult(ResultCode.FAILURE, "该请求被拒绝访问"));
			return false;
		}
		return true;
    }
    
    @SuppressWarnings({ "unchecked", "unused" })
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
    
    @SuppressWarnings("unchecked")
	private void judgeSensitiveWord(Map<String, String[]> paramMap) throws BusinessException {
    	for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
    		Object arg = entry.getValue()[0];
			if (arg instanceof Map) {
				Map<String, Object> argMap = (Map<String, Object>) arg;
				for (Map.Entry<String, Object> argEntry : argMap.entrySet()) {
					judgeSensitiveWord(argEntry.getValue());
				}
			} else {
				judgeSensitiveWord(arg);
			}
    	}
	}
    
    @SuppressWarnings("unchecked")
	private void judgeSensitiveWord(Object[] args) throws BusinessException {
		if (null != args && args.length != 0) {
			for (int i = 0, len = args.length; i < len; i++) {
				Object arg = args[i];
				if (arg instanceof Map) {
					Map<String, Object> map = (Map<String, Object>) arg;
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						judgeSensitiveWord(entry.getValue());
					}
				} else {
					judgeSensitiveWord(arg);
				}
			}
		}
	}
	
	private void judgeSensitiveWord(Object arg) throws BusinessException {
		if (arg instanceof String) {
			String queryTxt = String.valueOf(arg);
			String[] keywords = queryTxt.indexOf(" ") == -1 ? new String[]{queryTxt} : queryTxt.split(" ");
			for (int i = 0, len = keywords.length; i < len; i++) {
				if (RedisClusterUtils.getInstance().sismember("sensitive_word", keywords[i])) {
					throw new BusinessException("抱歉!该查询涉及敏感信息");
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private RequestMessage wrapperRequestMessage(HttpServletRequest request, String requestUrl, Object result) {
		RequestMessage requestMessage = new RequestMessage();
		requestMessage.setUrl(requestUrl);
		requestMessage.setParams(request.getParameterMap());
		requestMessage.setIpAddress(IPUtils.getIPAddress(request));
		requestMessage.setAccount(WebUtils.getCurrentAccout());
		requestMessage.setTime(new Date());
		requestMessage.setReturnResult(result);
		return requestMessage;
	}
	
	@SuppressWarnings("unused")
	private void sendMessage(HttpServletRequest request, String requestUrl, Object result) {
		mqService.sendMessage(MQueue.REQUEST_ACCESS_QUEUE.getName(), 
				wrapperRequestMessage(request, requestUrl, result));
	}
	
}
