package com.winxuan.ec.model.product;

import java.util.Comparator;

/**
 * @author yuhu
 * @version 1.0,2012-3-5上午11:40:33
 */
public class ProductMetaComparator implements Comparator<ProductMeta> {

	@Override
	public int compare(ProductMeta o1, ProductMeta o2) {
		Long a = o1.getId()-o2.getId();
		return a.intValue();
	}

}
