<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String doctorId=request.getParameter("doctorId");
	String teamId=request.getParameter("teamId");
	String fee=request.getParameter("fee");
	String num=request.getParameter("num");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title></title>
		<link href="<%=path%>/pub/css/sub.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/pub/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="<%=path%>/pub/dialog/lhgdialog.min.js?skin=idialog"></script>
		<script type="text/javascript" src="<%=path%>/pub/js/calendar.js"></script>
		<script type="text/javascript" src="<%=path%>/pub/js/date.js"></script>
		<script type="text/javascript" src="<%=path%>/js/comm.js"></script>
		<script type="text/javascript" src="<%=path%>/js/json.js"></script>
		<script type="text/javascript" src="<%=path%>/js/json2.js"></script>
		<script type="text/javascript">
			var allFlag = "0";
			$("document").ready(function(){
    			$("#all").click(function(){
     				if(allFlag == 0)
     				{
			    	  	$("[name='checkbox']").attr("checked", 'true');//全选
			    	  	allFlag = "1";
     				}
     				else
    				{
    	  				$("[name='checkbox']").removeAttr("checked");//取消全选 
    	  				allFlag = "0";
   	 				}
		    	});
			});

			function checkNum(oNum)
			{
			    //定义正则表达式部分
				var strP=/^\d+(\.\d+)?$/;
		  		if(!strP.test(oNum)) return false;
	  			try
	  			{
	  				if(parseFloat(oNum)!=oNum) return false;
	  			}
	  			catch(ex)
	  			{
	   				return false;
	  			}
	  			return true;
			}

			function registerTime()
			{
	   			var register =[];
			   	var fee = $("#fee").val();
			   	var num = $("#num").val();
	   			if(!checkNum(fee))
	   			{
	   				$.dialog.alert("预约费用输入有误", function(){return true;});
					return;
				}
				if (!checkNum(num)) 
				{
					$.dialog.alert("预约数量输入有误", function(){return true;});
					return;
				}
				var state = "00A";
				var teamId = '<%=teamId%>';
	   			var doctorId = '<%=doctorId%>';
				for(var i = 1; i <= 10; i++)
				{
					var time = $("#day"+i);
					var week = time.attr("registerWeek");
					var dayType = time.attr("dayType");
					var checkFlag = document.getElementById("day"+i);
					if(checkFlag.checked)
					{
						var row = {registerWeek:week, dayType:dayType, registerNum:num, registerFee:fee, state:state, teamId:teamId, doctorId:doctorId};
						register.push(row);
					}	
				}
				if(register.length==0)
				{
					$.dialog.alert("请至少选择一项预约时间", function(){return true;});
					return;
				}
				var regisgerTimes=JSON.stringify(register);
				window.location.href="/doctor.htm?method=updateRegisterTime&doctorId="+doctorId+"&registerTimes="+regisgerTimes;
			}
		</script>
	</head>
	<body>
		<div id="template" style="height:700px;overflow:auto">
			<br/>
			<table width="50%" border="0" cellspacing="0" cellpadding="0" align='center'>
				<tr>
				<td>
					<div class="titleba"><h3>医生预约时间</h3></div>	
					</td>
				</tr>
			</table>
			<table width="50%" border="1" cellspacing="0" cellpadding="0" align="center" class="maintable1">
				<tr class="tabletop">
					<td align="center">工作日</td>
					<td align="center" width="20%">上午/下午</td>
					<td align="center" width="20%">选择
						<input type='checkbox' name="all"  id='all'  />
					</td>
				</tr>
				<tr>
					<td align="center" width='15%'>周一</td>
					<td align="center">上午</td>
					<td align="center">
						<input type='checkbox' name="checkbox"  id='day1' registerWeek='一' dayType='上午'  />
					</td>		
				</tr>
				<tr>
					<td align="center" width='15%'>周一</td>
					<td align="center">下午</td>
					<td align="center">
						<input type='checkbox' name="checkbox"  id='day2' registerWeek='一' dayType='下午'  />
					</td>		
				</tr>
				<tr>
					<td align="center" width='15%'>周二</td>
					<td align="center">上午</td>
					<td align="center">
						<input type='checkbox' name="checkbox"  id='day3' registerWeek='二' dayType='上午'  />
					</td>		
				</tr>
				<tr>
					<td align="center" width='15%'>周二</td>
					<td align="center">下午</td>
					<td align="center">
						<input type='checkbox' name="checkbox"  id='day4' registerWeek='二' dayType='下午'  />
					</td>		
				</tr>
				<tr>
					<td align="center" width='15%'>周三</td>
					<td align="center">上午</td>
					<td align="center">
						<input type='checkbox' name="checkbox" id='day5' registerWeek='三' dayType='上午'  />
					</td>		
				</tr>
				<tr>
					<td align="center" width='15%'>周三</td>
					<td align="center">下午</td>
					<td align="center">
						<input type='checkbox' name="checkbox"  id='day6' registerWeek='三' dayType='下午'  />
					</td>		
				</tr>
				<tr>
					<td align="center" width='15%'>周四</td>
					<td align="center">上午</td>
					<td align="center">
						<input type='checkbox' name="checkbox"  id='day7' registerWeek='四' dayType='上午'  />
					</td>		
				</tr>
				<tr>
					<td align="center" width='15%'>周四</td>
					<td align="center">下午</td>
					<td align="center">
						<input type='checkbox' name="checkbox"  id='day8' registerWeek='四' dayType='下午'  />
					</td>		
				</tr>
				<tr>
					<td align="center" width='15%'>周五</td>
					<td align="center">上午</td>
					<td align="center">
						<input type='checkbox' name="checkbox"  id='day9' registerWeek='五' dayType='上午'  />
					</td>		
				</tr>
				<tr>
					<td align="center" width='15%'>周五</td>
					<td align="center">下午</td>
					<td align="center">
						<input type='checkbox' name="checkbox"  id='day10' registerWeek='五' dayType='下午'  />
					</td>		
				</tr>		
			</table>
			<br/>
			<table width="50%" border="0" cellspacing="0" cellpadding="0" align='center' >
				<tr align='center'>
				<td>
				预约费:<input type='text'  id='fee' value="<%=fee%>" size=1/>
			               预约数量:<input type='text' id='num'  value="<%=num%>" size=1 />
			               </td>
			    </tr>
			</table>
			<table width="50%" border="0" cellspacing="0" cellpadding="0" align='center'>
				<br/>
				<tr align='center'>
				<td>
					<input type="button" onclick="registerTime()" class="button3" value="确定" />&nbsp;&nbsp;
					<input type="button" onclick="registerTime()" class="button1" value="取消" />
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
