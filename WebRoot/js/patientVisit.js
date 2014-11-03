$(document).ready(function(){
	qryPatientVisit();
});

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
	content += "<tr class='tabletop'><td style='text-align:center' width='20%'>随访姓名</td><td style='text-align:center' width='10%'>随访类型</td><td style='text-align:center' width='10%'>就诊卡号</td><td style='text-align:center' width='30%'>随访时间</td></tr>";
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
			var visitType = obj.visitType;
			if(visitType == "asd")
			{
				visitType = "先心手术随访";
			}
			else if(visitType == "mvr")
			{
				visitType = "房颤手术随访";
			}
			var cardId = obj.cardId;
			if(cardId == "" ||　cardId == null)
			{
				cardId = "无";
			}
			content += "<td width='20%' style='text-align:center'>"+obj.visitName+"</td><td width='10%' style='text-align:center'>"+visitType+"</td>";
			content += "<td width='10%' style='text-align:center'>"+cardId+"</td><td width='30%' style='text-align:center'>"+obj.createDate+"</td></tr>";
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
