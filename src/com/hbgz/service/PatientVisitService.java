package com.hbgz.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbgz.dao.DigitalHealthDao;
import com.hbgz.dao.HibernateObjectDao;
import com.hbgz.pub.exception.QryException;
import com.hbgz.pub.util.JsonUtils;
import com.tools.pub.annotation.ServiceType;
import com.tools.pub.utils.ObjectCensor;
import com.tools.pub.utils.StringUtil;

@Service(value = "BUS300")
public class PatientVisitService 
{
	@Autowired
	private DigitalHealthDao digitalHealthDao;
	
	@Autowired
	private HibernateObjectDao hibernateObjectDao;
	
	@ServiceType(value = "BUS3001")
	public JSONArray getVisitPatients(String copyFlag)throws QryException
	{
		List patientVisits = digitalHealthDao.qryPatientVisits(copyFlag);
		JSONArray jsonArray = JsonUtils.fromArrayTimestamp(patientVisits);
		return jsonArray;
	}

	@ServiceType(value = "BUS3002")
	public JSONArray qryVisitDetail(String visitId) throws Exception 
	{
		JSONArray array = new JSONArray();
		if(ObjectCensor.isStrRegular(visitId))
		{
			List sList = digitalHealthDao.qryVisitDetail(visitId);
			if(ObjectCensor.checkListIsNull(sList))
			{
				array = JSONArray.fromObject(sList);
			}
		}
		return array;
	}
	
	@ServiceType(value = "BUS3003")
	public JSONObject qryPatientVisitById(String visitId) throws Exception 
	{
		JSONObject obj = new JSONObject();
		if(ObjectCensor.isStrRegular(visitId))
		{
			List sList = digitalHealthDao.qryPatientVisitById(visitId);
			if(ObjectCensor.checkListIsNull(sList))
			{
				obj = JSONObject.fromObject(sList.get(0));
			}
		}
		return obj;
	}
	
	@ServiceType(value = "BUS3004")
	public JSONArray getVisitPatientsById(String doctorId,String copyFlag)throws QryException
	{
		System.out.println("------BUS3004-----------");
		List patientVisits = digitalHealthDao.qryPatientVisits(copyFlag);
		if(patientVisits!=null && patientVisits.size()>0)
		{
			JSONArray jsonArray = JsonUtils.fromArrayTimestamp(patientVisits);
			System.out.println("------------------");
			return jsonArray;
		}else
		{
			System.out.println("------12------------");
			return new JSONArray();
		}
		
	}
	
	public List qryPatientVisitList(String startTime, String endTime, String visitName, String visitType, String cardId) throws Exception
	{
		List sList = null;
		if(ObjectCensor.isStrRegular(startTime, endTime))
		{
			sList = digitalHealthDao.qryPatientVisitList(startTime, endTime, visitName, visitType, cardId);
		}
		return sList;
	}

	public JSONObject qryUserList(int pageNum, int pageSize, String userName, String sex, String telephone) throws Exception 
	{
		JSONObject obj = new JSONObject();
		List sList = digitalHealthDao.qryUserList(pageNum, pageSize, userName, sex, telephone);
		int count = 0;
		if(ObjectCensor.checkListIsNull(sList))
		{
			obj.element("userList", sList);
			count = digitalHealthDao.qryUserCount(userName, sex, telephone);
		}
		obj.element("count", count);
		return obj;
	}
	
	public JSONObject qryUserLoginActivityList(int pageNum, int pageSize, String startTime, String endTime, String hospitalId) throws Exception
	{
		JSONObject obj = new JSONObject();
		int count = 0;
		List sList = digitalHealthDao.qryUserLoginActivityList(pageNum, pageSize, startTime, endTime, hospitalId);
		if(ObjectCensor.checkListIsNull(sList))
		{
			obj.element("activityList", sList);
			List list = digitalHealthDao.qryUserLoginActivityCount(startTime, endTime, hospitalId);
			if(ObjectCensor.checkListIsNull(list))
			{
				count = Integer.parseInt(StringUtil.getMapKeyVal((Map) list.get(0), "count"));
				String totalNum = StringUtil.getMapKeyVal((Map) list.get(0), "totalNum");
				obj.element("totalNum", totalNum);
			}
		}
		obj.element("count", count);
		return obj;
	}
	 
}
