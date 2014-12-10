package com.hbgz.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hbgz.dao.DigitalHealthDao;
import com.hbgz.dao.DoctorDao;
import com.hbgz.dao.HibernateObjectDao;
import com.hbgz.dao.SaveDB;
import com.hbgz.dao.UserQustionDao;
import com.hbgz.model.DoctorRegisterT;
import com.hbgz.model.HospitalLogT;
import com.hbgz.model.HospitalNewsT;
import com.hbgz.model.HospitalUserT;
import com.hbgz.model.RegisterOrderT;
import com.hbgz.model.UserContactT;
import com.hbgz.model.UserQuestionT;
import com.hbgz.model.UserRelateT;
import com.hbgz.model.WakeT;
import com.hbgz.pub.annotation.ServiceType;
import com.hbgz.pub.base.SysDate;
import com.hbgz.pub.cache.CacheManager;
import com.hbgz.pub.cloudPush.AndroidPushBroadcastMsg;
import com.hbgz.pub.exception.JsonException;
import com.hbgz.pub.exception.QryException;
import com.hbgz.pub.resolver.BeanFactoryHelper;
import com.hbgz.pub.sequence.SysId;
import com.hbgz.pub.util.AlipaySign;
import com.hbgz.pub.util.DateUtils;
import com.hbgz.pub.util.FileUtils;
import com.hbgz.pub.util.HttpUtil;
import com.hbgz.pub.util.JsonUtils;
import com.hbgz.pub.util.ObjectCensor;
import com.hbgz.pub.util.PinyinUtil;
import com.hbgz.pub.util.StringUtil;

@Service(value = "BUS200")
public class DigitalHealthService
{
	@Autowired
	private DigitalHealthDao digitalHealthDao;

	@Autowired
	private SysId sysId;

	@Autowired
	private UserQustionDao userQustionDao;
	
	@Autowired
	private DoctorDao doctorDao;
	
	@Autowired
	private SaveDB saveDB;

	@Autowired
	private HibernateObjectDao hibernateObjectDao;
	
	@Autowired
	private SynHISService synHISService;
	
	@Autowired
	private CacheManager cacheManager;
	
	private String projectPath = System.getProperty("user.dir");

	public Log log = LogFactory.getLog(DigitalHealthService.class);
	@ServiceType(value = "BUS2001")
	public JSONObject getDoctorList(String expertType, String onLineType, String teamId)
			throws QryException
	{
		List doctorList = digitalHealthDao.getDoctorByType(expertType, onLineType, teamId);
		
		if(ObjectCensor.checkListIsNull(doctorList))
		{
			for(int i=0;i<doctorList.size();i++)
			{
				Map map = (Map) doctorList.get(i);
				String name = StringUtil.getMapKeyVal(map,"name");
				String pinyin = PinyinUtil.spell(name);
				map.put("pinYin", pinyin);
			}
		}
		JSONObject obj = new JSONObject();
		obj.element("doctors", doctorList);
		return obj;
	}

	@ServiceType(value = "BUS2002")
	public JSONObject getTeamList(String hospitalId, String expertType,String parentId) throws QryException
	{
		List doctorList = digitalHealthDao.getTeamByType(hospitalId, expertType,parentId);
		if(ObjectCensor.checkListIsNull(doctorList))
		{
			for(int i=0;i<doctorList.size();i++)
			{
				Map map = (Map) doctorList.get(i);
				String name = StringUtil.getMapKeyVal(map,"teamName");
				String pinyin = PinyinUtil.spell(name);
				map.put("pinYin", pinyin);
			}
		}
		JSONObject obj = new JSONObject();
		obj.element("teams", doctorList);
		return obj;
	}

	public JSONObject getOrderHis(String teamIdT,String doctorIdT) throws Exception
	{
		List list = synHISService.synHisRegisterOrderService(teamIdT,doctorIdT);
		JSONObject obj = new JSONObject();
		obj.element("orders", list);
		return obj;
	}
	
	@ServiceType(value = "BUS2003")
	public JSONObject getOrderById(String hospitalId,String teamId,String doctorIdT) throws Exception
	{
		/*亚心医院*/
		if("102".equals(hospitalId))
		{
			return getOrderHis(teamId, doctorIdT);
		}
		
		int orderDayLen = 10;
		List orderList = digitalHealthDao.getOrderByTeamId(hospitalId,teamId);
		List list = new ArrayList();
		Date date = new Date();
		if (ObjectCensor.checkListIsNull(orderList))
		{
			for (int i = 1; i <= orderDayLen; i++)
			{
				Date dateT = DateUtils.afterNDate(i);
				String weekStr = DateUtils.getWeekOfDate(dateT);
				Map mapComp = new HashMap();
				mapComp.put("registerWeek", weekStr);
				List subList = StringUtil.getSubMapList(orderList, mapComp);
				for (int n = 0; n < subList.size(); n++)
				{
					Map subMap = (Map) subList.get(n);
					String doctorName = StringUtil.getMapKeyVal(subMap, "name");
					String teamName = StringUtil.getMapKeyVal(subMap, "teamName");
					String doctorId = StringUtil.getMapKeyVal(subMap, "doctorId");
					String introduce = StringUtil.getMapKeyVal(subMap, "introduce");
					String post = StringUtil.getMapKeyVal(subMap, "post");
					Map newMap = new HashMap();
					
					newMap.put("doctorName", doctorName);
					newMap.put("teamName", teamName);
					newMap.put("doctorId", doctorId);
					newMap.put("teamId", teamId);
					newMap.put("introduce", introduce);
					newMap.put("post", post);
					newMap.put("week",weekStr);
					newMap.put("day", DateUtils.CHN_DATE_FORMAT.format(dateT));
					if(n == 0)
					{
						newMap.put("display","Y");
					}else
					{
						newMap.put("display","N");
					}
					
					if(list.contains(newMap))
					{
						continue;
					}
					
					list.add(newMap);
				}
			}
		}
		JSONObject obj = new JSONObject();
		obj.element("orders", list);
		return obj;
	}

