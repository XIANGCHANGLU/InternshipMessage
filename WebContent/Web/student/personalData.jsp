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
<link
	href="<%=basePath%>/resource/css/admin/font-awesome.min.css?v=4.4.0"
	rel="stylesheet">
<link href="<%=basePath%>/resource/css/admin/animate.min.css"
	rel="stylesheet">
<link href="<%=basePath%>/resource/css/admin/style.min.css?v=4.1.0"
	rel="stylesheet">
<style type="text/css">
.personal {
	margin-top: 160px;
	background-color: #ffffff;
	width: 1010px;
}
</style>

</head>
<body>
	<div class="container">
	<div><button type="button" class="btn btn-primary btn2" style="float: right; margin-top: 86px;" onclick="updatePass(this);">修改密码</button></div>
		<div class="personal">
			<s:iterator value="studentinfoVOs">
				<table class="table table-striped" style="border-color: #fff">
					<tr>
						<td>学号：</td>
						<td><s:property value="studentNum" /></td>
						<td>姓名：</td>
						<td><s:property value="studentName" /></td>

					</tr>
					<tr>
						<td>家庭住址：</td>
						<td><s:property value="studentAddress" /></td>
						<td>邮箱：</td>
						<td><s:property value="studentMail" /></td>

					</tr>
					<tr>
						<td>专业：</td>
						<td><s:property value="studentMajor" /></td>
						<td>手机号码：</td>
						<td><s:property value="studentPhone" /></td>
					</tr>
					<tr>
						<td>指导老师：</td>
						<td><s:property value="teacherName" /></td>
						<td>辅导员：</td>
						<td><s:property value="supervisor" /></td>
					</tr>
					<tr>
						<td>班主任：</td>
						<td><s:property value="studyTeacher" /></td>
						<td>实习周记审核次数：</td>
						<td><%=request.getAttribute("num") %></td>
					</tr>
					<tr>
						<td>是否提交论文：</td>
						<td><%=request.getAttribute("status") %></td>
						<td>实习公司：</td>
						<td><%=request.getAttribute("companyName") %></td>
					</tr>
				</table>
			</s:iterator>
		</div>
	</div>
	
	<div class="modal fade" id="updatePass" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" >
		<div class="modal-dialog" role="document" style="width:450px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="exampleModalLabel">修改密码</h4>
				</div>
					<div class="modal-body">
						<div class="form-group">
							<div style="float:left;padding-right: 20px;">
							<label for="recipient-name" class="control-label">旧密码:</label>
							</div>
							<div>
							 <input id="oldStuPass" style="width:300px;" type="password" class="form-control" name="oldStuPass">
							</div>
						</div>
						<div class="form-group">
						<div style="float:left;padding-right: 20px;">
							<label for="recipient-name" class="control-label">新密码:</label> 
							</div>
							<div>
							<input id="newStuPass" style="width:300px;" type="password" class="form-control" id="recipient-name" name="newStuPass">
							</div>
							</div>
							<div class="form-group">
						<div style="float:left;padding-right: 7px;">
							<label for="recipient-name" class="control-label">确认密码:</label> 
							</div>
							<div>
							<input id="newStuPass1" style="width:300px;" type="password" class="form-control" id="recipient-name" name="newStuPass1">
							</div>
							</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" id="updataStuPass" >确定</button>

					</div>
			</div>
		</div>
	</div>
	<script src="<%=basePath%>resource/js/jquery.min.js?v=2.1.4"></script>
	<script src="<%=basePath%>resource/js/bootstrap.min.js?v=3.3.6"></script>
	<script type="text/javascript">
		function updatePass(obj) {
			$('#updatePass').modal('show');
		}
		$("#updataStuPass").click(function(){
			var oldStuPass =  $("input[name=oldStuPass]").val(),
				newStuPass =  $("input[name=newStuPass]").val(),
				newStuPass1 = $("input[name=newStuPass1]").val();
			if(oldStuPass!=""&&newStuPass!=""&&newStuPass1!=""){
				$.ajax({
					"type" : "post",
					"contentType" : "application/x-www-form-urlencoded;charset=utf-8",
					url : 'StudentAction_updataStuPass.action',
					data : {
						"oldStuPass" :oldStuPass,
						"newStuPass" :newStuPass,
						"newStuPass1":newStuPass1
					},
					success : function(data) {
						if(data){
							alert(data);
							$('#updatePass').modal('hide');
							$("input[name=oldStuPass]").val("");
							$("input[name=newStuPass]").val("");
							$("input[name=newStuPass1]").val("");
						}
					},
					error : function(data) {
						alert("系统出现异常请稍候再试");
					}
				});
			}else{
				alert("输入框不能为空");
			}
		
		});
		</script>
</body>
</html>