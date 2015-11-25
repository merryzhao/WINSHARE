package com.winxuan.ec.front.controller.product;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.customer.CustomerQuestion;
import com.winxuan.ec.model.customer.CustomerQuestionReply;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.customer.CustomerCommentService;
import com.winxuan.ec.service.customer.CustomerQuestionService;
import com.winxuan.ec.service.product.ProductSaleRankService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.service.society.KeenessService;
import com.winxuan.ec.service.verifycode.VerifyCodeService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.StringUtils;


/**
 * 商品底层页 咨询相关的
 * 
 * @author cast911
 * @description:
 * @lastupdateTime:2012-8-22下午03:46:26 -_-!
 */
@Controller
@RequestMapping(value = "/product")
public class ProductDetailsQuestionController {
	private static final Log LOG = LogFactory
			.getLog(ProductDetailsQuestionController.class);

	private static final String PRODUCT = "product";
	private static final String PRODUCT_SALE = "productSale";
	private static final String COMMENT_COUNT = "commentCount";
	private static final String QUESTION_COUNT = "questionCount";
	private static final String RANK_COUNT = "rankCount";
	private static final String PAGINATION = "pagination";
	private static final String CUSTOMERQUESTIONS = "customerQuestions";
	private static final String NOT_FOUND_MESSAGE = "商品不存在:";
	private static final String STATUS = "status";
	private static final String DOTS = "...";
	/**
	 * 商品种类杂志
	 */

	private static final short SORT = 0;


	@Autowired
	private ProductService productService;

	@Autowired
	private KeenessService keenessService;
	
	@Autowired
	private CustomerCommentService customerCommentService;

	@Autowired
	private CustomerQuestionService customerQuestionService;

	@Autowired
	private VerifyCodeService verifyCodeService;

	@Autowired
	private ProductSaleRankService productSaleRankService;

