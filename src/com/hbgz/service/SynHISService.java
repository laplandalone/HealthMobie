 package com.hbgz.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ISH_Service.AuthHeader;
import cn.ISH_Service.Service1;
import cn.ISH_Service.Service1Locator;
import cn.ISH_Service.Service1Soap12Stub;

import com.googlecode.ehcache.annotations.Cacheable;
import com.hbgz.dao.DigitalHealthDao;
import com.hbgz.dao.HibernateObjectDao;
import com.hbgz.model.DoctorT;
import com.hbgz.model.RegisterOrderT;
import com.hbgz.model.TeamT;
import com.hbgz.pub.base.SysDate;
import com.hbgz.pub.exception.QryException;
import com.hbgz.pub.sequence.SysId;
import com.hbgz.pub.util.DateUtils;
import com.hbgz.pub.xml.XMLComm;
import com.tools.pub.utils.ObjectCensor;
import com.tools.pub.utils.StringUtil;


@Service
public class SynHISService {
	
	@Autowired
	private DigitalHealthDao digitalHealthDao;
	
	@Autowired
	private HibernateObjectDao hibernateObjectDao;

	@Autowired
	private SysId sysId;
	private static Log log = LogFactory.getLog(SynHISService.class);
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
			log.error("HISSQL:"+sql);
			String retVal = servStub.SQLExecH(sql);
			return retVal;
		} catch (Exception err) 
		{
			err.printStackTrace();
		}
		return "error";
	}
	/**
	 * 科室同步
	 * @throws Exception
	 */
	public void snHisTeamService() throws Exception
	{
		
		String sql="<DS><SQL><str>select distinct  bzdm team_id ,bzmc team_name from mz_bzdyb  </str></SQL></DS>";
		String ss = invokeFunc(sql);
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
	
	/*
	 * 医生同步
	 */
	public void synHisDoctorService() 
	{
		String sql="<DS><SQL><str>select a.bzmc team_name,a.bzdm team_id,a.ysdm doctor_id,b.zgxm doctor_name,* from mz_bzdyb a, comm_zgdm b where a.ysdm=b.zgid  order by bzmc</str></SQL></DS>";
		try {
			String ss = invokeFunc(sql);
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
				doctorT.setDoctorId(doctorId.trim());
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
	
	/**
	 * 预约时间
	 * @param teamIdT
	 * @return
	 * @throws Exception
	 */
	@Cacheable(cacheName = "RegisterTime")
	public List synTimeRegister() throws Exception 
	{   
		Date startDateT = DateUtils.afterNDate(1);
		Date endDateT = DateUtils.afterNDate(7);
		String startDate=DateUtils.CHN_DATE_FORMAT.format(startDateT);
		String endDate=DateUtils.CHN_DATE_FORMAT.format(endDateT);
		startDate=startDate.replace('-','.');
		endDate=endDate.replace('-','.');
		String sql="<DS><SQL><str>select delb ,c.bzdm team_id,c.bzmc team_name,kszjdm doctor_id,b.zgxm doctor_name,derq day from mz_ghde a,comm_zgdm b,mz_bzdyb c where a.kszjdm=b.zgid and a.kszjdm=c.ysdm and derq between  '"+startDate+"' and '"+endDate+"' order by derq  </str></SQL></DS>";
		String ss =invokeFunc(sql);
		Document doc = XMLComm.loadXMLString(ss);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		List listHis = new ArrayList();
		String dayT="";
		if(ObjectCensor.checkListIsNull(list))
		{
			
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
			newMap.put("doctorId", doctorId.trim());
			newMap.put("teamId", teamId);
			newMap.put("post", "");
			newMap.put("week",weekStr);
			newMap.put("day", day);
//			
//			if(!dayT.equals(day))
//			{
//				newMap.put("display","Y");
//				dayT=day;
//			}else
//			{
//				newMap.put("display","N");
//			}
//			
			
			listHis.add(newMap);
		}
		}
		return listHis;
	}
	
	/**
	 * 预约时间
	 * @param teamIdT
	 * @return
	 * @throws Exception
	 */
	public List synDoctorRegister(String doctorIdT) throws Exception 
	{   
		Date startDateT = DateUtils.afterNDate(1);
		Date endDateT = DateUtils.afterNDate(7);
		String startDate=DateUtils.CHN_DATE_FORMAT.format(startDateT);
		String endDate=DateUtils.CHN_DATE_FORMAT.format(endDateT);
		startDate=startDate.replace('-','.');
		endDate=endDate.replace('-','.');
		String sql="<DS><SQL><str>select delb ,c.bzdm team_id,c.bzmc team_name,kszjdm doctor_id,b.zgxm doctor_name,derq day from mz_ghde a,comm_zgdm b,mz_bzdyb c where a.kszjdm=b.zgid and a.kszjdm=c.ysdm and a.kszjdm='"+doctorIdT+"' and derq between  '"+startDate+"' and '"+endDate+"' order by derq  </str></SQL></DS>";
		String ss =invokeFunc(sql);
		Document doc = XMLComm.loadXMLString(ss);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		List listHis = new ArrayList();
		String dayT="";
		if(ObjectCensor.checkListIsNull(list))
		{
			
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
			newMap.put("doctorId", doctorId.trim());
			newMap.put("teamId", teamId);
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
		}
		return listHis;
	}
	
	/**
	 * 预约时间
	 * @param teamIdT
	 * @return
	 * @throws Exception
	 */
	public List synHisRegisterOrderService(String teamIdT,String doctorIdT) throws Exception 
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
		if(ObjectCensor.checkListIsNull(list))
		{
			
		for (int i = 0; i < list.size(); i++)
		{
			Element e = (Element) list.get(i);
			String doctorId=e.getChildText("doctor_id");
			if(ObjectCensor.isStrRegular(doctorIdT))
			{
				if(!doctorId.equals(doctorIdT.trim()))
				{
					continue;
				}
			}
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
			newMap.put("doctorId", doctorId.trim());
			newMap.put("teamId", teamId);
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
		}
		return listHis;
	}
	
	/**
	 * 医生可预约时间
	 * @param doctorIdT
	 * @param userId
	 * @param dateStr
	 * @return
	 * @throws Exception
	 */
	public List getHisDoctorRegister(String doctorIdT,String userId,String dateStr) throws Exception
	{
		String derq=dateStr.replace('-','.');
		String sql="<DS><SQL><str>select c.yszc,a.id,c.bzmc team_name,c.bzdm team_id,kszjdm doctor_id,derq day,swdes-ylrs-swyyrs am_num,xwdes-xwylrs-xwyyrs pm_num,swyyrs+1 am_user_register_num, xwyyrs+1 pm_user_register_num  from mz_ghde a, mz_bzdyb c where derq='"+derq+"' and a.kszjdm=c.ysdm and kszjdm='"+doctorIdT+"' </str></SQL></DS>";
		String ss =invokeFunc(sql);
		Document doc = XMLComm.loadXMLString(ss);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		List listHis = new ArrayList();
		String dayT="";
		
		List userOrderList = digitalHealthDao.qryUserOrderByPhone(userId,dateStr);
		String registerFee="";
	
		String[] dayTypes=new String[]{"am","pm"};
		List doctorList = digitalHealthDao.getDoctorById(doctorIdT.trim());
		int userOrderNumInt=0;
		if(ObjectCensor.checkListIsNull(list))
		{
			Map mapComp = new HashMap();
			mapComp.put("doctor_id", doctorIdT.trim());
			List subList = StringUtil.getSubMapList(doctorList, mapComp);
			Map doctor = null;
			if(ObjectCensor.checkListIsNull(subList))
			{
				doctor=(Map) subList.get(0);
			}
			
			for(int m=0;m<dayTypes.length;m++)
			{
				String dayTypeMark=dayTypes[m];
			
				for (int i = 0; i < list.size(); i++)
				{
					Element e = (Element) list.get(i);
					String doctorId=e.getChildText("doctor_id");
					String teamId=e.getChildText("team_id");
					String teamName=e.getChildText("team_name");
					String yszc=e.getChildText("yszc");
					String registerId=e.getChildText("id");
					String registerNum=e.getChildText(dayTypeMark+"_num");/*可预约号数*/
					String userOrderNum=e.getChildText(dayTypeMark+"_user_register_num");/*用户可预约号*/
					String dayType="";
					String numMax="false";
					if(ObjectCensor.isStrRegular(userOrderNum))
					{
					      userOrderNumInt = Integer.parseInt(userOrderNum);
					}
					if("06".equals(yszc))
					{
						registerFee="9.5";
					}else if("07".equals(yszc))
					{
						registerFee="6.5";
					}else if("08".equals(yszc))
					{
						registerFee="4.5";
					}
					if("am".startsWith(dayTypeMark))
					{
						dayType="上午";
					}else
					{
						dayType="下午";
						userOrderNumInt=1000+userOrderNumInt;
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
					newMap.put("userOrderNum", userOrderNumInt+"");// 预约号码
					newMap.put("doctorId", doctorId.trim());
					newMap.put("teamId", teamId);
					newMap.put("fee", registerFee);
					newMap.put("registerNum", registerNum);
					newMap.put("day",day);
					newMap.put("workTime", " 星期" + weekStr + " " + dayType);
					newMap.put("userFlag", userFlag);
					newMap.put("orderTeamCount",orderTeamCount);
					newMap.put("numMax",numMax);
					newMap.put("introduce",StringUtil.getMapKeyVal(doctor, "introduce"));
					newMap.put("skill",StringUtil.getMapKeyVal(doctor, "skill"));
					newMap.put("photoUrl", StringUtil.getMapKeyVal(doctor, "img_url"));
					newMap.put("post", StringUtil.getMapKeyVal(doctor, "post"));
					listHis.add(newMap);
				}
			}
		}
		return listHis;
	}
	
	/**
	 * 预约成功：更新上午-swyyrs ，下午-xwyyrs 字段，未付款之前
	 * @param id-register_id
	 * @throws Exception
	 */
	public String hisRegisterOrder(String id,String weekTypeT,String orderType,String platformOrderId,String payState) throws Exception
	{
		String weekType="";
		String userRegisterNum="";
		int userRegisterNumInt=0;
		if(ObjectCensor.isStrRegular(id,weekTypeT))
		{
			String week=weekTypeT.substring(weekTypeT.length()-2,weekTypeT.length());
			if("上午".equals(week))
			{
				weekType="swyyrs";
			}
			if("下午".equals(week))
			{
				weekType="xwyyrs";
			}
			StringBuffer sql=new StringBuffer();
			sql.append("<DS>");
			sql.append("<SQL><str>update mz_ghde set "+weekType+"  = "+weekType+"  where id = "+id+"</str></SQL>");
			sql.append("<SQL><str>select "+weekType+" "+orderType+" 1 user_register_num from mz_ghde where id = "+id+"</str></SQL>");
			sql.append("<SQL><str>update mz_ghde set "+weekType+"  = "+weekType+" "+orderType+" 1 where id = "+id+"</str></SQL>");
			sql.append("</DS>");
			log.error(sql);
			String ss =invokeFunc(sql.toString());
			log.error(ss);
			Document doc = XMLComm.loadXMLString(ss);
			Element root = doc.getRootElement();
			List list = root.getChildren();
			
			for (int i = 0; i < list.size(); i++)
			{
				Element e = (Element) list.get(i);
				userRegisterNum=e.getChildText("user_register_num");
			}
			
			/*取消预约 退费*/
			if("-".equals(orderType) && ObjectCensor.isStrRegular(platformOrderId) && "103".equals(payState))
			{
				StringBuffer orderSql=new StringBuffer();
				orderSql.append("<DS>");
				orderSql.append("<SQL><str>update mz_yydj set  qxf  =  'Y'  where yylsh  = '"+platformOrderId+"'</str></SQL>");
				orderSql.append("</DS>");
				invokeFunc(orderSql.toString());
			}
			if(ObjectCensor.isStrRegular(userRegisterNum))
			{
				userRegisterNumInt=Integer.parseInt(userRegisterNum);
			}
			if("下午".equals(week))
			{
				userRegisterNumInt =1000+userRegisterNumInt;
			}
		}
		log.error("userRegisterNumInt:"+userRegisterNumInt);
		return userRegisterNumInt+"";
	
	}
	
	/**
	 * 用户支付后，亚心医院添加记录，修改xh
	 * @param orderT
	 * @throws Exception
	 */
	public void addOrderPay(RegisterOrderT orderT) throws Exception
	{
		
		/*重新分配序号*/
		String delb =orderT.getRegisterId();
//		String orderNum=orderT.getOrderNum();
		String doctorId=orderT.getDoctorId();
		String teamId=orderT.getTeamId();
		String registerTimeT=orderT.getRegisterTime();
		String orderDay=registerTimeT.substring(0,10);
		
		String week=registerTimeT.substring(registerTimeT.length()-2,registerTimeT.length());
		
		String weekType="";
		String xhFlag="";/*预约序号*/
		String startTime="";/*就诊开始时间*/
		
		if("上午".equals(week))
		{
			weekType="am";
			xhFlag="xh";
			startTime=" 8:00:00";
		}
		if("下午".equals(week))
		{
			weekType="pm";
			xhFlag="xwxh";
			startTime=" 14:00:00";
		}
		
		
		StringBuffer sqlxh=new StringBuffer();
		
		sqlxh.append("<DS>");
		sqlxh.append("<SQL><str>update mz_ghde set  "+xhFlag+"   = "+xhFlag+"   where id = "  +delb +"</str></SQL>");
		sqlxh.append("<SQL><str>select  "+xhFlag+"   from mz_ghde   where id = "  +delb +"</str></SQL>");
		sqlxh.append("<SQL><str>update mz_ghde set "+xhFlag+"  = "+xhFlag+"+1    where id = "  +delb +"</str></SQL>");
		sqlxh.append("</DS>");
		
		log.error("sqlxh:"+sqlxh);
		
		String ss = invokeFunc(sqlxh.toString());
		
		Document doc = XMLComm.loadXMLString(ss);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		
		String xhStr="";
		for (int i = 0; i < list.size(); i++)
		{
			 Element e = (Element) list.get(i);
			 	if("am".equals(weekType))
				{
			 		xhStr=e.getChildText("xh");
				}else
				{
					xhStr=e.getChildText("xwxh");
				}
			 
		}
		
		int xh=0; /*专家序号,用户预约号*/
		if(ObjectCensor.isStrRegular(xhStr))
		{
			xh=Integer.parseInt(xhStr);
			log.error("-------------------xh--------------:"+xh);
		}
		
		StringBuffer sqljzsc =new StringBuffer();
		sqljzsc.append("<DS>");
		sqljzsc.append("<SQL><str>select  jzsc,yszc from mz_bzdyb  where bzdm  = '"+ teamId +"' and ysdm ='"+doctorId+"'</str></SQL>");
		sqljzsc.append("</DS>");
		
		String jzscRst =invokeFunc(sqljzsc.toString());
		Document docJzsc = XMLComm.loadXMLString(jzscRst);
		Element rootJzsc  = docJzsc.getRootElement();
		List listJzsc = rootJzsc.getChildren();
		String jzsc="";
		String yszc="";
		for (int n = 0; n < listJzsc.size(); n++)
		{
			Element e = (Element) listJzsc.get(n);
			jzsc=e.getChildText("jzsc");
			yszc=e.getChildText("yszc");
		}
		
		int jzscInt=0;
		if(ObjectCensor.isStrRegular(jzsc))
		{
			jzscInt=Integer.parseInt(jzsc);
		}
		 
		jzscInt=jzscInt*xh;
		
		int afterHour=jzscInt/60;
		int afterMinute=jzscInt%60;
	
		Date registerDate=DateUtils.afterNTime(orderDay,startTime,afterHour, afterMinute);/*计算精确就诊时间*/
		String registerTime=DateUtils.CHN_DATE_TIME_EXTENDED_FORMAT.format(registerDate);
		
		String currentDay=DateUtils.getORA_DATE_FORMAT();
		String sysIdStr=sysId.getId()+"";
//		String id=getOrderId();//currentDay+sysIdStr.substring(sysIdStr.length()-4,sysIdStr.length());
		String sex="1";
		if("男".equals(orderT.getSex()))
		{
			sex="1";
		}
		if("女".equals(orderT.getSex()))
		{
			sex="2";
		}
		String birthDay="";
		String userAddress="无";
		String czydm="";/*操作员*/
		
		if("pm".equals(weekType))
		{
			xh=xh+1000;
		}
		
		StringBuffer sql=new StringBuffer("<DS><SQL><str>");
		sql.append("insert into mz_yydj ");
		sql.append("(yylsh,xm,xb,csrq,yysj,yyysdm,yyysxm,lxdz,dqsj,czydm,lxdh,yynr,xh,sfzh,sff,ghlbdm) values ");
		sql.append("('"+orderT.getOrderId()+"','"+orderT.getUserName()+"','"+sex+"','"+birthDay+"','"+registerTime+"','"+orderT.getDoctorId().trim()+"','"+orderT.getDoctorName()+"','"+userAddress+"',GETDATE(),'"+czydm+"','"+orderT.getUserTelephone()+"','掌上亚心',"+xh+",'"+orderT.getUserNo()+"','10','"+yszc+"')");
		sql.append("</str></SQL></DS>");
		log.error("pay-sql:"+sql);
		invokeFunc(sql.toString());
		orderT.setPayState("102");
		orderT.setPlatformOrderId(orderT.getOrderId());
		orderT.setOrderNum(xh+"");
		hibernateObjectDao.update(orderT);
	}
	
	
	public static void main(String[] args) throws Exception
	{
		String sql="<DS><SQL><str>select a.id,c.bzmc team_name,c.bzdm team_id,kszjdm doctor_id,derq day,swdes,ylrs,swyyrs ,swdes-ylrs-swyyrs am_num,xwdes-xwylrs-xwyyrs pm_num  from mz_ghde a, mz_bzdyb c where derq='2014.09.30' and a.kszjdm=c.ysdm and kszjdm='0629R' </str></SQL></DS>";
//		String sql="<DS><SQL><str>select distinct bzmc  from mz_bzdyb c order by bzmc </str></SQL></DS>";
		
//		String sql="<DS><SQL><str>select  yxf ,delb ,c.bzdm team_id,c.bzmc team_name,kszjdm doctor_id,b.zgxm doctor_name,derq day from mz_ghde a,comm_zgdm b,mz_bzdyb c where a.kszjdm=b.zgid and a.kszjdm=c.ysdm  and derq between  '2014.10.10' and '2014.10.16' order by c.bzdm  </str></SQL></DS>";
		
//		StringBuffer sql=new StringBuffer("<DS>");
//		sql.append("<SQL><str>update mz_ghde set swyyrs  = swyyrs  where id = 500011365</str></SQL>");
//		sql.append("<SQL><str>select xwyyrs   + 1 from mz_ghde where id = 201409285103</str></SQL>");
//		sql.append("<SQL><str>update mz_ghde set xwyyrs   = xwyyrs   - 1 where id = 500011365</str></SQL>");
		
//		sql.append("<SQL><str>select * from mz_ghde where id  = 500011337</str></SQL>");
		
//		sql.append("<SQL><str>insert into mz_yydj(yylsh,xm,xb,csrq,yysj,yyysdm,yyysxm,lxdz,dqsj,czydm,lxdh,yynr,xh,sfzh,sff) values ");
//		sql.append("('201409285000','haha','1','1984.08.01','2014.08.01','9999R','单纯开药','湖北省武汉市水厂一路4号4楼','2014.08.01','1178R','13808652241','开药',2,'422822198407311010','Y')</str></SQL>");
		
//		sql.append("</DS>");
		String sqls="<DS><SQL><str>select top 10 delb ,c.bzdm team_id,c.bzmc team_name,kszjdm doctor_id,b.zgxm doctor_name,derq day from mz_ghde a,comm_zgdm b,mz_bzdyb c where a.kszjdm=b.zgid and a.kszjdm=c.ysdm and a.kszjdm='1405R' and derq > '2014.10.20' order by derq  </str></SQL></DS>";
		String sqla="<DS><SQL><str>select distinct  bzdm team_id ,bzmc team_name from mz_bzdyb order by team_id </str></SQL></DS>";
		String sss ="<DS><SQL><str>update mz_yydj set  qxf  =  'Y'  where yylsh  = '201411286081'  </str></SQL></DS>";
		String ssss="<DS><SQL><str>select  xh+1 xh  from mz_ghde   where id =1000020274  </str></SQL></DS>";
		String sssss="<DS><SQL><str>select  jzsc,yszc from mz_bzdyb  where bzdm  = 'sh02' and ysdm ='1854R'</str></SQL></DS>";
		String sql1="<DS><SQL><str> insert into mz_yydj (yylsh,xm,xb,csrq,yysj,yyysdm,yyysxm,lxdz,dqsj,czydm,lxdh,yynr,xh,sfzh,sff,ghlbdm) values ('2015022711123','袁轲','1','','2015-03-06 14:06:00','9083R','门诊普通号','无',GETDATE(),'','18627902825','掌上亚心',1002,'420102198808084036','10','08')</str></SQL></DS>";
		String sql2="<DS><SQL><str> select * from view_ssqk_app v,mzbrxx m where v.patient_id=m.patient_id and v.patient_id  in ('773637') </str></SQL></DS>";
		String result =new SynHISService().invokeFunc(sql2);
		System.out.println(result);
//		Document doc = XMLComm.loadXMLString(result);
//		Element root = doc.getRootElement();
//		List list = root.getChildren();
//		for (int i = 0; i < list.size(); i++)
//		{
//			Element e = (Element) list.get(i);
//			TeamT teamT = new TeamT();
//			String teamId=e.getChildText("doctor_id").toString();
//			System.out.println(teamId);
//		}
		
	}
}
