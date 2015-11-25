/*
 * @(#)MrProductItemService.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.replenishment;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.replenishment.MrProductItem;
import com.winxuan.ec.model.user.User;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author yangxinyi
 * @version 1.0,2013-8-22
 */
public interface MrProductItemService {

	MrProductItem get(Long id);

	void save(MrProductItem mrProductItem);

	List<MrProductItem> findProductItems(Map<String, Object> parameters,
			Short sort, Pagination pagination);

	void update(MrProductItem mrProductItem);

	void updateCheckStatus(Long id, User operator);

	void delete(MrProductItem mrProductItem);

	void saveOrUpdate(MrProductItem mrProductItem);

	void exportProductItem(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> parameters,
			Pagination pagination);

	List<MrProductItem> fetchData(InputStream inputStream) throws IOException;
	
	List<MrProductItem> fetchArtificialApplication(InputStream inputStream) throws IOException;

	void updateAll(List<MrProductItem> items);
	
	void saveAll(List<MrProductItem> datas); 
	
	//人工上传补货申请的覆盖机制：只要是没有审核的，都用后来的记录覆盖前面的记录
	MrProductItem findArtificialProductItem(ProductSale productSale, Code dc, Code type, boolean status);
	
}
