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
<title>点评学生</title>
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
	.table {
		width: 92%;
		margin-left: 50px;
		margin-right: 50px;
	}
	
	th {
		text-align: left;
	}
	
	.page {
		margin-left: 50px;
	}
</style>
</head>
<body>
	<br>
	<br>
	<table class="table table-hover table-condensed table-striped">
		<colgroup>
			<col width="20%" />
			<col width="20%" />
			<col width="20%" />
			<col width="20%" />
			<col width="10%" />
		</colgroup>
		<thead>
			<tr height="100">
				<th width="400">序号</th>
				<th width="150">学号</th>
				<th width="100">姓名</th>
				<th width="100">指导老师</th>
				<th width="100">操作</th>
			</tr>
		</thead>
		<s:iterator value="applyJobVOs">

			<tbody>
				<tr height="100">
					<td><s:property value="id" /></td>
					<td><s:property value="studentNum" /></td>
					<td><s:property value="studentName" /></td>
					<td><s:property value="teacherName" /></td>
					<td><a type="button" class="btn btn-danger" onclick="evaStu(this);"><s:property value="evaStatus" /></a></td>
				</tr>
			</tbody>
		</s:iterator>
	</table>
	<s:if test="#request.Pagecount!=null&&#request.Pagecount!=0">
	<div class="page">
		<s:url id="url_pre" value="CompanyAction_showComwithStu.action">
			<s:param name="pageNow" value="pageNow-1"></s:param>
		</s:url>

		<s:url id="url_next" value="CompanyAction_showComwithStu.action">
			<s:param name="pageNow" value="pageNow+1"></s:param>
		</s:url>
		<s:url id="url" value="CompanyAction_showComwithStu.action">
				<s:param name="pageNow" value="1" />
		</s:url>
			<s:url id="last_url" value="CompanyAction_showComwithStu.action">
				<s:param name="pageNow" value="#request.Pagecount" />
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
		<div class="modal fade" id="EditevaStu" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="exampleModalLabel">评价学生</h4>
				</div>
					<div class="modal-body">
						<div class="form-group">
							<label for="recipient-name" class="control-label">学号:</label> <input
								id="studentNum" type="text" class="form-control"
								name="studentNum" readonly="readonly">
						</div>
						<div class="form-group">
							<label for="recipient-name" class="control-label">姓名:</label> <input
								id="studentName" type="text" class="form-control" id="recipient-name"
								name="studentName" readonly="readonly">
						</div>
						<div class="form-group">
							<label for="message-text" class="control-label">指导老师:</label> <input
								id="teacherName" type="text" class="form-control" id="recipient-name"
								name="teacherName" readonly="readonly">
						</div>
						<div class="form-group" style="margin-bottom: 0px;">
							<label for="recipient-name" class="control-label">内容:</label> <textarea
								id="content" class="form-control" id="recipient-name"
								name="content" style="height:100px;"></textarea>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<input id="EditEvaStuBtn" type="submit" class="btn btn-primary" value="确定">

					</div>
			</div>
		</div>
	</div>
	<script src="<%=basePath%>resource/js/jquery.min.js?v=2.1.4"></script>
	<script src="<%=basePath%>resource/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="<%=basePath%>resource/js/jquery.metisMenu.js"></script>
	<script src="<%=basePath%>resource/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript">
	var id;
		$(function() {
			$("#EditEvaStuBtn").click(function(){
				$.ajax({
					"type" : "post",
					"contentType" : "application/x-www-form-urlencoded;charset=utf-8",
					url : 'CompanyAction_saveEvaStu.action',
					data : {
						"studentNum" : $("#studentNum").val(),
						"studentName": $("#studentName").val(),
						"teacherName": $("#teacherName").val(),
						"content":$("#content").val(),
						"id":id
					},
					success : function(data) {
						alert("评价成功！");window.location.href="CompanyAction_showComwithStu.action";
						$('#EditevaStu').modal('hide');
						$("#content").val("");
					}
				});
			});
		});
		function evaStu(obj){
			var tds = $(obj).parent().parent().find('td');
			id = tds.eq(0).text();
			var studentNum = tds.eq(1).text();
			var studentName = tds.eq(2).text();
			var teacherName = tds.eq(3).text();
			$("#studentNum").val(studentNum);
			$("#studentName").val(studentName);
			$("#teacherName").val(teacherName);
			$('#EditevaStu').modal('show');
		}	
	</script>
</body>
</html>
