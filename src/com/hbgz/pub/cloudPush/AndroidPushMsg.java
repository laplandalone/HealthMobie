package com.hbgz.pub.cloudPush;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushUnicastMessageRequest;
import com.baidu.yun.channel.model.PushUnicastMessageResponse;
import com.hbgz.pub.util.ObjectCensor;

/**
 * @author WM
 * 推送单播消息
 */
public class AndroidPushMsg
{
	private static Log log = LogFactory.getLog(AndroidPushMsg.class);
	
	public static void pushMsg(String userId, String msgType, String msg, String channelId)
	{
		if(ObjectCensor.isStrRegular(userId, msgType, msg))
		{
			BaiduChannelClient channelClient = BaiduChannelClientSingleton.getInstance();
			try
			{
				// 4.创建请求对象
				PushUnicastMessageRequest request = new PushUnicastMessageRequest();
				// device_type => 1: web 2: pc 3:android 4:ios 5:wp
				request.setDeviceType(3);
				if(ObjectCensor.isStrRegular(channelId))
				{
					request.setChannelId(Long.parseLong(channelId));
				}
				request.setUserId(userId);
				if("notice".equalsIgnoreCase(msgType))
				{
					request.setMessageType(1);
				}
				request.setMessage(msg);
				// 5. 调用pushMessage接口
				PushUnicastMessageResponse response = channelClient.pushUnicastMessage(request);
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
	}
	
	public static void main(String[] args)
	{
		//消息
		pushMsg("1078586329118986050", "msg", "<user_id>72438</user_id><message>您有一个新的订单!</message><title>订单提醒</title><order_type>REPAST</order_type>", "");
		
//		pushMsg("706200416679787745", "msg", "\"custom_content\": {\"user_id\":\"value1\", \"title\":\"value2\"}", "");
		//通知
//		pushMsg("854319934071353822", "notice", "{\"title\":\"Notify_title_danbo\",\"description\":\"Notify_description_content\",\"open_type\":0,\"custom_content\":{\"user_id\":\"71189\",\"title\":\"Hello\"}}", "");
		
//		pushMsg("706200416679787745", "notice", "{\"title\":\"Notify_title_danbo\",\"description\":\"Notify_description_content\",\"custom_content\":{\"key1\":\"value1\",\"key2\":\"value2\"}}", "");
	}
}
