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
	public List qryDoctorsByHospitalId(int pageNum, int pageSize, String hospitalId, String doctorId, String teamId, String doctorName) throws QryException
	{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM (SELECT A.*, ROWNUM ROWNUMBER FROM (");
		sql.append("select t.doctor_id, t.hospital_id, t.telephone, t.post, t.name, t.register_fee, ");
		sql.append("t.sex, decode(t.expert_flag, '0', '专家') expert_flag, a.team_name ");
		sql.append("from doctor_t t, team_t a where a.team_id = t.team_id and t.hospital_id = ? and t.state = '00A' and a.expert_flag = '1' ");
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
		sql.append("order by t.order_num) A WHERE ROWNUM <= ?)  WHERE ROWNUMBER >= ? ");
		lstParam.add(pageNum * pageSize);
		lstParam.add((pageNum - 1) * pageSize + 1);
		return itzcQryCenter.executeSqlByMapList(sql.toString(), lstParam);
	}
	
	public int qryDoctorCount(String hospitalId, String doctorId, String teamId, String doctorName) throws QryException
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select t.doctor_id, t.hospital_id, t.telephone, t.post, t.name, ");
		sql.append("t.sex, decode(t.expert_flag, '0', '专家') expert_flag, a.team_name ");
		sql.append("from doctor_t t, team_t a where a.team_id = t.team_id and t.hospital_id = ? and t.state = '00A' and a.expert_flag = '1' ");
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
		sql.append("order by t.order_num ");
		return itzcQryCenter.getCount(sql.toString(), lstParam);
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
		sql.append("order by order_num ");
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
	public boolean updateHospitalMananger(String hospitalId, String doctorId, String name, String password) throws QryException
	{
		
		Connection conn = null;
		Statement stmt = null;
		boolean flag = true;
		try
		{
			String sql = "select * from hospital_manager_t where state = '00A' and hospital_id = '"+hospitalId+"' and doctor_id = '"+doctorId+"' ";
			List sList = itzcQryCenter.executeSqlByMapListWithTrans(sql, new ArrayList());
			if(ObjectCensor.checkListIsNull(sList))
			{
				sql = "update hospital_manager_t set  name='"+name+"',password='"+password+"' where state='00A' and  hospital_id='"+hospitalId+"' and doctor_id='"+doctorId+"' ";
			}
			else
			{
				sql = "insert into hospital_manager_t(hospital_id, manager_id, name, password, privs, doctor_id) values('"+hospitalId+"', '"+doctorId+"', '"+name+"', '"+password+"', '4', '"+doctorId+"') ";
			}
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
		}
		return flag;
	}
	
	public boolean deleteHospitalMananger(String hospitalId, String doctorId)
	{
		String sql = "update hospital_manager_t set  state='00X' where state='00A' and  hospital_id='"+hospitalId+"' and doctor_id='"+doctorId+"' ";
		
		Connection conn = null;
		Statement stmt = null;
		boolean flag = true;
		try
		{
			conn = itzcQryCenter.getDataSource().getConnection();
			stmt = conn.createStatement();
			stmt.execute(sql.toString());
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			flag = false;
		} 
		finally
		{
			try
			{
				stmt.close();
				conn.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				flag = false;
			}
		}
		return flag;
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
	public boolean updateDoctor(String hospitalId, String doctorId, String fee, String introduce, String skill, String post, String time, String address, String telephone, String sex) throws QryException
	{
		String sql = "update doctor_T set  register_fee='"+fee+"', introduce='"+introduce+"',skill='"+skill+"', post = '"+post+"', work_time = '"+time+"', work_address = '"+address+"', telephone = '"+telephone+"', sex = '"+sex+"' where state='00A' and  hospital_id='"+hospitalId+"' and doctor_id='"+doctorId+"'";
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
		} 
		finally
		{
			try
			{
				stmt.close();
				conn.close();

			}
			catch (Exception e)
			{
				e.printStackTrace();
				flag = false;
			}
		}
		return flag;
	}
	
	public boolean deleteDoctor(String hospitalId, String doctorId)
	{
		String sql = "update doctor_T set state='00X' where state='00A' and hospital_id='"+hospitalId+"' and doctor_id='"+doctorId+"' ";
		
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
		} 
		finally
		{
			try
			{
				stmt.close();
				conn.close();

			}
			catch (Exception e)
			{
				e.printStackTrace();
				flag = false;
			}
		}
		return flag;
	}
}
