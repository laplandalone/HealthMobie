package com.hbgz.pub.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Category;

import com.hbgz.pub.preferences.SystemGlobal;

/**
 * <p>
 * Title: 字符串处理工具类
 * </p>
 * 
 */

public class StringUtil
{
	private static Category cat;

	public StringUtil()
	{
	}

	public static final String getPropertyName(String name)
	{
		//System.out.println(SystemGlobal.get("string.utils." + name)+"");
		return "abcdefghijklmnopqrstuvwxyz";
	}

	public static String getRands(int amount, String chr)
	{
		int digit = -1;
		StringBuffer sb = new StringBuffer("");
		if ((chr != null) && (!(chr.equals(""))))
		{
			char[] c = chr.toCharArray();
			for (int i = 0; i < chr.length(); ++i)
			{
				char ch = c[i];
				String s = getPropertyName(new String(new char[]
				{ ch }));
				if (s != null)
				{
					sb.append(s);
				}
			}
		}
		if (sb.length() == 0)
			sb.append(getPropertyName("s"));
		digit = sb.length();
		StringBuffer rands = new StringBuffer("");
		for (int i = 0; i < amount; ++i)
		{
			int rand = (int) (Math.random() * digit);
			rands.append(sb.charAt(rand));
		}
		return rands.toString();
	}

	public static boolean isEmpty(Object value)
	{
		return ((value == null) || ("".equals(value.toString().trim())));
	}

