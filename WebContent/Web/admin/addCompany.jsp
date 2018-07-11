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
<title>添加公司信息</title>
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->

<link href="<%=basePath%>/resource/css/admin/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="<%=basePath%>/resource/css/admin/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="<%=basePath%>/resource/css/admin/animate.min.css" rel="stylesheet">
<link href="<%=basePath%>/resource/css/admin/style.min.css?v=4.1.0" rel="stylesheet">
<style>
	#addCompanyContainer input{
		width:400px;
		float:left;
	}
</style>
</head>

<body>
	<div id="addCompanyContainer">
		<button class="btn btn-primary" type="button" id="inputCmpData" style="float:right;margin-right: 40px;">导入数据</button>
		<form action="CompanyAction_insertCmp.action" method="post" class="form-horizontal m-t"
		id="commentForm" novalidate="novalidate" onsubmit="return checkCmpFrom()">
		<div class="form-group">
			<label class="col-sm-3 control-label" style="margin-top: 35px;">公司编码：</label>
			<div class="col-sm-8">
				<input id="Cmpcname" name="companyinfoVO.companyNum" minlength="2" type="text"
					class="form-control" required="" aria-required="true">
				<div id="CmpcnameTips" style="color: red;"></div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">公司名称：</label>
			<div class="col-sm-8">
				<input id="Cmpcemail" type="text" class="form-control"
					name="companyinfoVO.companyName" required="" aria-required="true">
				<div id="CmpcemailTips" style="color: red;"></div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">密码：</label>
			<div class="col-sm-8">
				<input id="Cmpcurl" type="password" class="form-control"
					name="companyinfoVO.companyPass">
				<div id="CmpcurlTips" style="color: red;"></div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">公司地址：</label>
			<div class="col-sm-8">
				<input id="curl" type="text" class="form-control"
					name="companyinfoVO.companyAddress">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">所属行业：</label>
			<div class="col-sm-8">
				<input id="curl" type="text" class="form-control"
					name="companyinfoVO.companyIndustry">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">类型：</label>
			<div class="col-sm-8">
				<input id="companyType" type="text" class="form-control"
					name="companyinfoVO.companyType">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">联系人：</label>
			<div class="col-sm-8">
				<input id="companyContacts" type="text" class="form-control"
					name="companyinfoVO.companyContacts">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">电话号码：</label>
			<div class="col-sm-8">
				<input id="companyPhone" type="text" class="form-control"
					name="companyinfoVO.companyPhone">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">简介：</label>
			<div class="col-sm-8">
				<input id="companyDescription" type="text" class="form-control"
					name="companyinfoVO.companyDescription">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-4 col-sm-offset-3">
				<button class="btn btn-primary" type="submit">提交</button>
			</div>
		</div>
	</form>
	</div>
	
	<div class="modal fade" id="uploadCmp" tabindex="-1" role="dialog"
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
			<form action="CompanyAction_ExcelToDb.action" method="post" class="form-horizontal m-t"
				id="commentForm" novalidate="novalidate"  enctype="multipart/form-data" onsubmit="return checkCmpFile()">
				<div class="form-group">
					<label class="col-sm-3 control-label">文件域：</label>
					<div class="col-sm-8">
						<input id="cname" name="excelCmy" minlength="2" type="file"
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
	$("#inputCmpData").click(function(){
		$('#uploadCmp').modal('show');
	});
		function checkCmpFrom(){
			var CmpNum = $("input[id=Cmpcname]").val();
			var CmpName = $("input[id=Cmpcemail]").val();	
			var CmpPass = $("input[id=Cmpcurl]").val();	
			if(CmpNum==""||CmpName==""||CmpPass==""){
				alert("请先填写企业信息！");
				return false;
			}
		}
		function checkCmpFile(){
			var fileCmp = $("input[name=excelCmy]").val();	
			if(fileCmp==""){
				alert("请选择要导入的数据文件");
				return false;
			}
		}
		$("input[id=Cmpcname]").blur(function(){
			if($("input[id=Cmpcname]").val()==""){
				$("#CmpcnameTips").html("企业编号不能为空！");
			}
		});
		$("input[id=Cmpcname]").focus(function(){
			$("#CmpcnameTips").html("");
		});
		$("input[id=Cmpcemail]").blur(function(){
			if($("input[id=Cmpcemail]").val()==""){
				$("#CmpcemailTips").html("企业名称不能为空！");
			}
		});
		$("input[id=Cmpcemail]").focus(function(){
			$("#CmpcemailTips").html("");
		});
		$("input[id=Cmpcurl]").blur(function(){
			if($("input[id=Cmpcurl]").val()==""){
				$("#CmpcurlTips").html("密码不能为空！");
			}
		});
		$("input[id=Cmpcurl]").focus(function(){
			$("#CmpcurlTips").html("");
		});
	</script>
</html>
