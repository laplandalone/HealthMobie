package com.hbgz.model;

public class ServiceRetMsg 
{
	/*������ñ�ʶ��success,failure*/
	private String executeType="success";
	
	/*������óɹ����ؽ��*/
	private Object returnMsg;
	
	/*������óɹ����ؽ������*/
	private String returnMsgType;
	
	/*����������б�ʶ*/
	private String serializeId;
	
	/*��������쳣��Ϣ*/
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
