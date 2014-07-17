var gUserObj = null

function addUser()
{
	window.location.href = "/mobile.htm?method=getUserById";
}

function delUserFunc()
{
	if(gUserObj == null)
	{
		alert("请选择您要删除的用户!");
	}
	else
	{
		$.ajax({
			url:"/mobile.htm?method=delUser",
			type:"post",
			data:"userId="+gUserObj.value,
			success:function(data){
				alert("用户删除成功!");
				window.location.href = "/mobile.htm?method=showUser";
			},
			failure:function(data){
				alert("用户删除失败!");
			}
		})
	}
}

function updateUser()
{
	if(gUserObj == null)
	{
		alert("请选择您要修改的用户!");
	}
	else
	{
		window.location.href = "/mobile.htm?method=getUserById&userId="+gUserObj.value;
	}
}

function chkClick(obj)
{
	if(gUserObj == null)
	{
		gUserObj = obj;
	}
	else
	{
		gUserObj.checked = false;
		gUserObj = obj;
	}
}