	@ServiceType(value = "BUS2004")
	public JSONObject getOrderByDoctorId(String hospitalId,String userId,String orderTeamId,String doctorId,String weekStr,String dateStr) throws Exception
	{
		/*亚心医院*/
		if("102".equals(hospitalId))
		{
			List list = synHISService.getHisDoctorRegister(doctorId,userId,dateStr);
			JSONObject obj = new JSONObject();
			obj.element("orders", list);
			return obj;
		}
		
		List orderList = digitalHealthDao.getOrderByWeekId(weekStr,doctorId);
		List ordertotalList = digitalHealthDao.qryOrderTotalNum(doctorId);
		List userOrderList = digitalHealthDao.qryUserOrderByPhone(userId,dateStr);
		List list = new ArrayList();
		
		/*用户预约科室总数统计*/
//		Map teamComp = new HashMap(); doctor_register_t
//		teamComp.put("teamId", orderTeamId);
//		List teamList = StringUtil.getSubMapList(userOrderList, teamComp);
		String orderTeamCount="0";
		if(ObjectCensor.checkListIsNull(userOrderList))
		{
			orderTeamCount=userOrderList.size()+"";
		}
		
		/*医生可预约时间*/
		Map mapComp = new HashMap();
		mapComp.put("registerWeek", weekStr);
		List subList = StringUtil.getSubMapList(orderList, mapComp);
		if(ObjectCensor.checkListIsNull(subList))
		{
			for (int n = 0; n < subList.size(); n++)
			{
				Map subMap = (Map) subList.get(n);
				String doctorName = StringUtil.getMapKeyVal(subMap, "name");
				String teamName = StringUtil.getMapKeyVal(subMap, "teamName");
				String teamId = StringUtil.getMapKeyVal(subMap, "teamId");
				String fee = StringUtil.getMapKeyVal(subMap, "registerFee");
				String registerNum = StringUtil.getMapKeyVal(subMap, "registerNum");
				String dayType = StringUtil.getMapKeyVal(subMap, "dayType");
				String registerId = StringUtil.getMapKeyVal(subMap, "registerId");
				String workTime = "星期" + weekStr + dayType;
				String dayWorkTime = dateStr + workTime;
	
				String userOrderNum = "1";
				if(ObjectCensor.checkListIsNull(ordertotalList))
				{
				for (int m = 0; m < ordertotalList.size(); m++)
				{
					Map mapT = (Map) ordertotalList.get(m);
					String idT = StringUtil.getMapKeyVal(mapT, "registerId");
					
					String dayT = StringUtil.getMapKeyVal(mapT, "registerTime");
					dayT = dayT.replaceAll(" ", "");
					if (registerId.equals(idT) && dayWorkTime.equals(dayT))
					{
						userOrderNum = StringUtil.getMapKeyVal(mapT, "orderNum");
						break;
					}
				}
				}
				/*用户是否已预约该时间*/
				String userFlag="N";
				if(ObjectCensor.checkListIsNull(userOrderList))
				{
					for(int i=0;i<userOrderList.size();i++)
					{
						Map userOrder= (Map) userOrderList.get(i);
						String registerIdT=StringUtil.getMapKeyVal(userOrder, "registerId");
						String registerTimeT=StringUtil.getMapKeyVal(userOrder, "registerTime");
						String userIdT=StringUtil.getMapKeyVal(userOrder, "userId");
						String doctorIdT=StringUtil.getMapKeyVal(userOrder, "doctorId");
						if(registerTimeT.equals(dayWorkTime)&&userIdT.equals(userId)&&doctorIdT.equals(doctorId))
						{
							userFlag="Y";
							break;
						}
					}
				}
				
				/*医生挂号是否已满*/
				String numMax="false";
				if(ObjectCensor.isStrRegular(registerNum,userOrderNum))
				{
					int registerNumInt=Integer.parseInt(registerNum);
					int userOrderNumInt=Integer.parseInt(userOrderNum);
					if(userOrderNumInt>registerNumInt)
					{
						numMax="true";
						userOrderNum=registerNum;
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
				newMap.put("day",dateStr);
				newMap.put("workTime", " 星期" + weekStr + " " + dayType);
				newMap.put("userFlag", userFlag);
				newMap.put("orderTeamCount",orderTeamCount);
				newMap.put("numMax",numMax);
				list.add(newMap);
			}
		}
		JSONObject obj = new JSONObject();
		obj.element("orders", list);
		return obj;
	}

	@ServiceType(value = "BUS2005")
	public Map getUser(String telephone, String password,String hospitalId) throws QryException
	{
		List users = digitalHealthDao.qryUserByTelephone(telephone, password);
		if (ObjectCensor.checkListIsNull(users))
		{
			if(ObjectCensor.isStrRegular(telephone,hospitalId))
			{
				HospitalLogT logT = new HospitalLogT();
				logT.setCreateDate(SysDate.getSysDate());
				logT.setState("00A");
				logT.setHospitalId(hospitalId);
				logT.setLogId(sysId.getId()+"");
				logT.setTelephone(telephone);
				hibernateObjectDao.save(logT);
			}
			return (Map) users.get(0);
		}
		return null;
	}

	/**
	 * 用户预约挂号提交
	 * @param userId
	 * @param registerId
	 * @param doctorId
	 * @param doctorName
	 * @param orderNum
	 * @param orderFee
	 * @param registerTime
	 * @param userName
	 * @param userNo
	 * @param userTelephone
	 * @param sex
	 * @param teamId
	 * @param teamName
	 * @return
	 * @throws Exception 
	 */
	@ServiceType(value = "BUS2006")
	public String addUserRegisgerOrder(String hospitalId,String userId, String registerId, String doctorId,
			String doctorName, String orderNum, String orderFee, String registerTime,
			String userName, String userNo, String userTelephone, String sex, String teamId,
			String teamName,String saveFlag,String contactId) throws Exception
	{
		String orderId =DateUtils.getORA_DATE_FORMAT()+sysId.getId() + "";
		
		/*普通挂号,默认取自定义预约号码*/ 
		if ("0".equals(orderNum) && "0".equals(registerId))
		{
			String registerTimeT = registerTime.replace(" ", "");
			List list = digitalHealthDao.qryOrderNormalTotal(teamId, registerTimeT);
			if (ObjectCensor.checkListIsNull(list))
			{
				Map orderMap = (Map) list.get(0);
				orderNum = StringUtil.getMapKeyVal(orderMap, "num");
			}
		}
		if("true".equals(saveFlag))
		{
			/*同步客户资料*/
			List contactList=null;
			
			
			contactList = hibernateObjectDao.qryUserContactT(userId, userNo);
			
			if(!ObjectCensor.checkListIsNull(contactList))
			{
				UserContactT contactT = new UserContactT();
				contactT.setUserId(userId);
				contactT.setContactId(sysId.getId()+"");
				contactT.setContactName(userName);
				contactT.setContactSex(sex);
				contactT.setContactTelephone(userTelephone);
				contactT.setContactNo(userNo);
				contactT.setCreateDate(SysDate.getSysDate());
				contactT.setState("00A");
				hibernateObjectDao.save(contactT);
			}
		}
		
		boolean flag=false;
		
		/*同步修改亚心医院预约号*/
		if("102".equals(hospitalId))
		{
//			/*为空是为普通挂号，需要重新取*/
//			if(!ObjectCensor.isStrRegular(registerId))
//			{
//				List normalList=synHISService.synHisRegisterOrderService(teamId);
//				if(ObjectCensor.checkListIsNull(normalList))
//				{
//					Map normalMap=(Map) normalList.get(0);
//					String docId = StringUtil.getMapKeyVal(normalMap", "doctorId");
//				}
//			}
		   String  yaXinOrderNum=synHISService.hisRegisterOrder(registerId, registerTime,"+","");
		   if(ObjectCensor.isStrRegular(yaXinOrderNum))
		   {
			  flag = digitalHealthDao.addRegisterOrder(hospitalId,orderId, userId, registerId, doctorId, doctorName,
						orderNum, orderFee, registerTime, userName, userNo, userTelephone, sex, teamId,
						teamName);
		   }
		}else
		{
			  flag = digitalHealthDao.addRegisterOrder(hospitalId,orderId, userId, registerId, doctorId, doctorName,
						orderNum, orderFee, registerTime, userName, userNo, userTelephone, sex, teamId,
						teamName);
		}
		
		/*返回order_id：用与支付*/
		if(flag)
		{
			return orderId;
		}
		
		return "";
	}

	@ServiceType(value = "BUS2007")
	public boolean addUserQuestion(String userQuestion) throws Exception
	{
		UserQuestionT questionT = (UserQuestionT) JsonUtils.toBean(userQuestion, UserQuestionT.class);
		String questionId=questionT.getQuestionId();
		questionT.setId(sysId.getId() + "");
	
		questionT.setCreateDate(SysDate.getSysDate());
		if(questionId==null || "".equals(questionId))
		{
			questionT.setQuestionId(sysId.getId() + "");
		}
		if("ans".equals(questionT.getRecordType()) && "102".equals(questionT.getHospitalId()))
		{
			WakeT wakeT = new WakeT();
			wakeT.setWakeId(BigDecimal.valueOf(sysId.getId()));
			wakeT.setCreateDate(SysDate.getSysDate());
			wakeT.setWakeContent(userQuestion);
			wakeT.setWakeDate(SysDate.getSysDate());
			wakeT.setWakeValue("1");
			wakeT.setState("00A");
			wakeT.setWakeType("ques");
			userQustionDao.save(wakeT);
			JSONObject msgJson = new JSONObject();
			msgJson.put("title","掌上亚心");
			msgJson.put("description", "提问回复");
			msgJson.put("msg_type","ques");
			msgJson.put("user_id", questionT.getUserId());
			msgJson.put("custom_param", userQuestion);
			AndroidPushBroadcastMsg.pushMsg("msg", msgJson.toString());
		}
		userQustionDao.save(questionT);
		return true;
	}

	public boolean updateUserQuestion(String questionId,String authType) throws Exception
	{
		List<UserQuestionT> questionTs= userQustionDao.qryQuestionTsByQuestionId(questionId);
		if(ObjectCensor.checkListIsNull(questionTs))
		{
			UserQuestionT questionT = questionTs.get(0);
			questionT.setAuthType(authType);
			userQustionDao.update(questionT);
		}
		return true;
	}

	@ServiceType(value = "BUS2008")
	public JSONArray getUserQuestions(String doctorId) throws JsonException
	{
		List list = userQustionDao.qryQuestionTs(doctorId);
		JSONArray jsonArray = JsonUtils.fromArrayTimestamp(list);
		return jsonArray;
	}
	
	@ServiceType(value = "BUS2009")
	public List getUserHospitalId(String userId) throws JsonException
	{
		return hibernateObjectDao.qryHospitalUserRelationshipT(userId);
	}

	@ServiceType(value = "BUS20010")
	public List getHospitals(String hospitalId) throws JsonException
	{
		List list =hibernateObjectDao.qryHospitalTs();
		JSONArray jsonArray = JsonUtils.fromArray(list);
		return jsonArray;
	}

	@ServiceType(value = "BUS20011")
	public String addUser(String user) throws JsonException
	{
		HospitalUserT userT = (HospitalUserT) JsonUtils.toBean(user, HospitalUserT.class);
		List userList = hibernateObjectDao.findByProperty("HospitalUserT", "telephone",userT.getTelephone());
		String id=sysId.getId() + "";
		if (ObjectCensor.checkListIsNull(userList))
		{
			return "1";
		} else
		{
			userT.setUserId(id);
			hibernateObjectDao.save(userT);
		}
		String userStr=JSONObject.fromObject(userT).toString();
		return userStr;
	}

	@ServiceType(value = "BUS20012")
	public boolean updateUser(String user) throws JsonException
	{
		HospitalUserT userT = (HospitalUserT) JsonUtils.toBean(user, HospitalUserT.class);
		List userList = hibernateObjectDao.findByProperty("HospitalUserT", "userId",userT.getUserId());
		if (ObjectCensor.checkListIsNull(userList))
		{
			HospitalUserT hospitalUserT=(HospitalUserT) userList.get(0);
			userT.setCreateDate(hospitalUserT.getCreateDate());
			hibernateObjectDao.update(userT);
		} 
		
		return true;
	}

	@ServiceType(value = "BUS20013")
	public Map getOrderNormalNum(String teamId, String registerTime) throws QryException
	{
		Map orderMap = new HashMap();
		String registerTimeT = registerTime.replace(" ", "");
		List list = digitalHealthDao.qryOrderNormalTotal(teamId, registerTimeT);
		if (ObjectCensor.checkListIsNull(list))
		{
			orderMap = (Map) list.get(0);
		}
		return orderMap;
	}

	@ServiceType(value = "BUS20014")
	public JSONArray getUserOrderById(String userId,String hospitalId) throws QryException
	{
		List list = hibernateObjectDao.qryRegisterOrderTs(userId,hospitalId);
		JSONArray jsonArray = JsonUtils.fromArray(list);
		return jsonArray;
	}

	@ServiceType(value = "BUS20015")
	public JSONArray getQuestionTsByUserId(String userId,String hospitalId) throws QryException
	{
		List list = userQustionDao.qryQuestionTsByUserId(userId,hospitalId);
		JSONArray jsonArray = JsonUtils.fromArray(list);
		return jsonArray;
	}

	@ServiceType(value = "BUS20016")
	public JSONArray getQuestionTsByIds(String questionId) throws QryException
	{
		List list = userQustionDao.qryQuestionTsByIds(questionId);
		JSONArray jsonArray = JsonUtils.fromArrayTimestamp(list);
		return jsonArray;
	}

	@ServiceType(value = "BUS20017")
	public JSONArray getTeamByHospitalId(String hospitalId) throws QryException
	{
		List list = hibernateObjectDao.qryteamTs(hospitalId);

		JSONArray jsonArray = JsonUtils.fromArray(list);
		return jsonArray;
	}

	@ServiceType(value = "BUS20018")
	public JSONArray getNewsByHospitalId(String hospitalId, String type, String typeId)
			throws QryException, UnsupportedEncodingException
	{
		List<HospitalNewsT> list = hibernateObjectDao.qryHospitalNews(hospitalId, type, typeId);
		
		CacheManager cacheManager = (CacheManager) BeanFactoryHelper.getBean("cacheManager");
		String imgIp = cacheManager.getImgIp("10");
		
		for (HospitalNewsT hospitalNewsT : list)
		{
			byte[] contentT = hospitalNewsT.getNewsContent();
			if (contentT != null)
			{
				hospitalNewsT.setContent(new String(contentT,"gb2312"));
				String newsImg=hospitalNewsT.getNewsImages();
				if (newsImg!=null && !"".equals(newsImg))
				{
					hospitalNewsT.setNewsImages(imgIp+hospitalNewsT.getNewsImages());
				}
				
			}
			hospitalNewsT.setNewsContent(null);
		}
		JSONArray jsonArray = JsonUtils.fromArrayTimestamp(list);
		return jsonArray;
	}

	@ServiceType(value = "BUS20019")
	public JSONObject checkNewVersion(String param) throws QryException
	{
		JSONObject rtnObj = new JSONObject();
		JSONObject jsonObject = JSONObject.fromObject(param);
		String applicationType = StringUtil.getJSONObjectKeyVal(jsonObject,"applicationType");
		String applicationVersionCode = StringUtil.getJSONObjectKeyVal(jsonObject,"applicationVersionCode");
		String deviceType = StringUtil.getJSONObjectKeyVal(jsonObject,"deviceType");
		String hospitalId=StringUtil.getJSONObjectKeyVal(jsonObject,"hospitalId");
		String telephone=StringUtil.getJSONObjectKeyVal(jsonObject,"telephone");
		if(ObjectCensor.isStrRegular(telephone))
		{
			HospitalLogT logT = new HospitalLogT();
			logT.setCreateDate(SysDate.getSysDate());
			logT.setState("00A");
			logT.setHospitalId(hospitalId);
			logT.setLogId(sysId.getId()+"");
			logT.setTelephone(telephone);
			hibernateObjectDao.save(logT);
		}
		if(!ObjectCensor.isStrRegular(hospitalId))
		{
			hospitalId="101";
		}
		List versionList = digitalHealthDao.getVerstion(hospitalId, "VERSION");

		if (ObjectCensor.checkListIsNull(versionList))
		{
			for (int i = 0; i < versionList.size(); i++)
			{
				Map mapT = (Map) versionList.get(i);
				String name = StringUtil.getMapKeyVal(mapT, "configName");
				String val = StringUtil.getMapKeyVal(mapT, "configVal");
				rtnObj.put(name, val);
			}
		}
		return rtnObj;
	}

	@ServiceType(value = "BUS20020")
	public JSONArray getNewsType(String hospitalId, String type) throws QryException
	{
		JSONArray array = new JSONArray();
		CacheManager cacheManager = (CacheManager) BeanFactoryHelper.getBean("cacheManager");
		List list = cacheManager.getNewsType(hospitalId, type, "HOSPITALNEWS");
		if(ObjectCensor.checkListIsNull(list))
		{
			array = JsonUtils.fromArray(list);
		}
		return array;
	}

	@ServiceType(value = "BUS20021")
	public String getAuthCode(String accNbr,String type) throws  Exception
	{
		String pswType="的注册验证码";
		if("set_psw".equals(type))
		{
			pswType="的新密码";
		}else if("edit_phone".equals(type))
		{
			pswType="正在修改手机号码,验证码";
		}
		
		List userList = hibernateObjectDao.findByProperty("HospitalUserT", "telephone",accNbr);
		
		CacheManager cacheManager = (CacheManager) BeanFactoryHelper.getBean("cacheManager");
		Map map =cacheManager.getAuthCode(accNbr);
		String url="http://api.app2e.com/smsBigSend.api.php";
		Map<String, String > params = new HashMap<String, String>();
		params.put("username", "haixing");
		params.put("pwd", "cb6fbeee3deb608f000a8f132531b738");
		params.put("p", accNbr);
		params.put("isUrlEncode", "no");
	    params.put("msg","【海星通技术】尊敬的用户，您"+pswType+"是"+StringUtil.getMapKeyVal(map, accNbr)+"。益健康愿成为您健康的好帮手。");
	    log.error(StringUtil.getMapKeyVal(map, accNbr));
		// 新用户注册,修改手机号码
		if(!ObjectCensor.checkListIsNull(userList) && ("NEW_USER".equals(type) ||"edit_phone".equals(type)))//修改手机号码  
		{
		    String msgRst= HttpUtil.http(url, params, "", "", "");
			return msgRst;
		}else if(ObjectCensor.checkListIsNull(userList) && ("NEW_USER".equals(type) ||"edit_phone".equals(type)) )//用户已注册
		{
			return "\"{\"status\":000}\"";
		}
		else if(ObjectCensor.checkListIsNull(userList) && "set_psw".equals(type))//重置密码
		{
			String msgRst= HttpUtil.http(url, params, "", "", "");
			JSONObject jsonObject = JSONObject.fromObject(msgRst);
			String status = jsonObject.getString("status");
			if("100".equals(status))//发送短信成功
			{
				HospitalUserT user = (HospitalUserT) userList.get(0);
				user.setPassword(StringUtil.getMapKeyVal(map, accNbr));
				hibernateObjectDao.update(user);
			}
			return msgRst;
		}else if(!ObjectCensor.checkListIsNull(userList) && "set_psw".equals(type))//重置密码(用户未注册)
		{
			return "\"{\"status\":001}\"";
		}
		
		return null;
	}

	@ServiceType(value = "BUS20022")
	public String CheckAuthCode(String accNbr,String authCode) throws  Exception
	{
		CacheManager cacheManager = (CacheManager) BeanFactoryHelper.getBean("cacheManager");
		
		Map map =cacheManager.getAuthCode(accNbr);
		
		String authCodeT=StringUtil.getMapKeyVal(map, accNbr);
		
		if(authCode.equals(authCodeT))
		{
			return "true";
		}else
		{
			return "false";
		}
	}

	/**
	 * 更新支付状态：     
	 * order_state,100：未支付；101：已取消；102：已支付;103:退款中;104:已退款；
	 * @param orderId
	 * @param payState
	 * @return
	 * @throws Exception
	 */
	@ServiceType(value = "BUS20023")
	public boolean orderPay(String orderId,String payState) throws  Exception
	{
	
		RegisterOrderT registerOrder=null;
		if (ObjectCensor.isStrRegular(orderId, payState))
		{
			List<RegisterOrderT> sList = hibernateObjectDao.qryRegisterOrderT(orderId);
			if (ObjectCensor.checkListIsNull(sList))
			{
				 registerOrder = sList.get(0);
				
				if("102".equals(payState) && registerOrder!=null)
				{
					 String id = synHISService.addOrderPay(registerOrder);
					 registerOrder.setPayState(payState);
					 registerOrder.setPlatformOrderId(id);
					 hibernateObjectDao.update(registerOrder);
					 
					 List orders = hibernateObjectDao.findByProperty("RegisterOrderT", "orderId",orderId);
						if (ObjectCensor.checkListIsNull(orders))
						{
							RegisterOrderT orderT = (RegisterOrderT) orders.get(0);

							JSONObject msgJson = new JSONObject();
							msgJson.put("title", "掌上亚心");
							msgJson.put("description", "预约提醒");
							msgJson.put("msg_type","order");
							msgJson.put("user_id", orderT.getUserId());
							msgJson.put("custom_param", JsonUtils.fromObject(orderT));
							
							String time = orderT.getRegisterTime().substring(0,10);
							
							Date wakeDate = DateUtils.getSpecifiedDayBefore(time);
							
							WakeT wakeT = new WakeT();
							wakeT.setWakeId(BigDecimal.valueOf(sysId.getId()));
							wakeT.setCreateDate(SysDate.getSysDate());
							wakeT.setWakeContent(msgJson.toString());
							wakeT.setWakeDate(SysDate.getFormatSimpleDate(wakeDate));
							wakeT.setWakeValue("1");
							wakeT.setState("00A");
							wakeT.setWakeType("order");
							hibernateObjectDao.save(wakeT);
//							AndroidPushBroadcastMsg.pushMsg("order", msgJson.toString());
						} 
						
				}
				else 
				
				/*取消预约*/
				if("103".equals(payState) && registerOrder!=null)
				{
					registerOrder.setPayState(payState);
					hibernateObjectDao.update(registerOrder);
					String id=registerOrder.getRegisterId();
					String weekTypeT=registerOrder.getRegisterTime();
					String platformId=registerOrder.getPlatformOrderId();
					synHISService.hisRegisterOrder(id, weekTypeT, "-",platformId);
				}else 
					/*取消预约*/
				if("101".equals(payState) && registerOrder!=null)
				{
					registerOrder.setPayState(payState);
					registerOrder.setPlatformOrderId("0");
					hibernateObjectDao.update(registerOrder);
				}
			}
		}
		return true;
	}
	
	@ServiceType(value = "BUS20024")
	public JSONObject getTaobaoSign(String orderId)
	{
		JSONObject object = new JSONObject();
		List<RegisterOrderT> sList = hibernateObjectDao.qryRegisterOrderT(orderId);
		RegisterOrderT registerOrder=null;
		if (ObjectCensor.checkListIsNull(sList))
		{
			 registerOrder = sList.get(0);
		}
		String sign = AlipaySign.sign("亚心医院-" + "预约", "预约",registerOrder.getOrderFee(),registerOrder.getOrderId());
		object.element("sign", sign);
		return object;
	}
	
	@ServiceType(value = "BUS20025")
	public JSONArray getUserQuestionsByDoctorId(String doctorId, String hospitalId, String startTime, String endTime) throws JsonException, QryException
	{
		List list = userQustionDao.qryQuestionList(doctorId, startTime, endTime);
		JSONArray jsonArray = JSONArray.fromObject(list);
		CacheManager cacheManager = (CacheManager) BeanFactoryHelper.getBean("cacheManager");
		String imgIp = cacheManager.getImgIp(hospitalId);

		for (int i = 0; i < jsonArray.size(); i++) 
		{
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String imgT = jsonObject.getString("imgUrl");
			String[] imgs = imgT.split(",");
			if (imgs != null && imgs.length > 0) 
			{
				for (int n = 0; n < imgs.length; n++) 
				{
					if (ObjectCensor.isStrRegular(imgs[n])) 
					{
						jsonObject.put("imgUrl" + n, imgIp + imgs[n]);
					}
				}
			}
		}
		return jsonArray;
	}

	@ServiceType(value = "BUS20026")
	public JSONArray getDoctorQuestions(String doctorId) throws JsonException
	{
		List list = userQustionDao.qryDoctorQues(doctorId);
		JSONArray jsonArray = JsonUtils.fromArrayTimestamp(list);
		return jsonArray;
	}
	
	@ServiceType(value = "BUS20027")
	public Map getUserWeb(String userName, String password) throws Exception
	{
		if (ObjectCensor.isStrRegular(userName, password))
		{
			List userLst = digitalHealthDao.getHospitalManager(userName, password);

			if (userLst != null && userLst.size() != 0)
			{
				Map map =(Map) userLst.get(0);
				String doctorId=StringUtil.getMapKeyVal(map, "doctor_id");
				List list = synHISService.synDoctorRegister(doctorId);
				String registerNum=list.size()+"";
				map.put("register_num", registerNum);
				List lstQ=getDoctorQuestionsByType(doctorId,"noans");
				if(ObjectCensor.checkListIsNull(lstQ))
				{
					if(lstQ.get(0)==null)
					{
						map.put("ques_num", "0");
					}else
					{
						map.put("ques_num", lstQ.size());
					}
				}
				return map;
			}
		}
		return null;
	}
	
	@ServiceType(value = "BUS20028")
	public JSONArray getDoctorQuestionsByType(String doctorId,String type) throws Exception
	{
		List list = userQustionDao.qryQuestionNoAns(doctorId,type);
		JSONArray jsonArray = JsonUtils.fromArray(list);
		return jsonArray;
	}
	
	@ServiceType(value = "BUS20029")
	public String updateAuth(String id, String authType) 
	{
		String retVal = "false";
		if(ObjectCensor.isStrRegular(id, authType))
		{
			List<UserQuestionT> sList = userQustionDao.qryUserQuestionById(id);
			if(ObjectCensor.checkListIsNull(sList))
			{
				UserQuestionT ques = sList.get(0);
				String newAuthType = "public";
				if("public".equals(authType))
				{
					newAuthType = "private";
				}
				ques.setAuthType(newAuthType);
				userQustionDao.update(ques);
				retVal = "true";
			}
		}
		return retVal;
	}
	
	@ServiceType(value = "BUS20030")
	public String updateAns(String id, String content) 
	{
		String retVal = "false";
		if(ObjectCensor.isStrRegular(id, content))
		{
			List<UserQuestionT> sList = userQustionDao.qryUserQuestionById(id);
			if(ObjectCensor.checkListIsNull(sList))
			{
				UserQuestionT ques = sList.get(0);
				ques.setContent(content);
				userQustionDao.update(ques);
				retVal = "true";
			}
		}
		return retVal;
	}

	@ServiceType(value = "BUS20031")
	public String deleteAns(String id) 
	{
		String retVal = "false";
		if(ObjectCensor.isStrRegular(id))
		{
			List<UserQuestionT> sList = userQustionDao.qryUserQuestionById(id);
			if(ObjectCensor.checkListIsNull(sList))
			{
				UserQuestionT ques = sList.get(0);
				ques.setState("00X");
				userQustionDao.update(ques);
				retVal = "true";
			}
		}
		return retVal;
	}
	
	@ServiceType(value = "BUS20032")
	public JSONObject doctorReigsterById(String doctorId) throws Exception 
	{
		List list = synHISService.synDoctorRegister(doctorId);
		JSONObject obj = new JSONObject();
		obj.element("orders", list);
		return obj;
	}
	//百度云推送消息
	@ServiceType(value = "BUS20033")
	public String sendMsg(String wakeId, String pushUserId, String pushChannelId) throws Exception
	{
		String retVal = "false";
		if(ObjectCensor.isStrRegular(wakeId, pushUserId))
		{
			List sList = digitalHealthDao.qryWakeList(wakeId);
			if(ObjectCensor.checkListIsNull(sList))
			{
				String wakeContent = StringUtil.getMapKeyVal((Map) sList.get(0), "wakeContent");
//				AndroidPushMsg.pushMsg(pushUserId, "msg", wakeContent, pushChannelId);
				retVal = "true";
			}
		}
		return retVal;
	}
	
	@ServiceType(value = "BUS20034")
	public JSONObject getTimeRegister(String doctorName) throws Exception
	{
		List list =synHISService.synTimeRegister();
		List doctorList = new ArrayList();
		String pinYin=PinyinUtil.getPinyin(doctorName);
		boolean firstFlag = PinyinUtil.checkFirstChar(doctorName);
		if(firstFlag)
		{
			pinYin=pinYin.toLowerCase();
		}
		if(ObjectCensor.checkListIsNull(list))
		{
			for(int i=0;i<list.size();i++)
			{
				Map map = (Map) list.get(i);
				String name = StringUtil.getMapKeyVal(map,"doctorName");
				String namePinYin = PinyinUtil.getPinyin(name);
				String pinYinAll=PinyinUtil.getPinyinAll(name)+"";
				if(firstFlag)
				{
					boolean b = true;
					if(pinYin!=null && namePinYin.contains(pinYin))
					{
						doctorList.add(map);
						b=false;
					}
					if(b)
					{
						if(pinYinAll.contains(pinYin))
						{
							doctorList.add(map);
						}
					}
					
				}else
				{
					if(name!=null && name.contains(doctorName))
					{
						doctorList.add(map);
					}
				}
			}
		}
		String dayT="";
		for(int n=0;n<doctorList.size();n++)
		{
			Map map = (Map) doctorList.get(n);
			String day = StringUtil.getMapKeyVal(map,"day");
			if(!dayT.equals(day))
			{
				map.put("display","Y");
				dayT=day;
			}else
			{
				map.put("display","N");
			}
		}
		JSONObject obj = new JSONObject();
		obj.element("orders", doctorList);
		return obj;
	}
	
	@ServiceType(value = "BUS20035")
	public boolean addPatientVisit(String json,String userId,String visitType) throws Exception
	{
		Map map = (Map)JsonUtils.toBean(json, Map.class);
		Iterator it = map.keySet().iterator();    
		List<Map> sList = new ArrayList<Map>();
		Long visitId = sysId.getId();
		while(it.hasNext())
		{    
			String key = it.next().toString();
			String value = StringUtil.getMapKeyVal(map, key);
			if(ObjectCensor.isStrRegular(value))
			{
				Map paramMap = new HashMap();
				paramMap.put("visit_id", visitId);
				paramMap.put("visit_type", visitType);
				paramMap.put("code_flag", key);
				paramMap.put("code_val", value);
				sList.add(paramMap);
			}
		}   
		List userList = hibernateObjectDao.findByProperty("HospitalUserT", "userId",userId);
		if(ObjectCensor.checkListIsNull(userList))
		{
			HospitalUserT hospitalUserT = (HospitalUserT) userList.get(0);
			Map visitMap = new HashMap();
			visitMap.put("visit_id", visitId);
			visitMap.put("visit_name", hospitalUserT.getUserName());
			visitMap.put("visit_type", visitType);
			visitMap.put("patient_id", hospitalUserT.getUserId());
			visitMap.put("card_id","");
			saveDB.insertRecord("patient_visit_t", visitMap);
			if(ObjectCensor.checkListIsNull(sList))
			{
				saveDB.insertRecord("patient_visit_detail_t", sList);
			}
		}
		
		return true;
	}
	

	@ServiceType(value = "BUS20036")
	public String addUserContact(String user) throws   JsonException
	{
		UserContactT contactT = (UserContactT) JsonUtils.toBean(user, UserContactT.class);
		contactT.setContactId(sysId.getId()+"");
		contactT.setState("00A");
		contactT.setCreateDate(new Date());
		String no=contactT.getContactNo();
		List userList = hibernateObjectDao.qryUserContactT(contactT.getUserId(), no);
		if(!ObjectCensor.checkListIsNull(userList))
		{
			hibernateObjectDao.save(contactT);
			return "0";
		}else
		{
			return "1";
		}
	}
	
	@ServiceType(value = "BUS20037")
	public JSONArray getUserContact(String userId) throws JsonException
	{
		List list = hibernateObjectDao.qryUserContactT(userId);
		JSONArray jsonArray = JsonUtils.fromArray(list);
		return jsonArray;
	}
	
	@ServiceType(value = "BUS20038")
	public boolean updateUserContact(String user) throws   JsonException
	{
		UserContactT contactT = (UserContactT) JsonUtils.toBean(user, UserContactT.class);
		contactT.setCreateDate(new Date());
		hibernateObjectDao.update(contactT);
		return true;
	}
	
	@ServiceType(value = "BUS20039")
	public String  addUserRelate(String userId,String userPhone,String userName,String userPass,String telephone,String password) throws QryException
	{
 		List users = digitalHealthDao.qryUserByTelephone(telephone, password);
		if (ObjectCensor.checkListIsNull(users))
		{
			List relateUser = hibernateObjectDao.qryUserRelateTByPhone(userId, telephone);
			if (!ObjectCensor.checkListIsNull(relateUser))
			{
				Map relateUserMap = (Map) users.get(0);
				
				UserRelateT relateT = new UserRelateT();
				relateT.setRelateId(sysId.getId()+"");
				relateT.setUserId(userId);
				relateT.setRelatePhone(telephone);
				relateT.setRelatePass(password);
				relateT.setState("00A");
				relateT.setRelateName(StringUtil.getMapKeyVal(relateUserMap, "userName"));
				relateT.setCreateDate(SysDate.getSysDate());
				hibernateObjectDao.save(relateT);
				
				
				String relateUserId=StringUtil.getMapKeyVal(relateUserMap, "userId");
				List relateUserT = hibernateObjectDao.qryUserRelateTByPhone(relateUserId, userPhone);
				if (!ObjectCensor.checkListIsNull(relateUserT))
				{
					UserRelateT userRelateT = new UserRelateT();
					userRelateT.setRelateId(sysId.getId()+"");
					userRelateT.setUserId(relateUserId);
					userRelateT.setRelatePhone(userPhone);
					userRelateT.setRelatePass(userPass);
					userRelateT.setState("00A");
					userRelateT.setRelateName(userName);
					userRelateT.setCreateDate(SysDate.getSysDate());
					hibernateObjectDao.save(userRelateT);
				}
			}else
			{
				return "1";/*关联账号已存在*/
			}
		}else
		{
			return "0";/*关联账号不存在*/
		}
		return "2";/*关联账号成功*/
	}
	
	
	@ServiceType(value = "BUS20040")
	public JSONArray getUserRelate(String userId) throws JsonException
	{
		List list = hibernateObjectDao.qryUserRelateT(userId);
		JSONArray jsonArray = JsonUtils.fromArray(list);
		return jsonArray;
	}
	
	@ServiceType(value = "BUS20041")
	public boolean deleteUserRelate(String relateId,String usrPhone) throws JsonException, QryException
	{
		List relateUsers = hibernateObjectDao.findByProperty("UserRelateT", "relateId", relateId);

		if (ObjectCensor.checkListIsNull(relateUsers))
		{
			UserRelateT relateT = (UserRelateT) relateUsers.get(0);
			
			List users = digitalHealthDao.qryUserByTelephone(relateT.getRelatePhone(), relateT.getRelatePass());
			if (ObjectCensor.checkListIsNull(users))
			{
				Map  relateUuser =(Map) users.get(0);
				String userId=StringUtil.getMapKeyVal(relateUuser, "userId");
				List relateUserTs = hibernateObjectDao.qryUserRelateTByPhone(userId, usrPhone);
				if (ObjectCensor.checkListIsNull(relateUserTs))
				{
					UserRelateT relateUserT = (UserRelateT) relateUserTs.get(0);
					hibernateObjectDao.delete(relateUserT);
				}
			}
			hibernateObjectDao.delete(relateT);
			
		}
		return true;
	}
	
	@ServiceType(value = "BUS20042")
	public String addQHUserRegisgerOrder(String hospitalId,String userId, String registerId, String doctorId,
			String doctorName, String orderNum, String orderFee, String registerTime,
			String userName, String userNo, String userTelephone, String sex, String teamId,
			String teamName,String detailTime) throws Exception
	{
		String orderId = sysId.getId() + "";
		
		/*普通挂号,默认取自定义预约号码*/ 
		if ("0".equals(orderNum) && "0".equals(registerId))
		{
			String registerTimeT = registerTime.replace(" ", "");
			List list = digitalHealthDao.qryOrderNormalTotal(teamId, registerTimeT);
			if (ObjectCensor.checkListIsNull(list))
			{
				Map orderMap = (Map) list.get(0);
				orderNum = StringUtil.getMapKeyVal(orderMap, "num");
			}
		}
		
		boolean flag= digitalHealthDao.addQHRegisterOrder(hospitalId,orderId, userId, registerId, doctorId, doctorName,
				orderNum, orderFee, registerTime, userName, userNo, userTelephone, sex, teamId,
				teamName,detailTime);
		
		return orderId;
	}
	
	@ServiceType(value = "BUS20043")
	public boolean updateUserPhone(String userId,String phone) throws JsonException
	{
		List userList = hibernateObjectDao.findByProperty("HospitalUserT", "userId",userId);
		if(ObjectCensor.checkListIsNull(userList))
		{
			HospitalUserT hospitalUserT = (HospitalUserT) userList.get(0);
			hospitalUserT.setTelephone(phone);
			hibernateObjectDao.update(hospitalUserT);
		}
		return true;
	}
	
	@ServiceType(value = "BUS20044")
	public boolean deleteRegisterOrder(String orderId) throws JsonException
	{
		List orderList = hibernateObjectDao.findByProperty("RegisterOrderT", "orderId",orderId);
		if(ObjectCensor.checkListIsNull(orderList))
		{
			RegisterOrderT registerOrderT = (RegisterOrderT) orderList.get(0);
//			hibernateObjectDao.delete(registerOrderT);
		}
		return true;
	}
	// 查询用户的挂号订单
	public JSONObject qryRegisterOrder(int pageNum, int pageSize, String hospitalId, String teamId, String startTime, String endTime, String state) throws Exception
	{
		JSONObject obj = new JSONObject();
		if(ObjectCensor.isStrRegular(hospitalId))
		{
			int count = 0;
			List registerOrderList = digitalHealthDao.qryRegisterOrderList(pageNum, pageSize, hospitalId, teamId, startTime, endTime, state);
			if(ObjectCensor.checkListIsNull(registerOrderList))
			{
				obj.element("registerOrderList", registerOrderList);
				count = digitalHealthDao.qryRegisterOrderCount(hospitalId, teamId, startTime, endTime, state);
			}
			obj.element("count", count);
		}
		return obj;
	}

	// 预约或取消挂号订单
	public boolean appointmentOrinvalidOrder(String orderId, String optionFlag)
	{
		boolean flag = false;
		if (ObjectCensor.isStrRegular(orderId, optionFlag))
		{
			List<RegisterOrderT> sList = hibernateObjectDao.qryRegisterOrderT(orderId);
			if (ObjectCensor.checkListIsNull(sList))
			{
				RegisterOrderT registerOrder = sList.get(0);
				if ("appointment".equalsIgnoreCase(optionFlag))
				{
					registerOrder.setOrderState("00A");
				} else if ("invalid".equalsIgnoreCase(optionFlag))
				{
					registerOrder.setOrderState("00X");
				}else if ("refund".equalsIgnoreCase(optionFlag))
				{
					registerOrder.setPayState("104");
				}
				hibernateObjectDao.update(registerOrder);
				flag = true;
			}
		}
		return flag;
	}
	
	
	public Map getDoctor(String doctorId) throws Exception
	{
		if (ObjectCensor.isStrRegular(doctorId))
		{
			List doctorLst = digitalHealthDao.getDoctorById(doctorId);

			if (doctorLst != null && doctorLst.size() != 0)
			{
				return (Map) doctorLst.get(0);
			}
		}
		return null;
	}
	
	public List getDoctorRegister(String doctorId) throws Exception
	{
		if (ObjectCensor.isStrRegular(doctorId))
		{
			List registerLst = digitalHealthDao.getDoctorRegister(doctorId);
			return registerLst;
		}
		return null;
	}
	
	/**
	 * 获取医生列表
	 * @param hospitalId
	 * @return
	 * @throws QryException
	 */
	public JSONObject getDoctorByHospitalId(int pageNum, int pageSize, String hospitalId, String doctorId, String teamId, String doctorName) throws QryException
	{
		JSONObject obj = new JSONObject();
		if (ObjectCensor.isStrRegular(hospitalId))
		{
			int count = 0;
			List sList = doctorDao.qryDoctorsByHospitalId(pageNum, pageSize, hospitalId, doctorId, teamId, doctorName);
			if(ObjectCensor.checkListIsNull(sList))
			{
				obj.element("doctorList", sList);
				count = doctorDao.qryDoctorCount(hospitalId, doctorId, teamId, doctorName);
			}
			obj.element("count", count);
		}
		return obj;
	}
	
	public boolean updateHospitalMananger(String hospitalId,String doctorId,String name,String password) throws QryException
	{
		if (ObjectCensor.isStrRegular(hospitalId,doctorId,name,password))
		{
			return doctorDao.updateHospitalMananger(hospitalId, doctorId, name, password);
		}
		return false;
	}
	
	public boolean deleteHospitalMananger(String hospitalId, String doctorId)
	{
		if(ObjectCensor.isStrRegular(hospitalId, doctorId))
		{
			return doctorDao.deleteHospitalMananger(hospitalId, doctorId);
		}
		return false;
	}
	
	public boolean updateDoctor(String hospitalId,String doctorId,String fee,String introduce,String skill, String post, String time, String address, String telephone, String sex) throws QryException
	{
		if (ObjectCensor.isStrRegular(hospitalId,doctorId))
		{
			return doctorDao.updateDoctor(hospitalId, doctorId, fee, introduce, skill, post, time, address, telephone, sex);
		}
		return false;
	}
	
	public boolean deleteDoctor(String hospitalId, String doctorId)
	{
		if (ObjectCensor.isStrRegular(hospitalId,doctorId))
		{
			return doctorDao.deleteDoctor(hospitalId, doctorId);
		}
		return false;
	}
	
	public boolean updateDoctorRegisterTimes(String registerTimes,String doctorId) throws QryException, JsonException
	{
		List<DoctorRegisterT> list = JsonUtils.toArray(registerTimes, DoctorRegisterT.class);
		
		doctorDao.delete("DoctorRegisterT", "doctorId", doctorId);
		
		for (DoctorRegisterT doctorRegisterT:list)
		{
			doctorRegisterT.setRegisterId(sysId.getId()+"");
			doctorRegisterT.setCaeateDate(new Date());
			doctorDao.save(doctorRegisterT);
		}
		return true;
	}
	public String addNewsFile(MultipartHttpServletRequest request) throws Exception
	{
		String newsTypeId= request.getParameter("newsTypeId");
		String newsType = request.getParameter("newsType");
		String newsTitle= request.getParameter("newsTitle");
		String hospitalId=request.getParameter("hospitalId");
	
		Map<String, MultipartFile> map = request.getFileMap();
		boolean flag=false;
		for (Map.Entry<String, MultipartFile> entity : map.entrySet())
		{
			MultipartFile partFile = entity.getValue();
			String fileName = partFile.getOriginalFilename();
		    if(fileName.endsWith(".txt"))
		    {
			File localFile = new File(fileName);
			partFile.transferTo(localFile);
			InputStream is = new FileInputStream(localFile);
			HospitalNewsT hospitalNewsT = new HospitalNewsT();
			hospitalNewsT.setCreateDate(SysDate.getSysDate());
			hospitalNewsT.setHospitalId("101");
			hospitalNewsT.setNewsId("11111");
			hospitalNewsT.setNewsTitle(newsTitle);
			hospitalNewsT.setState("00A");
			hospitalNewsT.setTypeId(newsTypeId);
			hospitalNewsT.setNewsType(newsType);
			int length=is.available();
			byte[] buffer=new byte[length];
			is.read(buffer);
			hospitalNewsT.setNewsContent(buffer);
			hibernateObjectDao.save(hospitalNewsT);		
		    }
		}
		return "";
	}

	public List qryDoctorList(String hospitalId, String teamId) throws QryException 
	{
		return doctorDao.qryDoctorList(hospitalId, teamId);
	}

	public JSONArray qryQuesList(String doctorId, String questionId) throws QryException 
	{
		JSONArray array = new JSONArray();
		if(ObjectCensor.isStrRegular(doctorId, questionId))
		{
			List sList = userQustionDao.qryQuesList(doctorId, questionId);
			array = JSONArray.fromObject(sList);
		}
		return array;
	}

	public JSONObject qryNewsList(int pageNum, int pageSize, String hospitalId, String startTime, String endTime, String newsType, String typeId, String state) throws Exception 
	{
		JSONObject obj = new JSONObject();
		if(ObjectCensor.isStrRegular(hospitalId, startTime, endTime))
		{
			int count = 0;
			List sList = digitalHealthDao.qryNewsList(pageNum, pageSize, hospitalId, startTime, endTime, newsType, typeId, state);
			if(ObjectCensor.checkListIsNull(sList))
			{
				obj.element("newsList", sList);
				count = digitalHealthDao.qryNewsCount(hospitalId, startTime, endTime, newsType, typeId, state);
			}
			obj.element("count", count);
		}
		return obj;
	}

	public JSONObject getNewsById(String newsId, String hospitalId) throws Exception 
	{
		JSONObject obj = new JSONObject();
		if(ObjectCensor.isStrRegular(newsId))
		{
			List<HospitalNewsT> sList = hibernateObjectDao.getNewsById(hospitalId, newsId);
			if(ObjectCensor.checkListIsNull(sList))
			{
				CacheManager cacheManager = (CacheManager) BeanFactoryHelper.getBean("cacheManager");
				String imgIp = cacheManager.getImgIp(hospitalId);
				HospitalNewsT hospitalNewsT = sList.get(0);
				String effDate = "";
				String expDate = "";
				if(hospitalNewsT.getEffDate() != null)
				{
					effDate = SysDate.getFormatDate(hospitalNewsT.getEffDate(), "yyyy-MM-dd HH:mm:ss");
				}
				if(hospitalNewsT.getExpDate() != null)
				{
					expDate = SysDate.getFormatDate(hospitalNewsT.getExpDate(), "yyyy-MM-dd HH:mm:ss");
				}
				byte[] contentT = hospitalNewsT.getNewsContent();
				if (contentT != null)
				{
					hospitalNewsT.setContent(new String(contentT, "gb2312"));
					String newsImg = hospitalNewsT.getNewsImages();
					if(ObjectCensor.isStrRegular(newsImg))
					{
						hospitalNewsT.setNewsImages(imgIp + hospitalNewsT.getNewsImages());
					}
				}
				else
				{
					hospitalNewsT.setNewsContent(null);
				}
				obj = JsonUtils.fromObject(hospitalNewsT);
				obj.remove("newsContent");
				obj.remove("effDate");
				obj.remove("expDate");
				obj.element("effDate", effDate);
				obj.element("expDate", expDate);
			}
		}
		return obj;
	}

	public boolean addNewsType(String hospitalId, String newsTypeId, String newsTypeName, String configType) 
	{
		String configId = sysId.getId() + "";
		boolean flag = digitalHealthDao.addNewsType(hospitalId, configId, newsTypeId, newsTypeName, configType);
		if(flag)
		{
			cacheManager.delConfigCache();
		}
		return flag;
	}

	public String addNews(String hospitalId, String newsId, String newsType, String typeId, String newsTitle, String effDate, String expDate, String newsContent, String imageUrl) throws Exception 
	{
		if(!ObjectCensor.isStrRegular(newsId))
		{
			newsId = sysId.getId() + "";
		}
		HospitalNewsT hospitalNewsT = new HospitalNewsT();
		hospitalNewsT.setHospitalId(hospitalId);
		hospitalNewsT.setNewsId(newsId);
		hospitalNewsT.setNewsType(newsType);
		hospitalNewsT.setTypeId(typeId);
		hospitalNewsT.setCreateDate(SysDate.getSysDate());
		hospitalNewsT.setNewsTitle(newsTitle);
		hospitalNewsT.setEffDate(SysDate.getSysDate(effDate, "yyyy-MM-dd"));
		hospitalNewsT.setExpDate(SysDate.getSysDate(expDate, "yyyy-MM-dd"));
		hospitalNewsT.setNewsContent(newsContent.getBytes("GBK"));
		hospitalNewsT.setState("00A");
		hospitalNewsT.setNewsImages(imageUrl);
		hibernateObjectDao.save(hospitalNewsT);
		
		if("102".equals(hospitalId))
		{
			CacheManager cacheManager = (CacheManager) BeanFactoryHelper.getBean("cacheManager");
			String imgIp = cacheManager.getImgIp("10");
			String newsImg = hospitalNewsT.getNewsImages();
			if (newsImg!=null && !"".equals(newsImg))
			{
				hospitalNewsT.setNewsImages(imgIp+hospitalNewsT.getNewsImages());
			}
			String typeName=cacheManager.getNewsTypeById(hospitalId, typeId);
			hospitalNewsT.setContent(newsContent);
			hospitalNewsT.setNewsContent(null);
			hospitalNewsT.setTypeName(typeName);
			JSONObject msgJson = new JSONObject();
			msgJson.put("title","掌上亚心");
			msgJson.put("description",newsTitle);
			msgJson.put("msg_type","news");
			msgJson.put("user_id", "");
			msgJson.put("custom_param", JsonUtils.fromObjectTimestamp(hospitalNewsT));
			AndroidPushBroadcastMsg.pushMsg("msg", msgJson.toString());
		}
		return "true";
	}

	public String uploadFile(MultipartHttpServletRequest request, String newsType) throws Exception 
	{
		JSONObject obj = new JSONObject();
		Map<String, MultipartFile> map = request.getFileMap();
		if(!ObjectCensor.checkObjectIsNull(map))
		{
			HttpSession session = request.getSession();
			String hospitalId = (String)session.getAttribute("hospitalId");
			String uploadType = newsType + "_IMG_PATH";
			String path = cacheManager.getUplodPathByType(hospitalId, uploadType);
			String newsId = sysId.getId() + "";
			obj.element("newsId", newsId);
			String imageUrl = "";
			for (Map.Entry<String, MultipartFile> entity : map.entrySet())
			{
				MultipartFile partFile = entity.getValue();
				String fileName = partFile.getOriginalFilename();
				File localFile = new File(fileName);
				partFile.transferTo(localFile);
				InputStream is = new FileInputStream(localFile);
				fileName = newsId + fileName.substring(fileName.lastIndexOf("."), fileName.length());
				String fullPath = projectPath + path;
				double fileSize = FileUtils.create(fullPath, fileName, is);
				imageUrl += path + fileName + ",";
				is.close();
			}
			imageUrl = imageUrl.substring(0, imageUrl.length() - 1);
			obj.element("imageUrl", imageUrl);
		}
		return obj.toString();
	}

	public String updateNews(String hospitalId, String newsId, String newsType,
			String typeId, String newsTitle, String effDate, String expDate,
			String newsContent, String newsImageUrl, String state,
			String oldNewsId) throws UnsupportedEncodingException, QryException 
	{
		List<HospitalNewsT> sList = hibernateObjectDao.getNewsById(hospitalId, oldNewsId);
		if(ObjectCensor.checkListIsNull(sList))
		{
			HospitalNewsT hospitalNewsT = sList.get(0);
			String imageUrl = hospitalNewsT.getNewsImages();
			
			hibernateObjectDao.delete(hospitalNewsT);
			
			if(!ObjectCensor.isStrRegular(newsId))
			{
				newsId = sysId.getId() + "";
			}
			HospitalNewsT newHospitalNewsT = new HospitalNewsT();
			newHospitalNewsT.setHospitalId(hospitalId);
			newHospitalNewsT.setNewsId(newsId);
			newHospitalNewsT.setNewsType(newsType);
			newHospitalNewsT.setTypeId(typeId);
			newHospitalNewsT.setCreateDate(SysDate.getSysDate());
			newHospitalNewsT.setNewsTitle(newsTitle);
			newHospitalNewsT.setEffDate(SysDate.getSysDate(effDate, "yyyy-MM-dd"));
			newHospitalNewsT.setExpDate(SysDate.getSysDate(expDate, "yyyy-MM-dd"));
			newHospitalNewsT.setNewsContent(newsContent.getBytes("GBK"));
			newHospitalNewsT.setState(state);
//			if(ObjectCensor.isStrRegular(imageUrl))
//			{
//				newsImageUrl += "," + imageUrl;
//			}
			newHospitalNewsT.setNewsImages(newsImageUrl);
			hibernateObjectDao.save(newHospitalNewsT);
//			if("00X".equals(state) && !state.equals(hospitalNewsT.getState()))
//			{
//				hospitalNewsT.setState(state);
//				hospitalNewsT.setNewsTitle(newsTitle);
//				hospitalNewsT.setNewsType(newsType);
//				hospitalNewsT.setTypeId(typeId);
//				hospitalNewsT.setEffDate(SysDate.getSysDate(effDate, "yyyy-MM-dd"));
//				hospitalNewsT.setExpDate(SysDate.getSysDate(expDate, "yyyy-MM-dd"));
//				hospitalNewsT.setNewsContent(newsContent.getBytes());
//				if(ObjectCensor.isStrRegular(imageUrl))
//				{
//					newsImageUrl += "," + imageUrl;
//				}
//				hospitalNewsT.setNewsImages(newsImageUrl);
//				hibernateObjectDao.update(hospitalNewsT);
//			}
//			else
//			{
//				
			
//			}
			
		}

		return "true";
	}
	
	public String deleteNews(String hospitalId, String newsId)
	{
		List<HospitalNewsT> sList = hibernateObjectDao.getNewsById(hospitalId, newsId);
		if(ObjectCensor.checkListIsNull(sList))
		{
			HospitalNewsT hospitalNewsT = sList.get(0);
			hibernateObjectDao.delete(hospitalNewsT);
		}
		return "true";
	}

	public JSONArray qryTeamList(String hospitalId) throws Exception 
	{
		JSONArray array = new JSONArray();
		if(ObjectCensor.isStrRegular(hospitalId))
		{
			List sList = digitalHealthDao.qryTeamList(hospitalId);
			if(ObjectCensor.checkListIsNull(sList))
			{
				array = JSONArray.fromObject(sList);
			}
		}
		return array;
	}

	public JSONObject qryOnlineDortorList(int pageNum, int pageSize, String hospitalId, String teamId, String skill, String doctorName) throws Exception 
	{
		JSONObject obj = new JSONObject();
		if(ObjectCensor.isStrRegular(hospitalId))
		{
			List sList = digitalHealthDao.qryOnlineDortorList(pageNum, pageSize, hospitalId, teamId, skill, doctorName);
			int count = 0;
			if(ObjectCensor.checkListIsNull(sList))
			{
				obj.element("onlineDortorList", sList);
				count = digitalHealthDao.qryOnlineDortorCount(hospitalId, teamId, skill, doctorName);
			}
			obj.element("count", count);
		}
		return obj;
	}

	public boolean updateOnlineState(String doctorId, String operatorType) 
	{
		return digitalHealthDao.updateOnlineState(doctorId, operatorType);
	}

	public boolean addWake(JSONObject obj) 
	{
		Long wakeId = sysId.getId();
		return digitalHealthDao.addWake(obj, wakeId);
	}

	public JSONArray qryWakeTypeList(String hospitalId) throws Exception 
	{
		JSONArray array = new JSONArray();
		CacheManager cacheManager = (CacheManager) BeanFactoryHelper.getBean("cacheManager");
		List list = cacheManager.getWakeType(hospitalId, "WAKE_TYPE");
		if(ObjectCensor.checkListIsNull(list))
		{
			array = JsonUtils.fromArray(list);
		}
		return array;
	}

	public boolean addDoctor(JSONObject obj) throws Exception 
	{
		Long doctorId = sysId.getId();
		obj.element("doctor_id", doctorId);
		Map map = (Map) JsonUtils.toBean(obj.toString(), Map.class);
		saveDB.insertRecord("doctor_t", map);
		return false;
	}

	public JSONObject qryOnLineDoctorQuesList(int pageNum, int pageSize, String hospitalId, String teamId, String doctorName) throws Exception 
	{
		JSONObject obj = new JSONObject();
		if(ObjectCensor.isStrRegular(hospitalId))
		{
			int count = 0;
			List sList = digitalHealthDao.qryOnLineDoctorQuesList(pageNum, pageSize, hospitalId, teamId, doctorName);
			if(ObjectCensor.checkListIsNull(sList))
			{
				obj.element("onLineDoctorQuesList", sList);
				count = digitalHealthDao.qryOnLineDoctorQuesCount(hospitalId, teamId, doctorName);
			}
			obj.element("count", count);
		}
		return obj;
	}
	
	public JSONObject qryUserQuestionsList(int pageNum, int pageSize, String doctorId, String hospitalId, String startTime, String endTime) throws JsonException, QryException
	{
		JSONObject obj = new JSONObject();
		if(ObjectCensor.isStrRegular(hospitalId, doctorId))
		{
			int count = 0;
			List sList = userQustionDao.qryUserQuestionList(pageNum, pageSize, doctorId, startTime, endTime);
			if(ObjectCensor.checkListIsNull(sList))
			{
				JSONArray array = JSONArray.fromObject(sList);
				CacheManager cacheManager = (CacheManager) BeanFactoryHelper.getBean("cacheManager");
				String imgIp = cacheManager.getImgIp(hospitalId);
				for (int i = 0; i < array.size(); i++) 
				{
					JSONObject jsonObject = array.getJSONObject(i);
					String imgT = jsonObject.getString("imgUrl");
					String[] imgs = imgT.split(",");
					if (imgs != null && imgs.length > 0) 
					{
						for (int n = 0; n < imgs.length; n++) 
						{
							if (ObjectCensor.isStrRegular(imgs[n])) 
							{
								jsonObject.put("imgUrl" + n, imgIp + imgs[n]);
							}
						}
					}
				}
				obj.element("questionList", array);
				count = userQustionDao.qryUserQuestionCount(doctorId, startTime, endTime);
			}
			obj.element("count", count);
		}
		return obj;
	}
}
