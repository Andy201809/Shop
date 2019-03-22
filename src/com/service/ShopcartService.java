package com.service;

import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ShopcartDao;
import com.entity.Product;
import com.entity.Shopcart;
import com.entity.Users;

@Service	// 注解为service层spring管理bean
@Transactional	// 注解此类所有方法加入spring事务, 具体设置默认
public class ShopcartService {

	@Resource	
	private ShopcartDao shopcartDao;
	@Resource
	private ProductService productService;
	
	
	/**
	 * 获取列表
	 * @return
	 */
	public List<Shopcart> getList(int userid){
		List<Shopcart> list = shopcartDao.getList(userid);
		for(Shopcart shopcart : list) {
			Product product = productService.packProductPrice(shopcart.getProduct());
			shopcart.setTotal(product.getPrice() * shopcart.getAmount());
			shopcart.setProduct(product);
		}
		return list;
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	public Shopcart get(int id) {
		return shopcartDao.get(Shopcart.class, id);
	}
	
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	public Shopcart get(int userid, int productid) {
		return shopcartDao.get(userid, productid);
	}
	
	/**
	 * 添加
	 * @param shopcart
	 * @return
	 */
	public boolean add(Product product, Users user) {
		Shopcart shopcart = this.get(user.getId(), product.getId());
		if (Objects.isNull(shopcart)) {
			shopcart = new Shopcart();
			shopcart.setUser(user);
			shopcart.setProduct(product);
			shopcart.setAmount(1);
			return shopcartDao.save(shopcart) > 0;
		}
		shopcart.setAmount(shopcart.getAmount() + 1);
		return shopcartDao.update(shopcart);
	}

	/**
	 * 更新
	 * @param shopcart
	 */
	public boolean update(Shopcart shopcart) {
		return shopcartDao.update(shopcart);
	}

	/**
	 * 删除
	 * @param shopcart
	 */
	public boolean delete(Shopcart shopcart) {
		return shopcartDao.delete(shopcart);
	}

	/**
	 * 清空购物车
	 * @param id
	 */
	public void clean(int userid) {
		List<Shopcart> list = getList(userid);
		for(Shopcart cart : list) {
			this.delete(cart);
		}
	}
	
}
