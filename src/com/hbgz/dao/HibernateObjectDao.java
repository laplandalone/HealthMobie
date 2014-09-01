package com.hbgz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hbgz.model.HospitalNewsT;
import com.hbgz.model.HospitalT;
import com.hbgz.model.HospitalUserRelationshipT;
import com.hbgz.model.RegisterOrderT;
import com.hbgz.model.TeamT;
import com.hbgz.pub.base.BaseDao;
import com.hbgz.pub.util.ObjectCensor;

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

	public List<HospitalT> qryHospitalTs(String hospitalId)
	{
		if (!ObjectCensor.isStrRegular(hospitalId))
		{
			return null;
		}
		String hql = "from HospitalT as model where model.state='00A' and model.parentId=?";
		return this.find(hql, new String[]
		{ hospitalId });
	}

	public List<RegisterOrderT> qryRegisterOrderTs(String userId)
	{
		if (!ObjectCensor.isStrRegular(userId))
		{
			return null;
		}
		String hql = "from RegisterOrderT as model where model.orderState='000' and model.state='00A' and model.userId=? order by subStr(register_time,0,10) desc";
		return this.find(hql, new String[]{ userId });
	}

	public List<TeamT> qryteamTs(String hospitalId)
	{
		if (!ObjectCensor.isStrRegular(hospitalId))
		{
			return null;
		}
		String hql = "from TeamT as model where  model.state='00A' and model.hospitalId=?";
		return this.find(hql, new String[]{ hospitalId });
	}

	public List<HospitalNewsT> qryHospitalNews(String hospitalId,String type,String typeId)
	{
		if (!ObjectCensor.isStrRegular(hospitalId,type))
		{
			return null;
		}
		String hql = "from HospitalNewsT as model where  model.state='00A' and model.effDate<=sysdate and model.expDate>=sysdate and model.hospitalId=? and model.typeId=? and model.newsType=? ";
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
		String hql = "from RegisterOrderT as model where model.orderState='000' and model.state='00A' and model.orderId=?";
		return this.find(hql, new String[]
		{ orderId });
	}
}
