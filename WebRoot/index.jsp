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
		<script type="text/javascript" src="<%=path%>/pub/js/jquery-1.9.1.min.js"></script>
		<style type="text/css">
        	#lockDiv{ display: none;  position: absolute;  top: 0%;  left: 0%;  width: 100%;  height: 100%;  background-color: #dce2f1;  z-index:1001;  -moz-opacity: 0.7;  opacity:.70;  filter: alpha(opacity=70);}
		</style>
		<script type="text/javascript">
			$(document).ready(function(){
				var width = document.body.scrollWidth;// 获取浏览器内容宽度
				if(width == 0)
				{
					width = document.documentElement.scrollWidth;
				}
				// 获取浏览器内容高度
				var height = document.body.scrollHeight;
				if(height == 0)
				{
					height = document.documentElement.scrollHeight;
				}
				
				var menu = document.getElementById("menuIframe");
				menu.style.height = (height - 64) + "px";// 设置iframe的高度
				
				var main = document.getElementById("mainIframe");
				main.style.height = (height - 64) + "px";// 设置iframe的高度
				main.style.width = (width - 150) + "px";
			
				//调用函数
				var menuStyle = function() {
					var height = $(window).height();
					var menuIframe = $("#menuIframe");
					menuIframe.height(height - 64);
				}
				
				var mainStyle = function()
				{
					var width = $(window).width();
					var height = $(window).height();
					var mainIframe = $("#mainIframe");
					mainIframe.height(height - 64);
					mainIframe.width(width - 150);
				}
				//注册加载事件 
				$("#menuIframe").load(menuStyle); 
				$("#mainIframe").load(mainStyle); 
				//注册窗体改变大小事件 
				$(window).resize(menuStyle);
				$(window).resize(mainStyle);
			});
		</script>
	</head>
	<body>
		<div id="lockDiv"></div>
		<div id="container" style="position:fixed; height:100%; width:100%; background-color: white;">
			<div id="header" style="margin:0 auto;">
				<iframe src="header.jsp" name="header" id="headerIframe" frameborder="0" scrolling="no" width="100%" height="64px"></iframe>
			</div>
			<div id="frame_main" style="margin-top: 0px; width: 100%; ">
				<div id="main_left" style="float:left; width: 150px">
					<iframe src="menu.jsp?&doctorId=${doctorId}" name="menu" frameborder="0" id="menuIframe" width="150px"></iframe>
				</div>
				<div id="main_right" style="float:left;">
					<iframe src="main.jsp" name="main" frameborder="0" id="mainIframe"></iframe>
				</div>
			</div>
			<div style="clear:both;"></div>
		</div>
	</body>
</html>