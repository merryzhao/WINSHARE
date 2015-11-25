/*
 * @(#)TestExcelController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.admin.controller.excel;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.presentcard.PresentCardQueryForm;
import com.winxuan.ec.admin.controller.seller.SellerProductSales;
import com.winxuan.ec.exception.PresentCardException;
import com.winxuan.ec.exception.PresentException;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerCashApply;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.present.PresentBatch;
import com.winxuan.ec.model.presentcard.PresentCard;
import com.winxuan.ec.model.product.ProductChannelApply;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.channel.ChannelService;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.employee.AttachService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.present.PresentService;
import com.winxuan.ec.service.presentcard.PresentCardService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.pagination.Pagination;

/**
 * Excel数据导出
 * 
 * @author wumaojie
 * @version 1.0,2011-8-22
 */
@Controller
@RequestMapping(value = "/excel")
public class ExcelController {
	private static final int ZERO=0;
	private static final int SECONDS = 5;
	private static final int NINE = 9;
	private static final int SIZE = 10000;
	private static final int ONE = 1;
	private static final int FIVE_THOUSAND = 5000;
	@Autowired
	ProductService productService;
	
	@Autowired
	PresentService presentService;
	
	@Autowired
	PresentCardService presentCardService;
	
	@Autowired
	AttachService attachService;
	
	@Autowired
	CodeService codeService;
	
	@Autowired
	ChannelService channelService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	private CustomerService customerService;
	
	
	
