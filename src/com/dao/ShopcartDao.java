package com.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.entity.Shopcart;

@Repository // 注册dao层bean等同于@Component
public class ShopcartDao extends BaseDao{

	
	/**
	 * 获取
	 * @return
	 */
	public Shopcart get(int userid, int productid) {
		return getSession().createQuery("from Shopcart where user.id=:userid and product.id=:productid", Shopcart.class)
				.setParameter("userid", userid).setParameter("productid", productid).setMaxResults(1).uniqueResult();
	}
	
	/**
	 * 获取列表
	 * @return
	 */
	public List<Shopcart> getList(int userid) {
		return getSession().createQuery("from Shopcart where user.id=:userid", Shopcart.class)
				.setParameter("userid", userid).list();
	}
	
	
}