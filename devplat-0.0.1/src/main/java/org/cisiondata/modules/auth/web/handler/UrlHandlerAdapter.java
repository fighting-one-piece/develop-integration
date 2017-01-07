package org.cisiondata.modules.auth.web.handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.auth.web.handler.ClassScanner.ClassResourceHandler;
import org.cisiondata.modules.auth.web.handler.UrlMappingStorage.Mapper;
import org.cisiondata.modules.auth.web.handler.UrlMappingStorage.ObjectMethodParams;
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
					//inject service
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
	}
	
	@Override
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
			String path = request.getServletPath();
			LOG.info("url handler request uri: {}", path);
			if(path.startsWith("/app")) {
//				String token = request.getParameter("token");
				Map<String, String> paramMap = new HashMap<String, String>();
				Object result = handleExternalRequest(path, paramMap, request, response);
				writeResponse(response, result);
			} else {
				Object result = handleNormalRequest(path, request, response);
				writeResponse(response, result);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			WebResult result = new WebResult();
			writeResponse(response, result);
		}
		return null;
	}

	private WebResult getFailureMap(int code, String failure) {
		WebResult result = new WebResult();
		result.setCode(code);
		result.setFailure(failure);
		return result;
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
		String interfaceUrl = path.replaceAll("/+", "/").replaceAll("^/app", "");
		ObjectMethodParams omp = UrlMappingStorage.getObjectMethod(interfaceUrl, requestMethod);
		if (omp == null) {
			return getFailureMap(ResultCode.URL_ERROR.getCode(), "No mapping found for HTTP request with URI " + path);
		}
		Method method = omp.getMethod();
		if (method == null) {
			return getFailureMap(ResultCode.URL_ERROR.getCode(), "No mapping found for HTTP request with URI " + path);
		}
		try {
			ParameterBinder parameterBinder = new ParameterBinder(ctx);
			Object[] params = parameterBinder.bindParameters(omp, omp.getParams(), request, response);
//			List<String> names = parameterBinder.parseMethodParamNames(method);
//			Map<String, String> values = getParams(names, omp.getParams(), request);
			Object result = ReflectionUtils.invokeMethod(method, omp.getObject(), params);
			return method.getReturnType() == void.class || response.isCommitted() ? "" : result;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			if (e.getCause() != null) e = (Exception) e.getCause();
			return getFailureMap(ResultCode.FAILURE.getCode(), e.getMessage());
		}
	}

	private Object handleExternalRequest(String interfaceUrl, Map<String, String> paramMap, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		ObjectMethodParams omp = UrlMappingStorage.getObjectMethod(interfaceUrl);
		if (omp == null) {
			return getFailureMap(ResultCode.URL_ERROR.getCode(), "No mapping found for HTTP request with URI " + interfaceUrl);
		}
		Method method = omp.getMethod();
		if (method == null) {
			return getFailureMap(ResultCode.URL_ERROR.getCode(), "No mapping found for HTTP request with URI " + interfaceUrl);
		}
		try {
			ParameterBinder parameterBinder = new ParameterBinder(ctx);
			if (omp.getParams() != null) {
				paramMap.putAll(omp.getParams());
			}
			Object[] params = parameterBinder.bindParameters(omp, paramMap, request, response);
			Object result = ReflectionUtils.invokeMethod(method, omp.getObject(), params);
			if (method.getReturnType() == void.class || response.isCommitted()) {
				return "";
			}
			return result;

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			if (e.getCause() != null) {
				e = (Exception) e.getCause();
			}
			return getFailureMap(ResultCode.FAILURE.getCode(), e.getMessage());
		}
	}
	
	private void writeResponse(HttpServletResponse response, Object obj) 
			throws JsonGenerationException, JsonMappingException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		//objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
		response.getWriter().write(objectMapper.writeValueAsString(obj));
	}

}
