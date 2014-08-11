<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  	<head>
    	<title>新增分类</title>
    	<link href="<%=path%>/pub/css/sub.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/pub/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="<%=path%>/pub/dialog/lhgdialog.min.js?skin=idialog"></script>
		<script type="text/javascript" src="<%=path%>/js/comm.js"></script>
		<script type="text/javascript" src="<%=path%>/js/news.js"></script>
  	</head>
  
  	<body>
  		<table width="400px" border="0" cellspacing="10" cellpadding="0" align='center'>
  			<tr>
  				<td colspan="2" align="center" style="margin-bottom: 40px; margin-top: 40px;font-weight: bold; font-size: 18px;">新增分类信息</td>
  			</tr>
  			<tr>
  				<td width="12%" align="right">文章类型</td>
				<td width="30%">
					<select id="newsTypeId" name="newsTypeId" class="subselect" style="width: 150px">
						<option value="NEWS" selected="selected">患教中心</option>
						<option value="BAIKE">健康百科</option>
					</select>
				</td>
  			</tr>
  			<tr>
  				<td width="12%" align="right">新增分类名称</td>
  				<td width="30%">
  					<input type="text" class="subtext" id="typeName" style="width: 150px"/>
  				</td>
  			</tr>
  		</table>
  	</body>
</html>
