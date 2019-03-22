<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>产品列表</title>
<meta charset="utf-8"/>
<link rel="stylesheet" href="css/bootstrap.css"/> 
</head>
<body>
<div class="container">

	<%@include file="header.jsp" %>
	
	<br>
		
	<table class="table table-bordered table-hover">

		<tr>
			<th width="5%">ID</th>
			<th width="10%">封面</th>
			<th width="10%">名称</th>
			<th width="10%">价格</th>
			<th width="10%">数量</th>
			<th width="10%">类目</th>
		</tr>
		
		<c:forEach var="item" items="${itemList}">
	         <tr>
	         	<td><p>${item.id}</p></td>
	         	<td><p><a href="../index/detail.action?productid=${item.product.id}" target="_blank"><img src="../${item.product.cover}" width="100px"></a></p></td>
	         	<td><p><a href="../index/detail.action?productid=${item.product.id}" target="_blank">${item.product.name}</a></p></td>
	         	<td><p>${item.price}</p></td>
	         	<td><p>${item.amount}</p></td>
	         	<td><p>${item.product.category.name}</p></td>
	       	</tr>
	     </c:forEach>
	     
	</table>

	<br>${pageHtml}<br>
</div>

</body>
</html>