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
		<script type="text/javascript" src="<%=path%>/pub/js/jquery-1.9.1.min.js"></script>
		<script language="javascript" type="text/javascript" src="<%=path%>/js/menu.js"></script>
	</head>

	<body>
		<input type="hidden" id="startTime"/>
		<input type="hidden" id="endTime"/>
		<div id="all">
			<div id="menu">
				<ul>
					<c:if test="${sessionScope.userPrivs == '1'}">
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;<a href="/view/order/registerOrderList.jsp" target="main">预约挂号管理</a>
						</li>
					</c:if>
					<c:if test="${sessionScope.userPrivs == '2'}">
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;<a href="/view/question/questionList.jsp?doctorId=<%=doctorId%>" target="main" >用户提问管理</a>
						</li>
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;<a href="/view/doctor/doctors.jsp?doctorId=<%=doctorId%>" target="main">医生信息管理</a>
						</li>
					</c:if>
					<c:if test="${sessionScope.userPrivs == '3'}">
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;<a href="/view/order/registerOrderList.jsp" target="main">预约挂号管理</a>
						</li>
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;<a href="/view/doctor/doctors.jsp?doctorId=<%=doctorId%>" target="main">医生信息管理</a>
						</li>
					</c:if>
					<c:if test="${sessionScope.userPrivs == '4'}">
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;<a href="/view/question/onlineDoctorQuesList.jsp" target="main" >用户提问管理</a>
						</li>
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;<a href="/view/order/registerOrderList.jsp" target="main">预约挂号管理</a>
						</li>
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;<a href="/view/news/newsList.jsp" target="main">信息发布管理</a>
						</li>
						<c:choose>
							<c:when test="${sessionScope.hospitalId == '102'}">
								<li>
									<img src="images/li.jpg" />&nbsp;&nbsp;<a href="/view/wake/addWake.jsp" target="main">新增提醒管理</a>
								</li>
							</c:when>
							<c:otherwise>
								<li>
									<img src="images/li.jpg" />&nbsp;&nbsp;<a href="/view/doctor/doctors.jsp?doctorId=<%=doctorId%>" target="main">医生信息管理</a>
								</li>
							</c:otherwise>
						</c:choose>
						<c:if test="${sessionScope.userPrivs == '4' && sessionScope.hospitalId == '102'}">
							<li style="height:75px;line-height:1.5;" id="accArea">
								<span><img src="images/li.jpg" />&nbsp;&nbsp;<a href="javascript:void(0)" onclick="treeList('accList','accArea')">患者随访</a></span>
								<ul id="accList" style="display:block">
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
							<li style="height:75px;line-height:1.5;" id="docInfoArea">
								<span><img src="images/li.jpg" />&nbsp;&nbsp;<a href="javascript:void(0)" onclick="treeList('docInfoList','docInfoArea')">医生数据管理</a></span>
								<ul id="docInfoList" style="display:block">
									<li>
										<div class="linePic"></div><div class="posPic"></div><a class="abtn" href="/view/doctor/onlineDocotrList.jsp" target="main">在线医生管理</a>
									</li>
									<li>
										<div class="linePic"></div><div class="posPic"></div><a class="abtn" href="/view/doctor/addDoctor.jsp" target="main">新增医生管理</a>
									</li>
									<li>
										<div class="linePicLast"></div><div class="posPic"></div><a class="abtn" href="/view/doctor/doctors.jsp?doctorId=<%=doctorId%>" target="main">医生信息管理</a>
									</li>
								</ul>
							</li>
							<li>
								<img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; 
								<a href="/view/user/userList.jsp" target="main">用户信息管理</a>
							</li>
						</c:if>
						<c:if test="${sessionScope.userPrivs == '5' && sessionScope.hospitalId == '102'}">
							<li style="height:75px;line-height:1.5;" id="accArea">
								<span><img src="images/li.jpg" />&nbsp;&nbsp;<a href="javascript:void(0)" onclick="treeList('accList','accArea')">患者随访</a></span>
								<ul id="accList" style="display:block">
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
							<li style="height:75px;line-height:1.5;" id="docInfoArea">
								<span><img src="images/li.jpg" />&nbsp;&nbsp;<a href="javascript:void(0)" onclick="treeList('docInfoList','docInfoArea')">医生数据管理</a></span>
								<ul id="docInfoList" style="display:block">
									<li>
										<div class="linePic"></div><div class="posPic"></div><a class="abtn" href="/view/doctor/onlineDocotrList.jsp" target="main">在线医生管理</a>
									</li>
									<li>
										<div class="linePic"></div><div class="posPic"></div><a class="abtn" href="/view/doctor/addDoctor.jsp" target="main">新增医生管理</a>
									</li>
									<li>
										<div class="linePicLast"></div><div class="posPic"></div><a class="abtn" href="/view/doctor/doctors.jsp?doctorId=<%=doctorId%>" target="main">医生信息管理</a>
									</li>
								</ul>
							</li>
							<li>
								<img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; 
								<a href="/view/user/userList.jsp" target="main">用户信息管理</a>
							</li>
							<li>
								<img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; 
								<a href="/view/user/userLoginActivity.jsp" target="main">登录活跃度统计</a>
							</li>
						</c:if>
					</c:if>
				</ul>
			</div>
		</div>
	</body>
</html>
