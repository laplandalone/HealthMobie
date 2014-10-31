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
 * �����鲥��Ϣ
 */
public class AndroidPushTagMsg
{
	private static Log log = LogFactory.getLog(AndroidPushTagMsg.class);
	
	public static void pushMsg(String tagName, String msgType, String msg)
	{
		BaiduChannelClient channelClient = BaiduChannelClientSingleton.getInstance();
		try
		{
			// �������������
			PushTagMessageRequest request = new PushTagMessageRequest();
			// device_type => 1: web 2: pc 3:android 4:ios 5:wp
			request.setDeviceType(3);
			request.setTagName(tagName);
			if("notice".equalsIgnoreCase(msgType))
			{
				// ����֪ͨ
				request.setMessageType(1);
			}
			request.setMessage(msg);
			// ����pushMessage�ӿ�
			PushTagMessageResponse response = channelClient.pushTagMessage(request);
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
	
	public static void main(String[] args)
	{
		//��Ϣ
		pushMsg("mq26", "msg", "<message>�̼ҽк�</message><title>�̼ҽк�</title>");
		//֪ͨ
//		pushMsg("test123", "notice", "{\"title\":\"Notify_title_danbo\",\"description\":\"Notify_description_content\"}");
	}
}
