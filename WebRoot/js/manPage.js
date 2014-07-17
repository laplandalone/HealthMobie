function queryFunc()
{
	var merOrderId = $("#merOrderId");
	var platOrderFlow = $("#platOrderFlow");
	var platOrderId = $("#platOrderId");
	merOrderId.val($.trim(merOrderId.val()));
	platOrderFlow.val($.trim(platOrderFlow.val()));
	platOrderId.val($.trim(platOrderId.val()));
	if(merOrderId.val() == "" && platOrderFlow.val() == "" && platOrderId.val() == "")
	{
		alertDialog("请输入要查询的信息");
	}
	else
	{
		$("#queryOrder").submit();
	}
}

function refundClick()
{
	var tranOrderId = $("#tranOrderId").val();
	var bankCode = $("#bankCode").val();
	var outTradeNo = $("#outTradeNo").val();
	if(tranOrderId != "" && bankCode != "" && outTradeNo != "")
	{
		var dig = null;
		$.ajax({
			url : "/transpond/refund.do",
			type : "POST",
			data : "tranOrderId="+tranOrderId+"&bankCode="+bankCode+"&outTradeNo="+outTradeNo,
			beforeSend : function(){
				dig = art.dialog({
				    title : '正在退款请稍候....',
				    lock : true
				});
			},
			success : function(result){
				try{
					dig.close();
				}catch(e)
				{
					
				}
				if(result == "success")
				{
					art.dialog({
					    lock: true,
					    content: "退款申请已提交!",
					    icon: 'warning',
					    ok: function () {
							window.location.href = "/platform/login.do";
					        return true;
					    },
					});	
				}
				else
				{
					art.dialog({
					    lock: true,
					    content: "退款失败("+result+")，如有疑问请联系平台管理人员",
					    icon: 'warning',
					    ok: function () {
							window.location.href = "/platform/login.do";
					        return true;
					    },
					});	
				}
			},
			failure : function(result){
				try{
					dig.close();
				}catch(e)
				{
					
				}
				alertDialog(result);
			}
		})	
	}
	else
	{
		alertDialog("订单信息有误，请联系平台管理人员");
	}
}