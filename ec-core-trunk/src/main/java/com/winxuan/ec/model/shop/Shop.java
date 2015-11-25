package com.winxuan.ec.model.shop;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerQuestion;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleRank;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.Seller;

/**
 * description
 * 
 * @author yuhu
 * @version 1.0,2011-9-28
 */

@Entity
@Table(name = "shop")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Shop implements Serializable {
	/**
	 * 文轩网SHOP编号
	 */
	public static final Long WINXUAN_SHOP = 1L;

	public static final String SCOPE_BOOK = "B";
	public static final String SCOPE_VEDIO = "V";
	public static final String SCOPE_GOODS = "G";

	private static final long serialVersionUID = 1L;

	private static final String LOGO_URL = "http://static.winxuancdn.com/upload/seller/";

	private static final String BLANK_LOGO = "http://static.winxuancdn.com/goods/sml_blank.jpg";
	private static final long ZERO = 0;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "shopname")
	private String shopName;

	@Column(name = "businessscope")
	private String businessScope;

	@Column(name = "companyname")
	private String companyName;

	@Column(name = "servicetel")
	private String serviceTel;

	@Column
	private String address;

	@Column(name = "zipcode")
	private String zipCode;

	@Column
	private String bank;
	
	@Column
	private String storage;

	@Column(name = "bankacount")
	private String bankAcount;
	
	@Column(name = "buy_area")
	private String buyArea;

	@Column(name = "deliveryfee")
	private BigDecimal deliveryFee = BigDecimal.ZERO;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state")
	private Code state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grade")
	private Code grade;

	@Column
	private String logo;

	@Column(name = "enddate")
	private Date endDate;

	@Column(name = "createdate")
	private Date createDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "createuser")
	private Employee createUser;

	@Column(name = "activedate")
	private Date activeDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "activeuser")
	private Employee activeUser;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, targetEntity = Seller.class)
	private Set<Seller> sellers;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, targetEntity = ShopLog.class)
	private Set<ShopLog> logs;
	
	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, targetEntity = ShopServiceNo.class)
	private Set<ShopServiceNo> shopServices;
	
	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, targetEntity = ShopServiceTime.class)
	@OrderBy("weekday")
	private Set<ShopServiceTime> shopServiceTimes;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, targetEntity = ShopCategory.class)
	@OrderBy("index")
	private Set<ShopCategory> shopCategorys;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, targetEntity = ShopUsualCategory.class)
	private Set<ShopUsualCategory> shopUsualCategory;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, targetEntity = ShopColumn.class)
	@OrderBy("index")
	private Set<ShopColumn> shopColumn;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, targetEntity = CustomerQuestion.class)
	private Set<CustomerQuestion> questions;

	@Transient
	private List<Integer> scorelist;

	public Shop() {
		super();
	}

	public Shop(Long id) {
		this.id = id;
	}

	public Set<ShopColumn> getShopColumn() {
		return shopColumn;
	}

	public void setShopColumn(Set<ShopColumn> shopColumn) {
		this.shopColumn = shopColumn;
	}

	public String getBuyArea() {
		return buyArea;
	}

	public void setBuyArea(String buyArea) {
		this.buyArea = buyArea;
	}
	public Set<CustomerQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<CustomerQuestion> questions) {
		this.questions = questions;
	}

	public List<Integer> getScorelist() {
		return scorelist;
	}

	public void setScorelist(List<Integer> scorelist) {
		this.scorelist = scorelist;
	}

	/**
	 * 获取店铺管理员
	 * 
	 * @return
	 */
	public Seller getManagerSeller() {
		if (sellers == null || sellers.isEmpty()) {
			return null;
		}
		for (Seller seller : sellers) {
			if (seller.isShopManager()) {
				return seller;
			}
		}
		return null;
	}

	public String getName() {
		return this.shopName;
	}

	public Set<ShopServiceNo> getShopServices() {
		return shopServices;
	}

	public void setShopServices(Set<ShopServiceNo> shopServices) {
		this.shopServices = shopServices;
	}
	
	public void addShopService(ShopServiceNo shopService) {
		if (shopServices == null) {
			shopServices = new HashSet<ShopServiceNo>();
		}
		shopServices.add(shopService);
	}

	/**
	 * 添加一条店铺日志
	 * 
	 * @param log
	 */
	public void addLog(ShopLog log) {
		if (logs == null) {
			logs = new HashSet<ShopLog>();
		}
		logs.add(log);
	}

	/**
	 * 获得店铺所有的商品数量
	 * 
	 * @return
	 */
	public Long getAllProductSize() {
		if (shopCategorys == null) {
			return null;
		}
		Long size = 0L;
		for (ShopCategory sc : shopCategorys) {
			size += sc.getProductNum();
		}
		return size;
	}

	/**
	 * 根据设定数量获取店铺栏目展示产品数量
	 * 
	 * @param shop
	 * @return
	 */
	public List<ProductSale> getShopColumnByNum(ShopColumn shopColumn) {
		List<ProductSale> result = null;
		if (shopColumn != null) {
			result = new ArrayList<ProductSale>();
			Set<ShopColumnItem> shopColumnList = shopColumn
					.getShopColumnItems();
			for (ShopColumnItem shopColumnItem : shopColumnList) {
				if (result.size() == shopColumn.getProductnum()) {
					break;
				}
				result.add(shopColumnItem.getProductSale());
			}
		}
		return result;
	}

	/**
	 * 获取店铺分类有效数量
	 * 
	 * @return
	 */
	public Set<ShopCategory> getEffectiveNum() {
		if (shopCategorys.size() == 0 || shopCategorys.isEmpty()) {
			return shopCategorys;
		}

		Set<ShopCategory> categoryList = new HashSet<ShopCategory>();
		for (ShopCategory shopCategory : shopCategorys) {
			if (shopCategory.getChildren().size() == ZERO
					&& shopCategory.getProductNum() != ZERO) {
				categoryList.add(shopCategory);
			}
		}
		return categoryList;
	}

	public Set<ShopCategory> getShopCategorys() {
		return shopCategorys;
	}

	/**
	 * 获取店铺正在使用的类别
	 * 
	 * @return
	 */
	public Set<ShopCategory> getUseShopCategory() {
		Set<ShopCategory> result = getShopCategorys();
		for(Iterator<ShopCategory> it = result.iterator() ; it.hasNext() ;){
			ShopCategory shopCategory = it.next();
			if(!shopCategory.isAvailable()){
				it.remove();
			}
		}
		return result;

	}

	public void setShopCategorys(Set<ShopCategory> shopCategorys) {
		this.shopCategorys = shopCategorys;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getServiceTel() {
		return serviceTel;
	}

	public void setServiceTel(String serviceTel) {
		this.serviceTel = serviceTel;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public Code getGrade() {
		return grade;
	}

	public void setGrade(Code grade) {
		this.grade = grade;
	}

	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Set<Seller> getSellers() {
		return sellers;
	}

	public void setSellers(Set<Seller> sellers) {
		this.sellers = sellers;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<ShopServiceTime> getShopServiceTimes() {
		return shopServiceTimes;
	}

	public void setShopServiceTimes(Set<ShopServiceTime> shopServiceTimes) {
		this.shopServiceTimes = shopServiceTimes;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankAcount() {
		return bankAcount;
	}

	public void setBankAcount(String bankAcount) {
		this.bankAcount = bankAcount;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Code getState() {
		return state;
	}

	public void setState(Code state) {
		this.state = state;
	}

	public String getLogo() {
		return logo;
	}

	public String getLogoUrl(){
		if(StringUtils.isBlank(logo)){
			return BLANK_LOGO;
		}
		return LOGO_URL+logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Employee getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Employee createUser) {
		this.createUser = createUser;
	}

	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	public Employee getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(Employee activeUser) {
		this.activeUser = activeUser;
	}

	public Set<ShopLog> getLogs() {
		return logs;
	}

	public void setLogs(Set<ShopLog> logs) {
		this.logs = logs;
	}

	public Set<ShopUsualCategory> getShopUsualCategory() {
		return shopUsualCategory;
	}

	public void setShopUsualCategory(Set<ShopUsualCategory> shopUsualCategory) {
		this.shopUsualCategory = shopUsualCategory;
	}

	/**
	 * 检查店铺是否可经营scope类型的商品
	 * 
	 * @param scope
	 * @return
	 */
	public boolean checkScope(String scope) {
		if (StringUtils.isBlank(scope)) {
			return false;
		}
		return businessScope.indexOf(scope) >= 0 ? true : false;
	}

	/**
	 * 该店铺商品是否可以销售
	 * 
	 * @return
	 */
	public boolean canSale() {
		return state.getId().equals(Code.SHOP_STATE_PASS)
				|| state.getId().equals(Code.SHOP_STATE_SEARCH_FAIL);
	}

	/**
	 * 总共多少分
	 * 
	 * @return
	 */
	private int getCountScore() {
		int count = 0;
		if (scorelist.size() != 0 && !scorelist.isEmpty()) {
			for (Integer integer : scorelist) {
				count += integer;
			}
		}
		return count;
	}

	/**
	 * 每颗星多少人
	 * 
	 * @param start
	 * @return
	 */
	public int getCountScoreByStart(short start) {
		int count = 0;
		if (scorelist.size() != 0 && !scorelist.isEmpty()) {
			for (Integer integer : scorelist) {
				if (integer == start) {
					count += 1;
				}
			}
		}
		return count;
	}

	/**
	 * 总平均分
	 * 
	 * @return
	 */
	public BigDecimal getAvgStarScore() {
		int count = this.getCountScore();
		if (count == 0) {
			return BigDecimal.ZERO;
		}
		return new BigDecimal(count).divide(new BigDecimal(scorelist.size()),
				1, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 每颗星占总人数多少
	 * 
	 * @param starOne
	 * @return
	 */
	private BigDecimal getShareByScore(short star) {

		if (scorelist != null && !scorelist.isEmpty()) {
			int count = this.scorelist.size();
			return count == 0 ? BigDecimal.ZERO : new BigDecimal(
					this.getCountScoreByStart(star)).divide(new BigDecimal(
					count), 2, RoundingMode.HALF_UP);
		}
		return BigDecimal.ZERO;
	}

	public BigDecimal getShareByOneStar() {
		return getShareByScore(ProductSaleRank.STAR_ONE);
	}

	public BigDecimal getShareByTwoStar() {
		return getShareByScore(ProductSaleRank.STAR_TWO);
	}

	public BigDecimal getShareByThreeStar() {
		return getShareByScore(ProductSaleRank.STAR_THREE);
	}

	public BigDecimal getShareByFourStar() {
		return getShareByScore(ProductSaleRank.STAR_FOUR);
	}

	public BigDecimal getShareByFiveStar() {
		return getShareByScore(ProductSaleRank.STAR_FIVE);
	}

	public int getRankCountByOneStar() {
		return getCountScoreByStart(ProductSaleRank.STAR_ONE);
	}

	public int getRankCountByTwoStar() {
		return getCountScoreByStart(ProductSaleRank.STAR_TWO);
	}

	public int getRankCountByThreeStar() {
		return getCountScoreByStart(ProductSaleRank.STAR_THREE);
	}

	public int getRankCountByFourStar() {
		return getCountScoreByStart(ProductSaleRank.STAR_FOUR);
	}

	public int getRankCountByFiveStar() {
		return getCountScoreByStart(ProductSaleRank.STAR_FIVE);
	}

}
