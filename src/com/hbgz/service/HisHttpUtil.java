package com.hbgz.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

 

import com.hbgz.pub.base.SysDate;
import com.hbgz.pub.util.DateUtils;
public class HisHttpUtil 
{
	private static String urls="http://27.17.0.42:10821/his/mobile.htm?method=axis&param=";
//	private static String urls="http://mobilemedical.net.cn:10821/his/mobile.htm?method=axis&param=";
	
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
		String sql="select card_id,sample_type,patient_id,check_scope,check_unit,check_name,department,check_result,check_type,convert(varchar(10),check_time,110) check_time from view_lis_result_app where patient_id='PID000251940' order by check_type";

		String sql6="select  top 10000 convert(varchar(10),operation_time,102) operation_time,patient_id from view_ssqk_app   order by operation_time desc";
		
		String sql1="select  top 10 patient_name,patient_id from mzbrxx where  patient_id='PID000595293'";
		String sql2="select  top 50 * from view_ssqk_app ";
		String sql5="select  convert(varchar(10),check_time,110) check_time from  view_lis_lx_app where patient_id='PID000100014' group by convert(varchar(10),check_time,110)";
		
		String sql3="select  * from mzbrxx where patient_id='PID000000014' and identity_id='420102400905311   '";
		String sql4="select rtrim(v.operation_type) operation_type,rtrim(v.department) department,rtrim(v.patient_id) patient_id,convert(varchar(10),v.operation_time,102) operation_time,rtrim(m.patient_name) patient_name from view_ssqk_app v,mzbrxx m where v.patient_id=m.patient_id and v.patient_id='PID000595293' ";
		String sql7="select card_id,sample_type,patient_id,check_scope,check_unit,check_name,department,check_result,check_type,convert(varchar(10),check_time,110) check_time from view_lis_result_app where patient_id='PID000251940' and convert(varchar(10),check_time,110)='12-05-2014' order by check_type asc";
		String sqlss="select  * from view_lis_result_app  where patient_id='PID000251940'";
		String sqlsss="select  distinct(check_type_id),check_type from  view_lis_lx_app where patient_id='PID000595293'";
		String ssss="select check_type,check_type_id from view_lis_result_app where    patient_id='PID000251940'";
		String ssssss="select card_id,sample_type,patient_id,check_scope,check_unit,check_name,department,check_result,check_type,convert(varchar(10),check_time,110) check_time from view_lis_result_app where check_type_id=291 patient_id='PID000251940' and convert(varchar(10),check_time,110)='12-05-2014' ";
		String a=" select  convert(varchar(10),check_time,102) check_time from  view_lis_lx_app where patient_id='PID000595293' group by convert(varchar(10),check_time,102)  order by check_time desc ";
		String b="select card_id,sample_type,patient_id,check_scope,check_unit,check_name,department,check_result,check_type,convert(varchar(10),check_time,102) check_time from view_lis_result_app where      convert(varchar(10),check_time,102)='2015.01.14' ";
		String ss="select top 10 card_id,sample_type,patient_id,rtrim(check_scope)check_scope,check_unit,check_name,department,rtrim(check_result)check_result,check_type,convert(varchar(10),check_time,102) check_time from view_lis_result_app";
		String sssss="  select rtrim(v.operation_type) operation_type,rtrim(v.department) department,rtrim(v.patient_id) patient_id,convert(varchar(10),v.operation_time,102) operation_time,rtrim(m.patient_name) patient_name  from view_ssqk_app v,mzbrxx m where v.patient_id=m.patient_id and v.patient_id  in ('PID000773637','PID000773403','PID000772707','PID000772104','PID000771928','PID000771732','PID000771675','PID000770578','PID000770363','PID000769917','PID000768347','PID000767249','PID000766981','PID000766818','PID000766508','PID000766006','PID000765964','PID000765607','PID000762709','PID000762443','PID000761653','PID000761375','PID000759633','PID000757786','PID000756311','PID000755546','PID000754537','PID000754191','PID000752998','PID000752998','PID000752067','PID000751741','PID000749272','PID000748375','PID000747069','PID000746659','PID000742096','PID000739567','PID000731989','PID000727535','PID000722245','PID000715528','PID000711489','PID000711489','PID000685481','PID000613830','PID000601980','PID000595293','PID000595293','PID000595293','PID000536955','PID000520439','PID000505369','PID000439076','PID000365830','PID000287146','PID000151251','PID000123488','PID000102274')";
		String retVal = HisHttpUtil.http(sssss);
		JSONArray array = JSONArray.fromObject(retVal);
		for(int i=0;i<array.size();i++)
		{
		    JSONObject object = array.getJSONObject(i);
		    String pId=object.getString("patient_id");
		    String pName=object.getString("patient_name");
		    String pTime=object.getString("operation_time");
		    pTime=pTime.replace(".", "-");
			String date30 = DateUtils.afterNDate(pTime, 31);
			System.out.println(SysDate.getSysDate(date30+" 00:00:00"));
			if(new Date().before(SysDate.getSysDate(date30+" 00:00:00")))
			{
				}
			}
		System.err.println(retVal);
//		String patientId="PID000251940";
//		patientId=patientId.substring(patientId.length()-6,patientId.length());
//		System.out.println(patientId);
	}
}
