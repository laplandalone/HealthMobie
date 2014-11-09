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
		<script type="text/javascript" src="<%=path%>/pub/dialog/lhgdialog.min.js?skin=idialog"></script>
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
			
			function queryMed()
			{
				var doctorId='<%= doctorId%>';
				var teamId = $("#teamId").val();
				var docotrName = $("#doctorName").val();
				docotrName = $.trim(docotrName);
				window.location.href = "/doctor.htm?method=queryPre&doctorId="+doctorId+"&teamId="+teamId+"&doctorName="+docotrName;
			}

			function queryDoctor(doctorId) 
			{
				if (doctorId != "") 
				{
					window.location.href = "/doctor.htm?method=getDoctor&doctorId=" + doctorId;
				}
			}
			
			function deleteDoctor(doctorId)
			{
				$.dialog.confirm("选中的医生确定要下线?", 
					function(){
						$.ajax({
							type : "POST",
							url : "/doctor.htm?method=deleteDoctor",
							data : "doctorId="+doctorId,
							success: function(data) 
							{
								if(data == "success")
								{
									$.dialog({title:false, width:"150px", esc:false, height:"60px", zIndex:2000, icon:"succ.png", lock:true, content:"删除医生信息成功!", ok:function() {queryMed(); return true;}});
								}
								else
								{
									$.dialog({title:false, width:"150px", esc:false, height:"60px", zIndex:2000, icon:"fail.png", lock:true, content:"删除医生信息失败!", ok:function() {queryMed(); return true;}});
								}
							},
							error : function(data)
							{
								$.dialog.alert(data.statusText, function(){return true;});
							}
						});
					},
					function(){return true;}
				);
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
				<td align="left">
					<input type="button" class="button3" value="查询" onclick="queryMed()"/>
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
	
						<td align="center">
							<c:choose>
								<c:when test="${doctor.sex == '' || doctor.sex ==  null || doctor.sex == 'null' || doctor.sex == undefined}">
									<c:out value="无"></c:out>
								</c:when>
								<c:otherwise>
									<c:out value="${doctor.sex }" />
								</c:otherwise>
							</c:choose>
						</td>
						<td align="center"><c:out value="${doctor.team_name}" /></td>
						<td style="text-align:center !important">
							<a href="javascript:void(0)" class="linkmore" onclick="queryDoctor('${doctor.doctor_id}')">管理</a>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0)" class="linkmore" onclick="deleteDoctor('${doctor.doctor_id}')">删除</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
