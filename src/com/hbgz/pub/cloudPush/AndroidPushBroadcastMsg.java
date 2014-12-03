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
	
	public static void pushMsg(String msgType, String msg)
	{
		if(ObjectCensor.isStrRegular(msgType, msg))
		{
			BaiduChannelClient channelClient = BaiduChannelClientSingleton.getInstance();
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
		String ss = "{\"title\":\"��������\",\"description\":\"ԤԼ����\",\"msg_type\":\"order\",\"user_id\":\"22861\",\"custom_param\":{\"createDate\":\"2014-12-02\",\"detailTime\":\"0\",\"doctorId\":\"9083R\",\"doctorName\":\"������ͨ��\",\"hospitalId\":\"102\",\"orderFee\":\"4.5\",\"orderId\":\"2014120226479\",\"orderNum\":\"1001\",\"orderState\":\"000\",\"payState\":\"100\",\"platformOrderId\":\"\",\"registerId\":\"1000020064\",\"registerTime\":\"2014-12-08  ����һ ����\",\"sex\":\"��\",\"state\":\"00A\",\"teamId\":\"mz12\",\"teamName\":\"������ͨ\",\"userId\":\"22861\",\"userName\":\"Ƚ��ȫ\",\"userNo\":\"422822198407311010\",\"userTelephone\":\"18907181648\"}}";
		pushMsg("msg", "{\"title\":ԤԼ����,\"description\":����,\"msg_type\":order,\"user_id\":\"22861\",custom_param:{userId:22861,doctorId:ASH_0009,userTelephone:18907181648,questionId:26005}}");
//		pushMsg("msg",ss);
	}
}
