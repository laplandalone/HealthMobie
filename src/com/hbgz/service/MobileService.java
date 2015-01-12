package com.hbgz.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hbgz.dao.UserDao;
import com.hbgz.pub.exception.QryException;
import com.hbgz.pub.file.FileBiz;
import com.hbgz.pub.sequence.SysId;
import com.hbgz.pub.util.DateUtils;
import com.hbgz.pub.util.FileUtil;
import com.hbgz.pub.util.FileUtils;
import com.tools.pub.invoke.ServiceEngine;
import com.tools.pub.utils.ObjectCensor;

@Service
public class MobileService
{
	private static Log log = LogFactory.getLog(MobileService.class);

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SysId sysId;
	
	
	
	public String axis(String param) throws Exception
	{
		return ServiceEngine.invokeService(param);
	}

	public List getUserList() throws QryException
	{
		return userDao.getUserList();
	}
	
	public String uploadFile(MultipartHttpServletRequest request, String userId, String medicalName,String remark) throws Exception
	{
		String fileId = request.getParameter("fileId");
		String storeId = request.getParameter("storeId");
	
		Map<String, MultipartFile> map = request.getFileMap();
		boolean flag=false;
		for (Map.Entry<String, MultipartFile> entity : map.entrySet())
		{
			MultipartFile partFile = entity.getValue();
			String fileName = partFile.getOriginalFilename();
			log.error(new FileUtil().isPicFile(fileName));
			System.err.println(fileName);
			String savePath = FileBiz.getUploadDir(null);
			String fullPath = FileBiz.getFullPath(savePath);
			
			if (new FileUtil().isPicFile(fileName))
			{
				File localFile = new File(fileName);
				partFile.transferTo(localFile);
				InputStream is = new FileInputStream(localFile);
				double fileSize = FileUtils.create(fullPath, fileName, is);
				String imgUrl=savePath+fileName;
				
				System.err.println(imgUrl);
				if(!ObjectCensor.isStrRegular(fileId))
				{
					 fileId=sysId.getId()+"";
					 String orderId=sysId.getId()+"";
					 String seq=orderId.substring(fileId.length()-3,fileId.length());
					 String medicalCode=storeId+"-"+DateUtils.CHN_DATE_FORMAT.format(new Date())+"-"+seq;
					 flag= userDao.addUserUploadFile(fileId,userId, imgUrl, remark, "0", medicalName,orderId,medicalCode);
				}
				String imgId=sysId.getId()+"";
				userDao.addImgUrl(fileId, imgId, imgUrl);
			}
		}
		return fileId;
	}
	
	public List getStoreList() throws QryException
	{
		return userDao.getStoreList();
	}
	
	public Map<String,String> getUserById(String userId) throws QryException
	{
		return userDao.getUserById(userId);
	}
	
	public boolean userOper(String userId , String userName , String password , String roleId , String storeId)
	{
		boolean flag = false;
		if(ObjectCensor.isStrRegular(userId))
		{
			flag = userDao.updateUser(userId, userName, password, roleId, storeId);
		}
		else
		{
			userId = String.valueOf(sysId.getId());
			flag = userDao.addUser(userId, userName, password, roleId, storeId);
		}
		return flag;
	}
	
	public boolean delUser(String userId)
	{
		return userDao.delUser(userId);
	}
	
	public Map<String,String> getStoreById(String storeId) throws QryException
	{
		return userDao.getStoreById(storeId);
	}
	
	public boolean storeOper(String storeId , String storename , String remark)
	{
		boolean flag = false;
		if(ObjectCensor.isStrRegular(storeId))
		{
			flag = userDao.updateStore(storeId, storename, remark);
		}
		else
		{
			storeId = String.valueOf(sysId.getId());
			flag = userDao.addStore(storeId, storename, remark);
		}
		return flag;
	}
	
	public boolean delStore(String storeId)
	{
		return userDao.delStore(storeId);
	}
}
