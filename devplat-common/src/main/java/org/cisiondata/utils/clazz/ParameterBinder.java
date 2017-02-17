package org.cisiondata.utils.clazz;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONArray;

@SuppressWarnings("unchecked")
public class ParameterBinder {

	private static Log log = LogFactory.getLog(ParameterBinder.class);
	
	@SuppressWarnings("unused")
	private ApplicationContext ctx = null;
	private static final MultipartResolver MULTIPARTRESOLVER = new CommonsMultipartResolver();
	private static Map<String, Object> classMethodParamNames = new HashMap<String, Object>();

	public ParameterBinder(ApplicationContext ctx) {
		this.ctx = ctx;
	}

	private String getParam(Map<String, String> params, HttpServletRequest request, String name) {
		if (params != null && params.get(name) != null) {
			return params.get(name);
		} else {
			String[] arr = request.getParameterValues(name);
			if (arr != null) {
				return StringUtils.join(arr, "\b");
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public Object[] bindParameters(ObjectMethodParams omp, Map<String, String> params, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (MULTIPARTRESOLVER.isMultipart(request)) {
			request = MULTIPARTRESOLVER.resolveMultipart(request);
		}
		List<String> parameterNames = this.parseMethodParamNames(omp.getMethod());
		Class[] parameterTypes = omp.getMethod().getParameterTypes();
		Object paramTarget[] = new Object[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			Class typeClasz = parameterTypes[i];
			if (typeClasz.isArray()) {
				String arr = getParam(params, request, parameterNames.get(i));
				String[] value = null;
				if (arr == null) {
					value = new String[0];
				} else {
					if (arr.contains("\b") || !arr.matches("\\s*\\[.*\\]\\s*")) {
						value = arr.split("\b");
					} else {
						JSONArray ja = JSONArray.parseArray(arr);
						value = new String[ja.size()];
						for (int j = 0; j < ja.size(); j++) {
							value[j] = ja.getString(j);
						}
					}
				}
				Object r = Array.newInstance(typeClasz.getComponentType(), value.length);
				for (int j = 0; j < value.length; j++) {
					try {
						Array.set(r, j, BeanUtil.directConvert(value[j], typeClasz.getComponentType()));
					} catch (Exception e) {
					}
				}
				paramTarget[i] = r;
			} else if (typeClasz.isPrimitive() || typeClasz == String.class || Number.class.isAssignableFrom(typeClasz)||Boolean.class==typeClasz) {
				paramTarget[i] = BeanUtil.directConvert(getParam(params, request, parameterNames.get(i)), typeClasz);
			} else if (typeClasz == Date.class) {
				String pattern = "yyyy-MM-dd";
				String key = parameterNames.get(i);
				if (key.indexOf("\b") != -1) {
					pattern = key.substring(key.indexOf("\b") + 1);
					key = key.substring(0, key.indexOf("\b"));
				}
				String strConvertDate = getParam(params, request, key);
				SimpleDateFormat simpledateformat = new SimpleDateFormat(pattern);
				if (strConvertDate != null && !"".equals(strConvertDate)) {
					paramTarget[i] = simpledateformat.parse(strConvertDate);
				}
			} else if (ServletRequest.class.isAssignableFrom(typeClasz)) {
				paramTarget[i] = request;
			} else if (ServletResponse.class.isAssignableFrom(typeClasz)) {
				paramTarget[i] = response;
			} else if (HttpSession.class.isAssignableFrom(typeClasz)) {
				paramTarget[i] = request.getSession(false);
			} else if (HttpHeaders.class.isAssignableFrom(typeClasz)) {
				HttpHeaders headers = new HttpHeaders();
				Enumeration en = request.getHeaderNames();
				while (en.hasMoreElements()) {
					String name = (String) en.nextElement();
					List<String> values = new LinkedList<String>();
					Enumeration ve = request.getHeaders(name);
					while (ve.hasMoreElements()) {
						values.add((String) ve.nextElement());
					}
					headers.put(name, values);
				}
				paramTarget[i] = headers;
			} else if (MultipartFile.class.isAssignableFrom(typeClasz)) {
				String fileName = parameterNames.get(i);
				paramTarget[i] = ((MultipartHttpServletRequest) request).getFile(fileName);
			} else {
				String value = getParam(params, request, parameterNames.get(i));
				if (StringUtils.isNotBlank(value)) {
					//兼容泛型 
					Class actualClass = getGenericClass(omp.getObject(), omp.getMethod(), i);
					paramTarget[i] = BeanUtil.directConvert(value, actualClass);
				} else {
					paramTarget[i] = getEntityParameter(typeClasz, parameterNames.get(i), params, request);
				}
			}
		}
		return paramTarget;
	}

	private static <T> Class<T> getClassByType(Type type) {
		if (type instanceof ParameterizedType) {
			Type t1 = ((ParameterizedType) type).getRawType();
			return getClassByType(t1);
		} else if (type instanceof Class) {
			return (Class<T>) type;
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private static <T> Class<T> getGenericClass(Object obj, Method method, int index) {
		Class[] actualClassArr = null;
		Map<String, Integer> genericStringMap = new HashMap<String, Integer>();
		Type superType = obj.getClass().getGenericSuperclass();
		if (superType instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) superType;
			Type[] arr = pt.getActualTypeArguments();
			actualClassArr = new Class[arr.length];
			for (int i = 0; i < arr.length; i++) {
				actualClassArr[i] = getClassByType(arr[i]);
			}

			TypeVariable[] genericTypeArr = obj.getClass().getSuperclass().getTypeParameters();
			for (int i = 0; i < genericTypeArr.length; i++) {
				genericStringMap.put(genericTypeArr[i].toString(), i);
			}

			Type paramType = method.getGenericParameterTypes()[index];
			if (paramType instanceof Class) {
				return (Class<T>) paramType;
			} else {
				String genericParamString = paramType.toString();
				Integer pos = genericStringMap.get(genericParamString);
				if (pos >= 0 && pos < actualClassArr.length) {
					return actualClassArr[pos];
				}
			}
		}
		return null;
	}

	/**
	 * 从request中获取参数，并转换成目标类型
	 * 
	 * @param type
	 *            目标类型
	 * @param bind
	 *            参数信息
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("rawtypes")
	public Object getEntityParameter(Class type, String name, Map<String, String> params, HttpServletRequest request)
			throws UnsupportedEncodingException {
		Object obj = null;
		try {
			obj = type.newInstance();
		} catch (InstantiationException e) {
			log.debug("InstantiationException happened when initializing bussiness object", e);
			return null;
		} catch (IllegalAccessException e) {
			log.debug("IllegalAccessException happened when initializing bussiness object", e);
			return null;
		}
		Map m = request.getParameterMap();
		Map m2 = new HashMap();
		Iterator it = m.entrySet().iterator();
		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			String[] arr = ((String[]) entry.getValue());
			if (arr.length == 1)
				m2.put(entry.getKey(), arr[0]);
			else
				m2.put(entry.getKey(), arr);
		}
		m2.putAll(params);

		BeanUtil.copyProperties(m2, obj);
		return obj;
	}

	public List<String> parseMethodParamNames(Method method) throws Exception {
		String clazz = method.getDeclaringClass().getName();
		Map<String, List<String>> names = (Map<String, List<String>>) classMethodParamNames.get(clazz);
		if (names == null) {
			names = new ParameterNameParser().parse(clazz);
			classMethodParamNames.put(clazz, names);
		}
		return names.get(getMethodLongName(method));
	}
	
	private static String getMethodLongName(Method method) {
		StringBuilder sb=new StringBuilder();
		sb.append(method.getName()).append("(");
		for(Class<?> type:method.getParameterTypes()){
			sb.append(type.getName()).append(",");
		}
		if(method.getParameterTypes().length>0)
			sb.delete(sb.length()-1, sb.length());
		sb.append(")");
		return sb.toString();
	}

}
