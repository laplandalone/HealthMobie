package com.hbgz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hbgz.model.HospitalNewsT;
import com.hbgz.model.HospitalT;
import com.hbgz.model.HospitalUserRelationshipT;
import com.hbgz.model.PatientVisitT;
import com.hbgz.model.RegisterOrderT;
import com.hbgz.model.TeamT;
import com.hbgz.model.UserContactT;
import com.hbgz.model.UserRelateT;
import com.hbgz.model.WakeT;
import com.hbgz.pub.base.BaseDao;
import com.tools.pub.utils.ObjectCensor;

@Repository
public class HibernateObjectDao extends BaseDao
{

	public List<HospitalUserRelationshipT> qryHospitalUserRelationshipT(String userId)
	{
		if (!ObjectCensor.isStrRegular(userId))
		{
			return null;
		}
		List<HospitalUserRelationshipT> list = this.findListByProperty("HospitalUserRelationshipT",
				"userId", userId);
		return list;
	}

	public List<HospitalT> qryHospitalTs()
	{
		String hql = "from HospitalT as model where model.hospitalId!='102' and model.state='00A' order by hospital_id desc ";
		return this.find(hql);
	}

	public List<RegisterOrderT> qryRegisterOrderTs(String userId,String hospitalId)
	{
		if (!ObjectCensor.isStrRegular(userId,hospitalId))
		{
			return null;
		}
		String hql = "from RegisterOrderT as model where model.hospitalId=? and model.state='00A' and model.userId=? and to_date(substr(register_time, 0, 10)||' 23:59:59', 'yyyy-mm-dd hh24:mi:ss')>=sysdate order by subStr(register_time,0,10) desc,subStr(register_time,length(register_time)-2,length(register_time))";
		return this.find(hql, new String[]{hospitalId,userId });
	}

	public List<TeamT> qryteamTs(String hospitalId)
	{
		if (!ObjectCensor.isStrRegular(hospitalId))
		{
			return null;
		}
		String hql = "from TeamT as model where  model.state='00A' and (model.teamType='1' or model.teamType='0') and model.hospitalId=?";
		return this.find(hql, new String[]{ hospitalId });
	}

	public List<HospitalNewsT> qryHospitalNews(String hospitalId,String type,String typeId)
	{
		if (!ObjectCensor.isStrRegular(hospitalId,type))
		{
			return null;
		}
		String hql = "from HospitalNewsT as model where  model.state='00A' and model.effDate<=sysdate and model.expDate>=sysdate and model.hospitalId=? and model.typeId=? and model.newsType=? order by createDate desc";
		return this.find(hql, new String[]{ hospitalId, typeId, type });
	}
	
	public List<HospitalNewsT> getNewsById(String hospitalId, String newsId)
	{
		if(ObjectCensor.isStrRegular(hospitalId, newsId))
		{
			String hql = "from HospitalNewsT as model where model.hospitalId = ? and model.newsId = ? ";
			return this.find(hql, new String[]{ hospitalId, newsId});
		}
		return null;
	}

	public List<RegisterOrderT> qryRegisterOrderT(String orderId)
	{
		if (!ObjectCensor.isStrRegular(orderId))
		{
			return null;
		}
		String hql = "from RegisterOrderT as model where  model.orderId=?";
		return this.find(hql, new String[]{ orderId });
	}
	
	public List<UserContactT> qryUserContactT(String userId)
	{
		if (!ObjectCensor.isStrRegular(userId))
		{
			return null;
		}
		String hql = "from UserContactT as model where  model.state='00A' and model.userId=?";
		return this.find(hql, new String[]{ userId });
	}
	
	public List<UserContactT> qryUserContactT(String userId,String no)
	{
		if (!ObjectCensor.isStrRegular(userId))
		{
			return null;
		}
		String hql = "from UserContactT as model where  model.state='00A' and model.userId=? and model.contactNo=?";
		return this.find(hql, new String[]{ userId ,no});
	}
	
	public List<UserRelateT> qryUserRelateT(String userId)
	{
		if (!ObjectCensor.isStrRegular(userId))
		{
			return null;
		}
		String hql = "from UserRelateT as model where  model.state='00A' and model.userId=?";
		return this.find(hql, new String[]{ userId });
	}


	public List<UserRelateT> qryUserRelateTByPhone(String userId,String phone)
	{
		if (!ObjectCensor.isStrRegular(userId,phone))
		{
			return null;
		}
		String hql = "from UserRelateT as model where  model.state='00A' and model.userId=? and model.relatePhone=?";
		return this.find(hql, new String[]{ userId,phone });
	}
	
	public List<UserRelateT> qryUserWake(String userId)
	{
		if (!ObjectCensor.isStrRegular(userId))
		{
			return null;
		}
		String hql = "from WakeT as model where  model.state='00A' and model.userId=? and model.wakeType='notice'  order by createDate desc";
		return this.find(hql, new String[]{ userId});
	}
	
	public List<WakeT> qryUserWakeById(String visitId)
	{
		if (!ObjectCensor.isStrRegular(visitId))
		{
			return null;
		}
		String hql = "from WakeT as model where  model.state='00A' and model.wakeValue=? and model.wakeType='notice'";
		return this.find(hql, new String[]{ visitId});
	}
	
	public List<PatientVisitT> qryPatientVisit()
	{
		String hql = "from PatientVisitT as model where  model.state='00A' and model.copyFlag='N' ";
		return this.find(hql, new String[]{});
	}
}
