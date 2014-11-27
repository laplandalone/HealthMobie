<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<script type="text/javascript" src="<%=path%>/js/json2.js"></script>
		<script type="text/javascript" src="<%=path%>/js/json.js"></script>
		<script type="text/javascript" src="<%=path%>/js/user.js"></script>
  	</head>
  
  	<body onload="qryUserList()">
  		<form action="">
  			<div class="mainsearch">
		  		<table width="100%">
					<tr>
						<td align="right" width="10%">用户名称：</td>
						<td width="8%">
							<input id="userName" name="userName" class="subtext"/>
						</td>
						<td align="right" width="10%">用户性别：</td>
						<td width="8%">
							<select id="sex" name="sex" class="subselect">
								<option value="">全部</option>
								<option value="男">男</option>
								<option value="女">女</option>
							</select>
						</td>
						<td align="right" width="10%">联系方式：</td>
						<td width="8%">
							<input id="telephone" name="telephone" class="subtext"/>
						</td>
						<td align="right" width="10%">
							<input type="button" class="button3" value="查询" onclick="qryUserList()"/>
						</td>
						<td width="36%">&nbsp;</td>
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
							<td width="8%">用户ID</td>
							<td width="10%">用户名称</td>
							<td width="10%">联系方式</td>
							<td width="8%">用户性别</td>
							<td width="20%">证件号码</td>
							<td width="15%">用户病案号</td>
							<td width="20%">注册时间</td>
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
