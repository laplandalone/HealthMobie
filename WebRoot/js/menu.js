function treeList(node,area)
{
	if($("#"+node).css("display") == "block")
	{
		$("#"+node).slideUp("1000",function(){
			$("#"+area).css("line-height","2.8").css("height","32px").css("margin-top","2px").css("margin-bottom","2px");
		});
	}
	else
	{
		$("#"+area).css("line-height","1.5").css("height","75px").css("margin-top","2px").css("margin-bottom","2px");
		$("#"+node).slideDown();
	}
}