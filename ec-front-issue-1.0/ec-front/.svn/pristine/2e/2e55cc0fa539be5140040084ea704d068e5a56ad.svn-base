/*
 * @(#)CommentController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.customer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.customer.CustomerBought;
import com.winxuan.ec.model.customer.CustomerComment;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleRank;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.channel.ChannelService;
import com.winxuan.ec.service.customer.CustomerCommentService;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.service.society.KeenessService;
import com.winxuan.ec.service.verifycode.VerifyCodeService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.StringUtils;

/**
 * 会员中心评论
 * 
 * @author HideHai
 * @version 1.0,2011-10-11
 */
@Controller
@RequestMapping(value = "/customer/comment")
public class CommentController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerCommentService customerCommentService;
	@Autowired
	private ProductService productService;
	@Autowired
	private VerifyCodeService verifyCodeService;
	@Autowired
	private KeenessService keenessService;
	@Autowired
	private ChannelService channelService;

	@RequestMapping(value="/product",method=RequestMethod.GET)
	ModelAndView prodcuts(@MyInject Customer customer,@MyInject Pagination pagination){
		ModelAndView modelAndView = new ModelAndView("/customer/comment/products");
		long productCommentCount = MagicNumber.ZERO;	//已购商品数量
		long myCommentCount = MagicNumber.ZERO;			//我的评论数量
		long myUseFulCount = MagicNumber.ZERO;			//有用的评论数量
		Map<String, Object> parameters = new HashMap<String, Object>();
		List<CustomerBought> customerBoughts = 				//用户已购商品
			customerService.findBought(customer, null, parameters, (short) 0, pagination);
		productCommentCount = pagination.getCount();
		myCommentCount = customerService.getCommentCount(customer);
		long[] styleCount = customerService.getCommentTypeCount(customer);
		myUseFulCount = styleCount==null ? 0 : styleCount[0];
		if(customerBoughts != null && !customerBoughts.isEmpty()){
			long count = MagicNumber.ZERO;
			for(CustomerBought bought : customerBoughts){
				count = customerCommentService.getCountByProductSaleAndCustomer(bought.getProductSale(), customer);
				bought.setHasComment(count==0?false:true);
			}
		}
		modelAndView.addObject("productCommentCount", productCommentCount);
		modelAndView.addObject("myCommentCount", myCommentCount);
		modelAndView.addObject("myUseFulCount", myUseFulCount);
		modelAndView.addObject("customerBoughts", customerBoughts);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}

	@RequestMapping(value="/my",method=RequestMethod.GET)
	ModelAndView my(@MyInject Customer customer,@MyInject Pagination pagination){
		ModelAndView modelAndView = new ModelAndView("/customer/comment/my");
		long productCommentCount = MagicNumber.ZERO;	//已购商品数量
		long myCommentCount = MagicNumber.ZERO;			//我的评论数量
		long myUseFulCount = MagicNumber.ZERO;			//有用的评论数量
		Map<String, Object> parameters = new HashMap<String, Object>();
		productCommentCount = customerService.findBoughtCount(customer, null, parameters);
		myCommentCount = customerService.getCommentCount(customer);
		long[] styleCount = customerService.getCommentTypeCount(customer);
		myUseFulCount = styleCount==null ? 0 : styleCount[0];
		List<CustomerComment> myComments = customerService.findComments(customer, pagination, Short.valueOf("0"));
		modelAndView.addObject("productCommentCount", productCommentCount);
		modelAndView.addObject("myCommentCount", myCommentCount);
		modelAndView.addObject("myUseFulCount", myUseFulCount);
		modelAndView.addObject("comments", myComments);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}

	@RequestMapping(value="/useful",method=RequestMethod.GET)
	ModelAndView useful(@MyInject Customer customer,@MyInject Pagination pagination){
		ModelAndView modelAndView = new ModelAndView("/customer/comment/useful");
		long productCommentCount = MagicNumber.ZERO;	//已购商品数量
		long myCommentCount = MagicNumber.ZERO;			//我的评论数量
		long myUseFulCount = MagicNumber.ZERO;			//有用的评论数量
		Map<String, Object> parameters = new HashMap<String, Object>();
		productCommentCount = customerService.findBoughtCount(customer, null, parameters);
		myCommentCount = customerService.getCommentCount(customer);
		long[] styleCount = customerService.getCommentTypeCount(customer);
		myUseFulCount = styleCount==null ? 0 : styleCount[0];
		List<CustomerComment> myComments = customerService.findUsefulComments(customer, pagination, Short.valueOf("0"));
		modelAndView.addObject("productCommentCount", productCommentCount);
		modelAndView.addObject("myCommentCount", myCommentCount);
		modelAndView.addObject("myUseFulCount", myUseFulCount);
		modelAndView.addObject("comments", myComments);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}

	@RequestMapping(value="/{productSale}",method=RequestMethod.GET)
	ModelAndView add(@MyInject Customer customer,@PathVariable("productSale") long productSaleId){
		ModelAndView modelAndView = new ModelAndView("/customer/comment/new");
		ProductSale productSale = productService.getProductSale(productSaleId);
		modelAndView.addObject("prodcutSale", productSale);
		return modelAndView;
	}

	@RequestMapping(value="/{productSale}",method=RequestMethod.POST)
	ModelAndView create(@MyInject Customer customer,
			@PathVariable("productSale")long productSaleId,
			@CookieValue(value="verify_number",required=true) String verifyNumber,
			@RequestParam(value="title",required=true) String title,
			@RequestParam(value="rank",required=true)String rank,
			@RequestParam(value="content",required=true)String content,
			@RequestParam(value="sRand",required=true) String sRand){
		ModelAndView modelAndView = new ModelAndView("/customer/comment/create");
		if(verifyCodeService.verify(sRand, verifyNumber)){
			ProductSale productSale = productService.getProductSale(productSaleId);
			if(productSale != null && !StringUtils.isNullOrEmpty(title)){
				ProductSaleRank productSaleRank = new ProductSaleRank();
				CustomerComment customerComment = new CustomerComment();
				customerComment.setCustomer(customer);
				customerComment.setProductSale(productSale);
				customerComment.setTitle(StringUtils.escapeSpecialLabel(keenessService.replaceRich(content)));
				customerComment.setContent(StringUtils.escapeSpecialLabel(keenessService.replaceRich(content)));
				customerComment.setCommentTime(new Date());
				customerComment.setRank(productSaleRank);
				customerComment.setChannel(channelService.get(Channel.CHANNEL_EC));
				productSaleRank.setProductSale(productSale);
				productSaleRank.setRank(Integer.parseInt(rank));
				productSaleRank.setRankTime(new Date());
				customerComment.setRank(productSaleRank);
//				productSaleRank.setComment(customerComment);
				//由于rankList做了集合缓存，在增加评分时，加至ranklist中，将来评分改为数据库直接读取后，这里可以删除
				productSale.getRankList().add(productSaleRank);
				int points = customerCommentService.saveComment(customerComment,productSale);
				modelAndView.addObject("customerComment", customerComment);
				modelAndView.addObject("points", points);
				return modelAndView;
			}
			modelAndView.addObject("message", "找不到被评论的商品或者评论标题为空!");
		}else{
			modelAndView.addObject("message", "验证码错误!");
		}
		modelAndView.setViewName("/customer/comment/create_Failure");
		return modelAndView;
	}



}
