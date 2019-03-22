<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>重置密码</title>
<meta charset="utf-8" />
<link rel="stylesheet" href="css/bootstrap.css" />
</head>
<body>
<div class="container">
	<%@include file="header.jsp"%>

	<br><br>
	
	<form class="form-horizontal" action="userReset.action" method="post">
	
		<input type="hidden" name="page" value="${param.page}"/>

		<input type="hidden" name="user.id" value="${user.id}"/>
	
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">用户名</label>
			<div class="col-sm-6">${user.username}</div>
		</div>
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">登录密码</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="input_name" name="user.password" placeholder="留空为不修改">
			</div>
		</div>
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">支付密码</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="input_name" name="user.payword" placeholder="留空为不修改">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-1 col-sm-10">
				<button type="submit" class="btn btn-success">提交保存</button>
			</div>
		</div>
	</form>
</div>
</body>
</html>