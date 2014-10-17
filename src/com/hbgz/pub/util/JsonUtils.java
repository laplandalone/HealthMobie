package com.hbgz.pub.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import net.sf.ezmorph.MorpherRegistry;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;

import com.hbgz.pub.exception.JsonException;

/**
 * json转java对象
 * @author RHQ
 *
 */
public class JsonUtils
{
	/**
	 * json转JavaBean
	 * @param jsonStr
	 * @param beanClass
	 * @return
	 * @throws JsonException
	 */
	public static Object toBean(String jsonStr, Class beanClass) throws JsonException
	{
		try
		{
			MorpherRegistry morpherRegistry = JSONUtils.getMorpherRegistry();
			jsonStr = StringUtil.filterEnter(jsonStr);
			JSONObject jsonPerson = JSONObject.fromObject(jsonStr); 
			JSONUtils.getMorpherRegistry().registerMorpher(new TimestampMorpher()); 
			return JSONObject.toBean(jsonPerson, beanClass); 
			
		}catch (Exception e)
		{
			throw new JsonException("jsonStr:"+jsonStr+" conver to "+beanClass);
		} 
	}

	/**
	 * json转list
	 * @param jsonStr
	 * @param beanClass
	 * @return
	 * @throws JsonException
	 */
	public static List toArray(String jsonStr,Class beanClass) throws JsonException
	{
		try
		{
			JSONUtils.getMorpherRegistry().registerMorpher(new TimestampMorpher()); 
			JSONArray jsonArray = JSONArray.fromObject(jsonStr);
			return (List) JSONArray.toCollection(jsonArray, beanClass);
		}catch (Exception e)
		{
			throw new JsonException("jsonStr:"+jsonStr+" conver to "+beanClass);
		}
	}
	
	public static JSONArray fromArray(List list)
	{
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new JsonValueProcessor(){
			public Object processArrayValue(Object value, JsonConfig config) 
			{
				return process(value);
			}

			public Object processObjectValue(String key, Object value, JsonConfig config) 
			{
				return process(value);
			}
			private Object process(Object value)
			{
				if(value == null)
				{
					return "";
				}
				else 
				{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					return sdf.format((Date)value);
				}
			}
		});
		return JSONArray.fromObject(list , config);
	}

	public static JSONArray fromArrayTimestamp(List list)
	{
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Timestamp.class, new JsonValueProcessor(){
			public Object processArrayValue(Object value, JsonConfig config) 
			{
				return process(value);
			}

			public Object processObjectValue(String key, Object value, JsonConfig config) 
			{
				return process(value);
			}
			private Object process(Object value)
			{
				if(value == null)
				{
				
					return "";
				}
				else 
				{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					
					return sdf.format(value);
				}
			}
		});
		return JSONArray.fromObject(list , config);
	}
	public static JSONObject fromObject(Object obj)
	{
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new JsonValueProcessor(){
			public Object processArrayValue(Object value, JsonConfig config) 
			{
				return process(value);
			}

			public Object processObjectValue(String key, Object value, JsonConfig config) 
			{
				return process(value);
			}
			private Object process(Object value)
			{
				if(value == null)
				{
					return "";
				}
				else 
				{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					return sdf.format((Date)value);
				}
			}
		});
		return JSONObject.fromObject(obj , config);
	}
	
	
}