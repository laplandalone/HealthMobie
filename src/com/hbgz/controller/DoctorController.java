package com.hbgz.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.view.RedirectView;

import com.hbgz.pub.util.ObjectCensor;
import com.hbgz.pub.util.StringUtil;
import com.hbgz.service.DigitalHealthService;

/**
 * 
 * @author ran haiquan 18907181648@189.cn
 *
 */
@Controller
@RequestMapping("/doctor.htm")
public class DoctorController 
{
	private static Log log = LogFactory.getLog(DoctorController.class);
	
	@Autowired
	private DigitalHealthService digitalHealthService;
	
	
	@RequestMapping(params = "method=queryPre")
	public ModelAndView queryPre(HttpServletResponse response , HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView("doctors");
		HttpSession session = request.getSession();
		String hospitalId = (String) session.getAttribute("hospitalId");
		String privs = (String) session.getAttribute("userPrivs");
		String doctorId = request.getParameter("doctorId");
		String teamId = request.getParameter("teamId");
		String doctorName = request.getParameter("doctorName");
		if (ObjectCensor.isStrRegular(hospitalId)) 
		{
			if ("3".equals(privs)) 
			{
				doctorId = "";// 查询所有，超级管理员
			}
			List doctorLst = digitalHealthService.getDoctorByHospitalId(hospitalId, doctorId, teamId, doctorName);
			model.addObject("teamId", teamId);
			model.addObject("doctorName", doctorName);
			model.addObject("doctorLst", doctorLst);
			model.setViewName("/view/doctor/doctors");
		} 
		else
		{
			model.setViewName("error");
		}
		return model;
	}
	
	@RequestMapping(params = "method=getDoctor")
	public ModelAndView  getDoctorById(HttpServletResponse response , HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView("updateDoctor");
		String doctorId = (String)request.getParameter("doctorId");
		
		if(ObjectCensor.isStrRegular(doctorId))
		{
			Map doctor  = digitalHealthService.getDoctor(doctorId);
			List registers =digitalHealthService.getDoctorRegister(doctorId);
			String fee="";
			String num="";
			if(ObjectCensor.checkListIsNull(registers))
			{
				Map map = (Map) registers.get(0);
				fee=StringUtil.getMapKeyVal(map, "register_fee");
				num=StringUtil.getMapKeyVal(map,"register_num");
				doctor.put("fee",fee);
				doctor.put("num",num);
			}
		
			model.addObject("doctor", doctor);
			model.addObject("registers", registers);
			model.setViewName("/view/doctor/updateDoctor");
		}
		else
		{
			model.setViewName("error");
		}
		return model;
	}
	
	@RequestMapping(params = "method=updateDoctor")
	public void updateDoctor(HttpServletResponse response , HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView("updateDoctor");
		HttpSession session = request.getSession();
		String hospitalId= (String)session.getAttribute("hospitalId");
		String doctorId = (String)request.getParameter("doctorId");
		
		String name = (String)request.getParameter("name");
		String password = (String)request.getParameter("password");
		String fee = (String)request.getParameter("fee"); 
		String introduce=request.getParameter("introduce");
		String skill=request.getParameter("skill");
		
		Writer wr = response.getWriter();
		if(ObjectCensor.isStrRegular(doctorId,hospitalId,name,password) )
		{
			try {
				digitalHealthService.updateHospitalMananger(hospitalId, doctorId, name, password);
				digitalHealthService.updateDoctor(hospitalId,doctorId, fee,introduce,skill);
			} catch (Exception e) 
			{
				e.printStackTrace();
				wr.write("error");
				return;
			}
		}
		else
		{
			wr.write("error");
			return;
		}
		wr.write("success");
		wr.close();
	}
	
	@RequestMapping(params = "method=updateRegisterTime")
	public ModelAndView updateRegisterTime(HttpServletResponse response , HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView("updateRegisterTime");
		HttpSession session = request.getSession();
		String hospitalId= (String)session.getAttribute("hospitalId");
		String doctorId = (String)request.getParameter("doctorId");
		
		String registerTimes = (String)request.getParameter("registerTimes");
		
		digitalHealthService.updateDoctorRegisterTimes(registerTimes,doctorId);
		
		if(ObjectCensor.isStrRegular(doctorId,hospitalId) )
		{
			return new ModelAndView(new RedirectView("/doctor.htm?method=getDoctor&doctorId="+doctorId));
		}
		else
		{
			model.setViewName("error");
		}
		return model;
	}
	
	@RequestMapping(params = "method=qryTeamList")
	public void qryTeamList(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setCharacterEncoding("UTF-8");
		PrintWriter wr = response.getWriter();
		HttpSession session = request.getSession();
		String hospitalId = (String) session.getAttribute("hospitalId");
		if(ObjectCensor.isStrRegular(hospitalId))
		{
			try 
			{
				JSONArray teamArray = digitalHealthService.qryTeamList(hospitalId);
				log.error(teamArray);
				wr.println(teamArray);
			}
			catch (Exception e) 
			{
				wr.println("error");
			}
		}
		else
		{
			wr.println("error");
		}
		wr.close();
	}
	
	@RequestMapping(params = "method=qryOnlineDortorList")
	public void qryOnlineDortorList(@RequestBody JSONObject obj ,HttpServletRequest request, HttpServletResponse response)
	{
		try 
		{
			response.setCharacterEncoding("UTF-8");
			HttpSession session = request.getSession();
			String hospitalId = (String) session.getAttribute("hospitalId");
			PrintWriter pw = response.getWriter();
			if(ObjectCensor.isStrRegular(hospitalId))
			{
				int pageNum = Integer.parseInt(StringUtil.getJSONObjectKeyVal(obj, "curId"));
				int pageSize = Integer.parseInt(StringUtil.getJSONObjectKeyVal(obj, "pageNum"));
				String teamId = StringUtil.getJSONObjectKeyVal(obj, "teamId");
				JSONObject object = digitalHealthService.qryOnlineDortorList(pageNum, pageSize, hospitalId, teamId);
				log.error(object);
				pw.println(object);
			}
			else
			{
				pw.println("error");
			}
			pw.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping(params = "method=updateOnlineState")
	public void updateOnlineState(HttpServletRequest request, HttpServletResponse response)
	{
		try 
		{
			response.setCharacterEncoding("UTF-8");
			String doctorId = request.getParameter("doctorId");
			String operatorType = request.getParameter("operatorType");
			boolean flag = digitalHealthService.updateOnlineState(doctorId, operatorType);
			PrintWriter pw = response.getWriter();
			pw.println(flag);
			pw.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
