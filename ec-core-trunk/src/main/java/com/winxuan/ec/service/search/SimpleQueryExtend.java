package com.winxuan.ec.service.search;

import org.hibernate.proxy.HibernateProxy;

import com.winxuan.ec.dao.SearchOpFreqStatisticsDao;
import com.winxuan.ec.dao.SearchQueryExtendDao;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.search.SearchOpFreqStatistics;

/**
 * 簡單產品類
 * 
 * @author sunflower
 * 
 */
public class SimpleQueryExtend extends QueryExtend {

	private static final String FIELD = "GOODS_ID";
	private static final String SPACE = " ";
	private static final long FREQ_MIN = 5;
	private static final long FREQ_SUM_MIN = 25;
	private static final double EXTEND_BOOST = 10;

	public SimpleQueryExtend(
			SearchOpFreqStatisticsDao searchOpFreqStatisticsDao,
			SearchQueryExtendDao searchQueryExtendDao) {
		super(searchOpFreqStatisticsDao, searchQueryExtendDao);
	}

	@Override
	protected String getQueryExtend(
			SearchOpFreqStatistics searchOpFreqStatistics, String query) {

		Long sum = searchOpFreqStatistics.getFreq1()
				+ searchOpFreqStatistics.getFreq2()
				+ searchOpFreqStatistics.getFreq3()
				+ searchOpFreqStatistics.getFreq4()
				+ searchOpFreqStatistics.getFreq5();
		if (sum < FREQ_SUM_MIN) {
			return query;
		}
        
		StringBuffer sb = new StringBuffer(query);
		if (searchOpFreqStatistics.getFreq1() >= FREQ_MIN) {
			add(getIdValue(searchOpFreqStatistics.getProductsale1()), sb, EXTEND_BOOST
					* searchOpFreqStatistics.getFreq1() / sum);
		}
		if (searchOpFreqStatistics.getFreq2() >= FREQ_MIN) {
			add(getIdValue(searchOpFreqStatistics.getProductsale2()), sb, EXTEND_BOOST
					* searchOpFreqStatistics.getFreq2() / sum);
		}
		if (searchOpFreqStatistics.getFreq3() >= FREQ_MIN) {
			add(getIdValue(searchOpFreqStatistics.getProductsale3()), sb, EXTEND_BOOST
					* searchOpFreqStatistics.getFreq3() / sum);
		}
		if (searchOpFreqStatistics.getFreq4() >= FREQ_MIN) {
			add(getIdValue(searchOpFreqStatistics.getProductsale4()), sb, EXTEND_BOOST
					* searchOpFreqStatistics.getFreq4() / sum);
		}
		if (searchOpFreqStatistics.getFreq5() >= FREQ_MIN) {
			add(getIdValue(searchOpFreqStatistics.getProductsale5()), sb, EXTEND_BOOST
					* searchOpFreqStatistics.getFreq5() / sum);
		}
        
		return sb.toString();
	}

	private void add(Long parameter, StringBuffer query, double boost) {
		if (query.length() != 0) {
			query.append(SPACE).append("OR").append(SPACE).append(FIELD)
					.append(":").append(parameter).append("^").append(boost)
					.append(SPACE);
		}
	}

	private Long getIdValue(ProductSale model) {
		if (model instanceof HibernateProxy) {
			return (Long) ((HibernateProxy) model)
					.getHibernateLazyInitializer().getIdentifier();
		}
		return model.getId();
	}

}
