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
<script type="text/javascript" src="<%=path%>/pub/js/jquery-1.9.1.min.js"></script>
<link href="<%=path%>/pub/css/sub.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>pub/dialog/lhgdialog.min.js?skin=idialog"></script>
<script type="text/javascript" src="<%=path%>/pub/js/calendar.js"></script>
<script type="text/javascript" src="<%=path%>/pub/js/date.js"></script>
<script type="text/javascript" src="<%=path%>/js/comm.js"></script>
<script type="text/javascript">
function updateOrder()
{
	 var teamId='${doctor.team_id}';
	 var doctorId ='${doctor.doctor_id}';
	 var fee ='${doctor.fee}';
	 var num ='${doctor.num}';
	 

	 var url="/view/doctor/registerTime.jsp?&teamId="+teamId+"&doctorId="+doctorId+"&fee="+fee+"&num="+num;
	 window.location.href=url;
}
function updateDoctor()
{
	var name = $("#name");
	var psw = $("#password");
	var confimPsw = $("#confimPassword");

	if(name.val()=='')
	{
		alert("用户名为空!");
		return;
	}

	if(psw.val()=='')
	{
		alert("密码为空!");
		return;
	}
	
	if(psw.val()!=confimPsw.val())
	{
		alert("密码不一致请重新输入!");
		return;
	}
	
   window.location.href="/doctor.htm?method=updateDoctor&name="+name.val()+"&password="+psw.val()+"&confimPassword="+confimPsw.val()+"&doctorId="+'${doctor.doctor_id}';
	
	
}

function refundAjax()
{
	var name = $("#name");
	var psw = $("#password");
	var confimPsw = $("#confimPassword");
    var fee=$("#fee");
	if(name.val()=='')
	{
		alert("用户名为空!");
		return;
	}

	if(psw.val()=='')
	{
		alert("密码为空!");
		return;
	}
	
	if(psw.val()!=confimPsw.val())
	{
		alert("密码不一致请重新输入!");
		return;
	}
	
	$.ajax({
		url:"/doctor.htm?method=updateDoctor",
		type:"POST",
		data:"name="+name.val()+"&password="+psw.val()+"&confimPassword="+confimPsw.val()+"&doctorId="+'${doctor.doctor_id}'+"&fee="+fee.val(),
		success:function(data)
		{
			$.dialog.alert('操作成功',function(){
				//window.location.href="/ques.htm?method=queryPre";
			});
		},
		error:function(stata)
		{
		 
		}
	});
}

</script>
</head>
<body>
		<div id="template" style="height:500px;overflow:auto">
		<table width="50%" border="0" cellspacing="0" cellpadding="0" align='center'
			class="maintable1">
			<br/>
			<tr align='left'>
				<div class="titleba"><h3>医生基本信息</h3></div>	
			</tr>
		</table>
<table width="50%" border="1" cellspacing="0" cellpadding="0" align='center'
			class="maintable1">

				<tr>
					<td align="center" width='30%'>医生名称</td>
					<td align="center">${doctor.name}</td>
				</tr>
				<tr>
					<td align="center" width='30%'>电话</td>
					<td align="center">${doctor.telephone}</td>
				</tr>
				<tr>
					<td align="center" width='20%'>性别</td>
					<td align="center">${doctor.sex}</td>
				</tr>
				<tr>
					<td align="center" width='20%'>科室</td>
					<td align="center">${doctor.team_name}</td>
				</tr>
				<tr>
					<td align="center" width='20%'>挂号费</td>
					<td align="center"><input type="text" id="fee" class="subtext" value='${doctor.register_fee}'></td>
				</tr>
				<tr>
					<td align="center" width='20%'>登录用户名</td>
					<td align="center"><input type="text" id="name" class="subtext" value='${doctor.manager_name}'></td>
				</tr>
				<tr>
					<td align="center" width='30%'>密码</td>
					<td align="center"><input type="password"  class="subtext" id="password" value='${doctor.password}'></td> </td>
				</tr>
				<tr>
					<td align="center" width='30%'>确认密码</td>
					<td align="center"><input type="password" class="subtext"  id="confimPassword"  value='${doctor.password}'></td> </td>
				</tr>
		</table>
			
		<table width="50%" border="0" cellspacing="0" cellpadding="0" align='center'
			class="maintable1">
			<br/>
			<tr align='center'>
						<input type="button"  align='center' onclick="refundAjax()" style="background-image:url('/pub/images/btn1_r1_c2.png');width:80px;height:28px;border:none;cursor:pointer" value="修改信息" />
			</tr>
			
		</table>
			<br/>
			<table width="50%" border="0" cellspacing="0" cellpadding="0" align='center'
			class="maintable1">
			<br/>
			<tr align='left'>
				<div class="titleba"><h3>医生预约时间</h3></div>			
			</tr>
		</table>
		
		<table width="50%" border="1" cellspacing="0" cellpadding="0" align='center'
			class="maintable1">
			<tr class="tabletop">
				<td align="center">工作日</td>
				<td align="center" width="20%">上午/下午</td>
				<td align="center" width="20%">预约费</td>
				<td align="center" width="20%">预约数量</td>
			</tr>
			<c:forEach items="${registers }" var="register" varStatus="i">
				<tr>
					<td align="center" width='15%'><c:out
							value="周${register.register_week}" />
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
		<table width="50%" border="0" cellspacing="0" cellpadding="0" align='center'
			class="maintable1">
			<br/>
			<tr align='center'>
						<input type="button" align='center' onclick="updateOrder()" style="background-image:url('/pub/images/btn1_r1_c2.png');width:80px;height:28px;border:none;cursor:pointer" value="预约排班" />&nbsp;&nbsp;
			</tr>
			
		</table>

</div>
</body>
</html>
