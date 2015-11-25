/**
 * 
 */
package com.winxuan.ec.admin.controller.product;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.exception.ProductSaleStockException;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleRapid;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.product.ProductSaleRapidService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.services.pss.model.vo.ProductSaleStockVo;

/**
 * @author zhousl
 *
 * 2013-9-3
 */
@Controller
@RequestMapping(value="/productsalerapid")
public class ProductSaleRapidController {
	
	@Autowired
	private ProductSaleRapidService productSaleRapidService;
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create(){
		return new ModelAndView("/product/productsalerapid_create");
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView create(@RequestParam("productSaleId")Long productSaleId,
			@RequestParam("amount")Integer amount,
			@MyInject Employee employee) throws ProductSaleStockException{
		ModelAndView mav = new ModelAndView("/product/productsalerapid_create");
		ProductSale productSale = productService.getProductSale(productSaleId);
		if(productSale == null){
			renderErrorInfo(mav, "商品不存在");
			return mav;
		}
		if (Shop.WINXUAN_SHOP.compareTo(productSale.getShop().getId()) != MagicNumber.ZERO) {
			renderErrorInfo(mav, "不是文轩商品");
			return mav;
		}
		if(productSaleRapidService.getByProductSale(productSale) != null){
			renderErrorInfo(mav, "已存在该快速分拨商品不存在");
			return mav;
		}
		if(amount == null || amount <= 0){
			renderErrorInfo(mav, "采购量应大于0");
			return mav;
		}
		ProductSaleRapid productSaleRapid = new ProductSaleRapid();
		productSaleRapid.setProductSale(productSale);
		productSaleRapid.setAmount(amount);
		productSaleRapid.setCreateTime(new Date());
		productSaleRapid.setCreator(employee);
		productSaleRapidService.save(productSaleRapid);
		mav.setViewName("redirect:/productsalerapid/detail/" + productSaleId);
		return mav;
	}

	/**
	 * @param mav
	 */
	private void renderErrorInfo(ModelAndView mav, String message) {
		mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
		mav.addObject(ControllerConstant.MESSAGE_KEY, message);
	}
	
	@RequestMapping(value="/detail/{productSaleId}", method=RequestMethod.GET)
	public ModelAndView detail(@PathVariable("productSaleId")Long productSaleId){
		ModelAndView mav = new ModelAndView("/product/productsalerapid_detail");
		ProductSale productSale = productService.getProductSale(productSaleId);
		ProductSaleRapid productSaleRapid = productSaleRapidService.getByProductSale(productSale);
		getProductSaleStock(mav, productSale);
		mav.addObject("productSaleRapid", productSaleRapid);
		return mav;
	}

	/**
	 * @param mav
	 * @param productSale
	 */
	private void getProductSaleStock(ModelAndView mav, ProductSale productSale) {
		Set<ProductSaleStockVo> productSaleStocks = productSale.getProductSaleStockVos();
		if(CollectionUtils.isNotEmpty(productSaleStocks)){
			for(ProductSaleStockVo productSaleStock : productSaleStocks){
				if(productSaleStock.getProductSale().equals(productSale)){
					mav.addObject("productSaleStock", productSaleStock);
				}
			}
		}
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id")Long id){
		ModelAndView mav = this.detail(id);
		mav.setViewName("/product/productsalerapid_edit");
		return mav;
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public ModelAndView update(@RequestParam("productSaleId")Long productSaleId,
			@RequestParam("amount")Integer amount) throws ProductSaleStockException{
		ProductSale productSale = productService.getProductSale(productSaleId);
		ProductSaleRapid productSaleRapid = productSaleRapidService.getByProductSale(productSale);
		productSaleRapid.setAmount(amount);
		productSaleRapid.setTimeStamp(Calendar.getInstance());
		productSaleRapidService.update(productSaleRapid);
		return this.detail(productSaleId);
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public ModelAndView doSearch(){
		return new ModelAndView("/product/productsalerapid_search");
	}
	
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public ModelAndView search(@RequestParam("productSaleId")Long productSaleId){
		ModelAndView mav = new ModelAndView("/product/productsalerapid_search");
		ProductSale productSale = productService.getProductSale(productSaleId);
		if(productSale == null){
			renderErrorInfo(mav, "商品不存在");
			return mav;
		}
		ProductSaleRapid productSaleRapid = productSaleRapidService.getByProductSale(productSale);
		if(productSaleRapid == null){
			renderErrorInfo(mav, "该快速分拨商品不存在");
			return mav;
		}
		return this.detail(productSaleId);
	}
}
