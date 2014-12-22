<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	System.out.print("path:"+path);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  	<head>
	    <title>登录</title>
	  	<script type="text/javascript" src="<%=path%>/pub/js/jquery-1.9.1.min.js"></script>
	  	<script type="text/javascript" src="<%=path%>/pub/js/art/artDialog.js?skin=default"></script>
	  	<script type="text/javascript" src="<%=path%>/js/login.js"></script>
	  	<link rel="stylesheet" href="<%=path%>/pub/css/bankList.css" type="text/css"></link>
	  	<link rel="stylesheet" href="<%=path%>/pub/css/login.css" type="text/css"></link>
  	</head>
  
  	<body>
  		<c:if test="${! empty result }">
	  		<c:choose>
		  		<c:when test="${'error' == result}">
		  			<script language="javascript">
		  				$(document).ready(function(){
		  					art.dialog({
						   	 	lock: true,
						    	content: "用户名或密码不正确",
						   	 	icon: 'error',
						    	ok: function () {
						    		window.location.href = "/login.jsp";
						        	return true;
						    	},
							});	
		  				});
		  			</script>
		  		</c:when>
		  		<c:otherwise>
		  			<script language="javascript">
		  				$(document).ready(function(){
		  					art.dialog({
						   	 	lock: true,
						    	content: "请重新登录",
						   	 	icon: 'error',
						    	ok: function () {
						    		window.location.href = "/login.jsp";
						        	return true;
						    	},
							});	
		  				});
		  			</script>
		  		</c:otherwise>
	  		</c:choose>
  		</c:if>
    	<div class="top">
    		<div class="logo">&nbsp;</div>
    	</div>
    	<div class="content">
    		<div class="conttop">
    			<div class="titleleft">&nbsp;</div>
    			<div class="titlemain">
    				<div class="titlemaintop">&nbsp;</div>
    				<div class="titlemaincont">系统管理后台</div>
    			</div>
    			<div class="titleright">&nbsp;</div>
    		</div>
    		<div class="contmain">
    			<div class="contmaint">
	    			<div class="contpayleft">&nbsp;</div>
	    			<div class="contpaymain">&nbsp;</div>
	    			<div class="contpayright">&nbsp;</div>
    			</div>
    			<div class="contmainb">
	    			<div class="contbankleft">&nbsp;</div>
	    			<div class="contbankmain">
	    				<form action="mobile.htm?method=login" method="post" onsubmit="return commit();">
							<table align="center" cellpadding="6" cellspacing="6" border="0" width="300" height="150">
								<tr>
									<td align="right" width="100">用户名</td>
									<td align="left" width="200"><input type="text" id="username" name="username" style="width:200px;height:20px;line-height:20px;padding-left:2px;" /></td>
								</tr>
								<tr>
									<td align="right">密　码</td>
									<td align="left"><input type="password" id="userpass" name="userpass" style="width:200px;height:20px;line-height:20px;padding-left:2px;" /></td>
								</tr>
								<tr>
									<td colspan="2" align="center">
										<input type="submit" style="background-image:url('/pub/img/submit.gif');width:96px;height:32px;border:none;cursor:pointer" value="" />&nbsp;&nbsp;
										<input type="reset" style="background-image:url('/pub/img/reset.gif');width:96px;height:32px;border:none;cursor:pointer" value="" />
									</td>
								</tr>
							</table>
						</form>
					</div>
	    			<div class="contbankright">&nbsp;</div>
    			</div>
    		</div>
		    <div class="bottom">
		    	<div class="bottomleft"></div>
		    	<div class="bottomcont"></div>
		    	<div class="bottomright"></div>
		    </div>
    	</div>
  	</body>
</html>