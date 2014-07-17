package com.hbgz.pub.resolver;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.ezmorph.Morpher;
import net.sf.ezmorph.MorpherRegistry;
import net.sf.ezmorph.bean.BeanMorpher;
import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.util.JSONUtils;

import com.hbgz.pub.base.JacksonJsonMapper;
import com.hbgz.pub.util.ObjectCensor;
import com.hbgz.pub.util.StringUtil;

public class ServiceMsgConverter 
{
	/**
	 * @author RHQ
	 * @param params
	 * @param beanClass
	 * @return
	 * @throws Exception
	 */
	public static Object jsonToBeanConverter(String params, Class beanClass)
			throws Exception 
	{
		try 
		{
			params = StringUtil.filterEnter(params);
			JSONObject jsonObject = JSONObject.fromObject(params);
			return jsonObject.toBean(jsonObject, beanClass);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new Exception("ServiceMsgConverter: JsonToBean conversion failure:" + "converType:" + beanClass + ",params:" + params);
		}
	}

	/**
	 * 
	 * @param method
	 * @param dataVals
	 * @return
	 * @throws Exception
	 */
	public static Object[] argsTypeConvertor(Method method, Object[] dataVals)
			throws Exception
	{

		Class[] paramTypes = method.getParameterTypes();
		int paramTypesL = paramTypes.length;
		int dataValsL = dataVals.length;

		if (paramTypesL != dataValsL) 
		{
			throw new Exception("ServiceMsgConverter: paramter converter is error:" + " method paramters length:" + paramTypesL + " joinParam length:" + dataValsL);
		}

		Object[] objects = new Object[dataValsL];
		for (int i = 0; i < paramTypesL; i++) 
		{
			Class dataType = paramTypes[i];
			String typeName = dataType.getName();
			String valTypeName = dataVals[i].getClass().getName();
			// modified by yangjingwen 2012.12.17
			String dataVal = dataVals[i].toString();

			if ("int".equals(typeName) && checkBaseDataType(dataVals[i])) 
			{
				if (!StringUtil.checkStringIsNum(dataVal)) 
				{
					throw new Exception("ServiceMsgConverter: paramter is error," + "converType:" + typeName + ",value:" + dataVal);
				}
				objects[i] = new Integer(dataVal);
			} 
			else if ("byte".equals(typeName) && checkBaseDataType(dataVals[i])) 
			{
				if (!StringUtil.checkStringIsNum(dataVal)) 
				{
					throw new Exception("ServiceMsgConverter: paramter is error," + "converType:" + typeName + ",value:" + dataVal);
				}
				objects[i] = new Byte(dataVal);
			} 
			else if ("short".equals(typeName) && checkBaseDataType(dataVals[i])) 
			{
				if (!StringUtil.checkStringIsNum(dataVal)) 
				{
					throw new Exception("ServiceMsgConverter: paramter is error," + "converType:" + typeName + ",value:" + dataVal);
				}
				objects[i] = new Short(dataVal);
			} 
			else if ("long".equals(typeName) && checkBaseDataType(dataVals[i])) 
			{
				if (!StringUtil.checkStringIsNum(dataVal))
				{
					throw new Exception("ServiceMsgConverter: paramter is error," + "converType:" + typeName + ",value:" + dataVal);
				}
				objects[i] = new Long(dataVal);
			} else if ("float".equals(typeName) && checkBaseDataType(dataVals[i])) 
			{
				if (!StringUtil.checkStringIsNum(dataVal)) 
				{
					throw new Exception("ServiceMsgConverter: paramter is error," + "converType:" + typeName + ",value:" + dataVal);
				}
				objects[i] = new Float(dataVal);
			}
			else if ("double".equals(typeName) && checkBaseDataType(dataVals[i])) 
			{
				if (!StringUtil.checkStringIsNum(dataVal)) 
				{
					throw new Exception("ServiceMsgConverter: paramter is error," + "converType:" + typeName + ",value:" + dataVal);
				}
				objects[i] = new Double(dataVal);
			} 
			else if ("java.lang.Integer".equals(typeName) && checkBaseDataType(dataVals[i])) 
			{
				if (!StringUtil.checkStringIsNum(dataVal)) 
				{
					throw new Exception("ServiceMsgConverter: paramter is error," + "converType:" + typeName + ",value:" + dataVal);
				}
				objects[i] = new Integer(dataVal);
			}
			else if ("java.lang.Byte".equals(typeName) && checkBaseDataType(dataVals[i])) 
			{
				if (!StringUtil.checkStringIsNum(dataVal)) 
				{
					throw new Exception("ServiceMsgConverter: paramter is error," + "converType:" + typeName + ",value:" + dataVal);
				}
				objects[i] = new Byte(dataVal);
			}
			else if ("java.lang.Short".equals(typeName) && checkBaseDataType(dataVals[i])) 
			{
				if (!StringUtil.checkStringIsNum(dataVal)) 
				{
					throw new Exception("ServiceMsgConverter: paramter is error," + "converType:" + typeName + ",value:" + dataVal);
				}
				objects[i] = new Byte(dataVal);
			} 
			else if ("java.lang.Long".equals(typeName) && checkBaseDataType(dataVals[i])) 
			{
				if (!StringUtil.checkStringIsNum(dataVal)) 
				{
					throw new Exception("ServiceMsgConverter: paramter is error," + "converType:" + typeName + ",value:" + dataVal);
				}
				objects[i] = new Long(dataVal);
			} 
			else if ("java.lang.Float".equals(typeName) && checkBaseDataType(dataVals[i])) 
			{
				if (!StringUtil.checkStringIsNum(dataVal)) 
				{
					throw new Exception(
							"ServiceMsgConverter: paramter is error," + "converType:" + typeName + ",value:" + dataVal);
				}
				objects[i] = new Long(dataVal);
			} 
			else if ("java.lang.Double".equals(typeName) && checkBaseDataType(dataVals[i])) 
			{
				if (!StringUtil.checkStringIsNum(dataVal)) 
				{
					throw new Exception("ServiceMsgConverter: paramter is error," + "converType:" + typeName + ",value:" + dataVal);
				}
				objects[i] = new Double(dataVal);
			}
			else if ("java.lang.Float".equals(typeName) && checkBaseDataType(dataVals[i])) 
			{
				if (!StringUtil.checkStringIsNum(dataVal)) 
				{
					throw new Exception("ServiceMsgConverter: paramter is error," + "converType:" + typeName + ",value:" + dataVal);
				}
				objects[i] = new Float(dataVal);
			} 
			else if ("java.lang.String".equals(typeName) && checkBaseDataType(dataVals[i])) 
			{
				objects[i] = dataVal;
			} 
			else 
			{
				JSON jsonVal = JSONSerializer.toJSON(dataVals[i]);
				objects[i] = jsonVal.toString();
			}
		}
		return objects;
	}

