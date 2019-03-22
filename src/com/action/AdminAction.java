package com.action;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.StringUtils;

import com.entity.Admin;
import com.entity.Category;
import com.entity.Indent;
import com.entity.Items;
import com.entity.Product;
import com.entity.ProductNew;
import com.entity.ProductSale;
import com.entity.ProductShow;
import com.entity.Users;
import com.service.AdminService;
import com.service.CategoryService;
import com.service.IndentService;
import com.service.ProductService;
import com.service.UserService;
import com.util.PageUtil;
import com.util.SafeUtil;
import com.util.UploadUtil;

@Namespace("/admin")
@Results({
	@Result(name="login",location="/admin/login.jsp"),
	@Result(name="index",location="/admin/index.jsp"),
	@Result(name="reindex",type="redirect",location="index.action"),
	@Result(name="indent",location="/admin/indent_list.jsp"),
	@Result(name="reindent",type="redirect",location="indentList.action?status=${status}&page=${page}"),
	@Result(name="item",location="/admin/item_list.jsp"),
	@Result(name="category",location="/admin/category_list.jsp"),
	@Result(name="categoryupdate",location="/admin/category_edit.jsp"),
	@Result(name="recategory",type="redirect",location="categoryList.action?page=${page}"),
	@Result(name="product",location="/admin/product_list.jsp"),
	@Result(name="productadd",location="/admin/product_add.jsp"),
	@Result(name="productupdate",location="/admin/product_update.jsp"),
	@Result(name="productaddshow",location="/admin/product_addshow.jsp"),
	@Result(name="productaddsale",location="/admin/product_addsale.jsp"),
	@Result(name="productaddnew",location="/admin/product_addnew.jsp"),
	@Result(name="reproduct",type="redirect",location="productList.action?status=${status}&page=${page}"),
	@Result(name="user",location="/admin/user_list.jsp"),
	@Result(name="useradd",location="/admin/user_add.jsp"),
	@Result(name="userreset",location="/admin/user_reset.jsp"),
	@Result(name="userupdate",location="/admin/user_update.jsp"),
	@Result(name="reuser",type="redirect",location="userList.action?page=${page}"),
	@Result(name="admin",location="/admin/admin_list.jsp"),
	@Result(name="adminadd",location="/admin/admin_add.jsp"),
	@Result(name="adminreset",location="/admin/admin_reset.jsp"),
	@Result(name="readmin",type="redirect",location="adminList.action?page=${page}"),
	@Result(name="stocklist",location="/admin/stock_list.jsp"),
	@Result(name="restocklist",type="redirect",location="stockList.action?page=${page}"),
})
class AdminAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private static final int rows = 10;
	
	@Resource
	private AdminService adminService;
	@Resource
	private IndentService indentService;
	@Resource
	private UserService userService;
	@Resource
	private ProductService productService;
	@Resource
	private CategoryService categoryService;
	
	private List<Indent> indentList;
	private List<Items> itemList;
	private List<Users> userList;
	private List<Product> productList;
	private List<Category> categoryList;
	private List<Admin> adminList;
	
	private Users user;
	private Product product;
	private ProductShow productShow;
	private ProductSale productSale;
	private ProductNew productNew;
	private Category category;
	private Admin admin;
	private int status;
	private int flag;
	private int id;
	
	private File photo;		//获取上传文件
    private String photoFileName;	//获取上传文件名称
    private String photoContentType;		//获取上传文件类型
	
	/**
	 * 管理员登录
	 * @return
	 */
    @Action("login")
	public String login() {
		if (adminService.checkUser(admin.getUsername(), admin.getPassword())) {
			getSession().setAttribute("admin", admin.getUsername());
			return "reindex";
		}
		request.setAttribute("msg", "用户名或密码错误!");
		return "login";
	}
    
    /**
     * 管理员退出
     * @return
     */
    @Action("logout")
    public String logout() {
    	getSession().removeAttribute("admin");
    	return "login";
    }
    
    /**
     * 后台首页
     * @return
     */
    @Action("index")
    public String index() {
    	request.setAttribute("msg", "欢迎来到商城管理后台");
    	return "index";
    }
	
	
	/**
	 * 订单列表
	 * @return
	 */
    @Action("indentList")
	public String indentList(){
    	flag = 1;
    	if (status > 0) {
    		indentList = indentService.getList(status, page, rows);
    		pageHtml = PageUtil.getPageToolAdmin(request, indentService.getTotal(status), page, rows);
		}else {
			indentList = indentService.getList(page, rows);
			pageHtml = PageUtil.getPageToolAdmin(request, indentService.getTotal(), page, rows);
		}
		return "indent";
	}
	
	/**
	 * 订单发货
	 * @return
	 */
    @Action("indentSend")
	public String indentSend(){
		indentService.send(id);
		return "reindent";
	}
	
	/**
	 * 订单完成
	 * @return
	 */
    @Action("indentFinish")
	public String indentFinish(){
		indentService.finish(id);
		return "reindent";
	}	
    
    /**
     * 订单删除
     * @return
     */
    @Action("indentDelete")
    public String indentDelete(){
    	indentService.delete(id);
    	return "reindent";
    }	
    
	/**
	 * 订单项列表
	 * @return
	 */
    @Action("itemList")
	public String itemList(){
    	flag = 1;
		itemList = indentService.getItemList(id, page, rows);
		pageHtml = PageUtil.getPageToolAdmin(request, indentService.getItemTotal(id), page, rows);
		return "item";
	}
    
	
	/**
	 * 类目列表
	 * @return
	 */
    @Action("categoryList")
	public String categoryList(){
    	flag = 2;
		categoryList = categoryService.getList(page, rows);
		pageHtml = PageUtil.getPageToolAdmin(request, categoryService.getTotal(), page, rows);
		return "category";
	}
	
	/**
	 * 类目添加
	 * @return
	 */
    @Action("categoryAdd")
	public String categoryAdd(){
		categoryService.add(category);
		return "recategory";
	}
	
	/**
	 * 类目更新
	 * @return
	 */
    @Action("categoryUp")
	public String categoryUp(){
    	flag = 2;
		category = categoryService.get(id);
		return "categoryupdate";
	}
	
	/**
	 * 类目更新
	 * @return
	 */
    @Action("categoryUpdate")
	public String categoryUpdate(){
		categoryService.update(category);
		return "recategory";
	}
	
	/**
	 * 类目删除
	 * @return
	 */
    @Action("categoryDelete")
	public String categoryDelete(){
		categoryService.delete(category);
		return "recategory";
	}

	
	/**
	 * 产品列表
	 * @return
	 */
    @Action("productList")
	public String productList(){
    	flag = 3;
		productList = productService.getStatusList(status, page, rows);
		pageHtml = PageUtil.getPageToolAdmin(request, productService.getStatusTotal(status), page, rows);
		return "product";
	}
	
    /**
     * 后台搜索
     * @return
     */
    @Action("productSearch")
	public String productSearch(){
    	flag = 3;
		if (product!=null && product.getId() > 0) {
			productList = Arrays.asList(productService.get(product.getId()));
		}else if (product!=null && product.getName()!=null && !product.getName().trim().isEmpty()) {
			productList = productService.getProductListByName(product.getName(), page, rows);
			pageHtml = PageUtil.getPageToolAdmin(request, productService.getProductTotalByName(product.getName()), page, rows);
		}else {
			return productList();
		}
		return "product";
	}
	
	/**
	 * 产品添加
	 * @return
	 */
    @Action("productA")
	public String productA(){
    	flag = 3;
		categoryList = categoryService.getList();
		return "productadd";
	}
	
	/**
	 * 产品添加
	 * @return
	 */
    @Action("productAdd")
	public String productAdd(){
		product.setCover(UploadUtil.fileUpload(photo, photoFileName, "picture"));
		productService.add(product);
		return "reproduct";
	}
	
	/**
	 * 产品更新
	 * @return
	 */
    @Action("productUp")
	public String productUp(){
    	flag = 3;
		categoryList = categoryService.getList();
		product = productService.get(id);
		return "productupdate";
	}
	
	/**
	 * 产品更新
	 * @return
	 */
    @Action("productUpdate")
	public String productUpdate(){
		if (photo != null) {
			product.setCover(UploadUtil.fileUpload(photo, photoFileName, "picture"));
		}
		productService.update(product);
		return "reproduct";
	}
	
	/**
	 * 产品删除
	 * @return
	 */
    @Action("productDelete")
	public String productDelete(){
		productService.delete(product.getId());
		return "reproduct";
	}
	
	/**
	 * 添加
	 * @return
	 */
    @Action("productShowA")
	public String productShowA(){
    	flag = 3;
		product = productService.get(id);
		return "productaddshow";
	}
	
	/**
	 * 添加
	 * @return
	 */
    @Action("productShowAdd")
	public String productShowAdd(){
		productService.add(productShow);
		return "reproduct";
	}
	
	/**
	 * 删除
	 * @return
	 */
    @Action("productShowDelete")
	public String productShowDelete(){
		productService.delete(productService.getShow(id));
		return "reproduct";
	}
	
	/**
	 * 添加
	 * @return
	 */
    @Action("productSaleA")
	public String productSaleA(){
    	flag = 3;
		product = productService.get(id);
		return "productaddsale";
	}
	
	/**
	 * 添加
	 * @return
	 */
    @Action("productSaleAdd")
	public String productSaleAdd(){
		productService.add(productSale);
		return "reproduct";
	}
	
	/**
	 * 删除
	 * @return
	 */
    @Action("productSaleDelete")
	public String productSaleDelete(){
		productService.delete(productService.getSale(id));
		return "reproduct";
	}
	
	/**
	 * 添加
	 * @return
	 */
    @Action("productNewA")
	public String productNewA(){
    	flag = 3;
		product = productService.get(id);
		return "productaddnew";
	}
	
	/**
	 * 添加
	 * @return
	 */
    @Action("productNewAdd")
	public String productNewAdd(){
		productService.add(productNew);
		return "reproduct";
	}
	
	/**
	 * 删除
	 * @return
	 */
    @Action("productNewDelete")
	public String productNewDelete(){
		productService.delete(productService.getNew(id));
		return "reproduct";
	}
	
	/**
	 * 库存列表
	 * @return
	 */
    @Action("stockList")
	public String stockList(){
    	flag = 4;
		productList = productService.getProductListStock(page, rows);
		pageHtml = PageUtil.getPageToolAdmin(request, productService.getProductTotal(), page, rows);
		return "stocklist";
	}
	
	/**
	 * 库存更新
	 * @return
	 */
    @Action("stockUpdate")
	public String stockUpdate(){
		productService.updateStock(product.getId(), product.getStock());
		return "restocklist";
	}
	
	
	/**
	 * 顾客管理
	 * @return
	 */
    @Action("userList")
	public String userList(){
    	flag = 5;
		userList = userService.getList(page, rows);
		pageHtml = PageUtil.getPageToolAdmin(request, userService.getTotal(), page, rows);
		return "user";
	}
	
	/**
	 * 顾客添加
	 * @return
	 */
    @Action("userAdd")
	public String userAdd(){
		if (userService.isExist(user.getUsername())) {
	    	flag = 5;
	    	request.setAttribute("msg", "用户名已存在!");
			return "useradd";
		}
		 userService.add(user);
		 return "reuser";
	}
    
    /**
     * 顾客添加
     * @return
     */
    @Action("userA")
    public String userA(){
		flag = 5;
		return "useradd";
    }
	
	/**
	 * 顾客密码重置页面
	 * @return
	 */
    @Action("userRe")
	public String userRe(){
    	flag = 5;
		user = userService.get(id);
		return "userreset";
	}
	
	/**
	 * 顾客密码重置
	 * @return
	 */
    @Action("userReset")
	public String userReset(){
    	if(StringUtils.isEmpty(user.getPassword()) && StringUtils.isEmpty(user.getPayword())) {
    		return "reuser";
    	}
		Users u = userService.get(user.getId());
		u.setPassword(StringUtils.isEmpty(user.getPassword()) ? u.getPassword() : SafeUtil.encode(user.getPassword()));
		u.setPayword(StringUtils.isEmpty(user.getPayword()) ? u.getPayword() : SafeUtil.encode(user.getPayword()));
		userService.update(u);
		return "reuser";
	}
	
	/**
	 * 顾客更新
	 * @return
	 */
    @Action("userUp")
	public String userUp(){
    	flag = 5;
		user = userService.get(id);
		return "userupdate";
	}
	
	/**
	 * 顾客更新
	 * @return
	 */
    @Action("userUpdate")
	public String userUpdate(){
		userService.update(user);
		return "reuser";
	}
	
	/**
	 * 顾客删除
	 * @return
	 */
    @Action("userDelete")
	public String userDelete(){
		userService.delete(user);
		return "reuser";
	}
    
	
	/**
	 * 管理员列表
	 * @return
	 */
    @Action("adminList")
	public String adminList(){
    	flag = 6;
		adminList = adminService.getList(page, rows);
		pageHtml = PageUtil.getPageToolAdmin(request, adminService.getTotal(), page, rows);
		return "admin";
	}
	
	/**
	 * 管理员添加
	 * @return
	 */
    @Action("adminA")
	public String adminA(){
		flag = 6;
		return "adminadd";
	}
    
    /**
     * 管理员添加
     * @return
     */
    @Action("adminAdd")
    public String adminAdd(){
    	if (adminService.isExist(admin.getUsername())) {
    		flag = 6;
	    	request.setAttribute("msg", "用户名已存在!");
    		return "adminadd";
    	}
    	adminService.add(admin);
    	return "readmin";
    }
	
	/**
	 * 重置密码页面
	 * @return
	 */
    @Action("adminRe")
	public String adminRe(){
    	flag = 6;
		admin = adminService.get(id);
		return "adminreset";
	}
	
	/**
	 * 重置密码
	 * @return
	 */
    @Action("adminReset")
	public String adminReset(){
    	if(StringUtils.isEmpty(admin.getPassword())) {
    		return "readmin";
    	}
		Admin a = adminService.get(admin.getId());
		a.setPassword(StringUtils.isEmpty(admin.getPassword()) ? a.getPassword() : SafeUtil.encode(admin.getPassword()));
		adminService.update(a);
		return "readmin";
	}
	
	/**
	 * 管理员删除
	 * @return
	 */
    @Action("adminDelete")
	public String adminDelete(){
		adminService.delete(admin);
		return "readmin";
	}
	
    
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Indent> getIndentList() {
		return indentList;
	}

	public void setIndentList(List<Indent> indentList) {
		this.indentList = indentList;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Items> getItemList() {
		return itemList;
	}

	public void setItemList(List<Items> itemList) {
		this.itemList = itemList;
	}

	public List<Users> getUserList() {
		return userList;
	}

	public void setUserList(List<Users> userList) {
		this.userList = userList;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public List<Admin> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<Admin> adminList) {
		this.adminList = adminList;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}

	public ProductShow getProductShow() {
		return productShow;
	}

	public void setProductShow(ProductShow productShow) {
		this.productShow = productShow;
	}

	public ProductSale getProductSale() {
		return productSale;
	}

	public void setProductSale(ProductSale productSale) {
		this.productSale = productSale;
	}

	public ProductNew getProductNew() {
		return productNew;
	}

	public void setProductNew(ProductNew productNew) {
		this.productNew = productNew;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}
