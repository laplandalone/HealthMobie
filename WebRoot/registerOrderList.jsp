<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.hbgz.pub.cache.PubData"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String hospitalId = (String) session.getAttribute("hospitalId");
	List teamList = PubData.qryTeamList(hospitalId);
	List doctorList = PubData.qryDoctorList(hospitalId);	
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
		<script type="text/javascript" src="<%=path%>js/registerOrder.js"></script>
	</head>

	<body>
		<table width="100%">
			<input type="hidden" id="hospitalId" value="<%=hospitalId %>"/>
			<input type="hidden" id="selStartTime" value="${startTime }"/>
			<input type="hidden" id="selEndTime" value="${endTime }"/>
			<input type="hidden" id="selTeamId" value="${teamId }"/>
			<input type="hidden" id="selDoctorId" value="${doctorId }"/>
			<tr>
				<td align="right" width="12%">预约时间：</td>
				<td align="center" width="8%">
					<table class="inputtable" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<input type="text" id="startTime" name="startTime" style="border:0;height:20px;width:130px;font-size:12px" readonly/>
							</td>
							<td>
								<a href="javascript:void(0);" onclick="showDate(document.getElementById('startTime'))"> 
									<img src="<%=path%>/pub/images/calendar.png" style="position:relative;top:0px" /> 
								</a>
							</td>
						</tr>
					</table>
				</td>
				<td align="center" width="5%">至</td>
				<td align="center" width="8%">
					<table class="inputtable" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<input type="text" id="endTime" name="endTime" style="border:0;height:20px;width:130px;font-size:12px" readonly/>
							<td>
							<td>
								<a href="javascript:void(0);" onclick="showDate(document.getElementById('endTime'))"> 
									<img src="<%=path%>/pub/images/calendar.png" style="position:relative;top:0px" /> 
								</a>
							</td>
						</tr>
					</table>
				</td>
				<td width="14%" align="right">科室名称：</td>
				<td width="8%">
					<c:set var="teamList" value="<%=teamList %>"></c:set>
					<select id="teamId" name="teamId" class="subselect">
						<option value="">---请选择---</option>
						<c:forEach items="${teamList }" var="team">
							<c:if test="${team.expertFlag == '0' }">
								<c:if test="${teamId == team.teamId }">
									<option value="${team.teamId }" selected="selected">${team.teamName }</option>
								</c:if>
								<c:if test="${teamId != team.teamId }">
									<option value="${team.teamId }">${team.teamName }</option>
								</c:if>
							</c:if>
						</c:forEach>
					</select>
				</td>
				<!--  
				<td width="12%" align="right">医生名称：</td>
				<td width="8%">
					<c:set var="doctorList" value="<%=doctorList %>"></c:set>
					<select id="doctorId" name="doctorId" class="subselect">
						<option value="">---请选择---</option>
						<c:forEach items="${doctorList }" var="doctor">
							<c:if test="${doctorId == doctor.doctorId }">
								<option value="${doctor.doctorId }" selected="selected">${doctor.name }</option>
							</c:if>
							<c:if test="${doctorId != doctor.doctorId }">
								<option value="${doctor.doctorId }">${doctor.name }</option>
							</c:if>
						</c:forEach>
					</select>
				</td>
				-->
				<td width="12%" align="right">订单状态：</td>
				<td width="8%">
					<select id="state" name="state" class="subselect">
						<c:choose>
							<c:when test="${hospitalId == '101' }">
								<c:choose>
									<c:when test="${state == '000' }">
										<option value="">---请选择---</option>
										<option value="000" selected="selected">未处理</option>
										<option value="00A">已预约</option>
										<option value="00X">已作废</option>
									</c:when>
									<c:when test="${state == '00A' }">
										<option value="">---请选择---</option>
										<option value="000">未处理</option>
										<option value="00A" selected="selected">已预约</option>
										<option value="00X">已作废</option>
									</c:when>
									<c:when test="${state == '00X' }">
										<option value="">---请选择---</option>
										<option value="000">未处理</option>
										<option value="00A">已预约</option>
										<option value="00X" selected="selected">已作废</option>
									</c:when>
									<c:otherwise>
										<option value="" selected="selected">---请选择---</option>
										<option value="000">未处理</option>
										<option value="00A">已预约</option>
										<option value="00X">已作废</option>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:when test="${hospitalId == '102' }">
								<c:choose>
									<c:when test="${state == '100' }">
										<option value="">---请选择---</option>
										<option value="100" selected="selected">未支付</option>
										<option value="101">已支付</option>
										<option value="102">已取消</option>
									</c:when>
									<c:when test="${state == '101' }">
										<option value="">---请选择---</option>
										<option value="100">未支付</option>
										<option value="101" selected="selected">已支付</option>
										<option value="102">已取消</option>
									</c:when>
									<c:when test="${state == '102' }">
										<option value="">---请选择---</option>
										<option value="100">未支付</option>
										<option value="101">已支付</option>
										<option value="102" selected="selected">已取消</option>
									</c:when>
									<c:otherwise>
										<option value="">---请选择---</option>
										<option value="100">未支付</option>
										<option value="101">已支付</option>
										<option value="102">已取消</option>
									</c:otherwise>
								</c:choose>
							</c:when>
						</c:choose>
					</select>
				</td>
				<td width="15%" align="right">
					<input type="button" onclick="qryRegisterOrder()" class="button3" value="查询" />
				</td>
			</tr>
		</table>
		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="maintable1">
			<tr class="tabletop">
				<td align="center" width="5%">订单号</td>
				<td align="center" width="5%">预约号</td>
				<td align="center" width="4%">类型</td>
				<td align="center" width="5%">预约人</td>
				<td align="center" width="8%">联系方式</td>
				<td align="center" width="10%">医院名称</td>
				<td align="center" width="8%">科室名称</td>
				<td align="center" width="6%">医生名称</td>
				<td align="center" width="4%">费用</td>
				<td align="center" width="12%">预约时间</td>
				<td align="center" width="10%">创建时间</td>
				<td align="center" width="6%">订单状态</td>
				<c:choose>
					<c:when test="${hospitalId == '101' }">
						<td align="center" width="10%">操作</td>
					</c:when>
				</c:choose>
			</tr>
			<c:forEach items="${registerOrderList }" var="registerOrder">
				<tr>
					<td>${registerOrder.orderId }</td>
					<td>${registerOrder.orderNum }</td>
					<td>
						<c:choose>
							<c:when test="${registerOrder.registerId == '0'}">普通号</c:when>
							<c:otherwise>专家号</c:otherwise>
						</c:choose>
					</td>
					<td>${registerOrder.userName }</td>
					<td>${registerOrder.userTelephone }</td>
					<td>${registerOrder.hospitalName }</td>
					<td>${registerOrder.teamName }</td>
					<td>${registerOrder.doctorName }</td>
					<td>${registerOrder.orderFee }</td>
					<td>${registerOrder.registerTime }</td>
					<td><fmt:formatDate value="${registerOrder.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<c:choose>
						<c:when test="${hospitalId == '101' }">
							<td>${registerOrder.orderState }</td>
							<td style="text-align:center !important">
								<c:choose>
									<c:when test="${registerOrder.orderState == '未处理' }">
										<a href="javascript:void(0)" class="linkmore"
											onclick="appointment('${registerOrder.name }', ${registerOrder.orderId })">预约</a>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<a href="javascript:void(0)" class="linkmore"
											onclick="invalid('${registerOrder.name }', ${registerOrder.orderId })">作废</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" style="color:#cccccc">预约</a>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<a href="javascript:void(0)" style="color:#cccccc">作废</a>
									</c:otherwise>
								</c:choose>
							</td>
						</c:when>
						<c:when test="${hospitalId == '102' }">
							<td>${registerOrder.payState }</td>
						</c:when>
					</c:choose>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>
