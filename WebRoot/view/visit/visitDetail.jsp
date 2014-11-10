<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"  %>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  	<head>
  		<title></title>
    	<link href="<%=path%>/pub/css/sub.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/pub/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="<%=path%>/pub/dialog/lhgdialog.min.js?skin=idialog"></script>
		<script type="text/javascript" src="<%=path%>/js/comm.js"></script>
  	</head>
  
  	<body>
  		<div style="width: 100%; height: 100%;">
	  		<table width='65%' border='0' cellspacing='0' cellpadding='0' style="margin-top: 20px;" align="center">
	  			<tr>
	  				<td align="right" width="10%"><h3>随访名称：</h3></td>
	  				<td width="15%">${visitType }</td>
	  				<td align="right" width="10%"><h3>随访姓名：</h3></td>
	  				<td width="15%">${visitName }</td>
	  				<td align="right" width="5%"><h3>性别：</h3></td>
	  				<td width="10%">${sex }</td>
	  			</tr>
	  		</table>
	  		<table id='table1' width='100%' border='1' cellspacing='0' cellpadding='0' class="maintable2">
	  			<c:forEach items="${sList }" var="lst" varStatus="i">
	  				<c:if test="${fn:length(sList) - 1 != i.index}">
	  					<c:if test="${i.index % 2 == 0}">
	  						<tr>
	  							<td width="30%" align="right">${lst.codeFlagVal }&nbsp;</td>
	  							<td width="20%" align="left">&nbsp;${lst.codeValFlag }</td>
	  					</c:if>
	  					<c:if test="${i.index % 2 != 0}">
	  							<td width="30%" align="right">${lst.codeFlagVal }&nbsp;</td>
	  							<td width="20%" align="left">&nbsp;${lst.codeValFlag }</td>
	  						</tr>
	  					</c:if>
	  				</c:if>
	  				<c:if test="${fn:length(sList) - 1 == i.index}">
	  					<c:if test="${i.index % 2 == 0}">
	  						<tr>
	  							<td width="30%" align="right">${lst.codeFlagVal }&nbsp;</td>
	  							<td width="20%" align="left">&nbsp;${lst.codeValFlag }</td>
	  							<td width="30%" align="right">&nbsp;</td>
	  							<td width="20%" align="left">&nbsp;</td>
							</tr>
	  					</c:if>
	  					<c:if test="${i.index % 2 != 0}">
	  							<td width="30%" align="right">${lst.codeFlagVal }&nbsp;</td>
	  							<td width="20%" align="left">&nbsp;${lst.codeValFlag }</td>
	  						</tr>
	  					</c:if>
	  				</c:if>
	  			</c:forEach>
	  		</table>
  		</div>
  	</body>
</html>
