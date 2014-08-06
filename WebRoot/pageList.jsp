<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title></title>
		<link href="<%=path%>/pub/css/sub.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="/pub/css/bankList.css" type="text/css"></link>
		<link rel="stylesheet" href="/pub/css/manPage.css" type="text/css"></link>
		<script type="text/javascript" src="pub/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="pub/dialog/lhgdialog.min.js?skin=idialog"></script>
		<script type="text/javascript" src="pub/js/calendar.js"></script>
		<script type="text/javascript" src="pub/js/date.js"></script>
		<script type="text/javascript">
			function refundAjax(fileId,state,comments)
			{
				$.ajax({
					url:"/mobile.htm?method=updateFile",
					type:"POST",
					data:"fileId="+fileId+"&state="+state+"&comments="+comments+"&verUserId=${userId}",
					success:function(data)
					{
						$.dialog.alert('操作成功',function(){
							window.location.href="/mobile.htm?method=queryPre";
						});
						/*
						var result="处理成功";
						if(data.result==false)
						{
							result="处理失败";
						}
						var stateDiv = document.getElementById(fileId+"state");
						if(data.result==true)
						{
							if(state==1)
							{
								stateDiv.innerText="同意";
							}
							else
							{
								stateDiv.innerText="不同意";
							}
							var yesDiv = document.getElementById(fileId+"yes");
							var noDiv = document.getElementById(fileId+"no");
							yesDiv.disabled=true;
							noDiv.disabled=true;
						}
						*/
    				},
    				error:function(stata)
    				{
    					$.dialog.alert(stata.statusText, function(){return true;});
    				}
				});
			}
			
			function verifyFunc(name,val)
			{
				$.dialog({
				    id: 'testID',
				    content: "<table cellspacing='10'><tr><td colspan='2'>是否同意"+name+"药通过审核?</td></tr><tr><td valign='top'>批注:</td><td><textarea cols='20' rows='8' id='comments'></textarea></td></tr></table>",
				    lock:true,
				    title:"操作",
				    button: [
				        {
				            name: '同意',
				            callback: function(){
				                refundAjax(val,1,$("#comments").val())
				            },
				            focus: true
				        },
				        {
				            name: '不同意',
				            callback: function(){
				                refundAjax(val,2,$("#comments").val())
				            }
				        }
				    ]
				});
			}
			
			function queryMed()
			{
				var medName = $("#medName");
				var startTime = $("#startTime");
				var endTime = $("#endTime");
				if($.trim(medName.val()) != "")
				{
					window.location.href="/mobile.htm?method=queryPre&medName="+medName.val()+"&startTime="+startTime.val()+"&endTime="+endTime.val();
				}
				else
				{
					$.dialog.alert("查询内容不能为空!", function(){medName.focus(); return true;});
					return;
				}
			}
		</script>
	</head>
	<body>
		<div id="template" style="height:700px;overflow:auto">
			<div style="height:40px;margin-left:10px;line-height:40px;width:800px">
				<table width="100%">
					<tr>
						<td align="right">处方时间：</td>
						<td align="center">
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
						<td align="center">-至-</td>
						<td align="center">
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
						<td align="right" width="10%">药品名称:</td>
						<td>
							<input type="text" id="medName" value="" style="150px" />
						</td>
						<td>
							<input type="button" onclick="queryMed()" style="background-image:url('/pub/images/btn1_r1_c2.png');width:80px;height:28px;border:none;cursor:pointer" value="查询" />
						</td>
					</tr>
				</table>
			</div>
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="maintable1">
				<tr class="tabletop">
					<td align="center">处方编号</td>
					<td align="center" width="10%">药品名称</td>
					<td align="center" width="20%">图片</td>
					<td align="center" width="8%">药店名称</td>
					<td align="center" width="8%">处理结果</td>
					<td align="center" width="10%">登记时间</td>
					<td align="center">审核人</td>
					<td align="center">批注</td>
					<td align="center" width="10%">操作</td>
				</tr>
				<c:forEach items="${userFileLst }" var="userFiles" varStatus="i">
					<tr>
						<td align="center" width='15%'><c:out
								value="${userFiles.medicalCode}" />
						</td>
						<td align="center"><c:out value="${userFiles.medicalName }" />
						</td>
						<td align="center">
							<c:choose>
								<c:when test="${userFiles.imgUrl0!=null && userFiles.imgUrl0!=''}">
									<a href="<c:out value="${userFiles.imgUrl0 }"/>" target="_blank">
										<img src="<c:out value="${userFiles.imgUrl0}"/>" width="60" height="60" />
									</a>
								</c:when>
							</c:choose> 
							 <c:choose>
								<c:when test="${userFiles.imgUrl1!=null && userFiles.imgUrl1!=''}">
									<a href="<c:out value="${userFiles.imgUrl1 }"/>" target="_blank">
										<img src="<c:out value="${userFiles.imgUrl1 }"/>" width="60" height="60" />
									</a>
								</c:when>
							</c:choose> 
							<c:choose>
								<c:when test="${userFiles.imgUrl2!=null && userFiles.imgUrl2!=''}">
									<a href="<c:out value="${userFiles.imgUrl2 }"/>" target="_blank">
										<img src="<c:out value="${userFiles.imgUrl2 }"/>" width="60" height="60" />
									</a>
								</c:when>
							</c:choose>
						</td>
						<td align="center">
							<c:out value="${userFiles.storeName }"></c:out>
						</td>
						<td align="center">${userFiles.state}</td>
						<td align="center">
							<fmt:formatDate value="${userFiles.registerDate}" pattern="yyyy-MM-dd" />
						</td>
						<td align="center">${userFiles.verUserName}</td>
						<td align="center">${userFiles.comments}</td>
						<td style="text-align:center !important">
							<c:choose>
								<c:when test="${userFiles.state == '未处理'}">
									<a href="javascript:void(0)" class="linkmore" onclick="verifyFunc('${userFiles.medicalName}',${userFiles.fileId})">审批</a>
								</c:when>
								<c:otherwise>
									<a href="javascript:void(0)" style="color:#cccccc">审批</a>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>