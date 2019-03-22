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
			<th width="10%">名称</th>
			<th width="10%">价格</th>
			<th width="10%">库存</th>
			<th width="10%">类目</th>
			<th width="15%">操作</th>
		</tr>
		
		<c:forEach var="product" items="${productList}">
	         <tr>
	         	<td><p>${product.id}</p></td>
	         	<td><p><a href="../index/detail.action?productid=${product.id}" target="_blank">${product.name}</a></p></td>
	         	<td><p>${product.price}</p></td>
	         	<td>
	         		<c:if test="${product.stock < 10}"><p style="color:red;">${product.stock}</p></c:if>
	         		<c:if test="${product.stock >= 10}"><p>${product.stock}</p></c:if>
	         	</td>
	         	<td><p>${product.category.name}</p></td>
				<td>
					<form class="form-inline" action="stockUpdate.action" method="post" id="form${product.id}">
					  <input type="hidden" name="page" value="${page}">
					  <input type="hidden" name="status" value="${status}">
					  <input type="hidden" name="product.id" value="${product.id}">
					  <div class="form-group">
						<input type="text" class="form-control" name="product.stock" placeholder="输入库存数量">
					  </div>
					  <a href="javascript:;" onclick="document.getElementById('form${product.id}').submit();" type="submit" class="btn btn-primary">保存</a>
					</form>
				</td>
	       	</tr>
	     </c:forEach>
	     
	</table>

	<br>${pageHtml}<br>
</div>

</body>
</html>