<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商城首页</title>
</head>
<body>

<div class="wrap">

<s:action name="header" executeResult="true"/>

	<div class="main">
		<div class="content">
		
			<div class="content_top">
				<div class="heading">
					<h3>热销推荐</h3>
				</div>
				<div class="clear"></div>
			</div>
			
			<div class="section group">
			
				<s:iterator value="showList" status="status">
					<div class="listview_1_of_2 images_1_of_2" <s:if test="#status.count%4==1">style="margin-left: 0;"</s:if>>
						<div class="listimg listimg_2_of_1">
							 <a href="detail.action?productid=<s:property value="product.id"/>">
								<img src="../<s:property value="product.cover"/>" alt="<s:property value="product.name"/>"/>
							</a>
						</div>
						<div class="text list_2_of_1">
							<h3><a href="detail.action?productid=<s:property value="product.id"/>"><s:property value="product.name"/></a></h3>
							<p><s:property value="showtext"/></p>
							<p><span style="color:red;">￥<s:property value="product.price"/></span></p>
							<div class="button">
								<s:if test="product.stock>0"><span><a href="javascript:buy(<s:property value="product.id"/>);">加入购物车</a></span></s:if>
								<s:else><span><a style="background:#ccc;color:red;">库存不足</a></span></s:else>
							</div>
					   </div>
				   </div>	
				</s:iterator>
				
				<div class="clear"></div>
				
			</div>
		
			<div class="content_bottom">
				<div class="heading">
					<h3>特价促销</h3>
				</div>
				<div class="clear"></div>
			</div>
			
			<div class="section group">
			
				<s:iterator value="saleList">
				
					<div class="grid_1_of_4 images_1_of_4">
						 <a href="detail.action?productid=<s:property value="product.id"/>">
						 	<img src="../<s:property value="product.cover"/>" alt="<s:property value="product.name"/>" width="212px" height="212px"/>
						 </a>
						 <div class="discount">
						 	<span class="percentage"><s:property value="discount"/>%</span>
						 </div>
						 <h2><a href="detail.action?productid=<s:property value="product.id"/>"><s:property value="product.name"/></a></h2>
						 <p>
							 <span class="strike">￥<s:property value="product.price"/></span>
							 <span class="price">￥<s:property value="price"/></span>
						 </p>
					     <div class="button">
					     	<s:if test="product.stock>0"><span><a href="javascript:buy(<s:property value="product.id"/>);" class="cart-button">加入购物车</a></span></s:if> 
						    <s:else><span><a style="background:#ccc;color:red;">库存不足</a></span></s:else>
					     </div>
					     <div class="button">
					     	<span><a href="detail.action?productid=<s:property value="product.id"/>" class="details">查看详情</a></span>
					     </div>
					</div>
				
				</s:iterator>

			</div>
			
			<div class="content_bottom">
	    		<div class="heading">
	    		<h3>新品上架</h3>
	    		</div>
	    		
	    		<div class="clear"></div>
    		</div>
			<div class="section group">
			
				<s:iterator value="newList">
					<div class="grid_1_of_4 images_1_of_4">
						<a href="detail.action?productid=<s:property value="product.id"/>">
							<img src="../<s:property value="product.cover"/>" alt="<s:property value="product.name"/>" width="212px" height="212px" />
						</a>
						<h2><a href="detail.action?productid=<s:property value="product.id"/>"><s:property value="product.name"/></a></h2>
						<p><span class="price">￥<s:property value="product.price"/></span></p>
					     <div class="button">
						     <s:if test="product.stock>0"><span><a href="javascript:buy(<s:property value="product.id"/>);" class="cart-button">加入购物车</a></span></s:if> 
						     <s:else><span><a style="background:#ccc;color:red;">加入购物车</a></span></s:else>
					     </div>
					     <div class="button">
					     	<span><a href="detail.action?productid=<s:property value="product.id"/>" class="details">查看详情</a></span>
					     </div>
					</div>			
				</s:iterator>
				
			</div>
			
    	</div>
	</div>

<jsp:include page="footer.jsp"/>

</div>

</body>
</html>