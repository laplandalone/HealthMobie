package com.hbgz.pub.cloudPush;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushBroadcastMessageRequest;
import com.baidu.yun.channel.model.PushBroadcastMessageResponse;
import com.hbgz.pub.util.ObjectCensor;

/**
 * @author WM
 * 推送广播消息
 */
public class AndroidPushBroadcastMsg
{
	private static Log log = LogFactory.getLog(AndroidPushBroadcastMsg.class);
	
	public static void pushMsg(String msgType, String msg)
	{
		if(ObjectCensor.isStrRegular(msgType, msg))
		{
			BaiduChannelClient channelClient = BaiduChannelClientSingleton.getInstance();
			try
			{
				// 4. 创建请求类对象
				PushBroadcastMessageRequest request = new PushBroadcastMessageRequest();
				// device_type => 1: web 2: pc 3:android 4:ios 5:wp
				request.setDeviceType(3); 
				if("notice".equalsIgnoreCase(msgType))
				{
					// 推送通知
					request.setMessageType(1);
				}
				request.setMessage(msg);
				//推送渠道
				// 5. 调用pushMessage接口
				PushBroadcastMessageResponse response = channelClient.pushBroadcastMessage(request);
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
//		pushMsg("notice", "<user_id>71189</user_id><message>Hello</message><title>Hello</title>");
		//通知
		pushMsg("msg", "{\"title\":提问回复,\"description\":新的消息回复请查看,\"msg_type\":ques,\"user_id\":\"\",custom_param:{userId:22861,doctorId:ASH_0009,userTelephone:18907181648,questionId:26005}}");
	}
}
