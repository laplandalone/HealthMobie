var gUserObj = null

function addUser() 
{
	window.location.href = "/mobile.htm?method=getUserById";
}

function delUserFunc() 
{
	if (gUserObj == null) 
	{
		$.dialog.alert("请选择您要删除的用户!", function(){return true;});
	} 
	else 
	{
		$.ajax({
			url : "/mobile.htm?method=delUser",
			type : "post",
			data : "userId=" + gUserObj.value,
			success : function(data) 
			{
				$.dialog.alert("用户删除成功!", function(){window.location.href = "/mobile.htm?method=showUser"; return true;});
				
			},
			failure : function(data) 
			{
				$.dialog.alert("用户删除失败!", function(){return true;});
			}
		})
	}
}

function updateUser() 
{
	if (gUserObj == null) 
	{
		$.dialog.alert("请选择您要修改的用户!", function(){return true;});
	} 
	else 
	{
		window.location.href = "/mobile.htm?method=getUserById&userId=" + gUserObj.value;
	}
}

function chkClick(obj) 
{
	if (gUserObj == null) 
	{
		gUserObj = obj;
	} 
	else 
	{
		gUserObj.checked = false;
		gUserObj = obj;
	}
}