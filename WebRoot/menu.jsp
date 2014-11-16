<%@ page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String doctorId = request.getParameter("doctorId");
	Date today = new Date();
	Calendar c = Calendar.getInstance(); 
	c.setTime(today);
	c.add(Calendar.MONTH, -1);
	Date date = c.getTime();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String startTime = sdf.format(date);
	String endTime = sdf.format(today);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<style type="text/css">
			html,body {
				height: 100%;
				overflow: hidden;
			} /*为兼容ie7,ff*/
			body {
				font-family: Arial, Helvetica, sans-serif;
				font-size: 12px;
				margin: 0px;
				text-align: center;
				border-right: 1px #ccc solid;
			}
			a {
				color: #000;
				text-decoration: none;
			}
			#menu img {
				_margin-top: 12px;
			} /*没办法,ie6对list-style-image支持不好*/
			#all {
				width: 100%;
				height: 100%;
			}
			#menu {
				width: 100%;
			}
			#menu ul {
				padding: 0;
				margin: 0;
				list-style: none;
			}	
			#menu ul li {
				background-image: url(/match/public/images/menu_bg.gif);
				background-repeat: repeat-x;
				background-position: center;
				height: 32px;;
				margin-top: 2px;
				margin-bottom: 2px;
				border: 1px #ccc solid;
				line-height: 2.8;
			}
		</style>
	</head>

	<body>
		<input type="hidden" id="startTime"/>
		<input type="hidden" id="endTime"/>
		<div id="all">
			<div id="menu">
				<ul>
					<c:if test="${sessionScope.userPrivs=='1'}">
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; 
							<a href="/mobile.htm?method=qryRegisterOrder&state=&teamId=&doctorId=<%=doctorId%>&startTime=<%=startTime %>&endTime=<%=endTime %>" target="main">预约挂号管理</a>
						</li>
					</c:if>
					<c:if test="${sessionScope.userPrivs=='2'}">
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; 
							<a href="/ques.htm?method=queryPre&doctorId=<%=doctorId%>&startTime=<%=startTime %>&endTime=<%=endTime %>" target="main" >用户提问管理</a>
						</li>
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; 
							<a href="/doctor.htm?method=queryPre&doctorId=<%=doctorId%>" target="main">医生信息管理</a>
						</li>
					</c:if>
					<c:if test="${sessionScope.userPrivs=='3'}">
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; 
							<a href="/mobile.htm?method=qryRegisterOrder&state=&teamId=&doctorId=<%=doctorId%>&startTime=<%=startTime %>&endTime=<%=endTime %>" target="main">预约挂号管理</a>
						</li>
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; 
							<a href="/doctor.htm?method=queryPre&doctorId=<%=doctorId%>" target="main">医生信息管理</a>
						</li>
					</c:if>
					<c:if test="${sessionScope.userPrivs=='4'}">
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; 
							<a href="/ques.htm?method=queryPre&doctorId=<%=doctorId%>&startTime=<%=startTime %>&endTime=<%=endTime %>" target="main" >用户提问管理</a>
						</li>
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; 
							<a href="/mobile.htm?method=qryRegisterOrder&state=&teamId=&doctorId=<%=doctorId%>&startTime=<%=startTime %>&endTime=<%=endTime %>" target="main">预约挂号管理</a>
						</li>
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; 
							<a href="/doctor.htm?method=queryPre&doctorId=<%=doctorId%>" target="main">医生信息管理</a>
						</li>
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; 
							<a href="/news.htm?method=qryNewsList&startTime=<%=startTime %>&endTime=<%=endTime %>&newsType=&typeId=&state=" target="main">信息发布管理</a>
						</li>
						<c:if test="${sessionScope.userPrivs=='4' && sessionScope.hospitalId=='102'}">
							<li>
								<img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; 
								<a href="/view/doctor/onlineDocotrList.jsp" target="main">在线医生管理</a>
							</li>
							<li>
								<img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; 
								<a href="/view/wake/addWake.jsp" target="main">新增提醒管理</a>
							</li>
							<li>
								<img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; 
								<a href="/view/visit/patientVisitList.jsp?visitType=asd" target="main">先心手术随访</a>
							</li>
							<li>
								<img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; 
								<a href="/view/visit/patientVisitList.jsp?visitType=mvr" target="main">房颤手术随访</a>
							</li>
							<li>
								<img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; 
								<a href="/view/doctor/addDoctor.jsp" target="main">新增医生管理</a>
							</li>
						</c:if>
					</c:if>
				</ul>
			</div>
		</div>
	</body>
</html>
