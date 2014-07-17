package com.hbgz.pub.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import net.sf.ezmorph.MorphException;
import net.sf.ezmorph.object.AbstractObjectMorpher;

/**
* 将Json中时间属性转换为Timestamp数据格式适配器
* @author RHQ
* 
*/
public class TimestampMorpher extends AbstractObjectMorpher {

   /*字符串转换时间默认格式*/	
   private final static String DEFAULT_FORMAT="yyyy-MM-dd HH:mm:ss";	
   private String format;

  
   public TimestampMorpher(String format)
   {
	   this.format = format;
   }
   
   public TimestampMorpher()
   {
	   this.format =DEFAULT_FORMAT;
   }

   @Override
   public Object morph(Object value)
   {
	try 
	{
	    String strValue;
	    if (value == null)
		return null;
	    if (Timestamp.class.isAssignableFrom(value.getClass()))
		return (Timestamp) value;
	    if (!supports(value.getClass()))
		throw new MorphException(value.getClass() + " is not supported");
	    strValue = (String) value;
	    return new Timestamp(new SimpleDateFormat(this.format).parse(strValue).getTime());
	} catch (ParseException e) 
	{
	    e.printStackTrace();
	}
	return null;
   }

   @Override
   public Class<?> morphsTo() 
   {
	   return Timestamp.class;
   }

}