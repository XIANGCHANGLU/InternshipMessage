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
<title>申请列表</title>
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
	<div class="search-wrap">
		<div class="search-content">
			<form action="ApplyAction_queryApplyList.action" method="get">
				<table class="search-tab">
					<tr>
						<th>审批状态:</th>
						<td width="150px"><select name="searchsort" id="sel"
							class="btn dropdown-toggle" style="border-color: #ccc;">
								<option value="审核中">审核中</option>
								<option value="已审核">已审核</option>
						</select></td>
						<td align="center" width="80px"><input
							class="btn btn-primary btn2" name="sub" value="查询" type="submit"></td>
						<td>
							<div class="tips-table">
								&nbsp;&nbsp;<span>请联系申请实习的学生进行考核，确认双方的意向， <span
									class="tips-table-key">“签订实习合同”</span>之后才可以进行审批
								</span>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<table class="table table-hover table-condensed table-striped">
		<colgroup>
			<col width="8%" />
			<col width="8%" />
			<col width="12%" />
			<col width="9%" />
			<col width="15%" />
		    <col width="15%" />
			<col width="8%" />
		</colgroup>
		<thead>
			<tr height="100">
				<th>序号</th>
				<th>学号</th>
				<th>姓名</th>
				<th>指导老师</th>
				<th>联系电话</th>
				<th>申请时间</th>
				<th>申请状态</th>
			</tr>
		</thead>
		<s:iterator value="applyJobVOs">

			<tbody>
				<tr height="100">
					<td><s:property value="id" /></td>
					<td><s:property value="studentNum" /></td>
					<td><s:property value="studentName" /></td>
					<td><s:property value="teacherName" /></td>
					<td><s:property value="studentTel" /></td>
					<td><s:property value="data" /></td>
					<td><a type="button" class="btn btn-danger"
						onclick="javascript:if(!confirm('确定要审核吗？\n此操作不可以恢复！')) { return false; }"
						href="ApplyAction_approvalApply.action?id=<s:property value="id" />&studentNum=<s:property value="studentNum" />">
							<s:property value="status" />
					</a></td>
				</tr>
			</tbody>
		</s:iterator>
	</table>
	<s:if test="#request.Pagecount!=null&&#request.Pagecount!=0">
	<div class="page">
		<s:url id="url_pre" value="ApplyAction_queryApplyList.action">
			<s:param name="pageNow" value="pageNow-1"></s:param>
			<s:param name="searchsort" value="searchsort"></s:param>
		</s:url>

		<s:url id="url_next" value="ApplyAction_queryApplyList.action">
			<s:param name="pageNow" value="pageNow+1"></s:param>
			<s:param name="searchsort" value="searchsort"></s:param>
		</s:url>
		<s:url id="url" value="ApplyAction_queryApplyList.action">
			<s:param name="pageNow" value="1" />
			<s:param name="searchsort" value="searchsort"></s:param>
		</s:url>
		<s:url id="last_url" value="ApplyAction_queryApplyList.action">
				<s:param name="pageNow" value="#request.Pagecount" />
				<s:param name="searchsort" value="searchsort"></s:param>
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
	<script src="<%=basePath%>resource/js/jquery.min.js?v=2.1.4"></script>
	<script src="<%=basePath%>resource/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="<%=basePath%>resource/js/jquery.metisMenu.js"></script>
	<script src="<%=basePath%>resource/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$("a").each(function() {
				var a = $(this).text().replace(/\s+/g, "");
				if (a == "已审核") {
					$(this).attr("class", "btn btn-success");
					$(this).removeAttr("href");
					$(this).attr("disabled", "disabled");
					$(this).removeAttr("onclick");
				}
			});
		});
		function check() {
			if (confirm("确定审核吗？")) {
				return true;
			} else {
				return false;
			}

		}
	</script>
</body>
</html>
