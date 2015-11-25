package com.winxuan.ec.front.controller.product;

import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.front.controller.Constant;
import com.winxuan.ec.model.customer.CustomerVisited;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.util.RandomCodeUtils;
import com.winxuan.framework.util.web.CookieUtils;


/**
 * 
 * @author cast911
 * @description:
 * @lastupdateTime:2012-8-22下午03:37:44
 * -_-!
 */
@Controller
@RequestMapping(value = "/product")
public class CommondAndQuestionController {
	//private static final Log LOG = LogFactory.getLog(CommondAndQuestionController.class);

	/**
	 * 商品种类杂志
	 */

	private static final int MAX_RESULT = 10;

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;


	

	
	
	
	
	
	/**
	 * 保存浏览记录用的这个可以确定 --cast911
	 * 
	 * @param customer
	 * @param productSaleId
	 * @param maxResults
	 * @param client
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/{id}/visit", method = RequestMethod.GET)
	public ModelAndView visit(
			@MyInject Customer customer,
			@PathVariable("id") Long productSaleId,
			@RequestParam(value = "size", required = false, defaultValue = "10") int maxResults,
			@CookieValue(value = Constant.COOKIE_CLIENT_NAME, required = false) String client,
			@CookieValue(value = Constant.COOKIE_SESSION_NAME, required = false) String session) {
		List<ProductSale> visitedList = null;
		if (StringUtils.isBlank(client)) {
			client = writeCookie(Constant.COOKIE_CLIENT_NAME,
					Constant.COOKIE_CLIENT_AGE);
		} else {
			visitedList = customerService.findVisitedList(client, maxResults);
		}
		if (StringUtils.isBlank(session)) {
			session = writeCookie(Constant.COOKIE_SESSION_NAME, -1);
		}
		ProductSale productSale = productService.getProductSale(productSaleId);
		if (productSale != null) {
			customerService.visit(new CustomerVisited(customer, client,
					session, productSale));
		}
		return new ModelAndView("/product/visit", "visitedList", visitedList);
	}

	@RequestMapping(value = "/visit/list", method = RequestMethod.GET)
	public ModelAndView list(
			@CookieValue(value = Constant.COOKIE_CLIENT_NAME, required = false) String client) {
		ModelAndView modelAndView = new ModelAndView("/product/visit");
		if (StringUtils.isBlank(client)) {
			return modelAndView;
		}
		List<ProductSale> visitedList = customerService.findVisitedList(client,
				MAX_RESULT);
		modelAndView.addObject("visitedList", visitedList);
		return modelAndView;
	}

	
	/**
	 * 清除浏览历史
	 * 
	 * @param client
	 * @return
	 */
	@RequestMapping(value = "/cleanVisited", method = RequestMethod.GET)
	public ModelAndView clean(
			@CookieValue(value = Constant.COOKIE_CLIENT_NAME, required = false) String client) {
		ModelAndView modelAndView = new ModelAndView("/product/clean");
		if (!StringUtils.isBlank(client)) {
			customerService.cleanVisitedList(client);
		}
		modelAndView.addObject("success", true);
		return modelAndView;
	}
	
	private String writeCookie(String name, int maxAge) {
		String value = RandomCodeUtils.create(Constant.COOKIE_RANDOM_CODE_MODE,
				Constant.COOKIE_RANDOM_CODE_LENGTH);
		Cookie cookie = new Cookie(name, value);
		cookie.setDomain(Constant.COOKIE_DOMAIN);
		cookie.setMaxAge(maxAge);
		cookie.setPath(Constant.COOKIE_PATH);
		CookieUtils.writeCookie(cookie);
		return value;
	}
	

}
