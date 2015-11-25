/**
 * 
 */
package com.winxuan.ec.service.replenishment;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.CodeDao;
import com.winxuan.ec.dao.MrCumulativeReceiveDao;
import com.winxuan.ec.dao.MrProductFreezeDao;
import com.winxuan.ec.dao.ProductDao;
import com.winxuan.ec.dao.ProductSaleDao;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.replenishment.MrCumulativeReceive;
import com.winxuan.ec.model.replenishment.MrCumulativeReceiveTemp;
import com.winxuan.ec.model.replenishment.MrProductFreeze;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * @author monica
 * 获取处于冻结期间的商品的累积收获量
 */
@Service("mrCumulativeReceiveService")
@Transactional(rollbackFor = Exception.class)
public class MrCumulativeReceiveServiceImpl implements MrCumulativeReceiveService, Serializable{

	private static final long serialVersionUID = 6023825632553518491L;
	
	private static final Log LOG = LogFactory.getLog(MrCumulativeReceiveServiceImpl.class);
	
	@InjectDao
	private MrProductFreezeDao mrProductFreezeDao;
	
	@InjectDao
	private ProductSaleDao productSaleDao;
	
	@InjectDao
	private ProductDao productDao;
	
	@InjectDao
	private MrCumulativeReceiveDao mrCumulativeReceiveDao;
	
	@InjectDao
	private CodeDao codeDao;
	
	@Autowired
	private CodeService codeService;
	
	private JdbcTemplate jdbcTemplate;

	/**
	 * 如果某个待解冻的商品没有在mr_product_cumulative_receive表中，则添加新纪录
	 */
	
	private void addCumulativeProduct(MrCumulativeReceiveTemp mrCumulativeReceiveTemp){
		String insertSql = "insert into mr_product_cumulative_receive(productsale,dc,receive,updatetime) values(?,?,?,?)";
		Long dc = codeDao.findCodeByName(mrCumulativeReceiveTemp.getPlace()).getId();
		jdbcTemplate.update(insertSql, new Object[]{mrCumulativeReceiveTemp.getProductSale(),
				dc,mrCumulativeReceiveTemp.getChangeqty(),mrCumulativeReceiveTemp.getUpdateTime()});
		LOG.info("productsale为" + mrCumulativeReceiveTemp.getProductSale() + "通过添加收货记录测试");
	}
	/**
	 * 如果某个待解冻的商品在mr_product_cumulative_receive表中，则更新记录
	 */
	private void updateCumulativeProduct(MrCumulativeReceiveTemp mrCumulativeReceiveTemp){
		ProductSale productSale = productSaleDao.findProductSale(mrCumulativeReceiveTemp.getProductSale());
		Code dc = codeDao.findCodeByName(mrCumulativeReceiveTemp.getPlace());
		MrCumulativeReceive mrCumulativeReceive = mrCumulativeReceiveDao.findByProductSaleAndDc(productSale, dc);
		if(!mrCumulativeReceiveTemp.getUpdateTime().equals(mrCumulativeReceive.getUpdateTime())){
			int initialReceive = mrCumulativeReceive.getReceive();
			int newReceive = initialReceive + mrCumulativeReceiveTemp.getChangeqty();
			mrCumulativeReceive.setReceive(newReceive);
			mrCumulativeReceive.setUpdateTime(mrCumulativeReceiveTemp.getUpdateTime());
			mrCumulativeReceiveDao.update(mrCumulativeReceive);
			LOG.info("productsale为" + mrCumulativeReceiveTemp.getProductSale() + "通过更新收货记录测试");
		}
	}
	
	/**
	 * 查看某个商品是否在mr_product_cumulative_receive表中
	 */
	@Override
	public boolean isExistInReceive(ProductSale productSale, Code dc){
		return mrCumulativeReceiveDao.isExistByProductSaleAndDc(productSale,dc);
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 利用mr_product_freeze和product_warehouse来更新mr_product_cumulative_receive
	 * 获取处于冻结期间的商品在冻结期间的累积收获量
	 */
	@Override
	public void updateCumulativeReceive(MrCumulativeReceiveTemp mrCumulativeReceiveTemp){
		ProductSale productSale = productDao.getProductSale(mrCumulativeReceiveTemp.getProductSale());
		Code dc = codeService.getDcCodeByDcName(mrCumulativeReceiveTemp.getPlace());
		Code type = codeDao.get(mrCumulativeReceiveTemp.getType());
		boolean isFreezed = mrProductFreezeDao.isExistByProductSaleAndTypeNew(productSale, type, dc);
		try{
			if (isFreezed){
				MrProductFreeze mrProductFreeze = mrProductFreezeDao.findFreezeByProductSaleAndTypeNew(productSale, type, dc);
				LOG.info("productsale为" + productSale.getId() + "的商品在冻结表对应的记录id为" + mrProductFreeze.getId());
				if (mrCumulativeReceiveTemp.getUpdateTime().after(mrProductFreeze.getStartTime()) 
						&& mrCumulativeReceiveTemp.getUpdateTime().before(mrProductFreeze.getEndTime())){
					if(isExistInReceive(productSale, dc)){
						MrCumulativeReceive mrCumulativeReceive = mrCumulativeReceiveDao.findByProductSaleAndDc(productSale, dc);
						if(!mrCumulativeReceiveTemp.getUpdateTime().equals(mrCumulativeReceive.getUpdateTime())){
							int initialReceive = mrCumulativeReceive.getReceive();
							int newReceive = initialReceive + mrCumulativeReceiveTemp.getChangeqty();
							mrCumulativeReceive.setReceive(newReceive);
							mrCumulativeReceive.setUpdateTime(mrCumulativeReceiveTemp.getUpdateTime());
							mrCumulativeReceiveDao.update(mrCumulativeReceive);
						}
						LOG.info("productsale为" + productSale.getId() + "变更累积收货表中的收货数量！");
					}
					else{
						String insertSql = "insert into mr_product_cumulative_receive(productsale,dc,receive,updatetime) values(?,?,?,?)";
						Long dcId = dc.getId(); 
						jdbcTemplate.update(insertSql, new Object[]{mrCumulativeReceiveTemp.getProductSale(),
						dcId,mrCumulativeReceiveTemp.getChangeqty(),mrCumulativeReceiveTemp.getUpdateTime()});
						LOG.info("productsale为" + productSale.getId() + "添加到累积收货表中！");
					}
				}
			}
		}catch(Exception e){
			LOG.info("productsale为" + productSale.getId() + "计算累积收获量出错！");
		}
		
	}
	
	@Override
	public List<MrCumulativeReceiveTemp> getCumulativeReceiveTempList(){
		String queryStr = "select pw.productsale, pw.place, pw.changeqty, pw.updatetime, mpf.type from product_warehouse pw" +
		" inner join mr_product_freeze mpf on mpf.productsale = pw.productsale" +
		" where mpf.type in (510001,510004) and mpf.flag in (0,2)" +
		" and pw.changeqty > 0";
		List<MrCumulativeReceiveTemp> cumulativeReceiveTempList = jdbcTemplate.query(queryStr, 
		new BeanPropertyRowMapper<MrCumulativeReceiveTemp>(MrCumulativeReceiveTemp.class));
		LOG.info("处于冻结期间的收货数量大于0的记录总数为：" + cumulativeReceiveTempList.size());
		return cumulativeReceiveTempList;
	}
}
