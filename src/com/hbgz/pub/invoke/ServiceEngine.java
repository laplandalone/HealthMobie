package com.hbgz.pub.invoke;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.hbgz.model.JoinParams;
import com.hbgz.model.ServiceRetMsg;
import com.hbgz.pub.resolver.MultiServiceMethodAdapter;
import com.hbgz.pub.resolver.ServiceMsgConverter;
import com.hbgz.pub.util.JsonUtils;
import com.hbgz.pub.util.ObjectCensor;
import com.hbgz.pub.util.QryPropertiesConfig;

/**
 * 调用WEB_SERVICE服务
 * @author ran haiquan 18907181648@189.cn
 * 
 */
public class ServiceEngine 
{
	// WEB_SERVICE 地址
	private static String address = "";

	public static String invokeService(String param) throws Exception 
	{
		ServiceRetMsg msg = new ServiceRetMsg();
		Object rstObj = null;
		try 
		{
			JoinParams joinParam = (JoinParams) ServiceMsgConverter.jsonToBeanConverter(param, JoinParams.class);
			 rstObj = MultiServiceMethodAdapter.invoke(joinParam);
			if ("user-defined".equals(joinParam.getRtnDataFormatType()))
			{
				return rstObj.toString();
			}
			if (rstObj instanceof List)
			{
				msg.setReturnMsgType("JSONArray");
			}
			else
			{
				msg.setReturnMsgType("JSONObject");
			}
			msg.setReturnMsg(rstObj);
			return ServiceMsgConverter.beanToJsonConverter(msg);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			msg.setExecuteType("failure");
			msg.setExceptionMsg(e.getMessage());
			try
			{
				return ServiceMsgConverter.beanToJsonConverter(msg);
			} 
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
		return null;
	}
}
