package com.winxuan.ec.admin.controller.promotion;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.promotion.Promotion;
import com.winxuan.ec.model.promotion.PromotionOrderRule;
import com.winxuan.ec.model.promotion.PromotionProductRule;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.product.ProductService;

/**
 * 购买指定商品满省活动form
 * @author ztx
 */
public class SpecifyProductPreferentialForm {
	
	public static final String DATAFORMART = "yyyy-MM-dd hh:mm";
	//主商品
	private List<Long> productSaleIds;
	//实洋
	private List<BigDecimal> salePrice;
	//限购数量
	private List<Integer> num;
	//限购总数量
	private List<Integer> nums;
	//促销标题
	private String promotionTitle;
	//促销描述
	private String promotionDescription;
	//促销开始日期
	private String promotionStartDate;
	//促销结束日期
	private String promotionEndDate;
	//促销开始时间
	private String promotionStartTime;
	//促销结束时间
	private String promotionEndTime;
	//促销广告语
	private String advert;
	//促销广告图片
	private String advertImage;
	//促销专题链接
	private String advertUrl;
	//订单支付有效时间
	private Integer effectivetime;
	// 促销方式
	private Boolean orderPricemanner;
	// 普通消费金额
	private BigDecimal generalPriceSpends;
	// 普通节省金额
	private BigDecimal generalPriceSpares;
	// 梯度消费金额
	private BigDecimal[] gradsPriceSpends;
	// 梯度节省金额
	private BigDecimal[] gradsPriceSpares;
	
	//获得promotion
	public Promotion getPromotion(Promotion promotion,ProductService productService,Employee e){
		boolean add = false;
		if(promotion == null){
			promotion = new Promotion();
			promotion.setCreateTime(new Date());
			promotion.setCreateUser(e);
			add = true;
		}
		promotion.setTitle(promotionTitle);	// 促销标题
		promotion.setDescription(promotionDescription);	// 促销描述
		try {// 促销有效起始时间
			promotion.setStartDate(new SimpleDateFormat(DATAFORMART).parse(promotionStartDate+" "+promotionStartTime));
		} catch (ParseException ee) {
			promotion.setStartDate(null);
		}
		try {// 促销有效截止时间 
			promotion.setEndDate(new SimpleDateFormat(DATAFORMART).parse(promotionEndDate+" "+promotionEndTime));
		} catch (ParseException ee) {
			promotion.setEndDate(null);
		}
		promotion.setAdvert(advert);// 促销广告语
		promotion.setAdvertUrl(advertUrl);
		promotion.setEffectiveTime(effectivetime);// 促销订单支付有效时间
		promotion.setType(new Code(Code.PROMOTION_TYPE_PRODUCT_SAVE_AMOUNT));
		Promotion p = null;
		try {
			if(add){
				p = promotion;
			}else{
				p = (Promotion) BeanUtils.cloneBean(promotion);
			}
		} catch (Exception e1) {
			throw new RuntimeException();
		} 
		//参与促销商品列表
		Set<PromotionProductRule> promotionProductRules = new LinkedHashSet<PromotionProductRule>();
		for (int i = 0; productSaleIds!=null && i < productSaleIds.size(); i++) {
			PromotionProductRule promotionProductRule = new PromotionProductRule();
			promotionProductRule.setProductSale(productService.getProductSale(productSaleIds.get(i)));
			promotionProductRule.setProductNum(0);	//设置默认为没有限制
			promotionProductRule.setProductNums(0);	//设置默认为没有限制
			promotionProductRules.add(promotionProductRule);
			promotionProductRule.setPromotion(p);
		}
		p.setProductRules(promotionProductRules);
		
		
		// 促销方式
		promotion.setManner(new Code(
						this.getOrderPricemanner() ? Code.PROMOTION_ORDER_SAVEORSEND_TYPE_GRADIENT
								: Code.PROMOTION_ORDER_SAVEORSEND_TYPE_NORMAL));
		
		// 设置活动规则
		Set<PromotionOrderRule> promotionOrderRules = new LinkedHashSet<PromotionOrderRule>();
		if (this.getOrderPricemanner()) {
			// 梯度满
			BigDecimal[] gradsPriceSpends = this.getGradsPriceSpends();
			// 梯度省
			BigDecimal[] gradsPriceSpares = this.getGradsPriceSpares();
			for (int i = 0; i < gradsPriceSpends.length; i++) {
				if (gradsPriceSpends[i] != null && gradsPriceSpares[i] != null) {
					PromotionOrderRule promotionOrderRule = new PromotionOrderRule();
					promotionOrderRule.setPromotion(p);
					// 满
					promotionOrderRule.setMinAmount(gradsPriceSpends[i]);
					// 省
					promotionOrderRule.setAmount(gradsPriceSpares[i]);
					promotionOrderRules.add(promotionOrderRule);
				}
			}
		} else {
			PromotionOrderRule promotionOrderRule = new PromotionOrderRule();
			promotionOrderRule.setPromotion(p);
			// 满
			promotionOrderRule.setMinAmount(this.getGeneralPriceSpends());
			// 省
			promotionOrderRule.setAmount(this.getGeneralPriceSpares());
			promotionOrderRules.add(promotionOrderRule);
		}
		// 将规则加入到促销活动中
		p.setOrderRules(promotionOrderRules);
		// 返回
		return p;
	}	
	public String getPromotionTitle() {
		return promotionTitle;
	}

