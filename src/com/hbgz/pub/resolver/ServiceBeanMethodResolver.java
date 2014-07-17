package com.hbgz.pub.resolver;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.annotation.AnnotationUtils;

import com.hbgz.pub.annotation.ServiceType;

public class ServiceBeanMethodResolver
{
	private final static Map<String, Method> handlerMethodMap = new HashMap<String, Method>();

	public static void registerHandlerMethods(Object delegate)
	{
		Class<?> delegateClass = delegate.getClass();
		Method[] methods = delegateClass.getDeclaredMethods();
		for (Method method : methods)
		{
			ServiceType annotation = AnnotationUtils.findAnnotation(method, ServiceType.class);
        	if(annotation!=null)
        	{
        		String serviceType=annotation.value();
            	handlerMethodMap.put(serviceType, method);
        	}
		}
	}

	public static Method getHandlerMethod(Object delegate,String serviceType) 
	{
		Method method = null;
		if(serviceType!=null && !"".equals(serviceType))
		{
			method = handlerMethodMap.get(serviceType);
		}
		if(method==null)
		{
			registerHandlerMethods(delegate);
			method = handlerMethodMap.get(serviceType);
		}
		return method;
	}
}