	/**
	 * 导出商品信息
	 * @param excelProductListForm
	 * @param result
	 * @param pagination
	 * @return
	 */
	@RequestMapping(value = "/productList", method = RequestMethod.POST)
	public ModelAndView productList(
			@Valid ExcelProductListForm excelProductListForm,
			BindingResult result, @MyInject Pagination pagination) {
		// 如果验证通过
		if (!result.hasErrors()) {
			// 如果没有勾选
			if (excelProductListForm.getExport() == null) {
				// 构建map
				Map<String, Object> parameters = new HashMap<String, Object>();
				// 编码部分
				if (excelProductListForm.getCodingContent() == null) {
					excelProductListForm.setCoding("");
				}
				parameters.put("productBarcode", excelProductListForm
						.getCodingContentStyleString("productBarcode"));
				parameters.put("outerId", excelProductListForm
						.getCodingContentStyleString("outerId"));
				parameters.put("productId", excelProductListForm
						.getCodingContentStyleLong("productId"));
				parameters.put("prodcutMerchId", excelProductListForm
						.getCodingContentStyleLong("prodcutMerchId"));
				// 表单其他
				parameters.put("productName",
						excelProductListForm.getProductNameQuery());
				parameters.put("productAuthor",
						excelProductListForm.getProductAuthor());
				parameters.put("productMcCategory",
						excelProductListForm.getProductMcCategory());
				parameters.put("sellerName",
						excelProductListForm.getSellerName());
				parameters.put("status", excelProductListForm.getStatus());
				// 查询数据
				// 确认记录数
				productService.findProductSale(parameters, pagination);
				// 设置查询记录数
				pagination.setPageSize(pagination.getCount());
				// 查询结果
				List<ProductSale> productSales = productService
						.findProductSale(parameters, pagination);
				// 返回
				ModelAndView mav = new ModelAndView("/product/productList");
				mav.addObject("productSales", productSales);
				return mav;
			} else {
				// 构建List
				List<ProductSale> productSales = new ArrayList<ProductSale>();
				// 获取勾选id数组
				Long[] ids = excelProductListForm.getExport();
				// 循环查询并add到List中
				for (Long id : ids) {
					productSales.add(productService.getProductSale(id));
				}
				// 返回
				ModelAndView mav = new ModelAndView("/product/productList");
				mav.addObject("productSales", productSales);
				return mav;
			}
		} else {
			return null;
		}
	}
	@RequestMapping(value = "/presents", method = RequestMethod.POST)
	public ModelAndView presents(@RequestParam("presentBatchId") String presentBatchId,
			@MyInject Employee operator ) throws RowsExceededException, WriteException, IOException, InterruptedException, ExecutionException, PresentException{		
		//获取批次
		Long id = Long.valueOf(presentBatchId);
		PresentBatch presentBatch = presentService.getBatch(id);
		//异步生成EXCEL文件
		Future<String> future = presentService.generatePresent(presentBatch, operator);
		String path = null;
		boolean isdown = true;
		try {
			//如果指定的时间内未返回抛出异常,isdown = false不下载
			path = future.get(SECONDS, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			isdown = false;
		}
		//返回
		ModelAndView mav = new ModelAndView("/presentcard/excel");
		mav.addObject("isDown", isdown);
		mav.addObject("path", path);
		return mav;
	}
	
	@RequestMapping(value = "/printlist", method = RequestMethod.POST)
	public ModelAndView printList(@RequestParam("printString") String printString,
			@MyInject Employee operator) throws PresentCardException
	{
		String [] ids=printString.split(";");
 		List<PrintCardDto> printList=new ArrayList<PrintCardDto>();
		List<PresentCard> presentCards=presentCardService.print(ids, operator);
		for(PresentCard presentCard :presentCards){
			PrintCardDto card=new PrintCardDto();
		    card.setId(presentCard.getId());
		    card.setPassWord(presentCard.getProclaimPassword());
		    printList.add(card);
		}
 		ModelAndView mav = new ModelAndView("/presentcard/printList");
		mav.addObject("printList", printList);
		return mav;
	}

	
	@RequestMapping(value = "/giftcard/list", method = RequestMethod.POST)
	public ModelAndView list(@Valid PresentCardQueryForm cardQueryForm,
			@MyInject Pagination pagination) throws ParseException {
		// 构建map
		Map<String, Object> parameters = cardQueryForm.generateQueryMap();
		ModelAndView mav = null;
		if(1 == cardQueryForm.getStyle()){
			mav = new ModelAndView("/presentcard/giftcardList");
		}else{
			mav = new ModelAndView("/presentcard/giftcardSendList");
			mav.addObject("orderId", cardQueryForm.getOrderId());
		}
		if(parameters.keySet().size()>0){
			// 获取礼品卡列表
			pagination.setPageSize(FIVE_THOUSAND);
			List<PresentCard> presentCards = presentCardService.find(parameters,pagination);
			mav.addObject("presentCards", presentCards);
		}
		return mav;
	}
	
	/**
	 * 卖家新建商品管理导出搜索结果
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/sellerProduactManage", method = RequestMethod.POST)
	public ModelAndView sellerProduactManage(@Valid SellerProductSales pSForm,
			BindingResult result){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(pSForm.getProductCode(), pSForm.getCodes());//商品编码及商品自编码
		parameters.put("shopId", pSForm.getShop());//店铺ID
		parameters.put("businessScope", pSForm.getClassifiedManage());//经营分类String
		parameters.put("auditStatus", pSForm.getAuditStatus());//审核状态
		parameters.put("status", pSForm.getProductUpDown());//上下架
		//parameters.put("productId", "");//是否有图片
		List<ProductSale> productSales =  new ArrayList<ProductSale>();
		List<ProductSale> list = null;
		Pagination pagination = new Pagination();
		pagination.setPageSize(MagicNumber.PAGE_SIZE_200);
		while((list = productService.findProductSale(parameters,pagination)) != null && !list.isEmpty()){
			if(productSales.size()>MagicNumber.MAX_EXPORT_EXCEL_NUM){
				break;
			}
			productSales.addAll(list);
		}
		//返回
		ModelAndView mav = new ModelAndView("/seller/sellerProductManage");
		//返回查询结果
		mav.addObject("productSales", productSales);
		return mav;
	}
	/**
	 * 卖家渠道销售审批导出搜索结果
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/salesAudit", method = RequestMethod.POST)
	public ModelAndView sellerSalesAudit(@Valid SellerProductSales pSForm,
			BindingResult result){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(pSForm.getProductCode(), pSForm.getCodes());//商品编码及商品自编码
		parameters.put("shopId", pSForm.getShop());//店铺ID
		parameters.put("typeId", pSForm.getApplyType());//申请类型
		parameters.put("stateId", pSForm.getStatus());//状态
		parameters.put("createStartDate", pSForm.getStartDate());//申请起始时间
		parameters.put("createEndDate", pSForm.getEndDate());//申请截止时间
		List<ProductChannelApply> productChannelApplies =  
			productService.findProductChannelApply(parameters,null);
		//返回
		ModelAndView mav = new ModelAndView("/seller/channelSalesAudit");
		//返回查询结果
		mav.addObject("pCAs", productChannelApplies);
		return mav;
	}
	
	/**
	 * 导出银行转账信息或支付宝信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/exbankifno", method = RequestMethod.POST)
	public ModelAndView exBankInfo(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		String cids=request.getParameter("exids");
		String flag=request.getParameter("actionType");
 		String[] ids=cids.split(",");
 		List<Long> longIds=new ArrayList<Long>();
		for(int i=ZERO;i<ids.length;i++){
			if(!StringUtils.isBlank(ids[i])){
				longIds.add(Long.valueOf(ids[i]));
 			}
		}	
 		Map<String,Object> parameters = new HashMap<String, Object>();
 		if("apply".equals(flag)){
 			parameters.put("type", Code.CUSTOMER_CASH_TYPE_ALIPAY);
 		}else{
 			parameters.put("type", Code.CUSTOMER_CASH_TYPE_BANK);
 		}
 		parameters.put("ids", longIds);
  		List<CustomerCashApply> customerCashApplies=customerService.findCustomerCashApply(parameters, null);
  		BigDecimal total=BigDecimal.ZERO;
  		for(CustomerCashApply customerCashApply:customerCashApplies){
  			total=total.add(customerCashApply.getMoney());
  		}
  		mav.addObject("total", total);
  		mav.addObject("count", customerCashApplies.size());
  		if("apply".equals(flag)){
  			SimpleDateFormat sFormat1=new SimpleDateFormat("yyyy-MM-dd:hh:mm");
  			String date1=sFormat1.format(new Date());
  			mav.addObject("code", getCode());
  			mav.addObject("date", date1);
  			mav.setViewName("/customer/applyinfo");
  		}else{
  			SimpleDateFormat sFormat=new SimpleDateFormat("yyyyMMdd");
  			String date=sFormat.format(new Date());
  			mav.addObject("date", date);
  			mav.setViewName("/customer/bankinfo");
  		}
  		mav.addObject("customerCashApplies", customerCashApplies);
		return mav;
	}
	
	@RequestMapping(value = "/exrefundifno", method = RequestMethod.POST)
	public ModelAndView exRefundInfo(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/customer/refundinfo");
		String cids=request.getParameter("exids");
		if(StringUtils.isBlank(cids)){
			mav.addObject("customerCashApplies", null);
			return  mav;
		}
 		String[] ids=cids.split(",");
 		List<Long> longIds=new ArrayList<Long>();
		for(int i=ZERO;i<ids.length;i++){
			if(!StringUtils.isBlank(ids[i])){
				longIds.add(Long.valueOf(ids[i]));
 			}
		}	
 		Map<String,Object> parameters = new HashMap<String, Object>();
 		parameters.put("ids", longIds);
  		List<CustomerCashApply> customerCashApplies = customerService.findCustomerCashApply(parameters, null);	
  		mav.addObject("customerCashApplies", customerCashApplies);
		return mav;
	}
	private String getCode(){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
		return simpleDateFormat.format(new Date())+String.valueOf((int)((Math.random()*NINE+ONE)*SIZE));
	}
	/**
	 * 导出查询条件模板
	 */
	@RequestMapping(value = "/exmodel", method = RequestMethod.POST)
	public ModelAndView exportQueryModel(){
 		ModelAndView mav = new ModelAndView("/product/productStopQueryModel");
		//返回查询结果
 		return mav;
	}
	
	/**
	 * 商品批量调价模板
	 * @return
	 */
	@RequestMapping(value="/exportProductPriceUpdateModel")
	public ModelAndView exportProductPriceUpdateModel(){
		return new ModelAndView("/product/productPriceUpdate");
	}
	
	
    /**
     * 代理商交易 查询
     * @param userName
     * @param startDate
     * @param endDate
     * @param pagination
     * @return
     */
	@RequestMapping(value = "/exagentinfo", method = RequestMethod.POST)
	public ModelAndView findAgentOrder(@RequestParam(required=false,value="exids") String userName,
			@RequestParam(required=false,value="exstartDate") String startDate,
			@RequestParam(required=false,value="exendDate") String endDate,
			@RequestParam("actionType") String actionType) {
		ModelAndView mav = new ModelAndView("/agent/agentinfo");
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("startCreateTime", StringUtils.isBlank(startDate)?null:getDateByString(startDate+" 00:00:00"));
		params.put("endCreateTime", StringUtils.isBlank(endDate)?null:getDateByString(endDate+" 23:59:59"));
		params.put("customerChannel",channelService.get(Channel.CHANNEL_AGENT));
		List<Order> orders=null;
		if("all".equals(actionType)){
			if(!StringUtils.isBlank(userName)){
				String[] names=userName.split("\r\n");
				List<String> nameList=new ArrayList<String>();
				for(String name:names){
					if(!StringUtils.isBlank(name)){
						nameList.add(name);
					}
				}
				params.put("name",nameList.isEmpty()?null:nameList);
			}
		}else{
			if(!StringUtils.isBlank(userName)){
				String[] names=userName.split(",");
				List<String> idList=new ArrayList<String>();
				for(String name:names){
					if(!StringUtils.isBlank(name)){
						idList.add(name);
					}
				}
				params.put("orderId",idList.isEmpty()?null:idList);
			}
		}
  		orders=orderService.find(params, (short)0, null);
   		mav.addObject("orders",orders);
		return mav;
	}
	public Date getDateByString(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date timeDate = new Date();
		try {
			if (StringUtils.isBlank(time)) {
				timeDate = null;
			} else {
				timeDate = sdf.parse(time);
			}
		} catch (ParseException e) {
			time = null;
		}
		return timeDate;
	}
	
	@RequestMapping(value="/channelPayDetailTemplate")
	public ModelAndView channelPayDetailTemplate(){
		return new ModelAndView("/channel/channelPayDetailTemplate");
	}
	@RequestMapping(value="/channelPayConfirmOrderTemplate")
	public ModelAndView channelPayConfirmOrderTemplate(){
		return new ModelAndView("/channel/channelPayConfirmOrderTemplate");
	}
	@RequestMapping(value="/channelSalesTempalte")
	public ModelAndView channelSalesTemplate(){
		return new ModelAndView("/channel/channelSalesTempalte");
	}

	@RequestMapping(value="/orderAddProductTemplate")
	public ModelAndView orderAddProductTemplate(){
		return new ModelAndView("/order/orderAddProductTemplate");
	}
	@RequestMapping(value="/exportReturnProductTemplate")
	public ModelAndView exportReturnProductTemplate(){
		return new ModelAndView("/returnorder/exportReturnProductTemplate");
	}
	@RequestMapping(value="/exportReturnOrderPackageTemplate")
	public ModelAndView exportReturnOrderPackageTemplate(){
		return new ModelAndView("/returnorder/exportReturnOrderPackageTemplate");
	}
}
