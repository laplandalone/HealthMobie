<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hbgz.pub.cache.PubData"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String hospitalId = (String) session.getAttribute("hospitalId");
	List teamLst = PubData.qryTeamList(hospitalId);
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
		<script type="text/javascript" src="<%=path%>/js/json2.js"></script>
		<script type="text/javascript" src="<%=path%>/js/json.js"></script>
		<script type="text/javascript" src="<%=path%>/js/question.js"></script>
  	</head>
  
  	<body onload="qryOnlineDortorQues()">
  		<form action="">
  			<div class="mainsearch">
  				<input type="hidden" id="type" value="online"/>
	  			<table width="100%">
	  				<tr>
	  					<td align="right" width="15%">在线科室：</td>
						<td width="8%">
							<c:set var="teamLst" value="<%=teamLst %>"></c:set>
							<select id="teamId" name="teamId" class="subselect">
								<option value="">全部</option>
								<c:forEach items="${teamLst }" var="team">
									<c:if test="${team.expertFlag == '1' }">
										<option value="${team.teamId }">${team.teamName }</option>
									</c:if>
								</c:forEach>
							</select>
						</td>
						<td align="right" width="15%">医生名称：</td>
						<td>
							<input id="doctorName" name="doctorName" class="subtext" value="${doctorName }"/>
						</td>
						<td align="right" width="10%">
							<input type="button" class="button3" value="查询" onclick="qryOnlineDortorQues()"/>
						</td>
						<td width="72%">&nbsp;</td>
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
							<td align="center" width='15%'>医生姓名</td>
							<td align="center" width="20%">医生职称</td>
							<td align="center" width="20%">医生科室</td>
							<td align="center" width="10%">提问总数</td>
							<td align="center" width="10%">回复数</td>
							<td align="center" width="10%">未回复数</td>
							<td align="center" width="15%">最近提问时间</td>
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
  		</div>
  	</body>
</html>
