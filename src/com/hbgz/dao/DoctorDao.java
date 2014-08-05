package com.hbgz.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hbgz.pub.base.BaseDao;
import com.hbgz.pub.exception.QryException;
import com.hbgz.pub.qry.QryCenter;
import com.hbgz.pub.util.ObjectCensor;

@Repository
public class DoctorDao extends BaseDao
{
	@Autowired
	QryCenter itzcQryCenter;

	/**
	 * 根据医院ID获取医生
	 * @param hospitalId
	 * @return
	 * @throws QryException
	 */
	public List qryDoctorsByHospitalId(String hospitalId, String doctorId, String teamId, String doctorName) throws QryException
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select t.doctor_id, t.hospital_id, t.telephone, t.post, t.name, ");
		sql.append("t.sex, decode(t.expert_flag, '0', '专家') expert_flag, a.team_name ");
		sql.append("from doctor_t t, team_t a where a.team_id = t.team_id and t.hospital_id = ? ");
		ArrayList lstParam = new ArrayList();
		lstParam.add(hospitalId);
		if(ObjectCensor.isStrRegular(doctorId))
		{
			sql.append("and t.doctor_id = ? ");
			lstParam.add(doctorId);
		}
		if(ObjectCensor.isStrRegular(teamId))
		{
			sql.append("and t.team_id = ? ");
			lstParam.add(teamId);
		}
		if(ObjectCensor.isStrRegular(doctorName))
		{
			sql.append("and upper(t.name) like upper('%"+doctorName+"%') ");
		}
		return itzcQryCenter.executeSqlByMapList(sql.toString(), lstParam);
	}
	
	public List qryDoctorList(String hospitalId, String teamId) throws QryException 
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from doctor_t where state = '00A' and hospital_id = ? ");
		ArrayList lstParam = new ArrayList();
		lstParam.add(hospitalId);
		if(ObjectCensor.isStrRegular(teamId))
		{
			sql.append("and team_id = ? ");
			lstParam.add(teamId);
		}
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
	}
	
	/**
	 * 更新用户名，密码
	 * @param hospitalId
	 * @param doctorId
	 * @param name
	 * @param password
	 * @return
	 * @throws QryException
	 */
	public boolean updateHospitalMananger(String hospitalId,String doctorId,String name,String password) throws QryException
	{
		String sql = "update hospital_manager_t set  name='"+name+"',password='"+password+"' where state='00A' and  hospital_id='"+hospitalId+"' and doctor_id='"+doctorId+"'";
		
		Connection conn = null;
		Statement stmt = null;
		boolean flag = true;
		try
		{
			conn = itzcQryCenter.getDataSource().getConnection();
			stmt = conn.createStatement();
			stmt.execute(sql.toString());
		} catch (Exception e)
		{
			e.printStackTrace();
			flag = false;
		} finally
		{
			try
			{
				stmt.close();
				conn.close();

			} catch (Exception e)
			{
				e.printStackTrace();
				flag = false;
			}
			return flag;
		}
	}
	
}
