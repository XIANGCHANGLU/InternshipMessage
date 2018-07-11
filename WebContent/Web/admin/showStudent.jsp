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
.search-wrap {
	margin-left: 100px;
}

#sel {
	width: 110px;
	height: 34px;
	margin-left: 10px;
}

.keyword {
	margin-left: 50x;
}

.table {
	width: 92%;
	margin-left: 50px;
	margin-right: 50px;
}

th {
	text-align: center;
}
.page{
	margin-left:50px;
}
</style>
</head>
<body>
	<br>
	<br>
	<div class="search-wrap">
		<div class="search-content">
			<form action="AdminAction_searchStu.action" method="get">
				<table class="search-tab">
					<tr>
						<th>查询条件:</th>
						<td width="150px"><select name="searchsort" id="sel"
							class="btn dropdown-toggle" style="border-color: #ccc;">
								<option value="studentNum">学号</option>
								<option value="teacherName">指导老师</option>
								<option value="supervisor">辅导员</option>
								<option value="studyTeacher">导师</option>
						</select></td>
						<th class="keyword" width="60px">关键字:</th>
						<td><input class="form-control" placeholder="关键字"
							name="searname" value="" id="searname" type="text"></td>
						<td align="center" width="80px"><input
							class="btn btn-primary btn2" name="sub" value="查询" type="submit"></td>
						<td><a class="btn btn-primary btn2" type="button"
							onclick="delStu();">删除</a></td>
						<td>
							<div class="tips-table">
								&nbsp;&nbsp;<span>请勾选后再删除</span> <span class="tips-table-key">“选中对应用户”</span>
								<span>才能使用。</span>
							</div>
						</td>
						<td><a class="btn btn-primary btn2" type="button"
							onclick="ResetStudentPass();">重置密码</a></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<table class="table table-hover table-condensed table-striped">
		<colgroup>
			<col width="3%" />
			<col width="5%" />
			<col width="7%" />
			<col width="15%" />
			<col width="20%" />
			<col width="10%" />
			<col width="13%" />
			<col width="7%" />
			<col width="8%" />
			<col width="7%" />
			<col width="5%" />
		</colgroup>
		<thead>
			<tr height="100">
				<th><input id="userSelAllBtn" type="checkbox" /></th>
				<th width="400">学号</th>
				<th width="150">姓名</th>
				<th width="100" align="center">邮箱</th>
				<th width="100">住宅地址</th>
				<th width="100">手机号码</th>
				<th width="100">专业</th>
				<th width="100">指导老师</th>
				<th width="100">导师</th>
				<th width="100">辅导员</th>
				<th width="150">操作</th>
			</tr>
		</thead>
		<s:iterator value="studentinfoVOs">
			<tbody>
				<tr height="100">
					<td><input type="checkbox" name="chk1"
						value="<s:property value="id" />" /></td>
					<td><s:property value="studentNum" /></td>
					<td><s:property value="studentName" /></td>
					<td><s:property value="studentMail" /></td>
					<td><s:property value="studentAddress" /></td>
					<td><s:property value="studentPhone" /></td>
					<td><s:property value="studentMajor" /></td>
					<td><s:property value="teacherName" /></td>
					<td><s:property value="studyTeacher" /></td>
					<td><s:property value="supervisor" /></td>
					<td><a href="javascript:void(0)" onclick="update(this);"><i
							class="icon-edit"></i></a>/ <a
						onclick="delStudent(this)"><i class="icon-trash"></i></a></td>
				</tr>
			</tbody>
		</s:iterator>
	</table>
	<s:if test="#request.Pagecount!=null&&#request.Pagecount!=0">
	<div class="page">
		<s:url id="url_pre" value="AdminAction_searchStudentinfo.action">
			<s:param name="pageNow" value="pageNow-1"></s:param>
			<s:param name="searchsort" value="searchsort"></s:param>
			<s:param name="searname" value="searname"></s:param>
		</s:url>
	
		<s:url id="url_next" value="AdminAction_searchStudentinfo.action">
			<s:param name="pageNow" value="pageNow+1"></s:param>
			<s:param name="searchsort" value="searchsort"></s:param>
			<s:param name="searname" value="searname"></s:param>
		</s:url>
			<s:url id="url" value="AdminAction_searchStudentinfo.action">
				<s:param name="pageNow" value="1" />
				<s:param name="searchsort" value="searchsort"></s:param>
				<s:param name="searname" value="searname"></s:param>
			</s:url>
			<s:url id="last_url" value="AdminAction_searchStudentinfo.action">
				<s:param name="pageNow" value="#request.Pagecount" />
				<s:param name="searchsort" value="searchsort"></s:param>
				<s:param name="searname" value="searname"></s:param>
			</s:url>
		<s:if test="pageNow==1">
			<s:a href="">首页</s:a>
		</s:if>
		<s:else>
			<s:a href="%{url}">首页</s:a>
		</s:else>
		<s:if test="pageNow-1==0">
				<s:a href="">上一页</s:a>
		</s:if>
		<s:else>
				<s:a href="%{url_pre}">上一页</s:a>
		</s:else>
		<s:if test="pageNow==#request.Pagecount">
			<s:a href="">下一页</s:a>
		</s:if>
		<s:else>
			<s:a href="%{url_next}">下一页</s:a>
		</s:else>
		<s:if test="pageNow==#request.Pagecount">
			<s:a href="">尾页</s:a>
		</s:if>
		<s:else>
			<s:a href="%{last_url}">尾页</s:a>
		</s:else>
	</div>
    </s:if>
	

	<div class="modal fade" id="update" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="exampleModalLabel">修改学生信息</h4>
				</div>
				<form action="AdminAction_updateStudentinfo.action" method="post">
					<div class="modal-body">
						<div class="form-group">
							<label for="recipient-name" class="control-label">学号:</label> <input
								id="id1" type="text" class="form-control"
								name="studentinfoVO.studentNum" readonly="readonly">
						</div>
						<div class="form-group">
							<label for="recipient-name" class="control-label">姓名:</label> <input
								id="id2" type="text" class="form-control" id="recipient-name"
								name="studentinfoVO.studentName">
						</div>
						<div class="form-group">
							<label for="recipient-name" class="control-label">邮箱:</label> <input
								id="id4" type="text" class="form-control" id="recipient-name"
								name="studentinfoVO.studentMail">
						</div>
						<div class="form-group">
							<label for="recipient-name" class="control-label">住宅地址:</label> <input
								id="id5" type="text" class="form-control" id="recipient-name"
								name="studentinfoVO.studentAddress">
						</div>
						<div class="form-group">
							<label for="recipient-name" class="control-label">手机号码:</label> <input
								id="id6" type="text" class="form-control" id="recipient-name"
								name="studentinfoVO.studentPhone">
						</div>
						<div class="form-group">
							<label for="recipient-name" class="control-label">专业:</label> <input
								id="id7" type="text" class="form-control" id="recipient-name"
								name="studentinfoVO.studentMajor">
						</div>
						<div class="form-group">
							<label for="recipient-name" class="control-label">指导老师:</label> <input
								id="id8" type="text" class="form-control" id="recipient-name"
								name="studentinfoVO.teacherName">
						</div>
						<div class="form-group">
							<label for="recipient-name" class="control-label">导师:</label> <input
								id="id9" type="text" class="form-control" id="recipient-name"
								name="studentinfoVO.studyTeacher">
						</div>
						<div class="form-group">
							<label for="recipient-name" class="control-label">辅导员:</label> <input
								id="id10" type="text" class="form-control" id="recipient-name"
								name="studentinfoVO.supervisor">
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<input type="submit" class="btn btn-primary" value="确定">

					</div>
				</form>
			</div>
		</div>
	</div>
	<script src="<%=basePath%>resource/js/jquery.min.js?v=2.1.4"></script>
	<script src="<%=basePath%>resource/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="<%=basePath%>resource/js/jquery.metisMenu.js"></script>
	<script src="<%=basePath%>resource/js/jquery.slimscroll.min.js"></script>
	<script src="<%=basePath%>resource/js/layer.min.js"></script>
	<script src="<%=basePath%>resource/js/hplus.min.js?v=4.1.0"></script>
	<script type="text/javascript"
		src="<%=basePath%>resource/js/contabs.min.js"></script>
	<script src="<%=basePath%>resource/js/pace.min.js"></script>
	<script type="text/javascript">
		function update(obj) {
			var tds = $(obj).parent().parent().find('td');
			$('#id1').val(tds.eq(1).text());
			$('#id2').val(tds.eq(2).text());
			$('#id4').val(tds.eq(3).text());
			$('#id5').val(tds.eq(4).text());
			$('#id6').val(tds.eq(5).text());
			$('#id7').val(tds.eq(6).text());
			$('#id8').val(tds.eq(7).text());
			$('#id9').val(tds.eq(8).text());
			$('#id10').val(tds.eq(9).text());
			$('#update').modal('show');
		}
		$("#userSelAllBtn").click(function() {
			if (this.checked) {
				$("input[type='checkbox']").each(function() {
					this.checked = true;
				});
			} else {
				$("input[type='checkbox']").each(function() {
					this.checked = false;
				});
			}
		});
		function delStudent(obj){
			if(checkSelStudent()){
				$.ajax({
					"type" : "post",
					"contentType" : "application/x-www-form-urlencoded;charset=utf-8",
					url : 'AdminAction_delStudent.action',
					data : {
						"delId" : $(obj).parent().parent().find('td').eq(1).text()
					},
					success : function(data) {
						if (data != null) {
							location.reload();
						} else {
							return null;
						}
					}
				});
			}
		}
		function checkSelStudent() {
			if (confirm("确定要删除吗？")) {
				return true;
			} else {
				return false;
			}

		}
		function checkPass() {
			if (confirm("确定要重置此用户的密码吗？")) {
				return true;
			} else {
				return false;
			}

		}
		function ResetStudentPass(){
			var obj = document.getElementsByName('chk1');
			var s = '';
			for (var i = 0; i < obj.length; i++) {
				if (obj[i].checked)
					s += obj[i].value + ','; //如果选中，将value添加到变量s中 
			}
			if(s==""){
				alert("请选择要重置的用户！");
			}else{
				if(checkPass()){
				$.ajax({
					"type" : "post",
					"contentType" : "application/x-www-form-urlencoded;charset=utf-8",
					url : 'AdminAction_resetStuPass.action',
					data : {
						"sid" : s.toString()
					},
					success : function(data) {
						if (data != null) {
							alert("密码重置成功！")
							location.reload();
						} else {
							return null;
						}
					}
				});
			}
			
		}
	}
		function delStu() {
			var obj = document.getElementsByName('chk1');
			var s = '';
			for (var i = 0; i < obj.length; i++) {
				if (obj[i].checked)
					s += obj[i].value + ','; //如果选中，将value添加到变量s中 
			}
			if(s==""){
				alert("请选择要删除的学生！");
			}else{
				if(checkSelStudent()){
					$.ajax({
						"type" : "post",
						"contentType" : "application/x-www-form-urlencoded;charset=utf-8",
						url : 'AdminAction_delAllStudent.action',
						data : {
							"sid" : s.toString()
						},
						success : function(data) {
							if (data != null) {
								location.reload();
							} else {
								return null;
							}
						}
						
					});}
		}}
	</script>
</body>
</html>
