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
	<div class="search-wrap">
		<div class="search-content">
			<form action="StudentAction_searchCmp.action" method="get">
				<table class="search-tab">
					<tr>
						<th style="width: 65px;">查询条件:</th>
						<td width="150px"><select name="searchsort" id="sel"
							class="btn dropdown-toggle" style="border-color: #ccc;">
								<option value="companyNum">公司编码</option>
								<option value="companyName">公司名称</option>
								<option value="companyType">类型</option>
						</select></td>
						<th class="keyword" width="60px">关键字:</th>
						<td><input class="form-control" placeholder="关键字"
							name="searname" value="" id="searname" type="text"></td>
						<td align="center" width="80px"><input
							class="btn btn-primary btn2" name="sub" value="查询" type="submit"></td>
						<td>
							<div class="tips-table">
								&nbsp;&nbsp;<span>鼠标移动到 <span class="tips-table-key">“公司名称”</span>可以查看企业详细信息
								</span>
							</div>
						</td>
						<td width="80px;"></td>
						<td><a href="StudentAction_showMyApply.action"
							class="btn btn-primary btn2" style="">查看我的申请</a></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<table class="table table-hover table-condensed table-striped">
		<colgroup>
			<col width="2%" />
			<col width="8%" />
			<col width="8%" />
			<col width="8%" />
			<col width="9%" />
			<col width="8%" />
			<col width="8%" />
			<col width="8%" />
			<col width="8%" />
		</colgroup>
		<thead>
			<tr height="100">
				<th><input id="userSelAllBtn" type="checkbox" /></th>
				<th width="400">公司编码</th>
				<th width="150">公司名称</th>
				<th width="100">公司地址</th>
				<th width="100">所属行业</th>
				<th width="100">类型</th>
				<th width="100">联系人</th>
				<th width="100">电话号码</th>
				<th width="150">操作</th>
			</tr>
		</thead>
		<s:iterator value="companyinfoVOs">
			<tbody>
				<tr height="100">
					<td><input type="checkbox" name="chk1"
						value="<s:property value="companyNum" />" /></td>
					<td><s:property value="companyNum" /></td>
					<td><a href="#" class="tooltipcontent" data-toggle="tooltip"
						title="<s:property value="companyDescription" />"><s:property
								value="companyName" /></a></td>
					<td><s:property value="companyAddress" /></td>
					<td><s:property value="companyIndustry" /></td>
					<td><s:property value="companyType" /></td>
					<td><s:property value="companyContacts" /></td>
					<td><s:property value="companyPhone" /></td>
					<td><button type="button" class="btn btn-info"
							onclick="apply(this);">申请</button></td>
				</tr>
			</tbody>
		</s:iterator>
	</table>
<s:if test="#request.Pagecount!=null&&#request.Pagecount!=0">
	<div class="page">
		<s:url id="url_pre" value="StudentAction_searchCompany.action">
			<s:param name="pageNow" value="pageNow-1"></s:param>
			<s:param name="searchsort" value="searchsort"></s:param>
			<s:param name="searname" value="searname"></s:param>
		</s:url>

		<s:url id="url_next" value="StudentAction_searchCompany.action">
			<s:param name="pageNow" value="pageNow+1"></s:param>
			<s:param name="searchsort" value="searchsort"></s:param>
			<s:param name="searname" value="searname"></s:param>
		</s:url>
		<s:url id="url" value="StudentAction_searchCompany.action">
			<s:param name="pageNow" value="1" />
			<s:param name="searchsort" value="searchsort"></s:param>
			<s:param name="searname" value="searname"></s:param>
		</s:url>
			<s:url id="last_url" value="StudentAction_searchCompany.action">
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
	<script src="<%=basePath%>resource/js/layer.min.js"></script>
	<script src="<%=basePath%>resource/js/hplus.min.js?v=4.1.0"></script>
	<script type="text/javascript"
		src="<%=basePath%>resource/js/contabs.min.js"></script>
	<script src="<%=basePath%>resource/js/pace.min.js"></script>
	<script type="text/javascript">
		function checkApplyjob(){
			if (confirm("确定要申请该公司的实习岗位吗？")) {
				return true;
			} else {
				return false;
			}
		}
		function apply(obj) {
			if(checkApplyjob()){
			var tds = $(obj).parent().parent().find('td');
			var companyNum = tds.eq(1).text();
			var companyName = tds.eq(2).text();
			$.ajax({
						"type" : "post",
						"contentType" : "application/x-www-form-urlencoded;charset=utf-8",
						url : 'StudentAction_applyJob.action',
						data : {
							"companyNum" : companyNum,
							"companyName" : companyName
						},
						success : function(data) {
							if (data != null) {
								if (data != "") {
									alert(data);
								}
								location.reload();
							} else {
								return null;
							}
						}
					});
			}
		}
	</script>
</body>
</html>
