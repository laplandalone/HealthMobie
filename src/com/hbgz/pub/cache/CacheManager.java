package com.hbgz.pub.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hbgz.pub.exception.QryException;
import com.hbgz.pub.util.ObjectCensor;
import com.hbgz.pub.util.StringUtil;

/**
 * <p>1、CacheManager类对SysCacheDao类缓存数据进行服务管理。
 * <p>2、CacheManager类对缓存数据进行二次业务逻辑封装。（例:取缓存子集）
 * <p>3、CacheManager类完成对SysCacheDao类缓存数据同步。
 * @author RHQ
 */
@Component
public class CacheManager
{
	
	private static Log log = LogFactory.getLog(CacheManager.class);
	
	@Autowired
	private SysCacheDao sysCacheDao;
	
	public String getImgIp(String hospitalId) throws QryException
	{
		List list = sysCacheDao.getHospitalConfig();
		HashMap mapComp = new HashMap();
		mapComp.put("hospitalId",hospitalId);
		mapComp.put("configType", "IMGWEB");
		mapComp.put("configName", "imgip");
		List subList = StringUtil.getSubMapList(list, mapComp);
		if(ObjectCensor.checkListIsNull(subList))
		{
			Map mapT=(Map) subList.get(0);
			return StringUtil.getMapKeyVal(mapT,"configVal");
		}
		return "";
	}
	
	public String getUplodPathByType(String hospitalId,String type) throws QryException
	{
		List list = sysCacheDao.getHospitalConfig();
		HashMap mapComp = new HashMap();
		mapComp.put("hospitalId",hospitalId);
		mapComp.put("configType", "UPLOAD");
		mapComp.put("configName", type);
		List subList = StringUtil.getSubMapList(list, mapComp);
		if(ObjectCensor.checkListIsNull(subList))
		{
			Map mapT=(Map) subList.get(0);
			return StringUtil.getMapKeyVal(mapT,"configVal");
		}
		return "";
	}
	
	public List getNewsType(String hospitalId,String type,String configName) throws QryException
	{
		List list = sysCacheDao.getHospitalConfig();
		HashMap mapComp = new HashMap();
		mapComp.put("hospitalId",hospitalId);
		mapComp.put("configType", configName);
		mapComp.put("configName", type);
		List subList = StringUtil.getSubMapList(list, mapComp);
		if(ObjectCensor.checkListIsNull(subList))
		{
			return subList;
		}
		return null;
	}
	
	public String getNewsTypeById(String hospitalId,String id) throws QryException
	{
		List list = sysCacheDao.getHospitalConfig();
		HashMap mapComp = new HashMap();
		mapComp.put("hospitalId",hospitalId);
		mapComp.put("configId",id);
//		mapComp.put("configName", "HOSPITALNEWS");
		List subList = StringUtil.getSubMapList(list, mapComp);
		System.out.println(subList);
		if(ObjectCensor.checkListIsNull(subList))
		{
			Map map = (Map) subList.get(0);
			return StringUtil.getMapKeyVal(map, "configVal");
		}
		return "";
	}
	
	public List getWakeType(String hospitalId, String configName) throws Exception
	{
		List list = sysCacheDao.getHospitalConfig();
		HashMap mapComp = new HashMap();
		mapComp.put("hospitalId",hospitalId);
		mapComp.put("configName", configName);
		List subList = StringUtil.getSubMapList(list, mapComp);
		if(ObjectCensor.checkListIsNull(subList))
		{
			return subList;
		}
		return null;
	}
	
	public Map getAuthCode(String accNbr)
	{
		return sysCacheDao.getAuthCode(accNbr);
	}

	public List qryTeamList(String hospitalId) throws QryException 
	{
		return sysCacheDao.qryTeamList(hospitalId);
	}

	public List qryDoctorList(String hospitalId) throws QryException 
	{
		return sysCacheDao.qryDoctorList(hospitalId);
	}
	
	public void delConfigCache()
	{
		sysCacheDao.delConfigCache();
	}
}
