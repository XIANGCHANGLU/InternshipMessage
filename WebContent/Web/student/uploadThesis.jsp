<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>编辑信息</title>
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->

<link href="<%=basePath%>/resource/css/admin/bootstrap.min.css?v=3.3.6"
	rel="stylesheet">
<style type="text/css">
.upload {
	margin-top: 100px;
}
</style>

</head>
<body>
	<div class="container">
		<div class="upload">
		<p>*请各位准毕业生准时提交毕业论文，上传格式为：学号+名字+加毕业论文题目</p>
			<form action="UploadAction_execute.action" method="post"
				enctype="multipart/form-data" name="uploadFileForm" onsubmit="return CheckPost()">
				文件：<input class="form-control" type="file" name="image"
					style="width: 320px;"  accept=".doc,.docx" /><br /> <input type="submit" value="上传"
					class="btn btn-success"/>
			</form>
		</div>
	</div>
	<script src="<%=basePath%>resource/js/jquery.min.js?v=2.1.4"></script>
	<script src="<%=basePath%>resource/js/bootstrap.min.js?v=3.3.6"></script>
	<script type="text/javascript">
	 function sendUploadFileForm () 
	    {
	        if(CheckPost()) 
	        {
	            document.uploadFileForm.submit();
	        }
	        return false;
	    }

	    function CheckPost () 
	    {
	         if (uploadFileForm.image.value == "") 
	         {
	               alert("请选择要上传的文件！");
	               return false;
	         }
	         return true;
	    }
	</script>
</body>
</html>