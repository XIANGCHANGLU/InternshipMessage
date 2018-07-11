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
<script src="<%=basePath%>/resource/ckeditor/ckeditor.js"></script>
</head>
<body>
	<br>
	<br>
	<div class="container">
		<s:iterator value="weeklyreportVOs">
				<textarea name="weeklyreportVO.reportContent" id="content">
     		<s:property value="reportContent" />
    	 </textarea>
				<br>
				<script type="text/javascript">
				CKEDITOR.replace('weeklyreportVO.reportContent');
			</script>
				<table>
					<tr>
						<td><button type="button"
								class="btn btn-primary btn2"
								onclick="update(<s:property value="reportID" />)">提交</button></td>
						<td width="130px" align="right"><button type="button"
								class="btn btn-primary btn2"
								onclick="updateNote(<s:property value="reportID" />)">保存为草稿</button></td>
						<td><div class="tips-table"
								style="font-size: 5px; margin-left: 15%;">
								&nbsp;&nbsp;<span>*填写后提交或者保存，注意</span> <span
									class="tips-table-key">“书写格式”，</span> <span>第一行为标题居中大小为24号，第二行开始填写周记内容</span>
							</div></td>
					</tr>

				</table>
		</s:iterator>
	</div>
</body>
<script src="<%=basePath%>resource/js/jquery.min.js?v=2.1.4"></script>
<script src="<%=basePath%>resource/js/bootstrap.min.js?v=3.3.6"></script>
<script src="<%=basePath%>resource/js/jquery.metisMenu.js"></script>
<script src="<%=basePath%>resource/js/jquery.slimscroll.min.js"></script>
<script type="text/javascript">
	function update(a){
		var content = CKEDITOR.instances.content.getData();
		$.ajax({
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded;charset=utf-8",
			url : 'JournalAction_SubmitDraft.action',
			data : {
				"reportID"		:a,
				"reportContent" : content
			},
			success : function(data) {
				alert("提交成功，等待老师审核！");
				window.location.href='JournalAction_showJournal.action';
			},
			error : function(e) {
				alert("系统出现异常请稍候再试");
			}
		});
	}
	function updateNote(a){
		var content = CKEDITOR.instances.content.getData();
		$.ajax({
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded;charset=utf-8",
			url : 'JournalAction_updateDraft.action',
			data : {
				"reportID"		:a,
				"reportContent" : content
			},
			success : function(data) {
				alert("保存为草稿，请尽快提交！");
				window.location.href='JournalAction_showJournal.action';
			},
			error : function(e) {
				alert("系统出现异常请稍候再试");
			}
		});
	}
</script>
</html>
