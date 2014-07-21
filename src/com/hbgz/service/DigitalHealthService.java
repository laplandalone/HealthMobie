package com.hbgz.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbgz.dao.DigitalHealthDao;
import com.hbgz.dao.HibernateObjectDao;
import com.hbgz.dao.UserQustionDao;
import com.hbgz.model.HospitalNewsT;
import com.hbgz.model.HospitalUserT;
import com.hbgz.model.RegisterOrderT;
import com.hbgz.model.UserQuestionT;
import com.hbgz.pub.annotation.ServiceType;
import com.hbgz.pub.cache.CacheManager;
import com.hbgz.pub.exception.JsonException;
import com.hbgz.pub.exception.QryException;
import com.hbgz.pub.resolver.BeanFactoryHelper;
import com.hbgz.pub.sequence.SysId;
import com.hbgz.pub.util.DateUtils;
import com.hbgz.pub.util.HttpUtil;
import com.hbgz.pub.util.JsonUtils;
import com.hbgz.pub.util.ObjectCensor;
import com.hbgz.pub.util.StringUtil;

@Service(value = "BUS200")
public class DigitalHealthService
{
	@Autowired
	public DigitalHealthDao digitalHealthDao;

	@Autowired
	private SysId sysId;

	@Autowired
	UserQustionDao userQustionDao;

	@Autowired
	HibernateObjectDao hibernateObjectDao;

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
		int orderDayLen = 5;
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
					newMap.put("day", DateUtils.CHN_DATE_FORMAT.format(dateT) + " ����" + weekStr);
					list.add(newMap);
				}
			}
		}
		JSONObject obj = new JSONObject();
		obj.element("orders", list);
		return obj;
	}

	@ServiceType(value = "BUS2004")
	public JSONObject getOrderByDoctorId(String doctorId) throws QryException
	{
		int orderDayLen = 5;
		List orderList = digitalHealthDao.getOrderByDoctorId(doctorId);
		List ordertotalList = digitalHealthDao.qryOrderTotalNum(doctorId);
		List list = new ArrayList();
		Date date = new Date();

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
				String teamId = StringUtil.getMapKeyVal(subMap, "teamId");
				String fee = StringUtil.getMapKeyVal(subMap, "registerFee");
				String registerNum = StringUtil.getMapKeyVal(subMap, "registerNum");
				String dayType = StringUtil.getMapKeyVal(subMap, "dayType");
				String registerId = StringUtil.getMapKeyVal(subMap, "registerId");
				String day = DateUtils.CHN_DATE_FORMAT.format(dateT);
				String workTime = "����" + weekStr + dayType;
				String dayWorkTime = day + workTime;

				String userOrderNum = "1";
				for (int m = 0; m < ordertotalList.size(); m++)
				{
					Map mapT = (Map) ordertotalList.get(m);
					String idT = StringUtil.getMapKeyVal(mapT, "registerId");
					;
					String dayT = StringUtil.getMapKeyVal(mapT, "registerTime");
					dayT = dayT.replaceAll(" ", "");
					if (registerId.equals(idT) && dayWorkTime.equals(dayT))
					{
						userOrderNum = StringUtil.getMapKeyVal(mapT, "orderNum");
						break;
					}
				}

				Map newMap = new HashMap();
				newMap.put("registerId", registerId);
				newMap.put("teamName", teamName);
				newMap.put("userOrderNum", userOrderNum);// ԤԼ����
				newMap.put("doctorId", doctorId);
				newMap.put("teamId", teamId);
				newMap.put("fee", fee);
				newMap.put("registerNum", registerNum);
				newMap.put("day", DateUtils.CHN_DATE_FORMAT.format(dateT));
				newMap.put("workTime", " ����" + weekStr + " " + dayType);
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
		if ("0".equals(orderNum) && "0".equals(registerId))// ��ͨ�Һ�
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
	public boolean addUserQuestion(String userQestion) throws Exception
	{
		UserQuestionT qestionT = (UserQuestionT) JsonUtils.toBean(userQestion, UserQuestionT.class);
		qestionT.setQestionId(sysId.getId() + "");
		userQustionDao.save(qestionT);
		return true;
	}

	@ServiceType(value = "BUS2008")
	public JSONArray getUserQuestions(String doctorId) throws JsonException
	{
		List list = userQustionDao.qryQuestionTs(doctorId);
		JSONArray jsonArray = JsonUtils.fromArray(list);
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
	public JSONArray getQuestionTsByIds(String userId, String doctorId) throws QryException
	{
		List list = userQustionDao.qryQuestionTsByIds(userId, doctorId);
		JSONArray jsonArray = JsonUtils.fromArray(list);
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
			throws QryException
	{
		List<HospitalNewsT> list = hibernateObjectDao.qryHospitalNews(hospitalId, type, typeId);
		for (HospitalNewsT hospitalNewsT : list)
		{
			byte[] contentT = hospitalNewsT.getNewsContent();
			if (contentT != null)
			{
				hospitalNewsT.setContent(new String(contentT));
			}
			hospitalNewsT.setNewsContent(null);
		}
		JSONArray jsonArray = JsonUtils.fromArray(list);
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
		CacheManager cacheManager = (CacheManager) BeanFactoryHelper.getBean("cacheManager");
		List list = cacheManager.getNewsType(hospitalId, type, "HOSPITALNEWS");
		JSONArray jsonArray = JsonUtils.fromArray(list);
		return jsonArray;
	}

	@ServiceType(value = "BUS20021")
	public String getAuthCode(String accNbr,String type) throws  Exception
	{
		CacheManager cacheManager = (CacheManager) BeanFactoryHelper.getBean("cacheManager");
		Map map =cacheManager.getAuthCode(accNbr);
		String url="http://api.app2e.com/smsBigSend.api.php";
		Map<String, String > params = new HashMap<String, String>();
		params.put("username", "haixing");
		params.put("pwd", "cb6fbeee3deb608f000a8f132531b738");
		params.put("p", accNbr);
		params.put("isUrlEncode", "no");
//	    params.put("msg","������ͨ�������𾴵��û�����ע����֤����"+StringUtil.getMapKeyVal(map, accNbr)+"�������ܼ�Ը��Ϊ�������ĺð���");
			
		String msgRst= HttpUtil.http(url, params, "", "", "");
	    JSONObject jsonObject = JSONObject.fromObject(msgRst);
	    String status = jsonObject.getString("status");
		if("set_psw".equals(type) && "100".equals(status))
		{
			
			List userList = hibernateObjectDao.findByProperty("HospitalUserT", "telephone",accNbr);
			if(ObjectCensor.checkListIsNull(userList))
			{
				HospitalUserT user = (HospitalUserT) userList.get(0);
				user.setPassword(StringUtil.getMapKeyVal(map, accNbr));
				hibernateObjectDao.update(user);
			}
		}
		return msgRst;
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

	// ��ѯ�û��ĹҺŶ���
	public List qryRegisterOrder(String hospitalName, String teamName, String doctorName,
			String startTime, String endTime, String userId) throws Exception
	{
		List registerOrderList = digitalHealthDao.qryRegisterOrder(hospitalName, teamName,
				doctorName, userId, startTime, endTime);
		return registerOrderList;
	}

	// ԤԼ��ȡ���ҺŶ���
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
}
