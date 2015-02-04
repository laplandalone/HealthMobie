package com.hbgz.timer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hbgz.pub.qry.QryCenter;
import com.hbgz.pub.qry.QryCenterFactory;
import com.hbgz.thread.AndroidPushMsgThread;
import com.hbgz.timer.handler.TimerValidateService;
import com.tools.pub.resolver.BeanFactoryHelper;
import com.tools.pub.utils.ObjectCensor;
import com.tools.pub.utils.StringUtil;

public class WakeService extends TimerValidateService
{
	private Log log = LogFactory.getLog(WakeService.class);
	
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
		 
			String sql = "select wake_id,user_id,wake_name,wake_content,wake_type,to_char(create_date, 'yyyy-mm-dd') create_date,to_char(wake_date, 'yyyy-mm-dd') wake_date from wake_t where state = '00A' and wake_flag = 'N' and sysdate >= wake_date ";
			QryCenter qryCenter = QryCenterFactory.getQryCenter();
			List sList = qryCenter.executeSqlByMapListWithTrans(sql, new ArrayList());
			log.error(sList);
			if(ObjectCensor.checkListIsNull(sList))
			{
				BeanFactory beanFactory = BeanFactoryHelper.getBeanfactory();
				List<String> exeList = new ArrayList<String>();
				for(int i = 0, len = sList.size(); i < len; i++)
				{
					Map map = (Map) sList.get(i);
					String wakeId = StringUtil.getMapKeyVal(map, "wakeId");
					String userId= StringUtil.getMapKeyVal(map, "userId");
					sql = "update wake_t set wake_flag = 'Y' where wake_id = '"+wakeId+"' ";
					exeList.add(sql);
					
					String msgType = StringUtil.getMapKeyVal(map, "wakeType");
					
					AndroidPushMsgThread target = new AndroidPushMsgThread();
					String wakeContent = StringUtil.getMapKeyVal(map, "wakeContent");
					String wakeName = StringUtil.getMapKeyVal(map, "wakeName");
					JSONObject obj ;
					
					if(!"ALL".equals(msgType) && !"visit_plan".equals(msgType))
					{
						 obj = JSONObject.fromObject(wakeContent);
						 userId = StringUtil.getJSONObjectKeyVal(obj, "userId");
					}else if("visit_plan".equals(msgType))
					{
						wakeContent=JSONObject.fromObject(map).toString();
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
		 
		retMap.put("remark", remark);
		retMap.put("failures", failures);
		return retMap;
	}
}
