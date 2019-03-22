package com.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.entity.Product;
import com.entity.ProductNew;
import com.entity.ProductSale;
import com.entity.ProductShow;

@Repository // 注册dao层bean等同于@Component
public class ProductDao extends BaseDao{

	
	/**
	 * 	获取列表
	 * @param category
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Product> getProductList(int page, int size){
		return getSession().createQuery("from Product order by id desc", Product.class)
				.setFirstResult((page-1)*size).setMaxResults(size).list();
	}
	
	/**
	 * 	获取列表
	 * @param category
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Product> getProductListStock(int page, int size){
		return getSession().createQuery("from Product order by stock asc", Product.class)
				.setFirstResult((page-1)*size).setMaxResults(size).list();
	}
	
	/**
	 * 	获取总数
	 * @param category
	 * @return
	 */
	public long getProductTotal(){
		return getSession().createQuery("select count(*) from Product", Long.class).uniqueResult();
	}
	
	/**
	 * 	获取列表
	 * @param category
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Product> getProductListByName(String name, int page, int size){
		return getSession().createQuery("from Product where name like :name order by id desc", Product.class)
				.setParameter("name", "%"+name+"%").setFirstResult((page-1)*size).setMaxResults(size).list();
	}
	
	/**
	 * 	获取总数
	 * @param category
	 * @return
	 */
	public long getProductTotalByName(String name){
		return getSession().createQuery("select count(*) from Product where name like :name", Long.class)
				.setParameter("name", "%"+name+"%").uniqueResult();
	}
	
	/**
	 * 库存预警列表
	 * @param status
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Product> getWarnList(int page, int size) {
		return getSession().createQuery("from Product where stock<10 order by id desc", Product.class)
				.setFirstResult((page-1)*size).setMaxResults(size).list();
	}
	
	/**
	 * 库存预警数量
	 * @param status
	 * @return
	 */
	public long getWarnTotal(){
		return (Long) getSession().createQuery("select count(*) from Product where stock<5").uniqueResult();
	}
	
	/**
	 * 	获取列表
	 * @param category
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Product> getCategoryList(int categoryid, int page, int size){
		return getSession().createQuery("from Product where category_id=:categoryid order by id desc", Product.class)
				.setParameter("categoryid", categoryid).setFirstResult((page-1)*size).setMaxResults(size).list();
	}
	
	/**
	 * 	获取总数
	 * @param category
	 * @return
	 */
	public long getCategoryTotal(int categoryid){
		return (Long) getSession().createQuery("select count(*) from Product where category_id=:categoryid")
				.setParameter("categoryid", categoryid).uniqueResult();
	}
	
	/**
	 * 	获取列表
	 * @param category
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Product> getStatusList(int status, int page, int size) {
		return getSession().createQuery("from Product where id in ("+packProductids(status)+") order by id desc", Product.class)
				.setFirstResult((page-1)*size).setMaxResults(size).list();
	}
	
	/**
	 * 	获取总数
	 * @param category
	 * @return
	 */
	public long getStatusTotal(int status) {
		return getSession().createQuery("select count(*) from Product where id in ("+packProductids(status)+")", Long.class).uniqueResult();
	}
	
	/**
	 * 封装productids
	 * @param status
	 * @return
	 */
	private String packProductids(int status) {
		String productids = "";
		switch (status) {
		case 1:
			List<ProductShow> showList = getShowList();
			for (ProductShow show : showList) {
				productids += show.getProduct().getId() + ",";
			}
			break;
		case 2:
			List<ProductSale> saleList = getSaleList();
			for (ProductSale sale : saleList) {
				productids += sale.getProduct().getId() + ",";
			}
			break;
		case 3:
			List<ProductNew> newList = getNewList();
			for (ProductNew news : newList) {
				productids += news.getProduct().getId() + ",";
			}
			break;
		}
		return productids.substring(0, productids.length()-1);
	}

	/**
	 * 	获取列表
	 * @param category
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Product> getSearchList(String search, int page, int size){
		return getSession().createQuery("from Product where name like :search order by id desc", Product.class)
				.setParameter("search", "%"+search+"%").setFirstResult((page-1)*size).setMaxResults(size).list();
	}
	
	/**
	 * 	获取总数
	 * @param category
	 * @return
	 */
	public long getSearchTotal(String search){
		return getSession().createQuery("select count(*) from Product where name like :search", Long.class)
				.setParameter("search", "%"+search+"%").uniqueResult();
	}
	
	/**
	 * 	获取特卖列表
	 * @param page
	 * @param size
	 * @return
	 */
	public List<ProductShow> getShowList(){
		return getSession().createQuery("from ProductShow order by id desc", ProductShow.class).list();
	}
	
	
	/**
	 * 	获取特卖列表
	 * @param page
	 * @param size
	 * @return
	 */
	public List<ProductShow> getShowList(int page, int size){
		return getSession().createQuery("from ProductShow order by id desc", ProductShow.class)
				.setFirstResult((page-1)*size).setMaxResults(size).list();
	}
	
	/**
	 * 	获取特卖总数
	 * @return
	 */
	public long getShowTotal(){
		return getSession().createQuery("select count(*) from ProductShow", Long.class).uniqueResult();
	}
	
	/**
	 * 	获取促销列表
	 * @param page
	 * @param size
	 * @return
	 */
	public List<ProductSale> getSaleList(){
		return getSession().createQuery("from ProductSale order by id desc", ProductSale.class).list();
	}
	
	/**
	 * 	获取促销列表
	 * @param page
	 * @param size
	 * @return
	 */
	public List<ProductSale> getSaleList(int page, int size){
		return getSession().createQuery("from ProductSale order by id desc", ProductSale.class)
				.setFirstResult((page-1)*size).setMaxResults(size).list();
	}
	
	/**
	 * 	获取促销总数
	 * @return
	 */
	public long getSaleTotal(){
		return getSession().createQuery("select count(*) from ProductSale", Long.class).uniqueResult();
	}
	
	/**
	 * 	获取新品列表
	 * @param page
	 * @param size
	 * @return
	 */
	public List<ProductNew> getNewList(){
		return getSession().createQuery("from ProductNew order by id desc", ProductNew.class).list();
	}
	
	/**
	 * 	获取新品列表
	 * @param page
	 * @param size
	 * @return
	 */
	public List<ProductNew> getNewList(int page, int size){
		return getSession().createQuery("from ProductNew order by id desc", ProductNew.class)
				.setFirstResult((page-1)*size).setMaxResults(size).list();
	}
	
	/**
	 * 	获取新品总数
	 * @return
	 */
	public long getNewTotal(){
		return getSession().createQuery("select count(*) from ProductNew", Long.class).uniqueResult();
	}
	
	/**
	 * 获取
	 * @param productid
	 * @return
	 */
	public ProductSale getSale(int productid) {
		return getSession().createQuery("from ProductSale where product_id=:productid", ProductSale.class)
				.setParameter("productid", productid).uniqueResult();
	}
	
	/**
	 * 获取
	 * @param productid
	 * @return
	 */
	public ProductShow getShow(int productid) {
		return getSession().createQuery("from ProductShow where product_id=:productid", ProductShow.class)
				.setParameter("productid", productid).uniqueResult();
	}
	
	/**
	 * 获取
	 * @param productid
	 * @return
	 */
	public ProductNew getNew(int productid) {
		return getSession().createQuery("from ProductNew where product_id=:productid", ProductNew.class)
				.setParameter("productid", productid).uniqueResult();
	}
	
}