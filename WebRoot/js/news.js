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
	
	var typeId = $("#selTypeId").val();
	$.getJSON("/news.htm?method=qryNewsTypeList", {"newsType":newsType}, function(data){
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

function qryNewsList()
{
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var newsType = $("#newsType").val();
	var typeId = $("#typeId").val();
	var state = $("#state").val();
	window.location.href = "/news.htm?method=qryNewsList&startTime="+startTime+"&endTime="+endTime+"&newsType="+newsType+"&typeId="+typeId+"&state="+state;
}

var demoDG1 = null;

function updateNews(newsId)
{
	lockScreen();
	demoDG1 = $.dialog({
		title:"修改",
		content:"url:/news.htm?method=getNewsById&newsId="+newsId,
		min:false, 
		max:false, 
		lock:true, 
		close:function(){unlockScreen();},
		ok: function()
		{
			
		},
		cancel: true
	});
}

function addNews()
{
	lockScreen();
	demoDG1 = $.dialog({
		title:"信息发布",
		content:"url:/view/news/addNews.jsp",
		min:false, 
		max:false, 
		lock:true, 
		close:function(){unlockScreen();}
//		ok: function()
//		{
//			var newsTitle = demoDG1.content.document.getElementById("newsTitle").value;
//			if($.trim(newsTitle) == "")
//			{
//				$.dialog.alert("消息标题为空", function(){return true;});
//				return false;
//			}
//			else
//			{
//				var effDate = demoDG1.content.document.getElementById("effDate").value;
//				if(effDate == "" || effDate == null || effDate ==  undefined)
//				{
//					$.dialog.alert("生效时间为空", function(){return true;});
//					return false;
//				}
//				else
//				{
//					var expDate = demoDG1.content.document.getElementById("expDate").value;
//					if(expDate == "" || expDate == null || expDate ==  undefined)
//					{
//						$.dialog.alert("生效时间为空", function(){return true;});
//						return false;
//					}
//					else
//					{
//						var newsContent = demoDG1.content.document.getElementById("newsContent").value;
//						if($.trim(newsContent) == "")
//						{
//							$.dialog.alert("文章内容为空", function(){return true;});
//							return false;
//						}
//						else
//						{
//							var newsImage = demoDG1.content.document.getElementById("newsImage").value;
//							var type = newsImage.substring(newsImage.lastIndexOf(".") + 1, newsImage.length).toLowerCase();
//							if(type != "jpg" && type != "bmp" && type != "gif" && type != "png")
//							{
//								$.dialog.alert("请上传正确的图片格式", function(){return true;});
//								return false;
//							}
//							else
//							{
//								demoDG1.content.submitClick();
//								return false;
//							}
//						}
//					}
//				}
//			}
//		},
//		cancel: true
	});
}

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
				$.dialog.alert("分类名称为空", function(){return true;});
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
		},
		cancel: true
	});
}