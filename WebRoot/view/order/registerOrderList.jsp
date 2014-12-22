<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.hbgz.pub.cache.PubData"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String hospitalId = (String) session.getAttribute("hospitalId");
	List teamList = PubData.qryTeamList(hospitalId);
	Date currentDate = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String startTime = sdf.format(currentDate);
	Calendar c = Calendar.getInstance(); 
	c.setTime(currentDate);
	c.add(Calendar.DATE, 7);
	Date date = c.getTime();
	String endTime = sdf.format(date);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title></title>
		<link href="<%=path%>/pub/css/sub.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/pub/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="<%=path%>/pub/dialog/lhgdialog.min.js?skin=idialog"></script>
		<script type="text/javascript" src="<%=path%>/pub/js/calendar.js"></script>
		<script type="text/javascript" src="<%=path%>/js/comm.js"></script>
		<script type="text/javascript" src="<%=path%>/js/json2.js"></script>
		<script type="text/javascript" src="<%=path%>/js/json.js"></script>
		<script type="text/javascript" src="<%=path%>/js/registerOrder.js"></script>
	</head>

	<body onload="qryRegisterOrder()">
		<form action="">
			<div class="mainsearch">
				<input type="hidden" id="hospitalId" value="<%=hospitalId %>"/>
				<table width="100%">
					<tr>
						<td align="right" width="10%">预约时间</td>
						<td align="left" width="5%">
							<table class="inputtable2" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<input type="text" id="startTime" name="startTime" style="border:0;height:20px;width:85px;font-size:12px" readonly value="<%=startTime %>"/>
									</td>
									<td>
										<a href="javascript:void(0);" onclick="showDate(document.getElementById('startTime'))"> 
											<img src="<%=path%>/pub/images/calendar.png" style="position:relative;top:0px" /> 
										</a>
									</td>
								</tr>
							</table>
						</td>
						<td align="right" width="3%">至</td>
						<td align="left" width="5%">
							<table class="inputtable2" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<input type="text" id="endTime" name="endTime" style="border:0;height:20px;width:85px;font-size:12px" readonly value="<%=endTime %>"/>
									<td>
									<td>
										<a href="javascript:void(0);" onclick="showDate(document.getElementById('endTime'))"> 
											<img src="<%=path%>/pub/images/calendar.png" style="position:relative;top:0px" /> 
										</a>
									</td>
								</tr>
							</table>
						</td>
						<td width="12%" align="right">科室名称</td>
						<td width="5%" align="left">
							<c:set var="teamList" value="<%=teamList %>"></c:set>
							<select id="teamId" name="teamId" class="subselect2">
								<option value="">全部</option>
								<c:forEach items="${teamList }" var="team">
									<c:if test="${team.expertFlag == '0' }">
										<option value="${team.teamId }">${team.teamName }</option>
									</c:if>
								</c:forEach>
							</select>
						</td>
						<td width="10%" align="right">订单状态</td>
						<td width="5%" align="left">
							<select id="state" name="state" class="subselect2">
								<c:choose>
									<c:when test="${sessionScope.hospitalId == '101' }">
										<option value="">全部</option>
										<option value="000">未处理</option>
										<option value="00A">已预约</option>
										<option value="00X">已作废</option>
									</c:when>
									<c:when test="${sessionScope.hospitalId == '102' }">
										<option value="">全部</option>
										<option value="100">未支付</option>
										<option value="101">已取消</option>
										<option value="102">已支付</option>
										<option value="103">申请退款</option>
										<option value="104">已退款</option>
									</c:when>
								</c:choose>
							</select>
						</td>
						<td width="10%" align="right">预约人</td>
						<td width="5%" align="left"><input id="userName" name="userName" class="subtext4"/></td>
						<td width="12%" align="left">
							<input type="button" onclick="qryRegisterOrder()" class="button3" value="查询" />
						</td>
					</tr>
				</table>
			</div>
			<div class="main">
	  			<div class="title">
					<div class="titleleft"></div>
					<div class="titlecentre">
						<h3>查询结果</h3>
					</div>
					<div class="titleright"></div>
				</div>
				<div id="template" class="box">
					<table width="100%" border="1" cellspacing="0" cellpadding="0" class="maintable1">
						<tr class="tabletop">
							<td align="center" width="10%">订单号</td>
							<td align="center" width="5%">预约号</td>
							<td align="center" width="5%">预约人</td>
							<td align="center" width="8%">联系方式</td>
							<td align="center" width="8%">科室名称</td>
							<td align="center" width="6%">医生名称</td>
							<td align="center" width="4%">费用</td>
							<td align="center" width="12%">预约时间</td>
							<td align="center" width="10%">创建时间</td>
							<td align="center" width="6%">订单状态</td>
						
							<td align="center" width="10%">操作</td>
						
						</tr>
					</table>
				</div>
				<div align="right" class="page">
					<div class='ctrl'>
						<table align='center' id="ctrltab">
							<tr>
								<td class="prev" align="right">
									<a href="javascript:void(0)" id="prevPage" onclick=rotPaging(1);>上一页</a>
								</td>
								<td align="center">
									<p id="tagId"></p>
								</td>
								<td class="next" align="left">
									<a href="javascript:void(0)" onclick=rotPaging(2); id="nextPage">下一页</a>
								</td>
								<td>
									共计<span id="totalPage"></span>页
								</td>
								<td>
									转到<input type="text" id="gotoPage" onkeydown="gotoKeydown()" onkeypress="gotoKeydown()" onkeyup="gotoKeydown()" />页
								</td>
								<td>
									<input type="button" id="goto" onclick="gotoFunc()" />
								</td>
		  						<td width="20%">&nbsp;</td>
		  						<td width="20%">&nbsp;</td>
		  					</tr>
		  				</table>
		  			</div>
		  		</div>
			</div>
		</form>
		<input type="hidden" id="treeId" />
		<input type="hidden" id="treeNum" />
		<input type="hidden" id="condition" />
		<input type="hidden" id="pagingNumCnt" value="16" />
		<script type="text/javascript" src="<%=path %>/pub/js/paging.js"></script>
	</body>
</html>
