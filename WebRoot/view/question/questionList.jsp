<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
   String doctorId= request.getParameter("doctorId");
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

			function refundAjax(doctorId,questionId,userId,telephone,content,authType)
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
    					alert(stata.statusText);
    				}
				});
			}
			
			function verifyFunc(doctorId,questionId,userId,telephone,id)
			{
			
				var val= $("#"+id);
				$.dialog({
				    id: 'testID',
				    
				    content: "<table border='0' cellspacing='10'><tr><td>"+telephone+":</td><td>"+val.html()+"</td></tr><tr><td valign='top'>回复:</td><td><textarea cols='40' rows='4' id='content'></textarea></td></tr><tr ><td>可见范围:</td><td>可见<input type='checkbox' id='public' value='public' checked='checked' /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;不可见<input type='checkbox' onclick=change() id='private' value='private' /></td></tr></table>",			   
				    
				    title:"操作",
				    button: [
				        {
				            name: '确定',
				            callback: function()
				            {
				            	var privateType=document.getElementById("private");
				            	var publicType=document.getElementById("public");
				            	var authType="";
				            	if(privateType.checked)
				            	{
				            		authType="private";
				            	}else if(publicType.checked)
				            	{
				            		authType="public";
				            	}else
				            	{
				            		alert("请选择可见范围");
				            		return false;
				            	}
				            	if(privateType.checked && publicType.checked)
				            	{
				            		alert("可见范围选择有误，请选择可见或者不可见");
				            		return false;
				            	}
				            	
				            	if($("#content").val()=="")
				            	{
				            		alert("回复内容为空");
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
				        }
				    ]
				});
			}
			
			function queryMed()
			{
			    var doctorId='<%=doctorId%>';
				var startTime = $("#startTime");
				var endTime = $("#endTime");
			  
				if(doctorId != "")
				{
					var url="/ques.htm?method=queryPre&doctorId="+doctorId+"&startTime="+startTime.val()+"&endTime="+endTime.val();
							
					window.location.href=url;
				}
				
			}
			
		</script>
</head>
<body>
	<div id="template" style="height:700px;overflow:auto">
	
			<table width="100%">
				<tr>
					<td align="right" width='10%'>创建时间：</td>
					<td align="center" width='15%'>
						<table class="inputtable" cellspacing="0" cellpadding="0">
							<tr>
								<td><input type="text" id="startTime" name="startTime"
									style="border:0;height:20px;width:130px;font-size:12px"
									readonly />
								<td>
								<td><a href="javascript:void(0);"
									onclick="showDate(document.getElementById('startTime'))"> <img
										src="<%=path%>/pub/images/calendar.png"
										style="position:relative;top:0px" /> </a></td>
							</tr>
						</table></td>
					<td align="center" width='5%'>-至-</td>
					<td align="center" width='15%'>
						<table class="inputtable" cellspacing="0" cellpadding="0">
							<tr>
								<td><input type="text" id="endTime" name="endTime"
									style="border:0;height:20px;width:130px;font-size:12px"
									readonly />
								<td>
								<td><a href="javascript:void(0);"
									onclick="showDate(document.getElementById('endTime'))"> <img
										src="<%=path%>/pub/images/calendar.png"
										style="position:relative;top:0px" /> </a></td>
							</tr>
						</table>
						</td>
					</td>
					<td><input type="button" onclick="queryMed()"
						style="background-image:url('/pub/images/btn1_r1_c2.png');width:80px;height:28px;border:none;cursor:pointer"
						value="查询" />
					</td>
				</tr>
			</table>
		
		<table width="100%" border="1" cellspacing="0" cellpadding="0"
			class="maintable1">
			<tr class="tabletop">
				<td align="center">用户</td>
				<td align="center" width="20%">用户问题</td>
				<td align="center" width="20%">用户图片</td>
				<td align="center" width="8%">可见范围</td>
				<td align="center" width="10%">创建时间</td>
				<td align="center" width="10%">操作</td>
			</tr>
			<c:forEach items="${quesLst }" var="ques" varStatus="i">
				<tr>
					<td align="center" width='15%'><c:out
							value="${ques.userTelephone}" />
					</td>
					<td align="center"><div id ="<c:out value="${ques.qestionId}" />content" ><c:out value="${ques.content }" /></div></td>
					<td align="center">
					<c:choose>
							<c:when test="${ques.imgUrl0!=null && ques.imgUrl0!=''}">
								<a href="<c:out value="${ques.imgUrl0 }"/>" target="_blank"><img
									src="<c:out value="${ques.imgUrl0}"/>" width="60"
									height="60" />
								</a>
							</c:when>

						</c:choose> 
					 <c:choose>
							<c:when test="${ques.imgUrl1!=null && ques.imgUrl1!=''}">
								<a href="<c:out value="${ques.imgUrl1 }"/>" target="_blank"><img
									src="<c:out value="${ques.imgUrl1 }"/>" width="60"
									height="60" />
								</a>
							</c:when>

						</c:choose> <c:choose>
							<c:when test="${ques.imgUrl2!=null && ques.imgUrl2!=''}">
								<a href="<c:out value="${ques.imgUrl2 }"/>" target="_blank"><img
									src="<c:out value="${ques.imgUrl2 }"/>" width="60"
									height="60" />
								</a>
							</c:when>

						</c:choose></td>
					<td align="center">	<c:choose>
							<c:when test="${ques.authType == 'private'}">
								不可见
							</c:when>
							<c:otherwise>
								可见
							</c:otherwise>
						</c:choose>
					
					</td>
					<td align="center"> <c:out
							value="${ques.createDate}"/>
					</td>
					
					<td style="text-align:center !important">
					<a href="javascript:void(0)" class="linkmore"
									onclick="verifyFunc('${ques.doctorId}','${ques.qestionId}','${ques.userId}','${ques.userTelephone}','${ques.qestionId}content')">回复</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>

</body>
</html>
