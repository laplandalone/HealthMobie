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
     * ����sftp������
     */
	private static ChannelSftp connect(JSONObject obj) throws Exception
	{
		String serverIp = StringUtil.getJSONObjectKeyVal(obj, "paramDesc");
		String userName = StringUtil.getJSONObjectKeyVal(obj, "paramValue");
		String password = StringUtil.getJSONObjectKeyVal(obj, "param1");
		//����JSch���� 
		JSch jsch = new JSch();
		//�����û���������ip���˿ڻ�ȡһ��Session���� 
		Session session = jsch.getSession(userName, serverIp, 22);
		//���ð��� 
		session.setPassword(password);
		Properties config = new Properties(); 
		config.put("StrictHostKeyChecking", "no"); 
		//ΪSession��������properties 
		session.setConfig(config);
		//���ɹ���Session�������� 
		session.connect();
		//��SFTPͨ�� 
		Channel channel = session.openChannel("sftp");
		// ����SFTPͨ�������� 
		channel.connect();
		return (ChannelSftp) channel;
	}
	
	/**
     * �ϴ�����
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
	 * ɾ���ļ�
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
