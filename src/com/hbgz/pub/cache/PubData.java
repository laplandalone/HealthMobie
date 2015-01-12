package com.hbgz.pub.cache;

import java.util.List;

import com.hbgz.pub.exception.QryException;
import com.tools.pub.resolver.BeanFactoryHelper;

public class PubData 
{
	public static List qryTeamList(String hospitalId) throws QryException
	{
		CacheManager cacheManager = (CacheManager) BeanFactoryHelper.getBean("cacheManager");
		return cacheManager.qryTeamList(hospitalId);
	}
	
	public static List qryDoctorList(String hospitalId) throws QryException
	{
		CacheManager cacheManager = (CacheManager) BeanFactoryHelper.getBean("cacheManager");
		return cacheManager.qryDoctorList(hospitalId);
	}
}
