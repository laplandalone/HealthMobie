<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<meta charset="utf-8">
		<title>上传文件</title>
		<link href="<%=path%>/pub/css/sub.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/pub/css/uploadify.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/pub/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="<%=path%>/pub/dialog/lhgdialog.min.js?skin=idialog"></script>
		<script type="text/javascript" src="<%=path%>/pub/js/jquery.uploadify-3.1.js"></script>
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
			var api = frameElement.api, W = api.opener;
			$(document).ready(function(){
				var newsType = $("#newsType").val();
				$.getJSON("/news.htm?method=qryNewsTypeList", {"newsType":newsType}, function(data){
					var options = "";
					if(data.length > 0)
					{
						for(var i = 0; i < data.length; i++)
						{
							options += "<option value='"+data[i].configId+"'>"+data[i].configVal+"</option>"; 
						}
					}
					$("#typeId").html(options);
					
					
					
				});
			});
		</script>
	</head>
	<body>
		<form id="addNewsForm" action="/news.htm?method=addNews" method="post" enctype="multipart/form-data">
			<input type="hidden" id="creditValueType" value="1"/>
			<table width="600px" border="0" cellspacing="10" cellpadding="0" align='center'>
				<tr>
					<td width="12%" align="right">文章类型</td>
					<td width="30%">
						<select id="newsType" name="newsType" class="subselect" style="width: 150px">
							<option value="NEWS" selected="selected">患教中心</option>
							<option value="BAIKE">健康百科</option>
						</select>
					</td>
					<td width="12%" align="right">标题类型</td>
					<td width="30%">
						<select id="typeId" name="typeId" class="subselect" style="width: 150px">
							
						</select>
					</td>
				</tr>
				<tr>
					<td width="12%" align="right">消息标题</td>
    				<td colspan="3">
    					<input id="newsTitle" name="newsTitle" class="subtext" type="text" style="width: 86%; height: 20px;"/>
    				</td>
				</tr>
				<tr>
					<td width="12%" align="right">生效时间</td>
    				<td width="30%">
						<table class="inputtable" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="text" id="effDate" name="effDate" style="border:0;height:20px;width:130px;font-size:12px" readonly/>
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
									<input type="text" id="expDate" name="expDate" style="border:0;height:20px;width:130px;font-size:12px" readonly/>
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
						<textarea id="newsContent" name="newsContent" rows="10" cols="77" style="border:1px solid #C0DC56;background:#fff;margin-bottom: 10px"></textarea>
						</br>
						<!--  
						<input type="file" id="newsContent" size="80" class="ifile" onchange="newsContentFileName.value=this.value; "/>
						<input name="newsContentFileName" type="text" class="subtext2" id="txtfilename" size="80" readonly style="height: 20px;" />  
						<img src="<%=path %>/pub/images/document_small_upload.png" width="20px" height="20px" align="absmiddle" onclick="newsContent.click();" style="z-index: 999;" />
						-->  
					</td>
    			</tr>
    			<tr>
    				<td width="12%" align="right">标题配图</td>
    				<td colspan="3">
						<input type="file" class="ifile" id="newsImage" name="newsImage" size="80" onchange="newsImageFileName.value=this.value; "/>
						<input name="newsImageFileName" type="text" class="subtext2" id="newsImageFileName" size="80" readonly style="height: 20px;" />  
						<img src="<%=path %>/pub/images/document_small_upload.png" width="20px" height="20px" align="absmiddle" onclick="newsImage.click();" style="z-index: 999;" />  
					</td>
    			</tr>
			</table>
			<table width="50%" cellspacing="0" cellpadding="0" align='center'>
				<br />
				<tr align='center'>
					<td>
						<input type="submit" class="button3" value="提交" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
