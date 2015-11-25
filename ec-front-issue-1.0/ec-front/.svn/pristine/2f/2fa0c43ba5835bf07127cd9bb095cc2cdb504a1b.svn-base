package com.winxuan.ec.front.controller.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerComment;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleRank;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.channel.ChannelService;
import com.winxuan.ec.service.customer.CustomerCommentService;
import com.winxuan.ec.service.product.ProductSaleRankService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.service.society.KeenessService;
import com.winxuan.ec.service.verifycode.VerifyCodeService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.pagination.Pagination;

/**
 * 商品底层页,评论相关的
 * 
 * @author cast911
 * @description:
 * @lastupdateTime:2012-8-22下午03:44:39 -_-!
 */
@Controller
@RequestMapping(value = "/product")
public class ProductDetailsCommondController {
	private static final Log LOG = LogFactory
			.getLog(ProductDetailsCommondController.class);

	private static final String PRODUCT = "product";
	private static final String PRODUCT_SALE = "productSale";
	private static final String COMMENT_COUNT = "commentCount";
	private static final String RANK_COUNT = "rankCount";
	private static final String PAGINATION = "pagination";
	private static final String CUSTOMER_COMMENTS = "customerComments";
	private static final String QUOTE_CUSTOMER_COMMENT = "quoteCustomerComment";
	private static final String NOT_FOUND_MESSAGE = "商品不存在:";
	private static final String DOTS = "...";
	/**
	 * 商品种类杂志
	 */

	@Autowired
	private ProductService productService;


	@Autowired
	private CustomerCommentService customerCommentService;

	@Autowired
	private KeenessService keenessService;


	@Autowired
	private VerifyCodeService verifyCodeService;

	@Autowired
	private ProductSaleRankService productSaleRankService;


	@Autowired
	private ChannelService channelService;


