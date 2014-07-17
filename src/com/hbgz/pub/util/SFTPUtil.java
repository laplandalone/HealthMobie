package com.hbgz.pub.util;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SFTPUtil 
{
	private static Log log = LogFactory.getLog(SFTPUtil.class);
	
	/**
     * 连接sftp服务器
     */
	private static ChannelSftp connect(JSONObject obj) throws Exception
	{
		String serverIp = StringUtil.getJSONObjectKeyVal(obj, "paramDesc");
		String userName = StringUtil.getJSONObjectKeyVal(obj, "paramValue");
		String password = StringUtil.getJSONObjectKeyVal(obj, "param1");
		//创建JSch对象 
		JSch jsch = new JSch();
		//按照用户名，主机ip，端口获取一个Session对象 
		Session session = jsch.getSession(userName, serverIp, 22);
		//设置暗码 
		session.setPassword(password);
		Properties config = new Properties(); 
		config.put("StrictHostKeyChecking", "no"); 
		//为Session对象设置properties 
		session.setConfig(config);
		//经由过程Session建树链接 
		session.connect();
		//打开SFTP通道 
		Channel channel = session.openChannel("sftp");
		// 建树SFTP通道的连接 
		channel.connect();
		return (ChannelSftp) channel;
	}
	
	/**
     * 上传附件
     */
	public static String uploadFile(JSONObject obj, String fileName, InputStream is)
	{
		String retVal = "failure";
		try 
		{
			ChannelSftp sftp = connect(obj);
			String path = StringUtil.getJSONObjectKeyVal(obj, "param2");
			log.error(path);
			if(ObjectCensor.isStrRegular(path))
			{
				sftp.cd(path);
				sftp.put(is, fileName);
				retVal = "success";
				if(sftp != null)
				{
					if(sftp.isConnected())
					{
						sftp.disconnect();
					}
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return retVal;
	}
	
	/**
	 * 删除文件
	 */
	public static String deleteFile(JSONObject obj, String fileName)
	{
		String retVal = "failure";
		try 
		{
			ChannelSftp sftp = connect(obj);
			String path = StringUtil.getJSONObjectKeyVal(obj, "param2");
			log.error(path);
			if(ObjectCensor.isStrRegular(path))
			{
				Vector vector = sftp.ls(path);
				if(vector.size() > 0)
				{
					Iterator iterator = vector.iterator();
					while (iterator.hasNext()) 
					{
						String filesName = iterator.next().toString();
						if(filesName.indexOf(fileName) != -1)
				    	{
				    		sftp.cd(path);
				    		sftp.rm(fileName);
				    		break;
				    	}
					}
					retVal = "success";
				    if(sftp != null)
				    {
						if(sftp.isConnected())
						{
						    sftp.disconnect();
						}
				    }
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return retVal;
	}
}
