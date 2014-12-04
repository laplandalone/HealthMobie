package com.hbgz.thread;

import com.hbgz.pub.cloudPush.AndroidPushBroadcastMsg;

public class AndroidPushMsgThread implements Runnable
{
	private String title;
	private String description;
	private String msgType;
	private String userId;
	private String customParam;
	public String getTitle() 
	{
		return title;
	}
	public void setTitle(String title) 
	{
		this.title = title;
	}
	public String getDescription() 
	{
		return description;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}
	public String getMsgType() 
	{
		return msgType;
	}
	public void setMsgType(String msgType) 
	{
		this.msgType = msgType;
	}
	public String getUserId() 
	{
		return userId;
	}
	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	public String getCustomParam() 
	{
		return customParam;
	}
	public void setCustomParam(String customParam) 
	{
		this.customParam = customParam;
	}
	public void run() 
	{
		AndroidPushBroadcastMsg.pushMsg("msg", "{\"title\":\"ÕÆÉÏÑÇÐÄ\",\"description\":\""+description+"\",\"msg_type\":\""+msgType+"\",\"user_id\":\"\",custom_param:"+customParam+"}");
	}
}
