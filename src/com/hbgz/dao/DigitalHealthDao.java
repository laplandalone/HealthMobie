package com.hbgz.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hbgz.pub.exception.QryException;
import com.hbgz.pub.qry.QryCenter;
import com.hbgz.pub.util.ObjectCensor;

@Repository
public class DigitalHealthDao
{
	@Autowired
	QryCenter itzcQryCenter;

	public List getUserHospitalIds(String userId)
	{

		return null;
	}

	// 根据类型获取医生列表
	public List getDoctorByType(String expertType, String onLineType, String teamId)
			throws QryException
	{
		StringBuffer sql = new StringBuffer();
		ArrayList lstParam = new ArrayList();
		sql.append("select t.doctor_id,t.hospital_id,t.name,decode(t.sex,'0','男','1','女')sex,t.telephone,to_char(t.create_date,'yyyy-MM-dd HH24:mm:ss')create_date,t.post,t.expert_flag,t.online_flag,t.introduce,t.skill,t.team_id,work_time,t.register_num,(select t.config_val  from hospital_config_t t where config_name='imgip' and config_type='IMGWEB' and t.state='00A')||t.photo_url photo_url,t.register_fee,t.work_address from doctor_t t where state='00A' ");

		if (ObjectCensor.isStrRegular(expertType))
		{
			sql.append(" and t.expert_flag=?");
			lstParam.add(expertType);
		}
		if (ObjectCensor.isStrRegular(expertType))
		{
			sql.append(" and t.online_flag=?");
			lstParam.add(onLineType);
		}
		if (ObjectCensor.isStrRegular(teamId))
		{
			sql.append(" and t.team_id=?");
			lstParam.add(teamId);
		}
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
	}

	// 根据类型获取科室列表
	public List getTeamByType(String hospitalId, String expertType) throws QryException
	{
		StringBuffer sql = new StringBuffer();
		ArrayList lstParam = new ArrayList();
		sql.append("select t.team_id,t.team_name,t.introduce,(select t.config_val  from hospital_config_t t where config_name='imgip' and config_type='IMGWEB' and t.state='00A')||img_url img_url from team_t t where state='00A' ");

		if (ObjectCensor.isStrRegular(expertType))
		{
			sql.append(" and t.expert_flag=?");
			lstParam.add(expertType);
		}

		if (ObjectCensor.isStrRegular(hospitalId))
		{
			sql.append(" and t.hospital_id=?");
			lstParam.add(hospitalId);
		}
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
	}
    
	/**
     * 查询用户预约
     * @param userId
     * @return
     * @throws QryException
     */
	public List getOrderByUserId(String userId) throws QryException
	{
		if (!ObjectCensor.isStrRegular(userId))
		{
			return null;
		}
		String sql = "select  t.*  from REGIStER_ORDER_T t where order_state='000' and state='00A' and  user_id=?";
		ArrayList lstParam = new ArrayList();
		lstParam.add(userId);
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
	}
    
	/**
	 * 查询科室可预约号
	 * @param teamId
	 * @return
	 * @throws QryException
	 */
	public List getOrderByTeamId(String teamId) throws QryException
	{
		if (!ObjectCensor.isStrRegular(teamId))
		{
			return null;
		}
		String sql = "select distinct (a.doctor_id),a.doctor_id,a.name ,a.post,b.team_name,a.introduce from doctor_register_t t,doctor_t a,team_t b where t.state='00A' and a.doctor_id=t.doctor_id and b.team_id=t.team_id and t.team_id=?";
		ArrayList lstParam = new ArrayList();
		lstParam.add(teamId);
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
	}
    /**
     * 查询专家可预约号
     * @param doctorId
     * @return
     * @throws QryException
     */
	public List getOrderByDoctorId(String doctorId) throws QryException
	{
		if (!ObjectCensor.isStrRegular(doctorId))
		{
			return null;
		}
		String sql = "select t.*,a.team_name from doctor_register_t t,team_T a where t.state='00A' and a.team_id=t.team_id and t.doctor_id=?";
		ArrayList lstParam = new ArrayList();
		lstParam.add(doctorId);
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
	}

	/**
	 * 查询用户信息
	 * @param telephone
	 * @param password
	 * @return
	 * @throws QryException
	 */
	public List qryUserByTelephone(String telephone, String password) throws QryException
	{
		if (!ObjectCensor.isStrRegular(telephone,password))
		{
			return null;
		}
		String sql = "select t.user_id,t.user_name,t.telephone,t.sex,t.password,t.user_no from hospital_user_t t where t.telephone=? and t.password=? ";
		ArrayList lstParam = new ArrayList();
		lstParam.add(telephone);
		lstParam.add(password);
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
	}

