<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		
		<script type="text/javascript" src="js/registerOrder.js"></script>
	</head>

	<body>
		
		
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
						<td  width="5%">-至-</td>
						<td >
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
						
						<td  width="6%">科室名称：</td>
						<td width="8%">
						<select id="teamId" name="teamId"><option value="10">---科室---</option> </select>
						</td>
						
						<td  width="6%"><select id="doctorId" name="doctorId"><option value="10001">---医生---</option> </select></td>
						<td width="5%">
							订单状态
						</td>
						
						<td  width="6%"><select id="state" name="state"><option>未处理</option><option>已预约</option><option>已作废</option></select></td>
						<td>
							<input type="button" onclick="qryRegisterOrder()" style="background-image:url('/pub/images/btn1_r1_c2.png');width:80px;height:28px;border:none;cursor:pointer" value="查询" />
						</td>
					</tr>
				</table>
		
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="maintable1">
				<tr class="tabletop">
					<td align="center" width="5%">订单号</td>
					<td align="center" width="5%">预约号</td>
					<td align="center" width="4%">类型</td>
					
					<td align="center" width="5%">预约人</td>
					<td align="center" width="8%">预约联系方式</td>
					
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
						
					     <td>${registerOrder.userName }</td>
						<td>${registerOrder.userTelephone }</td>
						
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
