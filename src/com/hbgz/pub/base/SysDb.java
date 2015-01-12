package com.hbgz.pub.base;

import java.sql.Timestamp;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tools.pub.resolver.BeanFactoryHelper;

/**
 * 
 * <p>
 * <h2>SysDb�������ṩͨ��JDBCֱ�ӷ������ݿ�ķ�����</h2>
 * </p>
 *
 * <p>
 * SysDb��������Ҫ��Spring�����ļ�����������Դ���ԣ�dataSource�����ù�����
 * ��֧�ֶ����ݿ�Ŀǰֻ֧�ֶ�����Դ���ʡ�Ŀǰֻ֧��ϵͳʱ�䡢Oracle���кŵ�
 * ��ѯ������
 * </p>
 * 
 */
public class SysDb
{
	private static String SqlSysDate = "SELECT sysdate FROM dual";
	
	private DataSource dataSource = null;
	
	private JdbcTemplate itzcJdbcTemplate;
	
	public DataSource getDataSource() 
	{
		return dataSource;
	}
	
	public void setDataSource(DataSource dataSource) 
	{
	    this.dataSource = dataSource;
	    this.itzcJdbcTemplate = new JdbcTemplate(dataSource);
	}

	//Singleton
	public SysDb() {}
	
	/**����SQLȡ��һ�����ڣ���������ȡ��Oracleϵͳʱ��*/
	public Date getSysDate() 
	{
		BeanFactory ctx = BeanFactoryHelper.getBeanfactory();
		itzcJdbcTemplate = (JdbcTemplate)ctx.getBean("itzcJdbcTemplate");
	    Object o = itzcJdbcTemplate.queryForObject(SqlSysDate, Timestamp.class);
	    if (o != null && o instanceof Date) 
	    {
		return (Date) o;
	    }
	    return null;
	}
}
