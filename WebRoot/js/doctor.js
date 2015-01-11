function queryMed() 
{
	var obj = JSON.stringify($("select,input").serializeObject());
	obj = qryStartFunc(obj);
	var dig = null;
	$.ajax({
		type : "POST",
		url : "/doctor.htm?method=queryPre",
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
	var pageNum = $("#pageNum").val();
	if(pageNum != null && pageNum != "" && pageNum != undefined && pageNum != "null")
	{
		showList(pageNum);
	}
}

function createTable(data, flagParam)
{
	var content = "<table id='table1' width='100%' border='1' cellspacing='0' cellpadding='0' class='maintable'>";
	content += "<tr class='tabletop'><td align='center' width='15%'>医生名称</td><td align='center' width='20%'>职称</td>";
	content += "<td align='center' width='10%'>性别</td><td align='center' width='20%'>科室</td><td align='center' width='10%'>挂号费</td><td align='center' width='10%'>操作</td></tr>";
	if(data.count > 0)
	{
		var cnt = 0;
		if(flagParam == 0)
		{
			cnt = qryMiddleFunc(data.count);
		}
		$.each(data.doctorList, function(i, obj){
			if(i % 2)
			{
				content += "<tr class='bkf0' onmouseover='trColorChange(this,"+i+")' onmouseout='trColorChange(this,"+i+")'>";
			}
			else
			{
				content += "<tr class='aaa' onmouseover='trColorChange(this,"+i+")' onmouseout='trColorChange(this,"+i+")'>";
			}
			var post = obj.post;
			if(post == null || post == "null" || post == undefined || post == "")
			{
				post = "无";
			}
			var sex = obj.sex;
			if(sex == null || sex == "null" || sex == undefined || sex == "")
			{
				sex = "无";
			}
			var register_fee = obj.register_fee;
			if(register_fee == null || register_fee == "null" || register_fee == undefined || register_fee == "")
			{
				register_fee = "无";
			}
			content += "<td style='text-align:center' width='15%'>"+obj.name+"</td><td style='text-align:center' width='20%'>"+post+"</td>";
			content += "<td style='text-align:center' width='10%'>"+sex+"</td><td style='text-align:center' width='20%'>"+obj.team_name+"</td>";
			content += "<td style='text-align:center' width='10%'>"+register_fee+"</td><td align='center' width='10%' style='text-align:center !important'>";
			content += "<a href='javascript:void(0)' class='linkmore' onclick='queryDoctor(\""+obj.doctor_id+"\")'>管理</a>";
			content += "&nbsp;&nbsp;&nbsp;&nbsp;";
			content += "<a href='javascript:void(0)' class='linkmore' onclick='deleteDoctor(\""+obj.doctor_id+"\")'>删除</a>";
			content += "</td></tr>";
		});
		if(flagParam == 0)
		{
			qryEndFunc(cnt);
		}
	}
	else
	{
		content += "<tr><td colspan='5' style='text-align:center'>没有查询到相应的记录!</td></tr>";
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
		url : "/doctor.htm?method=queryPre",
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

function queryDoctor(doctorId) 
{
	if (doctorId != "") 
	{
		window.location.href = "/doctor.htm?method=getDoctor&doctorId="+doctorId+"&pageNum="+gcurTag;
	}
}

function deleteDoctor(doctorId) 
{
	$.dialog.confirm("是否删除该医生?", function() {
		$.ajax({
			type : "POST",
			url : "/doctor.htm?method=deleteDoctor",
			data : "doctorId=" + doctorId,
			success : function(data) 
			{
				if (data == "success") 
				{
					$.dialog({title : false, width : "150px", esc : false, height : "60px", zIndex : 2000, icon : "succ.png", lock : true, content : "删除医生信息成功!", ok : function(){queryMed();return true;}});
				} 
				else
				{
					$.dialog({title : false, width : "150px", esc : false, height : "60px", zIndex : 2000, icon : "fail.png", lock : true, content : "删除医生信息失败!", ok : function() {queryMed(); return true;}});
				}
			},
			error : function(data) {$.dialog.alert(data.statusText, function() {return true;});
			}
		});
	}, function() {
		return true;
	});
}

function enterQry(fName) 
{
	if (event.keyCode == 13) 
	{
		event.returnValue = false;
		fName += '()';
		eval(fName);
	}
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