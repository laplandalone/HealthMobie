$(document).ready(function(){
	var hospitalId = $("#hospitalId").val();
	//科室发生改变
	$("#teamId").change(function(){
		var teamId = $("#teamId").val();
		$.getJSON("/mobile.htm?method=qryDoctorList",{"teamId":teamId, "hospitalId":hospitalId},function(data){ 
			if(data.length > 0)
			{
				var obj = document.getElementById("doctorId");
				for(var j = obj.options.length - 1; j > 0; j--)
				{
					obj.options.remove(j);
				}
				for(var k = 0 ; k < data.length ; k++)
				{  
					var varItem = new Option(data[k].name,data[k].doctorId);
					obj.options.add(varItem);
				} 
			}
		});
	});
	
	var teamId = $("#selTeamId").val();
	$("#teamId option[value='" + teamId + "']").attr("selected", true);
	if(teamId != "" && teamId != null && teamId != undefined)
	{
		$.getJSON("/mobile.htm?method=qryDoctorList",{"teamId":teamId, "hospitalId":hospitalId},function(data){
			var options = "";
			var doctorId = $("#selDoctorId").val();
			if(doctorId == "")
			{
				options += "<option value='' selected='selected'>---请选择---</option>"; 
			}
			else
			{
				options += "<option value=''>---请选择---</option>"; 
			}
			if(data.length > 0)
			{
				for(var i = 0; i < data.length; i++)
				{ 
					if(doctorId == data[i].doctorId)
					{
						options += "<option value='"+data[i].doctorId+"' selected='selected'>"+data[i].name+"</option>"; 
					}
					else
					{
						options += "<option value='"+data[i].doctorId+"'>"+data[i].name+"</option>"; 
					}
				}
			}
			$("#doctorId").html(options);
		});
	}
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

function qryRegisterOrder()
{
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var teamId = $("#teamId").val();
	var doctorId = $("#doctorId").val();
	var state = $("#state").val();
	window.location.href = "/mobile.htm?method=qryRegisterOrder&state="+state+"&teamId="+teamId+"&doctorId="+doctorId+"&startTime="+startTime+"&endTime="+endTime;
}

//预约挂号
function appointment(name, orderId)
{
	$.dialog.confirm("是否预约"+name+"医生进行看诊?",
		function ()
		{
			option(orderId, "appointment");
		},
		function(){return true;}
	);
}

//作废挂号
function invalid(name, orderId)
{
	$.dialog.confirm("是否作废"+name+"医生的挂号?",
		function ()
		{
			option(orderId, "invalid");
		},
		function(){return true;}
	);
}

function option(orderId, optionFlag)
{
	$.ajax({
		url : "/mobile.htm?method=appointmentOrinvalidOrder",
		type : "POST",
		data : "orderId="+orderId+"&optionFlag="+optionFlag,
		success:function(data)
		{
			$.dialog.alert('操作成功',function(){
				window.location.href = "/mobile.htm?method=qryRegisterOrder";
			});
		},
		error:function(stata)
		{
			alert(stata.statusText);
		}
	});
}