<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<% String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>添加学生信息</title>
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->

<link href="<%=basePath%>/resource/css/admin/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="<%=basePath%>/resource/css/admin/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="<%=basePath%>/resource/css/admin/animate.min.css" rel="stylesheet">
<link href="<%=basePath%>/resource/css/admin/style.min.css?v=4.1.0" rel="stylesheet">
<style>
	#addStuContainer input{
		width:400px;
		float:left;
	}
</style>
</head>
<body>
	<div id="addStuContainer" class="container">
	<br>
	<br>
		<button class="btn btn-primary" type="button" id="inputStuData" style="float:right;">导入数据</button>
		<form action="AdminAction_saveStudent.action" method="post" class="form-horizontal m-t" id="commentForm" novalidate="novalidate" onsubmit="return checkStuFrom()">
		<div class="form-group">
			<label class="col-sm-3 control-label" style="margin-top: 25px;">学号：</label>
			<div class="col-sm-8">
				<input id="cname" name="studentinfoVO.studentNum" minlength="2" type="text"
					class="form-control" required="" aria-required="true">
				<div id="cnameTips" style="color: red;"></div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">姓名：</label>
			<div class="col-sm-8">
				<input id="cemail" type="text" class="form-control"
					name="studentinfoVO.studentName" required="" aria-required="true">
				<div id="cemailTips" style="color: red;"></div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">密码：</label>
			<div class="col-sm-8">
				<input id="curl" type="password" class="form-control"
					name="studentinfoVO.studentPass">
					<div id="curlTips" style="color: red;"></div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">手机号码：</label>
			<div class="col-sm-8">
				<input id="studentPhone" type="text" class="form-control"
					name="studentinfoVO.studentPhone">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">专业：</label>
			<div class="col-sm-8">
				<input id="studentMajor" type="text" class="form-control"
					name="studentinfoVO.studentMajor">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">指导老师：</label>
			<div class="col-sm-8">
				<input id=teacherName type="text" class="form-control"
					name="studentinfoVO.teacherName">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">导师：</label>
			<div class="col-sm-8">
				<input id="studyTeacher" type="text" class="form-control"
					name="studentinfoVO.studyTeacher">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">辅导员：</label>
			<div class="col-sm-8">
				<input id="supervisor" type="text" class="form-control"
					name="studentinfoVO.supervisor">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-4 col-sm-offset-3">
				<button class="btn btn-primary" type="submit">提交</button>
			</div>
		</div>
	</form>
	</div>
	
	<div class="modal fade" id="uploadStu" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="exampleModalLabel">导入数据</h4>
				</div>
	<form action="AdminAction_ExcelToDb.action" method="post" class="form-horizontal m-t"
				id="commentForm" novalidate="novalidate" enctype="multipart/form-data" onsubmit="return checkStuFile()">
			<div class="modal-body">
				<div class="form-group">
					<label class="col-sm-3 control-label">文件域：</label>
					<div class="col-sm-8">
						<input id="excelStu" name="excelStu" minlength="2" type="file"
							class="form-control" required="" aria-required="true">
					</div>
				</div>
				</div>
			<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<input type="submit" class="btn btn-primary" value="一键导入">
					</div>
				</form>
				</div>
			</div>
		</div>
</body>
	<script src="<%=basePath%>resource/js/jquery.min.js?v=2.1.4"></script>
	<script src="<%=basePath%>resource/js/bootstrap.min.js?v=3.3.6"></script>
	<script type="text/javascript">
		$("#inputStuData").click(function(){
			$('#uploadStu').modal('show');
		});
		function checkStuFrom(){
			var StuNum = $("input[id=cname]").val();
			var StuName = $("input[id=cemail]").val();	
			var StuPass = $("input[id=curl]").val();	

			if(StuNum==""||StuName==""||StuPass==""){
				alert("请先填写学生信息！");
				return false;
			}
		}
		function checkStuFile(){
			var fileStu = $("input[id=excelStu]").val();	
			if(fileStu==""){
				alert("请选择要导入的数据文件");
				return false;
			}
		}
		$("input[id=cname]").blur(function(){
			if($("input[id=cname]").val()==""){
				$("#cnameTips").html("学号不能为空！");
			}else if(isNaN($("input[id=cname]").val())){
				$("#cnameTips").html("学号只能为数字！");
			}
		});
		$("input[id=cname]").focus(function(){
			$("#cnameTips").html("");
		});
		$("input[id=cemail]").blur(function(){
			if($("input[id=cemail]").val()==""){
			$("#cemailTips").html("姓名不能为空！");
			}
		});
		$("input[id=cemail]").focus(function(){
			$("#cemailTips").html("");
		});
		$("input[id=curl]").blur(function(){
			if($("input[id=curl]").val()==""){
			$("#curlTips").html("密码不能为空！");
			}
		});
		$("input[id=curl]").focus(function(){
			$("#curlTips").html("");
		});
	</script>
</html>
