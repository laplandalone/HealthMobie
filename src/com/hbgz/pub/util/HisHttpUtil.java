package com.hbgz.pub.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
public class HisHttpUtil 
{
	private static String urls="http://mobilemedical.net.cn:10821/his/mobile.htm?method=axis&param=";
	public static String http(String param) throws Exception
	{
		URL u = null;
		HttpURLConnection con = null;
		try
		{
			u = new URL(urls+param);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
//			osw.write(param);
			osw.flush();
			osw.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (con != null)
			{
				con.disconnect();
			}
		}

		// ∂¡»°∑µªÿƒ⁄»›
		StringBuffer buffer = new StringBuffer();
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
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
		String sss=buffer.toString();
		return sss;
	}
	
	public static void main(String[] args) throws Exception 
	{
		String sql="select ltrim(v.operation_type) operation_type,ltrim(v.department) department,ltrim(v.patient_id) patient_id,convert(varchar(10),v.operation_time,102) operation_time,rtrim(m.patient_name) patient_name,rtrim(m.birthday) birthday from view_ssqk_app v,mzbrxx m where v.patient_id=m.patient_id and v.patient_id in ('PID000286354','PID000101914')";
		String retVal = HisHttpUtil.http(sql);
		System.err.println(retVal);
		
	}
}
