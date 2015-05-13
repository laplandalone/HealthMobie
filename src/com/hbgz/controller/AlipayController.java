package com.hbgz.controller;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hbgz.service.DigitalHealthService;

/**
 * 支付宝支付
 * @author  
 */
@Controller
public class AlipayController 
{
	private static Log log = LogFactory.getLog(AlipayController.class); 
	
	@Autowired
	private DigitalHealthService digitalHealthService ;
	
	@RequestMapping("/alipay/planPayRst.htm")
	public void planPayResult(HttpServletRequest request, HttpServletResponse response)
	{
		try 
		{
			//获取异步通知参数
			Map<String, String> params = digitalHealthService.getParamMap(request);
			//验证消息是否是支付宝发出的合法消息
//			boolean flag = AlipayNotify.verify(param);
//			log.error(flag);
			if("TRADE_SUCCESS".equals(params.get("trade_status")))
			{
				PrintWriter out = response.getWriter();
				out.write("success");
				out.close();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
