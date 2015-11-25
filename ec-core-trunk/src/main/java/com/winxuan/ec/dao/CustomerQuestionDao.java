package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.customer.CustomerQuestion;
import com.winxuan.ec.model.customer.CustomerQuestionReply;
import com.winxuan.ec.model.user.User;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-10-12
 */
public interface CustomerQuestionDao {
	
	
	@Get
	CustomerQuestion get(Long id);
	
	@Save
	void save(CustomerQuestion customerQuestion);
	
	@Update
	void update(CustomerQuestion customerQuestion);
	
	@Query("select distinct c from CustomerQuestion c left join c.replyList l")
	@Conditions({
		@Condition("c.id in :ids"),
		@Condition("c.productSale = :productSale"),
		@Condition("c.shop.id = :shopId"),
		@Condition("c.customer.name = :customerName"),
		@Condition("c.customer = :customer"),
		@Condition("c.askTime>=:startSubmitTime"),
		@Condition("c.askTime<=:endSubmitTime"),
		@Condition("c.productSale.product.name = :productName"),
		@Condition("c.productSale.id = :productSaleId"),
		@Condition("c.shop.id=:shopId"),
		@Condition("c.reply =:reply"),
		@Condition("l.replyTime>=:replyTimeBegin"),
		@Condition("l.replyTime<=:replyTimeEnd"),
		@Condition("l.replier.name>=:replierName")
	})
	@OrderBys({
		@OrderBy("c.askTime desc"),
		@OrderBy("c.askTime asc"),
		@OrderBy("c.id desc"),
		@OrderBy("c.id asc")
	})
	List<CustomerQuestion> find(@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination,
			@Order Short orderIndex);
	
	
	@Query("FROM CustomerQuestion c")
	@Conditions({
		@Condition("c.id in :ids"),
		@Condition("c.productSale = :productSale"),
		@Condition("c.customer = :customer"),
		@Condition("c.shop =:shop")
	})
	long find(@ParameterMap Map<String, Object> parameters);
	
	
	@Query("FROM CustomerQuestionReply p")
	@Conditions({
		@Condition("p.question = :question"),
		@Condition("p.question.id = :questionId"),
		@Condition("p.replier = :replier"),
		@Condition("p.replier.id = :replierId"),
		@Condition("p.replier.name = :replierName"),
		@Condition("p.replyTime >= :replyTimeBegin"),
		@Condition("p.replyTime <= :replyTimeEnd"),
		@Condition("c.productSale.product.name = :productName"),
		@Condition("c.productSale.id = :productSaleId")
		
	})
	@OrderBys({
		@OrderBy("p.replyTime desc"),
		@OrderBy("p.replyTime asc"),
		@OrderBy("p.id desc"), 
		@OrderBy("p.id asc")
	})
	List<CustomerQuestionReply> findReply(@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination,
			@Order Short orderIndex);
	
	@Get 
	CustomerQuestionReply findReply(Long id);
	
	@Query("from CustomerQuestionReply c WHERE c.replier = ?")
	long findReply(User user);
	
	@Delete
	void delete(CustomerQuestion question);
	
	@Save
	void saveReply(CustomerQuestionReply reply);

	@Update
	void updateReply(CustomerQuestionReply reply);
}
