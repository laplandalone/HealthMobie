<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<script type="text/javascript">
			$(document).ready(function(){
				var teamId = $("#selTeamId").val();
				$.getJSON("/doctor.htm?method=qryTeamList", {"random":Math.random()}, function(data){
					if(data.length > 0)
					{
						var content = "<option value=''>全部</option>";
						for(var k = 0 ; k < data.length ; k++)
						{  
							if(teamId == data[k].teamId)
							{
								content += "<option value='"+data[k].teamId+"' selected='selected'>"+data[k].teamName+"</option>";
							}
							else
							{
								content += "<option value="+data[k].teamId+">"+data[k].teamName+"</option>";
							}
						} 
					}
					$("#teamId").html(content);
				});
			});
			
			function qryOnlineDortorQues()
			{
				var teamId = $("#teamId").val();
				window.location.href = "/ques.htm?method=queryPre&teamId="+teamId;
			}
			
			function trColorChange(val, i) 
			{
				if (val.className == "bkf0" || val.className == "aaa")
				{
					val.className = "trcolor";
				} 
				else 
				{
					if (i % 2)
					{
						val.className = "bkf0";
					}
					else
					{
						val.className = "aaa";
					}
				}
			}
		</script>
  	</head>
  
  	<body>
  		<div id="template" style="width:95%;height:100%;overflow:auto" align="center">
  			<input type="hidden" id="selTeamId" value="${teamId }"/>
  			<table width="100%">
  				<tr>
  					<td align="right" width="10%">在线科室：</td>
					<td width="8%">
						<select id="teamId" name="teamId" class="subselect">
							
						</select>
					</td>
					<td align="right" width="10%">
						<input type="button" class="button3" value="查询" onclick="qryOnlineDortorQues()"/>
					</td>
					<td width="72%">&nbsp;</td>
  				</tr>
  			</table>
  			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="maintable1">
  				<tr class="tabletop">
					<td align="center" width='15%'>医生姓名</td>
					<td align="center" width="20%">医生职称</td>
					<td align="center" width="20%">医生科室</td>
					<td align="center" width="10%">提问总数</td>
					<td align="center" width="10%">回复数</td>
					<td align="center" width="10%">未回复数</td>
				</tr>
				<c:forEach items="${quesLst }" var="ques" varStatus="i">
					<c:if test="${i.index % 2 != 0 }">
						<tr class='bkf0' onmouseover="trColorChange(this,${i.index})" onmouseout="trColorChange(this,${i.index })">
					</c:if>
					<c:if test="${i.index % 2 == 0 }">
						<tr class='aaa' onmouseover="trColorChange(this,${i.index})" onmouseout="trColorChange(this,${i.index })">
					</c:if>
						<td align="center" width='15%'>${ques.name }</td>
						<td align="center" width='15%'>${ques.post }</td>
						<td align="center" width='15%'>${ques.teamName }</td>
						<td align="center" width='15%'>${ques.totalQuesNum }</td>
						<td align="center" width='15%'>${ques.totalReplyNum }</td>
						<td align="center" width='15%'>${ques.totalQuesNum - ques.totalReplyNum }</td>
					</tr>
				</c:forEach>
  			</table>
  		</div>
  	</body>
</html>
