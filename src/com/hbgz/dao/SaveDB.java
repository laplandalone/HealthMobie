package com.hbgz.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hbgz.pub.exception.QryException;
import com.hbgz.pub.exception.TransferException;

public class SaveDB {

	Log log = LogFactory.getLog(SaveDB.class);
	private Connection conn;
	/**
	 * 拼装SQL语句及数据持久化
	 * 
	 * @param jt
	 * @param strTableName
	 * @param mapResult
	 */
	public void insertRecord( String strTableName, Map mapResult)
			throws SQLException, QryException, TransferException {
		// TODO Auto-generated method stub
		log.debug("tableName:"+strTableName);
		log.debug("tableValue:"+mapResult);
		
		
		if (mapResult.size() == 0)
			return;
		String sqlInsert = "";
		String sqlInsertHead = "insert into " + strTableName;
		String sqlInsertColumns = "(";
		String sqlInsertValues = "values (";

		Object[] objectArray = new Object[mapResult.size()];

		int objectArrayIndex = 0;
		boolean isFirst = true;

		for (Iterator iterMap = mapResult.keySet().iterator(); iterMap
				.hasNext();) {
			String key = (String) iterMap.next();
			if (!isFirst) {
				sqlInsertColumns += " , ";
				sqlInsertValues += " , ";
			}
			sqlInsertColumns += key;
			sqlInsertValues += "?";
			objectArray[objectArrayIndex++] = mapResult.get(key);
			isFirst = false;
		}
		sqlInsertColumns += " ) ";
		sqlInsertValues += " ) ";
		sqlInsert = sqlInsertHead + sqlInsertColumns + sqlInsertValues;
		log.debug(sqlInsert);
		PreparedStatement pstmt = null;
		int n = 0;
		try {
			pstmt = conn.prepareStatement(sqlInsert);
			for (n = 0; n < objectArray.length; n++) {
				if (objectArray[n] == null) {
					pstmt.setObject(n + 1, objectArray[n]);
				} else if (objectArray[n].getClass().getName()
						.indexOf("String") >= 0) {
					pstmt.setString(n + 1, (String) objectArray[n]);
				} else if (objectArray[n].getClass().getName().indexOf(
						"Integer") >= 0) {
					pstmt.setInt(n + 1, ((Integer) objectArray[n]).intValue());
				} else if (objectArray[n].getClass().getName().indexOf("Long") >= 0) {
					pstmt.setLong(n + 1, ((Long) objectArray[n]).longValue());
				} else if (objectArray[n].getClass().getName().indexOf(
						"Timestamp") >= 0) {
					pstmt.setTimestamp(n + 1, (Timestamp) (objectArray[n]));
				} else
					pstmt.setObject(n + 1, objectArray[n]);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// 订单入库时出现异常回滚
			conn.rollback();
			throw new QryException(e.getMessage(), sqlInsert);
		} catch (Exception e) {
			// 订单入库时出现异常回滚
			conn.rollback();
			throw new TransferException(strTableName + objectArray[n] + "转换异常");
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e1) {
				conn.rollback();
				throw new QryException(e1.getMessage(), sqlInsert);
			}
		}
	}
	
	/**
	 * 拼装SQL语句及数据持久化
	 * 支持分布式处理
	 * @param jt
	 * @param strTableName
	 * @param mapResult
	 */
	public void addRecord(Connection conn, String strTableName, Map mapResult)
			throws SQLException, QryException, TransferException {
		// TODO Auto-generated method stub
		log.debug("tableName:" + strTableName);
		log.debug("tableValue:" + mapResult);
		if (mapResult.size() == 0)
			return;
		String sqlInsert = "";
		String sqlInsertHead = "insert into " + strTableName;
		String sqlInsertColumns = "(";
		String sqlInsertValues = "values (";
		Object[] objectArray = new Object[mapResult.size()];
		int objectArrayIndex = 0;
		boolean isFirst = true;
		for (Iterator iterMap = mapResult.keySet().iterator(); iterMap
				.hasNext();) {
			String key = (String) iterMap.next();
			if (!isFirst) {
				sqlInsertColumns += " , ";
				sqlInsertValues += " , ";
			}
			sqlInsertColumns += key;
			sqlInsertValues += "?";
			objectArray[objectArrayIndex++] = mapResult.get(key);
			isFirst = false;
		}
		sqlInsertColumns += " ) ";
		sqlInsertValues += " ) ";
		sqlInsert = sqlInsertHead + sqlInsertColumns + sqlInsertValues;
		log.debug(sqlInsert);
		PreparedStatement pstmt = null;
		int n = 0;
		try {
			pstmt = conn.prepareStatement(sqlInsert);
			for (n = 0; n < objectArray.length; n++) {
				if (objectArray[n] == null) {
					pstmt.setObject(n + 1, objectArray[n]);
				} else if (objectArray[n].getClass().getName()
						.indexOf("String") >= 0) {
					pstmt.setString(n + 1, (String) objectArray[n]);
				} else if (objectArray[n].getClass().getName().indexOf(
						"Integer") >= 0) {
					pstmt.setInt(n + 1, ((Integer) objectArray[n]).intValue());
				} else if (objectArray[n].getClass().getName().indexOf("Long") >= 0) {
					pstmt.setLong(n + 1, ((Long) objectArray[n]).longValue());
				} else if (objectArray[n].getClass().getName().indexOf(
						"Timestamp") >= 0) {
					pstmt.setTimestamp(n + 1, (Timestamp) (objectArray[n]));
				} else
					pstmt.setObject(n + 1, objectArray[n]);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// 订单入库时出现异常回滚
			throw new QryException(e.getMessage(), sqlInsert);
		} catch (Exception e) {
			// 订单入库时出现异常回滚
			throw new TransferException(strTableName + objectArray[n] + "转换异常");
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e1) {
				throw new QryException(e1.getMessage(), sqlInsert);
			}
		}
	}
	public String insertCustIndentTache(Connection conn, long agreementId,
			long currentTache, long currentState, long resultTypeId,
			long resultId, String resultDesc, long staffId, long oldAgreementId)
			throws QryException, SQLException {
		String ret = "";
		String rtnMsg = "";
		String nextAction = "";
		CallableStatement stmt = null;
		try {
			stmt = conn
					.prepareCall("{call p_wf_engine(?,?,?,?,?,?,?,?,?,?,?)}");
			stmt.setLong(1, agreementId);
			stmt.setLong(2, currentTache);
			stmt.setLong(3, currentState);
			stmt.setLong(4, resultTypeId);
			stmt.setLong(5, resultId);
			stmt.setString(6, resultDesc);
			stmt.setLong(7, staffId);
			stmt.setLong(8, oldAgreementId);
			stmt.registerOutParameter(9, Types.NUMERIC);
			stmt.registerOutParameter(10, Types.VARCHAR);
			stmt.registerOutParameter(11, Types.VARCHAR);
			stmt.executeUpdate();
			ret = "" + stmt.getLong(9);
			rtnMsg = stmt.getString(10);
			nextAction = stmt.getString(11);
			log.debug("====================TACHE sucess=====================map---");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new QryException("CUST_INDENT_TACHE_T表里的存储过程调用异常");
		} finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new QryException("CUST_INDENT_TACHE_T表里的存储过程调用异常");
				}
		}
		return ret;
	}
}
