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
		<script type="text/javascript" src="../js/storeInfoMan.js"></script>
	</head>
	<body>
		<div id="template" style="height:500px;overflow:auto">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="maintable1">
				<tr class="tabletop">
					<td align="center">选择</td>
					<td align="center">药店名称</td>
					<td align="center">备注信息</td>
					<td align="center" width="20%">创建时间</td>
				</tr>
				<c:forEach items="${storeList}" var="node" varStatus="i">
					<tr>
						<td><input type="checkbox" name="checkStore" value="${node.storeId}" onclick="chkClick(this)"/></td>
						<td>${node.storeName}</td>
						<td>${node.remark}</td>
						<td><fmt:formatDate value="${node.createDate}" pattern="yyyy-MM-dd"/></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div class="ctrl">
			<input type="button" onclick="addStore()" class="button3" value="新增" />&nbsp;&nbsp;
			<input type="button" onclick="delStoreFunc()" class="button3" value="删除" />&nbsp;&nbsp;
			<input type="button" onclick="updateStore()" class="button3" value="修改" />
		</div>
	</body>
</html>