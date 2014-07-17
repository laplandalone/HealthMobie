package com.hbgz.pub.resolver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;

import com.hbgz.model.JoinParams;
import com.hbgz.pub.util.DateUtils;

/**
 * 
 * @author RHQ
 * 
 */
public class MultiServiceMethodAdapter 
{
	private static Log log = LogFactory.getLog(MultiServiceMethodAdapter.class);

	public static Object invoke(JoinParams joinParam) throws Exception 
	{
		Object delegate = null;
		Method method = null;
		String serviceName = null;
		String serviceType = joinParam.getServiceType();
		LinkedHashMap map = joinParam.getParams();
		List list = new ArrayList(map.values());
		try 
		{
			if (serviceType == null && serviceType.length() < 7) 
			{
				throw new Exception("MultiServiceMethodAdapter: ServiceType is format error £º" + serviceType);
			}
			serviceName = serviceType.substring(0, 6);
			BeanFactory beanFactory = BeanFactoryHelper.getBeanfactory();
			boolean containBean = beanFactory.containsBean(serviceName);
			if (!containBean) 
			{
				throw new Exception("MultiServiceMethodAdapter: Service does not exist £º" + serviceType);
			}
			delegate = beanFactory.getBean(serviceName);
			method = ServiceBeanMethodResolver.getHandlerMethod(delegate, serviceType);
			if (method == null) 
			{
				throw new Exception("MultiServiceMethodAdapter: Service without this method £º" + serviceType);
			}
			log.error("ServiceType:" + serviceType + "  params:" + list);
			Object[] objs = ServiceMsgConverter.argsTypeConvertor(method, list.toArray());
			return method.invoke(delegate, objs);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new Exception("MultiServiceMethodAdapter: Service call failure£º" + serviceType + " " + e.getMessage());
		}
	}
}
