<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>用户列表</title>
<meta charset="utf-8"/>
<link rel="stylesheet" href="css/bootstrap.css"/> 
</head>
<body>
<div class="container">

	<%@include file="header.jsp"%>
	
	<div class="text-right"><a class="btn btn-success" href="userA.action">添加顾客</a></div>
	
	<br>

	<table class="table table-bordered table-hover">

		<tr>
			<th width="5%">ID</th>
			<th width="10%">用户名</th>
			<th width="10%">电话</th>
			<th width="10%">地址</th>
			<th width="10%">操作</th>
		</tr>
		
		<c:forEach var="user" items="${userList}">
	         <tr>
	         	<td><p>${user.id}</p></td>
	         	<td><p>${user.username}</p></td>
	         	<td><p>${user.phone}</p></td>
	         	<td><p>${user.address}</p></td>
				<td>
					<a class="btn btn-info" href="userRe.action?id=${user.id}">重置密码</a>
					<a class="btn btn-primary" href="userUp.action?id=${user.id}">修改</a>
					<a class="btn btn-danger" href="userDelete.action?user.id=${user.id}">删除</a>
				</td>
	       	</tr>
	     </c:forEach>
	     
	</table>

	<br>${pageHtml}<br>

</div>
</body>
</html>