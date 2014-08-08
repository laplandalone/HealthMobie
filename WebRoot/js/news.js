$(document).ready(function(){
	$("#newsType").change(function(){
		var newsType = $("#newsType").val();
		$.getJSON("/news.htm?method=qryNewsTypeList", {"newsType":newsType}, function(data){ 
			var obj = document.getElementById("typeId");
			for(var j = obj.options.length - 1; j > 0; j--)
			{
				obj.options.remove(j);
			}
			if(data.length > 0)
			{
				for(var k = 0 ; k < data.length ; k++)
				{  
					var varItem = new Option(data[k].configVal, data[k].configId);
					obj.options.add(varItem);
				} 
			}
		});
	});
	
	var newsType = $("#selNewsType").val();
	$("#newsType option[value='" + newsType + "']").attr("selected", true);
	
	$.getJSON("/news.htm?method=qryNewsTypeList", {"newsType":newsType}, function(data){
		var options = "";
		var typeId = $("#selTypeId").val();
		if(typeId == "")
		{
			options += "<option value='' selected='selected'>内容分类</option>"; 
		}
		else
		{
			options += "<option value=''>内容分类</option>"; 
		}
		if(data.length > 0)
		{
			for(var i = 0; i < data.length; i++)
			{
				if(typeId == data[i].configId)
				{
					options += "<option value='"+data[i].configId+"' selected='selected'>"+data[i].configVal+"</option>"; 
				}
				else
				{
					options += "<option value='"+data[i].configId+"'>"+data[i].configVal+"</option>"; 
				}
			}
		}
	});
	
	var state = $("#selState").val();
	$("#state option[value='" + state + "']").attr("selected", true);
	
	var startTime = $("#selStartTime").val();
	var endTime = $("#selEndTime").val();
	if(startTime != "" && endTime != "")
	{
		$("#startTime").val(startTime);
		$("#endTime").val(endTime);
	}
});

function qryNewsList()
{
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var newsType = $("#newsType").val();
	var typeId = $("#typeId").val();
	var state = $("#state").val();
	window.location.href = "/news.htm?method=qryNewsList&startTime="+startTime+"&endTime="+endTime+"&newsType="+newsType+"&typeId="+typeId+"&state="+state;
}