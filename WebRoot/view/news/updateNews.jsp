<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  	<head>
    	<title></title>
    	<link href="<%=path%>/pub/css/sub.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/pub/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="<%=path%>pub/dialog/lhgdialog.min.js?skin=idialog"></script>
		<script type="text/javascript" src="<%=path%>/pub/js/calendar.js"></script>
		<script type="text/javascript" src="<%=path%>/pub/js/date.js"></script>
		<script type="text/javascript" src="<%=path%>/js/comm.js"></script>
		<script type="text/javascript" src="<%=path%>/js/news.js"></script>
		<style type="text/css">
			.ifile {  
				position: absolute;  
				height: 25px;  
				opacity: 0;  
				filter: alpha(opacity = 0);  
				-moz-opacity: 0;  
				width: 360px;  
				margin-top: 4px;  
			}
		</style>
		<script type="text/javascript">
			$(document).ready(function(){
				var newsType = $("#newsType").val();
				$.getJSON("/news.htm?method=qryNewsTypeList", {"newsType":newsType}, function(data){
					var options = "";
					var typeId = $("#selTypeId").val();
					if(data.length > 0)
					{
						for(var i = 0; i < data.length; i++)
						{
							if(typeId == data[i].configId)
							{
								options += "<option value='"+data[i].configId+"' selected='selected'>"+data[i].configVal+"</option>"; 
							}
							else
							{
								options += "<option value='"+data[i].configId+"'>"+data[i].configVal+"</option>"; 
							}
						}
					}
					$("#typeId").html(options);
				});
			});
		</script>
  	</head>
  
  	<body>
    	<form action="">
    		<input type="hidden" id="selTypeId" value="${news.typeId }"/>
    		<table width="600px" border="0" cellspacing="10" cellpadding="0" align='center'>
    			<tr>
    				<td width="12%" align="right">文章类型</td>
    				<td width="30%">
    					<select id="newsType" class="subselect" style="width: 150px">
    						<c:if test="${news.newsType == 'NEWS' }">
	    						<option value="NEWS" selected="selected">患教中心</option>
								<option value="BAIKE">健康百科</option>
    						</c:if>
    						<c:if test="${news.newsType == 'BAIKE' }">
    							<option value="NEWS">患教中心</option>
								<option value="BAIKE" selected="selected">健康百科</option>
    						</c:if>
    					</select>
    				</td>
    				<td width="12%" align="right">标题类型</td>
					<td width="30%">
						<select id="typeId" class="subselect" style="width: 150px">
							
						</select>
					</td>
    			</tr>
    			<tr>
    				<td width="12%" align="right">消息标题</td>
    				<td colspan="3">
    					<input id="newsTitle" class="subtext" type="text" style="width: 86%; height: 20px;" value="${news.newsTitle }"/>
    				</td>
    			</tr>
    			<tr>
    				<td width="12%" align="right">生效时间</td>
    				<td width="30%">
						<table class="inputtable" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="text" id="effDate" name="effDate" style="border:0;height:20px;width:130px;font-size:12px" readonly value="${news.effDate }"/>
								</td>
								<td>
									<a href="javascript:void(0);" onclick="showDate(document.getElementById('effDate'))"> 
										<img src="<%=path%>/pub/images/calendar.png" style="position:relative;top:0px" /> 
									</a>
								</td>
							</tr>
						</table>
					</td>
					<td width="12%" align="right">失效时间</td>
    				<td width="30%">
						<table class="inputtable" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="text" id="expDate" name="expDate" style="border:0;height:20px;width:130px;font-size:12px" readonly value="${news.expDate }"/>
								</td>
								<td>
									<a href="javascript:void(0);" onclick="showDate(document.getElementById('expDate'))"> 
										<img src="<%=path%>/pub/images/calendar.png" style="position:relative;top:0px" /> 
									</a>
								</td>
							</tr>
						</table>
					</td>
    			</tr>
    			<tr>
    				<td width="12%" align="right">文章内容</td>
					<td colspan="3">
						<textarea rows="10" cols="77" style="border:1px solid #C0DC56;background:#fff;margin-bottom: 10px">${news.content }</textarea>
						</br>
						<input type="file" id="newsContent" size="80" class="ifile" onchange="newsContentFileName.value=this.value; "/>
						<input name="newsContentFileName" type="text" class="subtext2" id="txtfilename" size="80" readonly style="height: 20px;" />  
						<img src="<%=path %>/pub/images/document_small_upload.png" width="20px" height="20px" align="absmiddle" onclick="newsContent.click();" style="z-index: 999;" />  
					</td>
    			</tr>
    			<tr>
    				<td width="12%" align="right">标题配图</td>
    				<td colspan="3">
    					<c:choose>
    						<c:when test="${news.newsImages != ''}">
    							<a href="${news.newsImages }" target="_blank" style="margin-bottom: 10px;">
    								<img src="${news.newsImages }" width="60" height="60" onclick="" style="margin-bottom: 10px;">
    							</a>
    							</br>
    						</c:when>
    					</c:choose>
						<input type="file" class="ifile" id="newsImage" size="80" onchange="newsImageFileName.value=this.value; "/>
						<input name="newsImageFileName" type="text" class="subtext2" id="txtfilename" size="80" readonly style="height: 20px;" />  
						<img src="<%=path %>/pub/images/document_small_upload.png" width="20px" height="20px" align="absmiddle" onclick="newsImage.click();" style="z-index: 999;" />  
					</td>
    			</tr>
    		</table>
    	</form>
  	</body>
</html>
