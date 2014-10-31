package com.hbgz.pub.cloudPush;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushTagMessageRequest;
import com.baidu.yun.channel.model.PushTagMessageResponse;

/**
 * @author WM
 * 推送组播消息
 */
public class AndroidPushTagMsg
{
	private static Log log = LogFactory.getLog(AndroidPushTagMsg.class);
	
	public static void pushMsg(String tagName, String msgType, String msg)
	{
		BaiduChannelClient channelClient = BaiduChannelClientSingleton.getInstance();
		try
		{
			// 创建请求类对象
			PushTagMessageRequest request = new PushTagMessageRequest();
			// device_type => 1: web 2: pc 3:android 4:ios 5:wp
			request.setDeviceType(3);
			request.setTagName(tagName);
			if("notice".equalsIgnoreCase(msgType))
			{
				// 推送通知
				request.setMessageType(1);
			}
			request.setMessage(msg);
			// 调用pushMessage接口
			PushTagMessageResponse response = channelClient.pushTagMessage(request);
			// 6. 认证推送成功
			log.error("push amount : " + response.getSuccessAmount());
		} 
		catch (ChannelClientException e)
		{
			e.printStackTrace();
		} 
		catch (ChannelServerException e)
		{
			// 处理服务端错误异常
			log.error(String.format("request_id: %d, error_code: %d, error_message: %s", e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
		}
	}
	
	public static void main(String[] args)
	{
		//消息
		pushMsg("mq26", "msg", "<message>商家叫号</message><title>商家叫号</title>");
		//通知
//		pushMsg("test123", "notice", "{\"title\":\"Notify_title_danbo\",\"description\":\"Notify_description_content\"}");
	}
}
