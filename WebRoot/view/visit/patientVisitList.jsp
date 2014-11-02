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
		<script type="text/javascript" src="<%=path%>/js/patientVisit.js"></script>
  	</head>
  
  	<body>
  		<form action="">
  			<div class="mainsearch">
		  		<table width="100%">
					<tr>
						<td align="right" width="10%">随访时间：</td>
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
						<td align="right" width="10%">
							<input type="button" class="button3" value="查询" onclick="qryPatientVisit()"/>
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
							<td width="20%">随访姓名</td>
							<td width="10%">随访类型</td>
							<td width="10%">就诊卡号</td>
							<td width="30%">随访时间</td>
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
		  					</tr>
		  				</table>
		  			</div>
		  		</div>
  			</div>
  		</form>
  		<input type="hidden" id="treeId" />
		<input type="hidden" id="treeNum" />
		<input type="hidden" id="condition" />
		<input type="hidden" id="pagingNumCnt" value="10" />
		<script type="text/javascript" src="<%=path %>/pub/js/paging.js"></script>
  	</body>  		
</html>
