package com.hbgz.controller;

import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.hbgz.model.DoctorRegisterT;
import com.hbgz.model.UserQuestionT;
import com.hbgz.pub.sequence.SysId;
import com.hbgz.pub.util.JsonUtils;
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
		String hospitalId= (String)session.getAttribute("hospitalId");
		
		String privs = (String) session.getAttribute("userPrivs");
		String doctorId=request.getParameter("doctorId");
		if(ObjectCensor.isStrRegular(hospitalId))
		{
			if("3".equals(privs))
			{
				 doctorId ="";//查询所有，超级管理员
			}
			List doctorLst = digitalHealthService.getDoctorByHospitalId(hospitalId,doctorId);
			model.addObject("doctorLst", doctorLst);
			model.setViewName("/view/doctor/doctors");
		}
		else
		{
			model.addObject("result", "error");
			model.setViewName("login");
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
			model.addObject("result", "error");
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(params = "method=updateDoctor")
	public ModelAndView updateDoctor(HttpServletResponse response , HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView("updateDoctor");
		HttpSession session = request.getSession();
		String hospitalId= (String)session.getAttribute("hospitalId");
		String doctorId = (String)request.getParameter("doctorId");
		
		String name = (String)request.getParameter("name");
		String password = (String)request.getParameter("password");
		String fee = (String)request.getParameter("fee"); 
		
		if(ObjectCensor.isStrRegular(doctorId,hospitalId,name,password) )
		{
			digitalHealthService.updateHospitalMananger(hospitalId, doctorId, name, password);
			digitalHealthService.updateDoctor(doctorId, fee);
			model.addObject("reslut", "success");
			model.setViewName("/view/doctor/updateDoctor");
		}
		else
		{
			model.addObject("reslut", "error");
			model.setViewName("/view/doctor/updateDoctor");
		}
		return model;
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
			model.addObject("reslut", "success");
			return new ModelAndView(new RedirectView("/doctor.htm?method=getDoctor&doctorId="+doctorId));
		}
		else
		{
			model.addObject("result", "error");
			model.setViewName("login");
		}
		return model;
	}
	
}
