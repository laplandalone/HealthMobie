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