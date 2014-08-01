package com.hbgz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hbgz.model.UserQuestionT;
import com.hbgz.pub.base.BaseDao;
import com.hbgz.pub.util.ObjectCensor;

@Repository
public class UserQustionDao extends BaseDao 
{
	public List<UserQuestionT> qryQuestionTs(String doctorId)
	{
		if (!ObjectCensor.isStrRegular(doctorId))
		{
			return null;
		}
		List list =this.findListByProperty("UserQuestionT", "doctorId", doctorId);
		return list;
	}
	
	public List<UserQuestionT> qryQuestionTsByUserId(String userId)
	{
		if (!ObjectCensor.isStrRegular(userId))
		{
			return null;
		}
		List list =this.find("from UserQuestionT as model where model.recordType='ask' and model.userId=?", new String[] { userId });
		return list;
	}
	
	public List<UserQuestionT> qryQuestionTsByQuestionId(String questionId)
	{
		if (!ObjectCensor.isStrRegular(questionId))
		{
			return null;
		}
		List list =this.find("from UserQuestionT as model where model.recordType='ask' and model.qestionId=?", new String[] { questionId });
		return list;
	}
	
	public List<UserQuestionT> qryQuestionTsByIds(String userId,String doctorId)
	{
		if (!ObjectCensor.isStrRegular(doctorId))
		{
			return null;
		}
		List list =this.find("from UserQuestionT as model where model.doctorId=? and model.userId=?", new String[] { doctorId,userId });
		return list;
	}
}
