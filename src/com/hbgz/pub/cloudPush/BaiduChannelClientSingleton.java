package com.hbgz.pub.cloudPush;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.hbgz.pub.cache.CacheManager;
import com.hbgz.pub.resolver.BeanFactoryHelper;
import com.hbgz.pub.util.ObjectCensor;
import com.hbgz.pub.util.StringUtil;

public class BaiduChannelClientSingleton
{	
	private static String apiKey = "EHKN6qSTGpmEWN0uXk85LWGO";
	
	private static String secretKey = "U4rZuCUf3vMCPk13daf9Z8aOZxzn49AK";
	
/*	static
	{
		try 
		{
			CacheManager cacheManager = (CacheManager) BeanFactoryHelper.getBean("cacheManager");
			List list = cacheManager.getWakeType("102", "API_KEY");
			if(ObjectCensor.checkListIsNull(list))
			{
				apiKey = StringUtil.getMapKeyVal((Map) list.get(0), "configVal");
			}
			list = cacheManager.getWakeType("102", "SECRET_KEY");
			if(ObjectCensor.checkListIsNull(list))
			{
				secretKey = StringUtil.getMapKeyVal((Map) list.get(0), "configVal");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}*/
	
	private static Log log = LogFactory.getLog(BaiduChannelClientSingleton.class);
	
	private static BaiduChannelClient instance = null;
	
	public synchronized static BaiduChannelClient getInstance() 
	{
		if(instance == null)
		{
			// 1. ����developerƽ̨��ApiKey/SecretKey
			ChannelKeyPair pair = new ChannelKeyPair(apiKey, secretKey);
			// 2. ����BaiduChannelClient����ʵ��
			instance = new BaiduChannelClient(pair);
			// 3. ��Ҫ�˽⽻��ϸ�ڣ���ע��YunLogHandler��
			instance.setChannelLogHandler(new YunLogHandler() {
	            public void onHandle(YunLogEvent event) {
	            	log.error(event.getMessage());
	            }
	        });
		}
		return instance;
	}
}
