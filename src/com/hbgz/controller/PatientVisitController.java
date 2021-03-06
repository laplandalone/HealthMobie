package com.hbgz.controller;

import java.io.PrintWriter;
import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import com.hbgz.service.PatientVisitService;
import com.tools.pub.utils.StringUtil;

@Controller
@RequestMapping("/visit.htm")
public class PatientVisitController 
{
	private static Log log = LogFactory.getLog(PatientVisitController.class);
	
	@Autowired
	private PatientVisitService patientVisitService;
	
	@RequestMapping(params = "method=qryPatientVisitList")
	public void qryPatientVisitList(@RequestBody JSONObject obj, HttpServletResponse response)
	{
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try
		{
			out = response.getWriter();
			String startTime = StringUtil.getJSONObjectKeyVal(obj, "startTime");
			String endTime = StringUtil.getJSONObjectKeyVal(obj, "endTime");
			String visitName = StringUtil.getJSONObjectKeyVal(obj, "visitName");
			String visitType = StringUtil.getJSONObjectKeyVal(obj, "visitType");
			String cardId = StringUtil.getJSONObjectKeyVal(obj, "cardId");
			List sList = patientVisitService.qryPatientVisitList(startTime, endTime, visitName, visitType, cardId);
			log.error(sList);
			out.println(JSONArray.fromObject(sList));
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			out.close();
		}
	}
	
	@RequestMapping(params = "method=qryVisitDetail")
	public ModelAndView qryVisitDetail(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView view = new ModelAndView("visitDetail");
		try 
		{
			String visitId = request.getParameter("visitId");
			JSONArray array = patientVisitService.qryVisitDetail(visitId);
			log.error(array);
			JSONObject obj = patientVisitService.qryPatientVisitById(visitId);
			log.error(obj);
			view.setViewName("/view/visit/visitDetail");
			view.addObject("sList", array);
			view.addObject("obj", obj);
		} 
		catch (Exception e) 
		{
			view.setViewName("error");
		}
		return view;
	}
	
	@RequestMapping(params = "method=qryUserList")
	public void qryUserList(@RequestBody JSONObject obj, HttpServletResponse response)
	{
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try
		{
			out = response.getWriter();
			int pageNum = Integer.parseInt(StringUtil.getJSONObjectKeyVal(obj, "curId"));
			int pageSize = Integer.parseInt(StringUtil.getJSONObjectKeyVal(obj, "pageNum"));
			String userName = StringUtil.getJSONObjectKeyVal(obj, "userName");
			String sex = StringUtil.getJSONObjectKeyVal(obj, "sex");
			String telephone = StringUtil.getJSONObjectKeyVal(obj, "telephone");
			JSONObject object = patientVisitService.qryUserList(pageNum, pageSize, userName, sex, telephone);
			log.error(object);
			out.println(object);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			out.close();
		}
	}
	
	@RequestMapping(params = "method=qryUserLoginActivityList")
	public void qryUserLoginActivityList(@RequestBody JSONObject obj, HttpServletRequest request, HttpServletResponse response)
	{
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try
		{
			out = response.getWriter();
			int pageNum = Integer.parseInt(StringUtil.getJSONObjectKeyVal(obj, "curId"));
			int pageSize = Integer.parseInt(StringUtil.getJSONObjectKeyVal(obj, "pageNum"));
			String startTime = StringUtil.getJSONObjectKeyVal(obj, "startTime");
			String endTime = StringUtil.getJSONObjectKeyVal(obj, "endTime");
			HttpSession session = request.getSession();
			String hospitalId = (String) session.getAttribute("hospitalId");
			JSONObject object = patientVisitService.qryUserLoginActivityList(pageNum, pageSize, startTime, endTime, hospitalId);
			log.error(object);
			out.println(object);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			out.close();
		}
	}
}
