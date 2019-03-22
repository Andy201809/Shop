/**
 * 加入购物车
 */
function buy(productid){
	$.post("buy.action", {productid:productid}, function(data){
		if(data=="ok"){
			layer.msg("加入成功!");
//			location.reload();
			$("#span_amount").text(parseInt($("#span_amount").text()) + 1);
		}else if(data=="login"){
			alert("请登录后购买!");
			location.href="login.jsp";
		}else if(data=="empty"){
			alert("库存不足!");
			location.reload();
		}else{
			alert("请求失败!");
		}
	});
}
/**
 * 购物车增加
 */
function add(shopcartid){
	$.post("add.action", {shopcartid:shopcartid}, function(data){
		if(data=="ok"){
			layer.msg("操作成功!");
			location.href="cart.action";
		}else if(data=="login"){
			alert("请登录后操作!");
			location.href="login.jsp";
		}else{
			alert("请求失败!");
		}
	});
}
/**
 * 购物车减去
 */
function lessen(shopcartid){
	$.post("lessen.action", {shopcartid:shopcartid}, function(data){
		if(data=="ok"){
			layer.msg("操作成功!");
			location.href="cart.action";
		}else if(data=="login"){
			alert("请登录后操作!");
			location.href="login.jsp";
		}else{
			alert("请求失败!");
		}
	});
}
/**
 * 购物车删除
 */
function deletes(shopcartid){
	$.post("delete.action", {shopcartid:shopcartid}, function(data){
		if(data=="ok"){
			layer.msg("删除成功!");
			location.reload();
		}else if(data=="login"){
			alert("请登录后操作!");
			location.href="login.jsp";
		}else{
			alert("请求失败!");
		}
	});
}