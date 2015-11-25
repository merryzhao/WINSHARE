package com.winxuan.ec.service.customer;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.customer.CustomerQuestion;
import com.winxuan.ec.model.customer.CustomerQuestionReply;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-10-12
 */
public interface CustomerQuestionService {
	
	void save(CustomerQuestion customerQuestion);
	
	
	CustomerQuestion get(Long id);
	/**
	 * 根据指定商品获取提问集合
	 * @param productSale
	 * @return
	 */
	List<CustomerQuestion> findQuestionsByProductSale(ProductSale productSale,Pagination pagination,Short sort);
	
	/**
	 * 获取店铺的咨询
	 * @param productSale
	 * @return
	 */
	List<CustomerQuestion> findQuestionsByShop(Shop shop,Pagination pagination,Short sort);
	
	long findQuestionCountByProductSale(ProductSale productSale);
	
	/**
	 * 获取店铺评论数量
	 * @param shop
	 * @return
	 */
	long findQuestionCountByShop(Shop shop);
	
	List<CustomerQuestion> find(Map<String,Object> parameters,Pagination pagination,Short order);
	
	void delete(CustomerQuestion question);
	
	/**
	 * 新增接口，当为客户回复时，isReply设置为false
	 * @param reply
	 */
	void saveCustomerReply(CustomerQuestionReply reply); 
	
	/**
	 * 新增接口当为客服回复提问时，isReply设置为true
	 * @param reply
	 */
	void saveServiceReply(CustomerQuestionReply reply);
	
	void updateReply(CustomerQuestionReply reply); 
	
	List<CustomerQuestionReply> findReplay(Map<String, Object> parameters, Pagination pagination, Short sort);
	
	CustomerQuestionReply findReply(Long id);
	
	
}
