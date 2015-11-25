package com.winxuan.ec.service.productMerge;

import com.winxuan.ec.dao.ProductJiuyueDao;
import com.winxuan.ec.model.product.ProductJiuyue;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.framework.dynamicdao.InjectDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: Administrator Date: 13-4-28 Time: 下午3:39 To
 * change this template use File | Settings | File Templates.
 */
@Service
public class ProductJiuyueServiceImpl implements ProductJiuyueService {
	@InjectDao
	ProductJiuyueDao productJiuyueDao;

	public ProductJiuyue fetchProductJiuyue(ProductSale jiu) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("productsaleid", jiu.getId());
		ProductJiuyue jiuyue = productJiuyueDao.fetctProductJiuyue(param);
		return jiuyue;
	}

	@Override
	public ProductJiuyue getProductSaleBySeptember(Long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("jiuyueProductid", id);
		ProductJiuyue winxuanP = productJiuyueDao.fetctProductJiuyue(param);
		return winxuanP;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public void update(ProductJiuyue jiuyue) {
		productJiuyueDao.update(jiuyue);
	}

	/**
	 * 得到中图
	 * 
	 * @param jiu
	 * @return
	 */
	public String fetchJiuyueMiddleImg(ProductSale jiu) {
		return fetchJiuyueImg(jiu, "middle");
	}

	/**
	 * 得到小图
	 * 
	 * @param jiu
	 * @return
	 */
	public String fetchJiuyueSmallImg(ProductSale jiu) {
		return fetchJiuyueImg(jiu, "middle");
	}

	/**
	 * 得到大图
	 * 
	 * @param jiu
	 * @return
	 */
	public String fetchJiuyueBigImg(ProductSale jiu) {
		return fetchJiuyueImg(jiu, "middle");
	}

	/**
	 * 得到9月图片
	 * 
	 * @param jiu
	 * @return
	 */
	private String fetchJiuyueImg(ProductSale jiu, String type) {
		ProductJiuyue jiuyue = fetchProductJiuyue(jiu);
		Long id = jiuyue.getJiuyueBookid();
		Long min = (id / 10000) * 10000 + 1;
		Long max = (id / 10000 + 1) * 10000;
		String url = "http://www.9yue.com/book/%d-%d/%d/cover/%s.jpg";
		jiu.setRemark(jiuyue.getJiuyueProductid().toString());
		return String.format(url, min, max, id, type);
	}

}
