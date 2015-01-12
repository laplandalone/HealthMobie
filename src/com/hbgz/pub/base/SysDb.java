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
 * <h2>SysDb工具类提供通过JDBC直接访问数据库的方法。</h2>
 * </p>
 *
 * <p>
 * SysDb工具类需要在Spring配置文件中配置数据源属性（dataSource），该工具类
 * 不支持多数据库目前只支持多数据源访问。目前只支持系统时间、Oracle序列号的
 * 查询方法。
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
	
	/**根据SQL取得一个日期，可以用来取得Oracle系统时间*/
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
