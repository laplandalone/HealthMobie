/**
 * Created on 2005-06-16
 * crm项目组公用查询包
 * 
 * 
 */
package com.hbgz.pub.qry;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hbgz.pub.exception.QryException;
import com.tools.pub.utils.StringUtil;

/**
 * crm项目组公用查询类
 * 
 * @author hbgz
 */
public class QryCenter
{
	protected final Log log = LogFactory.getLog(getClass());

	private DataSource dataSource = null;

	private int maxRecCount = 20000; // 缺省最大返回20000条记录，也可以在spring配置文件配置。

	private boolean showSql = false; // 控制是否输出sql.

	private boolean showParam = false; // 控制是否输出参数值。

	/**
	 * @return Returns the dataSource.
	 */
	public DataSource getDataSource()
	{
		return dataSource;
	}

	public boolean isShowSql()
	{
		return showSql;
	}

	public int getMaxRecCount()
	{
		return maxRecCount;
	}

	public boolean isShowParam()
	{
		return showParam;
	}

	/**
	 * @param dataSource
	 *            The dataSource to set.
	 */
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	public void setMaxRecCount(int maxRecCount)
	{
		this.maxRecCount = maxRecCount;
	}

	public void setShowSql(boolean showSql)
	{
		this.showSql = showSql;
	}

	public void setShowParam(boolean showParam)
	{
		this.showParam = showParam;
	}

	/**
	 * sqlConvert 将传统带有：的sql转换为JDBC风格的sql（即带有?）的sql
	 * 
	 * @param Sql
	 *            String
	 * @param map
	 *            HashMap
	 * @param list
	 *            List
	 */
	public String sqlConvert(String Sql, HashMap map, List list)
	{
		String paramName;
		int index = 0;
		String localSql = Sql + " ";
		int at = localSql.indexOf(":");

		while (at >= 0)
		{
			paramName = StringUtils.substringBetween(localSql, ":", " ");
			localSql = StringUtils.replaceOnce(localSql,
					":" + paramName.trim(), "?");
			list.add(index++, map.get(paramName.trim()));
			at = localSql.indexOf(":");
		}
		return localSql;
	}

	/**
	 * 处理传统的带有:param的sql。
	 * 
	 * @param sql
	 * @param map
	 * @param trimFlag
	 * @return Returns the Result List of a query
	 * @throws QryException
	 */
	public List executeQuery(String sql, HashMap map, boolean trimFlag)
			throws QryException
	{
		ArrayList list = new ArrayList();

		sql = sqlConvert(sql, map, list);
		return executeSqlByMapList(sql, list);
	}

	public List executeQuery(String sql, HashMap map) throws QryException
	{
		return this.executeQuery(sql, map, false);
	}

	/**
	 * 分页查询
	 * 
	 * @param sql
	 * @param lstParam
	 * @param pageStart
	 * @param pageSize
	 * @return Returns the Result List of a PagedQuery
	 * @throws QryException
	 */
	public List executePagedQuery(String sql, ArrayList lstParam,
			int pageStart, int pageSize) throws QryException
	{
		return this
				.executePagedQuery(sql, lstParam, pageStart, pageSize, false);
	}

	public List executePagedQuery(String sql, ArrayList lstParam,
			int pageStart, int pageSize, boolean trimFlag) throws QryException
	{
		String pagedsql = "";

		pagedsql += " SELECT T_2.* FROM (SELECT T_1.*, ROWNUM ROWSEQ FROM ( ";
		pagedsql += sql + " ) T_1 WHERE ROWNUM < "
				+ String.valueOf(pageStart + pageSize);
		pagedsql += ") T_2 WHERE ROWSEQ >= " + String.valueOf(pageStart);

		return this.executeSqlByMapList(pagedsql, lstParam);
	}

	/**
	 * 程序调用ORACLE存储过程
	 * 
	 * @param procedureName
	 *            存储过程名
	 * @param lstParam
	 *            参数值设置
	 * @return Returns the Result List of a StoredProcedure
	 * @throws QryException
	 */

