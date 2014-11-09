<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title></title>
		<link href="<%=path%>/pub/css/sub.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/pub/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="<%=path%>/pub/dialog/lhgdialog.min.js?skin=idialog"></script>
		<script type="text/javascript" src="<%=path%>/pub/js/calendar.js"></script>
		<script type="text/javascript" src="<%=path%>/pub/js/date.js"></script>
		<script type="text/javascript" src="<%=path%>/js/comm.js"></script>
		<script type="text/javascript">
			function updateOrder()
			{
				var teamId = '${doctor.team_id}';
				var doctorId = '${doctor.doctor_id}';
				var fee = '${doctor.fee}';
				var num = '${doctor.num}';
				var url = "/view/doctor/registerTime.jsp?&teamId="+teamId+"&doctorId="+doctorId+"&fee="+fee+"&num="+num;
				window.location.href=url;
			}
			
			function updateDoctor()
			{
				var name = $("#name");
				var psw = $("#password");
				var confimPsw = $("#confimPassword");
				if(name.val() == '')
				{
					$.dialog.alert("用户名为空!", function(){return true;});
					return;
				}
				if(psw.val() == '')
				{
					$.dialog.alert("密码为空!", function(){return true;});
					return;
				}
				if(psw.val() != confimPsw.val())
				{
					$.dialog.alert("密码不一致请重新输入!", function(){return true;});
					return;
				}
			   	window.location.href="/doctor.htm?method=updateDoctor&name="+name.val()+"&password="+psw.val()+"&confimPassword="+confimPsw.val()+"&doctorId="+'${doctor.doctor_id}';
			}

			function refundAjax()
			{
				var name = $("#name");
				var psw = $("#password");
				var confimPsw = $("#confimPassword");
				var introduce=$("#introduce");
				var skill=$("#skill");
				var post = $("#post").val();
				var time = $("#work_time").val();
				var address = $("#work_address").val();
			    var fee = $("#fee");
				if(name.val() == '')
				{
					$.dialog.alert("用户名为空!", function(){return true;});
					return;
				}
			
				if(psw.val() == '')
				{
					$.dialog.alert("密码为空!", function(){return true;});
					return;
				}
				
				if(psw.val() != confimPsw.val())
				{
					$.dialog.alert("密码不一致请重新输入!", function(){return true;});
					return;
				}
				$.ajax({
					url:"/doctor.htm?method=updateDoctor",
					type:"POST",
					data:"introduce="+introduce.val()+"&skill="+skill.val()+"&name="+name.val()+"&password="+psw.val()+"&confimPassword="+confimPsw.val()+"&doctorId="+'${doctor.doctor_id}'+"&fee="+fee.val()+"&post="+post+"&time="+time+"&address="+address,
					
					success:function(data)
					{
						if(data=='success')
						{
							$.dialog.alert('操作成功',function()
							{
								//window.location.href="/ques.htm?method=queryPre";
							});
						}else
						{
							$.dialog.alert('操作失败',function()
									{
										//window.location.href="/ques.htm?method=queryPre";
									});
						}
						
					},
					error:function(tata)
					{
						$.dialog.alert('操作失败',function()
								{
							//window.location.href="/ques.htm?method=queryPre";
						});
					}
				});
			}
		</script>
	</head>
	<body>
		<div id="template" style="height:100%;overflow:auto">
			<table width="70%" border="0" cellspacing="0" cellpadding="0" align='center' >
				<tr align='left'>
				<td>
					<div class="titleba">
						<h3>医生基本信息</h3>
					</div>
					</td>
				</tr>
			</table>
			<table width="70%" border="1" cellspacing="0" cellpadding="0" align='center' class="maintable1">
				<tr>
					<td align="center" width='30%'>医生名称</td>
					<td align="left">${doctor.name}</td>
				</tr>
				<tr>
					<td align="center" width='30%'>电话</td>
					<td align="left">${doctor.telephone}</td>
				</tr>
				<tr>
					<td align="center" width='20%'>性别</td>
					<td align="left">${doctor.sex}</td>
				</tr>
				<tr>
					<td align="center" width='20%'>科室</td>
					<td align="left">${doctor.team_name}</td>
				</tr>
				<tr>
					<td align="center" width='20%'>挂号费</td>
					<td align="left">
						<input type="text" id="fee" class="subtext" value='${doctor.register_fee}'>
					</td>
				</tr>
				<tr>
					<td align="center" width='20%'>门诊时间</td>
					<td align="left">
						<input type="text" id="work_time" class="subtext" value='${doctor.work_time}'>
					</td>
				</tr>
				<tr>
					<td align="center" width='20%'>门诊地点</td>
					<td align="left">
						<input type="text" id="work_address" class="subtext" value='${doctor.work_address}'>
					</td>
				</tr>
				<tr>
					<td align="center" width='20%'>职称</td>
					<td align="left">
						<input type="text" id="post" class="subtext" value='${doctor.post}'>
					</td>
				</tr>
				<tr>
					<td align="center" width='20%'>登录用户名</td>
					<td align="left">
						<input type="text" id="name" class="subtext" value='${doctor.manager_name}'>
					</td>
				</tr>
				<tr>
					<td align="center" width='30%'>密码</td>
					<td align="left">
						<input type="password" class="subtext" id="password" value='${doctor.password}'>
					</td>
				</tr>
				<tr>
					<td align="center" width='30%'>确认密码</td>
					<td align="left">
						<input type="password" class="subtext" id="confimPassword" value='${doctor.password}'>
					</td>
				</tr>
				<tr>
					<td align="center" width='30%'>医生简介</td>
					<td align="left">
						<textarea rows='4' cols="93" class="textarea" id="introduce" ><c:out value="${doctor.introduce}" /></textarea>
					</td>
				</tr>
				<tr>
					<td align="center" width='30%'>擅长</td>
					<td align="left">
						<textarea rows='4' cols="93" class="textarea" id="skill" ><c:out value="${doctor.skill}" /></textarea>
					</td>
				</tr>
			</table>
			<table width="70%" border="0" cellspacing="0" cellpadding="0" align='center' >
				<br />
				<tr align='center'>
				<td>
					<input type="button" align='center' onclick="refundAjax()" class="button3" value="修改信息" />
					</td>
				</tr>
			</table>
			<br />
			<table width="70%" border="0" cellspacing="0" cellpadding="0" align='center'>
				<tr align='left'>
				<td>
					<div class="titleba">
						<h3>医生预约时间</h3>
					</div>
					</td>
				</tr>
			</table>
			<table width="70%" border="1" cellspacing="0" cellpadding="0" align='center' class="maintable1">
				<tr class="tabletop">
					<td align="center">工作日</td>
					<td align="center" width="20%">上午/下午</td>
					<td align="center" width="20%">预约费</td>
					<td align="center" width="20%">预约数量</td>
				</tr>
				<c:forEach items="${registers }" var="register" varStatus="i">
					<tr>
						<td align="center" width='15%'>
							<c:out value="周${register.register_week}" />
						</td>
						<td align="center">
							<c:out value="${register.day_type }" />
						</td>
						<td align="center">
							<c:out value="${register.register_fee }" />
						</td>
						<td align="center">
							<c:out value="${register.register_num }" />
						</td>
					</tr>
				</c:forEach>
			</table>
			<table width="50%" border="0" cellspacing="0" cellpadding="0" align='center' >
				<br />
				
				<tr align='center'>
				<td>
					<input type="button" align='center' onclick="updateOrder()" class="button3" value="预约排班" />&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>