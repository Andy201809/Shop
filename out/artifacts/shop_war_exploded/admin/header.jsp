<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
</head>
<body>

	<nav class="navbar navbar-default" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand active" href="index.action">管理后台</a>
			</div>
			<div>
				<ul class="nav navbar-nav">
					<li <c:if test="${flag==1}">class="active"</c:if>><a href="indentList.action">订单管理</a></li>
					<li <c:if test="${flag==2}">class="active"</c:if>><a href="categoryList.action">类目管理</a></li>
					<li <c:if test="${flag==3}">class="active"</c:if>><a href="productList.action">商品管理</a></li>
					<li <c:if test="${flag==4}">class="active"</c:if>><a href="stockList.action">库存管理</a></li>
					<li <c:if test="${flag==5}">class="active"</c:if>><a href="userList.action">用户管理</a></li>
					<li <c:if test="${flag==6}">class="active"</c:if>><a href="adminList.action">管理员</a></li>
					<li><a href="logout.action">退出</a></li>
				</ul>
			</div>
		</div>
	</nav>

</body>
</html>