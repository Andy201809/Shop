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
        	
        		<form action="pay.action" method="post">
        		<input type="hidden" name="user.id" value="${user.id}">
        		<input type="hidden" name="indentid" value="${indentid}">
        		<input type="hidden" name="paytype" value="${paytype}">
        	
				<h3>输入支付密码</h3>
				
				<table class="cart_table">
	          		<tr>
			            <td colspan="4"><input type="text" name="user.payword" required="required"></td>
	          		</tr>
	        	</table>
        	
	            <div class="buttons">
	            	<div style="float:right; padding-top: 20px">
	            		<button class="grey" >提交</button>
	            	</div>
	            </div>
	            </form>
            
            <div class="clear"></div>
		</div>
       <div class="clear"></div>
    </div>
 </div>

<jsp:include page="footer.jsp"/>

</div>

</body>
</html>