package com.hbgz.timer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hbgz.pub.qry.QryCenter;
import com.hbgz.pub.qry.QryCenterFactory;
import com.hbgz.service.SynHISService;
import com.hbgz.timer.handler.TimerValidateService;
import com.tools.pub.utils.ObjectCensor;
import com.tools.pub.utils.StringUtil;

public class CancelOrderService extends TimerValidateService
{
	private Log log = LogFactory.getLog(CancelOrderService.class);
	
	SynHISService hisService = new SynHISService();
	
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
		String remark = "", failures = "Õý³£";
		
			String sql = "select t.* from  register_order_t t where pay_state='100' and state='00A' and hospital_id='102' and (sysdate-create_date)*24*60>45";
			QryCenter qryCenter = QryCenterFactory.getQryCenter();
			List sList = qryCenter.executeSqlByMapListWithTrans(sql, new ArrayList());
			log.error(sList);
			if(ObjectCensor.checkListIsNull(sList))
			{
				List<String> exeList = new ArrayList<String>();
				for(int i = 0, len = sList.size(); i < len; i++)
				{
					Map map = (Map) sList.get(i);
					String orderId = StringUtil.getMapKeyVal(map, "orderId");
					String id=StringUtil.getMapKeyVal(map, "registerId");
					String weekTypeT=StringUtil.getMapKeyVal(map, "registerTime");
					String platformId=StringUtil.getMapKeyVal(map, "platformOrderId");
					sql = "update  register_order_t set pay_state='101' where pay_state='100' and order_id='"+orderId+"'";
					exeList.add(sql);
					hisService.hisRegisterOrder(id, weekTypeT, "-", platformId,"");
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
