package com.hbgz.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hbgz.pub.util.ObjectCensor;
import com.hbgz.service.DigitalHealthService;

@Controller
@RequestMapping("/news.htm")
public class NewsController 
{
	private static Log log = LogFactory.getLog(NewsController.class);
	
	@Autowired
	private DigitalHealthService digitalHealthService;
	
	@RequestMapping(params = "method=qryNewsTypeList")
	public void qryNewsTypeList(HttpServletRequest request, HttpServletResponse response)
	{
		try 
		{
			response.setCharacterEncoding("UTF-8");
			String newsType = request.getParameter("newsType");
			log.error(newsType);
			HttpSession session = request.getSession();
			String hospitalId= (String)session.getAttribute("hospitalId");
			JSONArray array = digitalHealthService.getNewsType(hospitalId, newsType);
			log.error(array);
			PrintWriter out = response.getWriter();
			out.print(array);
			out.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping(params = "method=qryNewsList")
	public ModelAndView qryNewsList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView("newsList");
		HttpSession session = request.getSession();
		String hospitalId= (String)session.getAttribute("hospitalId");
		if(ObjectCensor.isStrRegular(hospitalId))
		{
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String newsType = request.getParameter("newsType");
			String typeId = request.getParameter("typeId");
			String state = request.getParameter("state");
			JSONArray array = digitalHealthService.qryNewsList(hospitalId, startTime, endTime, newsType, typeId, state);
			model.addObject("newsList", array);
			model.addObject("startTime", startTime);
			model.addObject("endTime", endTime);
			model.addObject("newsType", newsType);
			model.addObject("typeId", typeId);
			model.addObject("state", state);
			model.setViewName("/view/news/newsList");
		}
		else
		{
			model.addObject("result", "error");
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(params = "method=getNewsById")
	public ModelAndView getNewsById(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView("updateNews");
		String newsId = request.getParameter("newsId");
		HttpSession session = request.getSession();
		String hospitalId= (String)session.getAttribute("hospitalId");
		if(ObjectCensor.isStrRegular(newsId, hospitalId))
		{
			JSONObject obj = digitalHealthService.getNewsById(newsId, hospitalId);
			log.error(obj);
			model.addObject("news", obj);
			model.setViewName("/view/news/updateNews");
		}
		else
		{
			model.addObject("result", "error");
			model.setViewName("login");
		}
		return model;
	}
}
