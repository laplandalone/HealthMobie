
function submitClick()
{
	var formit = $("#formit");
	var checkname = getObjValueByName("bankId");
	if(checkname=="")
	{
		alert("您还没有选择您要支付的银行！");
	}
	else
	{
		formit.submit();
	}
}

//function submitFunc()
//{
//	var checkname = getObjValueByName("bankId");
//	if(checkname=="")
//	{
//		alert("您还没有选择您要支付的银行！");
//		return false;
//	}
//	else
//	{
//		var submitBtn = document.getElementById("submitBtn");
//		submitBtn.style.backgroundImage="url(/pub/img/bd.gif)";
//		submitBtn.disabled = true;
//		art.dialog({
//			content:"订单提交成功",
//			icon: 'succeed',
//			title:"消息",
//			lock:true,
//			button:[
//				{
//					name:"支付完成",
//					focus: true,
//					callback:function(){
//						window.close();
//					}
//				},
//				{
//					name:"未完成支付",
//					callback:function(){
//						window.history.go(-1);
//					}
//				}
//			]
//		});
//		window.open('about:blank',"payBank");
//		return true;
//	}
//}

function chaCard(obj)
{
	var credit = $("#credit");
	var deposit = $("#deposit");
	if(credit.attr("class") == "linkSel")
	{
		credit.attr("class","linkAnc");
		deposit.attr("class","linkSel");
	}
	else
	{
		credit.attr("class","linkSel");
		deposit.attr("class","linkAnc");
	}
}

/**
 * @author : tiankang
 * @param name
 * @return
 * 获取Radio或checkbox的值
 */
function getObjValueByName(name)
{
	var obj = document.getElementsByName(name)
	if(obj.length != 0)
	{
		if(obj[0].checked != null)
		{
			return getRadValueByName(obj);
		}
		else if(obj[0].selected != null)
		{
			return getSelValueByName(obj);
		}
		else
		{
			return obj[0].value;
		}
	}
	else
	{
		return false;
	}
}

/**
 * @author : tiankang 
 * @param obj
 * @return
 */
function getRadValueByName(obj)
{
	for(var i = 0;i < obj.length;i++)
	{
		if(obj[i].checked == true)
		{
			return obj[i].value;
		}
	}
	return false;
}

/**
 * @author : tiankang
 * @param obj
 * @return
 */
function getSelValueByName(obj)
{
	for(var i = 0;i < obj.length;i++)
	{
		if(obj[i].selected == true)
		{
			return obj[i].value;
		}
	}
	return false;
}