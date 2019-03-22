<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>产品添加</title>
<meta charset="utf-8" />
<link rel="stylesheet" href="css/bootstrap.css" />
</head>
<body>
<div class="container">
	<%@include file="header.jsp"%>

	<br><br>
	<form class="form-horizontal" action="productSaleAdd.action" method="post">
		
		<input type="hidden" name="page" value="${page}"/>
		<input type="hidden" name="status" value="${status}"/>
		<input type="hidden" name="productSale.product.id" value="${product.id}"/>
	
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">促销商品</label>
			<div class="col-sm-6">
				<img src="../${product.cover}" class="img-responsive"/>
				${product.name}
			</div>
		</div>
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">商品价格</label>
			<div class="col-sm-6">${product.price}</div>
		</div>
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">折扣比例</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="input_name" name="productSale.discount" required="required" placeholder="请输入1-100的整数">
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