package com.hbgz.pub.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpUtil
{
	public static Log log = LogFactory.getLog(HttpUtil.class);

	public static String http(String url, Map<String, String> params,
			String logStr, String flag, String encoding) throws Exception 
	{
		URL u = null;
		HttpURLConnection con = null;
		// �����������
		StringBuffer sb = new StringBuffer();
		if (params != null) 
		{
			for (Entry<String, String> e : params.entrySet()) 
			{
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
			sb.replace(0, sb.length(), sb.substring(0, sb.length() - 1));
		}
		log.warn("send_url:" + url + "?" + sb.toString());
		log.warn("send_data:" + sb.toString());

		try 
		{
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "GBK");
			osw.write(sb.toString());
			osw.flush();
			osw.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			if (con != null)
			{
				con.disconnect();
			}
		}

		// ��ȡ��������
		StringBuffer buffer = new StringBuffer();
		BufferedReader br = null;
		try
		{
			if ("UTF-8".equals(encoding.toUpperCase())) 
			{
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")); // ȥ����UTF-8
			} 
			else 
			{
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			}
			String temp;
			while ((temp = br.readLine()) != null) 
			{
				buffer.append(temp);
				buffer.append("\n");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(br != null)
			{
				br.close();
			}
		}
		return buffer.toString();
	}

	public static void main(String[] args) 
	{
		String url = "http://api.app2e.com/smsBigSend.api.php";
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", "haixing");
		params.put("pwd", "cb6fbeee3deb608f000a8f132531b738");
		params.put("p", "18907181658");
		params.put("isUrlEncode", "no");
		params.put("msg", "msg");
		// params.put("msg", "������ͨ�������𾴵��û�����ע����֤����123456�������ܼ�Ը��Ϊ�������ĺð��֣�");
		try
		{
			String retVal = HttpUtil.http(url, params, "", "", "");
			System.err.println(retVal);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public static String http(String address, String param)
	{
		log.error("send_data:" + param);
		HttpURLConnection conn = null;
		try 
		{
			URL url = new URL(address);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			osw.write(param);
			osw.flush();
			osw.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if (conn != null)
			{
				conn.disconnect();
			}
		}
		
		
		// ��ȡ��������
		StringBuffer buffer = new StringBuffer();
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null)
			{
				buffer.append(temp);
				buffer.append("\n");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(br != null)
			{
                try 
                {
                    br.close();
                } 
                catch (Exception e) 
                {
                      e.printStackTrace();
                }
			}
		}
		return buffer.toString();
	}
}
