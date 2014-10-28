function qryPatientVisit()
{
	var obj = JSON.stringify($("select,input").serializeObject());
	var dig = null;
	$.ajax({
		type : "POST",
		url : "/visit.htm?method=qryPatientVisitList",
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
			createTable(data);
		},
		error : function(data)
		{
			$.dialog.alert(data.statusText, function(){window.location.reload(); return true;});
		}
	});
}

function createTable(data)
{
	var content = "<table id='table1' width='100%' border='1' cellspacing='0' cellpadding='0' class='maintable'>";
	content += "<tr class='tabletop'><td width='10%'>随访ID</td><td width='20%'>随访姓名</td><td width='10%'>随访类型</td><td width='10%'>随访人ID</td><td width='10%'>卡ID</td><td width='10%'>状态</td><td width='30%'>随访时间</td></tr>";
	if(data.length > 0)
	{
		$.each(data, function(i, obj){
			if(i % 2)
			{
				content += "<tr class='bkf0' onmouseover='trColorChange(this,"+i+")' onmouseout='trColorChange(this,"+i+")' onclick='viewVisitDetail("+obj.visitId+")'>";
			}
			else
			{
				content += "<tr class='aaa' onmouseover='trColorChange(this,"+i+")' onmouseout='trColorChange(this,"+i+")' onclick='viewVisitDetail("+obj.visitId+")'>";
			}
			var state = obj.state;
			if(state == "00A")
			{
				state = "正常";
			}
			else
			{
				state = "作废";
			}
			content += "<td width='10%' style='text-align:center'>"+obj.visitId+"</td><td width='20%' style='text-align:center'>"+obj.visitName+"</td><td width='10%' style='text-align:center'>"+obj.visitType+"</td>";
			content += "<td width='10%' style='text-align:center'>"+obj.patientId+"</td><td width='10%' style='text-align:center'>"+obj.cardId+"</td><td width='10%' style='text-align:center'>"+state+"</td><td width='30%' style='text-align:center'>"+obj.createDate+"</td></tr>";
		});
	}
	else
	{
		content += "<tr><td colspan='6' style='text-align:center'>没有查询到相应的记录!</td></tr>";
	}
	content += "</table>";
	$("#template").html(content);
}

function viewVisitDetail(visitId)
{
	lockScreen();
	$.dialog({width:"900px", esc:false, title:"随访明细", content:"url:/visit.htm?method=qryVisitDetail&visitId="+visitId, min:false, max:false, lock:true, close:function(){unlockScreen();}});
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