<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  	<head>
		<link href="<%=path%>/pub/css/sub.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/pub/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="<%=path%>/pub/dialog/lhgdialog.min.js?skin=idialog"></script>
		<script type="text/javascript" src="<%=path%>/pub/js/calendar.js"></script>
		<script type="text/javascript" src="<%=path%>/pub/js/date.js"></script>
		<script type="text/javascript" src="<%=path%>/js/comm.js"></script>
		<script type="text/javascript" src="<%=path%>/js/json2.js"></script>
		<script type="text/javascript" src="<%=path%>/js/json.js"></script>
		<script type="text/javascript" src="<%=path%>/js/onlineDoctor.js"></script>
  	</head>
  
  	<body>
  		<form action="">
			<div class="main" align="center">
				<table width="650px" border="0" cellspacing="10" cellpadding="0" align='center'>
					<tr>
						<td align="right">医生姓名：</td>
						<td><input type="text" id="name" name="name" class="subtext" style="width: 150px"/></td>
						<td align="right">联系电话：</td>
						<td><input type="text" id="telephone" name="telephone" class="subtext" style="width: 150px"/></td>
					</tr>
					<tr>
						<td align="right">医生职称：</td>
						<td><input type="text" id="post" name="post" class="subtext" style="width: 150px"/></td>
						<td align="right">挂号费用：</td>
						<td><input type="text" id="register_fee" name="register_fee" class="subtext" style="width: 150px"/></td>
					</tr>
					<tr>
						<td align="right">医生性别：</td>
						<td>
							<input type="radio" name="sex" value="男"/>男
							<input type="radio" name="sex" value="女"/>女
						</td>
						<td align="right">所属科室：</td>
						<td>
							<select id="team_id" name="team_id" class="subselect" style="width: 150px">
								<option value="">---请选择---</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">医生介绍：</td>
						<td colspan="3">
							<textarea id="introduce" name="introduce" rows="8" cols="77" style="border:1px solid #C0DC56;background:#fff;margin-bottom: 10px"></textarea>
						</td>
					</tr>
					<tr>
						<td align="right">擅长领域：</td>
						<td colspan="3">
							<textarea id="skill" name="skill" rows="8" cols="77" style="border:1px solid #C0DC56;background:#fff;margin-bottom: 10px"></textarea>
						</td>
					</tr>
				</table>
				<table width="100%" cellspacing="0" cellpadding="0" align='center'>
					<tr align='center'>
						<td>
							<input type="button" value="新增" class="button3" onclick="addDoctor()"/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="取消" class="button" onclick="cancel()"/>
						</td>
					</tr>
				</table>
			</div>  		
  		</form>
  	</body>
</html>
