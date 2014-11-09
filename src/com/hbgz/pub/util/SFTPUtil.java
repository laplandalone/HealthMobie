package com.hbgz.pub.util;

import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import net.sf.json.JSONObject;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SFTPUtil 
{
	private static String[] array = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	
    private static ChannelSftp connect(JSONObject obj) throws Exception
    {
		String server = StringUtil.getJSONObjectKeyVal(obj, "server");
		String user = StringUtil.getJSONObjectKeyVal(obj, "user");
		String password = StringUtil.getJSONObjectKeyVal(obj, "password");
		//����JSch���� 
		JSch jsch = new JSch();
		//�����û���������ip���˿ڻ�ȡһ��Session���� 
		Session session = jsch.getSession(user, server, 22);
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
    
    public static void downloadFile(JSONObject obj, String fileName, OutputStream os, ZipOutputStream zos, BufferedInputStream br) throws Exception
    {
		ChannelSftp sftp = connect(obj);
		String filePath = StringUtil.getJSONObjectKeyVal(obj, "path");
		if(ObjectCensor.isStrRegular(filePath))
		{
		    sftp.cd(filePath);
		}
		if(zos != null)
		{
		    zos.setEncoding("GBK");
		}
		String prefix = "";
		if(fileName.indexOf(";") != -1)
		{
		    String[] str = fileName.split(";");
		    prefix = str[0].substring(0, str[0].lastIndexOf("_"));
		}
		Vector vector = sftp.ls(filePath);
		if(vector.size() > 0)
		{
		    Iterator iter = vector.iterator();
		    while(iter.hasNext())
		    {
				String filesName = iter.next().toString();
				if(fileName.indexOf(";") == -1)
				{
				    if(filesName.indexOf(fileName) != -1)
				    {
						sftp.get(fileName, os);
						sftp.rm(fileName);
						break;
				    }
				}
				else
				{
				    if(filesName.indexOf(prefix) != -1)
				    {
						String[] array = fileName.split(";");
						for(int i = 0, len = array.length; i < len; i++)
						{
						    if(filesName.indexOf(array[i]) != -1)
						    {
								String zipEntryFileName = filesName.substring(filesName.lastIndexOf("_") + 1);
								zos.putNextEntry(new ZipEntry(zipEntryFileName)); 
								sftp.get(array[i], zos);
								zos.closeEntry(); 
								sftp.rm(array[i]);
								break;
						    }
						}
				    }
				}
		    }
		}
		if(zos != null)
	  	{
	  	    zos.flush();
	  	    zos.close();
	  	}
	  	if(br != null)
	  	{
	  	    int len = 0;
	  	    byte[] buf = new byte[1024];
	  	    while ((len = br.read(buf)) != -1)
	  	    {
	  	    	os.write(buf, 0, len);
	  	    }
	  	    br.close();
	  	}
	  	if(os != null)
	  	{
	  	    os.close();
	  	}
	  	if(sftp != null)
	  	{
	  		sftp.disconnect();
	  	}
    }

	public static int getFileSize(JSONObject obj, String fileName) throws Exception
	{
		int size = 0;
		ChannelSftp sftp = connect(obj);
		String filePath = StringUtil.getJSONObjectKeyVal(obj, "path");
		if(ObjectCensor.isStrRegular(filePath))
		{
		    sftp.cd(filePath);
		}
		Vector vector = sftp.ls(filePath);
		if(vector.size() > 0)
		{
			Iterator iter = vector.iterator();
			while(iter.hasNext())
			{
				String filesName = iter.next().toString();
				if(filesName.indexOf(fileName) != -1)
				{
					for(int j = 0, l = array.length; j < l; j++)
		    		{
		    			int index = filesName.indexOf(array[j]);
		    			if(index != -1)
		    			{
		    				filesName = filesName.substring(0, index - 1);
		    				filesName = filesName.substring(filesName.lastIndexOf(" ") + 1);
		    				size = Integer.parseInt(filesName);
		    				break;
		    			}
		    		}
					break;
				}
			}
		}
		if(sftp != null)
	  	{
			sftp.disconnect();
	  	}
		return size;
	}
}
