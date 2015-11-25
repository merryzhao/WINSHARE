package com.winxuan.ec.support.web.resolver;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.model.shoppingcart.Shoppingcart;
import com.winxuan.ec.model.shoppingcart.ShoppingcartItem;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.shoppingcart.ShoppingcartService;
import com.winxuan.ec.support.web.resolver.model.Track;
import com.winxuan.framework.util.web.CookieUtils;

/**
 * 
 * @author sunflower
 *
 */
@Service("shoppingcartResolverService")
@Transactional(rollbackFor = Exception.class)
public class ShoppingcartResolverServiceImpl implements
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
			}
		} else {// 已登录用户
			if (shoppingcart == null) {// 不存在购物车
				shoppingcart = findShoppingcartByCustomer(response, customer);
			} else {
				if (shoppingcart.getItemList() != null
						&& shoppingcart.getItemList().size() > 0) {// 存在购物车条目
					Shoppingcart sc = findShoppingcartByCustomer(response,
							customer);
					
					for (ShoppingcartItem item : shoppingcart.getItemList()) {
						Long psid = item.getProductSaleId();
						boolean find = false;
						if(sc.getItemList()!=null)
						{
						for(ShoppingcartItem it : sc.getItemList()){
							if(psid.longValue()==it.getProductSaleId().longValue()){
								find = true;
								it.setQuantity(item.getQuantity());
								break;
							}
						}
						}
						if(!find){
							sc.add(item);
						}
					}
					return sc;
				} else {// 不存在购物车条目，取该用户的购物车
					shoppingcart = findShoppingcartByCustomer(response,
							customer);
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
