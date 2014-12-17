package com.hbgz.pub.cloudPush;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;

public class BaiduPushOnline
{	
	private static String apiKey = "M0fnhL6RTLrVH7CDkmWxdQ1x";
	
	private static String secretKey = "PZEUSUiSBi4Um91jqBMGYRIMTw7TDOz5";
	
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
	
	private static Log log = LogFactory.getLog(BaiduPushOnline.class);
	
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
