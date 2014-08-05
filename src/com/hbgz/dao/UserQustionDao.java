package com.hbgz.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hbgz.model.UserQuestionT;
import com.hbgz.pub.base.BaseDao;
import com.hbgz.pub.exception.QryException;
import com.hbgz.pub.qry.QryCenter;
import com.hbgz.pub.util.ObjectCensor;

@Repository
public class UserQustionDao extends BaseDao 
{
	@Autowired
	private QryCenter itzcQryCenter;
	
	public List<UserQuestionT> qryQuestionTs(String doctorId)
	{
		if (!ObjectCensor.isStrRegular(doctorId))
		{
			return null;
		}
		List list =this.find("from UserQuestionT as model where model.recordType='ask' and  model.doctorId=? order by createDate desc", new String[] { doctorId });
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
		List list =this.find("from UserQuestionT as model where model.recordType='ask' and model.questionId=?", new String[] { questionId });
		return list;
	}
	
	public List<UserQuestionT> qryQuestionTsByIds(String questionId)
	{
		if (!ObjectCensor.isStrRegular(questionId))
		{
			return null;
		}
		List list =this.find("from UserQuestionT as model where model.questionId=? order by createDate asc", new String[] {questionId});
		return list;
	}

	public List qryQuestionList(String doctorId, String startTime, String endTime) throws QryException 
	{
		if(ObjectCensor.isStrRegular(doctorId, startTime, endTime))
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select question_id, user_id, doctor_id, user_telephone, auth_type, content, to_char(create_date, 'yyyy-MM-dd hh24:mi:ss') create_date, img_url ");
			sql.append("from user_question_t where state = '00A' and record_type = 'ask' and doctor_id = ? ");
			sql.append("and create_date between to_date(?, 'yyyy-MM-dd hh24:mi:ss') and to_date(?, 'yyyy-MM-dd hh24:mi:ss')");
			ArrayList lstParam = new ArrayList();
			lstParam.add(doctorId);
			lstParam.add(startTime);
			lstParam.add(endTime + " 23:59:59");
			return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
		}
		return null;
	}

	public List qryQuesList(String doctorId, String questionId) throws QryException
	{
		if(ObjectCensor.isStrRegular(doctorId, questionId))
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select user_telephone, record_type, auth_type, content, to_char(create_date, 'yyyy-MM-dd hh24:mi:ss') create_date ");
			sql.append("from user_question_t where state = '00A' and doctor_id = ? and question_id = ? order by create_date ");
			ArrayList lstParam = new ArrayList();
			lstParam.add(doctorId);
			lstParam.add(questionId);
			return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
		}
		return null;
	}
}
