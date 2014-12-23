package com.hbgz.timer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hbgz.service.SynHISService;
import com.hbgz.timer.handler.TimerValidateService;

public class CancelOrderService extends TimerValidateService
{
	private Log log = LogFactory.getLog(CancelOrderService.class);
	
	SynHISService hisService = new SynHISService();
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
			String sql = "select t.* from  register_order_t t where pay_state='100' and state='00A' and hospital_id='102' and (sysdate-create_date)*24*60>45";
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
