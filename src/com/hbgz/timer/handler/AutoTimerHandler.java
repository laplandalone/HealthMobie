package com.hbgz.timer.handler;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.tools.pub.utils.ObjectCensor;
import com.tools.pub.utils.StringUtil;

@Component
public class AutoTimerHandler 
{
	@Autowired
	private JdbcTemplate itzcJdbcTemplate;
	
	/**
	 * 根据timerId获取定时服务任务信息，同时锁该条记录
	 */
	public List<Map<String, Object>> scanTimerById(String timerId)
	{
		if(ObjectCensor.isStrRegular(timerId))
		{
			StringBuffer query = new StringBuffer();
			query.append("select * from timer_config where control = 'A' and state = 'A' ");
			query.append("and (next_date < sysdate or next_date = sysdate) and timer_id = "+timerId+" for update ");
			return itzcJdbcTemplate.queryForList(query.toString());
		}
		return null;
	}
	
	/**
	 * 任务开始启动时，将表timer_config：control字段改为R，this_date(本次运行时间)
	 * 改为数据库系统时间，last_date(上次运行时间)改为this_date
	 */
	public void startBusiness(String timerId)
	{
		if(ObjectCensor.isStrRegular(timerId))
		{
			StringBuffer query = new StringBuffer();
			query.append("update timer_config set control = 'R', last_date = this_date, ");
			query.append("this_date = sysdate where state = 'A' and timer_id = "+timerId+" ");
			itzcJdbcTemplate.update(query.toString());
		}
	}

	public void finishBusiness(String timerId, String control, String unit,
			String interval, String remark, String failures) 
	{
		if(ObjectCensor.isStrRegular(timerId, control, unit, interval))
		{
			String nextTime = getIntervalTime(unit, interval);
			StringBuffer query = new StringBuffer();
			query.append("update timer_config set next_date = "+nextTime+", control = '"+control+"', remarks = '"+remark+"', ");
			query.append("failures = '"+failures+"' where timer_id = "+timerId+" ");
			itzcJdbcTemplate.update(query.toString());
		}
	}

	private String getIntervalTime(String unit, String interval) 
	{
		// 默认下次执行时间延时十秒
		String nextDate = "sysdate + 1 / (24 * 60 * 6)";

		if (StringUtil.checkStringIsNum(interval))
		{
			if ("Y".equals(unit))
			{
				nextDate = "add_months(sysdate,+" + interval + "*12)";
			}
			else if ("M".equals(unit))
			{
				nextDate = "add_months(sysdate,+" + interval + ")";
			} 
			else if ("D".equals(unit))
			{
				nextDate = "sysdate" + interval;
			} 
			else if ("H".equals(unit))
			{
				nextDate = "sysdate+" + interval + "/24";
			} 
			else if ("m".equals(unit))
			{
				nextDate = "sysdate+" + interval + "/24/60 ";
			} 
			else
			{
				nextDate = "sysdate+(" + interval + "/24/60/60)";
			}
		}
		return nextDate;
	}
}
