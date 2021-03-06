package com.hbgz.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hbgz.model.UserQuestionT;
import com.hbgz.pub.base.BaseDao;
import com.hbgz.pub.exception.QryException;
import com.hbgz.pub.qry.QryCenter;
import com.tools.pub.utils.ObjectCensor;

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
		List list =this.find("from UserQuestionT as model where model.recordType='ask' and model.authType='public' and  model.doctorId=? order by createDate desc", new String[] { doctorId });
		return list;
	}
	
	public List<UserQuestionT> qryDoctorQues(String doctorId)
	{
		if (!ObjectCensor.isStrRegular(doctorId))
		{
			return null;
		}
		List list =this.find("from UserQuestionT as model where model.recordType='ask' and  model.doctorId=? order by createDate desc", new String[] { doctorId });
		return list;
	}
	
	public List qryQuestionNoAns(String doctorId,String type) throws QryException
	{
		if (!ObjectCensor.isStrRegular(doctorId))
		{
			return null;
		}
	   StringBuffer ans = new StringBuffer();
	   StringBuffer sql = new StringBuffer("select ID,QUESTION_ID,TEAM_ID,USER_ID,DOCTOR_ID,USER_TELEPHONE,HOSPITAL_ID,RECORD_TYPE,CONTENT,to_char(create_date, 'yyyy-MM-dd')create_date");
	   sql.append(" from user_question_t where state='00A' and record_type='ask' and doctor_id ='"+doctorId+"' and question_id ");
	   if("ans".equals(type))
	   {
		   sql.append("  in  ");
	   }else 
	   {
		   sql.append(" not  in  ");
	   }
	   sql.append(" (select question_id from user_question_t t where state='00A' and doctor_id = '"+doctorId+"' and record_type='ans')");
	
	   if("ans".equals(type))
	   {
		   ans.append("select * from ( "+sql+")");
		   ans.append(" where question_id not in (");
		   
		   ans.append("select QUESTION_ID");
		   ans.append(" from  user_question_t t where state='00A' and record_type='copy' and (question_id, create_date) in ");
		   ans.append(" (select question_id, max(create_date) from user_question_t t where state='00A' and doctor_id = '"+doctorId+"' group by question_id) ) order by create_date desc ");
		   
	   } else 
	   {
		    sql.append(" union all  ");
		    
		    sql.append ("select ID,QUESTION_ID,TEAM_ID,USER_ID,DOCTOR_ID,USER_TELEPHONE,HOSPITAL_ID,RECORD_TYPE,CONTENT,to_char(create_date, 'yyyy-MM-dd')create_date");
			sql.append(" from user_question_t where state='00A' and record_type='ask' and doctor_id ='"+doctorId+"' and question_id ");
		    sql.append(" in (select QUESTION_ID");
		    sql.append(" from  user_question_t t where state='00A' and record_type='copy' and (question_id, create_date) in ");
		    sql.append(" (select question_id, max(create_date) from user_question_t t where state='00A' and  doctor_id = '"+doctorId+"' group by question_id) )order by create_date desc ");
	   }
	   
	    ArrayList lstParam = new ArrayList();
	    if("ans".equals(type))
		{
	    	return itzcQryCenter.executeSqlByMapListWithTrans(ans.toString(), lstParam);
		}else
		{
			StringBuffer noansSql= new StringBuffer("select distinct(question_id), c.* from ( "+sql+") c");
			return itzcQryCenter.executeSqlByMapListWithTrans(noansSql.toString(), lstParam);
		}
		
	}
	
	
	public List qryQuestionNoAnsCopy(String doctorId) throws QryException
	{
		if (!ObjectCensor.isStrRegular(doctorId))
		{
			return null;
		}
		StringBuffer sql = new StringBuffer("select ID,QUESTION_ID,TEAM_ID,USER_ID,DOCTOR_ID,USER_TELEPHONE,HOSPITAL_ID,RECORD_TYPE,CONTENT,to_char(create_date, 'yyyy-MM-dd')create_date");
	    sql.append(" from  user_question_t t where record_type='copy' and (question_id, create_date) in ");
	    sql.append(" (select question_id, max(create_date) from user_question_t t where doctor_id = '"+doctorId+"' group by question_id) order by create_date desc ");
		ArrayList lstParam = new ArrayList();
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
	}
	
	public List qryQuestionTsByUserId(String userId,String hospitalId) throws QryException
	{
		String sql="select a.question_id,a.user_id,a.doctor_id,a.user_telephone,a.content,to_char(a.create_date, 'yyyy-MM-dd') create_date,b.name from user_question_t a ,doctor_t b where a.state='00A' and a.record_type='ask' and a.doctor_id=b.doctor_id and a.team_id=b.team_id and user_id=? and a.hospital_Id=? order by create_date desc ";
		if (!ObjectCensor.isStrRegular(userId,hospitalId))
		{
			return null;
		}
		ArrayList lstParam = new ArrayList();
		lstParam.add(userId);
		lstParam.add(hospitalId);
		return itzcQryCenter.executeSqlByMapListWithTrans(sql, lstParam);
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
		List list =this.find("from UserQuestionT as model where model.state='00A' and model.questionId=? order by createDate ", new String[] {questionId});
		return list;
	}

	public List qryQuestionList(String doctorId, String startTime, String endTime) throws QryException 
	{
		List sList = null;
		if(ObjectCensor.isStrRegular(doctorId))
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select id, question_id, user_id, doctor_id, user_telephone, auth_type, content, to_char(create_date, 'yyyy-MM-dd hh24:mi:ss') create_date, img_url ");
			sql.append("from user_question_t where state = '00A' and record_type = 'ask' and doctor_id = ? ");
			ArrayList lstParam = new ArrayList();
			lstParam.add(doctorId);
			if(ObjectCensor.isStrRegular(startTime, endTime))
			{ 
				sql.append("and create_date between to_date(?, 'yyyy-MM-dd hh24:mi:ss') and to_date(?, 'yyyy-MM-dd hh24:mi:ss') ");
				lstParam.add(startTime);
				lstParam.add(endTime + " 23:59:59");
			}
			sql.append("order by create_date desc");
			sList = itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
		}
		return sList;
	}
	
	public List qryUserQuestionList(int pageNum, int pageSize, String doctorId, String startTime, String endTime) throws QryException 
	{
		List sList = null;
		if(ObjectCensor.isStrRegular(doctorId))
		{
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM (SELECT A.*, ROWNUM ROWNUMBER FROM (");
			sql.append("select id, question_id, user_id, doctor_id, user_telephone, auth_type, content, to_char(create_date, 'yyyy-MM-dd hh24:mi:ss') create_date, img_url ");
			sql.append("from user_question_t where state = '00A' and record_type = 'ask' and doctor_id = ? ");
			ArrayList lstParam = new ArrayList();
			lstParam.add(doctorId);
			if(ObjectCensor.isStrRegular(startTime, endTime))
			{ 
				sql.append("and create_date between to_date(?, 'yyyy-MM-dd hh24:mi:ss') and to_date(?, 'yyyy-MM-dd hh24:mi:ss') ");
				lstParam.add(startTime);
				lstParam.add(endTime + " 23:59:59");
			}
			sql.append("order by create_date desc) A WHERE ROWNUM <= ?)  WHERE ROWNUMBER >= ? ");
			lstParam.add(pageNum * pageSize);
			lstParam.add((pageNum - 1) * pageSize + 1);
			sList = itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
		}
		return sList;
	}
	
	public int qryUserQuestionCount(String doctorId, String startTime, String endTime) throws QryException 
	{
		int count = 0;
		if(ObjectCensor.isStrRegular(doctorId))
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select id, question_id, user_id, doctor_id, user_telephone, auth_type, content, to_char(create_date, 'yyyy-MM-dd hh24:mi:ss') create_date, img_url ");
			sql.append("from user_question_t where state = '00A' and record_type = 'ask' and doctor_id = ? ");
			ArrayList lstParam = new ArrayList();
			lstParam.add(doctorId);
			if(ObjectCensor.isStrRegular(startTime, endTime))
			{ 
				sql.append("and create_date between to_date(?, 'yyyy-MM-dd hh24:mi:ss') and to_date(?, 'yyyy-MM-dd hh24:mi:ss') ");
				lstParam.add(startTime);
				lstParam.add(endTime + " 23:59:59");
			}
			sql.append("order by create_date desc");
			count = itzcQryCenter.getCount(sql.toString(), lstParam);
		}
		return count;
	}

	public List qryQuesList(String doctorId, String questionId) throws QryException
	{
		if(ObjectCensor.isStrRegular(doctorId, questionId))
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select id, user_telephone, record_type, auth_type, content, to_char(create_date, 'yyyy-MM-dd hh24:mi:ss') create_date ");
			sql.append("from user_question_t where state = '00A' and doctor_id = ? and question_id = ? order by create_date desc ");
			ArrayList lstParam = new ArrayList();
			lstParam.add(doctorId);
			lstParam.add(questionId);
			return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
		}
		return null;
	}


	public List<UserQuestionT> qryUserQuestionById(String id) 
	{
		if(ObjectCensor.isStrRegular(id))
		{
			String sql = "from UserQuestionT as model where model.id = ?";
			return this.find(sql, new String[]{id});
		}
		return null;
	}
}
