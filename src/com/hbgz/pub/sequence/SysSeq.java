package com.hbgz.pub.sequence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.hbgz.model.SysSequence;

/**
 * 
 * <p>
 * <h2>SysDb工具类提供通过JDBC直接访问数据库的方法。</h2>
 * </p>
 * 
 * <p>
 * SysDb工具类需要在Spring配置文件中配置数据源属性（dataSource），该工具类
 * 不支持多数据库目前只支持多数据源访问。目前只支持系统时间、Oracle序列号的 查询方法。
 * </p>
 * 
 */

@Component
public class SysSeq 
{
	@Autowired
	private JdbcTemplate itzcJdbcTemplate;

	private Log log = LogFactory.getLog(SysSeq.class);

	private static String SqlSeq1 = "SELECT ";

	private static String SqlSeq2 = ".nextval FROM dual";

	private static String SqlSysDate = "SELECT systimestamp FROM dual";

	private static String SqlSequenceQuery = "SELECT seq_name, seq_id, step_value, min_value, max_value FROM  SYS_SEQUENCE_T where ";

	private static String SqlSequenceUpdate = "UPDATE "
			+ "SYS_SEQUENCE_T SET seq_id = ? " + "WHERE seq_name = ? ";

	/** 根据SQL取得一个日期，可以用来取得Oracle系统时间 */
	public Date getSysDate() 
	{
		Object o = itzcJdbcTemplate.queryForObject(SqlSysDate, Timestamp.class);
		if (o != null && o instanceof Date) 
		{
			return (Date) o;
		}
		return null;
	}

	/** 根据序列号名称取得一个值，用来取得Oracle序列号 */
	public Long getSequenceItzc(String seqName) 
	{
		return getSequenceOracle(seqName, itzcJdbcTemplate);
	}

	/** 根据序列号名称取得一个值，用来取得Oracle序列号 */
	public Long getSequenceOracle(String seqName, JdbcTemplate jdbcTemplate) 
	{
		if (seqName == null || seqName.length() <= 0) 
		{
			return null;
		}
		if (jdbcTemplate == null) 
		{
			return null;
		}
		String sql = SqlSeq1 + seqName + SqlSeq2;
		Object o = jdbcTemplate.queryForObject(sql, Long.class);
		if (o != null && o instanceof Long) 
		{
			return (Long) o;
		}
		return null;
	}

	/**
	 * 在事务中存取序列号 取得List(SysSequence)列表，从ioid_sequence_t表取数据
	 * 
	 * @throws Exception
	 */
	public SysSequence getSequence(String seqName) throws Exception 
	{
		if (seqName.length() <= 0) 
		{
			throw new Exception("系统无法取得序列号。");
		}
		Object[] params = new Object[] {};
		StringBuffer sequenceSql = new StringBuffer(SysSeq.SqlSequenceQuery + " seq_name='" + seqName + "' for update");
		List<SysSequence> seqList = itzcJdbcTemplate.query(sequenceSql.toString(), params, new SequenceRowMapper());

		if (seqList == null || seqList.size() == 0) 
		{
			throw new Exception("系统无法取得序列名称：" + seqName + "的序列.");
		}

		SysSequence po = seqList.get(0);
		long nextValue = po.getEndValue();
		Object[] values = new Object[] { new Long(nextValue), seqName };
		updateSequence(values);
		return po;
	}

	// 用于查询序列号表
	private class SequenceRowMapper implements RowMapper 
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			SysSequence po = new SysSequence();

			String className = rs.getString(1);
			Long seqId = new Long(rs.getLong(2));
			Integer stepValue = new Integer(rs.getInt(3));
			Long minValue = new Long(rs.getLong(4));
			Long maxValue = new Long(rs.getLong(5));

			int step = Math.max(1, stepValue.intValue());
			long startValue = seqId + 1;
			long nextValue = seqId + step;

			// 记录返回的序列号新值
			po.setStartValue(startValue);
			po.setEndValue(nextValue);

			po.setId(seqId);
			po.setClassName(className);
			po.setSeqId(seqId);
			po.setStepValue(stepValue);
			po.setMinValue(minValue);
			po.setMaxValue(maxValue);

			return po;
		}
	}

	// 用于更新序列号表
	private int updateSequence(Object[] params)
	{
		return itzcJdbcTemplate.update(SysSeq.SqlSequenceUpdate, params);
	}
}
