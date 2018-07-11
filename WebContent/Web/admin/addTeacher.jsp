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
	#addTeaContainer input{
		width:400px;
		float:left;
	}
</style>
</head>

<body>
	<button class="btn btn-primary" type="button" id="inputTeaData" style="float:right;margin: 50px;">导入数据</button>
	<div id="addTeaContainer">
		<br>
		<form action="TeacherAction_saveTeacher.action" method="post" class="form-horizontal m-t"
		id="commentForm" novalidate="novalidate" onsubmit="return checkTeaFrom()" style="margin-top:100px;">
		<div class="form-group">
			<label class="col-sm-3 control-label" style="margin-top: 15px;">工号：</label>
			<div class="col-sm-8">
				<input id="Teacname" name="teacherinfoVO.teacherNum" minlength="2" type="text"
					class="form-control" required="" aria-required="true" >
				<div id="TeacnameTips" style="color: red;"></div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">姓名：</label>
			<div class="col-sm-8">
				<input id="Teacemail" type="text" class="form-control"
					name="teacherinfoVO.teacherName"  aria-required="true">
				<div id="TeanameTips" style="color: red;"></div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">密码：</label>
			<div class="col-sm-8">
				<input id="Teacurl" type="password" class="form-control"
					name="teacherinfoVO.teacherPass">
				<div id="TeacurlTips" style="color: red;"></div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">职位：</label>
			<div class="col-sm-8">
				<input id="curl" type="text" class="form-control"
					name="teacherinfoVO.position">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">专业：</label>
			<div class="col-sm-8">
				<input id="curl" type="text" class="form-control"
					name="teacherinfoVO.teacherDept">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">手机号码：</label>
			<div class="col-sm-8">
				<input id="curl" type="text" class="form-control"
					name="teacherinfoVO.teacherPhone">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">邮箱：</label>
			<div class="col-sm-8">
				<input id="curl" type="text" class="form-control"
					name="teacherinfoVO.teacherMail">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-4 col-sm-offset-3">
				<button class="btn btn-primary" type="submit">提交</button>
			</div>
		</div>
	</form>
	</div>
	<div class="modal fade" id="uploadTea" tabindex="-1" role="dialog"
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
			<form action="TeacherAction_ExcelToDb.action" method="post" class="form-horizontal m-t" id="commentForm" novalidate="novalidate" enctype="multipart/form-data" onsubmit="return checkTeaFile()">
						<div class="form-group">
							<label class="col-sm-3 control-label">文件域：</label>
							<div class="col-sm-8">
								<input id="cname" name="excelTea" minlength="2" type="file"
									class="form-control" required="" aria-required="true">
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
	$("#inputTeaData").click(function(){
		$('#uploadTea').modal('show');
	});
		function checkTeaFrom(){
			var TeaNum = $("input[id=Teacname]").val();
			var TeaName = $("input[id=Teacemail]").val();	
			var TeaPass = $("input[id=Teacurl]").val();	
			if(TeaNum==""||TeaName==""||TeaPass==""){
				alert("请先填写职工信息！");
				return false;
			}
		}
		function checkTeaFile(){
			var fileTea = $("input[name=excelTea]").val();	
			if(fileTea==""){
				alert("请选择要导入的数据文件");
				return false;
			}
		}
		$("input[id=Teacname]").blur(function(){
			if($("input[id=Teacname]").val()==""){
				$("#TeacnameTips").html("工号不能为空！");
			}else if(isNaN($("input[id=Teacname]").val())){
				$("#TeacnameTips").html("工号只能为数字！");
			}
		});
		$("input[id=Teacname]").focus(function(){
			$("#TeacnameTips").html("");
		});
		$("input[id=Teacemail]").blur(function(){
			if($("input[id=Teacemail]").val()==""){
				$("#TeanameTips").html("姓名不能为空！");
			}
		});
		$("input[id=Teacemail]").focus(function(){
			$("#TeanameTips").html("");
		});
		$("input[id=Teacurl]").blur(function(){
			if($("input[id=Teacurl]").val()==""){
				$("#TeacurlTips").html("密码不能为空！");
			}
		});
		$("input[id=Teacurl]").focus(function(){
			$("#TeacurlTips").html("");
		});
	</script>
</html>
