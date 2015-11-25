package com.winxuan.ec.task.dao.franchisee;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.winxuan.ec.task.service.franchisee.product.FranchiseeSingleProductPriceRowMapper;
import com.winxuan.ec.task.service.franchisee.product.impl.FranchiseeRowMapper;
import com.winxuan.ec.task.service.franchisee.product.impl.PromotionProductRowMapper;
import com.winxuan.franchisee.model.product.FranchiseeSingleProductPrice;
import com.winxuan.franchisee.model.promotion.PromotionProduct;
import com.winxuan.franchisee.model.user.Franchisee;
/**
 * 
 * @author guanxingjiang
 * @version 1.0,2013-12-31
 */
@Repository("franchiseeProductDao")
public class FranchiseeProductDaoImpl implements FranchiseeProductDao {

	private static final long serialVersionUID = 8788429145536455688L;
	
	private static final String SELECT_FRANCHISEE_SQL = "SELECT f.user,f.quantity FROM franchisee f WHERE f.status=14002";

	private static final String SELECT_ONSHELF_QUANTITY_SQL = "SELECT COUNT(ps.productsaleid) FROM product_snapshot ps WHERE ps.outerstatus=1201 AND ps.onshelf=1 AND ps.franchisee=?";

	private static final String UPDATE_FRANCHISEE_SQL = "UPDATE franchisee f SET quantity=? WHERE f.user=?";

	private static final String SELECT_PROMOTIONPRODUCT_SQL = "SELECT ps.product product,pf.franchisee franchisee from promotion p,product_sort ps ,promotion_franchisee pf" +
			                    " where p.id=pf.promotion  and  ps.promotion = p.id and p.auditstatus=1502 and p.effectivestatus=2302 and ps.`status`=1 and pf.`status`=1 " +
			                    "and (p.starttime>=DATE_FORMAT(DATE_sub(NOW(),INTERVAL 1 day),'%Y-%m-%d') or p.endtime<=DATE_FORMAT(DATE_sub(NOW(),INTERVAL 1 day),'%Y-%m-%d'))";
	
	private static final String UPDATEEFFECTIVEEXPIRED = "update promotion p set p.effectivestatus='2303' where p.endtime <= now()";
	
	private static final String SELECT_SINGLEPRICE_SQL = "SELECT fp.franchisee franchisee,fp.product product " +
			"                                            from franchisee_single_product_price fp where fp.`status`=1 and (fp.begintime =? or fp.endtime =?)";
	
	private static final String SELECT_CATEGORYPRICE_SQL = "SELECT fp.franchisee franchisee,fp.mccode mccode " +
			"                                              from franchisee_category_product_price fp where fp.`status`=1 and (fp.begintime =? or fp.endtime =?)";
	@Autowired
	private JdbcTemplate jdbcTemplateFranchisee;

	@Override
	public List<Franchisee> findEffectiveFranchiseeList() {
		return jdbcTemplateFranchisee.query(SELECT_FRANCHISEE_SQL,
				new FranchiseeRowMapper());
	}

	@Override
	public Long getFranchiseeProductQuantity(Long franchiseeId) {
		return jdbcTemplateFranchisee.queryForLong(SELECT_ONSHELF_QUANTITY_SQL,
				new Object[] { franchiseeId });
	}

	@Override
	public void updateFranchisee(Franchisee franchisee) {
		jdbcTemplateFranchisee.update(UPDATE_FRANCHISEE_SQL,
				new Object[]{franchisee.getQuantity(),franchisee.getId()});
	}

	
	@Override
	public List<PromotionProduct> getPromotionProduct() {
		return jdbcTemplateFranchisee.query(SELECT_PROMOTIONPRODUCT_SQL,
				 new PromotionProductRowMapper());
	}

	
	@Override
	public void updateEffectiveExpired() {
		jdbcTemplateFranchisee.update(UPDATEEFFECTIVEEXPIRED);
	}

	
	@Override
	public List<FranchiseeSingleProductPrice> getFranchiseeSingleProductPrice(String date) {
		return jdbcTemplateFranchisee.query(SELECT_SINGLEPRICE_SQL, 
				new Object[]{date,date}, new FranchiseeSingleProductPriceRowMapper());
	}

	
	@Override
	public List<Map<String, Object>> getFranchiseeCategoryProductPrice(String date) {
		return jdbcTemplateFranchisee.queryForList(SELECT_CATEGORYPRICE_SQL, new Object[]{date,date});
	}

}
