$(document).ready(function(){
	$.getJSON("/doctor.htm?method=qryTeamList",function(data){
		if(data.length > 0)
		{
			var obj = document.getElementById("teamId");
			for(var k = 0 ; k < data.length ; k++)
			{  
				var varItem = new Option(data[k].teamName, data[k].teamId);
				obj.options.add(varItem);
			} 
		}
	});
});

function qryOnlineDortor()
{
	var obj = JSON.stringify($("select,input").serializeObject());
	obj = qryStartFunc(obj);
	var dig = null;
	$.ajax({
		type : "POST",
		url : "/doctor.htm?method=qryOnlineDortorList",
		data : obj,
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
	content += "<tr class='tabletop'><td width='3%'>在线</td><td width='7%'>医生名称</td><td width='7%'>职称</td><td width='3%'>性别</td><td width='8%'>科室</td><td width='6%'>挂号费</td> <td width='20%'>医生介绍</td><td width='20%'>擅长领域</td></tr>";
	if(data.count > 0)
	{
		$("#tab").css("display", "block");
		var cnt = 0;
		if(flagParam == 0)
		{
			cnt = qryMiddleFunc(data.count);
		}
		var onlineNum = 0;
		$.each(data.onlineDortorList, function(i, obj){
			if(i % 2)
			{
				content += "<tr class='bkf0' onmouseover='trColorChange(this,"+i+")' onmouseout='trColorChange(this,"+i+")'>";
			}
			else
			{
				content += "<tr class='aaa' onmouseover='trColorChange(this,"+i+")' onmouseout='trColorChange(this,"+i+")'>";
			}
			content += "<td width='3%' style='text-align:center'><input type='checkbox' name='box' class='"+obj.state+"' id='"+obj.doctorId+"' value='"+obj.teamId+"' ";
			if(obj.onlineFlag == '0')
			{
				content += "checked='checked' ";
				onlineNum++;
			}
			var sex = obj.sex;
			if(sex == "" || sex == null || sex == undefined)
			{
				sex = "";
			}
			content += "/></td><td width='7%' style='text-align:center'>"+obj.name+"</td><td width='7%' style='text-align:center'>"+obj.post+"</td><td width='3%' style='text-align:center'>"+sex+"</td><td width='8%' style='text-align:center'>"+obj.teamName+"</td>";
			content += "<td width='6%' style='text-align:center'>"+obj.registerFee+"</td> ";
			var introduce = obj.introduce;
			if(introduce != "" && introduce != null && introduce != undefined)
			{
				if(introduce.length > 20)
				{
					introduce = introduce.substring(0, 19) + "...";
				}
			}
			else
			{
				introduce = "";
			}
			var skill = obj.skill;
			if(skill != "" && skill != null && skill != undefined)
			{
				if(skill.length > 20)
				{
					skill = skill.substring(0, 19) + "...";
				}
			}
			else
			{
				skill = "";
			}
			content += "<td width='20%' title='"+obj.introduce+"'>"+introduce+"</td><td width='20%' title='"+obj.skill+"'>"+skill+"</td></tr>";
		});
		if(flagParam == 0)
		{
			qryEndFunc(cnt);
		}
		if(onlineNum == 0)
		{
			$("#offline").prop("disabled", "disabled");
		}
		else if(onlineNum == 10)
		{
			$("#online").prop("disabled", "disabled");
		}
		else
		{
			$("#offline").prop("disabled", "");
			$("#online").prop("disabled", "");
		}
	}
	else
	{
		content += "<tr><td colspan='8' style='text-align:center'>没有查询到相应的记录!</td></tr>";
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

//下线
function doctorOffline()
{
	var doctorId = "[";
	var count = 0;
	$("input[name='box']").each(function(){
		if (!$(this).prop("checked") && ($(this).prop("class") == '00A')) 
		{
			count++;
			doctorId += "{\"doctorId\":\"" + $(this).prop("id") + "\",\"teamId\":\"" + $(this).prop("value") + "\"},";
	    }
	});
	if(count > 0)
	{
		$.dialog.confirm("选中的医生确定要下线?", 
			function(){
				doctorId = doctorId.substring(0, doctorId.length - 1) + "]";
				$.ajax({
					type : "POST",
					url : "/doctor.htm?method=updateOnlineState",
					data : "doctorId="+doctorId+"&operatorType=offline",
					success: function(data) 
					{
						if(data)
						{
							$.dialog({title:false, width:"150px", esc:false, height:"60px", zIndex:2000, icon:"succ.png", lock:true, content:"医生下线成功!", ok:function() {qryOnlineDortor(); return true;}});
						}
						else
						{
							$.dialog({title:false, width:"150px", esc:false, height:"60px", zIndex:2000, icon:"fail.png", lock:true, content:"医生下线失败!", ok:function() {qryOnlineDortor(); return true;}});
						}
					},
					error : function(data)
					{
						$.dialog.alert(stata.statusText, function(){return true;});
					}
				});
			},
			function(){return true;}
		);
	}
	else
	{
		$.dialog.alert("请先选择要下线的医生!", function(){return true;});
	}
}

//上线
function doctorOnline()
{
	var doctorId = "[";
	var count = 0;
	$("input[name='box']").each(function(){
		if($(this).prop("checked") && ($(this).prop("class") == '00X'))
		{
			count++;
			doctorId += "{\"doctorId\":\"" + $(this).prop("id") + "\",\"teamId\":\"" + $(this).prop("value") + "\"},";
		}
	});
	if(count > 0)
	{
		$.dialog.confirm("选中的医生确定要上线?", 
			function(){
				doctorId = doctorId.substring(0, doctorId.length - 1) + "]";
				$.ajax({
					type : "POST",
					url : "/doctor.htm?method=updateOnlineState",
					data : "doctorId="+doctorId+"&operatorType=online",
					success: function(data) 
					{
						if(data)
						{
							$.dialog({title:false, width:"150px", esc:false, height:"60px", zIndex:2000, icon:"succ.png", lock:true, content:"医生上线成功!", ok:function() {qryOnlineDortor(); return true;}});
						}
						else
						{
							$.dialog({title:false, width:"150px", esc:false, height:"60px", zIndex:2000, icon:"fail.png", lock:true, content:"医生上线失败!", ok:function() {qryOnlineDortor(); return true;}});
						}
					},
					error : function(data)
					{
						$.dialog.alert(stata.statusText, function(){return true;});
					}
				});
			},
			function(){return true;}
		);
	}
	else
	{
		$.dialog.alert("请先选择要上线的医生!", function(){return true;});
	}
}

function qryPaging(val)
{
	var obj = qryPaingStartFunc(val);
	var dig = null;
	$.ajax({
		type : "POST",
		url : "/doctor.htm?method=qryOnlineDortorList",
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