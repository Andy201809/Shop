package com.action;

import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.entity.Indent;
import com.entity.Product;
import com.entity.Shopcart;
import com.entity.Users;
import com.service.IndentService;
import com.service.ProductService;
import com.service.ShopcartService;
import com.service.UserService;
import com.util.SafeUtil;

@Namespace("/index")
@Results({
	@Result(name="login",location="/index/login.jsp"),
	@Result(name="register",location="/index/register.jsp"),
	@Result(name="reindex",type="redirect",location="index.action"),
	@Result(name="cart",location="/index/cart.jsp"),
	@Result(name="recart",type="redirect",location="cart.action"),
	@Result(name="order",location="/index/order.jsp"),
	@Result(name="topay",type="redirect",location="topay.action?indentid=${indentid}"),
	@Result(name="pay",location="/index/pay.jsp"),
	@Result(name="my",location="/index/my.jsp"),
	@Result(name="prepay",location="/index/prepay.jsp"),
})
public class UserAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	
	private int type; // 标记
	private int flag; // 页面标记
	private int productid; // 商品id
	private int shopcartid;
	private int indentid;
	private int paytype;
	private Indent indent;
	private Users user; // 用户实体
	private List<Indent> indentList; // 订单列表
	
	@Resource
	private UserService userService;
	@Resource
	private IndentService indentService;
	@Resource
	private ProductService productService;
	@Resource
	private ShopcartService shopcartService;

	
	/**
	 * 注册用户
	 * @return
	 */
	@Action("register")
	public String register(){
		if (user.getUsername().isEmpty()) {
			addActionMessage("用户名不能为空!");
			return "register";
		}else if (userService.isExist(user.getUsername())) {
			addActionMessage("用户名已存在!");
			return "register";
		}else {
			userService.add(user);
			addActionError("注册成功, 请登录!");
			return "login";
		}
	}
	
	/**
	 * 用户登录
	 * @return
	 * @throws Exception
	 */
	@Action("login")
	public String login() {
		if(userService.checkUser(user.getUsername(), user.getPassword())){
			getSession().setAttribute("user", userService.get(user.getUsername()));
			buildShopcart(getSession());
			return "reindex";
		} else {
			addActionError("用户名或密码错误!");
			return "login";
		}
	}

	/**
	 * 注销登录
	 * @return
	 */
	@Action("logout")
	public String logout() {
		getSession().removeAttribute("user");
		getSession().removeAttribute("_amount");
		getSession().removeAttribute("_amount");
		return "login";
	}
	
	/**
	 * 查看购物车
	 * @return
	 */
	@Action("cart")
	public String cart() {
		Users user = (Users) getSession().getAttribute("user");
		if (user == null) {
			addActionError("请先登录!");
			return "login";
		}
		getSession().setAttribute("shopcartList", shopcartService.getList(user.getId()));
		return "cart";
	}
	
	/**
	 * 购买
	 * @return
	 */
	@Action("buy")
	public void buy(){
		Users user = (Users) getSession().getAttribute("user");
		if (user == null) {
			sendResponseMsg("login");
			return;
		}
		Product product = productService.get(productid);
		if (product .getStock() <= 0) { // 库存不足
			sendResponseMsg("empty");
			return;
		}
		shopcartService.add(product, user); // 存入购物车表
		buildShopcart(getSession()); // 刷新购物车session数据
		sendResponseMsg("ok"); 
	}
	
	/**
	 * 增多
	 */
	@Action("add")
	public void add(){
		Shopcart shopcart = shopcartService.get(shopcartid);
		shopcart.setAmount(shopcart.getAmount() + 1);
		shopcartService.update(shopcart);
		buildShopcart(getSession()); // 刷新购物车session数据
		sendResponseMsg("ok");
	}
	
	/**
	 * 减少
	 */
	@Action("lessen")
	public void lessen(){
		Shopcart shopcart = shopcartService.get(shopcartid);
		if(shopcart.getAmount() <= 1) {
			this.delete();
			return;
		}
		shopcart.setAmount(shopcart.getAmount() - 1);
		shopcartService.update(shopcart);
		buildShopcart(getSession()); // 刷新购物车session数据
		sendResponseMsg("ok");
	}
	
	/**
	 * 删除
	 */
	@Action("delete")
	public void delete(){
		Shopcart shopcart = shopcartService.get(shopcartid);
		shopcartService.delete(shopcart);
		buildShopcart(getSession()); // 刷新购物车session数据
		sendResponseMsg("ok");
	}
	
	
	/**
	 * 提交订单
	 * @return
	 */
	@Action("save")
	public String save(){
		Users user = (Users) getSession().getAttribute("user");
		if (user == null) {
			addActionError("请登录后提交订单!");
			return "login";
		}
		
		int amount = 0, total = 0;
		List<Shopcart> shopcartList = shopcartService.getList(user.getId());
		if (Objects.nonNull(shopcartList) && !shopcartList.isEmpty()) {
			for(Shopcart cart : shopcartList) {
				Product product = cart.getProduct();
				if(Objects.nonNull(product)) {
					if(cart.getAmount() > product.getStock()){
						addActionMessage("商品 ["+product.getName()+"] 库存不足! 当前库存数量: "+product.getStock());
						getSession().setAttribute("shopcartList", shopcartList);
						return "cart";
					}
					total += product.getPrice();
				}
				amount += cart.getAmount();
			}
			Indent indent = new Indent();
			indent.setUser(user);
			indent.setAmount(amount);
			indent.setTotal(total);
			indentid = indentService.saveIndent(indent, shopcartList);	// 保存订单
			shopcartService.clean(user.getId());
			getSession().removeAttribute("_amount");
			getSession().removeAttribute("_total");
			return "topay";
		}
		addActionMessage("购物车没有商品!");
		return "cart";
	}
	
	/**
	 * 支付
	 * @return
	 */
	@Action("topay")
	public String topay() {
		Users user = (Users) getSession().getAttribute("user");
		if (user == null) {
			addActionError("请先登录!");
			return "login";
		}
		indent = indentService.get(indentid);
		return "pay";
	}
	
	/**
	 * 支付
	 * @return
	 */
	@Action("pay")
	public String pay() {
		Users su = (Users) getSession().getAttribute("user");
		if (su == null) {
			addActionError("请先登录!");
			return "login";
		}
		if(paytype==3) { // 货到付款
			indentService.pay(indentid, paytype);
			addActionMessage("提交成功");
			return "pay";
		}else {
			Users u = userService.get(su.getId());
			if(Objects.nonNull(user) && SafeUtil.encode(user.getPayword()).equals(u.getPayword())){
				indentService.pay(indentid, paytype);
				addActionMessage("支付成功");
				return "pay";
			}else {
				addActionMessage("支付密码错误!");
				return "prepay";
			}
		}
//		indentService.pay(indentid, paytype);
//		if(paytype==3) { // 货到付款
//			addActionMessage("提交成功");
//		}else {
//			addActionMessage("支付成功");
//		}
//		return "pay";
	}
	
	/**
	 * 支付
	 * @return
	 */
	@Action("prepay")
	public String prepay() {
		Users su = (Users) getSession().getAttribute("user");
		if (su == null) {
			addActionError("请先登录!");
			return "login";
		}
		Users u = userService.get(su.getId());
		if(!u.getPhone().equals(user.getPhone()) || !u.getAddress().equals(user.getAddress())) {
			u.setPhone(user.getPhone());
			u.setAddress(user.getAddress());
			userService.update(u);  // 更新数据库
			getSession().setAttribute("user", u); // 更新session
		}
		return "prepay";
	}
	
	
	/**
	 * 查看订单
	 * @return
	 */
	@Action("order")
	public String order(){
		Users user = (Users) getSession().getAttribute("user");
		if (user == null) {
			addActionError("请登录后查看订单!");
			return "login";
		}
		indentList = indentService.getListByUserid(user.getId());
		if (indentList!=null && !indentList.isEmpty()) {
			for(Indent indent : indentList){
				indent.setItemList(indentService.getItemList(indent.getId(), 1, 100)); // 暂不分页
			}
		}
		flag = 7;
		return "order";
	}
	
	
	/**
	 * 个人信息
	 * @return
	 */
	@Action("my")
	public String my(){
		flag = 6;
		Users userLogin = (Users) getSession().getAttribute("user");
		if (userLogin == null) {
			addActionError("请先登录!");
			return "login";
		}
		
		if(type == 101) { // 修改个人信息
			Users u = userService.get(user.getId());
			u.setPhone(user.getPhone());
			u.setAddress(user.getAddress());
			userService.update(u);  // 更新数据库
			getSession().setAttribute("user", u); // 更新session
			addActionMessage("保存信息成功!");
		}else if (type == 102) { // 修改登录密码
			Users u = userService.get(user.getId());
			if(SafeUtil.encode(user.getPassword()).equals(u.getPassword())){
				u.setPassword(SafeUtil.encode(user.getPasswordNew()));
				userService.update(u);  // 更新数据库
				addActionMessage("修改登录密码成功!");
			}else {
				addActionMessage("原登录密码错误!");
			}
		}else if (type == 103) { // 修改支付密码
			Users u = userService.get(user.getId());
			if(SafeUtil.encode(user.getPayword()).equals(u.getPayword())){
				u.setPayword(SafeUtil.encode(user.getPasswordNew()));
				userService.update(u);  // 更新数据库
				addActionMessage("修改支付密码成功!");
			}else {
				addActionMessage("原支付密码错误!");
			}
		}
	
		return "my";
	}
	

	/**
	 * 构建购物车数据
	 * @param session
	 */
	private void buildShopcart(HttpSession session) {
		int amount = 0; 
		float total = 0;
		Users user = (Users) session.getAttribute("user");
		List<Shopcart> shopcartList = shopcartService.getList(user.getId());
		if (Objects.nonNull(shopcartList) && !shopcartList.isEmpty()) {
			for(Shopcart cart : shopcartList) {
				Product product = cart.getProduct();
				if(Objects.nonNull(product)) {
					cart.setTotal(product.getPrice() * cart.getAmount());
					total += cart.getTotal();
					amount += cart.getAmount();
				}
			}
		}
		session.setAttribute("_amount", amount);
		session.setAttribute("_total", total);
	}

	
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public int getProductid() {
		return productid;
	}

	public void setProductid(int productid) {
		this.productid = productid;
	}

	public List<Indent> getIndentList() {
		return indentList;
	}

	public void setIndentList(List<Indent> indentList) {
		this.indentList = indentList;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getShopcartid() {
		return shopcartid;
	}

	public void setShopcartid(int shopcartid) {
		this.shopcartid = shopcartid;
	}

	public int getIndentid() {
		return indentid;
	}

	public void setIndentid(int indentid) {
		this.indentid = indentid;
	}

	public Indent getIndent() {
		return indent;
	}

	public void setIndent(Indent indent) {
		this.indent = indent;
	}

	public int getPaytype() {
		return paytype;
	}

	public void setPaytype(int paytype) {
		this.paytype = paytype;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}