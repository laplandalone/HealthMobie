package com.hbgz.timer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hbgz.pub.qry.QryCenter;
import com.hbgz.pub.qry.QryCenterFactory;
import com.hbgz.pub.resolver.BeanFactoryHelper;
import com.hbgz.pub.util.Keys;
import com.hbgz.pub.util.ObjectCensor;
import com.hbgz.pub.util.StringUtil;
import com.hbgz.thread.AndroidPushMsgThread;
import com.hbgz.timer.handler.TimerValidateService;

public class WakeService extends TimerValidateService
{
	private Log log = LogFactory.getLog(WakeService.class);
	
	@Override
	public void delegate() 
	{
		this.accessBusiness();
	}

	@Override
	public Map executeBusiness() 
	{
		Map retMap = new HashMap();
		String remark = "", failures = "正常";
		try 
		{
			String sql = "select * from wake_t where state = '00A' and wake_flag = 'N' and sysdate >= wake_date ";
			QryCenter qryCenter = QryCenterFactory.getQryCenter();
			List sList = qryCenter.executeSqlByMapListWithTrans(sql, new ArrayList());
			log.error(sList);
			if(ObjectCensor.checkListIsNull(sList))
			{
				BeanFactory beanFactory = BeanFactoryHelper.getBeanfactory();
				JdbcTemplate jdbcTemplate = (JdbcTemplate) beanFactory.getBean(Keys.JTEMPLATE);
				List<String> exeList = new ArrayList<String>();
				for(int i = 0, len = sList.size(); i < len; i++)
				{
					Map map = (Map) sList.get(i);
					String wakeId = StringUtil.getMapKeyVal(map, "wakeId");
					
					sql = "update wake_t set wake_flag = 'Y' where wake_id = '"+wakeId+"' ";
					exeList.add(sql);
					
					String msgType = StringUtil.getMapKeyVal(map, "wakeType");
					
					AndroidPushMsgThread target = new AndroidPushMsgThread();
					String wakeContent = StringUtil.getMapKeyVal(map, "wakeContent");
					String wakeName = StringUtil.getMapKeyVal(map, "wakeName");
					JSONObject obj ;
					String userId="";
					if(!"ALL".equals(msgType))
					{
						 obj = JSONObject.fromObject(wakeContent);
						 userId = StringUtil.getJSONObjectKeyVal(obj, "userId");
					}
					target.setCustomParam(wakeContent);
					target.setUserId(userId);
					target.setMsgType(msgType);
					target.setTitle("掌上亚心");
					target.setDescription(wakeName);
					new Thread(target).start();
					
				}
				
				String[] exeSql = new String[exeList.size()];
				for(int i = 0, len = exeList.size(); i < len; i++)
				{
					exeSql[i] = exeList.get(i);
				}
				jdbcTemplate.batchUpdate(exeSql);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			remark = e.getMessage();
			failures = "处理失败!";
		}
		retMap.put("remark", remark);
		retMap.put("failures", failures);
		return retMap;
	}
}
