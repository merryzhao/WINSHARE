package com.winxuan.ec.admin.controller.product;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.enums.StockOriginEnum;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.MessageStock;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.StockLockRule;
import com.winxuan.ec.model.product.StockRule;
import com.winxuan.ec.model.product.StockRuleDc;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.product.ProductSaleStockService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.service.product.StockLockRuleService;
import com.winxuan.ec.service.product.StockRuleService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * @author liou
 * @version 2013-9-4:下午5:32:44
 * 
 */
@Controller
@RequestMapping("/stockrule")
public class StockRuleController {

	@Autowired
	private StockRuleService stockRuleService;

	@Autowired
	private CodeService codeService;
	
	@Autowired
	private StockLockRuleService stockLockRuleService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductSaleStockService productSaleStockService;
	
	public static final String RN = "\r\n";

	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public ModelAndView save() {
		ModelAndView andView = new ModelAndView("/product/stock_save");
		andView = getMultiterm(andView);
		return andView;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(StockRuleForm stockRuleForm, Employee employee) {
		ModelAndView andView = new ModelAndView("redirect:/stockrule/find");
		StockRule stockRule = stockRuleForm.setSaveStockRule(employee);
		stockRuleService.save(stockRule);
		return andView;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(StockRuleForm stockRuleForm, Employee employee) {
		ModelAndView andView = new ModelAndView("redirect:/stockrule/"+stockRuleForm.getId());
		List<StockRuleDc> stRuleDcs = stockRuleForm.getStRuleDcs();
		if(stRuleDcs!=null&&!stRuleDcs.isEmpty()){
			for (StockRuleDc ruleDc : stRuleDcs) {
				if (ruleDc.getId() != null) {
					stockRuleService.delete(ruleDc);
				}
			}
		}
		StockRule stockRule = stockRuleForm.setUpdateStockRule(employee);
		stockRuleService.update(stockRule);
		andView.addObject("type", "modify");
		return andView;
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public ModelAndView find(Pagination pagination) {
		ModelAndView andView = new ModelAndView("/product/stock_list");
		List<StockRule> stockRules = stockRuleService.find(pagination);
		andView.addObject("stockRules", stockRules).addObject("pagination",
				pagination);
		return andView;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView get(@PathVariable("id") Long id, String type) {
		ModelAndView andView = new ModelAndView();
		if ("edit".equals(type)) {
			andView = getMultiterm(andView);
			andView.setViewName("/product/stock_edit");
		} else if("modify".equals(type)){
			andView = getMultiterm(andView);
			andView.addObject("success", true);
			andView.setViewName("/product/stock_edit");
		}else {
			andView.setViewName("/product/stock_browse");
		}
		StockRule stockRule = stockRuleService.get(id);
		andView.addObject("stockRule", stockRule);
		return andView;
	}

	/**
	 * 获取渠道销售类型和库位
	 * 
	 * @param andView
	 * @return
	 */
	public ModelAndView getMultiterm(ModelAndView andView) {
		List<Code> supplyTypeList = codeService
				.findByParent(Code.PRODUCT_SALE_SUPPLY_TYPE);
		List<Code> dcList = codeService.findByParent(Code.DELIVERY_CENTER);
		andView.addObject("supplyTypeList", supplyTypeList)
				.addObject("dcList", dcList);
		return andView;
	}
	
	/**
	 * 验证渠道销售类型是否已有
	 * @param channelId
	 * @param supplyTypeId
	 * @return
	 */
	@RequestMapping(value="/verification",method=RequestMethod.GET)
	public ModelAndView isSupplyAndChannel(Long channelId,Long supplyTypeId){
		boolean isb = stockRuleService.isSupplyAndChannel(channelId, supplyTypeId);
		ModelAndView andView = new ModelAndView("/product/result");
		andView.addObject("isb", isb);
		return andView;
	}
	
	/**
	 * 编辑锁定库存
	 * @return
	 */
	@RequestMapping(value="/lock/{id}")
	public ModelAndView getStockLockRule(@PathVariable("id") Long id,String type){
		ModelAndView andView = new ModelAndView();
		if ("edit".equals(type)) {
			andView.setViewName("/product/lockStock_edit");
		}else{
			andView.setViewName("/product/lockStock_browse");
		}
		StockLockRule stockLockRule = stockLockRuleService.get(id);
		andView.addObject("stockLockRule", stockLockRule);
		return andView;
	}
	
	@RequestMapping(value = "/lock/save", method = RequestMethod.GET)
	public ModelAndView saveStockLockRule() {
		ModelAndView andView = new ModelAndView("/product/lockStock_save");
		return andView;
	}
	
	/**
	 * 增加锁定库存的
	 * @return
	 */
	@RequestMapping(value="/lock/save", method = RequestMethod.POST)
	public ModelAndView saveStockLockRule(@MyInject Employee employee,@Valid StockLockRule lockRule,String productSales){
		int psidnull=0;
		int psidsava=0;
		int psidupdate=0;
		ModelAndView andView = new ModelAndView("/product/lockStock_save");
		String[] arrayProductids = productSales.split(RN);
		List<StockLockRule> lockRules = new ArrayList<StockLockRule>();
		for(String spsid:arrayProductids){
			if(StringUtils.isBlank(spsid)){
				continue;
			}
			StockLockRule stockLockRule = new StockLockRule();
			Long psid = Long.valueOf(spsid);
			stockLockRule.setSales(0);
			stockLockRule.setChannel(lockRule.getChannel());
			stockLockRule.setProductSale(psid);
			stockLockRule.setCustomer(lockRule.getCustomer().getId()==null?null:lockRule.getCustomer());
			stockLockRule.getStockLockRule(lockRule);
			lockRules.add(stockLockRule);
		}
		for(StockLockRule rule:lockRules){
			ProductSale productSale = productService.getProductSale(rule.getProductSale());
			if(productSale==null){
				psidnull++;
				continue;
			}
			StockLockRule stockLockRule = null;
			if(rule.getCustomer()==null){
				stockLockRule = stockLockRuleService.getStockLockBycustomerNull(rule.getChannel().getId(), rule.getProductSale());
			}else{
				Map<String, Object> parame = new HashMap<String, Object>();
				parame.put("productSales", rule.getProductSale());
				parame.put("channels", rule.getChannel().getId());
				parame.put("customerId",rule.getCustomer().getId());
				List<StockLockRule> stockLockRules = stockLockRuleService.find(null, parame, (short)0);
				if(stockLockRules!=null&&!stockLockRules.isEmpty()){
					stockLockRule = stockLockRules.get(0);
				}
			}
			if(stockLockRule!=null){
				psidupdate++;
			}else{
				stockLockRuleSave(rule, employee);
				psidsava++;
			}
		}
		andView.addObject("psidsava", psidsava);
		andView.addObject("psidupdate", psidupdate);
		andView.addObject("psidnull", psidnull);
		andView.addObject("isVerification", true);
		return andView;
	}
	
	/**
	 * 更改锁定库存的
	 * @return
	 */
	@RequestMapping(value="/lock/update")
	public ModelAndView updateStockLockRule(@MyInject Employee employee,@Valid StockLockRule lockRule){
		ModelAndView andView = new ModelAndView("/product/lockStock_edit");
			Integer currentLockstock = lockRule.getLockStock();
			Integer currentRealLock =0;
			if(currentLockstock!=null){
				updateMessageStock(lockRule);
				currentRealLock =currentLockstock<0?currentLockstock:productSaleStockService.computeRealLock(lockRule);
			}
			StockLockRule stockLockRule = stockLockRuleService.get(lockRule.getId());
			if(stockLockRule!=null){
				Integer originalLockstock = stockLockRule.getLockStock();
				Integer originalRealLock = stockLockRule.getRealLock();
				stockLockRule.getStockLockRule(lockRule);
				if(currentLockstock!=null){
					if(originalRealLock!=null){
						Integer finalRealLock = originalRealLock+currentRealLock;
						stockLockRule.setRealLock(finalRealLock>=0?finalRealLock:0);
					}else{
						stockLockRule.setRealLock(currentRealLock);
					}
					if(originalLockstock!=null){
						Integer finalLockstock = originalLockstock+currentLockstock;
						stockLockRule.setLockStock(finalLockstock>=0?finalLockstock:0);
					}else{
						stockLockRule.setLockStock(currentLockstock);
					}
				}
				
				//-----日志在此添加
				stockLockRule.getStockLockRuleLog(employee);
				stockLockRuleService.update(stockLockRule);
				andView.addObject("isVerification", true);
			}else{
				andView.addObject("isVerification", false);
			}
			andView.addObject("stockLockRule", stockLockRule);
		return andView;
	}
	
	/**
	 * 浏览锁定库存的
	 * @return
	 */
	@RequestMapping(value="/lock/list")
	public ModelAndView findStockLockRule(@Valid StockLockRuleForm fLockRuleForm,@MyInject Pagination pagination){
		ModelAndView andView = new ModelAndView("/product/lockStock_list");
		List<StockLockRule> stockLockRules = stockLockRuleService.find(pagination, null, fLockRuleForm.getSort());
		andView.addObject("stockLockRules",stockLockRules);
		andView.addObject("pagination", pagination);
		return andView;
	}
	
	private void stockLockRuleSave(StockLockRule rule,Employee employee){
		rule.setCreateTime(new Date());
		rule.getStockLockRuleLog(employee);
		rule.setStatus(true);
		saveMessageStock(rule);
		stockLockRuleService.save(rule);
	}
	
	/**
	 * 修改时候发消息
	 * @param lockRule
	 */
	private void updateMessageStock(StockLockRule lockRule){
		MessageStock messageStock = new MessageStock(StockOriginEnum.INCREMENT,
				lockRule.getProductSale(), lockRule.getChannel().getId(),
				lockRule.getCustomer()==null?null:lockRule.getCustomer().getId(),lockRule.getLockStock());
		productSaleStockService.sendChannelStock(messageStock);
	}
	
	/**
	 * 新增时候发消息
	 * @param lockRule
	 */
	private void saveMessageStock(StockLockRule lockRule){
		MessageStock messageStock = new MessageStock(StockOriginEnum.STOCK_INIT,
				lockRule.getProductSale(), lockRule.getChannel().getId(),
				lockRule.getCustomer()==null?null:lockRule.getCustomer().getId(),lockRule.getLockStock());
		productSaleStockService.sendChannelStock(messageStock);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