	/**
	 * 发表评论
	 * 
	 * @param customer
	 * @param productSaleId
	 * @param verifyNumber
	 * @param title
	 * @param rank
	 * @param content
	 * @param sRand
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/{productSale}/comment", method = RequestMethod.POST)
	public ModelAndView create(
			@MyInject Customer customer,
			@PathVariable("productSale") long productSaleId,
			@CookieValue(value = "verify_number", required = true) String verifyNumber,
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "rank", required = true) String rank,
			@RequestParam(value = "content", required = true) String content,
			@RequestParam(value = "quoteCommentId", required = false) String quoteCommentId,
			@RequestParam(value = "sRand", required = true) String sRand,
			HttpServletResponse response) throws IOException {
		ModelAndView modelAndView = new ModelAndView("redirect:/product/"
				+ productSaleId + "/comment/list");
		if (verifyCodeService.verify(sRand, verifyNumber)) {
			ProductSale productSale = productService
					.getProductSale(productSaleId);
			if (productSale == null) {
				response.sendError(HttpStatus.SC_NOT_FOUND);
				return null;
			}
			if (!StringUtils.isBlank(title)) {
				ProductSaleRank productSaleRank = new ProductSaleRank();
				CustomerComment customerComment = new CustomerComment();
				customerComment.setCustomer(customer);
				customerComment.setProductSale(productSale);
				/*
				 * 标题长度及敏感词过滤
				 */
				if(title.length() >= MagicNumber.TWENTY_FIVE){
					title = title.substring(MagicNumber.ZERO, MagicNumber.TWENTY_FIVE) + DOTS;
				}
				customerComment.setTitle(com.winxuan.framework.util.StringUtils.escapeSpecialLabel(keenessService.replaceRich(title)));
				/*
				 * 评论内容长度及敏感词的过滤
				 */
				if(content.length() >= MagicNumber.HUNDRED*MagicNumber.FOUR){
					content = content.substring(MagicNumber.ZERO, MagicNumber.HUNDRED*MagicNumber.FOUR) + DOTS;
				}
				customerComment.setContent(com.winxuan.framework.util.StringUtils.escapeSpecialLabel(keenessService.replaceRich(content)));
				customerComment.setCommentTime(new Date());
				//如果quoteCommentId不为空，则为二次引用评论
				if(!StringUtils.isBlank(quoteCommentId)){
					customerComment.setQuoteComment(customerCommentService.get(Long.valueOf(quoteCommentId)));
				}
				customerComment.setChannel(channelService
						.get(Channel.CHANNEL_EC));
				productSaleRank.setProductSale(productSale);
				productSaleRank.setRank(Integer.parseInt(rank));
				productSaleRank.setRankTime(new Date());
				customerComment.setRank(productSaleRank);
//				productSaleRank.setComment(customerComment);
				//由于rankList做了集合缓存，在增加评分时，加至ranklist中，将来评分改为数据库直接读取后，这里可以删除
				productSale.getRankList().add(productSaleRank);
				customerCommentService.save(customerComment, productSale);
				return modelAndView;
			}
			modelAndView.addObject("message", "找不到被评论的商品或者评论标题为空!");
		} else {
			modelAndView.addObject("message", "验证码错误!");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public ModelAndView verify(
			@CookieValue(value = "verify_number", required = true) String verifyNumber,
			@RequestParam(value = "sRand", required = true) String sRand) {
		ModelAndView modelAndView = new ModelAndView("/product/useful");
		if (verifyCodeService.verify(sRand, verifyNumber)) {
			modelAndView.addObject("useful", true);
		} else {
			modelAndView.addObject("useful", false);
		}
		return modelAndView;
	}
	
	/**
	 * 评论列表
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 * @throws CloneNotSupportedException 
	 */
	@RequestMapping(value = "/{id}/comment/list")
	public ModelAndView list(@PathVariable("id") Long id,
			@MyInject Pagination pagination, HttpServletResponse response)
			throws IOException, CloneNotSupportedException {
		ProductSale productSale = productService.getProductSale(id);
		if (productSale == null) {
			LOG.info(NOT_FOUND_MESSAGE+ id);
			response.sendError(HttpStatus.SC_NOT_FOUND);
			return null;
		}
		
		List<CustomerComment> comments = customerCommentService
				.findByProductSale(productSale, Integer.parseInt("1"),
						Short.parseShort("1"));
		long rankCount = productSaleRankService.findRankCount(productSale);
		ModelAndView modelAndView;
		if (Code.PRODUCT_SORT_BOOK.equals(productSale.getProduct().getSort().getId())) {
			modelAndView = new ModelAndView("/product/comment/blend");
			Channel tmallChannel = new Channel(Channel.TAOBAO_WINSHARE);
			modelAndView.addObject("tmallCommentCount", customerCommentService
					.getCountByProductSale(productSale, tmallChannel));
		} else {
			modelAndView = new ModelAndView("/product/comment/list");
		}
		modelAndView.addObject(COMMENT_COUNT,
				customerCommentService.getCountByProductSale(productSale));
		if (comments != null && comments.size() > 0) {
			modelAndView.addObject("firstComment", comments.get(0));
		}
		
		List<CustomerComment> customerComments = customerCommentService.findByProductSale(productSale, pagination);

		List<List<CustomerComment>> customerCommentsWithQuote = new ArrayList<List<CustomerComment>>();
		if(customerComments.size() > 0){
			List<CustomerComment> list = new ArrayList<CustomerComment>();
			for(CustomerComment cusComment : customerComments){
				CustomerComment customerComment = cusComment.filterFromCustomer();
				list = customerComment.getQuoteCommentList(customerComment.getQuoteComment());
				customerCommentsWithQuote.add(list);
			}
		}
		modelAndView.addObject(PRODUCT, productSale.getProduct());
		modelAndView.addObject(PRODUCT_SALE, productSale);
		modelAndView.addObject(CUSTOMER_COMMENTS, CollectionUtils.isEmpty(customerCommentsWithQuote) ? null : customerCommentsWithQuote);
		modelAndView.addObject(RANK_COUNT, rankCount);
		modelAndView.addObject(PAGINATION, pagination);
		modelAndView.addObject("avgRank", productSaleRankService.getProductRankAverage(productSale));
		modelAndView.addObject("rankRate", productSaleRankService.getProductRankInfos(productSale));
		return modelAndView;
	}

	@RequestMapping(value = "/{id}/comment/tmall")
	public ModelAndView tmall(@PathVariable("id") Long id,
			@MyInject Pagination pagination, HttpServletResponse response)
			throws IOException {
		ProductSale productSale = productService.getProductSale(id);
		if (productSale == null) {
			LOG.info(NOT_FOUND_MESSAGE + id);
			response.sendError(HttpStatus.SC_NOT_FOUND);
			return null;
		}
		
		ModelAndView mav=new ModelAndView("/product/comment/blend");
		Channel tmallChannel=new Channel(Channel.TAOBAO_WINSHARE);
		mav.addObject("showType","tmall");
		mav.addObject(COMMENT_COUNT,
				customerCommentService.getCountByProductSale(productSale));
		mav.addObject("tmallCommentCount",customerCommentService
				.getCountByProductSale(productSale, tmallChannel));
		mav.addObject("tmallComments", customerCommentService
				.findByProductSale(productSale, pagination, tmallChannel));
		mav.addObject(PRODUCT, productSale.getProduct());
		mav.addObject(PRODUCT_SALE, productSale);
		mav.addObject(PAGINATION, pagination);
		mav.addObject(RANK_COUNT, productSaleRankService.findRankCount(productSale));
		mav.addObject("avgRank", productSaleRankService.getProductRankAverage(productSale));
		mav.addObject("rankRate", productSaleRankService.getProductRankInfos(productSale));
		return mav;
	}
	
	/**
	 * 单条评论
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
	public ModelAndView getComment(@PathVariable("id") Long id,
			HttpServletResponse response,
			@MyInject Customer customer) throws IOException,CloneNotSupportedException {
		CustomerComment customerComment = customerCommentService.get(id);
		if (customerComment == null) {
			response.sendError(HttpStatus.SC_NOT_FOUND);
			return null;
		}
		ModelAndView modelAndView = new ModelAndView("/product/comment/view");
		customerComment = customerComment.filterFromCustomer();
		ProductSale productSale = customerComment.getProductSale();
		modelAndView.addObject(RANK_COUNT, productSaleRankService.findRankCount(productSale));
		modelAndView.addObject("comment", customerComment);
		modelAndView.addObject(PRODUCT_SALE, productSale);
		modelAndView.addObject(PRODUCT, customerComment.getProductSale().getProduct());
		modelAndView.addObject("avgRank", productSaleRankService.getProductRankAverage(productSale));
		modelAndView.addObject("rankRate", productSaleRankService.getProductRankInfos(productSale));
		return modelAndView;
	}
	
	/**
	 * 跳转新建评论页面
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 * @throws CloneNotSupportedException 
	 */
	@RequestMapping(value = "/{id}/comment/_new", method = RequestMethod.GET)
	public ModelAndView comment(
			@PathVariable("id") Long id,
			@RequestParam(value = "quoteCommentId", required = false) String quoteCommentId,
			HttpServletResponse response) throws IOException, CloneNotSupportedException {

		ProductSale productSale = productService.getProductSale(id);
		if (productSale == null) {
			LOG.info("查询的商品  " + id + "不 存在");
			response.sendError(HttpStatus.SC_NOT_FOUND);
			return null;
		}
		ModelAndView modelAndView = new ModelAndView("/product/comment/_new");
		modelAndView.addObject(PRODUCT_SALE, productSale);
		modelAndView.addObject(RANK_COUNT, productSaleRankService.findRankCount(productSale));
		modelAndView.addObject(COMMENT_COUNT, customerCommentService.getCountByProductSale(productSale));
		modelAndView.addObject("aveRank", productSaleRankService.getProductRankAverage(productSale));//商品平均分
		//加入被引入的评论内容
		if(!StringUtils.isBlank(quoteCommentId)){
			modelAndView.addObject(QUOTE_CUSTOMER_COMMENT, customerCommentService.get(Long.valueOf(quoteCommentId)));
		}
		return modelAndView;
	}

	/**
	 * 评论是否有用
	 * 
	 * @param commentId
	 * @param isUseful
	 * @param customer
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/comment/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long commentId,
			@RequestParam(value = "isUseful") boolean isUseful,
			@MyInject Customer customer, HttpServletResponse response)
			throws IOException {
		CustomerComment customerComment = customerCommentService.get(commentId);
		if (customerComment == null) {
			LOG.info("没有id为" + commentId + "评论");
			response.sendError(HttpStatus.SC_NOT_FOUND);
			return null;
		}
		customerCommentService.updateCustomerComment(customerComment, customer,
				isUseful);
		if (isUseful) {
			ModelAndView modelAndView = new ModelAndView("/product/useful");
			modelAndView.addObject("useful", customerComment.getUsefulCount());
			return modelAndView;
		}
		ModelAndView modelAndView = new ModelAndView("/product/useless");
		modelAndView.addObject("useless", customerComment.getUselessCount());
		return modelAndView;

	}
	
	/**
	 * 对商品评分.
	 * 
	 * @param score
	 * @param id
	 * @param customer
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/{id}/rank", method = RequestMethod.GET)
	public ModelAndView rank(@RequestParam(value = "score") short score,
			@PathVariable("id") Long id, @MyInject Customer customer,
			HttpServletResponse response) throws IOException {
		ModelAndView modelAndView = new ModelAndView("/product/comment");
		ProductSale productSale = productService.getProductSale(id);
		if (productSale == null) {
			LOG.info(NOT_FOUND_MESSAGE+ id);
			response.sendError(HttpStatus.SC_NOT_FOUND);
			return null;
		}
		ProductSaleRank rank = new ProductSaleRank();
		rank.setRank(score);
		rank.setProductSale(productSale);
		rank.setRankTime(new Date());
		productSaleRankService.save(rank);
		modelAndView.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		modelAndView.addObject(PRODUCT_SALE, productSale);
		modelAndView.addObject(ControllerConstant.MESSAGE_KEY, "评分成功");
		return modelAndView;
	}
}
