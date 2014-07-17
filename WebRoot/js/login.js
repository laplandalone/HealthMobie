function commit()
{
	var username = $("#username");
	var userpass= $("#userpass");
	username.val($.trim(username.val()));
	userpass.val($.trim(userpass.val()));
	if(username.val() == "")
	{
		alertDialog("用户名不能为空");
		username.focus();
		return false;
	}
	else if(userpass.val() == "")
	{
		alertDialog("密码不能为空");
		userpass.focus();
		return false;
	}
	else
	{
		return true;
	}
}