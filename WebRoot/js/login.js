function commit() 
{
	var username = $("#username");
	var userpass = $("#userpass");
	username.val($.trim(username.val()));
	userpass.val($.trim(userpass.val()));
	if (username.val() == "") 
	{
		$.dialog.alert("用户名不能为空", function(){username.focus(); return true;});
		return false;
	} 
	else if (userpass.val() == "") 
	{
		$.dialog.alert("密码不能为空", function(){userpass.focus(); return true;});
		return false;
	} 
	else 
	{
		return true;
	}
}