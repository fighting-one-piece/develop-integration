package org.cisiondata.modules.auth.web.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.auth.service.IRequestService;
import org.cisiondata.modules.auth.web.handler.UrlMappingStorage.Mapper;
import org.cisiondata.utils.clazz.ClassScanner;
import org.cisiondata.utils.clazz.ClassScanner.ClassResourceHandler;
import org.cisiondata.utils.clazz.ObjectMethodParams;
import org.cisiondata.utils.clazz.ParameterBinder;
import org.cisiondata.utils.exception.BusinessException;
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

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UrlV1HandlerAdapter implements HandlerAdapter, ApplicationContextAware, InitializingBean {

	private Logger LOG = LoggerFactory.getLogger(UrlV1HandlerAdapter.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	private ApplicationContext ctx = null;

	private List<IRequestService> requestServiceList = new ArrayList<IRequestService>();

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
		Map<String, IRequestService> beans = ctx.getBeansOfType(IRequestService.class);
		for (Map.Entry<String, IRequestService> entry : beans.entrySet()) {
			requestServiceList.add(entry.getValue());
		}
		new ClassScanner(new String[] { "org.cisiondata.modules" }, new ClassResourceHandler() {
			
			public void handle(MetadataReader metadata) {
				String className = metadata.getClassMetadata().getClassName();
				String[] baseUrl = null;
				AnnotationMetadata annotationMetadata = metadata.getAnnotationMetadata();
				if (!annotationMetadata.hasAnnotation(Controller.class.getName())
						&& !annotationMetadata.hasAnnotation(RestController.class.getName()))
					return;
				Map<String, Object> attributes = metadata.getAnnotationMetadata()
						.getAnnotationAttributes(RequestMapping.class.getName());
				if (attributes != null) {
					baseUrl = (String[]) attributes.get("value");
				}
				try {
					Class<?> clazz = Class.forName(className);
					final Mapper mapper = new Mapper(baseUrl, clazz.newInstance());
					ReflectionUtils.doWithMethods(clazz, new MethodCallback() {
						public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
							if (Modifier.isPublic(method.getModifiers())
									&& method.isAnnotationPresent(RequestMapping.class)) {
								mapper.add(method);
							}
						}
					});
					UrlMappingStorage.addMapper(mapper);
					ReflectionUtils.doWithFields(clazz, new FieldCallback() {
						public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
							Resource resource = field.getAnnotation(Resource.class);
							String beanName = null == resource ? field.getName() : resource.name();
							if (ctx.containsBean(beanName) && ctx.isTypeMatch(beanName, field.getType())) {
								ReflectionUtils.makeAccessible(field);
								ReflectionUtils.setField(field, mapper.getController(), ctx.getBean(beanName));
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
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try {
			String path = request.getServletPath();
			LOG.info("url handler adapter request path: {}", path);
			for (IRequestService requestService : requestServiceList) {
				Object[] preResult = requestService.preHandle(request);
				if (preResult.length == 0 || (Boolean) preResult[0])
					continue;
				Object exceptionObj = preResult[1];
				if (exceptionObj instanceof BusinessException) {
					throw (BusinessException) exceptionObj;
				}
			}
			Object result = null;
			if (path.startsWith("/app")) {
				result = handleExternalRequest(path.replace("/app", ""), request, response);
			} else if (path.startsWith("/ext")) {
				result = handleExternalRequest(path.replace("/ext", ""), request, response);
			} else if (path.startsWith("/api/v1")) {
				result = handleNormalRequest(path.replace("/api/v1", ""), request, response);
			} else {
				result = handleNormalRequest(path, request, response);
			}
			for (IRequestService requestService : requestServiceList) {
				requestService.postHandle(request, result);
			}
			writeResponse(response, result);
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
			return wrapperFailureWebResult(ResultCode.URL_MAPPING_ERROR,
					"No mapping found for HTTP request with URI " + path);
		}
		Method method = omp.getMethod();
		if (method == null) {
			return wrapperFailureWebResult(ResultCode.URL_MAPPING_ERROR,
					"No mapping found for HTTP request with URI " + path);
		}
		try {
			ParameterBinder parameterBinder = new ParameterBinder();
			Object[] params = parameterBinder.bindParameters(omp, omp.getParams(), request, response);
			Object result = ReflectionUtils.invokeMethod(method, omp.getObject(), params);
			return method.getReturnType() == void.class || response.isCommitted() ? "" : result;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			if (e.getCause() != null)
				e = (Exception) e.getCause();
			return wrapperFailureWebResult(ResultCode.FAILURE, e.getMessage());
		}
	}

	private Object handleExternalRequest(String interfaceUrl, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		return handleExternalRequest(interfaceUrl, new HashMap<String, String>(), request, response);
	}

	private Object handleExternalRequest(String interfaceUrl, Map<String, String> paramMap, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		ObjectMethodParams omp = UrlMappingStorage.getObjectMethod(interfaceUrl);
		if (omp == null) {
			return wrapperFailureWebResult(ResultCode.URL_MAPPING_ERROR,
					"No mapping found for HTTP request with URI " + interfaceUrl);
		}
		Method method = omp.getMethod();
		if (method == null) {
			return wrapperFailureWebResult(ResultCode.URL_MAPPING_ERROR,
					"No mapping found for HTTP request with URI " + interfaceUrl);
		}

		try {
			ParameterBinder parameterBinder = new ParameterBinder();
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
		if (null != result && result instanceof ByteArrayOutputStream) {
			ByteArrayOutputStream baos = (ByteArrayOutputStream) result;
			response.getOutputStream().write(baos.toByteArray());
			baos.close();
		} else {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			response.getWriter().write(objectMapper.writeValueAsString(result));
		}
	}

}
