<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>无标题文档</title>
		<link href="<%=path%>/pub/css/sub.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="/pub/css/bankList.css" type="text/css"></link>
	  	<link rel="stylesheet" href="/pub/css/manPage.css" type="text/css"></link>
	  	<link rel="stylesheet" href="../pub/css/medUserMan.css" type="text/css" />
		<script type="text/javascript" src="pub/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="<%=path%>/pub/dialog/lhgdialog.min.js?skin=idialog"></script>
		<script type="text/javascript" src="../js/medUserMan.js"></script>
	</head>
	<body>
		<div id="template" style="height:500px;overflow:auto">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="maintable1">
				<tr class="tabletop">
					<td align="center">选择</td>
					<td align="center">用户姓名</td>
					<td align="center">所属角色</td>
					<td align="center" width="20%">注册时间</td>
					<td align="center" width="10%">所属药店</td>
				</tr>
				<c:forEach items="${userList}" var="node" varStatus="i">
					<tr>
						<td><input type="checkbox" name="checkUser" value="${node.user_id}" onclick="chkClick(this)"/></td>
						<td>${node.user_name}</td>
						<td>
							<c:choose>
								<c:when test="${node.role_id == 1}">职业医师</c:when>
								<c:otherwise>药店用户</c:otherwise>
							</c:choose>
						</td>
						<td><fmt:formatDate value="${node.create_date}" pattern="yyyy-MM-dd"/></td>
						<td>${node.store_name}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div class="ctrl">
			<input type="button" onclick="addUser()" class="button3" value="新增" />&nbsp;&nbsp;
			<input type="button" onclick="delUserFunc()" class="button1" value="删除" />&nbsp;&nbsp;
			<input type="button" onclick="updateUser()" class="button1" value="修改" />
		</div>
	</body>
</html>