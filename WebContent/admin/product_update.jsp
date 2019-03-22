<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>产品编辑</title>
<meta charset="utf-8" />
<link rel="stylesheet" href="css/bootstrap.css" />
</head>
<body>
<div class="container">
	<%@include file="header.jsp"%>

	<br><br>
	<form class="form-horizontal" action="productUpdate" method="post" enctype="multipart/form-data">
	
		<input type="hidden" name="page" value="${param.page}"/>
		<input type="hidden" name="status" value="${status}"/>
		<input type="hidden" name="product.id" value="${product.id}"/>
		<input type="hidden" name="product.cover" value="${product.cover}"/>

		<div class="form-group">
			<label for="input_file" class="col-sm-1 control-label">封面</label> 
			<div class="col-sm-6"><img src="../${product.cover}" class="img-responsive"/>
				<input type="file" name="photo"  id="input_file">推荐尺寸: 212 * 212
			</div>
		</div>
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">名称</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="input_name" name="product.name" value="${product.name}" required="required">
			</div>
		</div>
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">价格</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="input_name" name="product.price" value="${product.price}" required="required">
			</div>
		</div>
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">介绍</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="input_name" name="product.intro" value="${product.intro}" required="required">
			</div>
		</div>
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">库存</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="input_name" name="product.stock" value="${product.stock}" required="required">
			</div>
		</div>
		<div class="form-group">
			<label for="select_topic" class="col-sm-1 control-label">类目</label>
			<div class="col-sm-6">
				<select class="form-control" id="select_topic" name="product.category.id">
					<c:forEach var="category" items="${categoryList}">
						<c:if test="${category.id==product.category.id}"><option selected="selected" value="${category.id}">${category.name}</option></c:if>
						<c:if test="${category.id!=product.category.id}"><option value="${category.id}">${category.name}</option></c:if>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-1 col-sm-10">
				<button type="submit" class="btn btn-success">提交修改</button>
			</div>
		</div>
	</form>
</div>
</body>
</html>