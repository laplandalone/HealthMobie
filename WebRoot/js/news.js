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
});

function qryNewsList()
{
	var pageNum = $("#pageNum").val();
	if(pageNum != null && pageNum != "" && pageNum != undefined && pageNum != "null")
	{
		showList(pageNum);
	}
	else
	{
		var obj = JSON.stringify($("select,input").serializeObject());
		obj = qryStartFunc(obj);
		var dig = null;
		$.ajax({
			type : "POST",
			url : "/news.htm?method=qryNewsList",
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
}

function createTable(data, flagParam)
{
	var content = "<table id='table1' width='100%' border='1' cellspacing='0' cellpadding='0' class='maintable'>";
	content += "<tr class='tabletop'><td width='5%'>编号</td><td width='10%'>医院名称</td><td width='6%'>所属板块</td>";
	content += "<td width='8%'>分类</td><td width='20%'>信息标题</td><td width='6%'>发布状态</td><td width='10%'>创建时间</td><td width='5%'>操作</td></tr>";
	if(data.count > 0)
	{
		var cnt = 0;
		if(flagParam == 0)
		{
			cnt = qryMiddleFunc(data.count);
		}
		$.each(data.newsList, function(i, obj){
			if(i % 2)
			{
				content += "<tr class='bkf0' onmouseover='trColorChange(this,"+i+")' onmouseout='trColorChange(this,"+i+")'>";
			}
			else
			{
				content += "<tr class='aaa' onmouseover='trColorChange(this,"+i+")' onmouseout='trColorChange(this,"+i+")'>";
			}
			content += "<td style='text-align:center'>"+obj.newsId+"</td><td style='text-align:center'>"+obj.hospitalName+"</td><td style='text-align:center'>"+obj.newsType+"</td><td style='text-align:center'>"+obj.typeId+"</td>";
			content += "<td style='text-align:center'>"+obj.newsTitle+"</td><td style='text-align:center'>"+obj.stateVal+"</td><td style='text-align:center'>"+obj.createDate+"</td>";
			content += "<td style='text-align:center !important'><a href='javascript:void(0)' class='linkmore' onclick='updateNews("+obj.newsId+")'>修改</a>";
			content += "&nbsp;&nbsp;&nbsp;&nbsp;";
			content += "<a href='javascript:void(0)' class='linkmore' onclick='deleteNews("+obj.newsId+")'>删除</a>";
			content += "</td></tr>";
		});
		if(flagParam == 0)
		{
			qryEndFunc(cnt);
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

function reload(pageNum) 
{
	$("#pageNum").val(pageNum);
	qryNewsList();
}

function qryPaging(val)
{
	var obj = qryPaingStartFunc(val);
	var dig = null;
	$.ajax({
		type : "POST",
		url : "/news.htm?method=qryNewsList",
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

function updateNews(newsId)
{ 
	lockScreen();
	$.dialog({title:"修改", content:"url:/news.htm?method=getNewsById&newsId="+newsId+"&pageNum="+gcurTag, min:false, max:false, lock:true, close:function(){unlockScreen();}});
}

function deleteNews(newsId)
{
	$.dialog.confirm("是否删除该信息 ?", function() {
		$.ajax({
			type : "POST",
			url : "/news.htm?method=deleteNews",
			data : "newsId=" + newsId,
			success : function(data) 
			{
				if (data) 
				{
					$.dialog({title : false, width : "150px", esc : false, height : "60px", zIndex : 2000, icon : "succ.png", lock : true, content : "删除信息成功!", ok : function(){window.location.reload(); return true;}});
				} 
				else
				{
					$.dialog({title : false, width : "150px", esc : false, height : "60px", zIndex : 2000, icon : "fail.png", lock : true, content : "删除信息失败!", ok : function() {window.location.reload(); return true;}});
				}
			},
			error : function(data) {$.dialog.alert(data.statusText, function() {return true;});
			}
		});
	}, function() {
		return true;
	});
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
								$.dialog({title:false, width:"150px", esc:false, height:"60px", zIndex:2000, icon:'fail.png', lock:true, content:'新增分类信息失败!', ok:function() {return true;}});
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