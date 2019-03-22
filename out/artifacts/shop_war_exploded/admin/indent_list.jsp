<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>订单列表</title>
<meta charset="utf-8"/>
<link rel="stylesheet" href="css/bootstrap.css"/> 
</head>
<body>
<div class="container">

	<%@include file="header.jsp"%>
	
	<ul role="tablist" class="nav nav-tabs">
        <li role="presentation" <c:if test="${status==0}">class="active"</c:if>><a href="indentList.action">全部订单</a></li>
        <li role="presentation" <c:if test="${status==1}">class="active"</c:if>><a href="indentList.action?status=1">未付款</a></li>
        <li role="presentation" <c:if test="${status==2}">class="active"</c:if>><a href="indentList.action?status=2">已付款</a></li>
        <li role="presentation" <c:if test="${status==3}">class="active"</c:if>><a href="indentList.action?status=3">配送中</a></li>
        <li role="presentation" <c:if test="${status==4}">class="active"</c:if>><a href="indentList.action?status=4">已完成</a></li>
    </ul>
	
	<br>

	<table class="table table-bordered table-hover">
	
		<tr>
			<th width="5%">ID</th>
			<th width="10%">商品数量</th>
			<th width="10%">商品总价</th>
			<th width="10%">订单状态</th>
			<th width="10%">下单用户</th>
			<th width="10%">下单时间</th>
			<th width="10%">操作</th>
		</tr>
		
		<c:forEach var="indent" items="${indentList}">
	         <tr>
	         	<td><p>${indent.id}</p></td>
	         	<td><p>${indent.amount}</p></td>
	         	<td><p>${indent.total}</p></td>
	         	<td>
	         		<c:if test="${indent.status==1}">未付款</c:if>
	         		<c:if test="${indent.status==2}">已付款</c:if>
	         		<c:if test="${indent.status==3}">配送中</c:if>
	         		<c:if test="${indent.status==4}">已完成</c:if>
	         	</td>
	         	<td><p>${indent.user.username}</p></td>
	         	<td><p>${indent.systime}</p></td>
				<td>
					<a class="btn btn-info" href="itemList?id=${indent.id}">订单详情</a>
					<c:if test="${indent.status==1}"><a class="btn btn-danger" href="indentDelete?id=${indent.id}&status=${status}&page=${page}">删除</a></c:if>
					<c:if test="${indent.status==2}"><a class="btn btn-primary" href="indentSend?id=${indent.id}&status=${status}&page=${page}">发货</a></c:if>
					<c:if test="${indent.status==3}"><a class="btn btn-success" href="indentFinish?id=${indent.id}&status=${status}&page=${page}">完成</a></c:if>
				</td>
	       	</tr>
	     </c:forEach>
	     
	</table>

	<br>${pageHtml}<br>

</div>
</body>
</html>