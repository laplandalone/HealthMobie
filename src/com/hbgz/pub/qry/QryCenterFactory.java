package com.hbgz.pub.qry;

import org.springframework.beans.factory.BeanFactory;

import com.tools.pub.resolver.BeanFactoryHelper;

/**
 * SID��CRM��ѯ�࣬���������ʶ�����ݿ����ͷ��ض�Ӧ�Ĳ�ѯ�ࡣ
 * @author RHQ
 *
 */
public class QryCenterFactory
{
	public final static String QRY_ITZC_NAME= "itzcQryCenter";

	public static BeanFactory beanFactory = BeanFactoryHelper.getBeanfactory();
	
	/**
	 * �÷�����ȡITZC���ݿ�QryCenter
	 * @param dataName��DataType�࣬��SID��CRM,ITZC���ࣩ
	 * @return QryCenter����ѯ�ࣩ
	 * @throws Exception 
	 */
	public static QryCenter getQryCenter() throws Exception
	{
		String qryCenterName = QRY_ITZC_NAME;
		if (!beanFactory.containsBean(qryCenterName))
		{
			throw new Exception("qryCenterName��" + qryCenterName + " is null");
		}
		return (QryCenter) beanFactory.getBean(qryCenterName);
	}

}
