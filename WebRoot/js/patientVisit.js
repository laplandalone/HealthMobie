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
	if(data.length > 0)
	{
		$.each(data, function(i, obj){
			if(i != data.length - 1)
			{
				if(i % 2 == 0)
				{
					content += "<tr><td style='text-align:right;width:30%'>"+obj.codeFlagVal+"&nbsp;&nbsp;</td><td style='text-align:left;width:20%'>&nbsp;&nbsp;"+obj.codeValFlag+"</td>";
				}
				else
				{
					content += "<td style='text-align:right;width:30%'>"+obj.codeFlagVal+"&nbsp;&nbsp;</td><td style='text-align:left;width:20%'>&nbsp;&nbsp;"+obj.codeValFlag+"</td></tr>";
				}
			}
			else
			{
				if(i % 2 == 0)
				{
					content += "<tr><td style='text-align:right;width:30%'>"+obj.codeFlagVal+"&nbsp;&nbsp;</td><td style='text-align:left;width:20%'>&nbsp;&nbsp;"+obj.codeValFlag+"</td><td style='text-align:left;width:30%'>&nbsp;</td><td style='text-align:left;width:20%'>&nbsp;</td></tr>";
				}
				else
				{
					content += "<td style='text-align:right;width:30%'>"+obj.codeFlagVal+"&nbsp;&nbsp;</td><td style='text-align:left;width:20%'>&nbsp;&nbsp;"+obj.codeValFlag+"</td></tr>";
				}
			}
		});
	}
	else
	{
		content += "<tr><td colspan='6' style='text-align:center'>没有查询到相应的记录!</td></tr>";
	}
	content += "</table>";
	$("#template").html(content);
}