function qryRegisterOrder()
{
	var obj = JSON.stringify($("select,input").serializeObject());
	obj = qryStartFunc(obj);
	var dig = null;
	$.ajax({
		type : "POST",
		url : "/mobile.htm?method=qryRegisterOrder",
		data : obj,
		async : false,
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		beforeSend : function()
		{
			dig = new $.dialog({title:'正在查询请等待...',esc:false,min:false,max:false,lock:true});
		},
		success : function(data)
		{
			try
			{
				dig.close();
			}
			catch(e)
			{
				
			}
			createTable(data, 0);
		},
		error : function(data)
		{
			$.dialog.alert(data.statusText, function(){window.location.reload(); return true;});
		}
	});
}

function createTable(data, flagParam)
{
	var hospitalId = $("#hospitalId").val();
	var content = "<table id='table1' width='100%' border='1' cellspacing='0' cellpadding='0' class='maintable'>";
	content += "<tr class='tabletop'><td align='center' width='5%'>订单号</td><td align='center' width='5%'>预约号</td>";
	content += "<td align='center' width='4%'>类型</td><td align='center' width='5%'>预约人</td>";
	content += "<td align='center' width='8%'>联系方式</td><td align='center' width='10%'>医院名称</td>";
	content += "<td align='center' width='8%'>科室名称</td><td align='center' width='6%'>医生名称</td>";
	content += "<td align='center' width='4%'>费用</td><td align='center' width='12%'>预约时间</td>";
	content += "<td align='center' width='10%'>创建时间</td><td align='center' width='6%'>订单状态</td>";
	
	content += "<td align='center' width='10%'>操作</td>";
	
	content += "</tr>";
	if(data.count > 0)
	{
		var cnt = 0;
		if(flagParam == 0)
		{
			cnt = qryMiddleFunc(data.count);
		}
		$.each(data.registerOrderList, function(i, obj){
			if(i % 2)
			{
				content += "<tr class='bkf0' onmouseover='trColorChange(this,"+i+")' onmouseout='trColorChange(this,"+i+")'>";
			}
			else
			{
				content += "<tr class='aaa' onmouseover='trColorChange(this,"+i+")' onmouseout='trColorChange(this,"+i+")'>";
			}
			var registerId = obj.registerId;
			if(registerId == "0")
			{
				registerId = "普通号";
			}
			else
			{
				registerId = "专家号";
			}
			content += "<td>"+obj.orderId+"</td><td>"+obj.orderNum+"</td><td>"+registerId+"</td><td>"+obj.userName+"</td><td>"+obj.userTelephone+"</td>";
			content += "<td>"+obj.hospitalName+"</td><td>"+obj.teamName+"</td><td>"+obj.doctorName+"</td><td>"+obj.orderFee+"</td><td>"+obj.registerTime+"</td>";
			content += "<td>"+obj.createDate+"</td>";
			if(hospitalId == '101')
			{
				var orderState = obj.orderState;
				content += "<td>"+orderState+"</td><td style='text-align:center !important'>";
				if(orderState == "未处理")
				{
					content += "<a href='javascript:void(0)' class='linkmore' onclick='appointment(\""+obj.name+"\", "+obj.orderId+")'>预约</a>";
					content += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					content += "<a href='javascript:void(0)' class='linkmore' onclick='invalid(\""+obj.name+"\", "+obj.orderId+")'>作废</a>";
				}
				else
				{
					content += "<a href='javascript:void(0)' style='color:#cccccc'>预约</a>";
					content += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					content += "<a href='javascript:void(0)' style='color:#cccccc'>作废</a>";
				}
			}
			else if(hospitalId == '102')
			{
				var payState = obj.payState;
				var payStateCode = obj.payStateCode;
				content += "<td>"+payState+"</td><td style='text-align:center !important'>";
				if(payStateCode=='103')
				{
					content += "<a href='javascript:void(0)' class='linkmore' onclick='refund("+obj.orderId+")'>退款</a>";
				}else
				{
					content += "无";
				}
			}
			content += "</tr>";
		});
		if(flagParam == 0)
		{
			qryEndFunc(cnt);
		}
	}
	else
	{
		var cloumnNum = 13;
		if(hospitalId == "101")
		{
			cloumnNum = 13;
		}
		content += "<tr><td colspan='"+cloumnNum+"' style='text-align:center'>没有查询到相应的记录!</td></tr>";
		$("#ctrltab td").each(function(i,data){
			if(i < 8)
			{
				$(data).css("visibility","hidden");
			}
		});
	}
	content += "</table>";
	$("#template").html(content);
}

function qryPaging(val)
{
	var obj = qryPaingStartFunc(val);
	var dig = null;
	$.ajax({
		type : "POST",
		url : "/mobile.htm?method=qryRegisterOrder",
		data : obj,
		contentType:'application/json;charset=UTF-8',
		dataType: "json",
		beforeSend : function()
		{
			dig = new $.dialog({title:'正在查询请等待...',esc:false,min:false,max:false,lock:true});
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
			createTable(data, 1);
		},
		error : function(data)
		{
			dig.close();
			$.dialog({title : false,esc:false, zIndex: 2000,width:"150px",height:"60px", icon: 'fail.png', lock: true, content: '查询失败!',ok: function(){return true;}});
		}
	});
}

function trColorChange(val, i) 
{
	if (val.className == "bkf0" || val.className == "aaa")
	{
		val.className = "trcolor";
	} 
	else 
	{
		if (i % 2)
		{
			val.className = "bkf0";
		}
		else
		{
			val.className = "aaa";
		}
	}
}

//预约挂号
function appointment(name, orderId)
{
	$.dialog.confirm("是否预约"+name+"医生进行看诊?",
		function ()
		{
			option(orderId, "appointment");
		},
		function(){return true;}
	);
}

//
function refund(orderId)
{
	$.dialog.confirm("是否确定退款?",
		function ()
		{
			option(orderId, "refund");
		},
		function(){return true;}
	);
}

//作废挂号
function invalid(name, orderId)
{
	$.dialog.confirm("是否作废"+name+"医生的挂号?",
		function ()
		{
			option(orderId, "invalid");
		},
		function(){return true;}
	);
}

function option(orderId, optionFlag)
{
	$.ajax({
		url : "/mobile.htm?method=appointmentOrinvalidOrder",
		type : "POST",
		data : "orderId="+orderId+"&optionFlag="+optionFlag,
		success:function(data)
		{
			$.dialog.alert('操作成功',function(){
				window.location.reload();
			});
		},
		error:function(stata)
		{
			$.dialog.alert(stata.statusText, function(){return true;});
		}
	});
}