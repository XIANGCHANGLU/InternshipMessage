<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加用户信息</title>
</head>
<body>
	<form class="form-horizontal" method="post" action="testAction_insertTest.action">
        <div class="control-group">
            <label class="control-label" for="username">用户名：</label>

            <div class="controls">
                <input id="username" name="username" type="text" placeholder="用户名" class="span8">
                <span id="usernameTips" class="help-inline hide"></span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="nickname">昵称：</label>

            <div class="controls">
                <input id="nickname" name="nickname" type="text" placeholder="昵称" class="span8">
                <span id="nikenameTips" class="help-inline hide"></span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="mailbox">邮箱：</label>

            <div class="controls">
                <input id="mailbox" name="mailbox" type="text" placeholder="邮箱" class="span8">
                <span class="help-inline hide"></span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="telephone">手机：</label>

            <div class="controls">
                <input id="telephone" name="telephone" type="text" placeholder="手机" class="span8">
                <span class="help-inline hide"></span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="address">家庭住址：</label>
	   <div class="controls">
                <input id="address" name="address" type="text" placeholder="家庭住址" class="span8">
                <span class="help-inline hide"></span>
            </div>
          </div>
        <div class="control-group">
            <label class="control-label" for="remark">备注：</label>

            <div class="controls">
                <input id="remark" name="remark" type="text" placeholder="备注" class="span8">
            </div>
        </div>
        <div class="">
            <button id="submit" type="submit" class="btn btn-inverse">新增</button>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <button type="reset" class="btn">重置</button>
        </div>
    </form>
    
</body>
</html>