package com.hbgz.timer.handler;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;

import com.hbgz.pub.base.SysDate;
import com.tools.pub.resolver.BeanFactoryHelper;
import com.tools.pub.utils.ObjectCensor;
import com.tools.pub.utils.StringUtil;

public abstract class TimerValidateService 
{
	private Log log = LogFactory.getLog(TimerValidateService.class);
	
	//定时任务服务标识
	private String timerId;
	
	public abstract Map executeBusiness();
	
	public abstract void delegate();

	/**
	 * 定时任务入口
	 */
	public void accessBusiness()
	{
		String control = "A", remark = "", failures = "", unit = "", interval = "";
		try 
		{
			Map timerMap = validateTimer();
			if(ObjectCensor.checkObjectIsNull(timerMap))
			{
				return;
			}
			else
			{
				Map rstMap = this.executeBusiness();
				remark = StringUtil.getMapKeyVal(rstMap, "remark");
				failures = StringUtil.getMapKeyVal(rstMap, "failures");
				unit = StringUtil.getMapKeyVal(timerMap, "unit");
				interval = StringUtil.getMapKeyVal(timerMap, "interval");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			BeanFactory beanFactory = BeanFactoryHelper.getBeanfactory();
			AutoTimerHandler autoTimerHandler = (AutoTimerHandler) beanFactory.getBean("autoTimerHandler");
			autoTimerHandler.finishBusiness(timerId, control, unit, interval, remark, failures);
		}
	}

	private Map validateTimer()
	{
		Map timerMap = null;
		BeanFactory beanFactory = BeanFactoryHelper.getBeanfactory();
		AutoTimerHandler autoTimerHandler = (AutoTimerHandler) beanFactory.getBean("autoTimerHandler");
		log.error("validateTimer start time:" + SysDate.getCurrentTime());
		List<Map<String, Object>> timerConfigList = autoTimerHandler.scanTimerById(timerId);
		log.error("timerId:" + timerId + "; timerConfigList:" + timerConfigList);
		if(ObjectCensor.checkListIsNull(timerConfigList))
		{
			timerMap = timerConfigList.get(0);
			autoTimerHandler.startBusiness(timerId);
		}
		log.error("validateTimer end time:" + SysDate.getCurrentTime());
		return timerMap;
	}
	
	public String getTimerId() 
	{
		return timerId;
	}

	public void setTimerId(String timerId) 
	{
		this.timerId = timerId;
	}
}
