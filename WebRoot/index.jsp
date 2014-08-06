<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>系统管理首页</title>
		<link rel="stylesheet" href="/pub/css/bankList.css" type="text/css"></link>
	</head>
	<body>
		<div id="container" style="position:fixed; height:100%; width:100%; background-color: white;">
			<div id="header" style="margin:0 auto;">
				<iframe src="header.jsp" name="header" frameborder="0" scrolling="no" width="100%" height="64px"></iframe>
			</div>
			<div id="frame_main" style="margin-top: 0px;">
				<div id="main_left" style="float:left;">
					<iframe src="menu.jsp?&doctorId=${doctorId}" name="menu" frameborder=0" id="menuIframe" width="150px" height="900px"></iframe>
				</div>
				<div id="main_right" style="float:left;">
					<iframe src="main.jsp" name="main" frameborder=0" id="mainIframe" width="1200px" height="900px"></iframe>
				</div>
			</div>
			<div style="clear:both;"></div>
		</div>
		<div class="shadowlock" id="lockDiv" style="display: none;"></div>
	</body>
</html>