<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
    <title>首页</title>
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
</head>
  <style>
  div#divImg{ 
	position:fixed; 
	top:0; 
	left:0; 
	bottom:0; 
	right:0; 
	z-index:-1; 
} 
div#divImg > img { 
	height:100%; 
	width:100%; 
	border:0; 
} 
  </style>
  <body >
  	<div id="divImg"><img src="resource/images/sisebg.jpg" /></div> 
  	
  <div class="container" style="margin-top:200px;margin-left:400px;">
    <h1>欢迎你，<%=session.getAttribute("indentity") %></h1>
    </div>
  </body>
</html>
