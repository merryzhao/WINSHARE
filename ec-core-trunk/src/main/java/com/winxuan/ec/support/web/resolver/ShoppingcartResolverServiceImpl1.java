package com.winxuan.ec.support.web.resolver;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.model.shoppingcart.Shoppingcart;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.shoppingcart.ShoppingcartService;
import com.winxuan.ec.support.web.resolver.model.Track;
import com.winxuan.framework.util.RandomCodeUtils;
import com.winxuan.framework.util.web.CookieUtils;

/**
 * 
 * @author sunflower
 *
 */
@Transactional(rollbackFor = Exception.class)
public class ShoppingcartResolverServiceImpl1 implements
		ShoppingcartResolverService {

	private static final String DEFAULT_PRINCIPALCOOKIE_NAME = "p";
	private static final String DEFAULT_ENCODING = "utf-8";
	private static final String SPLIT = "&";

	@Autowired
	private ShoppingcartService shoppingcartService;

	@Autowired
	private CustomerService customerService;

	@Override
	public Shoppingcart resolve(String id, HttpServletResponse response) {
		
		Customer customer = getCustomer();
		Shoppingcart shoppingcart = shoppingcartService.get(id);
		if (customer == null) {// 未登录用户
			if (shoppingcart == null) {// 没有购物车
				shoppingcart = new Shoppingcart();
				shoppingcart.setId(id);
				Date now = new Date();
				shoppingcart.setCreateTime(now);
				shoppingcart.setUseTime(now);
			} else {// 有购物车
				if (shoppingcart.getCustomer() != null) {// 该购物车是某个登录用户的购物车，创建一个新的购物车
					id = RandomCodeUtils.create(Track.COOKIE_VALUE_MODE,
							Track.COOKIE_VALUE_LENGTH, true);
					shoppingcart = new Shoppingcart();
					shoppingcart.setId(id);
					Date now = new Date();
					shoppingcart.setCreateTime(now);
					shoppingcart.setUseTime(now);
					Cookie cookie = new Cookie(Track.PERSISTENCE_COOKIE_NAME,
							id);
					cookie.setDomain(Track.COOKIE_DOMAIN);
					cookie.setPath(Track.COOKIE_PATH);
					cookie.setMaxAge(Track.PERSISTENCE_COOKIE_AGE);
					CookieUtils.writeCookie(response, cookie);
				}
			}
		} else {// 已登录用户
			if (shoppingcart == null) {// 不存在购物车
				shoppingcart = findShoppingcartByCustomer(response, customer);
			} else {// 存在购物车
				if (shoppingcart.getCustomer() == null) {// 该购物车不是登录用户的购物车，获取该用户的购物车
					if (shoppingcart.getItemList() != null
							&& shoppingcart.getItemList().size() > 0) {// 存在购物车条目
						shoppingcart.setCustomer(customer);
						Shoppingcart sc = findShoppingcartByCustomer(response,
								customer);
						if(sc!=null&&sc.getItemList()!=null){
							shoppingcart.add(sc.getItemList());
						}
					} else {// 不存在购物车条目，取该用户的购物车
						shoppingcart = findShoppingcartByCustomer(response,
								customer);
					}
				} else {// 该购物车是某个登录用户的购物车
					if (shoppingcart.getCustomer().getId() != customer.getId()) {// 该购物车不是当前登录用户的购物车
						shoppingcart = findShoppingcartByCustomer(response,
								customer);
					}
				}
			}
		}
		return shoppingcart;
	}
	

	private Shoppingcart findShoppingcartByCustomer(
			HttpServletResponse response, Customer customer) {
		Shoppingcart shoppingcart;
		shoppingcart = shoppingcartService.findShoppingcartByCustomer(customer);
		Cookie cookie = new Cookie(Track.PERSISTENCE_COOKIE_NAME,
				shoppingcart.getId());
		cookie.setDomain(Track.COOKIE_DOMAIN);
		cookie.setPath(Track.COOKIE_PATH);
		cookie.setMaxAge(Track.PERSISTENCE_COOKIE_AGE);
		CookieUtils.writeCookie(response, cookie);
		return shoppingcart;
	}

	private Customer getCustomer() {
		Cookie cookie = CookieUtils.getCookie(DEFAULT_PRINCIPALCOOKIE_NAME);
		if (cookie != null) {
			String cookieValue = cookie.getValue();
			try {
				cookieValue = URLDecoder.decode(cookieValue, DEFAULT_ENCODING);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			String[] values = cookieValue.split(SPLIT);
			Integer cookieId = new Integer(values[0]);
			return (Customer) customerService.get(cookieId);
		}
		return null;
	}

}
