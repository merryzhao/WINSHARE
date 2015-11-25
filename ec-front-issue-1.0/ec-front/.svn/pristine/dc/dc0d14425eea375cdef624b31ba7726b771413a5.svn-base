/*
 * @(#)ShoppingcartController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.shoppingcart;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.front.controller.Constant;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shoppingcart.Shoppingcart;
import com.winxuan.ec.model.shoppingcart.ShoppingcartItem;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.service.promotion.PromotionService;
import com.winxuan.ec.service.shoppingcart.ShoppingcartService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.web.resolver.model.Track;
import com.winxuan.framework.util.web.CookieUtils;
import com.winxuan.framework.util.web.WebContext;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-8-4
 */
@Controller
@RequestMapping(value = "/shoppingcart")
public class ShoppingcartController {
	private static final String STATUS = "status";
	private static final short STATUS_SUCCESS = 1;
	private static final short STATUS_NOT_FOUND = 2;
	private static final short STATUS_NOT_ENOUGH = 3;
	private static final short STATUS_OFFSHELF = 4;
	private static final short STATUS_WRONG_NUMBER = 5;
	private static final short STATUS_WRONG_PARAM = 6;
	private static final short STATUS_NOT_BY_ENOUGH = 7;
	private static final String AVALIBLE_QUANTITY = "avalibleQuantity";

	@Autowired
	private ShoppingcartService shoppingcartService;

	@Autowired
	private ProductService productService;

