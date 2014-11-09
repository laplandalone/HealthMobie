package com.hbgz.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hbgz.pub.util.ObjectCensor;
import com.hbgz.pub.util.SFTPUtil;

@Controller	
@RequestMapping("/file.htm")
public class FileController 
{
	private Log log = LogFactory.getLog(FileController.class);
	
	String configDir = System.getProperty("user.dir");
    //单个下载
    @RequestMapping(params = "method=downloadFile")
    public void downloadFile(String fileName, HttpServletResponse response)
    {
		try 
		{
			System.out.println(configDir);
			System.out.println(fileName);
//			String pathname=configDir+"\\"+fileName;
			String pathname=configDir+"/"+fileName;
			File file = new File(pathname);
			String filedownload = fileName;
			
			response.reset();
			String filedisplay = fileName;
			String filenamedownload = URLEncoder.encode(filedisplay, "UTF-8");
			filenamedownload = filenamedownload.replaceAll("\\+", "%20");
			//设置文件类型（二进制） 
			response.setContentType("application/x-download; charset=UTF-8");
			response.setHeader("Content-Disposition","attachment;filename=" + filenamedownload);
			int fileSize =0;
			fileSize=new Long(file.length()).intValue();
			log.error("下载文件大小：" + file.length());
			if(fileSize != 0)
			{
				response.setContentLength(fileSize);
			}
			OutputStream os = response.getOutputStream();
			InputStream br = new FileInputStream(file); 
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
//			realDownloadFile(ipAddr, filedownload, os, null, null, staffId);
		}
		catch (Exception e) 
		{
		    e.printStackTrace();
		}
    }

    
    private void realDownloadFile(String remoteAddr, String fileName, OutputStream os, ZipOutputStream zos, BufferedInputStream br, String staffId) throws Exception
    {
		JSONObject obj = null;
		if(fileName.indexOf(";") == -1)
		{
		    fileName = staffId + "_" + fileName;
		}
		else
		{
			if(fileName.lastIndexOf(";") != -1)
			{
				fileName = staffId + "_" + fileName.substring(0, fileName.length() - 1);
			}
			else
			{
				String[] array = fileName.split(";");
				StringBuffer sb = new StringBuffer();
				for(int i = 0, len = array.length; i < len; i++)
				{
					sb.append(staffId + "_" + array[i] + ";");
				}
				fileName = sb.substring(0, sb.length() - 1);
			}
		}
		SFTPUtil.downloadFile(obj, fileName, os, zos, br);
    }
    
    private int getFileSize(String remoteAddr, String fileName, String staffId) throws Exception
	{
    	JSONObject obj = null;
    	fileName = staffId + "_" + fileName;
    	log.error(fileName);
		return SFTPUtil.getFileSize(obj, fileName);
	}
    
    /**
     * 用于异常处理的方法
     */
    @ExceptionHandler(Exception.class)
    public String myExceptionHandler(Exception ex, HttpServletRequest request) 
    {
		request.setAttribute("exception", ex);
		return "error";	
    }
}
