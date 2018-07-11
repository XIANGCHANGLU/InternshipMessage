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
	<button type="button" class="btn btn-primary btn2"
		style="float: right;margin-right: 75px;" onclick="javascript :history.back(-1)">返回申请页面
	</button>
	<table class="table table-hover table-condensed table-striped" style="width:100%">
		<colgroup>
			<col width="20%" />
			<col width="20%" />
			<col width="20%" />
			<col width="20%" />
			<col width="20%" />
		</colgroup>
		<thead>
			<tr height="100">
<!-- 				<th><input id="userSelAllBtn" type="checkbox" /></th> -->
				<th>序号</th>
				<th>公司编码</th>
				<th>公司名称</th>
				<th>申请时间</th>
				<th>申请状态</th>
			</tr>
		</thead>
		<%
			int i = 1;
		%>
		<s:iterator value="applyJobVOs">

			<tbody>
				<tr height="100">
<%-- 					<td><input type="checkbox" name="chk1"
						value="<s:property value="id" />" /></td> --%>

					<td><%=i++%></td>
					<td><s:property value="companyNum" /></td>
					<td><a href="#" class="tooltipcontent" data-toggle="tooltip"
						title=""><s:property value="companyName" /></a></td>
					<td><s:property value="data" /></td>
					<td><button type="button" class="btn btn-info">
							<s:property value="status" />
						</button></td>
				</tr>
			</tbody>
		</s:iterator>
	</table>
	<script src="<%=basePath%>resource/js/jquery.min.js?v=2.1.4"></script>
	<script src="<%=basePath%>resource/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="<%=basePath%>resource/js/jquery.metisMenu.js"></script>
	<script src="<%=basePath%>resource/js/jquery.slimscroll.min.js"></script>
	<script src="<%=basePath%>resource/js/layer.min.js"></script>
	<script src="<%=basePath%>resource/js/hplus.min.js?v=4.1.0"></script>
	<script type="text/javascript"
		src="<%=basePath%>resource/js/contabs.min.js"></script>
	<script src="<%=basePath%>resource/js/pace.min.js"></script>
</body>
</html>
