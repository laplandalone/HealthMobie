<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title></title>
		<link href="/pub/css/sub.css" rel="stylesheet" type="text/css" />
		<link href="/pub/css/bankList.css" rel="stylesheet" type="text/css"></link>
		<link href="/pub/css/manPage.css" rel="stylesheet" type="text/css"></link>
		<script type="text/javascript" src="pub/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="pub/dialog/lhgdialog.min.js?skin=idialog"></script>
		<script type="text/javascript" src="pub/js/calendar.js"></script>
		<script type="text/javascript" src="pub/js/date.js"></script>
		<script type="text/javascript" src="js/registerOrder.js"></script>
	</head>

	<body>
		<div id="template" style="height:700px;overflow:auto">
			<div style="height:40px;margin-left:10px;line-height:40px;width:100%">
				<table width="100%">
					<tr>
						<td align="right" width="10%">预约时间：</td>
						<td align="center">
							<table class="inputtable" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<input type="text" id="startTime" name="startTime" style="border:0;height:20px;width:130px;font-size:12px" readonly value="${startTime }"/>
									<td>
									<td>
										<a href="javascript:void(0);" onclick="showDate(document.getElementById('startTime'))"> 
											<img src="<%=path%>/pub/images/calendar.png" style="position:relative;top:0px" /> 
										</a>
									</td>
								</tr>
							</table>
						</td>
						<td align="center" width="5%">-至-</td>
						<td align="center">
							<table class="inputtable" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<input type="text" id="endTime" name="endTime" style="border:0;height:20px;width:130px;font-size:12px" readonly value="${endTime }"/>
									<td>
									<td>
										<a href="javascript:void(0);" onclick="showDate(document.getElementById('endTime'))"> 
											<img src="<%=path%>/pub/images/calendar.png" style="position:relative;top:0px" /> 
										</a>
									</td>
								</tr>
							</table>
						</td>
						<td align="right" width="10%">科室名称：</td>
						<td width="8%">
							<input type="text" id="teamName" value="${teamName }"/>
						</td>
						<td align="right" width="10%">医生名称：</td>
						<td width="8%">
							<input type="text" id="doctorName" value="${doctorName }"/>
						</td>
						<td>
							<input type="button" onclick="qryRegisterOrder()" style="background-image:url('/pub/images/btn1_r1_c2.png');width:80px;height:28px;border:none;cursor:pointer" value="查询" />
						</td>
					</tr>
				</table>
			</div>
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="maintable1">
				<tr class="tabletop">
					<td align="center" width="8%">订单号</td>
					<td align="center" width="8%">预约号</td>
					<td align="center" width="8%">类型</td>
					<td align="center" width="10%">医院名称</td>
					<td align="center" width="8%">科室名称</td>
					<td align="center" width="6%">医生名称</td>
					<td align="center" width="4%">费用</td>
					<td align="center" width="10%">预约时间</td>
					<td align="center" width="8%">订单状态</td>
					<td align="center" width="10%">操作</td>
				</tr>
				<c:forEach items="${registerOrderList }" var="registerOrder">
					<tr>
						<td>${registerOrder.orderId }</td>
						<td>${registerOrder.orderNum }</td>
						<td><c:choose>
							<c:when test="${registerOrder.registerId == '0'}">
								普通号
							</c:when>
							<c:otherwise>
								专家号
							</c:otherwise>
						</c:choose></td>
						<td>${registerOrder.hospitalName }</td>
						<td>${registerOrder.teamName }</td>
						<td>${registerOrder.doctorName }</td>
						<td>${registerOrder.orderFee }</td>
						<td>${registerOrder.registerTime }</td>
						<td>${registerOrder.orderState }</td>
						<td style="text-align:center !important">
							<c:choose>
								<c:when test="${registerOrder.orderState == '未处理' }">
									<a href="javascript:void(0)" class="linkmore" onclick="appointment('${registerOrder.name }', ${registerOrder.orderId })">预约</a>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<a href="javascript:void(0)" class="linkmore" onclick="invalid('${registerOrder.name }', ${registerOrder.orderId })">作废</a>
								</c:when>
								<c:otherwise>
									<a href="javascript:void(0)" style="color:#cccccc">预约</a>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<a href="javascript:void(0)" style="color:#cccccc">作废</a>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
