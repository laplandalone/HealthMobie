package com.hbgz.pub.base;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.hbgz.pub.util.ObjectCensor;

/**
 * 
 */
public final class SysDate 
{
    private static SysDate sysDate = new SysDate();
	
    private static SysDb sysDb = new SysDb();
    
    //从数据库取得时间
    private static Date getDbDate() 
    {
    	return sysDb.getSysDate();
    }
    
    public static SysDate getInstance() 
    {
    	return sysDate;
    }
	
    public void setSysDb(SysDb sysDb) 
    {
    	SysDate.sysDb = sysDb;
    }
    /**
     * 获取系统日期时间
     * 
     * @throws ParseException
     */
    public static Timestamp getSysDate() 
    {
		Date date = Calendar.getInstance().getTime();
	//	Date date = getDbDate();
		return getFormatSimpleDate(new Timestamp(date.getTime()));
    }

    private static Timestamp getFormatSimpleDate(Date date) 
    {
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat df = new SimpleDateFormat(format);
		String dateT = df.format(date);
		try 
		{
		    return new Timestamp(df.parse(dateT).getTime());
		} 
		catch (ParseException e)
		{
		    e.printStackTrace();
		}
		return null;
    }

    public static Timestamp getSysDate(String dateStr) 
    {
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat df = new SimpleDateFormat(format);
		try 
		{
		    return new Timestamp(df.parse(dateStr).getTime());
		}
		catch (ParseException e) 
		{
		    e.printStackTrace();
		}
		return null;
    }
    
    public static String getDate()
    {
		Date date = Calendar.getInstance().getTime();
		return getFormatDate(new Timestamp(date.getTime()));
    }
    
    public static String getCurrentTime()
    {
    	Date date = Calendar.getInstance().getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return sdf.format(date);
    }

    public static String getFormatDate(Date date) 
    {
		String format = "yy-MM-dd";
		SimpleDateFormat df = new SimpleDateFormat(format);
		String dateT = df.format(date);
		return dateT;
    }
    
    /**
     * @author tk
     * @return
     */
    public static String getSysDateStr()
    {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	Date date = getDbDate();
    	return sdf.format(date);
    }
    
	public static String getPreciseDate()
	{
		Date date = Calendar.getInstance().getTime();
		return getFormatDateZ(new Timestamp(date.getTime()));
	}

	public static String getFormatDateZ(Date date)
	{
		String format = "yyyyMMddHHmmss000";
		SimpleDateFormat df = new SimpleDateFormat(format);
		String dateT = df.format(date);
		return dateT;
	}
	
	/**
	 * @author : tiankang
	 * @param format
	 * @return
	 * 获取指定格式的时间
	 */
	public static String getDateByFormat(String format)
	{
		if(ObjectCensor.isStrRegular(format))
		{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}
		else
		{
			return null;
		}
	}
}