	/**
	 * 查询专家可预约号
	 * @param doctorId
	 * @return
	 * @throws QryException
	 */
	public List qryOrderTotalNum(String doctorId) throws QryException
	{
		if (!ObjectCensor.isStrRegular(doctorId))
		{
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*)+1 order_num, register_id,register_time from register_order_t ");
		sql.append(" where order_state = '000' and state = '00A'");
		sql.append(" and doctor_id = ? group by register_id,register_time");
		ArrayList lstParam = new ArrayList();
		lstParam.add(doctorId);
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
	}
   /**
    * 查询普通预约号
    * @param teamId
    * @param registerTime
    * @return
    * @throws QryException
    */
	public List qryOrderNormalTotal(String teamId, String registerTime) throws QryException
	{
		if (!ObjectCensor.isStrRegular(teamId,registerTime))
		{
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(order_id)+1 num from register_order_t ");
		sql.append(" where order_state = '000' and state = '00A' and  register_id='0' and team_id =? ");
		sql.append(" and REPLACE(register_time,' ','')=?");
		ArrayList lstParam = new ArrayList();
		lstParam.add(teamId);
		lstParam.add(registerTime);
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
	}

	/**
	 * 查询更新版本
	 * @param hospitalId
	 * @param configType
	 * @return
	 * @throws QryException
	 */
	public List getVerstion(String hospitalId, String configType) throws QryException
	{
		if (!ObjectCensor.isStrRegular(hospitalId,configType))
		{
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select config_name,config_val from hospital_config_t ");
		sql.append(" where  state = '00A' and hospital_id =? ");
		sql.append(" and config_type=? ");
		ArrayList lstParam = new ArrayList();
		lstParam.add(hospitalId);
		lstParam.add(configType);
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
	}
	
    /**
     * 添加用户预约
     * @param orderId
     * @param userId
     * @param registerId
     * @param doctorId
     * @param doctorName
     * @param orderNum
     * @param orderFee
     * @param registerTime
     * @param userName
     * @param userNo
     * @param userTelephone
     * @param sex
     * @param teamId
     * @param teamName
     * @return
     */
	public boolean addRegisterOrder(String orderId, String userId, String registerId,
			String doctorId, String doctorName, String orderNum, String orderFee,
			String registerTime, String userName, String userNo, String userTelephone, String sex,
			String teamId, String teamName)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into register_order_t(");
		sql.append("order_id,user_id,register_id,doctor_id,doctor_name,order_num,order_state,order_fee,register_time,user_name,user_no,user_telephone,sex,team_id,team_name,state,create_date)");
		sql.append("values (  '" + orderId + "','" + userId + "','" + registerId + "', '"
				+ doctorId + "','" + doctorName + "','" + orderNum + "','000', '" + orderFee
				+ "', '" + registerTime + "','" + userName + "', '" + userNo + "', '"
				+ userTelephone + "','" + sex + "','" + teamId + "', '" + teamName
				+ "','00A',sysdate)");

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

	/**
	 * 查询用户的挂号订单
	 * @param hospitalName
	 * @param teamName
	 * @param doctorName
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public List qryRegisterOrder(String hospitalId, String teamId, String doctorId,  String startTime, String endTime, String state) throws Exception 
	{
		StringBuffer query = new StringBuffer();
		query.append("select a.order_num,a.order_id, a.register_id, a.order_fee,a.doctor_name, a.register_time, a.create_date, a.team_name,a.user_name,a.user_telephone, b.hospital_name, ");
		query.append("decode(a.order_state, '000', '未处理', '00A', '已预约', '00X', '已作废') order_state ");
		query.append("from register_order_t a,  hospital_t b ");
		query.append("where a.state = '00A' and b.state = '00A'  and b.hospital_id = ? ");
		query.append("and to_date(substr(a.register_time, 0, 10), 'yyyy-mm-dd') between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd') ");
		ArrayList lstParam = new ArrayList();
		lstParam.add(hospitalId);
		lstParam.add(startTime);
		lstParam.add(endTime);
		if(ObjectCensor.isStrRegular(doctorId))
		{
			query.append("and a.doctor_id = ? ");
			lstParam.add(doctorId);
		}
		if(ObjectCensor.isStrRegular(teamId))
		{
			query.append("and a.team_id = ? ");
			lstParam.add(teamId);
		}
		if(ObjectCensor.isStrRegular(state))
		{
			query.append("and a.order_state = ? ");
			lstParam.add(state);
		}
		query.append("order by a.create_date ");
		return itzcQryCenter.executeSqlByMapListWithTrans(query.toString(), lstParam);
	}
	
	public List getDoctorById(String doctorId) throws QryException
	{
		String sql = "select a.*,b.team_name,c.name manager_name,c.password from doctor_t a,team_t b,hospital_manager_t c where a.state='00A' and a.team_id=b.team_id and a.doctor_id=? and c.state(+)='00A' and c.doctor_id(+)=a.doctor_id";
		ArrayList lstParam = new ArrayList();
		lstParam.add(doctorId);
		return itzcQryCenter.executeSqlByMapList(sql, lstParam);
	}
	
	public List getDoctorRegister(String doctorId) throws QryException
	{
		String sql = "select  * from doctor_register_t where state='00A' and doctor_id=?";
		ArrayList lstParam = new ArrayList();
		lstParam.add(doctorId);
		return itzcQryCenter.executeSqlByMapList(sql, lstParam);
	}
	
	public List getHospitalManager(String userName, String password) throws QryException
	{
		String sql = "select a.* from hospital_manager_t a where a.state='00A' and a.name=? and a.password=? ";
		ArrayList lstParam = new ArrayList();
		lstParam.add(userName);
		lstParam.add(password);
		return itzcQryCenter.executeSqlByMapList(sql, lstParam);
	}
}