	@Autowired
	private PromotionService promotionService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView view(Shoppingcart shoppingcart) {
		return render(shoppingcart, STATUS_SUCCESS, -1);
	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public ModelAndView modify(
			@RequestParam("p") Long[] productSaleIdArray,
			@RequestParam(value = "qty", required = false) String[] quantityArrayStr,
			@RequestParam(value = "opt", required = false, defaultValue = "add") String opt,
			Shoppingcart shoppingcart) {
		short status = STATUS_SUCCESS;
		if (quantityArrayStr != null
				&& productSaleIdArray.length != quantityArrayStr.length) {
			status = STATUS_WRONG_PARAM;
		}
		int[] quantityArray = new int[productSaleIdArray.length];
		if (quantityArrayStr == null) {
			for (int i = 0; i < quantityArray.length; i++) {
				quantityArray[i] = 1;
			}
		}else{
			for(int i = 0; i < quantityArrayStr.length; i++){
				try {
					quantityArray[i] = Integer.parseInt(quantityArrayStr[i]);
				} catch (NumberFormatException e) {
					status = STATUS_WRONG_PARAM;
				}
			}
		}
		int avalibleQuantity = -1;
		if (status == STATUS_SUCCESS) {
			for (int i = 0; i < quantityArray.length; i++) {
				int quantity = quantityArray[i];
				Long productSaleId = productSaleIdArray[i];
				ProductSale productSale = productService
						.getProductSale(productSaleId);
				if (productSale == null) {
					status = STATUS_NOT_FOUND;
				} else {
					int buyQuantity = 0;
					if (shoppingcart.getCustomer() != null) {
						buyQuantity = orderService.getPurchaseQuantityToday(
								shoppingcart.getCustomer(), productSale);
					}
					productSale.setPurchasedQuantity(buyQuantity);
					productSale.setPurchasedQuantityAll(orderService.getPurchaseQuantityToday(productSale));
					avalibleQuantity = productSale.getAvalibleQuantity();
					int canPurchaseQuantity = productSale
							.getCanPurchaseQuantity();
					ShoppingcartItem item = shoppingcart.getItem(productSale);
					int itemQuantity = item == null ? 0 : item.getQuantity();
					int updateQuantity = "add".equals(opt) ? itemQuantity
							+ quantity : quantity;
					if (updateQuantity < 0) {
						status = STATUS_WRONG_NUMBER;
					} else if (!productSale.canSale() && updateQuantity != 0) {
						status = STATUS_OFFSHELF;
					} else if (item == null && updateQuantity > 0
							|| item != null && updateQuantity >= 0) {
						if (updateQuantity > avalibleQuantity
								&& canPurchaseQuantity == avalibleQuantity) {
							status = STATUS_NOT_ENOUGH;
						} else if (updateQuantity > canPurchaseQuantity) {
							status = STATUS_NOT_BY_ENOUGH;
							avalibleQuantity = canPurchaseQuantity;
						}
						shoppingcartService.add(shoppingcart, productSale,
								updateQuantity);
					}
				}
			}
		}
		return render(shoppingcart, status, avalibleQuantity);
	}

	private ModelAndView render(Shoppingcart shoppingcart, short status,
			int avalibleQuantity) {
		ModelAndView modelAndView = new ModelAndView("/shoppingcart/view");
		promotionService.setupShoppingcartPromotion(shoppingcart);
		modelAndView.addObject("shoppingcart", shoppingcart);
		modelAndView.addObject(STATUS, status);
		if (avalibleQuantity != -1) {
			modelAndView.addObject(AVALIBLE_QUANTITY, avalibleQuantity);
		}
		updateClientCookie(shoppingcart);
		return modelAndView;
	}

	private void updateClientCookie(Shoppingcart shoppingcart) {
		Cookie cookie = new Cookie(Constant.COOKIE_SHOPPINGCART_COUNT_NAME,
				String.valueOf(shoppingcart.getCount()));
		cookie.setMaxAge(Constant.COOKIE_CLIENT_AGE);
		cookie.setPath(Constant.COOKIE_PATH);
		cookie.setDomain(Constant.COOKIE_DOMAIN);
		CookieUtils.writeCookie(cookie);
	}

	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public ModelAndView select(Shoppingcart shoppingcart) {
		promotionService.setupShoppingcartPromotion(shoppingcart);
		if (shoppingcart == null || shoppingcart.isEmpty()) {
			return new ModelAndView("redirect:/shoppingcart");
		} else if (shoppingcart.isSplited()) {
			ModelAndView modelAndView = new ModelAndView("/shoppingcart/select");
			modelAndView.addObject("shoppingcart", shoppingcart);
			return modelAndView;
		} else {
			return new ModelAndView("redirect:/customer/checkout");
		}
	}

	@RequestMapping(value = "/addAllFromCart/{id}")
	public ModelAndView addAllFromCart(Shoppingcart shoppingcart,
			@PathVariable("id") String orderId) {
		Order order = orderService.get(orderId);
		for (ProductSale ps : order.getProductSales()) {
			shoppingcartService.add(shoppingcart, ps, 1);
		}
		ModelAndView modelAndView = new ModelAndView(
				"redirect:/customer/favorite");
		return modelAndView;
	}

	@RequestMapping(value = "/merge")
	public ModelAndView mergeShoppingcart(@MyInject Customer customer,
			@RequestParam(value = "opt", required = true) int opt) {
		ModelAndView modelAndView = new ModelAndView("/shoppingcart/merge");
		if (customer == null || (opt != 1/** 合并购物车 */
		&& opt != 2/** 不合并购物车 */
		&& opt != 3/** 添加到收藏夹 */
		)) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, "参数不对");
			return modelAndView;
		}
		HttpServletRequest request = WebContext.currentRequest();
		Cookie cookie = CookieUtils.getCookie(request,
				Constant.COOKIE_CLIENT_NAME);
		Shoppingcart shoppingcart = shoppingcartService.get(cookie.getValue());
		Shoppingcart sc = shoppingcartService
				.findShoppingcartByCustomer(customer);
		if (opt == 1) {
			if (shoppingcart.getItemList() != null
					&& shoppingcart.getItemList().size() > 0) {
				for (ShoppingcartItem item : shoppingcart.getItemList()) {
					shoppingcartService.add(sc, item.getProductSale(),
							item.getQuantity());
				}
			}
		} else if (opt == 2 || opt == 3) {
			Set<ShoppingcartItem> temps = new HashSet<ShoppingcartItem>(
					sc.getItemList());
			for (ShoppingcartItem item : temps) {
				shoppingcartService.removeProduct(sc, item.getProductSale());
				if (opt == 3) {
					customerService.addToFavorite(customer,
							item.getProductSale());
				}
			}
			if (shoppingcart.getItemList() != null
					&& shoppingcart.getItemList().size() > 0) {
				for (ShoppingcartItem item : shoppingcart.getItemList()) {
					shoppingcartService.add(sc, item.getProductSale(),
							item.getQuantity());
				}
			}
		}
		cookie = new Cookie(Constant.COOKIE_CLIENT_NAME, sc.getId());
		cookie.setDomain(Track.COOKIE_DOMAIN);
		cookie.setPath(Track.COOKIE_PATH);
		cookie.setMaxAge(Track.PERSISTENCE_COOKIE_AGE);
		CookieUtils.writeCookie(WebContext.currentResponse(), cookie);
		return render(sc, STATUS_SUCCESS, -1);
	}

	@RequestMapping(value = "/queryShoppingcart")
	public ModelAndView queryShoppingcart(@MyInject Customer customer) {
		ModelAndView modelAndView = new ModelAndView(
				"/shoppingcart/shoppingcart");
		if (customer == null) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, "参数不对");
			return modelAndView;
		}
		Shoppingcart sc = shoppingcartService
				.findShoppingcartByCustomer(customer);

		HttpServletRequest request = WebContext.currentRequest();
		Cookie cookie = CookieUtils.getCookie(request,
				Constant.COOKIE_CLIENT_NAME);
		Shoppingcart shoppingcart = shoppingcartService.get(cookie.getValue());
		if(shoppingcart.getId().equals(sc.getId())){
			modelAndView.addObject(ControllerConstant.RESULT_KEY, 0);
			return modelAndView;
		}
		if (sc.getItemList() != null && sc.getItemList().size() > 0) {
			int num = sc.getItemList().size();
			Set<ShoppingcartItem> itemList = new HashSet<ShoppingcartItem>(sc.getItemList());
			for (ShoppingcartItem item : shoppingcart.getItemList()) {
				Long psid = item.getProductSaleId();
				for(ShoppingcartItem it : itemList){
					Long id = it.getProductSaleId();
					if(psid.longValue()==id.longValue()){
						num = num - 1;
						//sc.getItemList().remove(it);
						shoppingcartService.removeProduct(sc, it.getProductSale());
					}
				}
			}
			modelAndView.addObject(ControllerConstant.RESULT_KEY, num);
			modelAndView.addObject("itemList", sc.getItemList());
		} else {
			modelAndView.addObject(ControllerConstant.RESULT_KEY, 0);
		}
		return modelAndView;
	}
}
