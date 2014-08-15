<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title></title>
		<link href="<%=path%>/pub/css/sub.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/pub/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="<%=path%>/pub/dialog/lhgdialog.min.js?skin=idialog"></script>
		<script type="text/javascript" src="<%=path%>/pub/js/calendar.js"></script>
		<script type="text/javascript" src="<%=path%>/pub/js/date.js"></script>
		<script type="text/javascript" src="<%=path%>/js/comm.js"></script>
		<script type="text/javascript">
			var api = frameElement.api, W = api.opener;
			
			function edit(id)
			{
				document.getElementById("content" + id).style.border = "1px solid #d2d2d2";
				$("#editBtn" + id).css("display", "none");
				$("#submitBtn" + id).css("display", "block");
			}
			
			function renew(id)
			{
				document.getElementById("content" + id).style.border = "0";
				$("#editBtn" + id).css("display", "block");
				$("#submitBtn" + id).css("display", "none");
			}
			
			function updateAns(id, oldContent)
			{
				var content = $("#content" + id).val();
				if($.trim(content) == "")
				{
					W.$.dialog.alert("回复内容为空", function(){$("#content" + id).focus(); return true;}, api);
					return false;
				}
				else
				{
					$.ajax({
						type:"POST",
						url:"/ques.htm?method=updateAns",
						data:"id="+id+"&content="+content,
						success:function(data)
						{
							if(data)
							{
								$.dialog({parent:this, title:false, width:"160px", esc:false, height:"60px", zIndex:2000, icon:"succ.png", lock:true, content:"成功修改回复信息!", ok:function() {renew(id); api.reload(api.get("viewDemo"));}});
							}
							else
							{
								$.dialog({parent:this, title:false, width:"160px", esc:false, height:"60px", zIndex:2000, icon:"fail.png", lock:true, content:"修改回复信息失败!", ok:function() {$("#content" + id).val(oldContent); renew(id); api.reload(api.get("viewDemo"));}});
							}
						},
						error:function(stata)
	    				{
	    					$.dialog.alert(stata.statusText, function(){return true;}, this);
	    				}
					});
				}
			}
		</script>
	</head>

	<body>
		<div style="height: 596px; overflow-y: auto;">
			<table border='0' cellspacing='10' width='100%' style='font-family: 微软雅黑; font-size: 14px;'>
				<c:forEach items="${quesList }" var="ques">
					<tr>
						<td style='word-break:break-all;'>
							<fieldset style='width:380px; margin-top: 4px auto; margin-bottom: 10px; text-align: left;'>
								<legend align='left'><font color='gray'></font></legend>
								<c:if test="${ques.recordType == 'ans' }">
									<div align='right' id='editBtn${ques.id }' style='margin-top: 5px;margin-right: 5px'><a href='javascript:void(0)' class='linkmore' onclick="edit(${ques.id })">编辑</a></div>
									<div align='right' id='submitBtn${ques.id }' style='margin-top: 5px;margin-right: 5px;display: none;'><a href='javascript:void(0)' class='linkmore' onclick="updateAns(${ques.id }, '${ques.content }')">提交</a></div>
									<img src='/images/ans.png' height='25' width='30' style='vertical-align: top;margin-top: 5px'/>&nbsp;&nbsp;<textarea id='content${ques.id }' rows='4' cols='51' style='border: 0;height:50px;overflow:auto;margin-top: 5px'>${ques.content }</textarea>
								</c:if>
								<c:if test="${ques.recordType == 'ask' || ques.recordType == 'copy' }">
									<img src='/images/ask.png' height='25' width='30' style='vertical-align: top;margin-top: 5px'/>&nbsp;&nbsp;<input style="margin-top: 5px;border-style: none;" value="${ques.content }"/>
								</c:if>
								<div align='right' style='margin-bottom: 5xp;margin-right: 5px;margin-top: 5px'>
									${ques.createDate }
								</div>
							</fieldset>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