	public void setPromotionTitle(String promotionTitle) {
		this.promotionTitle = promotionTitle;
	}

	public String getPromotionDescription() {
		return promotionDescription;
	}

	public void setPromotionDescription(String promotionDescription) {
		this.promotionDescription = promotionDescription;
	}

	public String getPromotionStartDate() {
		return promotionStartDate;
	}

	public void setPromotionStartDate(String promotionStartDate) {
		this.promotionStartDate = promotionStartDate;
	}

	public String getPromotionEndDate() {
		return promotionEndDate;
	}

	public void setPromotionEndDate(String promotiionEndDate) {
		this.promotionEndDate = promotiionEndDate;
	}

	public String getAdvert() {
		return advert;
	}

	public void setAdvert(String advert) {
		this.advert = advert;
	}

	public String getAdvertImage() {
		return advertImage;
	}

	public void setAdvertImage(String advertImage) {
		this.advertImage = advertImage;
	}

	public String getAdvertUrl() {
		return advertUrl;
	}
	public void setAdvertUrl(String advertUrl) {
		this.advertUrl = advertUrl;
	}
	public Integer getEffectivetime() {
		return effectivetime;
	}

	public void setEffectivetime(Integer effectivetime) {
		this.effectivetime = effectivetime;
	}

	public List<Long> getProductSaleIds() {
		return productSaleIds;
	}

	public void setProductSaleIds(List<Long> productSaleIds) {
		this.productSaleIds = productSaleIds;
	}

	public List<BigDecimal> getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(List<BigDecimal> salePrice) {
		this.salePrice = salePrice;
	}
	public String getPromotionStartTime() {
		return promotionStartTime;
	}
	public void setPromotionStartTime(String promotionStartTime) {
		this.promotionStartTime = promotionStartTime;
	}
	public String getPromotionEndTime() {
		return promotionEndTime;
	}
	public void setPromotionEndTime(String promotionEndTime) {
		this.promotionEndTime = promotionEndTime;
	}
	public List<Integer> getNum() {
		return num;
	}
	public void setNum(List<Integer> num) {
		this.num = num;
	}
	public List<Integer> getNums() {
		return nums;
	}
	public void setNums(List<Integer> nums) {
		this.nums = nums;
	}
	public Boolean getOrderPricemanner() {
		return orderPricemanner;
	}
	public void setOrderPricemanner(Boolean orderPricemanner) {
		this.orderPricemanner = orderPricemanner;
	}
	public BigDecimal getGeneralPriceSpends() {
		return generalPriceSpends;
	}
	public void setGeneralPriceSpends(BigDecimal generalPriceSpends) {
		this.generalPriceSpends = generalPriceSpends;
	}
	public BigDecimal getGeneralPriceSpares() {
		return generalPriceSpares;
	}
	public void setGeneralPriceSpares(BigDecimal generalPriceSpares) {
		this.generalPriceSpares = generalPriceSpares;
	}
	public BigDecimal[] getGradsPriceSpends() {
		return gradsPriceSpends;
	}
	public void setGradsPriceSpends(BigDecimal[] gradsPriceSpends) {
		this.gradsPriceSpends = gradsPriceSpends;
	}
	public BigDecimal[] getGradsPriceSpares() {
		return gradsPriceSpares;
	}
	public void setGradsPriceSpares(BigDecimal[] gradsPriceSpares) {
		this.gradsPriceSpares = gradsPriceSpares;
	}
	
}
