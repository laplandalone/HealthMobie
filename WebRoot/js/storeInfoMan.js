var gStoreObj = null

function addStore() 
{
	window.location.href = "/mobile.htm?method=getStoreById";
}

function delStoreFunc() 
{
	if (gStoreObj == null) 
	{
		$.dialog.alert("请选择您要删除的药店!", function(){return true;});
	}
	else 
	{
		$.ajax({
			url : "/mobile.htm?method=delStore",
			type : "post",
			data : "storeId=" + gStoreObj.value,
			success : function(data) 
			{
				$.dialog.alert("药店删除成功!", function(){window.location.href = "/mobile.htm?method=showStore"; return true;});
			},
			failure : function(data) 
			{
				$.dialog.alert("药店删除失败!", function(){return true;});
			}
		})
	}
}

function updateStore() 
{
	if (gStoreObj == null) 
	{
		$.dialog.alert("请选择您要修改的药店!", function(){return true;});
	}
	else 
	{
		window.location.href = "/mobile.htm?method=getStoreById&storeId=" + gStoreObj.value;
	}
}

function chkClick(obj) 
{
	if (gStoreObj == null) 
	{
		gStoreObj = obj;
	} 
	else 
	{
		gStoreObj.checked = false;
		gStoreObj = obj;
	}
}