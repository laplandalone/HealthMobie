function qryUserList()
{
	var url = "/visit.htm?method=qryUserList";
	var qryType = $("#qryType").val();
	if("activity" == qryType)
	{
		url = "/visit.htm?method=qryUserLoginActivityList";
	}
	var obj = JSON.stringify($("select,input").serializeObject());
	obj = qryStartFunc(obj);
	var dig = null;
	$.ajax({
		type : "POST",
		url : url,
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
	var content = "<table id='table1' width='100%' border='1' cellspacing='0' cellpadding='0' class='maintable'>";
	var qryType = $("#qryType").val();
	if("info" == qryType)
	{
		$("#totalNum").html("总注册人数："+data.count+"人");
		content += "<tr class='tabletop'><td style='text-align:center' width='8%'>ID</td><td style='text-align:center' width='10%'>姓名</td><td style='text-align:center' width='10%'>联系方式</td><td style='text-align:center' width='8%'>性别</td><td style='text-align:center' width='15%'>证件号码</td><td style='text-align:center' width='15%'>病案号</td><td style='text-align:center' width='15%'>注册时间</td><td style='text-align:center' width='15%'>注册平台</td></tr>";
		if(data.count > 0)
		{
			var cnt = 0;
			if(flagParam == 0)
			{
				cnt = qryMiddleFunc(data.count);
			}
			$.each(data.userList, function(i, obj){
				if(i % 2)
				{
					content += "<tr class='bkf0' onmouseover='trColorChange(this,"+i+")' onmouseout='trColorChange(this,"+i+")'>";
				}
				else
				{
					content += "<tr class='aaa' onmouseover='trColorChange(this,"+i+")' onmouseout='trColorChange(this,"+i+")'>";
				}
				content += "<td width='8%' style='text-align:center'>"+obj.userId+"</td><td width='10%' style='text-align:center'>"+obj.userName+"</td>";
				content += "<td width='10%' style='text-align:center'>"+obj.telephone+"</td><td width='8%' style='text-align:center'>"+obj.sex+"</td>";
				content += "<td width='15%' style='text-align:center'>"+obj.userNo+"</td><td width='15%' style='text-align:center'>"+obj.cardNo+"</td>";
				content += "<td width='15%' style='text-align:center'>"+obj.createDate+"</td><td style='text-align:center' width='15%'>"+obj.hospitalId+"</td></tr>";
			});
			if(flagParam == 0)
			{
				qryEndFunc(cnt);
			}
		}
		else
		{
			content += "<tr><td colspan='7' style='text-align:center'>没有查询到相应的记录!</td></tr>";
			$("#ctrltab td").each(function(i,data){
				if(i < 8)
				{
					$(data).css("visibility","hidden");
				}
			});
		}
	}
	else if("activity" == qryType)
	{
		$("#totalNum").html("用户登录活跃数："+data.totalNum);
		content += "<tr class='tabletop'><td style='text-align:center' width='15%'>用户名称</td><td style='text-align:center' width='8%'>性别</td>";
		content += "<td style='text-align:center' width='15%'>登录号码</td><td style='text-align:center' width='15%'>登录活跃数</td></tr>";
		if(data.count > 0)
		{
			var cnt = 0;
			if(flagParam == 0)
			{
				cnt = qryMiddleFunc(data.count);
			}
			$.each(data.activityList, function(i, obj){
				if(i % 2)
				{
					content += "<tr class='bkf0' onmouseover='trColorChange(this,"+i+")' onmouseout='trColorChange(this,"+i+")'>";
				}
				else
				{
					content += "<tr class='aaa' onmouseover='trColorChange(this,"+i+")' onmouseout='trColorChange(this,"+i+")'>";
				}
				content += "<td width='15%' style='text-align:center'>"+obj.userName+"</td><td width='8%' style='text-align:center'>"+obj.sex+"</td>";
				content += "<td width='15%' style='text-align:center'>"+obj.telephone+"</td><td style='text-align:center' width='15%'>"+obj.activityNum+"</td></tr>";
			});
			if(flagParam == 0)
			{
				qryEndFunc(cnt);
			}
		}
		else
		{
			content += "<tr><td colspan='4' style='text-align:center'>没有查询到相应的记录!</td></tr>";
			$("#ctrltab td").each(function(i,data){
				if(i < 8)
				{
					$(data).css("visibility","hidden");
				}
			});
		}
	}
	content += "</table>";
	$("#template").html(content);
}

function qryPaging(val)
{
	var url = "/visit.htm?method=qryUserList";
	var qryType = $("#qryType").val();
	if("activity" == qryType)
	{
		url = "/visit.htm?method=qryUserLoginActivityList";
	}
	var obj = qryPaingStartFunc(val);
	var dig = null;
	$.ajax({
		type : "POST",
		url : url,
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