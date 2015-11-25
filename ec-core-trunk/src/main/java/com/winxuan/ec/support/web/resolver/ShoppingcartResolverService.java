package com.winxuan.ec.support.web.resolver;

import javax.servlet.http.HttpServletResponse;

import com.winxuan.ec.model.shoppingcart.Shoppingcart;

/**
 * 
 * @author sunflower
 *
 */
public interface ShoppingcartResolverService {

	Shoppingcart resolve(String id, HttpServletResponse response);

}