	/**
	 * firstCharLowerCase
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String firstCharLowerCase(String s)
	{
		if (s == null || "".equals(s))
			return ("");
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}

	/**
	 * firstCharUpperCase
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String firstCharUpperCase(String s)
	{
		if (s == null || "".equals(s))
			return ("");
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public static String[] splitStringByTokenCount(String value, String token, int arrSize)
	{
		Map map = new HashMap();

		if (arrSize == 0)
			return null;

		int cnt = StringUtils.countMatches(value, token);
		if (!token.equals(value.substring(value.length() - token.length())))
			cnt++;

		int len = cnt % arrSize == 0 ? (cnt / arrSize) : (cnt / arrSize) + 1;
		String[] rets = new String[len];

		if (len == 1)
			rets[0] = value;
		else
		{
			int count = 0;
			int ssIdx = 0;
			int iBeginIdx = 0;
			int idx = 0;
			while ((idx = value.indexOf(token, idx)) != -1)
			{
				count++;
				if (count % arrSize == 0)
				{
					rets[ssIdx] = value.substring(iBeginIdx, idx);
					ssIdx++;
					iBeginIdx = idx + token.length();
				}
				idx += token.length();
			}
			if (ssIdx + 1 == len)
			{
				rets[ssIdx] = value.substring(iBeginIdx);
			}

		}
		return rets;
	}

	public static String trimTokenAndNextFirstCharUpperCase(String value, String token)
	{
		if (value == null || "".equals(value))
			return value;

		String[] ss = StringUtils.split(value, token);

		ss[0] = ss[0].toLowerCase();
		for (int i = 1; i < ss.length; i++)
			ss[i] = firstCharUpperCase(ss[i].toLowerCase());

		return org.apache.commons.lang.StringUtils.join(ss);
	}

	/**
	 * 将JAVABEAN的属性转换为该BEAN对应的数据库表的字段
	 * 
	 * @param voName
	 * @return String
	 */
	public static String getConverColName(String property)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < property.length(); i++)
		{ // 遍历property如果有大写字母则将大写字母转换为_加小写
			char cur = property.charAt(i);
			if (Character.isUpperCase(cur))
			{
				sb.append("_");
				sb.append(Character.toLowerCase(cur));
			} else
			{
				sb.append(cur);
			}
		}
		return sb.toString();
	}

	// **************add by ojw*********************
	/**
	 * 根据字符串传成date
	 * 
	 * @param String
	 * @return Date
	 */
	public static Date strToDate(String s)
	{

		Date mydate = new Date();

		if (s.length() == 10)
		{
			mydate.setYear(Integer.parseInt(s.substring(0, 4)));
			mydate.setMonth(Integer.parseInt(s.substring(5, 7)));
			mydate.setDate(Integer.parseInt(s.substring(8, 10)));
		}
		if (s.length() == 8)
		{
			mydate.setYear(Integer.parseInt(s.substring(0, 4)));
			mydate.setMonth(Integer.parseInt(s.substring(4, 6)));
			mydate.setDate(Integer.parseInt(s.substring(6, 8)));
		}
		return mydate;
	}

	/**
	 * 根据Vo的字段名字得到dto的名字 !!!!我们规定页面上dto的名字为数据库中的表/字段名,必须全部为小写
	 * 
	 * @param voName
	 * @return
	 */
	public static String getDtoName_ByVo(String voName)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < voName.length(); i++)
		{ // 遍历voName如果有大写字母则将大写字母转换为_加小写
			char cur = voName.charAt(i);
			if (Character.isUpperCase(cur))
			{
				sb.append("_");
				sb.append(Character.toLowerCase(cur));
			} else
			{
				sb.append(cur);
			}
		}
		return sb.toString();
	}

	/****************
	 * 根据VO字段名得到记录中字段的名字
	 * 
	 * @param voName
	 * @return
	 */
	public static String getRstFieldName_ByVoFldName(String voName)
	{
		StringBuffer sb = new StringBuffer();

		sb.append(Character.toUpperCase(voName.charAt(0)));
		for (int i = 1; i < voName.length(); i++)
		{ // 遍历voName如果有大写字母则将大写字母转换为_加小写
			char cur = voName.charAt(i);
			if (Character.isUpperCase(cur) && i > 0)
			{
				sb.append("_");
				sb.append(Character.toUpperCase(cur));
			} else
			{
				sb.append(cur);
			}
		}
		return sb.toString();
	}

	/*****************************
	 * 根据model的字段名得到记录中的字段名称
	 * 
	 * @param voName
	 * @return
	 */
	public static String getVoFldName_ByRstFldName(String voName)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(Character.toLowerCase(voName.charAt(0)));
		for (int i = 1; i < voName.length(); i++)
		{ // 遍历voName如果有大写字母则将大写字母转换为_加小写
			char cur = voName.charAt(i);
			char interval = '_';
			if (cur == interval)
			{
				if (i + 1 < voName.length())
				{
					sb.append(Character.toUpperCase(voName.charAt(i + 1)));
					i++;
				}
			} else
			{
				sb.append(cur);
			}
		}
		return sb.toString();
	}

	/**
	 * 将行对象转换成值对象
	 * 
	 * @param row
	 * @param type值对象类型
	 * @return
	 * @throws java.lang.Exception
	 *             这里的异常一般在DAO中处理，因为DAO调用 这个方法进行Row和ValueObject的转换
	 */
	public static Object map_to_vo(Map row, Class type)
	{
		try
		{

			Object vo = type.newInstance(); // 创建一个值对象
			Field[] fields = type.getDeclaredFields(); // 得到值对象中所有字段

			for (int i = 0; i < fields.length; i++)
			{
				String name = fields[i].getName(); // 得到JavaBean的字段名
				String nameInRow = getDtoName_ByVo(name);// 在此进行值对象名称到行对象名称的转换
				Object value = row.get(nameInRow); // 得到从数据库中取出的与字段名对应的值
				if (value == null)
					continue;
				String methodName = "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1); // 得到setXXXX方法名
				Class argClass = null;

				if (value instanceof Class)
				{
					argClass = (Class) value;
					value = null;

				} else
				{
					// argClass = value.getClass();
					argClass = fields[i].getType();

				}
				String datatype = fields[i].getType().getName();
				try
				{// 对于hibernate影射的值对象主键字段,例如没有setCustid()方法
					if (datatype.equals("java.lang.Long"))
					{
						Method method = type.getMethod(methodName, new Class[]
						{ argClass }); // 得到set方法
						method.invoke(vo, new Long[]
						{ new Long(value.toString()) });// 调用setXXXX方法
					}
					if (datatype.equals("java.lang.Double"))
					{
						Method method = type.getMethod(methodName, new Class[]
						{ argClass }); // 得到set方法
						method.invoke(vo, new Double[]
						{ new Double(value.toString()) });// 调用setXXXX方法
					}
					if (datatype.equals("java.lang.Float"))
					{
						Method method = type.getMethod(methodName, new Class[]
						{ argClass }); // 得到set方法
						method.invoke(vo, new Float[]
						{ new Float(value.toString()) });// 调用setXXXX方法
					}
					if (datatype.equals("java.util.Date"))
					{
						Method method = type.getMethod(methodName, new Class[]
						{ argClass }); // 得到set方法
						Date mydate = strToDate(value.toString());
						method.invoke(vo, new java.util.Date[]
						{ mydate });// 调用setXXXX方法
					}
					if (datatype.equals("java.lang.String"))
					{
						Method method = type.getMethod(methodName, new Class[]
						{ argClass }); // 得到set方法
						method.invoke(vo, new Object[]
						{ value });// 调用setXXXX方法
					}

				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			return vo;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将行对象转换成值对象
	 * 
	 * @param row
	 * @param type值对象类型
	 * @return
	 * @throws java.lang.Exception
	 *             这里的异常一般在DAO中处理，因为DAO调用 这个方法进行Row和ValueObject的转换
	 */
	public static Object row_to_vo(Map row, Class type)
	{
		try
		{

			Object vo = type.newInstance(); // 创建一个值对象

			Field[] fields = type.getDeclaredFields(); // 得到值对象中所有字段

			for (int i = 0; i < fields.length; i++)
			{
				String name = fields[i].getName(); // 得到JavaBean的字段名
				String nameInRow = getRstFieldName_ByVoFldName(name);// 在此进行值对象名称到行对象名称的转换
				Object value = row.get(nameInRow); // 得到从数据库中取出的与字段名对应的值
				if (value == null)
					continue;
				String methodName = "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1); // 得到setXXXX方法名
				Class argClass = null;

				if (value instanceof Class)
				{
					argClass = (Class) value;
					value = null;

				} else
				{
					// argClass = value.getClass();
					argClass = fields[i].getType();

				}
				String datatype = fields[i].getType().getName();
				try
				{// 对于hibernate影射的值对象主键字段,例如没有setCustid()方法
					if (datatype.equals("java.lang.Long"))
					{
						Method method = type.getMethod(methodName, new Class[]
						{ argClass }); // 得到set方法
						method.invoke(vo, new Long[]
						{ new Long(value.toString()) });// 调用setXXXX方法
					}
					if (datatype.equals("java.lang.Double"))
					{
						Method method = type.getMethod(methodName, new Class[]
						{ argClass }); // 得到set方法
						method.invoke(vo, new Double[]
						{ new Double(value.toString()) });// 调用setXXXX方法
					}
					if (datatype.equals("java.lang.Float"))
					{
						Method method = type.getMethod(methodName, new Class[]
						{ argClass }); // 得到set方法
						method.invoke(vo, new Float[]
						{ new Float(value.toString()) });// 调用setXXXX方法
					}
					if (datatype.equals("java.util.Date"))
					{
						Method method = type.getMethod(methodName, new Class[]
						{ argClass }); // 得到set方法
						Date mydate = strToDate(value.toString());
						method.invoke(vo, new java.util.Date[]
						{ mydate });// 调用setXXXX方法
					}
					if (datatype.equals("java.lang.String"))
					{
						Method method = type.getMethod(methodName, new Class[]
						{ argClass }); // 得到set方法
						method.invoke(vo, new Object[]
						{ value });// 调用setXXXX方法
					}

				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			return vo;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static Map getShortName_Map(Map map, String tabName)
	{
		Iterator ite = map.keySet().iterator();
		Map retMap = new HashMap();
		while (ite.hasNext())
		{
			String key = (String) ite.next();
			if (key.length() <= tabName.length())
				continue;
			String pre = key.substring(0, tabName.length()).toUpperCase();
			if (pre.equals(tabName.toUpperCase()))
			{
				String fieldName = key.substring(tabName.length() + 1);
				retMap.put(fieldName, map.get(key));
			}
		}
		return retMap;
	}

	public static Object map_to_Model(Map map, Class type)
	{
		try
		{
			Object obj = type.newInstance();
			Iterator ite = map.keySet().iterator();
			Map modelMap = new HashMap();
			while (ite.hasNext())
			{
				String key = (String) ite.next();
				String voFldName = getVoFldName_ByRstFldName(key);
				modelMap.put(voFldName, map.get(key));
			}

			BeanUtils.copyProperties(obj, modelMap);
			return (obj);
		} catch (Exception e)
		{
			e.printStackTrace();
			return (null);
		}
	}

	public static String getLastToken(String str, String tokenSeparator)
	{
		return str.substring(str.lastIndexOf(tokenSeparator) + 1, str.length());
	}

	public static boolean isNull(String s)
	{
		return s == null || s.length() < 1;
	}

	public static boolean isNull(String s, String val)
	{
		return isNull(s) || s.compareTo(val) == 0;
	}

	public static String stackTrace(Throwable t)
	{
		StringWriter sw = new StringWriter();
		t.printStackTrace(new PrintWriter(sw));
		String s = sw.toString();
		try
		{
			sw.close();
		} catch (IOException e)
		{
			cat.error("::stackTrace - cannot close the StringWriter object", e);
		}
		return s;
	}

	public static String dbString(String v)
	{
		StringBuffer sb = new StringBuffer();
		return isNull(v) ? "" : sb.append("'").append(v).append("'").toString();
	}

	public static String dumpHashTable(Hashtable table, boolean html)
	{
		Enumeration keys = table.keys();
		Enumeration values = table.elements();
		StringBuffer sb = new StringBuffer();
		String eof = "\n";
		if (html)
			eof = "<br>\n";
		for (; keys.hasMoreElements(); sb.append("  key [").append(keys.nextElement().toString()).append("] = [").append(values.nextElement().toString()).append("]").append(eof))
			;
		return sb.toString();
	}

	public static String addURLParameter(String URL, String paramName, String paramValue)
	{
		String param = paramName + "=" + paramValue;
		return addURLParameter(URL, param);
	}

	public static String addURLParameter(String URL, String parameter)
	{
		StringBuffer sb = new StringBuffer(URL);
		if (URL.lastIndexOf(63) == -1)
			sb.append("?");
		else
			sb.append("&");
		sb.append(parameter);
		return sb.toString();
	}

	public static String remove(String str, String until)
	{
		String val = null;
		int indx = str.indexOf(until);
		if (indx != -1)
			val = str.substring(indx + until.length(), str.length());
		return val;
	}

	/**
	 * @param sSource
	 * @param sIntervel
	 *            将一串带有,(假设sIntervel=",")的字符串分解开 如emp_id,emp_code,empname分解后得到
	 *            三个字符串emp_id和emp_code和emp_name
	 */
	public static List stringMid(String sSource, String sIntervel)
	{
		String sDes = "";
		List lst = new ArrayList();
		int nindex;
		while (sSource.length() > 0)
		{
			if (sSource.equals(sIntervel))
			{
				break;
			}

			nindex = sSource.indexOf(sIntervel);
			if (nindex < 0 && (!sSource.equals(sIntervel)))
			{
				lst.add(sSource);
				break;
			}
			sDes = sSource.substring(0, nindex);
			sSource = sSource.substring(nindex + sIntervel.length());

			if (sDes.length() >= sIntervel.length())
				if (sDes.substring(0, sIntervel.length()).equals(sIntervel))
				{
					continue;
				} else
				{
					lst.add(sDes);
				}
		}
		return (lst);
	}

	/**
	 * 得到一个MapList子List(记录集的子List),根据条件Map(可以设置好几个条件)
	 * 
	 * @param maplst
	 *            : 源记录集
	 * @param mapComp
	 *            : 条件
	 * @return
	 */
	public static List getSubMapList(List maplst, Map mapComp)
	{
		if (maplst == null || maplst.size() == 0)
			return null;
		if (mapComp == null)
			return null;

		// 得到条件的所有的key
		Iterator iteKey = mapComp.keySet().iterator();
		if (iteKey == null)
			return null;
		List lstKey = new ArrayList();
		while (iteKey.hasNext())
		{
			lstKey.add(iteKey.next());
		}
		if (lstKey.size() == 0)
			return null;

		// 开始循环比较
		List lstResult = new ArrayList();
		for (int i = 0; i < maplst.size(); i++)
		{
			Map oneRow = (Map) maplst.get(i);
			int equalFlag = 1; // 初始化为相等
			for (int j = 0; j < lstKey.size(); j++)
			{
				String key = (String) lstKey.get(j);
				String val1 = getMapKeyVal(oneRow, key);
				String val2 = getMapKeyVal(mapComp, key);
				if (!val1.equals(val2))
				{
					equalFlag = 0;
					break;
				}
			}
			if (equalFlag == 1)
			{// 如果全部相等,加入结果集
				lstResult.add(oneRow);
			}

		}

		return lstResult;
	}

	/**
	 * 模糊：得到一个MapList子List(记录集的子List),根据条件Map(可以设置好几个条件) 支持逗号分开的(',')
	 * 
	 * @param maplst
	 *            : 源记录集
	 * @param mapComp
	 *            : 条件
	 * @return
	 */
	public static List getBlurSubMapList(List maplst, Map mapComp)
	{
		if (maplst == null || maplst.size() == 0)
			return null;
		if (mapComp == null)
			return null;

		// 得到条件的所有的key
		Iterator iteKey = mapComp.keySet().iterator();
		if (iteKey == null)
			return null;
		List lstKey = new ArrayList();
		while (iteKey.hasNext())
		{
			lstKey.add(iteKey.next());
		}
		if (lstKey.size() == 0)
			return null;

		// 开始循环比较
		List lstResult = new ArrayList();
		for (int i = 0; i < maplst.size(); i++)
		{
			Map oneRow = (Map) maplst.get(i);
			int equalFlag = 1; // 初始化为相等
			for (int j = 0; j < lstKey.size(); j++)
			{
				String key = (String) lstKey.get(j);
				String val1 = getMapKeyVal(oneRow, key);
				String val2 = getMapKeyVal(mapComp, key);
				if (("," + val1 + ",").indexOf("," + val2 + ",") == -1)
				{
					equalFlag = 0;
					break;
				}
			}
			if (equalFlag == 1)// 如果全部相等,加入结果集
			{
				lstResult.add(oneRow);
			}
		}
		return lstResult;
	}

	/*
	 * 判断字符串是否是数字
	 */
	public static boolean checkStringIsNum(Object cheStrT)
	{
		String cheStr = cheStrT.toString();
		for (int i = 0; i < cheStr.length(); i++)
		{
			if (new String("9876543210").indexOf(cheStr.substring(i, i + 1)) == -1)
				return false;
		}
		return true;
	}

	public static String getMapKeyVal(Map map, String key)
	{
		if (map == null)
			return "";
		if (key == null)
			return "";
		Object obj = map.get(key);
		String result;
		if (obj == null)
		{
			result = "";
		} else
		{
			result = obj.toString();
		}
		return result;
	}

	public static String getJSONObjectKeyVal(JSONObject object, String key)
	{
		if (object == null)
		{
			return "";
		}
		if (key == null)
		{
			return "";
		}
		Object obj = object.get(key);
		String result;
		if (obj == null)
		{
			result = "";
		} else
		{
			result = obj.toString();
		}
		return result;
	}

	/**
	 * <h3>将mapList按照columnName进行分类</h3>
	 * 
	 * @author rb
	 * @param maplst
	 *            : 源记录集
	 * @param column
	 *            : 列名
	 * @return
	 */

	public static List sortMapListByColumn(String columnName, List mapList)
	{
		List returnList = new ArrayList();
		if (mapList == null || mapList.size() == 0)
			return returnList;
		List uniqueValList = new ArrayList();
		for (int i = 0; i < mapList.size(); i++)
		{
			HashMap map = (HashMap) mapList.get(i);
			String columnVal = getMapKeyVal(map, columnName);
			if ("".equals(columnVal))
			{
				return returnList;
			}
			HashMap tempMap = new HashMap();
			tempMap.put(columnName, columnVal);
			if (getSubMapList(uniqueValList, tempMap) == null || getSubMapList(uniqueValList, tempMap).size() == 0)
			{
				uniqueValList.add(tempMap);
			}
		}
		for (int k = 0; k < uniqueValList.size(); k++)
		{
			HashMap uniqueValMap = (HashMap) uniqueValList.get(k);
			if (getSubMapList(mapList, uniqueValMap) != null && getSubMapList(mapList, uniqueValMap).size() != 0)
			{
				returnList.add(getSubMapList(mapList, uniqueValMap));
			}
		}
		return returnList;
	}

	/**
	 * 将list中某所有字段为key的值组装成字符串以“，”分隔
	 * 
	 * @param list
	 * @param key
	 * @return
	 */
	public static String makeColumsToStr(List list, String key, String divide)
	{
		if (list == null || list.size() == 0 || "".equals(key))
		{
			return "";
		}
		String str = "";
		for (int i = 0; i < list.size(); i++)
		{
			Map mapComp = (Map) list.get(i);
			String keyValue = getMapKeyVal(mapComp, key);
			if (i == 0 && list.size() == 1)
			{
				str += keyValue;
			} else if (i < list.size() - 1)
			{
				str += keyValue + divide;
			} else
			{
				str += keyValue;
			}
		}
		return str;
	}

	/**
	 * 判读字符串是否为手机号码
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean isMobileNO(String mobiles)
	{
		if (mobiles == null)
			return false;
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 判读字符串是否为邮箱地址
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean isEmail(String email)
	{
		if (email == null)
			return false;
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * @author : tiankang
	 * @param json
	 * @return 取JSONObject中的时间
	 */
	public static Timestamp getTimestampByJT(JSONObject json)
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date.setTime(Long.parseLong(StringUtil.getJSONObjectKeyVal(json, "time")));
		String dateStr = sdf.format(date);
		Timestamp sta = Timestamp.valueOf(dateStr);
		return sta;
	}

	/**
	 * @author : tiankang
	 * @param str
	 * @return
	 */
	public static String filterEnter(String str)
	{
		int ind;
		StringBuffer sb = new StringBuffer();
		while ((ind = str.lastIndexOf("\n")) != -1)
		{
			sb.append(str.substring(0, ind));
			sb.append("\\n");
			sb.append(str.substring(ind + 1));
			str = sb.toString();
			sb.delete(0, str.length());
		}
		return str;
	}

	/**
	 * @author : tiankang
	 * @param str
	 * @return 数据表字段转对象属性
	 */
	public static String transDataToObj(String str)
	{
		int ind;
		StringBuffer sb = new StringBuffer();
		for (int i = 0, n = str.length(); i < n; i++)
		{
			char ch = str.charAt(i);
			if (ch == '_')
			{
				char sr = str.charAt(i + 1);
				sb.append((char) (sr - 32));
			} else
			{
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	/**
	 * @author : tiankang
	 * @param str
	 * @return 对象命名方式转数据库命名方式
	 */
	public static String transObjToData(String str)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0, n = str.length(); i < n; i++)
		{
			char ch = str.charAt(i);
			if (ch >= 'A' && ch <= 'Z')
			{
				sb.append("_" + (char) (ch + 32));
			} else
			{
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	/**
	 * @author : tiankang
	 * @param str
	 * @param num
	 * @return 根据特定数值添加空格
	 */
	public static String addSpace(StringBuffer str, int num)
	{
		int count = num - str.toString().length();
		for (int i = 0; i < count; i++)
		{
			str.append(" ");
		}
		return str.toString();
	}

	/*
	 * 判断字符串是否满足钱的格式
	 */
	public static boolean checkStringIsMoney(Object cheStrT)
	{
		if (!ObjectCensor.checkObjectIsNull(cheStrT))
		{
			String str = String.valueOf(cheStrT);
			String regex = "^[0-9]+.?[0-9]*$";
			if (str.matches(regex))
			{
				return true;
			} else
			{
				return false;
			}
		} else
		{
			return false;
		}
	}

	// 转化成上传所需文件名格式
	public static String uploadfileConverter(String staffId, String createDate, String fileName)
	{
		if (createDate.indexOf(".") != -1)
		{
			createDate = createDate.substring(0, createDate.indexOf("."));
		}
		createDate = createDate.replaceAll(":", "_");
		createDate = createDate.replaceAll(" ", "_");
		return staffId + "_" + createDate + "_" + fileName;
	}

	// 通过list获取对应值列表
	public static String fromListToString(List list, String name)
	{
		if (ObjectCensor.checkListIsNull(list))
		{
			StringBuffer sb = new StringBuffer();
			for (int i = 0, n = list.size(); i < n; i++)
			{
				Map map = (Map) list.get(i);
				String val = StringUtil.getMapKeyVal(map, name);
				sb.append(val + ",");
			}
			if (!"".equals(sb.toString()))
			{
				String result = sb.toString();
				return result.substring(0, result.length() - 1);
			}
		}
		return "";
	}

	// 获取json中的指定元素
	public static String fromJsonToString(JSONArray json, String name)
	{
		if (!ObjectCensor.checkObjectIsNull(json))
		{
			StringBuffer sb = new StringBuffer();
			for (int i = 0, n = json.size(); i < n; i++)
			{
				JSONObject obj = json.getJSONObject(i);
				sb.append(StringUtil.getJSONObjectKeyVal(obj, name) + ",");
			}
			if (!"".equals(sb.toString()))
			{
				String result = sb.toString();
				return result.substring(0, result.length() - 1);
			}
		}
		return "";
	}

	// 转义符转成对应的ascii
	public static String transformChr(String content, String ctrl, String chr)
	{
		content = "'" + content + "'";
		String temp = "'||chr(" + chr + ")||'";
		content = content.replace(ctrl, temp);
		content = content.replace("||''||", "||");
		content = content.replace("''||chr(" + chr + ")||'", "'");
		int becnt = 0, encnt = content.length() - 1;
		while (becnt < content.length())
		{
			if (content.charAt(becnt) == '|' || (content.charAt(becnt) == '\'' && becnt != 0))
			{
				becnt++;
			} else
			{
				break;
			}
		}
		while (encnt >= 0)
		{
			if (content.charAt(encnt) == '|' || (content.charAt(encnt) == '\'' && encnt != content.length() - 1))
			{
				encnt--;
			} else
			{
				break;
			}
		}
		content = content.substring(becnt, encnt + 1);
		return content;
	}

	// 处理sql中in语句内容超过1000的情况
	public static void sqlInOperAdjust(String strList, String column, StringBuffer query)
	{
		String[] arrList = strList.split(",");
		if (arrList.length == 1)
		{
			query.append(" " + column + " in  (" + strList + ") ");
		} else
		{
			query.append(" ( ");
			int cnt = 0;
			StringBuffer sb = new StringBuffer();
			for (int i = 0, n = arrList.length; i < n; i++)
			{
				if (cnt < 1000)
				{
					sb.append(arrList[i] + ",");
					cnt++;
				} else
				{
					String temp = sb.toString();
					query.append(" " + column + " in (" + temp.substring(0, temp.length() - 1) + ")#");
					sb = new StringBuffer();
					cnt = 0;
				}
			}
			if (cnt < 1000)
			{
				String temp = sb.toString();
				query.append(" " + column + " in (" + temp.substring(0, temp.length() - 1) + ")");
			} else
			{
				String temp = query.toString();
				temp = temp.substring(0, temp.length() - 1);
				query.setLength(0);
				query.append(temp);
			}
			query.append(" ) ");
			String temp = query.toString();
			temp = temp.replace("#", " or ");
			query.setLength(0);
			query.append(temp);
		}
	}
}
