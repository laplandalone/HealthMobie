package com.hbgz.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hbgz.pub.exception.QryException;
import com.hbgz.pub.qry.QryCenter;
import com.tools.pub.utils.ObjectCensor;

@Repository
public class UserDao
{
	@Autowired
	QryCenter itzcQryCenter;

	public List getUser(String userName, String password) throws QryException
	{
		String sql = "select a.*,b.store_name from user_t a,medical_store_t b where a.state='00A' and a.user_name=? and a.password=? and a.role_id='0'  and a.store_id=b.store_id";
		ArrayList lstParam = new ArrayList();
		lstParam.add(userName);
		lstParam.add(password);
		return itzcQryCenter.executeSqlByMapList(sql, lstParam);
	}
	
	public List getUserWeb(String userName, String password) throws QryException
	{
		String sql = "select a.*,b.store_name from user_t a,medical_store_t b where a.state='00A' and a.user_name=? and a.password=? and a.role_id='1'  and a.store_id=b.store_id(+)";
		ArrayList lstParam = new ArrayList();
		lstParam.add(userName);
		lstParam.add(password);
		return itzcQryCenter.executeSqlByMapList(sql, lstParam);
	}
	
	public List getUserList() throws QryException
	{
		String sql = "select a.*,b.store_name from user_t a,medical_store_t b where a.state='00A' and a.store_id=b.store_id(+)";
		return itzcQryCenter.executeSqlByMapList(sql, new ArrayList());
	}

