package com.winxuan.ec.service.product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ProductSaleRankDao;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleRank;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-10-17
 */
@Service("productSaleRankService")
@Transactional(rollbackFor = Exception.class)
public class ProductSaleRankServiceImpl implements ProductSaleRankService {

	@InjectDao
	ProductSaleRankDao productSaleRankDao;
	
	@Override
	public void save(ProductSaleRank productSaleRank) {	
		
		productSaleRankDao.save(productSaleRank);
	}
	public long findRankCount(ProductSale productSale){
		return productSaleRankDao.findRankCount(productSale);
	}
	@Override
	public BigDecimal getProductRankAverage(ProductSale productSale) {
		BigDecimal rankAvg =  this.productSaleRankDao.getProductRankAverage(productSale.getId());
		if(rankAvg == null){
			rankAvg = BigDecimal.ZERO;
		}
		rankAvg = rankAvg.setScale(1, RoundingMode.HALF_UP);
		return rankAvg;
	}

	@Override
	public ProductSaleRankRateBean getProductRankInfos(ProductSale productSale) {
		List<ProductSaleRankInfo> rankInfos = this.productSaleRankDao.getProductRankInfos(productSale.getId());
		ProductSaleRankRateBean rankRateBean = new ProductSaleRankRateBean(rankInfos);
		return rankRateBean;
	}

}
