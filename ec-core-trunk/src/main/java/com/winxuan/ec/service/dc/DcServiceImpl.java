package com.winxuan.ec.service.dc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.DcDao;
import com.winxuan.ec.dao.OrderDistributionCenterDao;
import com.winxuan.ec.dao.OrderUpdateLogDao;
import com.winxuan.ec.exception.BusinessException;
import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.dc.DcPriority;
import com.winxuan.ec.model.dc.DcRule;
import com.winxuan.ec.model.dc.DcRuleArea;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderDistributionCenter;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.OrderUpdateLog;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleStock;
import com.winxuan.ec.model.product.StockSales;
import com.winxuan.ec.model.user.User;
import com.winxuan.ec.service.product.ProductSaleStockService;
import com.winxuan.ec.service.product.StockSalesService;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 ******************************* 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-7-30 上午11:30:12 --!
 * @description:
 ******************************** 
 */
@Service("dcService")
@Transactional(rollbackFor = Exception.class)
public class DcServiceImpl implements DcService {
	
	
	@InjectDao
	private DcDao dcDao;

	@InjectDao
	private OrderDistributionCenterDao orderDistributionCenterDao;
	
	@Autowired
	private ProductSaleStockService productSaleStockService;
	
	@Autowired
	private StockSalesService stockSalesService;
	
	@InjectDao
	private OrderUpdateLogDao orderUpdateLogDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public DcRule getDc(Long id) {
		return dcDao.getDc(id);
	}

	@Override
	public void saveDcArea(DcRuleArea area) {
		dcDao.saveDcArea(area);
	}

	@Override
	public DcRuleArea getDcArea(Long id) {
		return dcDao.getDcArea(id);
	}

	@Override
	public List<DcRule> find(Map<String, Object> parameters,
			Pagination pagination, Short sort) {
		return dcDao.find(parameters, pagination, sort);
	}

	@Override
	public List<DcRule> findByAvailableDc() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("available", Boolean.TRUE);
		return this.find(parameters, new Pagination(), (short) 1);
	}

	@Override
	public List<Long> findAvailableDc() {
		return jdbcTemplate.queryForList("select location from dcrule where available=1",Long.class);
	}
	
	@Override
	public List<DcPriority> findDcPriorityByArea(Area area) {
		return jdbcTemplate
				.query(
						"select d.location,da.priority ,d.available from dcrule d ,dcrule_area da " +
						          "where d.id = da.dc and (d.available=1 or (d.available=0 and da.priority =1)) and da.area =? order by da.priority ",
						new RowMapper<DcPriority>(){
							@Override
							public DcPriority mapRow(ResultSet rs,
									int rowNum) throws SQLException {
								DcPriority dp = new DcPriority();
								dp.setLocation(rs.getLong("location"));
								dp.setPriority(rs.getInt("priority"));
								dp.setAvailable(rs.getBoolean("available"));
								return dp;
							}
						}, area.getId());
	}

	@Override
	public List<DcRule> findDcArea(Area area,List<Long> dcs) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("area", area);
		parameters.put("available", Boolean.TRUE);
		if(dcs!=null&&dcs.size()>0){
			 List<Code> dcStr = new ArrayList<Code>();
			 for(Long dc:dcs){
				 dcStr.add(new Code(dc));
			 }
			 parameters.put("dc", dcStr);
		}
		return this.dcDao.find(parameters, new Pagination(), (short) 1);
	}

	@Override
	public List<DcRule> findDcAreaOrderByPriority(Area area,List<Long> dcs) {
		return this.findDcArea(area,dcs);
	}

	@Override
	public DcRule findByAvailableDc(String dc) {
		List<DcRule> dcRuleList = this.findByAvailableDc();
		for (DcRule dcRule : dcRuleList) {
			if (dcRule.getLocation().getName().equals(dc)) {
				return dcRule;
			}
		}
		return null;
	}

	@Override
	public void saveDcRule(DcRule dcRule) {
		this.dcDao.saveDcRule(dcRule);

	}

	@Override
	public Boolean updateOrderDcOriginal(Order order, Code dc, User user)
			throws BusinessException {

		OrderDistributionCenter od = orderDistributionCenterDao.get(order
				.getId());
		//移动之前的库位信息
		Code oldDc = od.getDcDest();
		Set<OrderItem> orderItemList = order.getItemList();
		for (OrderItem orderItem : orderItemList) {
			ProductSale ps = orderItem.getProductSale();
			productSaleStockService.updateQuantity(dc, ps, 0, orderItem.getPurchaseQuantity());
			productSaleStockService.updateQuantity(oldDc, ps, 0, -orderItem.getPurchaseQuantity());
			StockSales stockSales = stockSalesService.get(order.getId(), ps.getId(), od.getDcDest());
			if(stockSales != null){
				stockSales.setDc(dc);
				for (ProductSaleStock productSaleStock : ps.getProductSaleStocks()) {
					if(productSaleStock.getDc().getId() == dc.getId()){
						stockSales.setProductSaleStock(productSaleStock);
					}
				}
				stockSalesService.update(stockSales);
			}
		}
	    this.updateDc(od, dc);
	    OrderUpdateLog log = new OrderUpdateLog(user, order, OrderUpdateLog.ORIGINAL_DC, oldDc.getName(), dc.getName());
        orderUpdateLogDao.save(log);
		return true;
	}

	
	private void updateDc(OrderDistributionCenter od,Code dc){
		od.setDcOriginal(dc);
		od.setDcDest(dc);
		orderDistributionCenterDao.update(od);
	}

	@Override
	public void updateDcRule(DcRule dcRule) {
		dcDao.updateDcRule(dcRule);
	}

	@Override
	public void removeDcRuleArea(DcRuleArea dcRuleArea) {
		dcDao.removeDcRuleArea(dcRuleArea);
	}

	@Override
	public DcRule findByAvailableDc(Code dc) {
		List<DcRule> dcRuleList = this.findByAvailableDc();
		for (DcRule dcRule : dcRuleList) {
			if (dcRule.getLocation().getId().equals(dc.getId())) {
				return dcRule;
			}
		}
		return null;
	}
}