	public List executeProcedure(String procedureName, ArrayList lstParam)
			throws QryException
	{
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;

		try
		{
			conn = dataSource.getConnection();
			cstmt = conn.prepareCall("{" + procedureName + "}");

			for (int i = 0; i < lstParam.size(); i++)
			{
				// stmt.setObject(i, lstParam.get(i));
				if (lstParam.get(i).getClass().getName().indexOf("Integer") >= 0)
					cstmt.setInt(i + 1, ((Integer) lstParam.get(i)).intValue());
				else if (lstParam.get(i).getClass().getName().indexOf("Long") >= 0)
					cstmt.setLong(i + 1, ((Long) lstParam.get(i)).longValue());
				else if (lstParam.get(i).getClass().getName().indexOf("String") >= 0)
					cstmt.setString(i + 1, lstParam.get(i).toString());
				else
					cstmt.setObject(i + 1, lstParam.get(i));
			}
			cstmt.execute();

			// cstmt.registerOutParameter(1,Types.INTEGER);
			if (cstmt.getResultSet() != null)
			{
				rs = cstmt.getResultSet();

				return new RowSetDynaClass(rs, maxRecCount).getRows();
			} else
			{
				return null;
			}
		} catch (SQLException ex)
		{
			throw new QryException(ex.getMessage());
		} finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				} catch (SQLException ex)
				{
				}
			}
			if (cstmt != null)
			{
				try
				{
					cstmt.close();
				} catch (SQLException ex)
				{
					throw new QryException(ex.getMessage());
				}
			}
			if (conn != null)
			{
				try
				{
					conn.close();
				} catch (SQLException ex)
				{
					throw new QryException(ex.getMessage());
				}
			}
		}
	}

	/**
	 * 得到数据库表某个字段最大值加1
	 * 
	 * @param tableName
	 *            查询的表名
	 * @param seqColName
	 *            查询的列名
	 * @param qryCondition
	 *            查询的条件
	 * @return Returns next Sequence of a column
	 * @throws QryException
	 */

	public long getNextSeq(String tableName, String seqColName,
			String qryCondition) throws QryException
	{
		String sql = "SELECT NVL(MAX(" + seqColName + ")+1,1) maxseq FROM "
				+ tableName;

		if (qryCondition != "")
			sql = sql + " WHERE " + qryCondition;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try
		{
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next())
			{
				return rs.getLong(1);
			} else
			{
				return -1;
			}
		} catch (SQLException ex)
		{
			throw new QryException(ex.getMessage());
		} finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				} catch (SQLException ex)
				{
				}
			}
			if (stmt != null)
			{
				try
				{
					stmt.close();
				} catch (SQLException ex)
				{
					throw new QryException(ex.getMessage());
				}
			}
			if (conn != null)
			{
				try
				{
					conn.close();
				} catch (SQLException ex)
				{
					throw new QryException(ex.getMessage());
				}
			}
		}
	}

	/**
	 * 得到查询的记录数
	 * 
	 * @param sql
	 *            查询的sql
	 * @param lstParam
	 *            参数值
	 * @return 返回查询的记录数
	 * @throws QryException
	 */
	public int getCount(String sql, ArrayList lstParam) throws QryException
	{
		String countSql;
		countSql = "SELECT COUNT(*) FROM (" + sql + ")";

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try
		{
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(countSql);

			for (int i = 0; i < lstParam.size(); i++)
			{
				// stmt.setObject(i, lstParam.get(i));
				if (lstParam.get(i).getClass().getName().indexOf("Integer") >= 0)
					stmt.setInt(i + 1, ((Integer) lstParam.get(i)).intValue());
				else if (lstParam.get(i).getClass().getName().indexOf("Long") >= 0)
					stmt.setLong(i + 1, ((Long) lstParam.get(i)).longValue());
				else if (lstParam.get(i).getClass().getName().indexOf("String") >= 0)
					stmt.setString(i + 1, lstParam.get(i).toString());
				else
					stmt.setObject(i + 1, lstParam.get(i));
			}
			rs = stmt.executeQuery();
			if (rs.next())
			{
				return rs.getInt(1);
			} else
			{
				return -1;
			}
		} catch (SQLException ex)
		{
			throw new QryException(ex.getMessage());
		} finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				} catch (SQLException ex)
				{
				}
			}
			if (stmt != null)
			{
				try
				{
					stmt.close();
				} catch (SQLException ex)
				{
					throw new QryException(ex.getMessage());
				}
			}
			if (conn != null)
			{
				try
				{
					conn.close();
				} catch (SQLException ex)
				{
					throw new QryException(ex.getMessage());
				}
			}
		}
	}

	/**
	 * 根据传入的SQL语句返回一个List结果集
	 * 
	 * @param sql
	 *            查询的SQL
	 * @param lstParam
	 *            参数值
	 * @return List 返回一个HASHMAP的List对象
	 * @throws QryException
	 */

	public List executeSqlByMapList(String sql, ArrayList lstParam)
			throws QryException
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		final List recordList = new ArrayList();
		try
		{
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);

			for (int i = 0; i < lstParam.size(); i++)
			{

				if (lstParam.get(i).getClass().getName().indexOf("Integer") >= 0)
					stmt.setInt(i + 1, ((Integer) lstParam.get(i)).intValue());
				else if (lstParam.get(i).getClass().getName().indexOf("Long") >= 0)
					stmt.setLong(i + 1, ((Long) lstParam.get(i)).longValue());
				else if (lstParam.get(i).getClass().getName().indexOf("String") >= 0)
					stmt.setString(i + 1, lstParam.get(i).toString());
				else
					stmt.setObject(i + 1, lstParam.get(i));
			}
			// log.debug("-----------------------"+sql);
			rs = stmt.executeQuery();
			final ResultSetMetaData rsmd = rs.getMetaData();
			final int columnCount = rsmd.getColumnCount();
			while (rs.next())
			{
				final Map item = new HashMap(columnCount);
				for (int i = 1; i <= columnCount; i++)
				{
					final String columnName = rsmd.getColumnName(i);
					if (rs.getObject(i) instanceof java.sql.Date)
					{
						item.put(columnName.toLowerCase(), rs.getTimestamp(i));
						continue;
					}
					if (rs.getObject(i) instanceof java.sql.Timestamp)
					{
						item.put(columnName.toLowerCase(), rs.getTimestamp(i));
						continue;
					}
					if (rs.getObject(i) instanceof java.sql.Blob)
					{
						Blob blob = rs.getBlob(i);
						int blobLen = (int) blob.length();
						byte[] data = blob.getBytes(1, blobLen);
						item.put(columnName.toLowerCase(), new String(data));
						continue;
					}
					if (rs.getObject(i) instanceof java.sql.Clob)
					{
						Clob clob = rs.getClob(i);
						int clobLen = (int) clob.length();
						item.put(columnName.toLowerCase(),
								clob.getSubString(1, clobLen));
						continue;
					}
					item.put(columnName.toLowerCase(), rs.getObject(i));

				}
				recordList.add(item);
			}
		} catch (SQLException ex)
		{
			log.error(ex.getMessage());
			log.error("查询出错的SQL:" + sql);
			throw new QryException(ex.getMessage());
		} finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				} catch (SQLException ex)
				{
				}
			}
			if (stmt != null)
			{
				try
				{
					stmt.close();
				} catch (SQLException ex)
				{
				}
			}
			if (conn != null)
			{
				try
				{
					conn.close();
				} catch (SQLException ex)
				{
				}
			}
		}
		return recordList;
	}

	/**
	 * 根据传入的SQL语句返回一个List结果集
	 * 
	 * @param sql
	 *            查询的SQL
	 * @param lstParam
	 *            参数值
	 * @return List 返回一个HASHMAP的List对象
	 * @throws QryException
	 */

	public List executeSqlByMapListWithTrans(String sql, ArrayList lstParam)
			throws QryException
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		final List recordList = new ArrayList();
		try
		{
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);

			for (int i = 0; i < lstParam.size(); i++)
			{

				if (lstParam.get(i).getClass().getName().indexOf("Integer") >= 0)
					stmt.setInt(i + 1, ((Integer) lstParam.get(i)).intValue());
				else if (lstParam.get(i).getClass().getName().indexOf("Long") >= 0)
					stmt.setLong(i + 1, ((Long) lstParam.get(i)).longValue());
				else if (lstParam.get(i).getClass().getName().indexOf("String") >= 0)
					stmt.setString(i + 1, lstParam.get(i).toString());
				else
					stmt.setObject(i + 1, lstParam.get(i));
			}
			// log.debug("-----------------------"+sql);
			rs = stmt.executeQuery();
			final ResultSetMetaData rsmd = rs.getMetaData();
			final int columnCount = rsmd.getColumnCount();
			while (rs.next())
			{
				final Map item = new HashMap(columnCount);
				for (int i = 1; i <= columnCount; i++)
				{
					final String columnName = rsmd.getColumnName(i);
					if (rs.getObject(i) instanceof java.sql.Date)
					{
						item.put(StringUtil
								.getVoFldName_ByRstFldName(columnName
										.toLowerCase()), rs.getTimestamp(i));
						continue;
					}
					if (rs.getObject(i) instanceof java.sql.Timestamp)
					{
						item.put(StringUtil
								.getVoFldName_ByRstFldName(columnName
										.toLowerCase()), rs.getTimestamp(i));
						continue;
					}
					item.put(StringUtil.getVoFldName_ByRstFldName(columnName
							.toLowerCase()), rs.getObject(i));

				}
				recordList.add(item);
			}
		} catch (SQLException ex)
		{
			log.error(ex.getMessage());
			log.error("查询出错的SQL:" + sql);
			throw new QryException(ex.getMessage());
		} finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				} catch (SQLException ex)
				{
				}
			}
			if (stmt != null)
			{
				try
				{
					stmt.close();
				} catch (SQLException ex)
				{
				}
			}
			if (conn != null)
			{
				try
				{
					conn.close();
				} catch (SQLException ex)
				{
				}
			}
		}
		return recordList;
	}

	public List<String> executeSqlByJSONListWithTrans(String sql,
			ArrayList lstParam) throws Exception
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		final List<String> recordList = new ArrayList<String>();
		try
		{
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);

			for (int i = 0; i < lstParam.size(); i++)
			{

				if (lstParam.get(i).getClass().getName().indexOf("Integer") >= 0)
				{
					stmt.setInt(i + 1, ((Integer) lstParam.get(i)).intValue());
				} else if (lstParam.get(i).getClass().getName().indexOf("Long") >= 0)
				{
					stmt.setLong(i + 1, ((Long) lstParam.get(i)).longValue());
				} else if (lstParam.get(i).getClass().getName()
						.indexOf("String") >= 0)
				{
					stmt.setString(i + 1, lstParam.get(i).toString());
				} else
				{
					stmt.setObject(i + 1, lstParam.get(i));
				}
			}
			// log.debug("-----------------------"+sql);
			rs = stmt.executeQuery();
			final ResultSetMetaData rsmd = rs.getMetaData();
			final int columnCount = rsmd.getColumnCount();
			while (rs.next())
			{
				StringBuffer s = new StringBuffer("{");
				for (int i = 1; i <= columnCount; i++)
				{
					final String columnName = rsmd.getColumnName(i);
					if (rs.getObject(i) instanceof java.sql.Date)
					{
						s.append("\""
								+ StringUtil
										.getVoFldName_ByRstFldName(columnName
												.toLowerCase()) + "\":\""
								+ rs.getTimestamp(i) + "\",");
						continue;
					} else if (rs.getObject(i) instanceof java.sql.Timestamp)
					{
						s.append("\""
								+ StringUtil
										.getVoFldName_ByRstFldName(columnName
												.toLowerCase()) + "\":\""
								+ rs.getTimestamp(i) + "\",");
						continue;
					} else if (rs.getObject(i) instanceof java.sql.Blob)
					{
						Blob blob = rs.getBlob(i);
						int blobLen = (int) blob.length();
						byte[] data = blob.getBytes(1, blobLen);
						s.append("\""
								+ StringUtil
										.getVoFldName_ByRstFldName(columnName
												.toLowerCase()) + "\":\""
								+ new String(data) + "\",");
						continue;
					} else if (rs.getObject(i) instanceof java.sql.Clob)
					{
						Clob clob = rs.getClob(i);
						int clobLen = (int) clob.length();
						s.append("\""
								+ StringUtil
										.getVoFldName_ByRstFldName(columnName
												.toLowerCase()) + "\":\""
								+ clob.getSubString(1, clobLen) + "\",");
						continue;
					} else
					{
						s.append("\""
								+ StringUtil
										.getVoFldName_ByRstFldName(columnName
												.toLowerCase()) + "\":\""
								+ rs.getObject(i) + "\",");
					}
				}
				final String itemString = s.toString().substring(0,
						s.length() - 1)
						+ "}";
				recordList.add(itemString);
			}
		} catch (SQLException ex)
		{
			log.error(ex.getMessage());
			log.error("查询出错的SQL:" + sql);
			throw new QryException(ex.getMessage());
		} finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				} catch (SQLException ex)
				{
				}
			}
			if (stmt != null)
			{
				try
				{
					stmt.close();
				} catch (SQLException ex)
				{
				}
			}
			if (conn != null)
			{
				try
				{
					conn.close();
				} catch (SQLException ex)
				{
				}
			}
		}
		return recordList;
	}

	/**
	 * 根据传入的SQL[]语句返回一个List结果集
	 * 
	 * @param sql
	 *            查询的SQL
	 * @param lstParam
	 *            参数值
	 * @return List 返回一个HASHMAP的List对象
	 * @throws QryException
	 */

	public Map batchExecuteSqlByMapList(List lstsql, ArrayList lstParams,
			List lstKey) throws QryException
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Map resultMap = new HashMap();
		String sql = "";
		try
		{
			conn = dataSource.getConnection();
			for (int m = 0; m < lstsql.size(); m++)
			{

				List recordList = new ArrayList();
				sql = (String) lstsql.get(m);
				List lstParam = (List) lstParams.get(m);
				stmt = conn.prepareStatement(sql);
				String key = (String) lstKey.get(m);
				for (int i = 0; i < lstParam.size(); i++)
				{
					if (lstParam.get(i).getClass().getName().indexOf("Integer") >= 0)
						stmt.setInt(i + 1,
								((Integer) lstParam.get(i)).intValue());
					else if (lstParam.get(i).getClass().getName()
							.indexOf("Long") >= 0)
						stmt.setLong(i + 1,
								((Long) lstParam.get(i)).longValue());
					else if (lstParam.get(i).getClass().getName()
							.indexOf("String") >= 0)
						stmt.setString(i + 1, lstParam.get(i).toString());
					else
						stmt.setObject(i + 1, lstParam.get(i));
				}
				// log.debug("-----------------------"+sql);
				rs = stmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				while (rs.next())
				{
					final Map item = new HashMap(columnCount);
					for (int i = 1; i <= columnCount; i++)
					{
						final String columnName = rsmd.getColumnName(i);
						if (rs.getObject(i) instanceof java.sql.Date)
						{
							item.put(columnName.toLowerCase(),
									rs.getTimestamp(i));
							continue;
						}
						if (rs.getObject(i) instanceof java.sql.Timestamp)
						{
							item.put(columnName.toLowerCase(),
									rs.getTimestamp(i));
							continue;
						}
						item.put(columnName.toLowerCase(), rs.getObject(i));

					}
					recordList.add(item);
				}
				resultMap.put(key, recordList);
			}
		} catch (SQLException ex)
		{
			log.error(ex.getMessage());
			log.error("查询出错的SQL:" + sql);
			throw new QryException(ex.getMessage());
		} finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				} catch (SQLException ex)
				{
				}
			}
			if (stmt != null)
			{
				try
				{
					stmt.close();
				} catch (SQLException ex)
				{
					throw new QryException(ex.getMessage());
				}
			}
			if (conn != null)
			{
				try
				{
					conn.close();
				} catch (SQLException ex)
				{
					throw new QryException(ex.getMessage());
				}
			}
		}
		return resultMap;
	}

	/**
	 * 查询数据库表名称
	 * 
	 * @param sql
	 * @param lstParam
	 * @return string
	 * @throws QryException
	 */
	public String qryTableName(String sql, ArrayList lstParam)
			throws QryException
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String tableName = "";
		try
		{
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);

			for (int i = 0; i < lstParam.size(); i++)
			{

				if (lstParam.get(i).getClass().getName().indexOf("Integer") >= 0)
					stmt.setInt(i + 1, ((Integer) lstParam.get(i)).intValue());
				else if (lstParam.get(i).getClass().getName().indexOf("Long") >= 0)
					stmt.setLong(i + 1, ((Long) lstParam.get(i)).longValue());
				else if (lstParam.get(i).getClass().getName().indexOf("String") >= 0)
					stmt.setString(i + 1, lstParam.get(i).toString());
				else
					stmt.setObject(i + 1, lstParam.get(i));
			}
			rs = stmt.executeQuery();
			final ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next())
			{
				tableName = (String) rs.getObject(1);
			}

		} catch (SQLException ex)
		{
			throw new QryException(ex.getMessage());
		} finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				} catch (SQLException ex)
				{
				}
			}
			if (stmt != null)
			{
				try
				{
					stmt.close();
				} catch (SQLException ex)
				{
					throw new QryException(ex.getMessage());
				}
			}
			if (conn != null)
			{
				try
				{
					conn.close();
				} catch (SQLException ex)
				{
					throw new QryException(ex.getMessage());
				}
			}
		}
		return tableName;
	}

	/**
	 * 根据传入的SQL语句返回一个List结果集
	 * 
	 * @param sql
	 *            查询的SQL
	 * @param lstParam
	 *            参数值
	 * @return List 返回一个HASHMAP的List对象
	 * @throws QryException
	 */

	public List executeSqlByMapList(Connection conn, String sql,
			ArrayList lstParam) throws QryException
	{

		PreparedStatement stmt = null;
		ResultSet rs = null;
		final List recordList = new ArrayList();
		try
		{
			stmt = conn.prepareStatement(sql);

			for (int i = 0; i < lstParam.size(); i++)
			{

				if (lstParam.get(i).getClass().getName().indexOf("Integer") >= 0)
					stmt.setInt(i + 1, ((Integer) lstParam.get(i)).intValue());
				else if (lstParam.get(i).getClass().getName().indexOf("Long") >= 0)
					stmt.setLong(i + 1, ((Long) lstParam.get(i)).longValue());
				else if (lstParam.get(i).getClass().getName().indexOf("String") >= 0)
					stmt.setString(i + 1, lstParam.get(i).toString());
				else
					stmt.setObject(i + 1, lstParam.get(i));
			}
			// log.debug("-----------------------"+sql);
			rs = stmt.executeQuery();
			final ResultSetMetaData rsmd = rs.getMetaData();
			final int columnCount = rsmd.getColumnCount();
			while (rs.next())
			{
				final Map item = new HashMap(columnCount);
				for (int i = 1; i <= columnCount; i++)
				{
					final String columnName = rsmd.getColumnName(i);
					if (rs.getObject(i) instanceof java.sql.Date)
					{
						item.put(columnName.toLowerCase(), rs.getTimestamp(i));
						continue;
					}
					if (rs.getObject(i) instanceof java.sql.Timestamp)
					{
						item.put(columnName.toLowerCase(), rs.getTimestamp(i));
						continue;
					}

					item.put(columnName.toLowerCase(), rs.getObject(i));
				}
				recordList.add(item);
			}
		} catch (SQLException ex)
		{
			log.error(ex.getMessage());
			log.error("查询出错的SQL:" + sql);
			throw new QryException(ex.getMessage());
		} finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				} catch (SQLException ex)
				{
				}
			}
			if (stmt != null)
			{
				try
				{
					stmt.close();
				} catch (SQLException ex)
				{
				}
			}
		}
		return recordList;
	}

	/**
	 * 根据传入的SQL[]语句返回一个List结果集
	 * 
	 * @param sql
	 *            查询的SQL
	 * @param lstParam
	 *            参数值
	 * @return List 返回一个HASHMAP的List对象
	 * @throws QryException
	 */

	public Map batchExecuteSqlByMapListSplit(List lstsql, ArrayList lstParams,
			List lstKey) throws QryException
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Map resultMap = new HashMap();
		String sql = "";
		try
		{
			conn = dataSource.getConnection();
			for (int m = 0; m < lstsql.size(); m++)
			{

				List recordList = new ArrayList();
				sql = (String) lstsql.get(m);
				List lstParam = (List) lstParams.get(m);
				stmt = conn.prepareStatement(sql);
				String key = (String) lstKey.get(m);
				for (int i = 0; i < lstParam.size(); i++)
				{
					if (lstParam.get(i).getClass().getName().indexOf("Integer") >= 0)
						stmt.setInt(i + 1,
								((Integer) lstParam.get(i)).intValue());
					else if (lstParam.get(i).getClass().getName()
							.indexOf("Long") >= 0)
						stmt.setLong(i + 1,
								((Long) lstParam.get(i)).longValue());
					else if (lstParam.get(i).getClass().getName()
							.indexOf("String") >= 0)
						stmt.setString(i + 1, lstParam.get(i).toString());
					else
						stmt.setObject(i + 1, lstParam.get(i));
				}
				// log.debug("-----------------------"+sql);
				rs = stmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				while (rs.next())
				{
					final Map item = new HashMap(columnCount);
					for (int i = 1; i <= columnCount; i++)
					{
						final String columnName = rsmd.getColumnName(i);
						if (rs.getObject(i) instanceof java.sql.Date)
						{
							item.put(columnName.toLowerCase(),
									rs.getTimestamp(i));
							continue;
						}
						if (rs.getObject(i) instanceof java.sql.Timestamp)
						{
							item.put(columnName.toLowerCase(),
									rs.getTimestamp(i));
							continue;
						}
						item.put(columnName.toLowerCase(), rs.getObject(i));

					}
					recordList.add(item);
				}
				resultMap.put(key, recordList);
			}
		} catch (SQLException ex)
		{
			log.error(ex.getMessage());
			log.error("查询出错的SQL:" + sql);
			throw new QryException(ex.getMessage());
		} finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				} catch (SQLException ex)
				{
				}
			}
			if (stmt != null)
			{
				try
				{
					stmt.close();
				} catch (SQLException ex)
				{
					throw new QryException(ex.getMessage());
				}
			}
			if (conn != null)
			{
				try
				{
					conn.close();
				} catch (SQLException ex)
				{
					throw new QryException(ex.getMessage());
				}
			}
		}
		return resultMap;
	}

	/**
	 * 根据传入的SQL[]语句返回一个List结果集
	 * 
	 * @param sql
	 *            查询的SQL
	 * @param lstParam
	 *            参数值
	 * @return List 返回一个HASHMAP的List对象
	 * @throws QryException
	 */

	public Map batchExecuteSqlByMapListNew(List lstsql, ArrayList lstParams,
			List lstKey) throws Exception
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Map resultMap = new HashMap();
		String sql = "";
		try
		{
			conn = dataSource.getConnection();
			for (int m = 0; m < lstsql.size(); m++)
			{

				List recordList = new ArrayList();
				sql = (String) lstsql.get(m);
				List lstParam = (List) lstParams.get(m);
				stmt = conn.prepareStatement(sql);
				String key = (String) lstKey.get(m);
				for (int i = 0; i < lstParam.size(); i++)
				{
					if (lstParam.get(i).getClass().getName().indexOf("Integer") >= 0)
						stmt.setInt(i + 1,
								((Integer) lstParam.get(i)).intValue());
					else if (lstParam.get(i).getClass().getName()
							.indexOf("Long") >= 0)
						stmt.setLong(i + 1,
								((Long) lstParam.get(i)).longValue());
					else if (lstParam.get(i).getClass().getName()
							.indexOf("String") >= 0)
						stmt.setString(i + 1, lstParam.get(i).toString());
					else
						stmt.setObject(i + 1, lstParam.get(i));
				}
				// log.debug("-----------------------"+sql);
				rs = stmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				while (rs.next())
				{
					final Map item = new HashMap(columnCount);
					for (int i = 1; i <= columnCount; i++)
					{
						final String columnName = rsmd.getColumnName(i);
						if (rs.getObject(i) instanceof java.sql.Date)
						{
							item.put(columnName.toLowerCase(),
									rs.getTimestamp(i));
							continue;
						}
						if (rs.getObject(i) instanceof java.sql.Timestamp)
						{
							item.put(columnName.toLowerCase(),
									rs.getTimestamp(i));
							continue;
						}
						item.put(columnName.toLowerCase(), rs.getObject(i));

					}
					recordList.add(item);
				}
				resultMap.put(key, recordList);
			}
		} catch (SQLException ex)
		{
			log.error(ex.getMessage());
			log.error("查询出错的SQL:" + sql);
			throw new QryException(ex.getMessage());
		} finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				} catch (SQLException ex)
				{
				}
			}
			if (stmt != null)
			{
				try
				{
					stmt.close();
				} catch (SQLException ex)
				{
					throw new QryException(ex.getMessage());
				}
			}
			if (conn != null)
			{
				try
				{
					conn.close();
				} catch (SQLException ex)
				{
					throw new QryException(ex.getMessage());
				}
			}
		}
		return resultMap;
	}
}