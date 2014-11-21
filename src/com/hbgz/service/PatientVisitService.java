package com.hbgz.service;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbgz.dao.DigitalHealthDao;
import com.hbgz.pub.util.ObjectCensor;

@Service
public class PatientVisitService 
{
	@Autowired
	private DigitalHealthDao digitalHealthDao;
	
	public List qryPatientVisitList(String startTime, String endTime, String visitName, String visitType, String cardId) throws Exception
	{
		List sList = null;
		if(ObjectCensor.isStrRegular(startTime, endTime))
		{
			sList = digitalHealthDao.qryPatientVisitList(startTime, endTime, visitName, visitType, cardId);
		}
		return sList;
	}

	public List qryVisitDetail(String visitId) throws Exception 
	{
		List sList = null;
		if(ObjectCensor.isStrRegular(visitId))
		{
			sList = digitalHealthDao.qryVisitDetail(visitId);
		}
		return sList;
	}
	
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

	public JSONObject qryUserList(int pageNum, int pageSize, String userName, String sex, String telephone) throws Exception 
	{
		JSONObject obj = new JSONObject();
		if(ObjectCensor.isStrRegular())
		{
			List sList = digitalHealthDao.qryUserList(pageNum, pageSize, userName, sex, telephone);
			int count = 0;
			if(ObjectCensor.checkListIsNull(sList))
			{
				obj.element("userList", sList);
				count = digitalHealthDao.qryUserCount(userName, sex, telephone);
			}
			obj.element("count", count);
		}
		return obj;
	}
}
