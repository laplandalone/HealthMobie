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
 * ���͹㲥��Ϣ
 */
public class AndroidPushBroadcastMsg
{
	private static Log log = LogFactory.getLog(AndroidPushBroadcastMsg.class);
	
	public static void pushMsg(String msgType, String msg,String hospitalId)
	{
		if(ObjectCensor.isStrRegular(msgType, msg))
		{
			BaiduChannelClient channelClient = null ;
			if("102".equals(hospitalId))
			{
				channelClient = BaiduChannelClientSingleton.getInstance();
			}else if("103".equals(hospitalId))
			{
				channelClient = BaiduPushOnline.getInstance();
			}
			
			try
			{
				// 4. �������������
				PushBroadcastMessageRequest request = new PushBroadcastMessageRequest();
				// device_type => 1: web 2: pc 3:android 4:ios 5:wp
				request.setDeviceType(3); 
				if("notice".equalsIgnoreCase(msgType))
				{
					// ����֪ͨ
					request.setMessageType(1);
				}
				request.setMessage(msg);
				//��������
				// 5. ����pushMessage�ӿ�
				PushBroadcastMessageResponse response = channelClient.pushBroadcastMessage(request);
				// 6. ��֤���ͳɹ�
				log.error("push amount : " + response.getSuccessAmount());
			} 
			catch (ChannelClientException e)
			{
				e.printStackTrace();
			} 
			catch (ChannelServerException e)
			{
				// �������˴����쳣
				log.error(String.format("request_id: %d, error_code: %d, error_message: %s", e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
			}
		}
	}
	
	public static void main(String[] args)
	{
		//��Ϣ
//		pushMsg("notice", "<user_id>71189</user_id><message>Hello</message><title>Hello</title>");
		//֪ͨ
		String ss = "{\"authType\":\"public\",\"content\":\"����������\",\"doctorId\":\"1405R\",\"questionId\":\"26239\",\"recordType\":\"ans\",\"state\":\"00A\",\"userId\":\"10814\",\"userTelephone\":\"18907181620\"}";
		pushMsg("msg", "{\"title\":ԤԼ����,\"description\":����,\"msg_type\":ques,\"user_id\":\"0000_1\",custom_param:"+ss+"}","103");
//		pushMsg("msg",ss);
	}
}
