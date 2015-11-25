package com.winxuan.ec.task.service.image;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductImage;
import com.winxuan.ec.task.model.mdm.MDMImage;
import com.winxuan.framework.util.image.ImageCompress;

/**
 * description
 * 
 * @author YangJun
 * @version 1.0,2011-11-14
 */
public interface CompressImageService {	
	Product getProduct(Long merchId);

	ProductImage getProductImage(Map<String, Object> parameters);

	void compress();
	
	List<MDMImage> queryListData(String sql, int size);

	List<MDMImage> queryListData(String sql, String imageType, int size);

	long getProductId(long merchid);

	boolean save(ImageCompress ic, Product product, byte[] blob, short type, short index);

	boolean save(ImageCompress ic, Product product, byte[] blob);

	void deleteProductImage(long productId, short type);
	
	void updateMDMImage(String flag1, String flag2, long id);
	
	void compressOther();
}
