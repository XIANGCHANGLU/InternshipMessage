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
		<div><button type="button" class="btn btn-primary btn2" style="float: right; margin-top: 86px; " onclick="updatePass(this);">修改密码</button></div>
		<div class="personal">
			<s:iterator value="companyinfoVOs">
				<table class="table table-striped" style="border-color: #fff">
					<tr>
						<td>公司编码：</td>
						<td><s:property value="companyNum" /></td>
						<td>公司名称：</td>
						<td><s:property value="companyName" /></td>

					</tr>
					<tr>
						<td>公司地址：</td>
						<td><s:property value="companyAddress" /></td>
						<td>所属行业：</td>
						<td><s:property value="companyIndustry" /></td>

					</tr>
					<tr>
						<td>类型：</td>
						<td><s:property value="companyType" /></td>
						<td>联系人：</td>
						<td><s:property value="companyContacts" /></td>
					</tr>
					<tr>
						<td>电话：</td>
						<td><s:property value="companyPhone" /></td>
						<td>详情：</td>
						<td width="450px"><s:property value="companyDescription" /></td>
					</tr>
				</table>
			</s:iterator>
		</div>
	</div>
	
	<div class="modal fade" id="updatecmpPass" tabindex="-1" role="dialog"
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
							 <input id="oldCmpPass" style="width:300px;" type="password" class="form-control" name="oldCmpPass">
							</div>
						</div>
						<div class="form-group">
						<div style="float:left;padding-right: 20px;">
							<label for="recipient-name" class="control-label">新密码:</label> 
							</div>
							<div>
							<input id="newCmpPass" style="width:300px;" type="password" class="form-control" id="recipient-name" name="newCmpPass">
							</div>
							</div>
							<div class="form-group">
						<div style="float:left;padding-right: 7px;">
							<label for="recipient-name" class="control-label">确认密码:</label> 
							</div>
							<div>
							<input id="newCmpPass1" style="width:300px;" type="password" class="form-control" id="recipient-name" name="newCmpPass1">
							</div>
							</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" id="updatacmpPassword" >确定</button>

					</div>
			</div>
		</div>
	</div>
	<script src="<%=basePath%>resource/js/jquery.min.js?v=2.1.4"></script>
	<script src="<%=basePath%>resource/js/bootstrap.min.js?v=3.3.6"></script>
	<script type="text/javascript">
		function updatePass(obj) {
			$('#updatecmpPass').modal('show');
		}
		$("#updatacmpPassword").click(function(){
			var oldCmpPass =  $("input[name=oldCmpPass]").val(),
				newCmpPass =  $("input[name=newCmpPass]").val(),
				newCmpPass1 = $("input[name=newCmpPass1]").val();
			if(oldCmpPass!=""&&newCmpPass!=""&&newCmpPass1!=""){
				$.ajax({
					"type" : "post",
					"contentType" : "application/x-www-form-urlencoded;charset=utf-8",
					url : 'CompanyAction_updataCmpPass.action',
					data : {
						"oldCmpPass" :oldCmpPass,
						"newCmpPass" :newCmpPass,
						"newCmpPass1":newCmpPass1
					},
					success : function(data) {
						if(data){
							alert(data);
							$('#updatecmpPass').modal('hide');
							$("input[name=oldCmpPass]").val("");
							$("input[name=newCmpPass]").val("");
							$("input[name=newCmpPass1]").val("");
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