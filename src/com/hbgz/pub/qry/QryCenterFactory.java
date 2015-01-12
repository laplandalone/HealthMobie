package com.hbgz.pub.qry;

import org.springframework.beans.factory.BeanFactory;

import com.tools.pub.resolver.BeanFactoryHelper;

/**
 * SID，CRM查询类，根据区域标识，数据库类型返回对应的查询类。
 * @author RHQ
 *
 */
public class QryCenterFactory
{
	public final static String QRY_ITZC_NAME= "itzcQryCenter";

	public static BeanFactory beanFactory = BeanFactoryHelper.getBeanfactory();
	
	/**
	 * 该方法获取ITZC数据库QryCenter
	 * @param dataName（DataType类，分SID，CRM,ITZC三类）
	 * @return QryCenter（查询类）
	 * @throws Exception 
	 */
	public static QryCenter getQryCenter() throws Exception
	{
		String qryCenterName = QRY_ITZC_NAME;
		if (!beanFactory.containsBean(qryCenterName))
		{
			throw new Exception("qryCenterName：" + qryCenterName + " is null");
		}
		return (QryCenter) beanFactory.getBean(qryCenterName);
	}

}
