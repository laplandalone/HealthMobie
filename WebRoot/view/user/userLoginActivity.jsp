<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  	<head>
		<title>用户登录活跃度统计</title>
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
  			<input type="hidden" id="qryType" value="activity">
  			<div class="mainsearch">
		  		<table width="100%">
		  			<tr>
		  				<td align="right" width="12%">登录时间：</td>
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
		  				<td width="12%" align="center">
							<input type="button" onclick="qryUserList()" class="button3" value="查询" />
						</td>
						<td width="44%" colspan="2">&nbsp;</td>
		  			</tr>
		  		</table>
  			</div>  
  			<div class="main">
	  			<div class="title">
					<div class="titleleft"></div>
					<div class="titlecentre1">
						<h3>查询结果</h3>
						<span style="text-align: right;" id="totalNum"></span>
					</div>
				</div>
				<div id="template" class="box">
					<table width="100%" border="1" cellspacing="0" cellpadding="0" class="maintable1">
						<tr class="tabletop">
							<td width="15%">用户名称</td>
							<td width="8%">性别</td>
							<td width="15%">登录号码</td>
							<td width="10%">登录活跃数</td>
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
		  						<td width="10%">&nbsp;</td>
		  						<td width="10%">&nbsp;</td>
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
