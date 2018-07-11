<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>

<head>
<base href="<%=basePath%>">

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>学生实习信息管理系统</title>
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->

<link rel="shortcut icon" href="resource/images/favicon.ico">
<link href="resource/css/admin/bootstrap.min.css" rel="stylesheet">
<link href="resource/css/admin/font-awesome.min.css" rel="stylesheet">
<link href="resource/css/admin/animate.min.css" rel="stylesheet">
<link href="resource/css/admin/style.min.css?v=4.1.0" rel="stylesheet">
</head>

<body class="fixed-sidebar full-height-layout gray-bg"
	style="overflow: hidden">
	<div id="wrapper">
		<!--左侧导航开始-->
		<nav class="navbar-default navbar-static-side" role="navigation">
			<div class="nav-close">
				<i class="icon-remove-circle"></i>
			</div>
			<div class="sidebar-collapse">
				<ul class="nav" id="side-menu">
					<li class="nav-header">
						<div class="dropdown profile-element">
							<span><img alt="image" class="img-circle"
								src="resource/images/profile_small.jpg" style="width:64px;height:64px;"/> </span> <a
								data-toggle="dropdown" class="dropdown-toggle" href="#"> <span
								class="clear"> <span class="block m-t-xs"><strong
										class="font-bold"><%=session.getAttribute("username")%></strong>
								</span> <span class="text-muted text-xs block"> 欢迎你<%=session.getAttribute("indentity")%>
										<b class="caret"></b>
								</span>
							</span>
							</a>
						</div>
						<div class="logo-element">IM</div>
					</li>
					<%
						if((String) session.getAttribute("indentity")!=null){
						String indentity = (String) session.getAttribute("indentity");
						if (indentity.equals("管理员")) {
					%>
					<li><a class="J_menuItem" href="index_v1.jsp"> <i class="icon-home"></i> <span
							class="nav-label">主页</span> <span class="icon arrow"></span>
					</a></li>
					<li><a href="#"> <i class="icon-user"></i> <span
							class="nav-label">职工管理</span> <span class="icon arrow"></span>
					</a>
						<ul class="nav nav-second-level">
							<li><a class="J_menuItem" href="Web/admin/addTeacher.jsp">添加教师</a></li>
							<li><a class="J_menuItem" href="Web/admin/showTeacher.jsp">编辑教师</a></li>
						</ul></li>

					<li><a href="#"><i class="icon-envelope"></i> <span
							class="nav-label">学生管理 </span> </a>
						<ul class="nav nav-second-level">
							<li><a class="J_menuItem" href="Web/admin/addStudent.jsp">添加学生</a></li>
							<li><a class="J_menuItem" href="Web/admin/showStudent.jsp">编辑学生</a></li>
						</ul></li>
					<li><a href="#"><i class="icon-edit"></i> <span
							class="nav-label">企业管理</span><span class="icon arrow"></span> </a>
						<ul class="nav nav-second-level">
							<li><a class="J_menuItem" href="Web/admin/addCompany.jsp">添加企业</a></li>
							<li><a class="J_menuItem" href="Web/admin/showCompany.jsp">编辑企业</a></li>
						</ul></li>
					<li><a href="#"><i class="icon-desktop"></i> <span
							class="nav-label">成绩管理</span><span class="icon arrow"></span> </a>
						<ul class="nav nav-second-level">
							<li><a class="J_menuItem" href="Web/admin/showInternshipScore.jsp">成绩查询<span
									class="icon arrow"></span>
							</a></li>
							<li><a class="J_menuItem" href="AdminAction_countScoreLevel.action">信息分析<span
									class="icon arrow"></span>
							</a></li>
						</ul></li>
				</ul>
			</div>
			<%
				} else if (indentity.equals("学生")) {
			%>
			<li><a class="J_menuItem" href="index_v1.jsp"> <i class="icon-home"></i> <span
					class="nav-label">主页</span> <span class="icon arrow"></span>
			</a></li>
			<li><a href="#"> <i class="icon-user"></i> <span
					class="nav-label">实习管理</span> <span class="icon arrow"></span>
			</a>
				<ul class="nav nav-second-level">
					<li><a class="J_menuItem"
						href="AdminAction_showPersonalData.action">查看个人资料</a></li>
					<li><a class="J_menuItem" href="Web/student/applyJob.jsp">申请实习单位</a></li>
				</ul></li>
			<li><a href="#"><i class="icon-envelope"></i> <span
					class="nav-label">周报管理 </span> </a>
				<ul class="nav nav-second-level">
					<li><a class="J_menuItem" href="Web/student/weeklyJournal.jsp">填写周报</a></li>
					<li><a class="J_menuItem"
						href="JournalAction_showJournal.action">查看递交状态</a></li>
				</ul></li>
			<li><a href="#"><i class="icon-edit"></i> <span
					class="nav-label">毕业论文上传</span><span class="icon arrow"></span> </a>
				<ul class="nav nav-second-level">
					<li><a class="J_menuItem" href="Web/student/uploadThesis.jsp">上传毕业论文</a></li>
				</ul></li>

			<%
				} else if (indentity.equals("教师")) {
			%>
	<li><a class="J_menuItem" href="index_v1.jsp"> <i class="icon-home"></i> <span
					class="nav-label">主页</span> <span class="icon arrow"></span>
			</a></li>
			<li><a href="#"> <i class="icon-user"></i> <span
					class="nav-label">学生管理</span> <span class="icon arrow"></span>
			</a>
				<ul class="nav nav-second-level">
					<li><a class="J_menuItem"
						href="TeacherAction_showPersonalData.action">查看个人资料</a></li>
					<li><a class="J_menuItem" href="Web/teacher/showStudent.jsp">查看所带学生</a></li>
				</ul></li>
			<li><a href="#"><i class="icon-envelope"></i> <span
					class="nav-label">成绩管理</span> </a>
				<ul class="nav nav-second-level">
					<li><a class="J_menuItem" href="ApplyAction_ShowStudent.action">学生评分</a></li>
					<li><a class="J_menuItem"
						href="TeacherAction_showScore.action">查看学生成绩</a></li>
				</ul></li>
			<li><a href="#"><i class="icon-edit"></i> <span
					class="nav-label">周报管理</span><span class="icon arrow"></span> </a>
				<ul class="nav nav-second-level">
					<li><a class="J_menuItem" href="Web/teacher/reportList.jsp">审批周报</a></li>
					</ul></li>
					<li><a href="#"><i class="icon-edit"></i> <span
					class="nav-label">企业反馈</span><span class="icon arrow"></span> </a>
				<ul class="nav nav-second-level">
					<li><a class="J_menuItem" href="CompanyAction_showEvaStu.action">反馈信息</a></li>
				</ul></li>
									<li><a href="#"><i class="icon-edit"></i> <span
					class="nav-label">毕业论文管理</span><span class="icon arrow"></span> </a>
				<ul class="nav nav-second-level">
					<li><a class="J_menuItem" href="Web/teacher/FileDownload.jsp">毕业论文下载</a></li>
				</ul></li>
			<%
				}else if (indentity.equals("企业")) {
					%>
					<li><a class="J_menuItem" href="index_v1.jsp"> <i class="icon-home"></i> <span
									class="nav-label">主页</span> <span class="icon arrow"></span>
							</a></li>
							<li><a href="#"> <i class="icon-user"></i> <span
									class="nav-label">实习生管理</span> <span class="icon arrow"></span>
							</a>
								<ul class="nav nav-second-level">
									<li><a class="J_menuItem"
										href="CompanyAction_showCompanyInfo.action">企业资料</a></li>
									<li><a class="J_menuItem" href="Web/company/showStudent.jsp">查看公司实习生</a></li>
								</ul></li>
							<li><a href="#"><i class="icon-envelope"></i> <span
									class="nav-label">评价管理</span> </a>
								<ul class="nav nav-second-level">
									<li><a class="J_menuItem" href="CompanyAction_showComwithStu.action">点评学生</a></li>
									<li><a class="J_menuItem" href="CompanyAction_showEvaStus.action">评价反馈</a></li>
								</ul></li>
							<li><a href="#"><i class="icon-edit"></i> <span
									class="nav-label">职位管理</span><span class="icon arrow"></span> </a>
								<ul class="nav nav-second-level">
									<li><a class="J_menuItem" href="ApplyAction_queryApplyList.action">审批实习</a></li>
								</ul></li>
							<%
								}
						}else{
							response.sendRedirect("login.html");
						}
							%>
		</nav>
		<!--左侧导航结束-->
		<!--右侧部分开始-->
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<div class="row border-bottom">
				<nav class="navbar navbar-static-top" role="navigation"
					style="margin-bottom: 0">
					<div class="navbar-header">
						<a class="navbar-minimalize minimalize-styl-2 btn btn-primary "
							href="#"><i class="icon-align-left"></i> </a>
						<form role="search" class="navbar-form-custom" method="post"
							action="#">
							<div class="form-group">
								<input type="text" placeholder="请输入您需要查找的内容 …"
									class="form-control" name="top-search" id="top-search">
							</div>
						</form>
					</div>
				</nav>
			</div>
			<div class="row content-tabs">
				<button class="roll-nav roll-left J_tabLeft">
					<i class="icon-backward"></i>
				</button>
				<nav class="page-tabs J_menuTabs">
					<div class="page-tabs-content">
						<a href="javascript:;" class="active J_menuTab"
							data-id="index_v1.jsp">首页</a>
					</div>
				</nav>
				<button class="roll-nav roll-right J_tabRight">
					<i class="icon-forward"></i>
				</button>
				<div class="btn-group roll-nav roll-right">
					<button class="dropdown J_tabClose" data-toggle="dropdown">
						关闭操作<span class="caret"></span>

					</button>
					<ul role="menu" class="dropdown-menu dropdown-menu-right">
						<li class="J_tabShowActive"><a>定位当前选项卡</a></li>
						<li class="divider"></li>
						<li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
						<li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
					</ul>
				</div>
				<a href="RmSessionAction_removeSession.action" class="roll-nav roll-right J_tabExit"><i
					class="icon-signout"></i> 退出</a>
			</div>
			<div class="row J_mainContent" id="content-main">
				<iframe class="J_iframe" name="iframe0" width="100%" height="100%"
					src="index_v1.jsp" frameborder="0" data-id="index_v1.jsp" seamless></iframe>
			</div>
			<div class="footer">
				<div class="pull-right">
					&copy; 2016 <a href="#" target="_blank">广州大学华软软件学院</a>
				</div>
			</div>
		</div>
		<!--右侧部分结束-->
		<!--右侧边栏开始-->
		<div id="right-sidebar">
			<div class="sidebar-container"></div>
		</div>

	</div>



	<script src="resource/js/jquery.min.js"></script>
	<script src="resource/js/bootstrap.min.js"></script>
	<script src="resource/js/jquery.metisMenu.js"></script>
	<script src="resource/js/jquery.slimscroll.min.js"></script>
	<script src="resource/js/layer.min.js"></script>
	<script src="resource/js/hplus.min.js"></script>
	<script type="text/javascript" src="resource/js/contabs.min.js"></script>
	<script src="resource/js/pace.min.js"></script>
	<script type="text/javascript">
    $(document).ready(function(){               //加载完页面后执行
    	var indentity = '<%=(String) session.getAttribute("indentity")%>';
		if(indentity==="学生"){
			$(".img-circle").attr("src","resource/images/kedaya.jpg");
		}else if(indentity==="教师"){
			$(".img-circle").attr("src","resource/images/cat.jpg");
		}else if(indentity==="企业"){
			$(".img-circle").attr("src","resource/images/bika.jpg");
		}else if(indentity==="管理员"){
			$(".img-circle").attr("src","resource/images/bikaqiu.jpg");
		}
    });

	</script>
</body>

</html>
