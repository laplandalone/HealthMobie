package com.hbgz.model;

import java.util.LinkedHashMap;
/**
 * 
 * @author RHQ
 *
 */
public class JoinParams
{
	private String userId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	//渠道标识
	private String channel;
	
	//渠道类型
	private String channelType;
	
	//服务ID
	private String serviceType;
	
	//检验码
	private String securityCode;
	
	//调用服务入参
	private LinkedHashMap params;
	
	//出参格式类型
	private String rtnDataFormatType;
	
	public LinkedHashMap getParams()
	{
		return params;
	}
	public void setParams(LinkedHashMap params)
	{
		this.params = params;
	}
	public String getChannel()
	{
		return channel;
	}
	public void setChannel(String channel)
	{
		this.channel = channel;
	}
	public String getChannelType()
	{
		return channelType;
	}
	public void setChannelType(String channelType)
	{
		this.channelType = channelType;
	}
	public String getServiceType()
	{
		return serviceType;
	}
	public void setServiceType(String serviceType)
	{
		this.serviceType = serviceType;
	}
	public String getSecurityCode()
	{
		return securityCode;
	}
	public void setSecurityCode(String securityCode)
	{
		this.securityCode = securityCode;
	}
	public String getRtnDataFormatType()
	{
		return rtnDataFormatType;
	}
	public void setRtnDataFormatType(String rtnDataFormatType)
	{
		this.rtnDataFormatType = rtnDataFormatType;
	}
}
