package org.cisiondata.utils.http;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.cisiondata.modules.auth.entity.User;
import org.springframework.web.bind.annotation.RequestBody;

public class AnnotationParam {

	public void hello(HttpServletRequest request, String account, @RequestBody User user) {
		
	}
	
	public static void main(String[] args) {
		Method[] methods = AnnotationParam.class.getDeclaredMethods();
		for (int i = 0, len = methods.length; i < len; i++) {
			Method method = methods[i];
			System.out.println(method.getName());
			Annotation[][] annotationss = method.getParameterAnnotations();
			Class<?>[] annotationTypes = new Class[annotationss.length];
			System.out.println(annotationss.length);
			for (int j = 0, jlen = annotationss.length; j < jlen; j++) {
				Annotation[] annotations = annotationss[j];
				if (annotations.length == 0) {
					annotationTypes[j] = null;
				} else if (annotations.length == 1) {
					annotationTypes[j] = annotations[0].annotationType();
				}
			}
			for (Class<?> clazz : annotationTypes) {
				System.out.println(clazz);
			}
		}
	}
	
}
