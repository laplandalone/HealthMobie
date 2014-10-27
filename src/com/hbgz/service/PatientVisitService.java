package com.hbgz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbgz.dao.DigitalHealthDao;
import com.hbgz.pub.util.ObjectCensor;

@Service
public class PatientVisitService 
{
	@Autowired
	private DigitalHealthDao digitalHealthDao;
	
	public List qryPatientVisitList(String visitId, String startTime, String endTime) throws Exception
	{
		List sList = null;
		if(ObjectCensor.isStrRegular(visitId))
		{
			sList = digitalHealthDao.qryPatientVisitList(visitId, startTime, endTime);
		}
		return sList;
	}
}
