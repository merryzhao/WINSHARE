package com.winxuan.ec.admin.controller.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.product.ProductMerge;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.service.productMerge.ProductJiuyueService;
import com.winxuan.ec.service.productMerge.ProductMergeService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.cache.CacheManager;

/**
 * ******************************
 * 
 * @author:cast911
 * @lastupdateTime:2013-4-27 上午11:14:58 --! 商品数据合并
 ******************************** 
 */

@Controller
@RequestMapping("/product")
public class ProductDataMergeController {

	@Autowired
	ProductService productService;

	@Autowired
	ProductMergeService jiuyueProductMergeServiceImpl;

	@Autowired
	CacheManager cacheManager;

	@Autowired
	ProductJiuyueService productJiuyueService;

	@RequestMapping("/data")
	public ModelAndView getProductData(@MyInject Employee operator,
			@RequestParam(value = "prevId", required = false) Long prevId) {
		ModelAndView mav = new ModelAndView("/product/productData");
		Map<ProductSale, List<ProductSale>> data = jiuyueProductMergeServiceImpl
				.fetchMergeItem();
		mav.addObject("data", data);
		mav.addObject("prevId", prevId);
		if(MapUtils.isNotEmpty(data) ){
			this.putDate(operator, (HashMap<ProductSale, List<ProductSale>>) data);
		}
		return mav;
	}

	@RequestMapping("/recoverData")
	public ModelAndView getPreviousData(@MyInject Employee operator,
			@RequestParam(value = "psId", required = true) Long psId) {
		ModelAndView mav = new ModelAndView("/product/productData");
		Map<ProductSale, List<ProductSale>> data = this.getDate(operator, psId);
		if (MapUtils.isEmpty(data)) {
			data = jiuyueProductMergeServiceImpl.fetchMergeItem();
		} else {
			Set<ProductSale> productSaleSet = data.keySet();
			if (CollectionUtils.isNotEmpty(productSaleSet)) {
				for (ProductSale productSale : productSaleSet) {
					jiuyueProductMergeServiceImpl.cancelOperate(operator,
							productSale);
				}
			}
		}
		mav.addObject("data", data);
		return mav;
	}

	@RequestMapping(value = "/merge", method = RequestMethod.POST)
	public ModelAndView mergeProduct(@MyInject Employee operator,
			ProductDataForm productDataForm) {
		ProductMerge productMerge = new ProductMerge();
		productMerge.setWxProduct(productService.getProductSale(productDataForm
				.getPsId()));
		productMerge.setMergeProduct(productService
				.getProductSale(productDataForm.getYuePsId()));
		productMerge.setMerge(productDataForm.getIsMerge());
		productMerge.setIgnore(productDataForm.getIsIgnore());
		jiuyueProductMergeServiceImpl.mergeItem(operator, productMerge);
		return new ModelAndView("/product/result");
	}

	private String cacheKey(Employee operator, Long psid) {
		return DigestUtils.md5Hex(psid + operator.getId() + operator.getName()
				+ ".PRODUCT_MERGE");
	}

	private void putDate(Employee operator,
			HashMap<ProductSale, List<ProductSale>> data) {
		HashMap<Long, List<Long>> map = new HashMap<Long, List<Long>>();
		Set<ProductSale> productSaleSet = data.keySet();
		for (ProductSale productSale : productSaleSet) {
			Long id = productSale.getId();
			List<Long> list = new ArrayList<Long>();
			List<ProductSale> psList = data.get(productSale);
			for (ProductSale ps : psList) {
				list.add(ps.getId());
			}
			map.put(id, list);
			cacheManager.put(this.cacheKey(operator, id), map,
					Integer.MAX_VALUE);
		}

	}

	private Map<ProductSale, List<ProductSale>> getDate(Employee operator,
			Long psId) {
		HashMap<Long, List<Long>> map = (HashMap<Long, List<Long>>) cacheManager
				.get(this.cacheKey(operator, psId));
		Map<ProductSale, List<ProductSale>> data = new HashMap<ProductSale, List<ProductSale>>();
		Set<Long> productSaleSet = map.keySet();
		for (Long id : productSaleSet) {
			ProductSale ps = productService.getProductSale(id);
			this.appendImage(ps);
			List<Long> idList = map.get(id);
			List<ProductSale> productSalelist = new ArrayList<ProductSale>();
			for (Long psid : idList) {
				ProductSale productSale = productService.getProductSale(psid);
				productSalelist.add(productSale);
			}
			data.put(ps, productSalelist);
		}
		return data;
	}

	/**
	 * 添加9月网的图片
	 * 
	 * @param ps
	 */
	private void appendImage(ProductSale ps) {
		if (null == ps) {
			return;
		}
		String url = productJiuyueService.fetchJiuyueMiddleImg(ps);
		ps.setImageUrl(url);
	}

}