	/**
	 * 提交咨询
	 * 
	 * @param id
	 * @param verifyCode
	 * @param title
	 * @param content
	 * @param customer
	 * @param verifyNumber
	 * @return
	 * @throws CloneNotSupportedException 
	 */
	@RequestMapping(value = "/{id}/question", method = RequestMethod.POST)
	public ModelAndView create(@PathVariable("id") Long id,
			@RequestParam String verifyCode,
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "content", required = true) String content,
			@MyInject Customer customer,
			@CookieValue(value = "verify_number") String verifyNumber) throws CloneNotSupportedException {
		ProductSale productSale = productService.getProductSale(id);
		if (!verifyCodeService.verify(verifyCode, verifyNumber)) {
			ModelAndView modelAndView = new ModelAndView(
					"/product/create_failure");
			modelAndView.addObject(STATUS, false);
			modelAndView.addObject("message", "验证码错误!");
			return modelAndView;
		}
		CustomerQuestion customerQuestion = new CustomerQuestion();
		if (customer != null) {
			customerQuestion.setCustomer(customer);
		}
		/*
		 * 咨询标题的长度及敏感词过滤
		 */
		if(title.length() >= MagicNumber.TWENTY_FIVE){
			title = title.substring(MagicNumber.ZERO, MagicNumber.TWENTY_FIVE) + DOTS;
		}
		customerQuestion.setTitle(StringUtils.escapeSpecialLabel(keenessService.replaceRich(title)));
		/*
		 * 咨询内容长度及敏感词过滤
		 */
		if(content.length() >= MagicNumber.HUNDRED*MagicNumber.FOUR){
			content = content.substring(MagicNumber.ZERO, MagicNumber.HUNDRED*MagicNumber.FOUR) + DOTS;
		}
		customerQuestion.setContent(StringUtils.escapeSpecialLabel(keenessService.replaceRich(content)));
		customerQuestion.setAskTime(new Date());
		customerQuestion.setProductSale(productSale);
		customerQuestionService.save(customerQuestion);
		List<CustomerQuestion> customerQuestions = customerQuestionService
				.findQuestionsByProductSale(productSale, null, SORT);
		long count = customerQuestionService
				.findQuestionCountByProductSale(productSale);
		ModelAndView modelAndView = new ModelAndView(
				"/product/customerQuestions");
		modelAndView.addObject(STATUS, true);
		modelAndView.addObject("count", count);
		
		if(!CollectionUtils.isEmpty(customerQuestions)){
			modelAndView.addObject(CUSTOMERQUESTIONS, customerQuestions);
		}
		return modelAndView;
	}

	/**
	 * 单条提问
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/question/{id}", method = RequestMethod.GET)
	public ModelAndView getQuestion(@PathVariable("id") Long id,
			HttpServletResponse response) throws IOException,CloneNotSupportedException {
		CustomerQuestion customerQuestion = customerQuestionService.get(id);
		if (customerQuestion == null) {
			response.sendError(HttpStatus.SC_NOT_FOUND);
			return null;
		}
		ModelAndView modelAndView = new ModelAndView("/product/question/view");
		ProductSale productSale = customerQuestion.getProductSale();
		long rankCount = productSaleRankService.findRankCount(productSale);
		modelAndView.addObject(RANK_COUNT, rankCount);
		modelAndView.addObject("customerQuestion", customerQuestion);
		modelAndView.addObject(PRODUCT_SALE, productSale);
		modelAndView.addObject(PRODUCT, productSale.getProduct());
		modelAndView.addObject("avgRank", productSaleRankService.getProductRankAverage(productSale));
		return modelAndView;
	}

	/**
	 * 咨询列表页
	 * 
	 * @param id
	 * @param customer
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/{id}/question/list")
	public ModelAndView list(@PathVariable("id") Long id,
			@MyInject Customer customer, @MyInject Pagination pagination,
			HttpServletResponse response) throws IOException, CloneNotSupportedException {
		ProductSale productSale = productService.getProductSale(id);
		if (productSale == null) {
			LOG.info(NOT_FOUND_MESSAGE + id);
			response.sendError(HttpStatus.SC_NOT_FOUND);
			return null;
		}
		ModelAndView modelAndView = new ModelAndView("/product/question/list");
		
		long questionCount = customerQuestionService
				.findQuestionCountByProductSale(productSale);
		long rankCount = productSaleRankService.findRankCount(productSale);
		long commentCount = customerCommentService
				.getCountByProductSale(productSale);
		pagination.setPageSize(MagicNumber.TEN);
		List<CustomerQuestion> customerQuestions = customerQuestionService
		.findQuestionsByProductSale(productSale, pagination, SORT);

		if(!CollectionUtils.isEmpty(customerQuestions)){
			modelAndView.addObject(CUSTOMERQUESTIONS, customerQuestions);
		}
		modelAndView.addObject(PRODUCT, productSale.getProduct());
		modelAndView.addObject(PRODUCT_SALE, productSale);
		modelAndView.addObject("customer", customer);
		modelAndView.addObject(RANK_COUNT, rankCount);
		modelAndView.addObject(QUESTION_COUNT, questionCount);
		modelAndView.addObject(COMMENT_COUNT, commentCount);
		modelAndView.addObject(PAGINATION, pagination);
		modelAndView.addObject("avgRank", productSaleRankService.getProductRankAverage(productSale));
		return modelAndView;
	}
	
	/**
	 * 咨询回复
	 * @param id
	 * @param customer
	 * @param content
	 * @throws CloneNotSupportedException 
	 */
	@RequestMapping(value = "/question/reply/{id}", method=RequestMethod.POST)
	public ModelAndView reply(@PathVariable("id") Long id,
			@MyInject Customer customer,
			@RequestParam("content") String content,
			@MyInject Pagination pagination) throws CloneNotSupportedException{
		if(customer != null){
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("replierId", customer.getId());
			parameters.put("questionId", id);
			List<CustomerQuestionReply> customerQuestionReplies = customerQuestionService.findReplay(parameters, null, SORT);
			if(customerQuestionReplies.size() < MagicNumber.FIVE){//一个会员针对一条咨询最多回复5次
				ModelAndView mav = new ModelAndView("/product/question/productQuestion");
				CustomerQuestion customerQuestion = customerQuestionService.get(id);
				CustomerQuestionReply customerQuestionReply = new CustomerQuestionReply();
				customerQuestionReply.setQuestion(customerQuestion);
				customerQuestionReply.setReplier(customer);
				/*
				 * 咨询回复内容长度及敏感词过滤
				 */
				if(content.length() >= MagicNumber.HUNDRED*MagicNumber.FOUR){
					content = content.substring(MagicNumber.ZERO, MagicNumber.HUNDRED*MagicNumber.FOUR) + DOTS;
				}
				customerQuestionReply.setContent(StringUtils.escapeSpecialLabel(keenessService.replaceRich(content)));
				customerQuestionReply.setReplyTime(new Date());
				
				/*
				 * 当为用户提问时，直接将问题的状态设置为“未回复”
				 */
				customerQuestionService.saveCustomerReply(customerQuestionReply);
				
				customerQuestion = customerQuestionService.get(id);
				mav.addObject("replyList", customerQuestion.getReplyList());
				mav.addObject(STATUS, true);
				return mav;		
			}else{
				//一个会员针对单条咨询回复超过5次
				ModelAndView mav = new ModelAndView("/product/question/replyFailure");
				mav.addObject(STATUS, false);
				mav.addObject("message", "单个问题最多只能回复5次!");
				return mav;
			}
		}else{
			//游客不能回复咨询
			ModelAndView mav = new ModelAndView("/product/question/replyFailure");
			mav.addObject(STATUS, false);
			mav.addObject("message", "您还没有登陆，请先登陆!");
			return mav;
		}		
		
	}


}
