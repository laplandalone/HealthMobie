package com.hbgz.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public class NotifyService 
{
	public Map<String, String> getParamMap(HttpServletRequest request) throws Exception 
	{
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("out_trade_no", request.getParameter("out_trade_no"));
		paramMap.put("success", request.getParameter("success"));
		
		return paramMap;
	}
}
