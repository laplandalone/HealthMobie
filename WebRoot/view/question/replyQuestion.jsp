<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String telephone = request.getParameter("telephone");
	String question = request.getParameter("question");
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
  		<table border='0' cellspacing='10' width="400px">
			<tr>
				<td id="telephone"><%=telephone %>:</td>
				<td id="question"><%=question %></td>
			</tr>
			<tr>
				<td valign='top'>回复:</td>
				<td><textarea cols='40' rows='4' id='replyContent'></textarea></td>
			</tr>
			<tr>
				<td>可见范围:</td>
				<td>
					<input type="radio" name='authType' value='public' checked='checked' id='public' />可见
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name='authType' value='private' id='private' />不可见
				</td>
			</tr>
		</table>
 	</body>
</html>
