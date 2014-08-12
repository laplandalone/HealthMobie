<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Insert title here</title>
		<link href="<%=path%>/pub/css/sub.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="/pub/css/bankList.css" type="text/css"></link>
	  	<link rel="stylesheet" href="/pub/css/manPage.css" type="text/css"></link>
		<script type="text/javascript" src="<%=path%>/pub/js/jquery-1.9.1.min.js"></script>
	  	<script type="text/javascript" src="<%=path%>/pub/dialog/lhgdialog.min.js?skin=idialog"></script>
	  	<script language="javascript">
	  		function enterClick()
	  		{
	  			var storename = document.getElementById("storename");
	  			var remark = $("#remark");
	  			if(storename != null && $.trim(storename.value) == "")
	  			{
	  				$.dialog.alert("药店名称不能为空!", function(){return true;});
	  				storename.focus();
	  			}
	  			else
	  			{
	  				$("#form").submit();
	  			}
	  		}
	  		
	  		function cancelClick()
	  		{
	  			window.location.href = "/mobile.htm?method=showStore";
	  		}
	  	</script>
	</head>
	<body>
		<form action="/mobile.htm?method=storeOper" method="post" id="form">
			<input type="hidden" name="storeId" value="${storeId}" />
			<div id="template" style="height:500px;overflow:auto">
				<table align="center" width="400px" border="0" cellspacing="10" cellpadding="0" style="margin-top:20px">
					<tr>
						<td align="right">药店名称:</td>
						<td>
							<c:choose>
								<c:when test="${flag == 1}">
									<input type="text" name="storename" id="storename" value="" style="width:200px" />
								</c:when>
								<c:otherwise>
									${storename}
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td align="right">备注信息:</td>
						<td>
							<textarea style="width:200px;height:100px" name="remark" id="remark">${remark}</textarea>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input class="button3" onclick="enterClick()" type="button" value="确定"/>&nbsp;&nbsp;
							<input class="button1" onclick="cancelClick()" type="button" value="取消"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>