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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hbgz.pub.util.ObjectCensor;
import com.hbgz.pub.util.StringUtil;
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
	public void qryNewsList(@RequestBody JSONObject obj, HttpServletRequest request, HttpServletResponse response)
	{
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try
		{
			log.error(obj);
			out = response.getWriter();
			HttpSession session = request.getSession();
			String hospitalId= (String)session.getAttribute("hospitalId");
			if(ObjectCensor.isStrRegular(hospitalId))
			{
				int pageNum = Integer.parseInt(StringUtil.getJSONObjectKeyVal(obj, "curId"));
				int pageSize = Integer.parseInt(StringUtil.getJSONObjectKeyVal(obj, "pageNum"));
				String startTime = StringUtil.getJSONObjectKeyVal(obj, "startTime");
				String endTime = StringUtil.getJSONObjectKeyVal(obj, "endTime");
				String newsType = StringUtil.getJSONObjectKeyVal(obj, "newsType");
				String typeId = StringUtil.getJSONObjectKeyVal(obj, "typeId");
				String state = StringUtil.getJSONObjectKeyVal(obj, "state");
				JSONObject object = digitalHealthService.qryNewsList(pageNum, pageSize, hospitalId, startTime, endTime, newsType, typeId, state);
				log.error(object);
				out.println(object);
			}
			else
			{
				out.println("error");
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			out.println("error");
		}
		finally
		{
			out.close();
		}
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
			String pageNum = request.getParameter("pageNum");
			JSONObject obj = digitalHealthService.getNewsById(newsId, hospitalId);
			log.error(obj);
			String newsType = StringUtil.getJSONObjectKeyVal(obj, "newsType");
			JSONArray array = digitalHealthService.getNewsType(hospitalId, newsType);
			log.error(array);
			model.addObject("pageNum", pageNum);
			model.addObject("news", obj);
			model.addObject("typeList", array);
			model.setViewName("/view/news/updateNews");
		}
		else
		{
			model.addObject("result", "error");
			model.setViewName("error");
		}
		return model;
	}
	
	@RequestMapping(params = "method=addNewsType")
	public void addNewsType(HttpServletRequest request, HttpServletResponse response)
	{
		try 
		{
			response.setCharacterEncoding("GBK");
			HttpSession session = request.getSession();
			String hospitalId= (String)session.getAttribute("hospitalId");
			String newsTypeId = request.getParameter("newsTypeId");
			String newsTypeName = request.getParameter("newsTypeName");
			boolean flag = digitalHealthService.addNewsType(hospitalId, newsTypeId, newsTypeName, "HOSPITALNEWS");
			log.error(flag);
			PrintWriter out = response.getWriter();
			out.print(flag);
			out.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping(params = "method=addNews")
	public void addNews(HttpServletRequest request, HttpServletResponse response)
	{
		response.setCharacterEncoding("GBK");
		try 
		{
			HttpSession session = request.getSession();
			String hospitalId = (String) session.getAttribute("hospitalId");
			String newsId = request.getParameter("newsId");
			String newsType = request.getParameter("newsType");
			String typeId = request.getParameter("typeId");
			String newsTitle = request.getParameter("newsTitle");
			String effDate = request.getParameter("effDate");
			String expDate = request.getParameter("expDate");
			String newsContent = request.getParameter("newsContent");
			String newsImageUrl = request.getParameter("newsImageUrl");
			String urlText = request.getParameter("urlText");
			String url = request.getParameter("url");
			String retVal = digitalHealthService.addNews(hospitalId, newsId, newsType, typeId, newsTitle, effDate, expDate, newsContent, newsImageUrl, urlText, url);
			PrintWriter out = response.getWriter();
			log.error(retVal);
			out.println(retVal);
			out.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping(params = "method=updateNews")
	public void updateNews(HttpServletRequest request, HttpServletResponse response)
	{
		response.setCharacterEncoding("GBK");
		try 
		{
			HttpSession session = request.getSession();
			String hospitalId = (String) session.getAttribute("hospitalId");
			String newsId = request.getParameter("newsId");
			String newsType = request.getParameter("newsType");
			String typeId = request.getParameter("typeId");
			String newsTitle = request.getParameter("newsTitle");
			String effDate = request.getParameter("effDate");
			String expDate = request.getParameter("expDate");
			String newsContent = request.getParameter("newsContent");
			String newsImageUrl = request.getParameter("newsImageUrl");
			String state = request.getParameter("state");
			String oldNewsId = request.getParameter("oldNewsId");
			String urlText = request.getParameter("urlText");
			String url = request.getParameter("url");
			log.error(oldNewsId);
			String retVal = digitalHealthService.updateNews(hospitalId, newsId, newsType, typeId, newsTitle, effDate, expDate, newsContent, newsImageUrl, state, oldNewsId, urlText, url);
			PrintWriter out = response.getWriter();
			log.error(retVal);
			out.println(retVal);
			out.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping(params = "method=deleteNews")
	public void deleteNews(HttpServletRequest request, HttpServletResponse response)
	{
		response.setCharacterEncoding("GBK");
		try 
		{
			HttpSession session = request.getSession();
			String hospitalId = (String) session.getAttribute("hospitalId");
			String newsId = request.getParameter("newsId");
			String retVal = digitalHealthService.deleteNews(hospitalId, newsId);
			PrintWriter out = response.getWriter();
			log.error(retVal);
			out.println(retVal);
			out.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping(params = "method=uploadFile")
	public void uploadFile(MultipartHttpServletRequest request, HttpServletResponse response)
	{
		try 
		{
			response.setCharacterEncoding("UTF-8");
		    String newsType = request.getParameter("newsType");
		    PrintWriter out = response.getWriter();
		    String msg = digitalHealthService.uploadFile(request, newsType);
		    out.println(msg);
		    out.close();
		}
		catch (Exception e) 
		{
		    e.printStackTrace();
		}
	}
}
