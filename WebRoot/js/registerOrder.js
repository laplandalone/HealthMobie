function qryRegisterOrder()
{
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var hospitalName = $("#hospitalName").val();
	var teamName = $("#teamName").val();
	var doctorName = $("#doctorName").val();
	window.location.href = "/mobile.htm?method=qryRegisterOrder&hospitalName="+hospitalName+"&teamName="+teamName+"&doctorName="+doctorName+"&startTime="+startTime+"&endTime="+endTime;
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