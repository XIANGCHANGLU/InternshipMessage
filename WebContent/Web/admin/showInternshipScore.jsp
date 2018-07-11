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

input {
	width: 50px;
	align: center;
}
</style>
</head>
<body>
<br>
<br>
		<div class="search-wrap">
		<div class="search-content">
			<form action="AdminAction_showAllScore.action" method="get">
				<table class="search-tab">
					<tr>
						<th>查询条件:</th>
						<td width="150px"><select name="searchsort" id="sel"
							class="btn dropdown-toggle" style="border-color: #ccc;">
								<option value="studentNum">学号</option>
								<option value="studentName">姓名</option>
								<option value="companyName">单位名称</option>
						</select></td>
						<th class="keyword" width="60px">关键字:</th>
						<td><input class="form-control" placeholder="关键字"
							name="searname" value="" id="searname" type="text"></td>
						<td align="center" width="80px"><input
							class="btn btn-primary btn2" name="sub" value="查询" type="submit"></td>
						<td width="30px;"></td>
						<td><a class="btn btn-primary btn2" type="button" href="AdminAction_downloadFile.action?downloadFilePath=score.xls">导出成绩表</a></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<table class="table table-hover table-condensed table-striped">
		<colgroup>
			<col width="8%" />
			<col width="8%" />
			<col width="9%" />
			<col width="12%" />
			<col width="9%" />
		</colgroup>
		<thead>
			<tr height="100">
				<th width="400">序号</th>
				<th width="150">学号</th>
				<th width="100">姓名</th>
				<th width="100">实习单位</th>
				<th width="100">实习分数</th>
			</tr>
		</thead>
			<tbody>
			<s:iterator value="scoreVOs">
				<tr height="100">
					<td><s:property value="id" /></td>
					<td><s:property value="studentNum" /></td>
					<td><s:property value="studentName" /></td>
					<td><s:property value="companyName" /></td>
					<td><s:property value="score" /></td>
				</tr>
			</s:iterator>
			</tbody>
	</table>
	<s:if test="#request.Pagecount!=null&&#request.Pagecount!=0">
	<div class="page">
		<s:url id="url_pre" value="AdminAction_showAllScore.action">
			<s:param name="pageNow" value="pageNow-1"></s:param>
			<s:param name="searchsort" value="searchsort"></s:param>
			<s:param name="searname" value="searname"></s:param>
		</s:url>

		<s:url id="url_next" value="AdminAction_showAllScore.action">
			<s:param name="pageNow" value="pageNow+1"></s:param>
			<s:param name="searchsort" value="searchsort"></s:param>
			<s:param name="searname" value="searname"></s:param>
		</s:url>
		<s:url id="url" value="AdminAction_showAllScore.action">
			<s:param name="pageNow" value="1" />
			<s:param name="searchsort" value="searchsort"></s:param>
			<s:param name="searname" value="searname"></s:param>
		</s:url>
		<s:url id="last_url" value="AdminAction_showAllScore.action">
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
	<script src="<%=basePath%>resource/js/jquery.min.js?v=2.1.4"></script>
	<script src="<%=basePath%>resource/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="<%=basePath%>resource/js/jquery.metisMenu.js"></script>
	<script src="<%=basePath%>resource/js/jquery.slimscroll.min.js"></script>
</body>
</html>
