package com.hbgz.timer.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hbgz.dao.HibernateObjectDao;
import com.hbgz.model.WakeT;
import com.hbgz.pub.base.SysDate;
import com.hbgz.pub.qry.QryCenter;
import com.hbgz.pub.qry.QryCenterFactory;
import com.hbgz.pub.sequence.SysId;
import com.hbgz.pub.util.DateUtils;
import com.hbgz.pub.util.HisHttpUtil;
import com.hbgz.timer.handler.TimerValidateService;
import com.tools.pub.resolver.BeanFactoryHelper;
import com.tools.pub.utils.ObjectCensor;
import com.tools.pub.utils.StringUtil;

public class VisitService extends TimerValidateService
{
	private Log log = LogFactory.getLog(VisitService.class);
	
	@Autowired
	private SysId sysId;
	
	@Autowired
	private HibernateObjectDao hibernateObjectDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void delegate() throws Exception 
	{
		this.accessBusiness();
	}

	@Override
	public Map executeBusiness() throws Exception 
	{
		Map retMap = new HashMap();
		String remark = "", failures = "正常";
		 
		String sql = "select user_id,user_name,card_no from hospital_user_t t where state='00A' and card_no!='null' ";
		QryCenter qryCenter = QryCenterFactory.getQryCenter();
		List sList = qryCenter.executeSqlByMapListWithTrans(sql, new ArrayList());
		
		String operSql="select * from user_oper_t where state='00A'";
		List operSqlList = qryCenter.executeSqlByMapListWithTrans(operSql, new ArrayList());
		
		log.error(sList);
		if(ObjectCensor.checkListIsNull(sList))
		{
			BeanFactory beanFactory = BeanFactoryHelper.getBeanfactory();
			List<String> exeList = new ArrayList<String>();
			String patientIds="'";
			Map<String,String> userMap = new HashMap<String,String>();
			for(int i = 0, len = sList.size(); i < len; i++)
			{
				Map map = (Map) sList.get(i);
				String patientId = StringUtil.getMapKeyVal(map, "cardNo");
				if(patientId.length()==6)
				{
					patientId="PID000"+patientId;
				}
				String userId = StringUtil.getMapKeyVal(map, "userId");
				patientIds+=patientId+"','";
				userMap.put(patientId, userId);
			}
			if(patientIds.length()>2)
			{
				patientIds=patientIds.substring(0,patientIds.length()-2);
				String hisSql="select rtrim(v.operation_type) operation_type,rtrim(v.department) department,rtrim(v.patient_id) patient_id,convert(varchar(10),v.operation_time,102) operation_time,rtrim(m.patient_name) patient_name from view_ssqk_app v,mzbrxx m where v.patient_id=m.patient_id and v.patient_id  in ("+patientIds+")";
				 
				String hisRst = HisHttpUtil.http(hisSql);
				JSONArray array =JSONArray.fromObject(hisRst);
				for(int i=0;i<array.size();i++)
				{
				    JSONObject object = array.getJSONObject(i);
				    String pId=object.getString("patient_id");
				    String pName=object.getString("patient_name");
				    String pTime=object.getString("operation_time");
				    pTime=pTime.replace(".", "-");
				    
				    Map map = new HashMap();
				    map.put("patientId", pId);
				    map.put("operationTime", pTime);
				    List oper = StringUtil.getSubMapList(operSqlList,map);
				    if(oper!=null && oper.size()>0)
				    {
				    	continue;
				    }
				    
				    String userId = userMap.get(pId);
				    WakeT wakeT = new WakeT();
					wakeT.setWakeId(BigDecimal.valueOf(sysId.getId()));
					wakeT.setUserId(userId);
					String date30 = DateUtils.afterNDate(pTime, 31);
					wakeT.setWakeName(date30+" 随访消息");
					wakeT.setWakeContent("患者"+pName+",您于"+pTime+"日在亚洲心脏病医院做了相关手术,为了保障您术后健康及恢复请在"+date30+"日完成随访表格的填写.");
					wakeT.setWakeDate(SysDate.getSysDate(date30+" 11:00:00"));
					wakeT.setCreateDate(SysDate.getSysDate(date30+" 00:00:00"));
					wakeT.setWakeValue("1");
					wakeT.setState("00A");
					wakeT.setWakeType("visit_plan");
					wakeT.setWakeFlag("N");
					if(new Date().before(SysDate.getSysDate(date30)))
					{	
						hibernateObjectDao.save(wakeT);
					}
					
					wakeT.setWakeId(BigDecimal.valueOf(sysId.getId()));
					String date90 = DateUtils.afterNDate(pTime, 91);
					wakeT.setWakeName(date90+" 随访消息");
					wakeT.setWakeContent("患者"+pName+",您于"+pTime+"日在亚洲心脏病医院做了相关手术,为了保障您术后健康及恢复请在"+date90+"日完成随访表格的填写.");
					wakeT.setWakeDate(SysDate.getSysDate(date90+" 11:00:00"));
					wakeT.setCreateDate(SysDate.getSysDate(date90+" 00:00:00"));
					if(new Date().before(SysDate.getSysDate(date90)))
					{	
						hibernateObjectDao.save(wakeT);
					}
					
					wakeT.setWakeId(BigDecimal.valueOf(sysId.getId()));
					wakeT.setCreateDate(SysDate.getSysDate());
					String date180 = DateUtils.afterNDate(pTime, 181);
					wakeT.setWakeName(date90+" 随访消息");
					wakeT.setWakeContent("患者"+pName+",您于"+pTime+"日在亚洲心脏病医院做了相关手术,为了保障您术后健康及恢复请在"+date180+"日完成随访表格的填写.");
					wakeT.setWakeDate(SysDate.getSysDate(date180+" 11:00:00"));
					wakeT.setCreateDate(SysDate.getSysDate(date180+" 00:00:00"));
					if(new Date().before(SysDate.getSysDate(date180)))
					{	
						hibernateObjectDao.save(wakeT);
					}
					
					wakeT.setWakeId(BigDecimal.valueOf(sysId.getId()));
					wakeT.setCreateDate(SysDate.getSysDate());
					String date360 = DateUtils.afterNDate(pTime, 360);
					wakeT.setWakeName(date90+" 随访消息");
					wakeT.setWakeContent("患者"+pName+",您于"+pTime+"日在亚洲心脏病医院做了相关手术,为了保障您术后健康及恢复请在"+date360+"日完成随访表格的填写.");
					wakeT.setWakeDate(SysDate.getSysDate(date360+" 11:00:00"));
					wakeT.setCreateDate(SysDate.getSysDate(date360+" 00:00:00"));
					if(new Date().before(SysDate.getSysDate(date360)))
					{	
						hibernateObjectDao.save(wakeT);
					}
					
					String addOper="insert into user_oper_t(id,patient_name,patient_id,OPERATION_TIME,state,create_date) values('"+sysId.getId()+"','"+pName+"','"+pId+"','"+pTime+"','00A',sysdate)";
					jdbcTemplate.execute(addOper);
				}
			}
		}
		retMap.put("remark", remark);
		retMap.put("failures", failures);
		return retMap;
	}
}
