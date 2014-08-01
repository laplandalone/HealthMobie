package com.hbgz.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbgz.dao.UserDao;
import com.hbgz.pub.annotation.ServiceType;
import com.hbgz.pub.util.ObjectCensor;
import com.hbgz.pub.util.StringUtil;
import com.hbgz.pub.util.SystemProperties;

@Service(value = "BUS102")
public class LoginService
{

	@Autowired
	private UserDao userDao;
	
	
	
	@ServiceType(value = "BUS1023")
	public Map validInfo(String userName, String password) throws Exception
	{
		if (ObjectCensor.isStrRegular(userName, password))
		{
			List userLst = userDao.getUser(userName, password);

			if (userLst != null && userLst.size() != 0)
			{
				return (Map) userLst.get(0);
			}
		}
		return null;
	}
	
	
	public List getUserFile(String medName , String startTime , String endTime) throws Exception
	{
		List userLst = userDao.qryUserFile(medName , startTime , endTime);
		List imgLst = userDao.qryFileImgUrls();
		if (userLst != null && userLst.size() != 0)
		{
			for(int i=0;i<userLst.size();i++)
			{
				Map map = (Map) userLst.get(i);
				String fileId=StringUtil.getMapKeyVal(map, "fileId");
				Map mapT=new HashMap();
				mapT.put("fileId", fileId);
				List imgT=StringUtil.getSubMapList(imgLst, mapT);
				if (imgT != null && imgT.size() != 0)
				{
					for(int n=0;n<imgT.size();n++)
					{
						Map url=(Map) imgT.get(n);
						map.put("imgUrl"+n, StringUtil.getMapKeyVal(url, "imgUrl"));
					}
				}
			}
			return userLst;
		}
		return null;
	}
	
	
	@ServiceType(value = "BUS1022")
	public JSONObject getUserFileForMobile(int pageNum, int pageSize, String userId, String startTime, String endTime) throws Exception
	{
		JSONObject obj = new JSONObject();
		if (ObjectCensor.isStrRegular(userId))
		{
			List userLst = userDao.qryUserFileForMobile(pageNum, pageSize, userId, startTime, endTime);
			List imgLst = userDao.qryFileImgUrls();
			if (userLst != null && userLst.size() != 0)
			{
				String httpUrl=SystemProperties.getHttpUrl();
				for(int i=0;i<userLst.size();i++)
				{
					Map map = (Map) userLst.get(i);
					String fileId=StringUtil.getMapKeyVal(map, "fileId");
					Map mapT=new HashMap();
					mapT.put("fileId", fileId);
					List imgT=StringUtil.getSubMapList(imgLst, mapT);
					if (imgT != null && imgT.size() != 0)
					{
						for(int n=0;n<imgT.size();n++)
						{
							Map url=(Map) imgT.get(n);
							map.put("imgUrl"+n, httpUrl+StringUtil.getMapKeyVal(url, "imgUrl"));
						}
					}
				}
				obj.element("custList", userLst);
				return obj;
			}
		}
		return obj;
	}
	public boolean updateUserFileById(String fileId,String state,String comments , String verUserId) throws Exception
	{
		if (ObjectCensor.isStrRegular(fileId))
		{
			return userDao.updateUserUploadFile(fileId, state, comments, verUserId);

		}
		return false;
	}
	
	
}
