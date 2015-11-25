package com.winxuan.ec.service.customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.CustomerQuestionDao;
import com.winxuan.ec.model.customer.CustomerQuestion;
import com.winxuan.ec.model.customer.CustomerQuestionReply;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.readwritesplitting.Read;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-10-12
 */
@Service("customerQuestionService")
@Transactional(rollbackFor = Exception.class)
public class CustomerQuestionServiceImpl implements CustomerQuestionService{
	
	
	@InjectDao 
	CustomerQuestionDao customerQuestionDao;
	
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public CustomerQuestion get(Long id) {
		return customerQuestionDao.get(id);
	}
	
	public void save(CustomerQuestion customerQuestion){
		if(customerQuestion.getProductSale() != null){
			customerQuestion.setShop(customerQuestion.getProductSale().getShop());
		}
		customerQuestionDao.save(customerQuestion);
	}
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<CustomerQuestion> findQuestionsByProductSale(ProductSale productSale,Pagination pagination,Short sort){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productSale", productSale);
		return customerQuestionDao.find(parameters, pagination, sort);
	}
	
	public long findQuestionCountByProductSale(ProductSale productSale){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productSale", productSale);
		return customerQuestionDao.find(parameters);
	}

	@Override
	public List<CustomerQuestion> find(Map<String, Object> parameters,
			Pagination pagination, Short order) {
		List<CustomerQuestion> result = customerQuestionDao.find(parameters, pagination, order);
		return result;
	}

	@Override
	public void delete(CustomerQuestion question) {
		customerQuestionDao.delete(question);
	}
	
	public void updateReply(CustomerQuestionReply reply){
		customerQuestionDao.updateReply(reply);
	}

	@Override
	public List<CustomerQuestion> findQuestionsByShop(Shop shop,
			Pagination pagination, Short sort) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("shopId", shop.getId());
		return customerQuestionDao.find(parameters, pagination, sort);
	}

	@Override
	public long findQuestionCountByShop(Shop shop) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("shop", shop);
		return customerQuestionDao.find(parameters);
	}

	@Override
	public List<CustomerQuestionReply> findReplay(
			Map<String, Object> parameters, Pagination pagination, Short sort) {
		return customerQuestionDao.findReply(parameters, pagination, sort);
	}
	
	public CustomerQuestionReply findReply(Long id){
		return customerQuestionDao.findReply(id);
	}
	
	/**
	 * 新增方法，实现当为用户提问时，问题的状态设置为“未回复”
	 * 当为客服回复提问时，问题的状态设置为“已回复”
	 */
	@Override
	public void saveCustomerReply(CustomerQuestionReply reply){
		customerQuestionDao.saveReply(reply);
		if(reply.getQuestion()!=null){
			CustomerQuestion customerQuestion = reply.getQuestion();
			customerQuestion.setReply(false);
			customerQuestionDao.update(customerQuestion);
		}
	}
	
	@Override
	public void saveServiceReply(CustomerQuestionReply reply){
		customerQuestionDao.saveReply(reply);
		if(reply.getQuestion()!=null){
			CustomerQuestion customerQuestion = reply.getQuestion();
			customerQuestion.setReply(true);
			customerQuestionDao.update(customerQuestion);
		}
	}
}