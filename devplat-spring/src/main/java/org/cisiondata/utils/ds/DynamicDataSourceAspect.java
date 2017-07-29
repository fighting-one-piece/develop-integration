package org.cisiondata.utils.ds;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Aspect
//@Order(0)  
//@Component
public class DynamicDataSourceAspect {

	public static final Logger LOG = LoggerFactory.getLogger(DynamicDataSourceAspect.class);
	
	private static final String EXECUTION = "execution(* org.cisiondata.modules.*.service.*.*.*(..))";

	@Before(EXECUTION)  
    public void before(JoinPoint jp) throws Throwable {  
		MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method targetMethod = methodSignature.getMethod();
        if(targetMethod.isAnnotationPresent(TargetDataSource.class)){
            String targetDataSource = targetMethod.getAnnotation(TargetDataSource.class).value();
            System.err.println("target data source: " + targetDataSource);
            DataSourceContextHolder.setDataSource(targetDataSource);
        }
    }  
	
	@AfterReturning(EXECUTION)  
    public void afterReturning() throws Throwable {  
        DataSourceContextHolder.clearDataSource();  
    }  
	
	@Around(EXECUTION)
	public Object proceed(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		try {
			MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
	        Method targetMethod = methodSignature.getMethod();
	        if(targetMethod.isAnnotationPresent(TargetDataSource.class)){
	            String targetDataSource = targetMethod.getAnnotation(TargetDataSource.class).value();
	            System.err.println("target data source: " + targetDataSource);
	            DataSourceContextHolder.setDataSource(targetDataSource);
	        }
			return proceedingJoinPoint.proceed();
		} finally {
			DataSourceContextHolder.clearDataSource();
		}
	}

}