	public List qryFileImgUrls() throws QryException
	{
		String sql="select * from user_up_file_t t , file_img_url_t a where t.file_id=a.file_id and a.state='00A' and t.record_state='00A'";
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), new ArrayList());
	}
	public List qryUserFile(String medName , String startTime , String endTime) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select  a.store_name,t.order_id,t.register_date,t.img_url,t.user_id,t.file_id,t.medical_name,t.remark,decode(t.state,'0','未处理','1','同意','2','不同意') state,comments,medical_code,ver_user,(select user_name from user_t where user_id = t.ver_user and state='00A') ver_user_name from user_up_file_t t,medical_store_t a,user_t b where t.user_id=b.user_id and b.store_id=a.store_id ");
		if(ObjectCensor.isStrRegular(startTime , endTime))
		{
			sql.append(" and register_date between to_date('"+startTime+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endTime+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ");
		}
		if(ObjectCensor.isStrRegular(medName))
		{
			sql.append(" and medical_name like '%"+medName+"%' ");
		}
		return itzcQryCenter.executeSqlByMapListWithTrans(sql.toString(), new ArrayList());
	}
	public List qryUserFileForMobile(int pageNum, int pageSize, String userId, String startTime, String endTime) throws Exception
	{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM (SELECT A.*, ROWNUM ROWNUMBER FROM (");
		query.append( "select t.order_id,t.register_date,t.img_url,t.user_id,t.file_id,t.medical_name,t.remark,decode(t.state,'0','未处理','1','同意','2','不同意') state,comments,ver_user,(select user_name from user_t where user_id = t.ver_user and state='00A')ver_user_name from user_up_file_t t where user_id=?");
		
		ArrayList listArray = new ArrayList();
		listArray.add(userId);
		if(ObjectCensor.isStrRegular(startTime, endTime))
		{
			query.append("and register_date between to_date(?, 'yyyy-MM-dd hh24:mi:ss') and to_date(?, 'yyyy-MM-dd hh24:mi:ss') ");
			listArray.add(startTime);
			endTime += " 23:59:59";
			listArray.add(endTime);
		}
		query.append(") A WHERE ROWNUM <= ?)  WHERE ROWNUMBER >= ? ");
		listArray.add(pageNum * pageSize);
		listArray.add((pageNum - 1) * pageSize + 1);
		System.out.println(query.toString());
		return itzcQryCenter.executeSqlByMapListWithTrans(query.toString(), listArray);
	}
	
	
	public boolean addUserUploadFile(String fileId,String userId, String imgUrl, String remark, String state, String medicalName,String order_id,String medicalCode)
	{
		String sql = "INSERT INTO user_up_file_t (file_id,user_id,img_url,remark,state,medical_name,register_date,order_id,medical_code,record_state) values ('"+fileId+"','" + userId + "','" + imgUrl + "','" + remark + "','" + state + "','" + medicalName + "',sysdate,'"+order_id+"','"+medicalCode+"','00A')";
		Connection conn = null;
		Statement stmt = null;
		boolean flag=true;
		try
		{
			conn = itzcQryCenter.getDataSource().getConnection();
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (Exception e)
		{
				e.printStackTrace();
				flag=false;
		} finally
		{
			try
			{
				stmt.close();
				conn.close();
				
			} catch (Exception e)
			{
				e.printStackTrace();
				flag=false;
			}
			return flag;
		}
	}
	

	public boolean addImgUrl(String fileId,String imgId, String imgUrl)
	{
		String sql = "INSERT INTO file_img_url_t (file_id,img_id,img_url,state,create_date) values ('"+fileId+"','" + imgId + "','" + imgUrl + "','00A',sysdate)";
		Connection conn = null;
		Statement stmt = null;
		boolean flag=true;
		try
		{
			conn = itzcQryCenter.getDataSource().getConnection();
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (Exception e)
		{
				e.printStackTrace();
				flag=false;
		} finally
		{
			try
			{
				stmt.close();
				conn.close();
				
			} catch (Exception e)
			{
				e.printStackTrace();
				flag=false;
			}
			return flag;
		}
	}
	
	public boolean updateUserUploadFile(String fileId,  String state, String comments, String verUserId)
	{
		String sql = "UPDATE user_up_file_t SET state='"+state+"',comments='"+comments+"',ver_user='"+verUserId+"' WHERE File_ID="+fileId;
		Connection conn = null;
		Statement stmt = null;
		boolean flag=true;
		try
		{
			conn = itzcQryCenter.getDataSource().getConnection();
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (Exception e)
		{
			e.printStackTrace();
			flag=false;
		} finally
		{
			try
			{
				stmt.close();
				conn.close();
			} catch (Exception e)
			{
				flag=false;
			}
			return flag;
		}
	}
	
	public List getStoreList() throws QryException
	{
		String sql = "select * from medical_store_t where state = '00A'";
		return itzcQryCenter.executeSqlByMapListWithTrans(sql, new ArrayList());
	}
	
	public Map<String,String> getUserById(String userId) throws QryException
	{
		String sql = "select a.*,b.store_name from user_t a,medical_store_t b where a.state='00A' and a.store_id=b.store_id(+) and a.state='00A' and user_id=? ";
		ArrayList lstParam = new ArrayList();
		lstParam.add(userId);
		List<Map<String,String>> list = itzcQryCenter.executeSqlByMapListWithTrans(sql, lstParam);
		if(list.isEmpty())
		{
			return new HashMap<String,String>();
		}
		else
		{
			return list.get(0);
		}
	}
	
	public boolean updateUser(String userId , String userName , String password , String roleId , String storeId)
	{
		String sql = "UPDATE user_t SET password='"+password+"',role_id='"+roleId+"',store_id="+storeId+" WHERE user_id="+userId;
		Connection conn = null;
		Statement stmt = null;
		boolean flag=true;
		try
		{
			conn = itzcQryCenter.getDataSource().getConnection();
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (Exception e)
		{
			e.printStackTrace();
			flag=false;
		} finally
		{
			try
			{
				stmt.close();
				conn.close();
			} catch (Exception e)
			{
				flag=false;
			}
		}
		return flag;
	}
	
	public boolean addUser(String userId , String userName , String password , String roleId , String storeId)
	{
		String sql = "insert into user_t(user_id,user_name,password,role_id,state,create_date,store_id) values("+userId+",'"+userName+"','"+password+"','"+roleId+"','00A',sysdate,"+storeId+")";
		Connection conn = null;
		Statement stmt = null;
		boolean flag=true;
		try
		{
			conn = itzcQryCenter.getDataSource().getConnection();
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (Exception e)
		{
			e.printStackTrace();
			flag=false;
		} finally
		{
			try
			{
				stmt.close();
				conn.close();
			} catch (Exception e)
			{
				flag=false;
			}
		}
		return flag;
	}
	
	public boolean delUser(String userId)
	{
		String sql = "delete from user_t where user_id="+userId;
		Connection conn = null;
		Statement stmt = null;
		boolean flag=true;
		try
		{
			conn = itzcQryCenter.getDataSource().getConnection();
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (Exception e)
		{
			e.printStackTrace();
			flag=false;
		} finally
		{
			try
			{
				stmt.close();
				conn.close();
			} catch (Exception e)
			{
				flag=false;
			}
		}
		return flag;
	}
	
	public Map<String,String> getStoreById(String storeId) throws QryException
	{
		String sql = "select * from medical_store_t where state = '00A' and store_id = ? ";
		ArrayList lstParam = new ArrayList();
		lstParam.add(storeId);
		List<Map<String,String>> list = itzcQryCenter.executeSqlByMapListWithTrans(sql, lstParam);
		if(list.isEmpty())
		{
			return new HashMap<String,String>();
		}
		else
		{
			return list.get(0);
		}
	}
	
	public boolean updateStore(String storeId , String storename , String remark)
	{
		String sql = "UPDATE medical_store_t SET remark='"+remark+"' WHERE store_id="+storeId;
		Connection conn = null;
		Statement stmt = null;
		boolean flag=true;
		try
		{
			conn = itzcQryCenter.getDataSource().getConnection();
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (Exception e)
		{
			e.printStackTrace();
			flag=false;
		} finally
		{
			try
			{
				stmt.close();
				conn.close();
			} catch (Exception e)
			{
				flag=false;
			}
		}
		return flag;
	}
	
	public boolean addStore(String storeId , String storename , String remark)
	{
		String sql = "insert into medical_store_t(store_id,store_name,remark,state,create_date) values("+storeId+",'"+storename+"','"+remark+"','00A',sysdate)";
		Connection conn = null;
		Statement stmt = null;
		boolean flag=true;
		try
		{
			conn = itzcQryCenter.getDataSource().getConnection();
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (Exception e)
		{
			e.printStackTrace();
			flag=false;
		} finally
		{
			try
			{
				stmt.close();
				conn.close();
			} catch (Exception e)
			{
				flag=false;
			}
		}
		return flag;
	}
	
	public boolean delStore(String storeId)
	{
		String sql = "delete from medical_store_t where store_id="+storeId;
		Connection conn = null;
		Statement stmt = null;
		boolean flag=true;
		try
		{
			conn = itzcQryCenter.getDataSource().getConnection();
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (Exception e)
		{
			e.printStackTrace();
			flag=false;
		} finally
		{
			try
			{
				stmt.close();
				conn.close();
			} catch (Exception e)
			{
				flag=false;
			}
		}
		return flag;
	}
	
	
}
