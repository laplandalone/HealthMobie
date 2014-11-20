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
		<link rel="stylesheet" href="<%=path%>/pub/css/menu.css" type="text/css" />
	</head>

	<body>
		<input type="hidden" id="startTime"/>
		<input type="hidden" id="endTime"/>
		<div id="all">
			<div id="menu">
				<ul>
					<c:if test="${sessionScope.userPrivs=='1'}">
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;<a href="/mobile.htm?method=qryRegisterOrder&state=&teamId=&doctorId=<%=doctorId%>&startTime=<%=startTime %>&endTime=<%=endTime %>" target="main">预约挂号管理</a>
						</li>
					</c:if>
					<c:if test="${sessionScope.userPrivs=='2'}">
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;<a href="/ques.htm?method=queryPre&doctorId=<%=doctorId%>&startTime=<%=startTime %>&endTime=<%=endTime %>" target="main" >用户提问管理</a>
						</li>
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;<a href="/doctor.htm?method=queryPre&doctorId=<%=doctorId%>" target="main">医生信息管理</a>
						</li>
					</c:if>
					<c:if test="${sessionScope.userPrivs=='3'}">
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;<a href="/mobile.htm?method=qryRegisterOrder&state=&teamId=&doctorId=<%=doctorId%>&startTime=<%=startTime %>&endTime=<%=endTime %>" target="main">预约挂号管理</a>
						</li>
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;<a href="/doctor.htm?method=queryPre&doctorId=<%=doctorId%>" target="main">医生信息管理</a>
						</li>
					</c:if>
					<c:if test="${sessionScope.userPrivs=='4'}">
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;<a href="/ques.htm?method=queryPre&doctorId=<%=doctorId%>&startTime=<%=startTime %>&endTime=<%=endTime %>" target="main" >用户提问管理</a>
						</li>
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;<a href="/mobile.htm?method=qryRegisterOrder&state=&teamId=&doctorId=<%=doctorId%>&startTime=<%=startTime %>&endTime=<%=endTime %>" target="main">预约挂号管理</a>
						</li>
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;<a href="/news.htm?method=qryNewsList&startTime=<%=startTime %>&endTime=<%=endTime %>&newsType=&typeId=&state=" target="main">信息发布管理</a>
						</li>
						<c:choose>
							<c:when test="${sessionScope.hospitalId=='102'}">
								<li>
									<img src="images/li.jpg" />&nbsp;&nbsp;<a href="/view/wake/addWake.jsp" target="main">新增提醒管理</a>
								</li>
							</c:when>
							<c:otherwise>
								<li>
									<img src="images/li.jpg" />&nbsp;&nbsp;<a href="/doctor.htm?method=queryPre&doctorId=<%=doctorId%>" target="main">医生信息管理</a>
								</li>
							</c:otherwise>
						</c:choose>
						<c:if test="${sessionScope.userPrivs=='4' && sessionScope.hospitalId=='102'}">
							<li style="height:75px;line-height:1.5;">
								<span><img src="images/li.jpg" />&nbsp;&nbsp;患者随访</span>
								<ul>
									<li>
										<div class="linePic"></div><div class="posPic"></div><a class="abtn" href="/view/visit/patientVisitList.jsp?visitType=asd" target="main">先心手术随访</a>
									</li>
									<li>
										<div class="linePic"></div><div class="posPic"></div><a class="abtn" href="/view/visit/patientVisitList.jsp?visitType=mvr" target="main">房颤手术随访</a>
									</li>
									<li>
										<div class="linePicLast"></div><div class="posPic"></div><a class="abtn" href="/view/visit/patientVisitList.jsp?visitType=gxb" target="main">冠心病手术随访</a>
									</li>
								</ul>
							</li>
							<li style="height:75px;line-height:1.5;">
								<span><img src="images/li.jpg" />&nbsp;&nbsp;医生数据管理</span>
								<ul>
									<li>
										<div class="linePic"></div><div class="posPic"></div><a class="abtn" href="/view/doctor/onlineDocotrList.jsp" target="main">在线医生管理</a>
									</li>
									<li>
										<div class="linePic"></div><div class="posPic"></div><a class="abtn" href="/view/doctor/addDoctor.jsp" target="main">新增医生管理</a>
									</li>
									<li>
										<div class="linePicLast"></div><div class="posPic"></div><a class="abtn" href="/doctor.htm?method=queryPre&doctorId=<%=doctorId%>" target="main">医生信息管理</a>
									</li>
								</ul>
							</li>
						</c:if>
					</c:if>
				</ul>
			</div>
		</div>
	</body>
</html>
