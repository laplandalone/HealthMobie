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
	 * ����timerId��ȡ��ʱ����������Ϣ��ͬʱ��������¼
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
	 * ����ʼ����ʱ������timer_config��control�ֶθ�ΪR��this_date(��������ʱ��)
	 * ��Ϊ���ݿ�ϵͳʱ�䣬last_date(�ϴ�����ʱ��)��Ϊthis_date
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
		// Ĭ���´�ִ��ʱ����ʱʮ��
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
