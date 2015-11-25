package com.winxuan.ec.service.shop;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ShopDao;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerComment;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.shop.ShopColumn;
import com.winxuan.ec.model.shop.ShopColumnItem;
import com.winxuan.ec.model.shop.ShopLog;
import com.winxuan.ec.model.shop.ShopRank;
import com.winxuan.ec.model.shop.ShopServiceNo;
import com.winxuan.ec.model.shop.ShopServiceTime;
import com.winxuan.ec.model.shop.ShopUsualCategory;
import com.winxuan.ec.model.user.User;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.StringUtils;

/**
 * 
 * @author xuan jiu dong
 * 
 */
@Service("shopService")
@Transactional(rollbackFor = Exception.class)
public class ShopServiceImpl implements ShopService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5367694038863027770L;
	
	private static final String TIME_MIAO = ":00";
	private static final String SUMRANK = "sumrank";
	private static final String COUNTRANK = "countrank";
	private static final String AVGRANK = "avgrank";

	@InjectDao
	private ShopDao shopDao;

	@Override
	public Shop get(Long id) {
		return shopDao.getById(id);
	}

	@Override
	public void save(Shop shop, User operator) {
		shop.setGrade(new Code(Code.SHOP_GRADE_USUAL));
		shopDao.save(shop);
		ShopLog log = new ShopLog(shop,operator);
		shop.addLog(log);
		shopDao.save(log);
	}

	@Override
	public void update(Shop shop, User operator) {
		shopDao.update(shop);
		ShopLog log = new ShopLog(shop,operator);
		shop.addLog(log);
		shopDao.save(log);
	}

	@Override
	public List<Shop> find(Map<String, Object> params, Pagination pagination) {
		if (pagination == null) {
			return shopDao.find(params);
		} else {
			return shopDao.find(params, pagination);
		}
	}

	@Override
	public void saveUsualCagtegory(ShopUsualCategory uc) {
		shopDao.save(uc);
	}

	@Override
	public void save(ShopColumn shopColumn) {
		shopDao.save(shopColumn);

	}

	@Override
	public List<ShopColumn> find(Map<String, Object> params) {
		Short sort=0;
		return shopDao.findShopColumnList(params,sort);
	}

	@Override
	public void save(ShopColumnItem shopColumnItem) {
		this.shopDao.save(shopColumnItem);

	}

	@Override
	public void update(ShopColumnItem shopColumnItem) {
		this.shopDao.update(shopColumnItem);

	}

	@Override
	public void delete(ShopColumnItem shopColumnItem) {
		this.shopDao.delete(shopColumnItem);

	}

	@Override
	public ShopColumnItem getShopColumnItem(long id) {
		return this.shopDao.getShopColumnItem(id);
	}

	public List<Shop> findAll() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("limitedEndDate", new Date());
		params.put("noStates", new Code[]{new Code(Code.SHOP_STATE_CANCEL)});
		return shopDao.find(params);
	}

	@Override
	public void saveOrUpdate(ShopColumn shopColumn) {
		
		if(shopColumn.getId() == 0){//新增
			ShopColumn sc = shopDao.findMaxIndexShopColumnByShopId(shopColumn.getShop().getId());
			if(sc == null){
				shopColumn.setIndex(1);
			}else{
				shopColumn.setIndex(sc.getIndex()+1);
			}
		}
		shopDao.saveOrUpdate(shopColumn);
		
	}
	
	@Override
	public void deleteShopColumn(Long id){
		ShopColumn shopColumn = shopDao.getShopColumn(id);
		if(shopColumn!=null){
			shopDao.delete(shopColumn);
		}
	}
	
	@Override
	public void moveUp(ShopColumn shopColumn) {
		
		ShopColumn sc = shopDao.findUpShopColumn(shopColumn.getShop().getId(),shopColumn.getIndex());
		if(sc != null){
			int tmp = sc.getIndex();
			sc.setIndex(shopColumn.getIndex());
			shopColumn.setIndex(tmp);
			shopDao.saveOrUpdate(shopColumn);
			shopDao.saveOrUpdate(sc);
		}
	}
	
	@Override
	public void moveDown(ShopColumn shopColumn) {
		
		ShopColumn sc = shopDao.findDownShopColumn(shopColumn.getShop().getId(),shopColumn.getIndex());
		if(sc != null){
			int tmp = sc.getIndex();
			sc.setIndex(shopColumn.getIndex());
			shopColumn.setIndex(tmp);
			shopDao.saveOrUpdate(shopColumn);
			shopDao.saveOrUpdate(sc);
		}
	}

	@Override
	public ShopColumn getShopColumn(Long id) {
		return shopDao.getShopColumn(id);
	}

	@Override
	public long shopProductCount(Map<String, Object> parameters) {
		return shopDao.shopProductCount(parameters);
	}

	@Override
	public List<CustomerComment> findAllShopComment(Map<String, Object> parameters,Pagination pagination) {
		return shopDao.findShopComment(parameters,pagination);
	}

	@Override
	public List<Integer> getShopRank(Shop shop) {
		List<Integer> result =shopDao.getShopAllRank(shop.getId());
		return result;
	}

	@Override
	public long getCommentCount(Shop shop) {
		return shopDao.getCommentCount(shop.getId());
	}

	@Override
	public List<ProductSale> findProductSaleByCategory(
			Map<String, Object> parameters,Pagination pagination) {
		return shopDao.findProductSaleByCategory(parameters,pagination);
	}

	@Override
	@Deprecated
	public List<HashMap<String, Object>> getRank(Shop shop) {
		return this.shopDao.getRank(shop.getId());
	}

	@Override
	@Deprecated
	public List<HashMap<String, Object>> getRank(Shop shop, int rank) {
		return this.shopDao.getRank(shop.getId(), rank);
	}

	@Override
	public ShopRank convertToShopRank(Shop shop){
		List<HashMap<String, Object>> results = this.shopDao.getRank(shop.getId());
		return convertToShopRank(results, shop);
	}
	@Override
	public ShopRank convertToShopRank(Shop shop, int rank) {
		List<HashMap<String, Object>> results = this.shopDao.getRank(shop.getId(), rank);
		return convertToShopRank(results, shop);
	}
	private ShopRank convertToShopRank(List<HashMap<String, Object>> results, Shop shop){
		ShopRank shopRank = null;
		if(CollectionUtils.isNotEmpty(results)){
			shopRank = new ShopRank();
			HashMap<String, Object> map = results.get(0);
			if(!"0".equals(map.get(COUNTRANK).toString())){
				shopRank.setShopId(shop.getId());
				shopRank.setSumRank(Double.parseDouble(map.get(SUMRANK).toString()));
				shopRank.setRankCount(new BigInteger(map.get(COUNTRANK).toString()));
				shopRank.setAverageRank(Double.parseDouble(map.get(AVGRANK).toString()));
			}
		}
		return shopRank;
	}

	@Override
	public List<ProductSale> findProductSaleByCategory(
			Map<String, Object> parameters, int size) {
		return this.shopDao.findProductSaleByCategory(parameters, size);
	}

	@Override
	public ShopColumn findSubject(Long shopId) {
		return shopDao.findSubject(shopId,Code.SHOP_COLUMN_TYPE_IMG);
	}

	@Override
	public Map<String, Object> serviceInfo(Shop shop) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<String> serviceQqNos = new ArrayList<String>();
		List<String> phones = new ArrayList<String>();
		Set<ShopServiceNo> set = shop.getShopServices();
		if (set != null && !set.isEmpty()) {
			Iterator<ShopServiceNo> it = set.iterator();
			while (it.hasNext()) {
				ShopServiceNo shopServiceNo = it.next();
				if (Code.SHOP_SERVICE_TYPE_QQ.equals(shopServiceNo.getType()
						.getId())
						&& !StringUtils.isNullOrEmpty(shopServiceNo
								.getServiceNo())) {// qq客服
					serviceQqNos.add(shopServiceNo.getServiceNo());
				}
				if ((Code.SHOP_SERVICE_TYPE_MOBILE_PHONE.equals(shopServiceNo
						.getType().getId()) || Code.SHOP_SERVICE_TYPE_PHONE
						.equals(shopServiceNo.getType().getId()))
						&& !StringUtils.isNullOrEmpty(shopServiceNo
								.getServiceNo())) {// 电话
					phones.add(shopServiceNo.getServiceNo());
				}
			}
		}

		Set<ShopServiceTime> shopServiceTimes = shop.getShopServiceTimes();
		List<ShopServiceTime> list = new ArrayList<ShopServiceTime>(
				shopServiceTimes);
		/*
		 * ShopServiceTime[] shopServiceTimeArray = shopServiceTimes
		 * .toArray(new ShopServiceTime[shopServiceTimes.size()]);
		 * Arrays.sort(shopServiceTimeArray); list = new
		 * ArrayList<ShopServiceTime>( Arrays.asList(shopServiceTimeArray));
		 */
		map.put("serviceTimes", getServiceTime(list));
		map.put("serviceQqNos", serviceQqNos);
		map.put("phones", phones);
		return map;
	}
	

	private List<String> getServiceTime(List<ShopServiceTime> list) {
		List<String> serviceTimes = new ArrayList<String>();
		getServiceTime(list, 0, serviceTimes);
		return serviceTimes;
	}
	
	private void getServiceTime(List<ShopServiceTime> list, int start,
			List<String> serviceTimes) {
		if(list == null || list.size()==0){
			return;
		}
		if (start == list.size() - 1) {
			ShopServiceTime shopServiceTime = list.get(start);
			if (shopServiceTime.getWeekdayStartHour() >= shopServiceTime
					.getWeekdayEndHour()) {
				return;
			} else {
				serviceTimes.add(fetchInfo(start, start, shopServiceTime));
				return;
			}
		}
		ShopServiceTime shopServiceTime = list.get(start);
		if (shopServiceTime.getWeekdayStartHour() >= shopServiceTime
				.getWeekdayEndHour()) {
			getServiceTime(list, start + 1, serviceTimes);
			return;
		}
		int end = start;
		for (int i = start + 1; i < list.size(); i++) {
			ShopServiceTime sst = list.get(i);
			if (sst.getWeekdayStartHour() >= sst.getWeekdayEndHour()) {
				serviceTimes.add(fetchInfo(start, i - 1, shopServiceTime));
				getServiceTime(list, i + 1, serviceTimes);
				return;
			} else {
				if (sst.getWeekdayStartHour() != shopServiceTime
						.getWeekdayStartHour()
						|| sst.getWeekdayEndHour() != shopServiceTime
								.getWeekdayEndHour()) {
					serviceTimes.add(fetchInfo(start, i - 1, shopServiceTime));
					getServiceTime(list, i, serviceTimes);
					return;
				}else{
					end = i;
				}
			}
		}
		serviceTimes.add(fetchInfo(start, end, shopServiceTime));
	}
	

	private String fetchInfo(int start, int end, ShopServiceTime shopServiceTime) {
		if (start == end) {
			return weekdaySwitch(shopServiceTime.getWeekday()) + " "
					+ shopServiceTime.getWeekdayStartHour()+TIME_MIAO + "-"
					+ shopServiceTime.getWeekdayEndHour()+TIME_MIAO;
		} else {
			return weekdaySwitch(shopServiceTime.getWeekday()) + "到"
					+ weekdaySwitch(end + 1) + " "
					+ shopServiceTime.getWeekdayStartHour()+TIME_MIAO + "-"
					+ shopServiceTime.getWeekdayEndHour()+TIME_MIAO;
		}
	}

	private String weekdaySwitch(int weekday) {
		switch (weekday) {
		case MagicNumber.ONE:
			return "\u5468\u4e00";
		case MagicNumber.TWO:
			return "\u5468\u4e8c";
		case MagicNumber.THREE:
			return "\u5468\u4e09";
		case MagicNumber.FOUR:
			return "\u5468\u56db";
		case MagicNumber.FIVE:
			return "\u5468\u4e94";
		case MagicNumber.SIX:
			return "\u5468\u516d";
		case MagicNumber.SEVEN:
			return "\u5468\u65e5";
		default:
			return null;
		}
	}

	@Override
	public long findProductSaleCount(Map<String, Object> parameters) {
		return shopDao.findProductSaleCount(parameters);
	}
}
