package com.hbgz.pub.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 */
public final class SysDate 
{
    private static SysDate sysDate = new SysDate();
    
    public static SysDate getInstance() 
    {
    	return sysDate;
    }
    public static String getCurrentTime()
    {
    	Date date = Calendar.getInstance().getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	return sdf.format(date);
    }
    
    public static void main(String[] args) 
    {
    	String[] currentDate = SysDate.getCurrentTime().split("-");
    	for (int i = 0; i < currentDate.length; i++) 
    	{
    		System.out.println(currentDate[i]);
		}
	}
}
