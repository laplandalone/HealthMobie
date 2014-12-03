<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  	<head>
    	<title></title>
    	<link href="<%=path%>/pub/css/sub.css" rel="stylesheet" type="text/css" />
    	<link href="<%=path%>/pub/css/uploadify.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/pub/css/upload.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/pub/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="<%=path%>/pub/dialog/lhgdialog.min.js?skin=idialog"></script>
		<script type="text/javascript" src="<%=path%>/pub/js/jquery.uploadify-3.1.js"></script>
		<script type="text/javascript" src="<%=path%>/pub/js/calendar.js"></script>
		<script type="text/javascript" src="<%=path%>/pub/js/date.js"></script>
		<script type="text/javascript" src="<%=path%>/js/comm.js"></script>
		<script type="text/javascript" src="<%=path%>/js/json.js"></script>
		<script type="text/javascript" src="<%=path%>/js/json2.js"></script>
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
				$("#newsType").change(function(){
					var newsType = $("#newsType").val();
					$.getJSON("/news.htm?method=qryNewsTypeList", {"newsType":newsType, "random":Math.random()}, function(data){ 
						var options = "";
						$("#typeId").html(options);
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
				
				$("#fileName").uploadify({ 
					"method" : "post",  //提交方式Post 或Get 
					"width" : 20,  //设置浏览按钮的宽度
		    		"height" : 20,  //设置浏览按钮的高度
					"swf" : "/pub/swf/uploadify.swf",  //uploadify.swf 文件的相对路径
					"uploader" : "/news.htm?method=uploadFile",  //后台处理程序的相对路径
					"buttonText" : "",  //浏览按钮的文本
					"buttonClass" : "btnsUpload",
					"buttonImage" : "/pub/images/document_small_upload.png",  //浏览按钮的图片的路径
					"auto" : false, //是否立即上传
					"multi" : false, //是否支持多文件上传
					"fileTypeExts" : "*.jpg;*.gif;*.png;*.bmp;*.jpeg;", //限制文件类型   
					"queueID" : "foo" ,  //文件队列的ID，该ID与存放文件队列的div的ID一致
					"cancelImage" : "/pub/images/uploadify-cancel.png",  //选择文件到文件队列中后的每一个文件上的关闭按钮图标
					"onUploadSuccess" : function(file, data, response)
					{
	    			 	var obj = JSON.parse(data);
	    			 	$("#newsId").val(obj.newsId);
	    			 	$("#newsImageUrl").val(obj.imageUrl);
	    		 	},
					"onQueueComplete" : function()
					{
	    		 		realUpdateNews();
					}
				});
			});
			
			function updateNews()
			{
				if(!checkPara())
		    	{
					return;
		    	}
		    	else
		    	{
		    		if($.trim($("#foo").html()) == "")
					{
						realUpdateNews();
					}
					else
					{
						$("#updateNewsBtn").attr("disabled", "disabled");
						$("#cancelBtn").attr("disabled", "disabled");
						
						var newsType = $("#newsType").val();
						$("#fileName").uploadify("settings" , "formData" ,{"newsType" : newsType});
						$('#fileName').uploadify("upload", "*");
					}
		    	}
			}
			
			function realUpdateNews()
			{
				var oldNewsId = $("#oldNewsId").val();
				var newsId = $("#newsId").val();
				var newsType = $("#newsType").val();
				var typeId = $("#typeId").val();
				var state = $("#state").val();
				var newsTitle = $("#newsTitle").val();
				var effDate = $("#effDate").val();
				var expDate = $("#expDate").val();
				var newsContent = $("#newsContent").val();
				var newsImageUrl = $("#newsImageUrl").val();
				if(newsImageUrl == "" || newsImageUrl == null || newsImageUrl == undefined)
				{
					newsImageUrl = $("#newsImages").val();
				}
				var dig = null;
				$.ajax({
					type:"POST",
					url:"/news.htm?method=updateNews",
					data:"newsId="+newsId+"&newsType="+newsType+"&typeId="+typeId+"&newsTitle="+newsTitle+"&effDate="+effDate+"&expDate="+expDate+"&newsContent="+newsContent+"&newsImageUrl="+newsImageUrl+"&state="+state+"&oldNewsId="+oldNewsId,
					beforeSend : function()
					{
						dig = new W.$.dialog({parent:api, title:"正在修改请等待...",esc:false,min:false,max:false,lock:true});
					},
					success:function(data)
					{
						try
						{
							dig.close();
						}
						catch(e)
						{
							
						}
						if(data)
						{
							W.$.dialog({parent:api, title:false, width:"150px", esc:false, height:"60px", zIndex:2000, icon:'succ.png', lock:true, content:'成功修改信息!', ok:function() {W.reload(); api.close(); return true;}});
						}
						else
						{
							W.$.dialog({parent:api, title:false, width:"150px", esc:false, height:"60px", zIndex:2000, icon:'fail.png', lock:true, content:'修改信息失败!', ok:function() {api.close(); return true;}});
						}
					},
					error:function(stata)
    				{
    					try
						{
							dig.close();
						}
						catch(e)
						{
							
						}
    					W.$.dialog.alert(stata.statusText, function(){api.close(); return true;}, api);
    				}
				});
			}
			
			function cancel()
			{
				if($("#fileName").val()!= null && $("#fileName").val()!= undefined && $("#fileName").val() != "")
		    	{
		    		$("#fileName").uploadify("cancel", "*");
		    	}
    			api.close();
			}
			
			function checkPara()
			{
				var newsTitle = $("#newsTitle").val();
				if($.trim(newsTitle) == "" || $.trim(newsTitle) == null || $.trim(newsTitle) == undefined)
				{
					W.$.dialog.alert("消息标题为空", function(){return true;}, api);
					return false;
				}
				var effDate = $("#effDate").val();
				if(effDate == "" || effDate == null || effDate ==  undefined)
				{
					W.$.dialog.alert("生效时间为空", function(){return true;}, api);
					return false;
				}
				var expDate = $("#expDate").val();
				if(expDate == "" || expDate == null || expDate ==  undefined)
				{
					W.$.dialog.alert("失效时间为空", function(){return true;}, api);
					return false;
				}
				var newsContent = $("#newsContent").val();
				if($.trim(newsContent) == "" || $.trim(newsContent) == null || $.trim(newsContent) ==  undefined)
				{
					W.$.dialog.alert("文章内容为空", function(){return true;}, api);
					return false;
				}
				return true;
			}
		</script>
  	</head>
  
  	<body>
    	<form action="">
    		<input type="hidden" id="newsId"/>
    		<input type="hidden" id="oldNewsId" value="${news.newsId }"/>
    		<input type="hidden" id="creditValueType" value="1"/>
    		<input type="hidden" id="newsImages" value="${news.newsImages }">
    		<table width="630px" border="0" cellspacing="10" cellpadding="0" align='center'>
    			<tr>
    				<td width="13%" align="right">文章类型</td>
    				<td width="12%">
    					<select id="newsType" class="subselect" style="width: 110px">
    						<c:if test="${news.newsType == 'NEWS' }">
	    						<option value="NEWS" selected="selected">患教中心</option>
								<option value="BAIKE">就医帮助</option>
    						</c:if>
    						<c:if test="${news.newsType == 'BAIKE' }">
    							<option value="NEWS">患教中心</option>
								<option value="BAIKE" selected="selected">就医帮助</option>
    						</c:if>
    					</select>
    				</td>
    				<td width="13%" align="right">标题类型</td>
					<td width="12%">
						<select id="typeId" class="subselect" style="width: 110px">
							<c:forEach items="${typeList }" var="type">
								<c:if test="${news.typeId == type.configId }">
									<option value="${type.configId }" selected="selected">${type.configVal }</option>
								</c:if>
								<c:if test="${news.typeId != type.configId }">
									<option value="${type.configId }">${type.configVal }</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
					<td width="8%">状态</td>
					<td width="13%">
						<select id="state" class="subselect" style="width: 110px">
							<c:if test="${news.state == '00A' }">
								<option value="00A" selected="selected">正常</option>
								<option value="00X">作废</option>
							</c:if>
							<c:if test="${news.state == '00X' }">
								<option value="00A">正常</option>
								<option value="00X" selected="selected">作废</option>
							</c:if>
						</select>
					</td>
    			</tr>
    			<tr>
    				<td width="12%" align="right">消息标题</td>
    				<td colspan="5">
    					<input id="newsTitle" class="subtext" type="text" style="width: 99%; height: 20px;" value="${news.newsTitle }"/>
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
					<td colspan="2">&nbsp;</td>
    			</tr>
    			<tr>
    				<td width="12%" align="right">文章内容</td>
					<td colspan="5">
						<textarea rows="10" cols="83" id="newsContent" style="border:1px solid #C0DC56;background:#fff;margin-bottom: 10px">${news.content }</textarea>
						</br>
						<!--  
						<input type="file" id="newsContent" size="80" class="ifile" onchange="newsContentFileName.value=this.value; "/>
						<input name="newsContentFileName" type="text" class="subtext2" id="txtfilename" size="80" readonly style="height: 20px;" />  
						<img src="<%=path %>/pub/images/document_small_upload.png" width="20px" height="20px" align="absmiddle" onclick="newsContent.click();" style="z-index: 999;" /> 
						--> 
					</td>
    			</tr>
    			<c:choose>
    				<c:when test="${news.newsImages != ''}">
    					<tr>
    						<td width="12%">文章配图</td>
    						<td colspan="5">
	    						<a href="${news.newsImages }" target="_blank">
	    							<img src="${news.newsImages }" width="60" height="60" onclick="" style="margin-bottom: 10px;">
	    						</a>
    						</td>
    					</tr>
    				</c:when>
    			</c:choose>
    			<tr>
    				<td width="12%" align="right">选择图片</td>
    				<td colspan="5" style="position:relative;">
    					<!--  
						<input type="file" class="ifile" id="newsImage" size="80" onchange="newsImageFileName.value=this.value; "/>
						<input name="newsImageFileName" type="text" class="subtext2" id="txtfilename" size="80" readonly style="height: 20px;" />  
						<img src="<%=path %>/pub/images/document_small_upload.png" width="20px" height="20px" align="absmiddle" onclick="newsImage.click();" style="z-index: 999;" /> 
						-->
						<input type="file" id="fileName" name="fileName" style="border:0;font-size:12px; margin-bottom: 20px" />
						<input type="hidden" id="newsImageUrl" name="newsImageUrl"/>
					</td>
    			</tr>
    			<tr>
    				<td width="12%"></td>
					<td colspan="5">
						<div id="foo"></div>
					</td>
				</tr>
				<tr>
					<td colspan="6">
						<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：您上传的图片必须符合图片大小为：300 × 299，或者相等比例</font>
					</td>
				</tr>
    		</table>
    		<table width="100%" cellspacing="0" cellpadding="0" align='center'>
				<tr align='center'>
					<td>
						<input type="button" id="updateNewsBtn" value="修改" class="button3" onclick="updateNews()"/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" id="cancelBtn" value="取消" class="button" onclick="cancel()"/>
					</td>
				</tr>
			</table>
    	</form>
  	</body>
</html>
