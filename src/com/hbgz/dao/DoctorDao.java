package com.hbgz.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hbgz.model.DoctorRegisterT;
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
	public List qryDoctorsByHospitalId(String hospitalId,String doctorId) throws QryException
	{
		StringBuffer sql = new StringBuffer("select t.doctor_id,t.hospital_id,t.telephone,t.post,t.name,t.sex,decode(t.expert_flag,'0','专家')expert_flag,a.team_name from doctor_t t,team_t a where a.team_id=t.team_id and t.hospital_id=?");
		ArrayList lstParam = new ArrayList();
		lstParam.add(hospitalId);
		if(ObjectCensor.isStrRegular(doctorId))
		{
			sql.append("and t.doctor_id=?");
			lstParam.add(doctorId);
		}
		return itzcQryCenter.executeSqlByMapList(sql.toString(), lstParam);
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
