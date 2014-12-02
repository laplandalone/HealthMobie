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
		<script type="text/javascript" src="<%=path%>/pub/WdatePicker.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				$.getJSON("/mobile.htm?method=qryWakeTypeList", {"random":Math.random()}, function(data){ 
					var obj = document.getElementById("wakeType");
					if(data.length > 0)
					{
						for(var k = 0 ; k < data.length ; k++)
						{  
							var varItem = new Option(data[k].configVal, data[k].configType);
							obj.options.add(varItem);
						} 
					}
				});
			});
			function checkParam()
			{
				var wakeName = $("#wakeName").val();
				if(wakeName == "" || wakeName == null || wakeName == undefined)
				{
					$.dialog.alert("请输入提醒名称!", function(){return true;});
					return false;
				}
				var wakeType = $("#wakeType").val();
				if(wakeType == "" || wakeType == null || wakeType == undefined)
				{
					$.dialog.alert("请选择提醒类型!", function(){return true;});
					return false;
				}
				var wakeValue = $("#wakeValue").val();
				if(wakeValue == "" || wakeValue == null || wakeValue == undefined)
				{
					$.dialog.alert("请输入提醒阈值!", function(){return true;});
					return false;
				}
				var wakeDate = $("#wakeDate").val();
				if(wakeDate == "" || wakeDate == null || wakeDate == undefined)
				{
					$.dialog.alert("请选择提醒时间!", function(){return true;});
					return false;
				}
				var wakeContent = $("#wakeContent").val();
				if(wakeContent == "" || wakeContent == null || wakeContent == undefined)
				{
					$.dialog.alert("请输入提醒内容!", function(){return true;});
					return false;
				}
				return true;
			}
			
			function addWake()
			{
				if(!checkParam())
				{
					return;
				}
				else
				{
					var obj = JSON.stringify($("select,input,textarea").serializeObject());
					var dig = null;
					$.ajax({
						type : "POST",
						url : "/mobile.htm?method=addWake",
						data : obj,
						contentType:"application/json;charset=UTF-8",
						beforeSend : function()
						{
							dig = new $.dialog({title:"正在新增请等待...",esc:false,min:false,max:false,lock:true});
						},
						success: function(data) 
						{
							try
							{
								dig.close();
							}
							catch(e)
							{
								
							}
							if(data)
							{
								$.dialog({title:false, width:"150px", esc:false, height:"60px", zIndex:2000, icon:"succ.png", lock:true, content:"新增提醒成功!", ok:function() {window.location.reload(); return true;}});
							}
							else
							{
								$.dialog({title:false, width:"150px", esc:false, height:"60px", zIndex:2000, icon:"fail.png", lock:true, content:"新增提醒失败!", ok:function() {window.location.reload(); return true;}});
							}
						},
						error : function(data)
						{
							dig.close();
							$.dialog({title : false,esc:false, zIndex: 2000,width:"150px",height:"60px", icon: "fail.png", lock: true, content: "新增失败!",ok: function(){return true;}});
						}
					});
				}
			}
			
			function cancel()
			{
				window.location.reload();
			}
		</script>
  	</head>
  
  	<body>
  		<form action="">
  			<div class="main" align="center">
  				<table width="600px" border="0" cellspacing="10" cellpadding="0" align='center'>
  					<tr>
  						<td align="right">提醒名称</td>
  						<td>
  							<input type="text" id="wakeName" name="wakeName" class="subtext" style="width: 150px"/>
  						</td>
  					</tr>
  					<tr>
  						<td align="right">提醒类型</td>
  						<td>
  							<select id="wakeType" name="wakeType" class="subselect" style="width: 150px">
  								
  							</select>
  						</td>
  					</tr>
  					<tr>
  						<td align="right">提醒阈值</td>
  						<td>
  							<input type="text" id="wakeValue" name="wakeValue" class="subtext" style="width: 150px" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
  						</td>
  					</tr>
  					<tr>
  						<td align="right">提醒时间</td>
  						<td>
  							<input readonly="readonly" type="text" class="Wdate" id="wakeDate" name="wakeDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" style="border:1px solid #C0DC56;background:#fff;width:150px;font-size:12px;"/>
  						</td>
  					</tr>
  					<tr>
  						<td align="right">提醒内容</td>
  						<td>
  							<textarea id="wakeContent" name="wakeContent" rows="10" cols="77" style="border:1px solid #C0DC56;background:#fff;margin-bottom: 10px"></textarea>
							</br>
  						</td>
  					</tr>
  				</table>
  				<table width="100%" cellspacing="0" cellpadding="0" align='center'>
					<tr align='center'>
						<td>
							<input type="button" value="新增" class="button3" onclick="addWake()"/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="取消" class="button" onclick="cancel()"/>
						</td>
					</tr>
				</table>
  			</div>
  		</form>
  	</body>
</html>
