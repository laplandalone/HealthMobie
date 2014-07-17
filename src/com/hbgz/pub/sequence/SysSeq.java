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
 * <h2>SysDb�������ṩͨ��JDBCֱ�ӷ������ݿ�ķ�����</h2>
 * </p>
 * 
 * <p>
 * SysDb��������Ҫ��Spring�����ļ�����������Դ���ԣ�dataSource�����ù�����
 * ��֧�ֶ����ݿ�Ŀǰֻ֧�ֶ�����Դ���ʡ�Ŀǰֻ֧��ϵͳʱ�䡢Oracle���кŵ� ��ѯ������
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

	/** ����SQLȡ��һ�����ڣ���������ȡ��Oracleϵͳʱ�� */
	public Date getSysDate() 
	{
		Object o = itzcJdbcTemplate.queryForObject(SqlSysDate, Timestamp.class);
		if (o != null && o instanceof Date) 
		{
			return (Date) o;
		}
		return null;
	}

	/** �������к�����ȡ��һ��ֵ������ȡ��Oracle���к� */
	public Long getSequenceItzc(String seqName) 
	{
		return getSequenceOracle(seqName, itzcJdbcTemplate);
	}

	/** �������к�����ȡ��һ��ֵ������ȡ��Oracle���к� */
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
	 * �������д�ȡ���к� ȡ��List(SysSequence)�б���ioid_sequence_t��ȡ����
	 * 
	 * @throws Exception
	 */
	public SysSequence getSequence(String seqName) throws Exception 
	{
		if (seqName.length() <= 0) 
		{
			throw new Exception("ϵͳ�޷�ȡ�����кš�");
		}
		Object[] params = new Object[] {};
		StringBuffer sequenceSql = new StringBuffer(SysSeq.SqlSequenceQuery + " seq_name='" + seqName + "' for update");
		List<SysSequence> seqList = itzcJdbcTemplate.query(sequenceSql.toString(), params, new SequenceRowMapper());

		if (seqList == null || seqList.size() == 0) 
		{
			throw new Exception("ϵͳ�޷�ȡ���������ƣ�" + seqName + "������.");
		}

		SysSequence po = seqList.get(0);
		long nextValue = po.getEndValue();
		Object[] values = new Object[] { new Long(nextValue), seqName };
		updateSequence(values);
		return po;
	}

	// ���ڲ�ѯ���кű�
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

			// ��¼���ص����к���ֵ
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

	// ���ڸ������кű�
	private int updateSequence(Object[] params)
	{
		return itzcJdbcTemplate.update(SysSeq.SqlSequenceUpdate, params);
	}
}
