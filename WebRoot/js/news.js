$(document).ready(function(){
	$("#newsType").change(function(){
		var newsType = $("#newsType").val();
		$.getJSON("/news.htm?method=qryNewsTypeList", {"newsType":newsType, "random":Math.random()}, function(data){ 
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
	
	var typeId = $("#selTypeId").val();
	$.getJSON("/news.htm?method=qryNewsTypeList", {"newsType":newsType, "random":Math.random()}, function(data){
		var options = "";
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
		$("#typeId").html(options);
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

function reload()
{
	window.location.reload();
}

function qryNewsList()
{
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var newsType = $("#newsType").val();
	var typeId = $("#typeId").val();
	var state = $("#state").val();
	window.location.href = "/news.htm?method=qryNewsList&startTime="+startTime+"&endTime="+endTime+"&newsType="+newsType+"&typeId="+typeId+"&state="+state;
}

function updateNews(newsId)
{
	lockScreen();
	$.dialog({title:"修改", content:"url:/news.htm?method=getNewsById&newsId="+newsId, min:false, max:false, lock:true, close:function(){unlockScreen();}});
}

function addNews()
{
	lockScreen();
	$.dialog({title:"信息发布", content:"url:/view/news/addNews.jsp", min:false, max:false, lock:true, close:function(){unlockScreen();}});
}

var demoDG1 = null;
function addNewsType()
{
	lockScreen();
	demoDG1 = $.dialog({
		title:"新增分类",
		content:"url:/view/news/addNewsType.jsp",
		min:false, 
		max:false, 
		lock:true, 
		close:function(){unlockScreen();},
		ok: function()
		{
			var typeName = demoDG1.content.document.getElementById("typeName").value;
			if($.trim(typeName) == "")
			{
				$.dialog.alert("分类名称为空!", function(){return true;});
			    return false;
			}
			else
			{
				if($.trim(typeName).length > 30)
				{
					$.dialog.alert("分类名称的长度请不要超过30个字!", function(){return true;});
				    return false;
				}
				else
				{
					var newsTypeId = demoDG1.content.document.getElementById("newsTypeId").value;
					$.ajax({
						type:"POST",
						url:"/news.htm?method=addNewsType",
						data:"newsTypeId="+newsTypeId+"&newsTypeName="+typeName,
						success:function(data)
						{
							if(data == "true")
							{
								$.dialog({title:false, width:"150px", esc:false, height:"60px", zIndex:2000, icon:'succ.png', lock:true, content:'成功新增分类信息!', ok:function() {return true;}});
							}
							else
							{
								$.dialog( { title:false, width:"150px", esc:false, height:"60px", zIndex:2000, icon:'fail.png', lock:true, content:'新增分类信息失败!', ok:function() {return true;}});
							}
						},
						error:function(stata)
						{
							$.dialog.alert(stata.statusText, function(){return true;});
						}
					});
				}
			}
		},
		cancel: true
	});
}