package com.winxuan.ec.front.controller.product;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


import com.winxuan.ec.model.product.ProductJiuyue;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.productMerge.ProductJiuyueService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;
import org.apache.http.HttpStatus;

/**
 * 处理电子书定向跳转过来的URL
 * 
 * @author 
 * @version 1.0,2011-9-29
 */

@Controller
@RequestMapping(value = "/productebook")
public class VisitEbookController {
    

	

    @Autowired
    private ProductJiuyueService productJiuyueService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView view(@MyInject Customer customer,
			@PathVariable("id") Long id, @MyInject Pagination pagination,
			HttpServletResponse response) throws IOException, CloneNotSupportedException {
       
		ProductJiuyue productJiuyue = productJiuyueService.getProductSaleBySeptember(id);
		
		if(productJiuyue==null)
		{
		 //如果查不到对应电子书，则跳转到文轩网电子书首页	
		  return new ModelAndView(new RedirectView("http://ebook.winxuan.com"));
		}
		ProductSale productSale = productJiuyue.getProductSale();
		if (productSale == null) {
			response.sendError(HttpStatus.SC_NOT_FOUND);
			return null;
		}
		if (!productSale.isShow()) {
			response.sendError(HttpStatus.SC_GONE);
			return null;
		}
       
       
		
		return new ModelAndView(new RedirectView("http://www.winxuan.com/product/"+productSale.getId()));
	}
}
