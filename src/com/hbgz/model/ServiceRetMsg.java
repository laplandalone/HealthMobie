package com.hbgz.model;

public class ServiceRetMsg 
{
	/*服务调用标识：success,failure*/
	private String executeType="success";
	
	/*服务调用成功返回结果*/
	private Object returnMsg;
	
	/*服务调用成功返回结果类型*/
	private String returnMsgType;
	
	/*服务调用序列标识*/
	private String serializeId;
	
	/*服务调用异常信息*/
	private String exceptionMsg;

	public String getReturnMsgType()
	{
		return returnMsgType;
	}
	public void setReturnMsgType(String returnMsgType)
	{
		this.returnMsgType = returnMsgType;
	}
	public String getExecuteType()
	{
		return executeType;
	}
	public void setExecuteType(String executeType)
	{
		this.executeType = executeType;
	}
	public Object getReturnMsg()
	{
		return returnMsg;
	}
	public void setReturnMsg(Object returnMsg)
	{
		this.returnMsg = returnMsg;
	}
	public String getSerializeId()
	{
		return serializeId;
	}
	public void setSerializeId(String serializeId)
	{
		this.serializeId = serializeId;
	}
	public String getExceptionMsg()
	{
		return exceptionMsg;
	}
	public void setExceptionMsg(String exceptionMsg)
	{
		this.exceptionMsg = exceptionMsg;
	}
}
