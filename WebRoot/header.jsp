<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link rel="stylesheet" href="/pub/css/bankList.css" type="text/css"></link>
		<title>无标题文档</title>
		<script language="javascript">
			function unlogin()
			{
				parent.window.location.href = "/mobile.htm?method=unlogin";
			}
		</script>
	</head>
	
	<body>
	 	<div class="top">
	    	<div class="logo">
		    	<div class="baseInfo">
					当前用户:${sessionScope.username}<span style="padding-left:40px">&nbsp;<span style="padding-left:20px">&nbsp;</span><a href="javascript:void(0)" onclick="unlogin()" class="linkmore">注销</a>	
		    	</div>
	    	</div>
	    </div>
	</body>
</html>