package com.hbgz.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.hbgz.pub.util.ObjectCensor;
import com.hbgz.pub.util.StringUtil;
import com.hbgz.service.DigitalHealthService;
import com.hbgz.service.LoginService;
import com.hbgz.service.MobileService;

/**
 * 
 * @author ran haiquan 18907181648@189.cn
 *
 */
@Controller
@RequestMapping("/mobile.htm")
public class MobileController 
{
	private static Log log = LogFactory.getLog(MobileController.class);
	
	@Autowired
	private MobileService mobileService;
	
	@Autowired
	public LoginService loginService;
	
	@Autowired
	private DigitalHealthService digitalHealthService;
	
	@RequestMapping(params = "method=axis")
	public void axis(String param, HttpServletResponse response)
	{
		try 
		{
			String retVal = mobileService.axis(param);
			log.error(retVal);
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(retVal);
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
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null ;
		try 
		{
			out = response.getWriter();
			String userId = request.getParameter("userId");
			String medicalName = request.getParameter("medicalName");
			String remark = request.getParameter("remark");
			String retVal =mobileService.uploadFile(request, userId, medicalName, remark);
			
			
			String rtn="{returnCode:"+retVal+"}";
			out.println(rtn);
			out.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			String rtn="{returnCode:1}";
			out.println(rtn);
			out.close();
		}
	}
	
	@RequestMapping(params = "method=login")
	public ModelAndView login(HttpServletResponse response , HttpServletRequest request) throws Exception
	{
		 String configDir = System.getProperty("user.dir");
		 System.out.println("configDir:"+configDir);
		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("userpass");
		String sessName = (String)session.getAttribute("username");
		if(username == null && !"".equals(sessName))
		{
			username = sessName;
		}
		try{
			Map user = loginService.getUserWeb(username, password);
			if(user!=null)
			{
				String userId = StringUtil.getMapKeyVal(user, "manager_id");
				session.setAttribute("username", username);
				session.setAttribute("userId", userId);
				model.setViewName("index");
			}
			else
			{
				model.addObject("result", "error");
				model.setViewName("login");
			}
		}
		catch(Exception err)
		{
			err.printStackTrace();
			throw err;
		}
		return model;
	}
	
	@RequestMapping(params = "method=unlogin")
	public ModelAndView unlogin(HttpServletRequest request)
	{
		ModelAndView model = new ModelAndView("login");
		HttpSession session = request.getSession();
		session.removeAttribute("userId");
		session.removeAttribute("username");
		session.removeAttribute("roleId");
		return model;
	}
	
	@RequestMapping(params = "method=queryPre")
	public ModelAndView queryPre(HttpServletResponse response , HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView("pageList");
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		String medName = (String)request.getParameter("medName");
		String startTime = (String)request.getParameter("startTime");
		String endTime = (String)request.getParameter("endTime");
		if(ObjectCensor.isStrRegular(userId))
		{
			List userFileLst = loginService.getUserFile(medName , startTime , endTime);
			model.addObject("userFileLst", userFileLst);
			model.setViewName("pageList");
		}
		else
		{
			model.addObject("result", "error");
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(params = "method=showUser")
	public ModelAndView showUser(HttpServletResponse response , HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		if(ObjectCensor.isStrRegular(userId))
		{
			List list = mobileService.getUserList();
			model.addObject("userList", list);
			model.setViewName("view/medUserMan");
		}
		else
		{
			model.addObject("result", "error");
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(params = "method=getUserById")
	public ModelAndView getUserById(String userId , HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		ModelAndView model = new ModelAndView();
		if(session.getAttribute("userId") != null)
		{
			try
			{
				List storeList = mobileService.getStoreList();
				if(ObjectCensor.isStrRegular(userId))
				{
					Map<String,String> map = mobileService.getUserById(userId);
					model.addObject("userNewId", userId);
					model.addObject("username", StringUtil.getMapKeyVal(map, "userName"));
					model.addObject("userpass", StringUtil.getMapKeyVal(map, "password"));
					model.addObject("userrole", StringUtil.getMapKeyVal(map, "roleId"));
					model.addObject("userstore", StringUtil.getMapKeyVal(map, "storeId"));
					model.addObject("flag", 0);
				}
				else
				{
					model.addObject("flag", 1);
				}
				model.addObject("storeList", storeList);
			}
			catch(Exception err)
			{
				err.printStackTrace();
			}
			model.setViewName("view/userOper");
		}
		else
		{
			model.addObject("result", "error");
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(params = "method=showStore")
	public ModelAndView showStore(HttpServletResponse response , HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		if(ObjectCensor.isStrRegular(userId))
		{
			List list = mobileService.getStoreList();
			model.addObject("storeList", list);
			model.setViewName("view/storeInfoMan");
		}
		else
		{
			model.addObject("result", "error");
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(params = "method=getStoreById")
	public ModelAndView getStoreById(String storeId , HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		ModelAndView model = new ModelAndView();
		if(session.getAttribute("userId") != null)
		{
			try
			{
				if(ObjectCensor.isStrRegular(storeId))
				{
					Map<String,String> map = mobileService.getStoreById(storeId);
					model.addObject("storeId", storeId);
					model.addObject("storename", StringUtil.getMapKeyVal(map, "storeName"));
					model.addObject("remark", StringUtil.getMapKeyVal(map, "remark"));
					model.addObject("flag", 0);
				}
				else
				{
					model.addObject("flag", 1);
				}
			}
			catch(Exception err)
			{
				err.printStackTrace();
			}
			model.setViewName("view/storeOper");
		}
		else
		{
			model.addObject("result", "error");
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(params = "method=updateFile")
	public ModelAndView updateUserFileById(HttpServletResponse response , HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession();
		String fileId = request.getParameter("fileId");
		String verUserId = request.getParameter("verUserId");
		String state = request.getParameter("state");
		String comments = request.getParameter("comments");
		try{
			MappingJacksonJsonView view = new MappingJacksonJsonView();
			boolean flag = loginService.updateUserFileById(fileId , state , comments , verUserId);
			model.addObject("result", flag);
			model.setView(view);
		}
		catch(Exception err)
		{
			err.printStackTrace();
			throw err;
		}
		return model;
	}
	
	@RequestMapping(params = "method=userOper")
	public ModelAndView userOper(String userId , String username , String userpass , String userrole , String userstore , HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		ModelAndView model = new ModelAndView();
		if(session.getAttribute("userId") != null)
		{
			try
			{
				if(mobileService.userOper(userId, username, userpass, userrole, userstore))
				{
					List list = mobileService.getUserList();
					model.addObject("userList", list);
					model.setViewName("view/medUserMan");
				}
			}
			catch(Exception err)
			{
				err.printStackTrace();
			}
		}
		if("".equals(model.getViewName()))
		{
			model.addObject("result", "error");
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(params = "method=delUser")
	public void delUser(String userId , HttpServletResponse response , HttpServletRequest request)
	{
		PrintWriter out = null;
		try{
			HttpSession session = request.getSession();
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			if(session.getAttribute("userId") != null)
			{
				if(mobileService.delUser(userId))
				{
					out.print("success");
				}
				else
				{
					out.print("error");
				}
			}
			else
			{
				out.print("error");
			}
		}catch(Exception err)
		{
			
		}
		finally
		{
			out.close();
		}
	}
	
	@RequestMapping(params = "method=storeOper")
	public ModelAndView storeOper(String storeId , String storename , String remark , HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		ModelAndView model = new ModelAndView();
		if(session.getAttribute("userId") != null)
		{
			try
			{
				if(mobileService.storeOper(storeId, storename, remark))
				{
					List list = mobileService.getStoreList();
					model.addObject("storeList", list);
					model.setViewName("view/storeInfoMan");
				}
			}
			catch(Exception err)
			{
				err.printStackTrace();
			}
		}
		if("".equals(model.getViewName()))
		{
			model.addObject("result", "error");
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(params = "method=delStore")
	public void delStore(String storeId , HttpServletResponse response , HttpServletRequest request)
	{
		PrintWriter out = null;
		try{
			HttpSession session = request.getSession();
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			if(session.getAttribute("userId") != null)
			{
				if(mobileService.delStore(storeId))
				{
					out.print("success");
				}
				else
				{
					out.print("error");
				}
			}
			else
			{
				out.print("error");
			}
		}catch(Exception err)
		{
			
		}
		finally
		{
			out.close();
		}
	}
	
	//查询专家挂号订单
	@RequestMapping(params = "method=qryRegisterOrder")
	public ModelAndView qryRegisterOrder(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView("registerOrderList");
		HttpSession session = request.getSession();
		session.setAttribute("userId", "10001");
		String userId = (String)session.getAttribute("userId");
		if(ObjectCensor.isStrRegular(userId))
		{
			String hospitalName = (String)request.getParameter("hospitalName");
			String teamName = (String)request.getParameter("teamName");
			String doctorName = (String)request.getParameter("doctorName");
			String startTime = (String)request.getParameter("startTime");
			String endTime = (String)request.getParameter("endTime");
			List registerOrderList = digitalHealthService.qryRegisterOrder(hospitalName, teamName, doctorName, startTime , endTime, userId);
			model.addObject("registerOrderList", registerOrderList);
			model.addObject("hospitalName", hospitalName);
			model.addObject("teamName", teamName);
			model.addObject("doctorName", doctorName);
			model.addObject("startTime", startTime);
			model.addObject("endTime", endTime);
			model.setViewName("registerOrderList");
		}
		else
		{
			model.addObject("result", "error");
			model.setViewName("login");
		}
		return model;
	}
	
	//预约或作废专家挂号订单
	@RequestMapping(params = "method=appointmentOrinvalidOrder")
	public void appointmentOrinvalidOrder(HttpServletRequest request, HttpServletResponse response)
	{
		PrintWriter out = null;
		try 
		{
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			String orderId = request.getParameter("orderId");
			String optionFlag = request.getParameter("optionFlag");
			boolean flag = digitalHealthService.appointmentOrinvalidOrder(orderId, optionFlag);
			out.println(flag);
			out.close();
		} 
		catch (IOException e) 
		{
			
		}
		finally
		{
			out.close();
		}
	}
}
