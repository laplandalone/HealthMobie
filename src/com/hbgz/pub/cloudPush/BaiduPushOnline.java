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
			// 1. 设置developer平台的ApiKey/SecretKey
			ChannelKeyPair pair = new ChannelKeyPair(apiKey, secretKey);
			// 2. 创建BaiduChannelClient对象实例
			instance = new BaiduChannelClient(pair);
			// 3. 若要了解交互细节，请注册YunLogHandler类
			instance.setChannelLogHandler(new YunLogHandler() {
	            public void onHandle(YunLogEvent event) {
	            	log.error(event.getMessage());
	            }
	        });
		}
		return instance;
	}
}
