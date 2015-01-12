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
import org.springframework.web.servlet.ModelAndView;

import com.hbgz.model.UserQuestionT;
import com.hbgz.service.DigitalHealthService;
import com.tools.pub.utils.ObjectCensor;
import com.tools.pub.utils.StringUtil;

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
	public void queryPre(@RequestBody JSONObject obj, HttpServletRequest request, HttpServletResponse response)
	{
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String hospitalId= (String)session.getAttribute("hospitalId");
		PrintWriter out = null;
		try 
		{
			if(ObjectCensor.isStrRegular(hospitalId))
			{
				out = response.getWriter();
				String privs = (String) session.getAttribute("userPrivs");
				JSONObject object = new JSONObject();
				if("4".equals(privs) || "5".equals(privs))
				{
					int pageNum = Integer.parseInt(StringUtil.getJSONObjectKeyVal(obj, "curId"));
					int pageSize = Integer.parseInt(StringUtil.getJSONObjectKeyVal(obj, "pageNum"));
					String teamId = StringUtil.getJSONObjectKeyVal(obj, "teamId");
					String doctorName = StringUtil.getJSONObjectKeyVal(obj, "doctorName");
					object = digitalHealthService.qryOnLineDoctorQuesList(pageNum, pageSize, hospitalId, teamId, doctorName);
				}
				else
				{
					int pageNum = Integer.parseInt(StringUtil.getJSONObjectKeyVal(obj, "curId"));
					int pageSize = Integer.parseInt(StringUtil.getJSONObjectKeyVal(obj, "pageNum"));
					String doctorId = StringUtil.getJSONObjectKeyVal(obj, "doctorId");
					String startTime = StringUtil.getJSONObjectKeyVal(obj, "startTime");
					String endTime = StringUtil.getJSONObjectKeyVal(obj, "endTime");
					if(ObjectCensor.isStrRegular(doctorId))
					{
						object = digitalHealthService.qryUserQuestionsList(pageNum, pageSize, doctorId, hospitalId, startTime, endTime);
					}
				}
				log.error(object);
				out.println(object);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			out.println("error");
		}
		finally
		{
			if(out != null)
			{
				out.close();
			}
		}
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
	
	@RequestMapping(params = "method=deleteAns")
	public void deleteAns(HttpServletResponse response , HttpServletRequest request)
	{
		response.setCharacterEncoding("UTF-8");
		try 
		{
			String questionId = request.getParameter("questionId");
			String retVal = digitalHealthService.deleteAns(questionId);
			PrintWriter out = response.getWriter();
			out.println(retVal);
			out.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping(params = "method=updateAuth")
	public void updateAuth(HttpServletRequest request, HttpServletResponse response)
	{
		response.setCharacterEncoding("UTF-8");
		try 
		{
			String id = request.getParameter("id");
			String authType = request.getParameter("authType");
			String retVal = digitalHealthService.updateAuth(id, authType);
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
