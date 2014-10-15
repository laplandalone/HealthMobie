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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hbgz.model.UserQuestionT;
import com.hbgz.pub.util.ObjectCensor;
import com.hbgz.service.DigitalHealthService;

/**
 * 
 * @author ran haiquan 18907181648@189.cn
 *
 */
@Controller
@RequestMapping("/ques.htm")
public class QuestionController 
{
	private static Log log = LogFactory.getLog(QuestionController.class);
	
	@Autowired
	private DigitalHealthService digitalHealthService;
	
	
	@RequestMapping(params = "method=queryPre")
	public ModelAndView queryPre(HttpServletResponse response , HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView("questionList");
		HttpSession session = request.getSession();
		String hospitalId= (String)session.getAttribute("hospitalId");
		String doctorId = (String)request.getParameter("doctorId");
		String startTime = (String)request.getParameter("startTime");
		String endTime = (String)request.getParameter("endTime");
		if(ObjectCensor.isStrRegular(doctorId))
		{
			List userFileLst = digitalHealthService.getUserQuestionsByDoctorId(doctorId, hospitalId, startTime, endTime);
			log.error(userFileLst);
			model.addObject("quesLst", userFileLst);
			model.addObject("startTime", startTime);
			model.addObject("endTime", endTime);
			model.addObject("doctorId", doctorId);
			model.setViewName("/view/question/questionList");
		}
		else
		{
			model.addObject("result", "error");
			model.setViewName("error");
		}
		return model;
	}
	
	@RequestMapping(params = "method=qryQuesList")
	public ModelAndView qryQuesList(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView("viewContent");
		try 
		{
			String doctorId = request.getParameter("doctorId");
			String questionId = request.getParameter("questionId");
			JSONArray array = digitalHealthService.qryQuesList(doctorId, questionId);
			log.error(array);
			model.setViewName("/view/question/viewContent");
			model.addObject("quesList", array);
		} 
		catch (Exception e) 
		{
			model.addObject("result", "error");
			model.setViewName("error");
		}
		return model;
	}
	
	@RequestMapping(params = "method=updateQues")
	public ModelAndView updateQuestion(HttpServletResponse response , HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView("questionList");
		String doctorId = (String)request.getParameter("doctorId");
		String userId = (String)request.getParameter("userId");
		String authType = (String)request.getParameter("authType");
		String content = (String)request.getParameter("content");
		String questionId=(String)request.getParameter("questionId");
		String telephone=(String)request.getParameter("telephone");
		if(ObjectCensor.isStrRegular(doctorId,content,userId,questionId,telephone,authType) )
		{
			UserQuestionT userQuestionT = new UserQuestionT();
			
			userQuestionT.setAuthType(authType);
			userQuestionT.setContent(content);
			userQuestionT.setUserId(userId);
		    userQuestionT.setDoctorId(doctorId);
			userQuestionT.setQuestionId(questionId);
			userQuestionT.setUserTelephone(telephone);
			userQuestionT.setRecordType("ans");
			userQuestionT.setState("00A");
			
			JSONObject userQestion = JSONObject.fromObject(userQuestionT);
			
			digitalHealthService.updateUserQuestion(questionId, authType);
			digitalHealthService.addUserQuestion(userQestion.toString());
//			model.addObject("quesLst", userFileLst);
			model.setViewName("/view/question/questionList");
		}
		else
		{
			model.addObject("result", "error");
			model.setViewName("error");
		}
		return model;
	}
	
	@RequestMapping(params = "method=updateAns")
	public void updateAns(HttpServletResponse response , HttpServletRequest request)
	{
		response.setCharacterEncoding("UTF-8");
		try 
		{
			String id = request.getParameter("id");
			String content = request.getParameter("content");
			String retVal = digitalHealthService.updateAns(id, content);
			PrintWriter out = response.getWriter();
			out.println(retVal);
			out.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
