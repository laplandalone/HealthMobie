<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html>  
<head>  
<meta charset="utf-8">  
<title>上传文件</title>  
</head>  
<script type="text/javascript" src="<%=path%>/pub/js/jquery-1.9.1.min.js"></script>
<link href="<%=path%>/pub/css/sub.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>pub/dialog/lhgdialog.min.js?skin=idialog"></script>
<script type="text/javascript" src="<%=path%>/pub/js/calendar.js"></script>
<script type="text/javascript" src="<%=path%>/pub/js/date.js"></script>
<script type="text/javascript" src="<%=path%>/js/comm.js"></script>
<body>  
<form action="/mobile.htm?method=addNewsFile" method="post" enctype="multipart/form-data">  



<table width="50%" border="1" cellspacing="0" cellpadding="0" align='center'>
			
			<tr>
			<td align='center'>文章类型</td>
			<td>
			<select  name="newsTypeId">
			<option value="NEWS">患教中心</option><option value="BAIKE">健康百科</option>
			</select>
			</td>
		    </tr>
		    
			<tr>
			<td align='center'>标题类型</td>
			<td><select  name="newsTypeId"><option value="10015">种植牙</option></select></td>
		    </tr>
		    <tr>
		    <td align='center'>标题内容</td>
		    <td><input name="newsTitle"  class="subtext" type="text" /></td>
		    </tr>
		    
		    <tr>
		    <td align='center'>选择图片</td>
		    <td><input type="file"  class="subtext2" name="image" /></td>
		    </tr>
		    
		    <tr>
		    <td align='center'>文章内容</td>
		    <td><input type="file"  class="subtext2" name="txt" /></td>
		    </tr>
		    
		</table>
		
	<table width="50%" cellspacing="0" cellpadding="0" align='center'
			>
			<br/>
			<tr align='center'>
			<td>
			<input type="submit"  style="background-image:url('/pub/images/btn1_r1_c2.png');width:80px;height:28px;border:none;cursor:pointer" value="提交" />
			</td>
			</tr>
			
		</table>



		
</form>  

</body>  
</html>  