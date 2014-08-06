<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.hbgz.pub.cache.PubData"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String hospitalId = (String) session.getAttribute("hospitalId");
	String doctorId = request.getParameter("doctorId");
	List teamLst = PubData.qryTeamList(hospitalId);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title></title>
		<link href="<%=path%>/pub/css/sub.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/pub/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="<%=path%>pub/dialog/lhgdialog.min.js?skin=idialog"></script>
		<script type="text/javascript" src="<%=path%>/pub/js/calendar.js"></script>
		<script type="text/javascript" src="<%=path%>/pub/js/date.js"></script>
		<script type="text/javascript" src="<%=path%>/js/comm.js"></script>
		<script type="text/javascript" src="<%=path%>/js/json2.js"></script>
		<script type="text/javascript">
		$(document).ready(function(){
			var teamId = $("#selTeamId").val();
			$("#teamId option[value='" + teamId + "']").attr("selected", true);
			var name = $("#selName").val();
			$("#doctorName").val(name);
		});
		function refundAjax(doctorId, questionId, userId, telephone, content, authType)
		{
			$.ajax({
				url:"/ques.htm?method=updateQues",
				type:"POST",
				data:"doctorId="+doctorId+"&questionId="+questionId+"&userId="+userId+"&telephone="+telephone+"&authType="+authType+"&content="+content,
				success:function(data)
				{
					$.dialog.alert('操作成功',function(){
						//window.location.href="/ques.htm?method=queryPre";
					});
    			},
    			error:function(stata)
    			{
    				$.dialog.alert(stata.statusText, function(){return true;});
    			}
			});
		}
			
		function verifyFunc(doctorId,questionId,userId,telephone,val)
		{
			lockScreen();	
			$.dialog({
				id: 'testID',
				content: "<table border='0' cellspacing='10'><tr><td>"+telephone+":</td><td>"+val+"</td></tr><tr><td valign='top'>回复:</td><td><textarea cols='40' rows='4' id='content'></textarea></td></tr><tr ><td>可见范围:</td><td>可见<input type='checkbox' id='public' value='public' checked='checked' /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;不可见<input type='checkbox' onclick=change() id='private' value='private' /></td></tr></table>",			   
				title:"操作",
				button: 
					[{
						name: '确定',
					    callback: function()
					    {
					    	var privateType = document.getElementById("private");
							var publicType = document.getElementById("public");
							var authType = "";
							if(privateType.checked)
							{
								authType = "private";
							}
							else if(publicType.checked)
							{
								authType = "public";
							}
							else
							{
								$.dialog.alert("请选择可见范围", function(){return true;});
								return false;
							}
							if(privateType.checked && publicType.checked)
							{
								$.dialog.alert("可见范围选择有误，请选择可见或者不可见", function(){return true;});
								return false;
							}
					        if($("#content").val()=="")
					        {
					        	$.dialog.alert("回复内容为空", function(){return true;});
								return false;
							}
							refundAjax(doctorId,questionId,userId,telephone,$("#content").val(),authType)
						},
						focus: true
					},
					{
						name: '取消',
						callback: function()
						{
							return;
						}
				    }]
				});
			}
			
			function queryMed()
			{
				var doctorId='<%= doctorId%>';
				var teamId = $("#teamId").val();
				var docotrName = $("#doctorName").val();
				window.location.href = "/doctor.htm?method=queryPre&doctorId="+doctorId+"&teamId="+teamId+"&doctorName="+docotrName;
			}

			function queryDoctor(doctorId) 
			{
				if (doctorId != "") 
				{
					window.location.href = "/doctor.htm?method=getDoctor&doctorId=" + doctorId;
				}
			}
			
			function enterQry(fName)
			{
				if(event.keyCode == 13)
				{   
					event.returnValue = false;
					fName +='()';
					eval(fName);
				}	
			}
		</script>
	</head>
	<body>
		<table width="100%">
			<input type="hidden" id="selTeamId" value="${teamId }"/>
			<input type="hidden" id="selName" value="${doctorName }"/>
			<tr>
				<td align="right" width="10%">科室名称：</td>
				<td width="8%">
					<c:set var="teamLst" value="<%=teamLst %>"></c:set>
					<select id="teamId" name="teamId" class="subselect">
						<option value="">---请选择---</option>
						<c:forEach items="${teamLst }" var="team">
							<option value="${team.teamId }">${team.teamName }</option>
						</c:forEach>
					</select>
				</td>
				<td align="right" width="10%">医生名称：</td>
				<td width="8%">
					<input type="text" id="doctorName" name="doctorName" class="subtext" onkeydown="enterQry('queryMed')"/>
				</td>
				<td align="right">
					<input type="button" style="background-image:url('/pub/images/btn1_r1_c2.png');width:80px;height:28px;border:none;cursor:pointer" value="查询" onclick="queryMed()"/>
				</td>
				<td width="10%">&nbsp;</td>
			</tr>
		</table>
		<div id="template" style="height:700px;overflow:auto">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="maintable1">
				<tr class="tabletop">
					<td>医生名称</td>
					<td width="20%">职称</td>
					<td width="20%">性别</td>
					<td width="10%">科室</td>
					<td width="10%">操作</td>
				</tr>
				<c:forEach items="${doctorLst }" var="doctor" varStatus="i">
					<tr>
						<td align="center" width='15%'><c:out value="${doctor.name}" />
						</td>
						<td align="center"><c:out value="${doctor.post}" /></td>
	
						<td align="center"><c:out value="${doctor.sex }" />
						<td align="center"><c:out value="${doctor.team_name}" /></td>
						<td style="text-align:center !important"><a
							href="javascript:void(0)" class="linkmore"
							onclick="queryDoctor('${doctor.doctor_id}')">管理</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
