package com.hbgz.controller;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hbgz.model.UserQuestionT;
import com.hbgz.pub.cache.CacheManager;
import com.hbgz.pub.invoke.ServiceEngine;
import com.hbgz.pub.resolver.BeanFactoryHelper;
import com.hbgz.pub.util.FileUtils;
import com.hbgz.pub.util.JsonUtils;

public class FileUploadServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	private Log log = LogFactory.getLog(FileUploadServlet.class);

	private String projectPath = System.getProperty("user.dir");

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	{
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	{
		CacheManager cacheManager = (CacheManager) BeanFactoryHelper.getBean("cacheManager");

		String retVal = "";
		// 创建文件处理工厂,它用于生成FileItem对象
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 创建request的解析器,它会将数据封装到FileItem对象中
		ServletFileUpload sfu = new ServletFileUpload(factory);
		// 解析保存在request中的数据并返回list集合
		List items;
		try 
		{
			String path = "";
			items = sfu.parseRequest(request);
			// 遍历list集合,取出每一个输入项的FileItem对象,并分别获取数据
			Iterator itr = items.iterator();
			JSONObject object = new JSONObject();
			String imgages = "";

			while (itr.hasNext()) 
			{
				FileItem item = (FileItem) itr.next();
				if (item.isFormField()) 
				{
					object.element(item.getFieldName(), item.getString("UTF-8"));
				} 
				else 
				{
					File tempFile = new File(item.getName());
					String fileName = tempFile.getName();
					InputStream is = item.getInputStream();
					String hospitalId = object.getString("hospitalId");
					String uploadType = object.getString("uploadType");
					path = cacheManager.getUplodPathByType(hospitalId, uploadType);
					log.error("projectPath:" + projectPath);
					String fullPath = projectPath + path;
					log.error("upland_fullpath:" + fullPath);
					double fileSize = FileUtils.create(fullPath, fileName, is);
					String imgUrl = path + fileName;
					is.close();
					imgages += imgUrl + ",";
				}
			}
			String questionTs = object.getString("questionT");
			UserQuestionT questionT = (UserQuestionT) JsonUtils.toBean(questionTs, UserQuestionT.class);
			questionT.setImgUrl(imgages);
			JSONObject str = JSONObject.fromObject(questionT);
			System.out.println("FileUpload:"+str);
			String param = "{channel:\"Q\",channelType:\"Android\",serviceType:\"BUS2007\",securityCode:\"0000000000\",params:{'userQestion':'"
					+ str.toString() + "'},rtnDataFormatType:\"JSONObject\"}";
			retVal = ServiceEngine.invokeService(param);
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.println(retVal);
			out.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
