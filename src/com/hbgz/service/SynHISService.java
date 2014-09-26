package com.hbgz.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ISH_Service.AuthHeader;
import cn.ISH_Service.Service1;
import cn.ISH_Service.Service1Locator;
import cn.ISH_Service.Service1Soap12Stub;

import com.hbgz.dao.DigitalHealthDao;
import com.hbgz.dao.HibernateObjectDao;
import com.hbgz.model.DoctorT;
import com.hbgz.model.TeamT;
import com.hbgz.pub.base.SysDate;
import com.hbgz.pub.sequence.SysId;
import com.hbgz.pub.util.DateUtils;
import com.hbgz.pub.util.ObjectCensor;
import com.hbgz.pub.util.StringUtil;
import com.hbgz.pub.xml.XMLComm;

@Service
public class SynHISService {
	
	@Autowired
	private DigitalHealthDao digitalHealthDao;
	
	@Autowired
	private HibernateObjectDao hibernateObjectDao;

	@Autowired
	private SysId sysId;
	
	public  String invokeFunc(String sql) throws Exception 
	{
		Service1 ser = new Service1Locator();
		Service1Soap12Stub servStub;
		URL url;
		try {
			url = new URL("http://27.17.0.42:8800/ISH_Service/service1.asmx?wsdl");
			servStub = (Service1Soap12Stub) ser.getService1Soap12(url);
			AuthHeader authHeader = new AuthHeader();
			authHeader.setAuthorizationCode("pF8/tPHpkBoAgVxqLZyOMg==");
			servStub.setHeader("http://ISH_Service.cn/", "AuthHeader",authHeader);
			String retVal = servStub.SQLExecH(sql);
			return retVal;
		} catch (Exception err) 
		{
			err.printStackTrace();
		}
		return "error";
	}
	public void snHisTeamService() throws Exception
	{
		
		String sql="<DS><SQL><str>select distinct  bzdm team_id ,bzmc team_name from mz_bzdyb  </str></SQL></DS>";
		String ss = invokeFunc(sql);
		System.out.println(ss);
		Document doc = XMLComm.loadXMLString(ss);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		for (int i = 0; i < list.size(); i++)
		{
			Element e = (Element) list.get(i);
			TeamT teamT = new TeamT();
			String teamId=e.getChildText("team_id");
			String teamName=e.getChildText("team_name");
			teamT.setHospitalId("102");
			teamT.setTeamId(teamId);
			teamT.setTeamName(teamName);
			teamT.setState("00A");
			teamT.setTeamType("2");
			teamT.setExpertFlag("0");
			hibernateObjectDao.save(teamT);
		}
		
	}
	public void synHisDoctorService() 
	{
		String sql="<DS><SQL><str>select a.bzmc team_name,a.bzdm team_id,a.ysdm doctor_id,b.zgxm doctor_name,* from mz_bzdyb a, comm_zgdm b where a.ysdm=b.zgid  order by bzmc</str></SQL></DS>";
		try {
			String ss = invokeFunc(sql);
			System.out.println(ss);
			Document doc = XMLComm.loadXMLString(ss);
			Element root = doc.getRootElement();
			List list = root.getChildren();
			for (int i = 0; i < list.size(); i++)
			{
				Element e = (Element) list.get(i);
				String doctorId=e.getChildText("doctor_id");
				String doctorName=e.getChildText("doctor_name");
//				String sex="";
//				String post="";
				String teamId=e.getChildText("team_id");;
				DoctorT doctorT = new DoctorT();
				doctorT.setDoctorId(doctorId);
				doctorT.setName(doctorName);
				doctorT.setHospitalId("102");
//				doctorT.setSex(sex);
				doctorT.setCreateDate(SysDate.getSysDate());
//				doctorT.setPost(post);
				doctorT.setTeamId(teamId);
				doctorT.setState("00A");
//				hibernateObjectDao.save(doctorT);
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List synHisRegisterOrderService(String teamIdT) throws Exception 
	{
		Date startDateT = DateUtils.afterNDate(1);
		Date endDateT = DateUtils.afterNDate(7);
		String startDate=DateUtils.CHN_DATE_FORMAT.format(startDateT);
		String endDate=DateUtils.CHN_DATE_FORMAT.format(endDateT);
		startDate=startDate.replace('-','.');
		endDate=endDate.replace('-','.');
		String sql="<DS><SQL><str>select  delb ,c.bzdm team_id,c.bzmc team_name,kszjdm doctor_id,b.zgxm doctor_name,derq day from mz_ghde a,comm_zgdm b,mz_bzdyb c where a.kszjdm=b.zgid and a.kszjdm=c.ysdm and  c.bzdm='"+teamIdT+"' and derq between  '"+startDate+"' and '"+endDate+"' order by derq  </str></SQL></DS>";
		String ss =invokeFunc(sql);
		Document doc = XMLComm.loadXMLString(ss);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		List listHis = new ArrayList();
		String dayT="";
		
		for (int i = 0; i < list.size(); i++)
		{
			Element e = (Element) list.get(i);
			String doctorId=e.getChildText("doctor_id");
			String doctorName=e.getChildText("doctor_name");
			String teamId=e.getChildText("team_id");
			String teamName=e.getChildText("team_name");
			String day=e.getChildText("day");
			day=day.replace('.','-');
			Date dateT=DateUtils.CHN_DATE_FORMAT.parse(day);
			String weekStr = DateUtils.getWeekOfDate(dateT);
			Map newMap = new HashMap();
			newMap.put("doctorName", doctorName);
			newMap.put("teamName", teamName);
			newMap.put("doctorId", doctorId);
			newMap.put("teamId", teamId);
			newMap.put("introduce", "");
			newMap.put("post", "");
			newMap.put("week",weekStr);
			newMap.put("day", day);
			
			if(!dayT.equals(day))
			{
				newMap.put("display","Y");
				dayT=day;
			}else
			{
				newMap.put("display","N");
			}
			
			
			listHis.add(newMap);
		}
		return listHis;
	}
	public List getHisDoctorRegister(String doctorIdT,String userId,String dateStr) throws Exception
	{
		String derq=dateStr.replace('-','.');
		String sql="<DS><SQL><str>select a.id,c.bzmc team_name,c.bzdm team_id,kszjdm doctor_id,derq day,swdes-ylrs-swyyrs am_num,xwdes-xwylrs-xwyyrs pm_num,swyyrs+1 am_user_register_num, xwyyrs+1 pm_user_register_num  from mz_ghde a, mz_bzdyb c where derq='"+derq+"' and a.kszjdm=c.ysdm and kszjdm='"+doctorIdT+"' </str></SQL></DS>";
		String ss =invokeFunc(sql);
		Document doc = XMLComm.loadXMLString(ss);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		List listHis = new ArrayList();
		String dayT="";
		
		List userOrderList = digitalHealthDao.qryUserOrderByPhone(userId,dateStr);
		
		String[] dayTypes=new String[]{"am","pm"};
		
		for(int m=0;m<dayTypes.length;m++)
		{
			String dayTypeMark=dayTypes[m];
		
			for (int i = 0; i < list.size(); i++)
			{
				Element e = (Element) list.get(i);
				String doctorId=e.getChildText("doctor_id");
				String teamId=e.getChildText("team_id");
				String teamName=e.getChildText("team_name");
				String registerId=e.getChildText("id");
				String registerNum=e.getChildText(dayTypeMark+"_num");/*可预约号数*/
				String userOrderNum=e.getChildText(dayTypeMark+"_user_register_num");/*用户可预约号*/
				String dayType="";
				String numMax="false";
				
				if("am".startsWith(dayTypeMark))
				{
					dayType="上午";
				}else
				{
					dayType="下午";
				}
				if("0".equals(registerNum))
				{
					numMax="true";
					userOrderNum="0";
				}
				String day=e.getChildText("day");
				day=day.replace('.','-');
				Date dateT=DateUtils.CHN_DATE_FORMAT.parse(day);
				String weekStr = DateUtils.getWeekOfDate(dateT);
				String workTime = "星期" + weekStr + dayType;
				String dayWorkTime = dateStr + workTime;
			
				String fee="5";
				
				String orderTeamCount="0";
				if(ObjectCensor.checkListIsNull(userOrderList))
				{
					orderTeamCount=userOrderList.size()+"";
				}
				
				String userFlag="N";
				if(ObjectCensor.checkListIsNull(userOrderList))
				{
					for(int n=0;n<userOrderList.size();n++)
					{
						Map userOrder= (Map) userOrderList.get(n);
						String registerIdT=StringUtil.getMapKeyVal(userOrder, "registerId");
						String registerTimeT=StringUtil.getMapKeyVal(userOrder, "registerTime");
						String userIdT=StringUtil.getMapKeyVal(userOrder, "userId");
						String regitsertdoctorIdT=StringUtil.getMapKeyVal(userOrder, "doctorId");
						if(registerTimeT.equals(dayWorkTime)&&userIdT.equals(userId)&&regitsertdoctorIdT.equals(doctorId))
						{
							userFlag="Y";
							break;
						}
					}
				}
				
				Map newMap = new HashMap();
				newMap.put("registerId", registerId);
				newMap.put("teamName", teamName);
				newMap.put("userOrderNum", userOrderNum);// 预约号码
				newMap.put("doctorId", doctorId);
				newMap.put("teamId", teamId);
				newMap.put("fee", fee);
				newMap.put("registerNum", registerNum);
				newMap.put("day",day);
				newMap.put("workTime", " 星期" + weekStr + " " + dayType);
				newMap.put("userFlag", userFlag);
				newMap.put("orderTeamCount",orderTeamCount);
				newMap.put("numMax",numMax);
				listHis.add(newMap);
			}
		}
		return listHis;
	}
	
	/**
	 * 预约成功：更新上午-swyyrs ，下午-xwyyrs 字段，未付款之前
	 * @param id-register_id
	 * @throws Exception
	 */
	public void hisRegisterOrder(String id,String weekType) throws Exception
	{
		StringBuffer sql=new StringBuffer();
		sql.append("<DS>");
		sql.append("<SQL><str>update mz_ghde set "+weekType+"  = "+weekType+"  where id = "+id+"</str></SQL>");
		sql.append("<SQL><str>select "+weekType+"  + 1 from mz_ghde where id = "+id+"</str></SQL>");
		sql.append("<SQL><str>update mz_ghde set "+weekType+"  = "+weekType+"  + 1 where id = "+id+"</str></SQL>");
		sql.append("</DS>");
		String ss =invokeFunc(sql.toString());
		System.out.println(ss);
	}
	
	
	
	
	public static void main(String[] args) throws Exception
	{
//		String sql="<DS><SQL><str>select a.id,c.bzmc team_name,c.bzdm team_id,kszjdm doctor_id,derq day,swdes,ylrs,swyyrs ,swdes-ylrs-swyyrs am_num,xwdes-xwylrs-xwyyrs pm_num  from mz_ghde a, mz_bzdyb c where derq='2014.09.30' and a.kszjdm=c.ysdm and kszjdm='0629R' </str></SQL></DS>";
//		String sql="<DS><SQL><str>select distinct bzmc  from mz_bzdyb c order by bzmc </str></SQL></DS>";
//		String sql="<DS><SQL><str>select  yxf ,delb ,c.bzdm team_id,c.bzmc team_name,kszjdm doctor_id,b.zgxm doctor_name,derq day from mz_ghde a,comm_zgdm b,mz_bzdyb c where a.kszjdm=b.zgid and a.kszjdm=c.ysdm  and derq between  '2014.09.27' and '2014.10.01' order by derq  </str></SQL></DS>";

		StringBuffer sql=new StringBuffer("<DS>");
//		sql.append("<SQL><str>update mz_ghde set swyyrs  = swyyrs  where id = 500011365</str></SQL>");
		sql.append("<SQL><str>select swyyrs  + 1 from mz_ghde where id = 500011365</str></SQL>");
//		sql.append("<SQL><str>update mz_ghde set swyyrs  = swyyrs  - 1 where id = 500011365</str></SQL>");
		sql.append("</DS>");
		
		String ss =new SynHISService().invokeFunc(sql.toString());
		System.out.println(ss);
	   
	}
}
