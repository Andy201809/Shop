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
	
	<div class="text-right"><a class="btn btn-success" href="productA.action">添加产品</a></div>
	
	<ul role="tablist" class="nav nav-tabs">
        <li role="presentation" <c:if test="${status==0}">class="active"</c:if>><a href="productList.action">全部产品</a></li>
        <li role="presentation" <c:if test="${status==1}">class="active"</c:if>><a href="productList.action?status=1">热销推荐</a></li>
        <li role="presentation" <c:if test="${status==2}">class="active"</c:if>><a href="productList.action?status=2">特价促销</a></li>
        <li role="presentation" <c:if test="${status==3}">class="active"</c:if>><a href="productList.action?status=3">最新上架</a></li>
    </ul>

	<br>
	
	<form class="form-inline" action="productSearch.action" id="serchform" method="post">
		<div class="form-group">
			<label for="id">ID: </label>
			<input type="text" class="form-control" id="id" name="product.id">
		</div>
		<div class="form-group">
			<label for="name">名称: </label>
			<input type="text" class="form-control" id="name" name="product.name">
		</div>
		<a href="javascript:;" onclick="document.getElementById('serchform').submit();" type="submit" class="btn btn-default">点击搜索</a>
	</form>
	
	<br>
		
	<table class="table table-bordered table-hover">

		<tr>
			<th width="5%">ID</th>
			<th width="10%">封面</th>
			<th width="10%">名称</th>
			<th width="10%">价格</th>
			<th width="20%">介绍</th>
			<th width="10%">库存</th>
			<th width="10%">类目</th>
			<th width="25%">操作</th>
		</tr>
		
		<c:forEach var="product" items="${productList}">
	         <tr>
	         	<td><p>${product.id}</p></td>
	         	<td><p><a href="../index/detail.action?productid=${product.id}" target="_blank"><img src="../${product.cover}" width="100px"></a></p></td>
	         	<td><p><a href="../index/detail.action?productid=${product.id}" target="_blank">${product.name}</a></p></td>
	         	<td><p>${product.price}</p></td>
	         	<td><p>${product.intro}</p></td>
	         	<td><p>${product.stock}</p></td>
	         	<td><p>${product.category.name}</p></td>
				<td>
					<c:if test="${product.isShow}">
						<a class="btn btn-warning" href="productShowDelete.action?id=${product.id}&page=${page}&status=${status}">取消推荐</a>
					</c:if>
					<c:if test="${!product.isShow}">
						<a class="btn btn-info" href="productShowA.action?id=${product.id}&page=${page}&status=${status}">设为推荐</a>
					</c:if>
					<c:if test="${product.isSale}">
						<a class="btn btn-warning" href="productSaleDelete.action?id=${product.id}&page=${page}&status=${status}">取消促销</a>
					</c:if>
					<c:if test="${!product.isSale}">
						<a class="btn btn-info" href="productSaleA.action?id=${product.id}&page=${page}&status=${status}">设为促销</a>
					</c:if>
					<c:if test="${product.isNew}">
						<a class="btn btn-warning" href="productNewDelete.action?id=${product.id}&page=${page}&status=${status}">取消最新</a>
					</c:if>
					<c:if test="${!product.isNew}">
						<a class="btn btn-info" href="productNewA.action?id=${product.id}&page=${page}&status=${status}">设为最新</a>
					</c:if>
					<br><br>
					<a class="btn btn-primary" href="productUp.action?id=${product.id}&page=${page}&status=${status}">修改</a>
					<a class="btn btn-danger" href="productDelete.action?product.id=${product.id}&page=${page}&status=${status}">删除</a>
				</td>
	       	</tr>
	     </c:forEach>
	     
	</table>

	<br>${pageHtml}<br>
</div>

</body>
</html>