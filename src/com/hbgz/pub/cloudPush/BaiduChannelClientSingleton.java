package com.hbgz.pub.cloudPush;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;

public class BaiduChannelClientSingleton
{	
	private final static String apiKey = "EHKN6qSTGpmEWN0uXk85LWGO";
	
	private final static String secretKey = "U4rZuCUf3vMCPk13daf9Z8aOZxzn49AK";
	
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
