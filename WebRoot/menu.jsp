﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String doctorId = request.getParameter("doctorId");
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
		<div id="all">
			<div id="menu">
				<ul>
					<c:if test="${sessionScope.userPrivs=='1'}">
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; 
							<a href="/registerOrderList.jsp" target="main">预约挂号管理</a>
						</li>
					</c:if>
					<c:if test="${sessionScope.userPrivs=='2'}">
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; 
							<a href="/view/question/questionList.jsp?&doctorId=<%=doctorId%>" target="main" >用户提问管理</a>
						</li>
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; 
							<a href="/doctor.htm?method=queryPre&doctorId=<%=doctorId%>" target="main">医生信息管理</a>
						</li>
					</c:if>
					<c:if test="${sessionScope.userPrivs=='3'}">
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; 
							<a href="/registerOrderList.jsp" target="main">预约挂号管理</a>
						</li>
						<li>
							<img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; 
							<a href="/doctor.htm?method=queryPre&doctorId=<%=doctorId%>" target="main">医生信息管理</a>
						</li>
					</c:if>
					<!-- 
            		<li><img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; <a href="/mobile.htm?method=queryPre" target="main">电子处方管理</a></li>
           	 		<li><img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; <a href="/mobile.htm?method=showUser" target="main">系统用户管理</a></li>
           			<li><img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; <a href="/mobile.htm?method=showStore" target="main">药店资料管理</a></li>
           			<li><img src="images/li.jpg" />&nbsp;&nbsp;&nbsp; <a href="/mobile.htm?method=showUser" target="main">药店用户管理</a></li>
          	 		-->
				</ul>
			</div>
		</div>
	</body>
</html>