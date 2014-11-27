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
		//userId=863332631944779153
		//rg4=4149288692313311524

		//消息
//		pushMsg("982179218847486686", "msg", "测试", "982179218847486686");
//		pushMsg("982179218847486686", "msg", "{\"title\":\"Notify_title_danbo\",\"description\":\"haha\",\"open_type\":\"0\",\"custom_content\":{\"user_id\":\"71189\",\"title\":\"Hello\"}}", "");
		
		pushMsg("982179218847486686", "msg", "{\"title\":预约提醒,\"description\":haha,\"msg_type\":order,\"user_id\":\"22861,12345,123123\"}", "");

//		String msg = String.format("{'title':'%s','description':'%s','notification_builder_id':1,'notification_basic_style':0,'open_type':2,'pkg_content':'%s','pkg_name':'%s','custom_content':{'test':'test'}}",title,jsonencode(message),pkg_content,pkg_name);

	}
}
