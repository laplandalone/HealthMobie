function qryOnlineDortorQues()
{
	var obj = JSON.stringify($("select,input").serializeObject());
	obj = qryStartFunc(obj);
	var dig = null;
	$.ajax({
		type : "POST",
		url : "/ques.htm?method=queryPre",
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

function createTable(data, flagParam)
{
	var type = $("#type").val();
	var content = "<table width='100%' border='1' cellspacing='0' cellpadding='0' class='maintable1'><tr class='tabletop'>";
	if("online" == type)
	{
		content += "<td align='center' width='15%'>医生姓名</td><td align='center' width='20%'>医生职称</td><td align='center' width='20%'>医生科室</td>";
		content += "<td align='center' width='10%'>提问总数</td><td align='center' width='10%'>回复数</td><td align='center' width='10%'>未回复数</td>";
	}
	else if("question" == type)
	{
		content += "<td align='center' width='8%'>用户</td><td align='center' width='20%'>用户问题</td><td align='center' width='20%'>用户图片</td>";
		content += "<td align='center' width='8%'>可见范围</td><td align='center' width='10%'>创建时间</td>";
		content += "<td align='center' width='8%'>所有内容</td><td align='center' width='10%'>操作</td>";
	}
	content += "</tr>";
	if(data.count > 0)
	{
		var cnt = 0;
		if(flagParam == 0)
		{
			cnt = qryMiddleFunc(data.count);
		}
		if("online" == type)
		{
			$.each(data.onLineDoctorQuesList, function(i, obj){
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
				var totalQuesNum = obj.totalQuesNum;
				var totalReplyNum = obj.totalReplyNum;
				var num = totalQuesNum - totalReplyNum;
				content += "<td align='center' width='15%'>"+obj.name+"</td><td align='center' width='20%'>"+post+"</td><td align='center' width='20%'>"+obj.teamName+"</td>";
				content += "<td align='center' width='10%'>"+totalQuesNum+"</td><td align='center' width='10%'>"+totalReplyNum+"</td><td align='center' width='10%'>"+num+"</td></tr>";
			});
		}
		else if("question" == type)
		{
			$.each(data.questionList, function(i, obj){
				if(i % 2)
				{
					content += "<tr class='bkf0' onmouseover='trColorChange(this,"+i+")' onmouseout='trColorChange(this,"+i+")'>";
				}
				else
				{
					content += "<tr class='aaa' onmouseover='trColorChange(this,"+i+")' onmouseout='trColorChange(this,"+i+")'>";
				}
				content += "<td align='center' width='8%'>"+obj.userTelephone+"</td>";
				content += "<td align='center' width='20%'><div id='"+obj.questionId+"content'>"+obj.content+"</div></td>";
				content += "<td align='center' width='20%'>";
				if(obj.imgUrl0 != null && obj.imgUrl0 != "")
				{
					content += "<a href='"+obj.imgUrl0+"' target='_blank'><img src='"+obj.imgUrl0+"' width='60' height='60' /></a>";
				}
				if(obj.imgUrl1 != null && obj.imgUrl1 != "")
				{
					content += "<a href='"+obj.imgUrl1+"' target='_blank'><img src='"+obj.imgUrl1+"' width='60' height='60' /></a>";
				}
				if(obj.imgUrl2 != null && obj.imgUrl2 != "")
				{
					content += "<a href='"+obj.imgUrl2+"' target='_blank'><img src='"+obj.imgUrl2+"' width='60' height='60' /></a>";
				}
				content += "</td><td align='center' width='8%'>";
				var authType = obj.authType;
				if(authType == "private")
				{
					content += "<input type='button' class='button1' value='不可见' onclick='updateAuth(\""+authType+"\", "+obj.id+")'/>";
				}
				else
				{
					content += "<input type='button' class='button3' value='可见' onclick='updateAuth(\""+authType+"\", "+obj.id+")'/>";
				}
				content += "</td><td align='center' width='15%'>"+obj.createDate+"</td>";
				content += "<td align='center' width='8%' style='text-align:center !important'>";
				content += "<a href='javascript:void(0)' class='linkmore' onclick='viewContent(\""+obj.doctorId+"\", "+obj.questionId+")'>查看</a></td>";
				content += "<td align='center' width='10%' style='text-align:center !important'>";
				content += "<a href='javascript:void(0)' class='linkmore' onclick='verifyFunc(\""+obj.doctorId+"\","+obj.questionId+","+obj.userId+",\""+obj.userTelephone+"\",\""+obj.questionId+"content\")'>回复</a></td>";
			});
		}
		if(flagParam == 0)
		{
			qryEndFunc(cnt);
		}
	}
	else
	{
		var num = 6;
		if("question" == type)
		{
			num = 7;
		}
		content += "<tr><td colspan='"+num+"' style='text-align:center'>没有查询到相应的记录!</td></tr>";
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
		url : "/ques.htm?method=queryPre",
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

var demoDG1 = null;
function verifyFunc(doctorId, questionId, userId, telephone, id)
{
	var val = $("#"+id).html();
	val = encodeURI(val);
	lockScreen();
	demoDG1 = $.dialog({
		title:"操作", 
		content:"url:/view/question/replyQuestion.jsp?telephone="+telephone+"&question="+val+"&doctorId="+doctorId+"&questionId="+questionId+"&userId="+userId, 
		min:false, 
		max:false, 
		lock:true, 
		close:function(){unlockScreen();},
		ok: function()
		{
			var replyContent = demoDG1.content.document.getElementById("replyContent").value;
			if($.trim(replyContent) == "")
			{
				$.dialog.alert("回复内容为空", function(){return true;});
			    return false;
			}
			else
			{
				var authType = "";
				var array = demoDG1.content.document.getElementsByName("authType");
				for(var i = 0, len = array.length; i < len; i++)
				{
					if(array[i].checked) 
					{
						authType = array[i].value;
					}
				}
				if(authType == "")
				{
					$.dialog.alert("请选择可见范围", function(){return true;});
			        return false;
				}
				else
				{
					refundAjax(doctorId, questionId, userId, telephone, replyContent, authType);
				}
			}
		},
		cancel: true
	});
}

function refundAjax(doctorId, questionId, userId, telephone, content, authType)
{
	$.ajax({
		url:"/ques.htm?method=updateQues",
		type:"POST",
		data:"doctorId="+doctorId+"&questionId="+questionId+"&userId="+userId+"&telephone="+telephone+"&authType="+authType+"&content="+content,
		success:function(data)
		{
			$.dialog.alert("操作成功",function(){return true;});
		},
		error:function(stata)
		{
			$.dialog.alert(stata.statusText, function(){return true;});
		}
	});
	unlockScreen();
}

function viewContent(doctorId, questionId)
{
	lockScreen();
	$.dialog({id:"viewDemo", width:"450px", esc:false, title:"所有内容", content:"url:/ques.htm?method=qryQuesList&doctorId="+doctorId+"&questionId="+questionId, min:false, max:false, lock:true, close:function(){unlockScreen();}});
}

function updateAuth(authType, id)
{
	$.dialog.confirm("确定修改可见范围?",
		function(){
			$.ajax({
				type:"POST",
				url:"/ques.htm?method=updateAuth",
				data:"authType="+authType+"&id="+id,
				success:function(data)
				{
					if(data)
					{
						qryOnlineDortorQues();
					}
				}
			});
		},
		function(){return true;}
	);
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
