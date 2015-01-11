package com.hbgz.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hbgz.model.PatientVisitT;
import com.hbgz.pub.exception.QryException;
import com.hbgz.pub.qry.QryCenter;
import com.hbgz.pub.util.ObjectCensor;
import com.hbgz.pub.util.StringUtil;

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
		sql.append("select t.doctor_id,t.hospital_id,t.name,decode(t.sex,'0','男','1','女')sex,t.telephone,to_char(t.create_date,'yyyy-MM-dd HH24:mm:ss')create_date,t.post,t.expert_flag,t.online_flag,t.introduce,t.skill,t.team_id,work_time,t.register_num,(select t.config_val  from hospital_config_t t where config_name='imgip' and config_type='IMGWEB' and t.state='00A')||t.photo_url photo_url,t.register_fee,t.work_address ");
		
		sql.append(" from doctor_t t,team_t a where a.team_id=t.team_id and t.state='00A' ");

		if (ObjectCensor.isStrRegular(expertType))
		{
			sql.append(" and a.expert_flag = ?");
			lstParam.add(expertType);
		}
		if (ObjectCensor.isStrRegular(onLineType))
		{
			sql.append(" and t.online_flag = ?");
			lstParam.add(onLineType);
		}
		if (ObjectCensor.isStrRegular(teamId))
		{
			sql.append(" and t.team_id=?");
			lstParam.add(teamId);
		}
		sql.append(" order by order_num ");
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
	}

	// 根据类型获取科室列表
	public List getTeamByType(String hospitalId, String expertType,String parentId) throws QryException
	{
		StringBuffer sql = new StringBuffer();
		ArrayList lstParam = new ArrayList();
		sql.append("select t.team_id,t.team_name,t.introduce,(select t.config_val  from hospital_config_t t where config_name='imgip' and config_type='IMGWEB' and t.state='00A')||img_url img_url from team_t t where (t.team_type='1' or t.team_type='2')  and  state='00A' ");

		if (ObjectCensor.isStrRegular(expertType))
		{
			sql.append(" and t.expert_flag= ? ");
			lstParam.add(expertType);
		}

		if (ObjectCensor.isStrRegular(parentId))
		{
			sql.append(" and t.parent_Id= ? ");
			lstParam.add(parentId);
		}
		if (ObjectCensor.isStrRegular(hospitalId))
		{
			sql.append(" and t.hospital_id=?  order by team_order ");
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
	public List getOrderByTeamId(String hospitalId,String teamId) throws QryException
	{
		if (!ObjectCensor.isStrRegular(teamId))
		{
			return null;
		}
		String sql = "select distinct (t.register_week),a.doctor_id,a.name ,a.post,b.team_name,a.introduce from doctor_register_t t,doctor_t a,team_t b where (b.team_type='1' or b.team_type='2') and t.state='00A' and a.doctor_id=t.doctor_id and b.team_id=t.team_id and t.team_id=? and b.hospital_Id=?";
		ArrayList lstParam = new ArrayList();
		lstParam.add(teamId);
		lstParam.add(hospitalId);
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
		String sql = "select t.*,a.team_name from doctor_register_t t,team_T a where (a.team_type='1' or a.team_type='2') and t.state='00A' and a.team_id=t.team_id and t.doctor_id=?";
		ArrayList lstParam = new ArrayList();
		lstParam.add(doctorId);
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
	}

	/**
     * 查询专家可预约号
     * @param doctorId
     * @return
     * @throws QryException
     */
	public List getOrderByWeekId(String weekId,String doctorId) throws QryException
	{
		if (!ObjectCensor.isStrRegular(weekId))
		{
			return null;
		}
		String sql = "select t.*,a.team_name from doctor_register_t t,team_T a where (a.team_type='1' or a.team_type='2') and t.state='00A' and a.team_id=t.team_id and t.doctor_id=? and t.register_week=?  order by day_type";
		ArrayList lstParam = new ArrayList();
		lstParam.add(doctorId);
		lstParam.add(weekId);
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
	 * 查询专家可预约号
	 * @param doctorId
	 * @return
	 * @throws QryException
	 */
	public List qryUserOrderByPhone(String userId,String dateStr) throws QryException
	{
		if (!ObjectCensor.isStrRegular(userId,dateStr))
		{
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select  team_id,user_id,doctor_id,register_id,replace(register_time,' ','')register_time from register_order_t ");
		sql.append(" where (pay_state = '100' or pay_state = '102' or pay_state = '103')and state = '00A'");
		sql.append(" and substr(register_time, 1, 10) =? and user_id=?");
		ArrayList lstParam = new ArrayList();
		lstParam.add(dateStr);
		lstParam.add(userId);
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
	

	public List qryTeamList(String hospitalId) throws Exception 
	{
		List sList = null;
		if(ObjectCensor.isStrRegular(hospitalId))
		{
			String sql = "select t.*, t.rowid from team_t t where hospital_id = ? and expert_flag = '1' ";
			ArrayList lstParam = new ArrayList();
			lstParam.add(hospitalId);
			sList = itzcQryCenter.executeSqlByMapListWithTrans(sql, lstParam);
		}
		return sList;
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
	public boolean addRegisterOrder(String hospitalId,String orderId, String userId, String registerId,
			String doctorId, String doctorName, String orderNum, String orderFee,
			String registerTime, String userName, String userNo, String userTelephone, String sex,
			String teamId, String teamName)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into register_order_t(");
		sql.append("hospital_id,order_id,user_id,register_id,doctor_id,doctor_name,order_num,order_state,order_fee,register_time,user_name,user_no,user_telephone,sex,team_id,team_name,state,create_date)");
		sql.append("values ("+ hospitalId+", '" + orderId + "','" + userId + "','" + registerId + "', '"
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
	public boolean addQHRegisterOrder(String hospitalId,String orderId, String userId, String registerId,
			String doctorId, String doctorName, String orderNum, String orderFee,
			String registerTime, String userName, String userNo, String userTelephone, String sex,
			String teamId, String teamName,String detailTime)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into register_order_t(");
		sql.append("hospital_id,order_id,user_id,register_id,doctor_id,doctor_name,order_num,order_state,order_fee,register_time,user_name,user_no,user_telephone,sex,team_id,team_name,state,create_date,detail_Time)");
		sql.append("values ("+ hospitalId+", '" + orderId + "','" + userId + "','" + registerId + "', '"
				+ doctorId + "','" + doctorName + "','" + orderNum + "','000', '" + orderFee
				+ "', '" + registerTime + "','" + userName + "', '" + userNo + "', '"
				+ userTelephone + "','" + sex + "','" + teamId + "', '" + teamName
				+ "','00A',sysdate,'"+detailTime+"')");

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
	
	/**
	 * 查询用户的挂号订单
	 * pay_state:100：未支付；101：已取消；102：已支付;103:退款中;104:已退款；
	 * @param hospitalName
	 * @param teamName
	 * @param doctorName
	 * @param userId
	 * @param startTime
	 * @param endTime  
	 * @return 
	 * @throws Exception
	 */
	public List qryRegisterOrderList(int pageNum, int pageSize, String hospitalId, String teamId, String startTime, String endTime, String state, String userName) throws Exception 
	{
		StringBuffer query = new StringBuffer();
		query.append("select * from (select c.*, ROWNUM RN from (");
		query.append("select a.order_num,a.order_id, a.register_id, a.order_fee,a.doctor_name, a.register_time, to_char(a.create_date, 'yyyy-MM-dd hh24:mi:ss') create_date, a.team_name,a.user_name,a.user_telephone, b.hospital_name, ");
		query.append("decode(a.order_state, '000', '未处理', '00A', '已预约', '00X', '已作废') order_state, ");
		query.append("decode(a.pay_state, '100', '未支付', '101', '已取消', '102', '已支付','103','申请退款','104','已退款') pay_state,pay_state pay_state_code ");
		query.append("from register_order_t a, hospital_t b ");
		query.append("where a.state = '00A' and b.state = '00A' and a.hospital_id = b.hospital_id  and b.hospital_id = ? ");
		ArrayList lstParam = new ArrayList();
		lstParam.add(hospitalId);
		if(ObjectCensor.isStrRegular(startTime, endTime))
		{
			query.append("and to_date(substr(a.register_time, 0, 10), 'yyyy-mm-dd') between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd') ");
			lstParam.add(startTime);
			lstParam.add(endTime);
		}
		if(ObjectCensor.isStrRegular(teamId))
		{
			query.append("and a.team_id = ? ");
			lstParam.add(teamId);
		}
		if(ObjectCensor.isStrRegular(state))
		{
			if("101".equals(hospitalId))
			{
				query.append("and a.order_state = ? ");
			}
			else if("102".equals(hospitalId))
			{
				query.append("and a.pay_state = ? ");
			}
			lstParam.add(state);
		}
		if(ObjectCensor.isStrRegular(userName))
		{
			query.append("and upper(a.user_name) like upper('%"+userName+"%') ");
		}
		query.append("order by a.create_date desc) c where ROWNUM <= ?) where RN >= ?");
		lstParam.add(pageNum * pageSize);
		lstParam.add((pageNum - 1) * pageSize + 1);
		return itzcQryCenter.executeSqlByMapListWithTrans(query.toString(), lstParam);
	}
	
	public int qryRegisterOrderCount(String hospitalId, String teamId, String startTime, String endTime, String state, String userName) throws Exception 
	{
		StringBuffer query = new StringBuffer();
		query.append("select a.order_num,a.order_id, a.register_id, a.order_fee,a.doctor_name, a.register_time, a.create_date, a.team_name,a.user_name,a.user_telephone, b.hospital_name, ");
		query.append("decode(a.order_state, '000', '未处理', '00A', '已预约', '00X', '已作废') order_state, ");
		query.append("decode(a.pay_state, '100', '未支付', '101', '已支付', '102', '已取消') pay_state ");
		query.append("from register_order_t a, hospital_t b ");
		query.append("where a.state = '00A' and b.state = '00A' and a.hospital_id = b.hospital_id  and b.hospital_id = ? ");
		ArrayList lstParam = new ArrayList();
		lstParam.add(hospitalId);
		if(ObjectCensor.isStrRegular(startTime, endTime))
		{
			query.append("and to_date(substr(a.register_time, 0, 10), 'yyyy-mm-dd') between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd') ");
			lstParam.add(startTime);
			lstParam.add(endTime);
		}
		if(ObjectCensor.isStrRegular(teamId))
		{
			query.append("and a.team_id = ? ");
			lstParam.add(teamId);
		}
		if(ObjectCensor.isStrRegular(state))
		{
			if("101".equals(hospitalId))
			{
				query.append("and a.order_state = ? ");
			}
			else if("102".equals(hospitalId))
			{
				query.append("and a.pay_state = ? ");
			}
			lstParam.add(state);
		}
		if(ObjectCensor.isStrRegular(userName))
		{
			query.append("and upper(a.user_name) like upper('%"+userName+"%') ");
		}
		return itzcQryCenter.getCount(query.toString(), lstParam);
	}
	
	public List getDoctorById(String doctorId) throws QryException
	{
		String sql = "select a.*,b.team_name,c.name manager_name,c.password,(select t.config_val  from hospital_config_t t where config_name='imgip' and config_type='IMGWEB' and t.state='00A')||photo_url img_url from doctor_t a,team_t b,hospital_manager_t c where (b.team_type='1' or b.team_type='2') and a.state='00A' and a.team_id=b.team_id and a.doctor_id=? and c.state(+)='00A' and c.doctor_id(+)=a.doctor_id order by a.order_num ";
		ArrayList lstParam = new ArrayList();
		lstParam.add(doctorId);
		return itzcQryCenter.executeSqlByMapList(sql, lstParam);
	}
	
	public List getDoctorRegister(String doctorId) throws QryException
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select a.*, decode(a.register_week, '一', '1', '二', '2', '三', '3', '四', '4', '五', '5') sort_time ");
		sql.append("from doctor_register_t a where a.state = '00A' and a.doctor_id = ?) order by sort_time, day_type ");
		ArrayList lstParam = new ArrayList();
		lstParam.add(doctorId);
		return itzcQryCenter.executeSqlByMapList(sql.toString(), lstParam);
	}
	
	public List getHospitalManager(String userName, String password) throws QryException
	{
		String sql = "select a.* from hospital_manager_t a where a.state='00A' and a.name=? and a.password=? ";
		ArrayList lstParam = new ArrayList();
		lstParam.add(userName);
		lstParam.add(password);
		return itzcQryCenter.executeSqlByMapList(sql, lstParam);
	}

	public List qryNewsList(int pageNum, int pageSize, String hospitalId, String startTime, String endTime, String newsType, String typeId, String state) throws Exception 
	{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM (SELECT A.*, ROWNUM ROWNUMBER FROM (");
		query.append("select a.news_id, a.news_title, a.state, decode(a.news_type, 'NEWS', '患教中心', 'BAIKE', '就医帮助') news_type, decode(a.state, '00A', '正常', '00X', '作废') state_val, to_char(a.create_date, 'yyyy-MM-dd hh24:mi:ss') create_date, b.hospital_name, ");
		query.append("(select config_val from hospital_config_t where state = '00A' and hospital_id = ? and config_type = 'HOSPITALNEWS' and config_name = a.news_type and config_id = a.type_id) type_id ");
		query.append("from hospital_news_t a, hospital_t b where a.hospital_id = b.hospital_id and b.state = '00A' and a.hospital_id = ? ");
		query.append("and a.create_date between to_date(?, 'yyyy-MM-dd hh24:mi:ss') and to_date(?, 'yyyy-MM-dd hh24:mi:ss') ");
		ArrayList lstParam = new ArrayList();
		lstParam.add(hospitalId);
		lstParam.add(hospitalId);
		lstParam.add(startTime);
		lstParam.add(endTime + " 23:59:59");
		if(ObjectCensor.isStrRegular(newsType))
		{
			query.append("and a.news_type = ? ");
			lstParam.add(newsType);
		}
		if(ObjectCensor.isStrRegular(typeId))
		{
			query.append("and a.type_id = ? ");
			lstParam.add(typeId);
		}
		if(ObjectCensor.isStrRegular(state))
		{
			query.append("and a.state = ? ");
			lstParam.add(state);
		}
		query.append("order by a.create_date desc) A WHERE ROWNUM <= ?)  WHERE ROWNUMBER >= ? ");
		lstParam.add(pageNum * pageSize);
		lstParam.add((pageNum - 1) * pageSize + 1);
		return itzcQryCenter.executeSqlByMapListWithTrans(query.toString(), lstParam);
	}
	
	public int qryNewsCount(String hospitalId, String startTime, String endTime, String newsType, String typeId, String state) throws Exception 
	{
		StringBuffer query = new StringBuffer();
		query.append("select a.news_id, a.news_title, a.state, decode(a.news_type, 'NEWS', '患教中心', 'BAIKE', '就医帮助') news_type, decode(a.state, '00A', '正常', '00X', '作废') state_val, to_char(a.create_date, 'yyyy-MM-dd hh24:mi:ss') create_date, b.hospital_name, ");
		query.append("(select config_val from hospital_config_t where state = '00A' and hospital_id = ? and config_type = 'HOSPITALNEWS' and config_name = a.news_type and config_id = a.type_id) type_id ");
		query.append("from hospital_news_t a, hospital_t b where a.hospital_id = b.hospital_id and b.state = '00A' and a.hospital_id = ? ");
		query.append("and a.create_date between to_date(?, 'yyyy-MM-dd hh24:mi:ss') and to_date(?, 'yyyy-MM-dd hh24:mi:ss') ");
		ArrayList lstParam = new ArrayList();
		lstParam.add(hospitalId);
		lstParam.add(hospitalId);
		lstParam.add(startTime);
		lstParam.add(endTime + " 23:59:59");
		if(ObjectCensor.isStrRegular(newsType))
		{
			query.append("and a.news_type = ? ");
			lstParam.add(newsType);
		}
		if(ObjectCensor.isStrRegular(typeId))
		{
			query.append("and a.type_id = ? ");
			lstParam.add(typeId);
		}
		if(ObjectCensor.isStrRegular(state))
		{
			query.append("and a.state = ? ");
			lstParam.add(state);
		}
		query.append("order by a.create_date desc");
		return itzcQryCenter.getCount(query.toString(), lstParam);
	}

	public List getNewsById(String newsId) throws Exception 
	{
		StringBuffer query = new StringBuffer();
		query.append("select hospital_id, news_id, news_title, news_content, news_images, state, news_type, type_id, to_char(eff_date, 'yyyy-MM-dd') eff_date, to_char(exp_date, 'yyyy-MM-dd') exp_date from hospital_news_t where news_id = ? ");
		ArrayList lstParam = new ArrayList();
		lstParam.add(newsId);
		return itzcQryCenter.executeSqlByMapListWithTrans(query.toString(), lstParam);
	}
	
	public boolean addNewsType(String hospitalId, String configId, String newsTypeId, String newsTypeName, String configType)
	{
		boolean flag = false;
		Connection conn = null;
		Statement stmt = null;
		try 
		{
			StringBuffer sql = new StringBuffer();
			sql.append("insert into hospital_config_t(hospital_id, config_id, config_name, config_val, config_type, state) ");
			sql.append("values('"+hospitalId+"', '"+configId+"', '"+newsTypeId+"', '"+newsTypeName+"', '"+configType+"', '00A') ");
			conn = itzcQryCenter.getDataSource().getConnection();
			stmt = conn.createStatement();
			int i = stmt.executeUpdate(sql.toString());
			if(i > 0)
			{
				flag = true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
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

	public List qryOnlineDortorList(int pageNum, int pageSize, String hospitalId, String teamId, String skill, String doctorName) throws Exception 
	{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM (SELECT A.*, ROWNUM ROWNUMBER FROM (");
		query.append("select a.*, b.team_name from doctor_t a, team_t b where a.state = '00A' and b.state = '00A' and a.team_id = b.team_id and a.hospital_id = ? and b.expert_flag = '1' ");
		ArrayList lstParam = new ArrayList();
		lstParam.add(hospitalId);
		if(ObjectCensor.isStrRegular(teamId))
		{
			query.append("and a.team_id = ? ");
			lstParam.add(teamId);
		}
		if(ObjectCensor.isStrRegular(skill))
		{
			query.append("and upper(a.skill) like upper('%"+skill+"%') ");
		}
		if(ObjectCensor.isStrRegular(doctorName))
		{
			query.append("and upper(a.name) like upper('%"+doctorName+"%') ");
		}
		query.append("order by a.order_num) A WHERE ROWNUM <= ?)  WHERE ROWNUMBER >= ? ");
		lstParam.add(pageNum * pageSize);
		lstParam.add((pageNum - 1) * pageSize + 1);
		return itzcQryCenter.executeSqlByMapListWithTrans(query.toString(), lstParam);
	}

	public int qryOnlineDortorCount(String hospitalId, String teamId, String skill, String doctorName) throws Exception 
	{
		StringBuffer query = new StringBuffer();
		query.append("select a.*, b.team_name from doctor_t a, team_t b where a.state = '00A' and b.state = '00A' and a.team_id = b.team_id and a.hospital_id = ? and b.expert_flag = '1' ");
		ArrayList lstParam = new ArrayList();
		lstParam.add(hospitalId);
		if(ObjectCensor.isStrRegular(teamId))
		{
			query.append("and a.team_id = ? ");
			lstParam.add(teamId);
		}
		if(ObjectCensor.isStrRegular(skill))
		{
			query.append("and upper(a.skill) like upper('%"+skill+"%') ");
		}
		if(ObjectCensor.isStrRegular(doctorName))
		{
			query.append("and upper(a.name) like upper('%"+doctorName+"%') ");
		}
		return itzcQryCenter.getCount(query.toString(), lstParam);
	}

	public boolean updateOnlineState(String doctorId, String operatorType) 
	{
		boolean flag = true;
		Connection conn = null;
		Statement stmt = null;
		try 
		{
			conn = itzcQryCenter.getDataSource().getConnection();
			stmt = conn.createStatement();
			
			JSONArray array = JSONArray.fromObject(doctorId);
			if(ObjectCensor.checkListIsNull(array))
			{
				String state = "0";/*在线*/
				if("offline".equals(operatorType))
				{
					state = "1";/*下线*/
				}
				for(int i = 0, len = array.size(); i < len; i++)
				{
					JSONObject obj = array.getJSONObject(i);
					doctorId = StringUtil.getJSONObjectKeyVal(obj, "doctorId");
					String teamId = StringUtil.getJSONObjectKeyVal(obj, "teamId");
					String sql = "update doctor_t set online_flag = '"+state+"' where doctor_id = '"+doctorId+"' and team_id = '"+teamId+"' ";
					stmt.addBatch(sql);
				}
			}
			
			int[] ints = stmt.executeBatch();
			for(int i = 0, len = ints.length; i < len; i++)
			{
				if(ints[i] < 0)
				{
					flag = false;
					break;
				}
			}
			
			if(flag)
			{
				conn.commit();
			}
			else
			{
				conn.rollback();
			}
		} 
		catch (SQLException e) 
		{
			flag = false;
			e.printStackTrace();
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
				flag = false;
				e.printStackTrace();
			}
		}
		return flag;
	}

	public boolean addWake(JSONObject obj, Long wakeId) 
	{
		boolean flag = false;
		Connection conn = null;
		Statement stmt = null;
		try 
		{
			conn = itzcQryCenter.getDataSource().getConnection();
			stmt = conn.createStatement();
			StringBuffer sql = new StringBuffer();
			
			String wakeType = StringUtil.getJSONObjectKeyVal(obj, "wakeType");
			String wakeValue = StringUtil.getJSONObjectKeyVal(obj, "wakeValue");
			String wakeContent = StringUtil.getJSONObjectKeyVal(obj, "wakeContent");
			String wakeDate = StringUtil.getJSONObjectKeyVal(obj, "wakeDate");
			String wakeName = StringUtil.getJSONObjectKeyVal(obj, "wakeName");
			sql.append("insert into wake_t(wake_id, wake_type, wake_value, wake_content, wake_date, create_date, state, wake_name) ");
			sql.append("values('"+wakeId+"', '"+wakeType+"', '"+wakeValue+"', '"+wakeContent+"', to_date('"+wakeDate+"', 'yyyy-MM-dd hh24:mi:ss'), sysdate, '00A', '"+wakeName+"')");
			int i = stmt.executeUpdate(sql.toString());
			if(i > 0)
			{
				flag = true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
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
			}
		}
		return flag;
	}

	public List qryWakeList(String wakeId) throws Exception 
	{
		String sql = "select * from wake_t where state = '00A' and wake_id = ? ";
		ArrayList lstParam = new ArrayList();
		lstParam.add(wakeId);
		return itzcQryCenter.executeSqlByMapListWithTrans(sql, lstParam);
	}
	
	public List qryPatientVisitList(String startTime, String endTime, String visitName, String visitType, String cardId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select visit_id, patient_id, card_id, visit_name, visit_type, state, to_char(create_date, 'yyyy-MM-dd hh24:mi:ss') create_date, ");
		sql.append("(select sex from hospital_user_t where state = '00A' and user_id = patient_id) sex ");
		sql.append("from patient_visit_t where state = '00A' and create_date between to_date(?, 'yyyy-MM-dd hh24:mi:ss') and to_date(?, 'yyyy-MM-dd hh24:mi:ss') ");
		ArrayList lstParam = new ArrayList();
		lstParam.add(startTime);
		lstParam.add(endTime + " 23:59:59");
		if(ObjectCensor.isStrRegular(visitName))
		{
			sql.append("and upper(visit_name) like upper('%"+visitName+"%') ");
		}
		if(ObjectCensor.isStrRegular(visitType))
		{
			sql.append("and visit_type = ? ");
			lstParam.add(visitType);
		}
		if(ObjectCensor.isStrRegular(cardId))
		{
			sql.append("and card_id = ? ");
			lstParam.add(cardId);
		}
		sql.append("order by create_date desc");
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
	}

	public List qryVisitDetail(String visitId) throws Exception 
	{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT code_flag_val, code_val_flag FROM ( ");
		sql.append("select (select distinct code_name from code_name_t where state = '00A' and code_type = A.VISIT_TYPE and code_flag = a.code_flag) code_flag_val, ");
		sql.append("decode((select code_val from code_name_t where state = '00A' AND code_flag = a.code_flag and code_type = A.VISIT_TYPE and rownum <= 1), ");
		sql.append("null, (a.code_val || (select code_val_flag from code_name_t where state = '00A' and code_type = A.VISIT_TYPE and code_flag = a.code_flag)), ");
		sql.append("(select code_val_flag from code_name_t where state = '00A' and code_type = A.VISIT_TYPE and code_flag = a.code_flag and code_val = a.code_val)) code_val_flag, ");
		sql.append("(select distinct SHOW_SEQ from code_name_t where state = '00A' and code_type = A.VISIT_TYPE and code_flag = a.code_flag and code_val = a.code_val) SHOW_SEQ ");
		sql.append("from patient_visit_detail_t a where state = '00A' and visit_id = ?) ORDER BY SHOW_SEQ ");
		ArrayList lstParam = new ArrayList();
		lstParam.add(visitId);
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
	}

	public List qryPatientVisitById(String visitId) throws Exception 
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select visit_name, visit_type, (select sex from hospital_user_t where state = '00A' and user_id = patient_id) sex ");
		sql.append("from patient_visit_t where state = '00A' and visit_id = ? ");
		ArrayList lstParam = new ArrayList();
		lstParam.add(visitId);
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
	}

	public List qryOnLineDoctorQuesList(int pageNum, int pageSize, String hospitalId, String teamId, String doctorName) throws Exception 
	{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM (SELECT A.*, ROWNUM ROWNUMBER FROM (");
		sql.append("select distinct a.doctor_id, a.name, a.order_num, a.post, b.team_name, ");
		sql.append("(select max(to_char(create_date, 'yyyy-MM-dd hh24:mi:ss')) from user_question_t where state = '00A' and doctor_id = a.doctor_id) create_date, ");
		sql.append("(select count(distinct question_id) from user_question_t where state = '00A' and doctor_id = a.doctor_id and record_type = 'ask') total_ques_num, ");
		sql.append("(select count(distinct question_id) from user_question_t where state = '00A' and doctor_id = a.doctor_id and (record_type = 'ans' or record_type = 'copy')) total_reply_num ");
		sql.append("from doctor_t a, team_t b where a.state = '00A' and a.online_flag = '0' and b.state = '00A' and a.hospital_id = b.hospital_id ");
		sql.append("and a.team_id = b.team_id and b.expert_flag = '1' and a.hospital_id = ? ");
		ArrayList lstParam = new ArrayList();
		lstParam.add(hospitalId);
		if(ObjectCensor.isStrRegular(teamId))
		{
			sql.append("and a.team_id = ? ");
			lstParam.add(teamId);
		}
		if(ObjectCensor.isStrRegular(doctorName))
		{
			sql.append("and upper(a.name) like upper('%"+doctorName+"%') ");
		}
		sql.append("order by a.order_num) A WHERE ROWNUM <= ?)  WHERE ROWNUMBER >= ? ");
		lstParam.add(pageNum * pageSize);
		lstParam.add((pageNum - 1) * pageSize + 1);
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), lstParam);
	}
	
	public int qryOnLineDoctorQuesCount(String hospitalId, String teamId, String doctorName) throws Exception 
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct a.doctor_id, a.name, a.order_num, a.post, b.team_name, ");
		sql.append("(select count(distinct question_id) from user_question_t where state = '00A' and doctor_id = a.doctor_id and record_type = 'ask') total_ques_num, ");
		sql.append("(select count(distinct question_id) from user_question_t where state = '00A' and doctor_id = a.doctor_id and (record_type = 'ans' or record_type = 'copy')) total_reply_num ");
		sql.append("from doctor_t a, team_t b where a.state = '00A' and a.online_flag = '0' and b.state = '00A' and a.hospital_id = b.hospital_id ");
		sql.append("and a.team_id = b.team_id and b.expert_flag = '1' and a.hospital_id = ? ");
		ArrayList lstParam = new ArrayList();
		lstParam.add(hospitalId);
		if(ObjectCensor.isStrRegular(teamId))
		{
			sql.append("and a.team_id = ? ");
			lstParam.add(teamId);
		}
		if(ObjectCensor.isStrRegular(doctorName))
		{
			sql.append("and upper(a.name) like upper('%"+doctorName+"%') ");
		}
		sql.append("order by a.order_num ");
		return itzcQryCenter.getCount(sql.toString(), lstParam);
	}

	public List qryUserList(int pageNum, int pageSize, String userName, String sex, String telephone) throws Exception 
	{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM (SELECT A.*, ROWNUM ROWNUMBER FROM (");
		query.append("select user_id, user_name, telephone, sex, decode(user_no, '无', user_no, substr(user_no, 0, 10) || '********') user_no, card_no, create_date, hospital_id ");
		query.append("from (select user_id, decode(user_name, null, '无', user_name) user_name, telephone, decode(sex, null, '无', sex) sex, decode(user_no, null, '无', user_no) user_no, ");
		query.append("decode(card_no, null, '无', card_no) card_no, to_char(create_date, 'yyyy-mm-dd') create_date, ");
		query.append("decode(hospital_id, null, '无', '101', '清华阳光益健康', '102', '掌上亚心') hospital_id from hospital_user_t where state = '00A' ");
		ArrayList lstParam = new ArrayList();
		if(ObjectCensor.isStrRegular(userName))
		{
			query.append("and upper(user_name) like upper('%"+userName+"%') ");
		}
		if(ObjectCensor.isStrRegular(sex))
		{
			query.append("and sex = ? ");
			lstParam.add(sex);
		}
		if(ObjectCensor.isStrRegular(telephone))
		{
			query.append("and upper(telephone) like upper('%"+telephone+"%') ");
		}
		query.append(") order by create_date desc) A WHERE ROWNUM <= ?)  WHERE ROWNUMBER >= ? ");
		lstParam.add(pageNum * pageSize);
		lstParam.add((pageNum - 1) * pageSize + 1);
		return itzcQryCenter.executeSqlByMapListWithTrans(query.toString(), lstParam);
	}

	public int qryUserCount(String userName, String sex, String telephone) throws Exception 
	{
		StringBuffer query = new StringBuffer();
		query.append("select * from hospital_user_t where state = '00A' ");
		ArrayList lstParam = new ArrayList();
		if(ObjectCensor.isStrRegular(userName))
		{
			query.append("and upper(user_name) like upper('%"+userName+"%') ");
		}
		if(ObjectCensor.isStrRegular(sex))
		{
			query.append("and sex = ? ");
			lstParam.add(sex);
		}
		if(ObjectCensor.isStrRegular(telephone))
		{
			query.append("and upper(telephone) like upper('%"+telephone+"%') ");
		}
		return itzcQryCenter.getCount(query.toString(), lstParam);
	}

	public List qryUserLoginActivityList(int pageNum, int pageSize, String startTime, String endTime, String hospitalId) throws Exception 
	{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM (SELECT A.*, ROWNUM ROWNUMBER FROM (");
		query.append("select decode(a.user_name, null, '无', a.user_name) user_name, b.telephone, decode(sex, null, '无', sex) sex, ");
		query.append("b.activity_num, decode(b.hospital_id, null, '无', '101', '清华阳光益健康', '102', '掌上亚心') hospital_id ");
		query.append("from (select count(telephone) activity_num, telephone, hospital_id from hospital_log_t where state = '00A' ");
		ArrayList lstParam = new ArrayList();
		if(ObjectCensor.isStrRegular(startTime, endTime))
		{
			query.append("and create_date between to_date(?, 'yyyy-mm-dd hh24:mi:ss') and to_date(?, 'yyyy-mm-dd hh24:mi:ss') ");
			lstParam.add(startTime);
			lstParam.add(endTime + " 23:59:59");
		}
		if(ObjectCensor.isStrRegular(hospitalId))
		{
			query.append("and hospital_id = ? ");
			lstParam.add(hospitalId);
		}
		query.append("group by telephone, hospital_id) b left join hospital_user_t a on a.telephone = b.telephone and a.state = '00A' ");
		query.append("order by b.activity_num desc) A WHERE ROWNUM <= ?)  WHERE ROWNUMBER >= ? ");
		lstParam.add(pageNum * pageSize);
		lstParam.add((pageNum - 1) * pageSize + 1);
		return itzcQryCenter.executeSqlByMapListWithTrans(query.toString(), lstParam);
	}

	public List qryUserLoginActivityCount(String startTime, String endTime, String hospitalId) throws Exception 
	{
		StringBuffer query = new StringBuffer();
		query.append("select count(*) count, sum(activity_num) total_num from (");
		query.append("select b.activity_num from  (select count(telephone) activity_num, telephone, hospital_id ");
		query.append("from hospital_log_t where state = '00A' ");
		ArrayList lstParam = new ArrayList();
		if(ObjectCensor.isStrRegular(startTime, endTime))
		{
			query.append("and create_date between to_date(?, 'yyyy-mm-dd hh24:mi:ss') and to_date(?, 'yyyy-mm-dd hh24:mi:ss') ");
			lstParam.add(startTime);
			lstParam.add(endTime + " 23:59:59");
		}
		if(ObjectCensor.isStrRegular(hospitalId))
		{
			query.append("and hospital_id = ? ");
			lstParam.add(hospitalId);
		}
		query.append("group by telephone, hospital_id) b left join hospital_user_t a on a.telephone = b.telephone and a.state = '00A') ");
		return itzcQryCenter.executeSqlByMapListWithTrans(query.toString(), lstParam);
	}
	
	public List  qryPatientVisits(String copyFlag) throws QryException
	{
		String sql = "select p.*,t.config_val  name from hospital_config_t t ,patient_visit_t p where t.config_name=p.visit_type and p.copy_flag=?";
		ArrayList lstParam = new ArrayList();
		lstParam.add(copyFlag);
		return itzcQryCenter.executeSqlByMapListWithTrans(sql, lstParam);
	}
}
