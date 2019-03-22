<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>购物车</title>
</head>
<body>

<div class="wrap">

<s:action name="header" executeResult="true"/>

<div class="main">
    <div class="content">
    	<div class="login_panel"  style="width:600px">	
        	<span style="color:red;"><s:actionmessage/></span>
        	
        	<s:if test="indent != null">
	        	
				<h3>收货信息</h3>
				
				<form action="prepay.action" method="post" id="payform">
				
					<input type="hidden" name="indentid" value="${indent.id}">
					<input type="hidden" name="paytype" id="paytype">
				
					<table class="cart_table">
						<tr>
		          			<td colspan="3">电话: <input type="text" name="user.phone" value="${user.phone}" id="fphone" style="width:80%;"></td>
		          		</tr>
						<tr>
		          			<td colspan="3">地址: <input type="text" name="user.address" value="${user.address}" id="faddress" style="width:80%;"></td>
		          		</tr>
		        	</table>
	        	</form>
	        	
	        	<br><br>
	        	
				<h3>订单支付</h3>
				
				<table class="cart_table">
				
					<tr>
	          			<td>订单号: ${indent.id}</td>
	          			<td><s:date name="indent.systime" format="yyyy-MM-dd HH:mm:ss"/></td>
			            <td><span class="red">总价: ￥${indent.total}</span></td>
	          		</tr>
				
					<tr class="cart_title">
						<td>
							<a href="javascript:dopay(1);"><img src="images/wechat.jpg" alt="微信支付"></a>
						</td>
						<td>
							<a href="javascript:dopay(2);"><img src="images/alipay.jpg" alt="支付宝支付"></a>
						</td>
						<td>
							<a href="javascript:dopay(3);"><img src="images/offline.jpg" alt="货到付款"></a>
						</td>
					</tr>	
					
	        	</table>
	        	
	        </s:if>
	            
            <div class="clear"></div>
		</div>
       <div class="clear"></div>
    </div>
 </div>

<jsp:include page="footer.jsp"/>

</div>

<script type="text/javascript">
	function dopay(paytype){
		// 信息校验
		/* var name = $("#fname").val();
		if(name==null || name==""){
			layer.msg("请正确填写收货人!");
			return;
		} */
		var phone = $("#fphone").val();
		if(phone==null || phone=="" || !/^[0-9]*$/.test(phone)){
			layer.msg("请正确填写收货电话!");
			return;
		}
		var address = $("#faddress").val();
		if(address==null || address==""){
			layer.msg("请正确填写收货地址!");
			return;
		}
		
		$("#paytype").val(paytype);
		$("#payform").submit();
	}
</script>

</body>
</html>