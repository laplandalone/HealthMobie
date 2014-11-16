package com.hbgz.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hbgz.pub.util.ObjectCensor;
import com.hbgz.pub.util.StringUtil;
import com.hbgz.service.PatientVisitService;

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
			String visitType = request.getParameter("visitType");
			String visitName = request.getParameter("visitName");
			String sex = request.getParameter("sex");
			sex = (sex == null || "".equals(sex) || "null".equals(sex)) ? "нч" : sex;
			List sList = patientVisitService.qryVisitDetail(visitId);
			log.error(sList);
			view.setViewName("/view/visit/visitDetail");
			view.addObject("sList", sList);
			view.addObject("visitType", visitType);
			view.addObject("visitName", visitName);
			view.addObject("sex", sex);
		} 
		catch (Exception e) 
		{
			view.setViewName("error");
		}
		return view;
	}
}
