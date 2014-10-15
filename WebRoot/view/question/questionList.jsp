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
			$(document).ready(function(){
				var startTime = $("#selStartTime").val();
				var endTime = $("#selEndTime").val();
				if(startTime != "" && endTime != "")
				{
					$("#startTime").val(startTime);
					$("#endTime").val(endTime);
				}
			});
			
			var demoDG1 = null;
			function verifyFunc(doctorId, questionId, userId, telephone, id)
			{
				var val = $("#"+id).html();
				val = encodeURI(val);
				lockScreen();
				demoDG1 = $.dialog({
					title:"操作", 
					content:"url:/view/question/replyQuestion.jsp?telephone="+telephone+"&question="+val+"&doctorId="+doctorId+"&questionId="+questionId+"&userId="+userId, 
					min:false, 
					max:false, 
					lock:true, 
					close:function(){unlockScreen();},
					ok: function()
					{
						var replyContent = demoDG1.content.document.getElementById("replyContent").value;
						if($.trim(replyContent) == "")
						{
							$.dialog.alert("回复内容为空", function(){return true;});
						    return false;
						}
						else
						{
							var authType = "";
							var array = demoDG1.content.document.getElementsByName("authType");
							for(var i = 0, len = array.length; i < len; i++)
							{
								if(array[i].checked) 
								{
									authType = array[i].value;
								}
							}
							if(authType == "")
							{
								$.dialog.alert("请选择可见范围", function(){return true;});
						        return false;
							}
							else
							{
								refundAjax(doctorId, questionId, userId, telephone, replyContent, authType);
							}
						}
					},
					cancel: true
				});
			}
			
			function refundAjax(doctorId, questionId, userId, telephone, content, authType)
			{
				$.ajax({
					url:"/ques.htm?method=updateQues",
					type:"POST",
					data:"doctorId="+doctorId+"&questionId="+questionId+"&userId="+userId+"&telephone="+telephone+"&authType="+authType+"&content="+content,
					success:function(data)
					{
						$.dialog.alert("操作成功",function(){return true});
    				},
    				error:function(stata)
    				{
    					$.dialog.alert(stata.statusText, function(){return true;});
    				}
				});
				unlockScreen();
			}
			
			function queryMed()
			{
			    var doctorId = $("#doctorId").val();
				if (doctorId != "") 
				{
					var startTime = $("#startTime").val();
					var endTime = $("#endTime").val();
					var url = "/ques.htm?method=queryPre&doctorId=" + doctorId + "&startTime=" + startTime + "&endTime=" + endTime;
					window.location.href = url;
				}
			}
			
			function viewContent(doctorId, questionId)
			{
				lockScreen();
				$.dialog({id:"viewDemo", width:"450px", esc:false, title:"所有内容", content:"url:/ques.htm?method=qryQuesList&doctorId="+doctorId+"&questionId="+questionId, min:false, max:false, lock:true, close:function(){unlockScreen();}});
			}
		</script>
	</head>
	<body>
		<div id="template" style="width:95%;height:100%;overflow:auto" align="center">
			<table width="100%">
				<input type="hidden" id="selStartTime" value="${startTime }"/>
				<input type="hidden" id="selEndTime" value="${endTime }"/>
				<input type="hidden" id="doctorId" value="${doctorId }"/>
				<tr>
					<td align="right" width='10%'>创建时间：</td>
					<td align="center" width='15%'>
						<table class="inputtable" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="text" id="startTime" name="startTime" style="border:0;height:20px;width:130px;font-size:12px" readonly />
								<td>
								<td>
									<a href="javascript:void(0);" onclick="showDate(document.getElementById('startTime'))"> 
										<img src="<%=path%>/pub/images/calendar.png" style="position:relative;top:0px" /> 
									</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="center" width='5%'>-至-</td>
					<td align="center" width='15%'>
						<table class="inputtable" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="text" id="endTime" name="endTime" style="border:0;height:20px;width:130px;font-size:12px" readonly />
								<td>
								<td>
									<a href="javascript:void(0);" onclick="showDate(document.getElementById('endTime'))"> 
										<img src="<%=path%>/pub/images/calendar.png" style="position:relative;top:0px" /> 
									</a>
								</td>
							</tr>
						</table>
					</td>
					<td>
						<input type="button" onclick="queryMed()" class="button3" value="查询" />
					</td>
				</tr>
			</table>
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="maintable1">
				<tr class="tabletop">
					<td align="center">用户</td>
					<td align="center" width="20%">用户问题</td>
					<td align="center" width="20%">用户图片</td>
					<td align="center" width="8%">可见范围</td>
					<td align="center" width="10%">创建时间</td>
					<td align="center" width="8%">所有内容</td>
					<td align="center" width="10%">操作</td>
				</tr>
				<c:forEach items="${quesLst }" var="ques" varStatus="i">
					<tr>
						<td align="center" width='15%'>
							<c:out value="${ques.userTelephone}" />
						</td>
						<td align="center">
							<div id="<c:out value="${ques.questionId}" />content">
								<c:out value="${ques.content }" />
							</div>
						</td>
						<td align="center">
							<c:choose>
								<c:when test="${ques.imgUrl0 != null && ques.imgUrl0 != ''}">
									<a href="<c:out value="${ques.imgUrl0 }"/>" target="_blank">
										<img src="<c:out value="${ques.imgUrl0}"/>" width="60" height="60" />
									</a>
								</c:when>
							</c:choose> 
							<c:choose>
								<c:when test="${ques.imgUrl1 != null && ques.imgUrl1 != ''}">
									<a href="<c:out value="${ques.imgUrl1 }"/>" target="_blank">
										<img src="<c:out value="${ques.imgUrl1 }"/>" width="60" height="60" />
									</a>
								</c:when>
							</c:choose> 
							<c:choose>
								<c:when test="${ques.imgUrl2 != null && ques.imgUrl2 != ''}">
									<a href="<c:out value="${ques.imgUrl2 }"/>" target="_blank">
										<img src="<c:out value="${ques.imgUrl2 }"/>" width="60" height="60" />
									</a>
								</c:when>
							</c:choose>
						</td>
						<td align="center">
							<c:choose>
								<c:when test="${ques.authType == 'private'}">
									不可见
								</c:when>
								<c:otherwise>
									可见
								</c:otherwise>
							</c:choose>
						</td>
						<td align="center">
							<c:out value="${ques.createDate}" />
						</td>
						<td style="text-align:center !important">
							<a href="javascript:void(0)" class="linkmore" onclick="viewContent('${ques.doctorId}', '${ques.questionId}')">查看</a>
						</td>
						<td style="text-align:center !important">
							<a href="javascript:void(0)" class="linkmore" onclick="verifyFunc('${ques.doctorId}','${ques.questionId}','${ques.userId}','${ques.userTelephone}','${ques.questionId}content')">回复</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
