<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>userOper.jsp
		<title>Insert title here</title>
		<script type="text/javascript" src="pub/js/jquery-1.9.1.min.js"></script>
		<link href="<%=path%>/pub/css/sub.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="/pub/css/bankList.css" type="text/css"></link>
	  	<link rel="stylesheet" href="/pub/css/manPage.css" type="text/css"></link>
	  	<script type="text/javascript" src="pub/dialog/lhgdialog.min.js?skin=idialog"></script>
	  	<script language="javascript">
	  		function enterClick()
	  		{
	  			var username = document.getElementById("username");
	  			var userpass = $("#userpass");
	  			var userrepass = $("#userrepass");
	  			if(username != null && $.trim(username.value) == "")
	  			{
	  				alert("用户账户不能为空!");
	  				username.focus();
	  			}
	  			else if($.trim(userpass.val()) == "")
	  			{
	  				alert("用户密码不能为空!");
	  				userpass.focus();
	  			}
	  			else if($.trim(userrepass.val()) == "")
	  			{
	  				alert("确认密码不能为空!");
	  				userrepass.focus();
	  			}
	  			else if($.trim(userpass.val()) != $.trim(userrepass.val()))
	  			{
	  				alert("用户密码与确认密码不一致!");
	  				userpass.val("");
	  				userrepass.val("");
	  				userpass.focus();
	  			}
	  			else
	  			{
	  				$("#form").submit();
	  			}
	  		}
	  		
	  		function cancelClick()
	  		{
	  			window.location.href = "/mobile.htm?method=showUser";
	  		}
	  	</script>
	</head>
	<body>
		<form action="/mobile.htm?method=userOper" method="post" id="form">
			<input type="hidden" name="userId" value="${userNewId}" />
			<div id="template" style="height:500px;overflow:auto">
				<table align="center" width="400px" border="0" cellspacing="10" cellpadding="0" style="margin-top:20px">
					<tr>
						<td align="right">用户账户:</td>
						<td>
							<c:choose>
								<c:when test="${flag == 1}">
									<input type="text" name="username" id="username" value="" style="width:150px" />
								</c:when>
								<c:otherwise>
									${username}
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td align="right">用户密码:</td>
						<td><input type="password" name="userpass" id="userpass" value="${userpass}" style="width:150px" /></td>
					</tr>
					<tr>
						<td align="right">确认密码:</td>
						<td><input type="password" name="userrepass" id="userrepass" value="${userpass}" style="width:150px" /></td>
					</tr>
					<tr>
						<td align="right">用户角色:</td>
						<td>
							<select name="userrole" value="${userrole}" style="width:155px" >
								<c:choose>
									<c:when test="${userrole == 0}">
										<option value="0" selected="selected">药店用户</option>
										<option value="1">职业医师</option>
									</c:when>
									<c:otherwise>
										<option value="0">药店用户</option>
										<option value="1" selected="selected">职业医师</option>
									</c:otherwise>
								</c:choose>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">所属药店:</td>
						<td>
							<select name="userstore" value="${userstore}" style="width:155px" >
							   
										<option value="1" selected="selected">无</option>
								
								<c:forEach items="${storeList}" var="node">
									<c:choose>
										<c:when test="${userstore == node.storeId}">
											<option value="${node.storeId}" selected="selected">${node.storeName}</option>
										</c:when>
										<c:otherwise>
											<option value="${node.storeId}">${node.storeName}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input style="width: 80px; height: 28px; border-top-color: currentColor; border-right-color: currentColor; border-bottom-color: currentColor; border-left-color: currentColor; border-top-width: medium; border-right-width: medium; border-bottom-width: medium; border-left-width: medium; border-top-style: none; border-right-style: none; border-bottom-style: none; border-left-style: none; cursor: pointer; background-image: url(/pub/images/btn1_r1_c2.png);" onclick="enterClick()" type="button" value="确定"/>&nbsp;&nbsp;
							<input style="width: 80px; height: 28px; border-top-color: currentColor; border-right-color: currentColor; border-bottom-color: currentColor; border-left-color: currentColor; border-top-width: medium; border-right-width: medium; border-bottom-width: medium; border-left-width: medium; border-top-style: none; border-right-style: none; border-bottom-style: none; border-left-style: none; cursor: pointer; background-image: url(/pub/images/btn1_r1_c2.png);" onclick="cancelClick()" type="button" value="取消"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>