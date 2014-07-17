<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统管理首页</title>
</head>
<link rel="stylesheet" href="/pub/css/bankList.css" type="text/css"></link>
<frameset cols="*" rows="64,*"  frameborder="0" framespacing="0">
    <frame src="header.jsp" name="header" scrolling="no" />
    <frameset cols="150, *" rows="*" id="frame_main"  frameborder="0" framespacing="0" >    
        <frame src="menu.jsp" name="menu" />
        <frame src="main.jsp" name="main" />
    </frameset>	
</frameset>
<noframes>
<body>
	<c:if test="${empty sessionScope.userId}">
		<c:redirect url="/login.jsp" />
	</c:if>
</body>
</noframes></html>
