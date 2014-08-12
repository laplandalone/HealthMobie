package com.hbgz.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hbgz.dao.DigitalHealthDao;
import com.hbgz.dao.DoctorDao;
import com.hbgz.dao.HibernateObjectDao;
import com.hbgz.dao.UserQustionDao;
import com.hbgz.model.DoctorRegisterT;
import com.hbgz.model.DoctorT;
import com.hbgz.model.HospitalNewsT;
import com.hbgz.model.HospitalUserT;
import com.hbgz.model.RegisterOrderT;
import com.hbgz.model.UserQuestionT;
import com.hbgz.pub.annotation.ServiceType;
import com.hbgz.pub.base.SysDate;
import com.hbgz.pub.cache.CacheManager;
import com.hbgz.pub.exception.JsonException;
import com.hbgz.pub.exception.QryException;
import com.hbgz.pub.resolver.BeanFactoryHelper;
import com.hbgz.pub.sequence.SysId;
import com.hbgz.pub.util.DateUtils;
import com.hbgz.pub.util.FileUtils;
import com.hbgz.pub.util.HttpUtil;
import com.hbgz.pub.util.JsonUtils;
import com.hbgz.pub.util.ObjectCensor;
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
	private HibernateObjectDao hibernateObjectDao;
	
	@Autowired
	private CacheManager cacheManager;
	
	private String projectPath = System.getProperty("user.dir");

	@ServiceType(value = "BUS2001")
	public JSONObject getDoctorList(String expertType, String onLineType, String teamId)
			throws QryException
	{
		List doctorList = digitalHealthDao.getDoctorByType(null, null, teamId);
		JSONObject obj = new JSONObject();
		obj.element("doctors", doctorList);
		return obj;
	}

	@ServiceType(value = "BUS2002")
	public JSONObject getTeamList(String hospitalId, String expertType) throws QryException
	{
		List doctorList = digitalHealthDao.getTeamByType(hospitalId, expertType);
		JSONObject obj = new JSONObject();
		obj.element("teams", doctorList);
		return obj;
	}

	@ServiceType(value = "BUS2003")
	public JSONObject getOrderById(String teamId) throws QryException
	{
		int orderDayLen = 10;
		List orderList = digitalHealthDao.getOrderByTeamId(teamId);
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
	public JSONObject getOrderByDoctorId(String userId,String orderTeamId,String doctorId,String weekStr,String dateStr) throws QryException
	{
//		String userId="10806";
//		String orderTeamId="10";
		List orderList = digitalHealthDao.getOrderByWeekId(weekStr,doctorId);
		List ordertotalList = digitalHealthDao.qryOrderTotalNum(doctorId);
		List userOrderList = digitalHealthDao.qryUserOrderByPhone(userId);
		List list = new ArrayList();
		
		/*用户预约科室总数统计*/
		Map teamComp = new HashMap();
		teamComp.put("teamId", orderTeamId);
		List teamList = StringUtil.getSubMapList(userOrderList, teamComp);
		String orderTeamCount="0";
		if(ObjectCensor.checkListIsNull(teamList))
		{
			orderTeamCount=teamList.size()+"";
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
	public Map getUser(String telephone, String password) throws QryException
	{
		List users = digitalHealthDao.qryUserByTelephone(telephone, password);
		if (ObjectCensor.checkListIsNull(users))
		{
			return (Map) users.get(0);
		}
		return null;
	}

	@ServiceType(value = "BUS2006")
	public boolean addUserRegisgerOrder(String userId, String registerId, String doctorId,
			String doctorName, String orderNum, String orderFee, String registerTime,
			String userName, String userNo, String userTelephone, String sex, String teamId,
			String teamName) throws QryException
	{
		String orderId = sysId.getId() + "";
		if ("0".equals(orderNum) && "0".equals(registerId))// 普通挂号
		{
			String registerTimeT = registerTime.replace(" ", "");
			List list = digitalHealthDao.qryOrderNormalTotal(teamId, registerTimeT);
			if (ObjectCensor.checkListIsNull(list))
			{
				Map orderMap = (Map) list.get(0);
				orderNum = StringUtil.getMapKeyVal(orderMap, "num");
			}
		}
		return digitalHealthDao.addRegisterOrder(orderId, userId, registerId, doctorId, doctorName,
				orderNum, orderFee, registerTime, userName, userNo, userTelephone, sex, teamId,
				teamName);
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

	@ServiceType(value = "BUS2009")
	public List getUserHospitalId(String userId) throws JsonException
	{
		return hibernateObjectDao.qryHospitalUserRelationshipT(userId);
	}

	@ServiceType(value = "BUS20010")
	public List getHospitals(String hospitalId) throws JsonException
	{
		return hibernateObjectDao.qryHospitalTs(hospitalId);
	}

	@ServiceType(value = "BUS20011")
	public String addUser(String user) throws JsonException
	{
		HospitalUserT userT = (HospitalUserT) JsonUtils.toBean(user, HospitalUserT.class);
		List userList = hibernateObjectDao.findByProperty("HospitalUserT", "telephone",
				userT.getTelephone());
		if (ObjectCensor.checkListIsNull(userList))
		{
			return "1";
		} else
		{
			userT.setUserId(sysId.getId() + "");
			hibernateObjectDao.save(userT);
		}
		return "0";
	}

	@ServiceType(value = "BUS20012")
	public boolean updateUser(String user) throws JsonException
	{
		HospitalUserT userT = (HospitalUserT) JsonUtils.toBean(user, HospitalUserT.class);
		hibernateObjectDao.update(userT);
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
	public JSONArray getUserOrderById(String userId) throws QryException
	{
		List list = hibernateObjectDao.qryRegisterOrderTs(userId);
		JSONArray jsonArray = JsonUtils.fromArray(list);
		return jsonArray;
	}

	@ServiceType(value = "BUS20015")
	public JSONArray getQuestionTsByUserId(String userId) throws QryException
	{
		List list = userQustionDao.qryQuestionTsByUserId(userId);
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
		String imgIp = cacheManager.getImgIp(hospitalId);
		
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
		String applicationType = jsonObject.getString("applicationType");
		String applicationVersionCode = jsonObject.getString("applicationVersionCode");
		String deviceType = jsonObject.getString("deviceType");
		// String hospitalId=jsonObject.getString("hospitalId");

		List versionList = digitalHealthDao.getVerstion("101", "VERSION");

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
		String pswType="注册验证码";
		if("set_psw".equals(type))
		{
			pswType="新密码";
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
	    params.put("msg","【海星通技术】尊敬的用户，您的"+pswType+"是"+StringUtil.getMapKeyVal(map, accNbr)+"。健康管家愿成为您健康的好帮手");
		
		// 新用户注册
		if(!ObjectCensor.checkListIsNull(userList) && "NEW_USER".equals(type))
		{
		    String msgRst= HttpUtil.http(url, params, "", "", "");
			return msgRst;
		}else if(ObjectCensor.checkListIsNull(userList) && "NEW_USER".equals(type))//用户已注册
		{
			JSONObject object = new JSONObject();
			
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

	// 查询用户的挂号订单
	public List qryRegisterOrder(String hospitalId, String teamId, String doctorId,
			String startTime, String endTime, String state) throws Exception
	{
		List registerOrderList = digitalHealthDao.qryRegisterOrder(hospitalId, teamId,doctorId, startTime, endTime, state);
		return registerOrderList;
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
				}
				hibernateObjectDao.update(registerOrder);
				flag = true;
			}
		}
		return flag;
	}
	
	public Map getUserWeb(String userName, String password) throws Exception
	{
		if (ObjectCensor.isStrRegular(userName, password))
		{
			List userLst = digitalHealthDao.getHospitalManager(userName, password);

			if (userLst != null && userLst.size() != 0)
			{
				return (Map) userLst.get(0);
			}
		}
		return null;
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
	public JSONArray getDoctorByHospitalId(String hospitalId, String doctorId, String teamId, String doctorName) throws QryException
	{
		if (ObjectCensor.isStrRegular(hospitalId))
		{
			List list = doctorDao.qryDoctorsByHospitalId(hospitalId, doctorId, teamId, doctorName);
			return JSONArray.fromObject(list);
		}
		return null;
	}
	
	public boolean updateHospitalMananger(String hospitalId,String doctorId,String name,String password) throws QryException
	{
		if (ObjectCensor.isStrRegular(hospitalId,doctorId,name,password))
		{
			return doctorDao.updateHospitalMananger(hospitalId, doctorId, name, password);
		}
		return false;
	}
	
	public boolean updateDoctor(String doctorId,String fee) throws QryException
	{
		if (ObjectCensor.isStrRegular(doctorId,fee))
		{
			List<DoctorT> doctorTs=doctorDao.findByProperty("DoctorT", "doctorId", doctorId);
			if(ObjectCensor.checkListIsNull(doctorTs))
			{
				DoctorT doctorT = doctorTs.get(0);
				doctorT.setRegisterFee(fee);
				hibernateObjectDao.update(doctorT);
			}
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

	public JSONArray qryNewsList(String hospitalId, String startTime, String endTime, String newsType, String typeId, String state) throws Exception 
	{
		JSONArray array = new JSONArray();
		if(ObjectCensor.isStrRegular(hospitalId, startTime, endTime))
		{
			List sList = digitalHealthDao.qryNewsList(hospitalId, startTime, endTime, newsType, typeId, state);
			if(ObjectCensor.checkListIsNull(sList))
			{
				array = JsonUtils.fromArray(sList);
			}
		}
		return array;
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
		hospitalNewsT.setNewsContent(newsContent.getBytes());
		hospitalNewsT.setState("00A");
		hospitalNewsT.setNewsImages(imageUrl);
		hibernateObjectDao.save(hospitalNewsT);
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
			String oldNewsId) 
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
			newHospitalNewsT.setNewsContent(newsContent.getBytes());
			newHospitalNewsT.setState(state);
			if(ObjectCensor.isStrRegular(imageUrl))
			{
				newsImageUrl += "," + imageUrl;
			}
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
}
