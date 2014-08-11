<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  	<head>
    	<title>信息发布管理</title>
    	<link href="<%=path%>/pub/css/sub.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/pub/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="<%=path%>/pub/dialog/lhgdialog.min.js?skin=idialog"></script>
		<script type="text/javascript" src="<%=path%>/pub/js/calendar.js"></script>
		<script type="text/javascript" src="<%=path%>/pub/js/date.js"></script>
		<script type="text/javascript" src="<%=path%>/js/comm.js"></script>
		<script type="text/javascript" src="<%=path%>/js/news.js"></script>
  	</head>
  
  	<body>
  		<table width="100%">
  			<input type="hidden" id="selStartTime" value="${startTime }"/>
  			<input type="hidden" id="selEndTime" value="${endTime }"/>
  			<input type="hidden" id="selNewsType" value="${newsType }"/>
  			<input type="hidden" id="selTypeId" value="${typeId }"/>
  			<input type="hidden" id="selState" value="${state }"/>
  			<tr>
  				<td align="right" width="8%">创建时间：</td>
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
  				<td align="center" width="14%">
  					<select id="newsType" name="newsType" class="subselect">
  						<option value="">信息类型</option>
  						<option value="NEWS">新闻</option>
  						<option value="BAIKE">百科</option>
  					</select>
  				</td>
  				<td align="center" width="14%">
  					<select id="typeId" name="typeId" class="subselect">
  						<option value="">内容分类</option>
  					</select>
  				</td>
  				<td align="center" width="14%">
  					<select id="state" name="state" class="subselect">
  						<option value="">全部状态</option>
  						<option value="00A">正常</option>
  						<option value="00X">作废</option>
  					</select>
  				</td>
  				<td width="12%" align="center">
					<input type="button" onclick="qryNewsList()" class="button3" value="查询" />
				</td>
				<td width="12%" align="center">
					<input type="button" onclick="addNews()" class="button1" value="发布新信息" />
				</td>
				<td width="12%" align="center">
					<input type="button" onclick="addNewsType()" class="button1" value="新增分类" />
				</td>
  			</tr>
  		</table>
  		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="maintable1">
			<tr class="tabletop">
				<td width="5%">编号</td>
				<td width="10%">医院名称</td>
				<td width="6%">所属板块</td>
				<td width="8%">分类</td>
				<td width="20%">信息标题</td>
				<td width="6%">发布状态</td>
				<td width="10%">创建时间</td>
				<td width="5%">操作</td>
			</tr>
			<c:forEach items="${newsList }" var="news">
				<tr>
					<td>${news.newsId }</td>
					<td>${news.hospitalName }</td>
					<td>${news.newsType }</td>
					<td>${news.typeId }</td>
					<td>${news.newsTitle }</td>
					<td>${news.stateVal }</td>
					<td>${news.createDate }</td>
					<td style="text-align:center !important">
						<c:choose>
							<c:when test="${news.state == '00A' }">
								<a href="javascript:void(0)" class="linkmore" onclick="updateNews('${news.newsId }')">修改</a>
							</c:when>
							<c:otherwise>
								<a href="javascript:void(0)" style="color:#cccccc">修改</a>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</table>
  	</body>
</html>