	private static Object transferToBean(MorphDynaBean dynaBean, Class clazz) 
	{
		MorpherRegistry morpherRegistry = JSONUtils.getMorpherRegistry();
		Morpher dynaMorpher = new BeanMorpher(clazz, morpherRegistry);
		morpherRegistry.registerMorpher(dynaMorpher);
		return morpherRegistry.morph(clazz, dynaBean);
	}

	/**
	 * 判断对象是否数据基本类型
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean checkBaseDataType(Object obj) 
	{
		boolean flag = false;
		String typeName = obj.getClass().getName();
		if ("int".equals(typeName)) 
		{
			flag = true;
		}
		else if ("byte".equals(typeName)) 
		{
			flag = true;
		} 
		else if ("short".equals(typeName)) 
		{
			flag = true;
		} 
		else if ("long".equals(typeName)) 
		{
			flag = true;
		}
		else if ("float".equals(typeName)) 
		{
			flag = true;
		} 
		else if ("double".equals(typeName)) 
		{
			flag = true;
		}
		else if (("java.lang.Integer").equals(typeName)) 
		{
			flag = true;
		}
		else if ("java.lang.Byte".equals(typeName)) 
		{
			flag = true;
		}
		else if ("java.lang.Short".equals(typeName)) 
		{
			flag = true;
		} 
		else if ("java.lang.Long".equals(typeName)) 
		{
			flag = true;
		}
		else if ("java.lang.Double".equals(typeName)) 
		{
			flag = true;
		} 
		else if ("java.lang.Float".equals(typeName)) 
		{
			flag = true;
		}
		else if ("java.lang.String".equals(typeName)) 
		{
			flag = true;
		} 
		else if ("java.lang.Boolean".equals(typeName)) 
		{
			flag = true;
		}
		else if ("java.lang.Character".equals(typeName)) 
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * @param obj
	 *            调用服务访问结果
	 * @param converType
	 *            数据转化类型标识
	 * @return
	 * @throws Exception
	 */
	public static String beanToJsonConverter(Object obj) throws Exception 
	{
		String serviceResult = "";
		if (obj == null) 
		{
			return null;
		}
		if (obj instanceof List) 
		{
			try 
			{
				JSONArray jsonArray = JSONArray.fromObject(obj);
				serviceResult = jsonArray.toString();
			} 
			catch (Exception e) 
			{
				throw new Exception("ServiceMsgConverter: Service conversion failure:" + "converType:List" + obj + e.getMessage());
			}
		}
		else if (!checkBaseDataType(obj)) 
		{
			try
			{
				JSONObject jsonObject = JSONObject.fromObject(obj);
				serviceResult = jsonObject.toString();
			} 
			catch (Exception e) 
			{
				throw new Exception("ServiceMsgConverter: Service conversion failure:" + "converType:Object" + obj + e.getMessage());
			}
		}
		else 
		{
			return obj.toString();
		}
		return serviceResult;
	}

	public static Object getServiceRstMessage(String rst, Class paramClassType) throws Exception 
	{
		JSONObject object = JSONObject.fromObject(rst);
		String executeType = StringUtil.getJSONObjectKeyVal(object, "executeType");
		String returnMsgType = StringUtil.getJSONObjectKeyVal(object, "returnMsgType");
		if ("success".equals(executeType)) 
		{
			String returnMsg = StringUtil.getJSONObjectKeyVal(object, "returnMsg");
			if ("JSONArray".equals(returnMsgType)) 
			{
				return JacksonJsonMapper.getInstance().readValue(returnMsg, paramClassType);
			} 
			else 
			{
				if (ObjectCensor.isStrRegular(returnMsg) && !"null".equals(returnMsg))
				{
					JSONObject jsonObject = JSONObject.fromObject(returnMsg);
					return jsonObject.toBean(jsonObject, paramClassType);
				}
				return null;
			}
		} 
		else 
		{
			String execeptionMsg = StringUtil.getJSONObjectKeyVal(object, "exceptionMsg");
			throw new Exception(execeptionMsg);
		}
	}
}
