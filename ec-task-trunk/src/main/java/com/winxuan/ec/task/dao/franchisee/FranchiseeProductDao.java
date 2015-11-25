package com.winxuan.ec.task.dao.franchisee;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.winxuan.franchisee.model.product.FranchiseeSingleProductPrice;
import com.winxuan.franchisee.model.promotion.PromotionProduct;
import com.winxuan.franchisee.model.user.Franchisee;
/**
 * @author guanxingjiang
 * @version 1.0,2013-12-31
 */
public interface FranchiseeProductDao extends Serializable {

	List<Franchisee> findEffectiveFranchiseeList();

	Long getFranchiseeProductQuantity(Long franchiseeId);

	void updateFranchisee(Franchisee franchisee);
	
	List<PromotionProduct> getPromotionProduct();
	
	void updateEffectiveExpired();
	
	List<FranchiseeSingleProductPrice> getFranchiseeSingleProductPrice(String date);
	
	List<Map<String, Object>> getFranchiseeCategoryProductPrice(String date);
}
